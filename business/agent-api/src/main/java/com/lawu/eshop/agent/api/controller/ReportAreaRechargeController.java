package com.lawu.eshop.agent.api.controller;

import javax.validation.Valid;
import java.util.List;

import com.lawu.eshop.agent.api.service.ReportAreaRechargeService;
import com.lawu.eshop.agent.api.service.ReportAreaUserRegDailyService;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.statistics.dto.AgentAreaRechargeQReturnDTO;
import com.lawu.eshop.statistics.dto.AgentUserRegUserListDTO;
import com.lawu.eshop.statistics.param.AgentReportParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 充值统计查询
 * @author yangqh
 * @date 2017/8/15.
 */
@Api(tags = "areaRecharge")
@RestController
@RequestMapping(value = "areaRecharge/")
public class ReportAreaRechargeController extends BaseController {

    @Autowired
    private ReportAreaRechargeService reportAreaRechargeService;

    @ApiOperation(value = "查询区域充值统计", notes = "查询区域充值统计（杨清华）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getAreaRechargeList", method = RequestMethod.GET)
    public Result<AgentAreaRechargeQReturnDTO> getAreaRechargeList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                         @ModelAttribute @Valid AgentReportParam param, BindingResult result) {
        String message = validate(result);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return successCreated(reportAreaRechargeService.getAreaRechargeList(param));
    }
}
