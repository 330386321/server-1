package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.dto.LotteryRecordOperatorDTO;
import com.lawu.eshop.mall.query.OperatorLotteryRecordQuery;
import com.lawu.eshop.operator.api.service.LotteryRecordService;
import com.lawu.eshop.operator.api.service.MemberService;
import com.lawu.eshop.user.dto.MemberDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/11/27.
 */
@Api(tags = "lotteryRecord")
@RestController
@RequestMapping(value = "lotteryRecord/")
public class LotteryRecordController extends BaseController {

    @Autowired
    private LotteryRecordService lotteryRecordService;

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "参与抽奖列表", notes = "参与抽奖列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    //@RequiresPermissions("log:list")
    @PageBody
    @RequestMapping(value = "listOperatorLotteryRecord", method = RequestMethod.POST)
    public Result<Page<LotteryRecordOperatorDTO>> listOperatorLotteryRecord(@RequestBody @ApiParam OperatorLotteryRecordQuery query) {
        Result<Page<LotteryRecordOperatorDTO>> result = lotteryRecordService.listOperatorLotteryRecord(query);
        if (!result.getModel().getRecords().isEmpty()) {
            for (LotteryRecordOperatorDTO operatorDTO : result.getModel().getRecords()) {
                Result<MemberDTO> memberResult = memberService.getMemberByAccount(operatorDTO.getAccount());
                if (isSuccess(memberResult)) {
                    operatorDTO.setName(memberResult.getModel().getName());
                }
            }
        }
        return result;
    }

}
