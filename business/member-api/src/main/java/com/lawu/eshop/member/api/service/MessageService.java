package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 站内信息接口
 * Created by zhangyong on 2017/3/29.
 */
@FeignClient(value = "mall-srv")
public interface MessageService {

    /**
     * 站内信息统计
     *
     * @param userNum
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "message/getMessageStatistics/{userNum}")
    Result<MessageStatisticsDTO> getMessageStatistics(@PathVariable("userNum") String userNum);

    /**
     * 站内信息列表
     *
     * @param userNum   用户编号
     * @param pageParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "message/getMessageList/{userNum}")
    Result<Page<MessageDTO>> getMessageList(@PathVariable("userNum") String userNum, @ModelAttribute MessageParam pageParam);

    /**
     * 站内信息操作（已读）
     * @param messageId
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.PUT, value = "message/updateMessageStatus/{messageId}")
    Result updateMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("userNum") String userNum);

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "message/delMessageStatus/{messageId}", method = RequestMethod.DELETE)
    Result delMessageStatus(@PathVariable("messageId") Long messageId, @RequestParam("userNum") String userNum);
    
    /**
     * 插入站内消息
     *
     * @param userNum          用户编号
     * @param messageInfoParam 消息参数
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "message/saveMessage/{userNum}")
    Result saveMessage(@PathVariable("userNum") String userNum, @ModelAttribute MessageInfoParam messageInfoParam);

    /**
     * 批量删除消息
     * @param ids
     * @return
     */
    @RequestMapping(value = "message/delMessageByIds",method = RequestMethod.DELETE)
    Result delMessageByIds(@RequestParam("ids") String ids, @RequestParam("userNum") String userNum);
    
    /**
     * 推送消息
     * @param userNum
     * @param typeEnum
     * @return
     */
    @RequestMapping(value = "message/pushMessageBySetCid", method = RequestMethod.POST)
	Result pushMessageBySetCid(@RequestParam("userNum") String userNum,@ModelAttribute MessageTypeEnum typeEnum);
}
