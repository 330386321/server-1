package com.lawu.eshop.mall.srv.bo;

/**
 * 站内信息统计BO
 * Created by zhangyong on 2017/3/29.
 */
public class MessageStatisticsBO {

    /**
     * 信息内容
     */
    private  String content;

    private  Byte type;

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
