package org.apache.playframework.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.playframework.annotations.ResubmitToken;
import org.apache.playframework.domain.ClientMessage;
import org.apache.playframework.util.Md5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * <p>
 * 表单重复提交切面，使用注解标记是否验证。
 * key值的生成,包名+类+方法,md5生成唯一key
 * 注意事项：如果类中方法名相同，只能选择一个方法打注解,否则会互斥
 * </p>
 * @author fuwei
 * @Date 2017-04-06
 */
@Aspect
@Component
public class RedisResubmitAspect {
	 
	private static final String RESUBMIT_TOKEN = "resubmit_token_";

	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	 
	@Autowired
	private HttpServletRequest request; 
	
	public ShardedJedis getShardedJedis() {
		ShardedJedis shardJedis = null;
		try {
			shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		} catch (Exception e) {
			if (shardJedis != null) {
				shardJedis.close();
			}
		}
		return null;
	}


	/**
	 * <p>
	 * 执行切面拦截逻辑
	 * </p>
	 * @param joinPoint 切面对象
	 * @param resubmitToken 表单票据注解
	 * @throws Throwable
	 */
	@Around("@annotation(resubmitToken)")
	public Object execute(ProceedingJoinPoint joinPoint, ResubmitToken resubmitToken) throws Throwable {
		String lockRequestParam = request.getParameter(resubmitToken.lockRequestParam());
		if (StringUtils.isBlank(lockRequestParam)) {
			logger.debug("防止重复提交,锁定请求参数【{}】为空", resubmitToken.lockRequestParam());
			return new ClientMessage("1005", "非法请求");
		}
		String methodFullName = joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName();
		String resubmitTokenKey = RESUBMIT_TOKEN + Md5Utils.getMD5(methodFullName) + "_" + lockRequestParam;
		ShardedJedis jedis = getShardedJedis();
		long count = 0;
		logger.debug("resubmitTokenKey lock key: " + resubmitTokenKey);
		try {
			count = jedis.incr(resubmitTokenKey);
			// 如果等于1，说明是第一个请求，如果该KEY的数值大于1，说明是1分钟内的多次请求，
			if (count == 1) {
				// 设置有效期20秒钟
				jedis.expire(resubmitTokenKey, resubmitToken.expiredTime());
				logger.debug("resubmitTokenKey get lock, key: " + resubmitTokenKey + " , expire in " + resubmitToken.expiredTime() + " seconds.");
				return joinPoint.proceed();
			} else {
				String desc = jedis.get(resubmitTokenKey);  
				logger.debug("resubmitTokenKey key: " + resubmitTokenKey + " locked by another business：" + desc);
				return null;
			}
		} finally {
			if (count == 1) {
				jedis.del(resubmitTokenKey);
				logger.debug("release lock, resubmitTokenKey key :" + resubmitTokenKey);
			}
			jedis.close();
		}
	}

}
