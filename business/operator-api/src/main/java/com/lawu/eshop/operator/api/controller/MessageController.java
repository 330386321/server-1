package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.OperatorMessageInfoParam;
import com.lawu.eshop.operator.api.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "指定用户发送系统通知", notes = "指定用户发送系统通知 [1005,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMessage/{userNum}", method = RequestMethod.POST)
    public Result saveMessage(@PathVariable("userNum") String userNum, @ModelAttribute OperatorMessageInfoParam messageInfoParam) {
        //增加系统站内消息
        Result result = messageService.saveMessageOperator(userNum, messageInfoParam);
        return result;
    }

    @ApiOperation(value = "给所有用户发送系统通知", notes = "给所有用户发送系统通知 [1005,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMessage", method = RequestMethod.POST)
    public Result saveMessageToAll(@ModelAttribute OperatorMessageInfoParam messageInfoParam) {
        //增加系统站内消息
        Result result = messageService.saveMessageToAll(messageInfoParam);
        return result;
    }
}
