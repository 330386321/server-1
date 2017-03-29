package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.page.PageParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param userNum
     * @return
     */
    @RequestMapping(value = "getMessageStatistics/{userNum}", method = RequestMethod.GET)
    public Result<MessageStatisticsDTO> getMessageStatistics(@PathVariable("userNum") String userNum) {
        int counts = messageService.selectNoReadCount(userNum);
        MessageStatisticsDTO messageStatisticsDTO = new MessageStatisticsDTO();
        messageStatisticsDTO.setNoReadCount(counts);
        if (counts > 0) {
            MessageStatisticsBO messageStatisticsBO =  messageService.selectLastMessage(userNum);
            messageStatisticsDTO.setContent(messageStatisticsBO.getContent());
            messageStatisticsDTO.setType(messageStatisticsBO.getType());
            return successGet(messageStatisticsDTO);
        }
        return successGet();
    }

    @RequestMapping(value = "getMessageList/{userNum}", method = RequestMethod.GET)
    public Result<Page<MessageDTO>> getMessageList(@PathVariable("userNum") String userNum, @RequestBody PageParam pageParam){

        return  successGet();

    }

}
