package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.merchant.api.service.FansMerchantService;
import com.lawu.eshop.merchant.api.service.MessageService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.param.ListFansParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Autowired
    private MessageService messageService;

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "查询粉丝会员", notes = "查询可邀请成为粉丝的会员。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listInviteFans", method = RequestMethod.GET)
    public Result<List<FansMerchantDTO>> updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                        @RequestParam @ApiParam(required = true, value = "区域路径") String regionPath) {
        long merchantId = UserUtil.getCurrentUserId(getRequest());
        return fansMerchantService.listInviteFans(merchantId, regionPath);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "邀请粉丝", notes = "邀请会员成为粉丝。[1004|6002|6024] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "inviteFans", method = RequestMethod.POST)
    public Result inviteFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                             @RequestParam @ApiParam(required = true, value = "会员编号,以逗号分隔") String nums) {
        if (StringUtils.isEmpty(nums)) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        String[] numArray = nums.split(",");
        String userNum = UserUtil.getCurrentUserNum(getRequest());

        //邀请粉丝扣除积分
        PropertyInfoDataParam propertyInfoDataParam = new PropertyInfoDataParam();
        propertyInfoDataParam.setUserNum(userNum);
        propertyInfoDataParam.setPoint(String.valueOf(numArray.length));
        Result result = propertyInfoService.inviteFans(propertyInfoDataParam);
        if (!isSuccess(result)) {
            return result;
        }

        //发送站内消息
        MessageInfoParam messageInfoParam = new MessageInfoParam();
        messageInfoParam.setRelateId(UserUtil.getCurrentUserId(getRequest()));
        messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_STORE);
        messageInfoParam.setContent(UserUtil.getCurrentAccount(getRequest()) + "邀请你成为店铺粉丝");
        for (String num : numArray) {
            messageService.saveMessage(num, messageInfoParam);
        }
        return successCreated();
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "粉丝列表", notes = "商户粉丝列表。 (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listFans", method = RequestMethod.GET)
    public Result<Page<FansMerchantDTO>> listFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                  @ModelAttribute @ApiParam(required = true, value = "查询条件") ListFansParam listFansParam) {
        long merchantId = UserUtil.getCurrentUserId(getRequest());
        return fansMerchantService.listFans(merchantId, listFansParam);
    }
}
