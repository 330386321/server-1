package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.member.api.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
class MockMessageService extends BaseController implements MessageService {

    @Override
    public Result<MessageStatisticsDTO> getMessageStatistics(@PathVariable("userNum") String userNum) {
        return null;
    }

    @Override
    public Result<Page<MessageDTO>> getMessageList(@PathVariable("userNum") String userNum, @ModelAttribute MessageParam pageParam) {
        return null;
    }

    @Override
    public Result updateMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("userNum") String userNum) {
        return null;
    }

    @Override
    public Result delMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("userNum") String userNum) {
        return null;
    }

    @Override
    public Result saveMessage(@PathVariable("userNum") String userNum, @ModelAttribute MessageInfoParam messageInfoParam) {
        return successCreated();
    }

    @Override
    public Result delMessageByIds(@RequestParam("ids") String ids, @RequestParam("userNum") String userNum) {
        return null;
    }
}
