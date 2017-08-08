package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@Api(tags = "fansMerchant")
@RestController
@RequestMapping(value = "fansMerchant/")
public class FansMerchantController extends BaseController {

    @Autowired
    private FansMerchantService fansMerchantService;

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "成为商家粉丝", notes = "成为商家粉丝。 [2012] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveFansMerchant", method = RequestMethod.PUT)
    public Result saveFansMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @RequestParam @ApiParam(required = true, value = "商家ID") Long merchantId,
                                   @RequestParam @ApiParam(required = true, value = "粉丝来源") FansMerchantChannelEnum channelEnum ) {
        long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<Boolean> result = fansMerchantService.isFansMerchant(merchantId, memberId);
        if(result.getModel()){
            return successCreated(ResultCode.FANS_MERCHANT);
        }
        return fansMerchantService.saveFansMerchant(merchantId, memberId, channelEnum);
    }

    
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "用户处理商家邀请粉丝", notes = "用户处理商家邀请粉丝。 [2012] (洪钦明)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "dealFansInvite", method = RequestMethod.PUT)
    public Result dealFansInvite(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @RequestParam @ApiParam(required = true, value = "商家ID") Long merchantId,
                                   @RequestParam @ApiParam(required = true, value = "邀请的信息的ID") Long messageId,
                                   @RequestParam @ApiParam(required = true, value = "处理方式true同意,false拒绝") Boolean dealWay) {
        long memberId = UserUtil.getCurrentUserId(getRequest());
    	Result<Boolean> result = fansMerchantService.isFansMerchant(merchantId, memberId);
        if(result.getModel()){
            return successCreated(ResultCode.FANS_MERCHANT);
        }
        return fansMerchantService.saveFansMerchantFromInvite(merchantId, memberId, messageId, dealWay);
    }
}
