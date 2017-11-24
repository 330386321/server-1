package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.param.LotteryRecordParam;
import com.lawu.eshop.member.api.service.LotteryRecordService;
import com.lawu.eshop.member.api.service.PointDetailService;
import com.lawu.eshop.member.api.service.PropertyInfoDataService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@Api(tags = "lotteryRecord")
@RestController
@RequestMapping(value = "lotteryRecord/")
public class LotteryRecordController extends BaseController {

    @Autowired
    private LotteryRecordService lotteryRecordService;

    @Autowired
    private PointDetailService pointDetailService;

    @Autowired
    private PropertyInfoDataService propertyInfoDataService;

    @ApiOperation(value = "立即抽奖", notes = "立即抽奖。 (梅述全)", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveLotteryRecord", method = RequestMethod.POST)
    public Result saveLotteryRecord(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                    @RequestParam @ApiParam(required = true, value = "抽奖活动ID") Long lotteryActivityId,
                                    @RequestParam @ApiParam(required = true, value = "奖品名称") String prizeName) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        String account = UserUtil.getCurrentAccount(getRequest());

        LotteryRecordParam param = new LotteryRecordParam();
        param.setUserId(userId);
        param.setUserNum(userNum);
        param.setAccount(account);
        param.setLotteryActivityId(lotteryActivityId);
        param.setPrizeName(prizeName);
        lotteryRecordService.saveLotteryRecord(param);
        return successCreated();
    }

    @ApiOperation(value = "积分抽奖", notes = "积分抽奖。[1004|2020|6025|6002|6003|6024|6010|6011] (梅述全)", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "pointConvertLottery", method = RequestMethod.POST)
    public Result pointConvertLottery(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                      @RequestParam @ApiParam(required = true, value = "抽奖活动ID") Long lotteryActivityId) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());

        Result<Boolean> result = pointDetailService.existsPointDetailByUserNumAndBizId(userNum, String.valueOf(lotteryActivityId));
        if (result.getModel()) {
            return successCreated(ResultCode.NO_LOTTERY_COUNT);
        }

        //TODO 获取对应积分
        String point = "";

        PropertyInfoDataParam param = new PropertyInfoDataParam();
        param.setUserNum(userNum);
        param.setPoint(point);
        param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.POINT_CONVERT_LOTTERY);
        param.setBizId(String.valueOf(lotteryActivityId));
        Result propertyResult = propertyInfoDataService.doHanlderMinusPointWithLottery(param);
        return successCreated(propertyResult.getRet());
    }

}
