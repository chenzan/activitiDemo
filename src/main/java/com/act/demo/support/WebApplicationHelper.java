package com.act.demo.support;

import com.act.demo.support.annotation.IgnoreLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class WebApplicationHelper {
    public static final String LOGIN = "/login";

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 判断是否是忽略Url
     *
     * @param request
     * @param handlerMethod
     * @return8
     */
    public static boolean isIgnoreLoginRequest(HttpServletRequest request, HandlerMethod handlerMethod) {
        String requestUrl = request.getServletPath();
        if (handlerMethod != null) {
            IgnoreLogin ignoreLogin = handlerMethod.getMethodAnnotation(IgnoreLogin.class);
            if (ignoreLogin == null) {
                ignoreLogin = handlerMethod.getBeanType().getAnnotation(IgnoreLogin.class);
            }
            if (ignoreLogin != null) {
                log.info("ignore login intercept:" + requestUrl + "");
                return true;
            }
        }
        return false;
    }
}
