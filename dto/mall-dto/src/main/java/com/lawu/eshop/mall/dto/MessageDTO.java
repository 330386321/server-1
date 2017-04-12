package com.lawu.eshop.mall.dto;

import com.lawu.eshop.mall.constants.MessageStatusReturnEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import io.swagger.annotations.ApiParam;

/**
 * 信息返回
 * Created by Administrator on 2017/3/29.
 */
public class MessageDTO {
    /**
     * 信息id
     */
    @ApiParam(name = "id",value = "信息id")
    private Long id;

    /**
     * 消息内容
     */
    @ApiParam(name = "content",value = "消息内容")
    private String content;

    /**
     * 消息类型
     */
    @ApiParam(name = "type",value = "消息类型")
    private MessageTypeEnum messageTypeEnum;

    /**
     * 消息状态
     */
    @ApiParam(name = "status",value = "消息状态")
    private MessageStatusReturnEnum statusEnum;

    /**
     * 消息状态
     */
    @ApiParam(name = "title",value = "标题")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageTypeEnum getMessageTypeEnum() {
        return messageTypeEnum;
    }

    public void setMessageTypeEnum(MessageTypeEnum messageTypeEnum) {
        this.messageTypeEnum = messageTypeEnum;
    }

    public MessageStatusReturnEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(MessageStatusReturnEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
