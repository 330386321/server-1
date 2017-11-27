package com.lawu.eshop.operator.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.UserGradeService;
import com.lawu.eshop.user.dto.UserGradeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/11/24.
 */
@Api(tags = "userGrade")
@RestController
@RequestMapping(value = "userGrade/")
public class UserGradeController extends BaseController {

    @Autowired
    private UserGradeService userGradeService;

    @ApiOperation(value = "查询会员等级", notes = "查询会员等级。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@RequiresPermissions("log:list")
    @RequestMapping(value = "selectLotteryActivityPointByGradeValue", method = RequestMethod.GET)
    public Result<List<UserGradeDTO>> selectLotteryActivityPointByGradeValue() {
        return userGradeService.selectLotteryActivityPointByGradeValue();
    }

}
