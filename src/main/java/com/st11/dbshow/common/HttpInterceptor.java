package com.st11.dbshow.common;

import com.st11.dbshow.service.DbShowMongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import static com.st11.dbshow.common.DbShow.getParameterMap;

@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private DbShowMongoDBService dbShowMongoDBService;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        logger.info("[Request] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());

//        try {
//            dbShowMongoDBService.mongoLogInsert(request,response);
//        } catch (Exception e) {
//            System.out.println("[MongoDB Exception]:" + e.getStackTrace());
//            throw e;
//        }
        return true;
    }

}
