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
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListInviteFansParam;
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
    @ApiOperation(value = "查询粉丝会员", notes = "查询可邀请成为粉丝的会员。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listInviteFans", method = RequestMethod.GET)
    public Result<List<FansMerchantDTO>> listInviteFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                        @ModelAttribute @ApiParam ListInviteFansParam param) {
        long merchantId = UserUtil.getCurrentUserId(getRequest());
        return fansMerchantService.listInviteFans(merchantId, param);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "邀请粉丝", notes = "邀请会员成为粉丝。[1002|1004|1022|1023|6002|6024] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "inviteFans", method = RequestMethod.POST)
    public Result inviteFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                             @ModelAttribute InviteFansParam param) {
        if (StringUtils.isEmpty(param.getNums())) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        String[] numArray = param.getNums().split(",");
        String userNum = UserUtil.getCurrentUserNum(getRequest());

        Result<Boolean> pwdResult = propertyInfoService.varifyPayPwd(userNum, param.getPayPwd());
        if (!isSuccess(pwdResult)) {
            return pwdResult;
        }
        if (!pwdResult.getModel()) {
            return successCreated(ResultCode.PAY_PWD_ERROR);
        }

        //邀请粉丝扣除积分、插入粉丝邀请记录
        PropertyInfoDataParam propertyInfoDataParam = new PropertyInfoDataParam();
        propertyInfoDataParam.setUserNum(userNum);
        propertyInfoDataParam.setPoint(String.valueOf(numArray.length));
        //propertyInfoDataParam.setTransactionTitleEnum(TransactionTitleEnum.INVITE_FANS);
        propertyInfoDataParam.setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum.INVITE_FANS);
        propertyInfoDataParam.setMerchantId(UserUtil.getCurrentUserId(getRequest()));
        propertyInfoDataParam.setRegionName(param.getRegionName());
        propertyInfoDataParam.setInviteFansCount(numArray.length);
        propertyInfoDataParam.setSex(param.getUserSexEnum().val);
        propertyInfoDataParam.setAge(param.getIsAgeLimit() ? param.getStartAge() + "-" + param.getEndAge() : "");
        Result result = propertyInfoService.inviteFans(propertyInfoDataParam);
        if (!isSuccess(result)) {
            return result;
        }

        //发送站内消息
        MessageInfoParam messageInfoParam = new MessageInfoParam();
        messageInfoParam.setRelateId(UserUtil.getCurrentUserId(getRequest()));
        messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_STORE);
        // TODO 发送消息实体内容
        messageInfoParam.setMessageParam("");
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
                                                  @ModelAttribute @ApiParam ListFansParam listFansParam) {
        long merchantId = UserUtil.getCurrentUserId(getRequest());
        return fansMerchantService.listFans(merchantId, listFansParam);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "粉丝数量", notes = "查询商家粉丝数量。 (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "countFans", method = RequestMethod.GET)
    public Result<Integer> countFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        long merchantId = UserUtil.getCurrentUserId(getRequest());
        return fansMerchantService.countFans(merchantId);
    }

}
