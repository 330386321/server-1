package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.merchant.api.service.FansMerchantService;
import com.lawu.eshop.merchant.api.service.MessageService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @ApiOperation(value = "查询粉丝会员", notes = "查询可邀请成为粉丝的会员。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listInviteFans", method = RequestMethod.GET)
    public Result<List<FansMerchantDTO>> updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                        @ModelAttribute @ApiParam(required = true, value = "查询条件") InviteFansParam inviteFansParam) {
        return fansMerchantService.listInviteFans(inviteFansParam);
    }

    @ApiOperation(value = "邀请粉丝", notes = "邀请会员成为粉丝。[1004|2007] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "inviteFans", method = RequestMethod.POST)
    public Result<List<FansMerchantDTO>> inviteFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                    @RequestParam @ApiParam(required = true, value = "会员编号,以逗号分隔") String nums) {
        if (StringUtils.isEmpty(nums)) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        String[] numArray = nums.split(",");
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        Result<PropertyPointDTO> result = propertyInfoService.getPropertyPoint(userNum);
        if (numArray.length > result.getModel().getPoint()) {
            return successCreated(ResultCode.USER_POINT_NOT_ENOUGH);
        }

        propertyInfoService.inviteFans(userNum, numArray.length);
        MessageInfoParam messageInfoParam = new MessageInfoParam();
        messageInfoParam.setRelateId(UserUtil.getCurrentUserId(getRequest()));
        messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_STORE);
        messageInfoParam.setContent(UserUtil.getCurrentAccount(getRequest()) + "邀请你成为店铺粉丝");
        for (String num : numArray) {
            messageService.saveMessage(num, messageInfoParam);
        }
        return successCreated();
    }

    @ApiOperation(value = "粉丝列表", notes = "商户粉丝列表。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listFans", method = RequestMethod.GET)
    public Result<Page<FansMerchantDTO>> listFans(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                  @ModelAttribute @ApiParam(required = true, value = "查询条件") ListFansParam listFansParam) {
        long merchantId = UserUtil.getCurrentUserId(getRequest());
        Result<List<FansMerchantDTO>> fansResult = fansMerchantService.listFans(merchantId, listFansParam);
        if (!isSuccess(fansResult)) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<FansMerchantDTO> fansMerchantDTOList = fansResult.getModel();
        for (FansMerchantDTO fansMerchantDTO : fansMerchantDTOList) {
            //TODO 查询消费次数、消费金额、最近消费时间
        }
        if (listFansParam.getOrderType() != null) {
            Collections.sort(fansMerchantDTOList, new Comparator<FansMerchantDTO>() {
                @Override
                public int compare(FansMerchantDTO o1, FansMerchantDTO o2) {
                    double field1 = o1.getConsumeCount();
                    double field2 = o2.getConsumeCount();
                    if (listFansParam.getOrderType() == 2) {  //消费额
                        field1 = o1.getConsumeAmount();
                        field2 = o2.getConsumeAmount();
                    } else if (listFansParam.getOrderType() == 3) {  //最近消费时间
                        field1 = DateUtil.dateDateToDoubleDate(o1.getLastConsumeTime());
                        field2 = DateUtil.dateDateToDoubleDate(o2.getLastConsumeTime());
                    }
                    if ("desc".equalsIgnoreCase(listFansParam.getOrderRule())) {
                        if (field1 > field2) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else {
                        if (field1 > field2) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            });
        }
        Page<FansMerchantDTO> page = new Page<>();
        page.setTotalCount(fansMerchantDTOList.size());
        page.setCurrentPage(listFansParam.getCurrentPage());
        List<FansMerchantDTO> fansMerchantDTOS = new ArrayList<>();
        for (int i = listFansParam.getOffset(); i < fansMerchantDTOList.size(); i++) {
            fansMerchantDTOS.add(fansMerchantDTOList.get(i));
            if (fansMerchantDTOS.size() == listFansParam.getPageSize()) {
                break;
            }
        }
        page.setRecords(fansMerchantDTOS);
        return successGet(page);
    }
}
