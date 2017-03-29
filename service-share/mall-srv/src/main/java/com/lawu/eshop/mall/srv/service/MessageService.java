package com.lawu.eshop.mall.srv.service;

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
}
