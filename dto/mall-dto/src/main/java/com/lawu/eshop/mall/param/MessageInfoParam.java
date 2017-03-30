package com.lawu.eshop.mall.param;

import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import io.swagger.annotations.ApiParam;

/**
 * 新建站内信息记录实体类
 * Created by Administrator on 2017/3/30.
 */
public class MessageInfoParam {

    /**
     * 关联ID
     */
    @ApiParam(name = "relateId", value = "关联ID")
    private Long relateId;

    /**
     * 消息类型
     */
    @ApiParam(name = "typeEnum", value = "消息类型 MESSAGE_TYPE_REFUND:退款，MESSAGE_TYPE_APPRAISE：评价，MESSAGE_TYPEUS_NOTICE：通知")
    private MessageTypeEnum typeEnum;

    /**
     * 消息内容
     */
    @ApiParam(name = "content", value = "消息内容")
    private String content;

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public MessageTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(MessageTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
