package com.lawu.eshop.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.lawu.eshop.framework.web.exception.HeaderParamException;

/**
 * 统一异常处理
 *
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

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result defaultMethodArgumentErrorHandler(MethodArgumentTypeMismatchException e) throws Exception {
        logger.error("参数格式错误", e);
        return response(HttpCode.SC_NOT_ACCEPTABLE, ResultCode.FAIL, "参数格式错误", e.getMessage(), null);
    }

    @ExceptionHandler(value = HeaderParamException.class)
    @ResponseBody
    public Result headerParamErrorHandler(HeaderParamException e) throws Exception {
        logger.error("头部参数非法", e);
        return response(HttpCode.SC_NOT_ACCEPTABLE, ResultCode.FAIL, "头部参数非法", e.getMessage(), null);
    }

}
