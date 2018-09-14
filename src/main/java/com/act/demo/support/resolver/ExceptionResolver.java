package com.act.demo.support.resolver;

import com.act.demo.common.ConstantValue;
import com.act.demo.common.SessionContext;
import com.act.demo.domain.SysUser;
import com.act.demo.support.ResponseResult;
import com.act.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * 处理异常
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Resource
    SessionContext sessionContext;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        //httpServletResponse调用了geOutputStream().write()做了提交，response不能再写东西
        if (response.isCommitted()) {
            return null;
        }
        SysUser user = sessionContext.getSessionValue(ConstantValue.CURRENT_USER, SysUser.class);

        int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String errorMsg = "服务器繁忙，请稍后再试！";
        if (ex instanceof ConversionNotSupportedException) {
            errorMsg = "服务器内部异常！";
            errorCode = HttpStatus.NOT_ACCEPTABLE.value();
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            errorMsg = "无和请求Accept匹配的MIME类型！";
            errorCode = HttpStatus.NOT_ACCEPTABLE.value();
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            errorMsg = "不支持的MIME类型！";
            errorCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
        } else if (ex instanceof HttpMessageNotReadableException) {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        } else if (ex instanceof HttpMessageNotWritableException) {
            errorMsg = "消息转换异常！";
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            errorMsg = "不支持的请求方法:" + request.getMethod();
            errorCode = HttpStatus.METHOD_NOT_ALLOWED.value();
        } else if (ex instanceof MissingServletRequestParameterException) {
            errorMsg = "请求出错！";
            errorCode = HttpStatus.BAD_REQUEST.value();
        }
//        } else if (ex instanceof NoSuchRequestHandlingMethodException) {
//            errorMsg = "找不到请求的资源！";
//            errorCode = HttpStatus.NOT_FOUND.value();
//        }
        else if (ex instanceof TypeMismatchException) {
            errorMsg = "类型转换错误";
            errorCode = HttpStatus.BAD_REQUEST.value();
        } else if (ex instanceof SQLException) {
            errorMsg = "数据库操作异常！";
            errorCode = HttpStatus.SERVICE_UNAVAILABLE.value();
        }
        log.error(ex.getMessage(), ex);
        try {
            if (HttpUtil.isAjaxWithRequest(request)) {
                response.reset();
                response.setContentType("text/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(ResponseResult.error(errorMsg, errorCode));
                response.getWriter().close();
                return null;
            }
            return new ModelAndView("_error/" + errorCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("_error/500");
    }
}
