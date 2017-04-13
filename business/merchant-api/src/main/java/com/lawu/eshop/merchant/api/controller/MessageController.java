package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.merchant.api.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 站内信息接口
 * Created by zhangyong on 2017/3/29.
 */
@Api(tags = "message")
@RestController
@RequestMapping(value = "message/")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "站内信息统计", notes = "根据用户编号获取站内未读信息统计 [1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMessageStatistics", method = RequestMethod.GET)
    public Result<MessageStatisticsDTO> getMessageStatistics(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());

        return messageService.getMessageStatistics(userNum);
    }

    @ApiOperation(value = "站内信息列表", notes = "根据用户编号获取站内未删除的信息列表 [1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMessageList", method = RequestMethod.POST)
    public Result<Page<MessageDTO>> getMessageList(@ModelAttribute @ApiParam MessageParam pageParam, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        Result<Page<MessageDTO>> messageDTOPage = messageService.getMessageList(userNum, pageParam);
        return messageDTOPage;
    }

    @ApiOperation(value = "站内信息操作", notes = "站内信息操作（已读未读） [1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
   // @Authorization
    @RequestMapping(value = "updateMessageStatus/{messageId}", method = RequestMethod.PUT)
    public Result updateMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("statusEnum") MessageStatusEnum statusEnum) {
        messageService.updateMessageStatus(messageId, statusEnum);
        return successCreated();
    }
}