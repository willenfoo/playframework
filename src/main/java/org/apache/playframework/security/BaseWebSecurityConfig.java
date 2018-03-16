package org.apache.playframework.security;

import com.alibaba.fastjson.JSONObject;
import org.apache.playframework.domain.RestResult;
import org.apache.playframework.enums.ErrorCode;
import org.apache.playframework.util.HttpResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST API 安全验证，是否登陆判断等等
 * @author fuwei
 */
public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException authException) throws IOException, ServletException {
                        RestResult clientMessage = RestResult.failed(ErrorCode.USER_NOT_LOGIN);
                        HttpResponseUtils.renderText(response, JSONObject.toJSONString(clientMessage.getBody()));
                    }
                }).and().requestCache().requestCache(new NullRequestCache()).and().httpBasic();
    }


}