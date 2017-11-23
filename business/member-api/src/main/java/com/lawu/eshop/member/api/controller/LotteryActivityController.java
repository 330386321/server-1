package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.LotteryActivityDTO;
import com.lawu.eshop.mall.param.LotteryActivityParam;
import com.lawu.eshop.mall.param.LotteryActivityRealParam;
import com.lawu.eshop.member.api.service.LotteryActivityService;
import com.lawu.eshop.order.dto.foreign.MemberMineInfoForeignDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@Api(tags = "lotteryActivity")
@RestController
@RequestMapping(value = "lotteryActivity/")
public class LotteryActivityController extends BaseController {

    @Autowired
    private LotteryActivityService lotteryActivityService;

    @ApiOperation(value = "抽奖活动列表", notes = "抽奖活动列表。 (梅述全)", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listLotteryActivity", method = RequestMethod.GET)
    public Result<MemberMineInfoForeignDTO> listLotteryActivity(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                @ModelAttribute LotteryActivityParam activityParam) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        LotteryActivityRealParam param = new LotteryActivityRealParam();
        param.setCurrentPage(activityParam.getCurrentPage());
        param.setPageSize(activityParam.getPageSize());
        param.setUserNum(userNum);
        Result<Page<LotteryActivityDTO>> result = lotteryActivityService.listLotteryActivity(param);
        return successGet(result);
    }
}
