package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.page.PageParam;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;

/**
 * 站内消息接口
 * Created by Administrator on 2017/3/29.
 */
public interface MessageService {
    /**
     * 查询未读信息总条数
     * @param userNum
     * @return
     */
    int selectNoReadCount(String userNum);

    /**
     * 查询最后一条未读信息
     * @param userNum
     */
    MessageStatisticsBO selectLastMessage(String userNum);

    /**
     * 查询站内信息列表
     * @param userNum
     * @param pageParam
     * @return
     */
    Page<MessageBO> getMessageList(String userNum, MessageParam pageParam);

    /**
     * 站内信息操作（已读，删除）
     * @param messageId
     * @param statusEnum
     */
    void updateMessageStatus(Long messageId, MessageStatusEnum statusEnum);
}
