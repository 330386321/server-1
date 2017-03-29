package com.lawu.eshop.mall.srv.bo;

/**
 * 消息列表BO
 * Created by zhangyong on 2017/3/29.
 */
public class MessageBO {
    /**
     * 信息id
     */
    private Long id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private Byte type;

    /**
     * 消息状态
     */
    private Byte status;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
