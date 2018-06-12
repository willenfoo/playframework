package org.apache.playframework.aop;

import io.swagger.annotations.ApiOperation;
import org.apache.playframework.util.HttpServletUtils;
import org.apache.playframework.util.Request;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class SwaggerLogKeywordAspect {

    @Autowired
    private HttpServletRequest request;

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerLogKeywordAspect.class);

    private static final String USER_LOG_KEYWORD = ",用户ID:%s";

    /**
     * <p>
     * 执行切面拦截逻辑
     * </p>
     * @param joinPoint 切面对象
     * @throws Throwable
     */
    @Around("@annotation(apiOperation)")
    public Object execute(ProceedingJoinPoint joinPoint, ApiOperation apiOperation) throws Throwable {
        //设置日志关键字，如果用户登录了，加上用户id
        String userId = "1000";
        String logKeyword;
        if (!StringUtils.isEmpty(userId)) {
            logKeyword = String.format(apiOperation.value() + USER_LOG_KEYWORD, userId);
        } else {
            logKeyword = apiOperation.value();
        }
        Request.setLogKeyword(logKeyword);

        //为请求分配一个requestId
        String rid = request.getHeader("X-Request-ID");
        Request.setId(rid);

        //设置请求ip
        String ip = HttpServletUtils.getIpAddr(request);
        Request.setRIP(ip);

        LOGGER.info("RequestId:{}, URL:{}", new Object[]{Request.getId(), request.getRequestURI()});

        try {
            return joinPoint.proceed();
        } finally {
            Request.unset();
        }
    }

}
