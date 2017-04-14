package com.lawu.eshop.mall.param;

/**
 * @author zhangyong
 * @date 2017/4/14.
 */
public class MessagePushInfo {

    private Long messageId;

    private String title;

    private String userNum;

    private String content;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
