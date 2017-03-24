package com.lawu.eshop.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(Exception e) throws Exception {
        logger.error("内部异常", e);
        return failServerError(e.getMessage());
    }

}
