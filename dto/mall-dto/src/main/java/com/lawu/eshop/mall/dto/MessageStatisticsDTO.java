package com.lawu.eshop.mall.dto;

/**
 * 站内信息统计信息
 * Created by zhangyong on 2017/3/29.
 */
public class MessageStatisticsDTO {
    /**
     * 未读消息总数
     */
    private Integer noReadCount;

    /**
     * 消息内容
     */
    private String content;

    public Integer getNoReadCount() {
        return noReadCount;
    }

    public void setNoReadCount(Integer noReadCount) {
        this.noReadCount = noReadCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
