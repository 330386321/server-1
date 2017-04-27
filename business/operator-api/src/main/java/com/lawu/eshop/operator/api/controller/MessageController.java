package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.dto.OperatorMessageDTO;
import com.lawu.eshop.mall.param.*;
import com.lawu.eshop.operator.api.service.MemberService;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.operator.api.service.MessageService;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.user.dto.MessagePushDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Api(tags = "message")
@RestController
@RequestMapping("message/")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MerchantService merchantService;


    @ApiOperation(value = "指定用户发送系统通知", notes = "指定用户发送系统通知 [1005,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMessage", method = RequestMethod.POST)
    public Result saveMessage(@ModelAttribute OperatorMessageInfoParam messageInfoParam) {
        //增加系统站内消息
        String moblie = messageInfoParam.getMoblie();
        String userNum = "";
        MessagePushDTO messagePushDTO = null;
        if (UserTypeEnum.MEMBER.val == messageInfoParam.getUserType().val) {
            //查询用户信息
            messagePushDTO = memberService.findMessagePushByMobile(moblie);
            if (messagePushDTO == null) {
                return successGet(ResultCode.MEMBER_NO_EXIST);
            }
            userNum = messagePushDTO.getUserNum();
        } else {
            messagePushDTO = merchantService.findMessagePushByMobile(moblie);
            if (messagePushDTO == null) {
                return successGet(ResultCode.MEMBER_NO_EXIST);
            }
            userNum = messagePushDTO.getUserNum();
        }

        Result result = messageService.saveMessageOperator(userNum, messageInfoParam);
        return result;
    }

    @ApiOperation(value = "给所有用户发送系统通知", notes = "给所有用户发送系统通知 [1005,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMessageToAll", method = RequestMethod.POST)
    public Result saveMessageToAll(@ModelAttribute OperatorMessageListParam messageInfoParam) {

        if (messageInfoParam == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        //查询所有用户信息
        Result<List<MessagePushDTO>> result = null;
        String area = messageInfoParam.getArea();
        if (StringUtils.isEmpty(area)) {
            area = "all";
        }
        if (UserTypeEnum.MEMBER.val == messageInfoParam.getUserTypeEnum().val) {
            result = memberService.findMessagePushList(area);
        } else {
            result = merchantService.findMessagePushList(area);
        }
        if (result == null || result.getModel().isEmpty()) {
            return successCreated(ResultCode.PUSH_HAS_NOUSER);
        }
        List<PushParam> list = new ArrayList<>();
        for (MessagePushDTO messagePushDTO : result.getModel()) {
            PushParam pushParam = new PushParam();
            pushParam.setUserNum(messagePushDTO.getUserNum());
            pushParam.setGtCid(messagePushDTO.getGtCid());
            list.add(pushParam);
        }
        OperatorMessageParam param = new OperatorMessageParam();
        param.setParams(list);
        param.setUserTypeEnum(messageInfoParam.getUserTypeEnum());
        param.setArea(area);
        param.setTitle(messageInfoParam.getTitle());
        param.setContent(messageInfoParam.getContent());
        //增加系统站内消息
        Result r = messageService.saveMessageToAll(param);
        return r;
    }

    @ApiOperation(value = "查询消息列表", notes = "查询消息列表 [1000]", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @PageBody
    @RequestMapping(value = "getOperatorMessageList", method = RequestMethod.GET)
    public Result<Page<OperatorMessageDTO>> getOperatorMessageList(@ModelAttribute MessageQueryParam param) {
        Result<Page<OperatorMessageDTO>> result = messageService.getOperatorMessageList(param);
        return result;
    }

    @ApiOperation(value = "消息删除", notes = "消息删除 [1000]（章勇）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "delMessage/{ids}", method = RequestMethod.DELETE)
    public Result delMessage(@PathVariable(value = "ids") String ids) {
        Result result = messageService.delMessageByIds(ids);
        return result;
    }
}
