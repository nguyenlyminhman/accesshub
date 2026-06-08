package com.access.hub.platform.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component
public class AccessHubInterceptor implements HandlerInterceptor {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LOGGER.info(" \n\n*** Begin - preHandle *** ");
        LOGGER.info(" ==> URL : " + request.getRequestURI());
        LOGGER.info(" ==> Method : " + request.getMethod());

        LOGGER.info(" ==> Time : " + new Date());

        if(request.getRequestURI().toLowerCase().contains("swagger-ui")) return true;

        String token = "";
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        LOGGER.info(" *** End - preHandle *** ");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
        LOGGER.info(" ==> Begin - postHandle <== ");

        LOGGER.info(" ==> End - postHandle <== ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                         Exception ex) throws Exception {
        LOGGER.info(" ==> Begin - afterCompletion <== ");

        LOGGER.info(" ==> End - afterCompletion <== ");
    }

}
