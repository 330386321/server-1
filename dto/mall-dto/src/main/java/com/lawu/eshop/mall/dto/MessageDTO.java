package com.lawu.eshop.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.mall.constants.MessageStatusReturnEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 信息返回
 * Created by Administrator on 2017/3/29.
 */
public class MessageDTO {
    /**
     * 信息id
     */
    @ApiModelProperty(name = "id",value = "信息id")
    private Long id;

    /**
     * 消息内容
     */
    @ApiModelProperty(name = "content",value = "消息内容")
    private String content;

    /**
     * 消息类型
     */
    @ApiModelProperty(name = "type",value = "消息类型")
    private MessageTypeEnum messageTypeEnum;

    /**
     * 消息状态
     */
    @ApiModelProperty(name = "status",value = "消息状态")
    private MessageStatusReturnEnum statusEnum;

    /**
     * 消息状态
     */
    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "gmtCreate",value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @ApiModelProperty(name = "relateId",value = "关联商家ID")
    private Long relateId;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }
}
