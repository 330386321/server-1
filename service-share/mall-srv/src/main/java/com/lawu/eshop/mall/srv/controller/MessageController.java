package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import com.lawu.eshop.mall.dto.MessageTemplateDTO;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.mall.param.OperatorMessageInfoParam;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.bo.MessageTemplateBO;
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
            return successGet(messageStatisticsDTO);
        }
        return successGet();
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
     * @param statusEnum
     * @return
     */
    @RequestMapping(value = "updateMessageStatus/{messageId}", method = RequestMethod.PUT)
    public Result updateMessageStatus(@PathVariable("messageId") Long messageId) {
        messageService.updateMessageStatus(messageId, MessageStatusEnum.MESSAGE_STATUS_READ);
        return successCreated();
    }
    /**
     * 站内信息操作（删除）
     *
     * @param messageId
     * @param statusEnum
     * @return
     */
    @RequestMapping(value = "delMessageStatus/{messageId}", method = RequestMethod.DELETE)
    public Result delMessageStatus(@PathVariable("messageId") Long messageId) {
        messageService.updateMessageStatus(messageId, MessageStatusEnum.MESSAGE_STATUS_DELETE);
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
        if(id == 0 || id<0){
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 查询模板信心
     * @param type
     * @return
     */
    public Result<MessageTemplateDTO> getTemplateByType(@RequestParam("type") MessageTypeEnum type){

        MessageTemplateBO templateBO = messageService.getTemplateByType(type);
        if(templateBO == null){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        MessageTemplateDTO messageTemplateDTO = new MessageTemplateDTO();
        messageTemplateDTO.setTitle(templateBO.getTitle());
        messageTemplateDTO.setContent(templateBO.getContent());
        messageTemplateDTO.setId(templateBO.getId());

        return successGet(messageTemplateDTO);
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
     * @param messageInfoParam
     * @return
     */
    @RequestMapping(value = "saveMessageToAll", method = RequestMethod.POST)
    public Result saveMessageToAll(@RequestBody OperatorMessageInfoParam messageInfoParam){
        Long id = messageService.saveMessageToAll(messageInfoParam);
        if(id == 0 || id<0){
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

}
