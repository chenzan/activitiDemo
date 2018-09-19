package com.act.demo.support.resolver;

import com.act.demo.support.annotation.RequestList;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 处理controller方法参数
 */
@Component
public class ListArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestList.class);
    }

    /**
     * 相同name的参数
     *
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        List<String> list = new ArrayList();
        RequestList requestList = methodParameter.getParameterAnnotation(RequestList.class);
        String[] parameterValues = nativeWebRequest.getParameterValues(requestList.name());
        if (parameterValues != null) {
            if (parameterValues.length > 0) {
                if (parameterValues.length == 1) {//,分割情况
                    String parameter = nativeWebRequest.getParameter(requestList.name());
                    String[] split = parameter.split(requestList.separator());
                    Collections.addAll(list, split);
                } else {
                    Collections.addAll(list, parameterValues);
                }
            }
        }
        return list;
    }
}
