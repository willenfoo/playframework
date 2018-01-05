package org.apache.playframework.aop;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.playframework.domain.ClientMessage;
import org.apache.playframework.security.UserUtils;
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

	public ShardedJedis getShardedJedis() {
		ShardedJedis shardJedis = null;
		try {
			shardJedis = shardedJedisPool.getResource();
		} catch (Exception e) {
			if (shardJedis != null) {
				shardJedis.close();
			}
		}
		return shardJedis;
	}


	/**
	 * <p>
	 * 执行切面拦截逻辑
	 * </p>
	 * @param joinPoint 切面对象
	 * @throws Throwable
	 */
	@Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		String userId = UserUtils.getUserId();
		if (StringUtils.isBlank(userId)) {
			logger.debug("防止重复提交,用户未登录【{}】为空");
			return ClientMessage.failed("1000", "用户未登录");
		}
		String methodFullName = joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName();
		String resubmitTokenKey = RESUBMIT_TOKEN + Md5Utils.getMD5(methodFullName) + "_" + userId;
		ShardedJedis jedis = getShardedJedis();
		long count = 0;
		logger.debug("resubmitTokenKey lock key: " + resubmitTokenKey);
		try {
			count = jedis.incr(resubmitTokenKey);
			// 如果等于1，说明是第一个请求，如果该KEY的数值大于1，说明是第一次请求处理未完成，重复提交的请求，不做处理
			if (count == 1) {
				// 设置有效期
				jedis.expire(resubmitTokenKey, 20);
				logger.debug("resubmitTokenKey get lock, key: " + resubmitTokenKey + " , expire in 20 seconds.");
				return joinPoint.proceed();
			} else {
				if (logger.isDebugEnabled()) {
					String desc = jedis.get(resubmitTokenKey);  
					logger.debug("resubmitTokenKey key: " + resubmitTokenKey + " locked by another business：" + desc);
				}
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
