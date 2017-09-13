package com.lawu.eshop.operator.api;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * @author Leach
 * @date 2017/5/9
 */
@ControllerAdvice
public class OperatorApiExceptionHandler extends BaseController {


    private Logger logger = LoggerFactory.getLogger(OperatorApiExceptionHandler.class);

    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public Result defaultUnauthenticatedHandler(UnauthenticatedException e) throws Exception {
        return failUnauthorized(ResultCode.USER_UNAUTHORIZED);
    }


}
