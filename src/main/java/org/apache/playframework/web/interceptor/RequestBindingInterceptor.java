package org.apache.playframework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.apache.playframework.cache.CacheSwitcher;
import org.apache.playframework.util.HttpServletUtils;
import org.apache.playframework.util.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.reflect.Method;

/**
 * 请求绑定拦截器，
 * 1. 绑定请求id，为每一个请求分配一个请求id，日志打印出来，方便查找错误
 * 2. 绑定API功能关键字，日志打印出来，方便查找错误
 *
 * @author fuwei
 */
public class RequestBindingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestBindingInterceptor.class);

    private static final String USER_LOG_KEYWORD = ",用户ID:%s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            //设置日志关键字，如果用户登录了，加上用户id
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                String userId = request.getParameter("userId");
                String logKeyword;
                if (!StringUtils.isEmpty(userId)) {
                    logKeyword = String.format(apiOperation.value() + USER_LOG_KEYWORD, userId);
                } else {
                    logKeyword = apiOperation.value();
                }
                Request.setLogKeyword(logKeyword);
            }
        }
        String rid = request.getHeader("X-Request-ID");
        String userId = request.getHeader("userId");
        String merchantId = request.getHeader("merchantId");
        Request.setId(rid);
        LOGGER.info("RequestId:{}, URL:{}, method:{}, merchantId:{}, userId:{}, parameters:{}", Request.getId(), request.getRequestURI(),request.getMethod(),  merchantId, userId, HttpServletUtils.getParameterMap(request));

        String cached = request.getHeader("X-Cached");
        String ip = HttpServletUtils.getIpAddr(request);
        Request.setRIP(ip);

        if ("true".equalsIgnoreCase(cached) || "false".equalsIgnoreCase(cached)) {
            CacheSwitcher.set(Boolean.valueOf(cached));
        } else {
            CacheSwitcher.set(true);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        Request.unset();
        CacheSwitcher.unset();
    }
}
