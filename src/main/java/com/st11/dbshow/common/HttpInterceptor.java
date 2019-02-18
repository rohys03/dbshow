package com.st11.dbshow.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import static com.st11.dbshow.common.DbShow.getParameterMap;

@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        logger.info("[Request] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        return true;
    }

}
