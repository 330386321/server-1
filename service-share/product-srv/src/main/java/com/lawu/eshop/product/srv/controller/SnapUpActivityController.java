package com.lawu.eshop.product.srv.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.param.SnapUpActivityPageQueryParam;

@RestController
@RequestMapping(path = "snapUpActivity/")
public class SnapUpActivityController extends BaseController {
    
    /**
     * 根据查询参数分页查询活动列表
     * 用于运营平台
     * 
     * 
     * @param param
     * @param bindingResult
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    @RequestMapping(path = "operator/page", method = RequestMethod.PUT)
    public Result page(@RequestBody @Validated SnapUpActivityPageQueryParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        
        
        return null;
    }
}
