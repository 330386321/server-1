package com.lawu.eshop.mall.dto;

import io.swagger.annotations.ApiParam;

/**
 * 站内信息统计信息
 * Created by zhangyong on 2017/3/29.
 */
public class MessageStatisticsDTO {
    /**
     * 未读消息总数
     */
    @ApiParam(name = "noReadCount",value = "未读消息总数")
    private Integer noReadCount;

    /**
     * 消息内容
     */
    @ApiParam(name = "content",value = "消息内容")
    private String content;

    /**
     * 消息类型
     */
    @ApiParam(name = "type",value = "消息类型")
    private Byte type;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
