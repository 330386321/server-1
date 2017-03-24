package com.lawu.eshop.framework.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Leach
 * @date 2017/3/13
 */
public abstract class BaseController {

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        return response;
    }

    public <T> Result<T> response(int httpCode, int retCode, String message, String debug, T model) {
        getResponse().setStatus(httpCode);
        Result<T> result = new Result();
        result.setRet(retCode);
        result.setMsg(message);
        result.setDebug(debug);
        result.setModel(model);
        return result;
    }

    public <T> Result<T> successGet(T model) {
        return response(HttpCode.SC_OK, HttpCode.SC_OK, HttpCode.get(HttpCode.SC_OK), null, model);
    }

    public <T> Result<T> successGet() {
        return successGet(null);
    }

    public <T> Result<T> successCreated(T model) {
        return response(HttpCode.SC_CREATED, HttpCode.SC_CREATED, HttpCode.get(HttpCode.SC_CREATED), null, model);
    }

    public <T> Result<T> successCreated() {
        return successCreated(null);
    }

    public <T> Result<T> successAccepted(T model) {
        return response(HttpCode.SC_ACCEPTED, HttpCode.SC_ACCEPTED, HttpCode.get(HttpCode.SC_CREATED), null, model);
    }

    public <T> Result<T> successAccepted() {
        return successAccepted(null);
    }

    public <T> Result<T> successDelete(T model) {
        return response(HttpCode.SC_NO_CONTENT, HttpCode.SC_NO_CONTENT, HttpCode.get(HttpCode.SC_CREATED), null, model);
    }

    public <T> Result<T> successDelete() {
        return successDelete(null);
    }

    public <T> Result<T> failCreated(int retCode, T model) {
        return response(HttpCode.SC_BAD_REQUEST, retCode, HttpCode.get(retCode), null, model);
    }

    public <T> Result<T> failCreated(int retCode) {
        return failCreated(retCode, null);
    }

    public <T> Result<T> failUnauthorized(int retCode, T model) {
        return response(HttpCode.SC_UNAUTHORIZED, retCode, HttpCode.get(retCode), null, model);
    }

    public <T> Result<T> failUnauthorized(int retCode) {
        return failUnauthorized(retCode, null);
    }

    public <T> Result<T> failForbidden(int retCode, T model) {
        return response(HttpCode.SC_FORBIDDEN, retCode, HttpCode.get(retCode), null, model);
    }

    public <T> Result<T> failForbidden(int retCode) {
        return failForbidden(retCode, null);
    }

    public <T> Result<T> failGone(int retCode, T model) {
        return response(HttpCode.SC_GONE, retCode, HttpCode.get(retCode), null, model);
    }

    public <T> Result<T> failGone(int retCode) {
        return failGone(retCode, null);
    }

    public <T> Result<T> failServerError(String debug) {
        return response(HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.get(HttpCode.SC_INTERNAL_SERVER_ERROR), debug, null);
    }

    public ModelAndView createMav(String viewName, Map<String, ?> model) {
        return new ModelAndView(viewName, model);
    }

}
