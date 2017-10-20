package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import com.lawu.eshop.mall.dto.OperatorMessageDTO;
import com.lawu.eshop.mall.param.*;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.converter.MessageConverter;
import com.lawu.eshop.mall.srv.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 站内信息controller
 * Created by zhangyong on 2017/3/29.
 */
@RestController
@RequestMapping(value = "message/")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * 站内信息统计
     *
     * @param userNum
     * @return
     */
    @RequestMapping(value = "getMessageStatistics/{userNum}", method = RequestMethod.GET)
    public Result<MessageStatisticsDTO> getMessageStatistics(@PathVariable("userNum") String userNum) {
        int counts = messageService.selectNoReadCount(userNum);
        MessageStatisticsDTO messageStatisticsDTO = new MessageStatisticsDTO();
        messageStatisticsDTO.setNoReadCount(counts);
        if (counts > 0) {
            MessageStatisticsBO messageStatisticsBO = messageService.selectLastMessage(userNum);
            messageStatisticsDTO.setContent(messageStatisticsBO.getContent());
            messageStatisticsDTO.setType(MessageTypeEnum.getEnum(messageStatisticsBO.getType()));
            messageStatisticsDTO.setGmtCreate(messageStatisticsBO.getGmtCreate());
            return successGet(messageStatisticsDTO);
        }
        return successGet(messageStatisticsDTO);
    }

    /**
     * 站内信息列表
     *
     * @param userNum
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "getMessageList/{userNum}", method = RequestMethod.POST)
    public Result<Page<MessageDTO>> getMessageList(@PathVariable("userNum") String userNum, @RequestBody MessageParam pageParam) {

        Page<MessageBO> messageDTOPage = messageService.getMessageList(userNum, pageParam);
        if(messageDTOPage == null){
            return successGet(new Page<>());
        }
        List<MessageBO> messageBOS = messageDTOPage.getRecords();
        //BO转DTO
        List<MessageDTO> messageDTOS = MessageConverter.coverDTOS(messageBOS);
        Page<MessageDTO> pages = new Page<>();
        pages.setRecords(messageDTOS);
        pages.setCurrentPage(pageParam.getCurrentPage());
        pages.setTotalCount(messageDTOPage.getTotalCount());
        return successGet(pages);

    }

    /**
     * 站内信息操作（已读）
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "updateMessageStatus/{messageId}", method = RequestMethod.PUT)
    public Result updateMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("userNum") String userNum) {
        messageService.updateMessageStatus(messageId, MessageStatusEnum.MESSAGE_STATUS_READ, userNum);
        return successCreated();
    }
    /**
     * 站内信息操作（删除）
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "delMessageStatus/{messageId}", method = RequestMethod.DELETE)
    public Result delMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("userNum") String userNum) {
        messageService.updateMessageStatus(messageId, MessageStatusEnum.MESSAGE_STATUS_DELETE, userNum);
        return successCreated();
    }

    /**
     * 新增站内信息
     *
     * @param userNum
     * @param messageInfoParam
     * @return
     */
    @RequestMapping(value = "saveMessage/{userNum}", method = RequestMethod.POST)
    public Result saveMessage(@PathVariable("userNum") String userNum, @RequestBody MessageInfoParam messageInfoParam) {
        Integer id = messageService.saveMessage(userNum, messageInfoParam);
        if(id == null){
            return successCreated(ResultCode.SAVE_FAIL);
        }
        if( id == 0 || id<0){
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }


    /**
     * 运营平台新增站内信息-单个用户
     *
     * @param userNum
     * @param messageInfoParam
     * @return
     */
    @RequestMapping(value = "saveMessageOperator/{userNum}", method = RequestMethod.POST)
    public Result saveMessageOperator(@PathVariable("userNum") String userNum,@RequestBody OperatorMessageInfoParam messageInfoParam) {
        Integer id = messageService.saveMessageOperator(userNum,messageInfoParam);
        if(id == 0 || id<0){
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 运营平台给一类用户推送消息
     * @param param
     * @return
     */
    @RequestMapping(value = "saveMessageToAll", method = RequestMethod.POST)
    public Result saveMessageToAll(@RequestBody OperatorMessageParam param){
         messageService.saveMessageToAll(param);

        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 运营平台查询消息列表
     * @param param
     * @return
     * @autor zy
     */
    @RequestMapping(value = "getOperatorMessageList",method = RequestMethod.POST)
    public Result<Page<OperatorMessageDTO>> getOperatorMessageList(@RequestBody MessageQueryParam param){

        Page<MessageBO> messageBOPage = messageService.getOperatorMessageList(param);
        if(messageBOPage == null){
            return successGet(new Page<>());
        }
        List<OperatorMessageDTO> messageDTOS = MessageConverter.coverOperatorDTOS(messageBOPage.getRecords());
        Page<OperatorMessageDTO> page = new Page<>();
        page.setCurrentPage(messageBOPage.getCurrentPage());
        page.setTotalCount(messageBOPage.getTotalCount());
        page.setRecords(messageDTOS);
        return successGet(page);
    }

    @RequestMapping(value = "delMessageByIds", method = RequestMethod.DELETE)
    public Result delMessageByIds(@RequestParam("ids") String ids, @RequestParam("userNum") String userNum) {
        String[] idString = ids.split(",");
        Long[] lids = new Long[idString.length];
        for (int i = 0; i < idString.length; i++) {
            lids[i] = Long.valueOf(idString[i]);
            messageService.updateMessageStatus(lids[i], MessageStatusEnum.MESSAGE_STATUS_DELETE, userNum);
        }
        return successDelete();
    }
    
    @RequestMapping(value = "selectMessageById/{id}", method = RequestMethod.GET)
    public Result<MessageDTO> selectMessageById(@PathVariable("id") Long id) {
    	MessageBO messageBO=messageService.selectMessageId(id);
        return successGet(MessageConverter.coverDTO(messageBO));
    }
    
    /**
     * 设置cid推送消息
     * @param userNum
     * @param typeEnum
     * @return
     */
    @RequestMapping(value = "pushMessageBySetCid", method = RequestMethod.POST)
	public Result pushMessageBySetCid(@RequestParam String userNum,@RequestBody MessageTypeEnum typeEnum){
    	messageService.pushMessageBySetCid(userNum, typeEnum);
    	return successCreated();
    }

}
