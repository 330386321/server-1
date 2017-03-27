package com.lawu.eshop.test.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.test.api.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leach
 * @date 2017/3/27
 */
@Api(tags = "fail")
@RestController
@RequestMapping(value = "fail/")
public class FailController extends BaseController {

    @Autowired
    private MemberService memberService;


    @ApiOperation(value = "GET请求失败", notes = "服务端异常。[]（孙林青）", httpMethod = "GET")
    @RequestMapping(value = "get/{pathParam}", method = RequestMethod.GET)
    public Result<TestDTO> getFail(@PathVariable @ApiParam(required = true, value = "路径参数") String pathParam,
                                  @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        return failServerError("此处是调试信息，方便服务端排错");
    }


}

