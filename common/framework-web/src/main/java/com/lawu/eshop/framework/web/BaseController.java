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

    public Result response(int retCode) {
        return response(retCode, ResultCode.get(retCode));
    }

    public Result response(int retCode, String message) {
        Result result = new Result();
        result.setRet(retCode);
        result.setMsg(message);
        return result;
    }

    public <T> Result<T> response(int retCode, T model) {
        return response(retCode, ResultCode.get(retCode), model);
    }

    public <T> Result<T> response(int retCode, String message, T model) {
        Result<T> result = new Result();
        result.setRet(retCode);
        result.setMsg(message);
        result.setModel(model);
        return result;
    }

    public <T> Result<T> successResponse(T model) {
        return response(ResultCode.SUCCESS_CODE, model);
    }

    public <T> Result<T> successResponse() {
        return successResponse(null);
    }

    public Result failResponse(int retCode, String debug) {
        Result result = new Result();
        result.setRet(retCode);
        result.setMsg(ResultCode.get(retCode));
        result.setDebug(debug);
        return result;
    }

    public ModelAndView createMav(String viewName, Map<String, ?> model) {
        return new ModelAndView(viewName, model);
    }

}
