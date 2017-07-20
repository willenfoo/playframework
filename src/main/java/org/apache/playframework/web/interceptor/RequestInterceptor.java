package org.apache.playframework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.playframework.cache.CacheSwitcher;
import org.apache.playframework.util.HttpServletUtils;
import org.apache.playframework.util.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		String rid = request.getHeader("X-Request-ID");
		Request.setId(rid);
		LOGGER.info("RequestId:{}, URL:{}", Request.getId(), request.getRequestURI());
		
		String cached = request.getHeader("X-Cached");
		String ip = HttpServletUtils.getIpAddr(request);
		Request.setRIP(ip);
		LOGGER.info("rid: {}", rid);
		
		if ("true".equalsIgnoreCase(cached) || "false".equalsIgnoreCase(cached)) {
			CacheSwitcher.set(Boolean.valueOf(cached));
		} else {
			CacheSwitcher.set(true);
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		Request.unset();
		CacheSwitcher.unset();
	}
}
