package com.act.demo.common;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionContext {

    private HttpSession initSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession(true);
    }

    /**
     * 设置值到session中
     *
     * @param key
     * @param objValue
     */
    public void putSessionValue(String key, Object objValue) {
        HttpSession session = initSession();
        session.setAttribute(key, objValue);
    }

    /**
     * 从session中获取值
     *
     * @param key
     * @param typeClass
     * @param <T>
     * @return
     */
    public <T> T getSessionValue(String key, Class<T> typeClass) {
        HttpSession session = initSession();
        T value = (T) session.getAttribute(key);
        return value;
    }

}
