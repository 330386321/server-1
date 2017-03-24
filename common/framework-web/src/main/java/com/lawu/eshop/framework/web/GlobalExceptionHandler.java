package com.lawu.eshop.framework.web;

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
        return failServerError(e.getMessage());
    }

}
