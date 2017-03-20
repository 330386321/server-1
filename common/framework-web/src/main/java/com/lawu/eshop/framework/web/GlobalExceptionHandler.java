package com.lawu.eshop.framework.web;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * @author Leach
 * @date 2017/3/13
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(Exception e) throws Exception {
        return failResponse(500, e.getMessage());
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Result requestNotReadable(HttpMessageNotReadableException e) {
        return failResponse(400, e.getMessage());
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public Result requestTypeMismatch(TypeMismatchException e) {
        return failResponse(400, e.getMessage());
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result requestMissingServletRequest(MissingServletRequestParameterException e) {
        return failResponse(400, e.getMessage());
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result request405(HttpRequestMethodNotSupportedException e) {
        return failResponse(405, e.getMessage());
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public Result request406(HttpMediaTypeNotAcceptableException e) {
        return failResponse(406, e.getMessage());
    }

    //406错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public Result server500(RuntimeException e) {
        return failResponse(406, e.getMessage());
    }
}
