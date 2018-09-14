package com.act.demo.support.interceptor;

import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysUser;
import com.act.demo.support.ResponseResult;
import com.act.demo.support.WebApplicationHelper;
import com.act.demo.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * session拦截器
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    SessionContext sessionContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 只处理handlerMethod方法
        if (handler instanceof HandlerMethod) {
            if (WebApplicationHelper.isIgnoreLoginRequest(request, (HandlerMethod) handler)) {
                return true;
            }
            if (sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class) == null) {
                //处理ajax
                if (HttpUtil.isAjaxWithRequest(request)) {
                    Map<String, Object> extParam = new HashMap<>();
                    extParam.put("flag", "sessionTimeout");
                    ResponseResult.renderJson(response, ResponseResult.error("用户过期,请重新登录", extParam));
                } else {
                    String url = request.getContextPath() + WebApplicationHelper.LOGIN;
                    response.sendRedirect(url);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
