package com.lawu.eshop.mall.srv.bo;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
public class CommentProductBO {
    /**
     * 评价ID
     */
    private Long id;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 商家回复内容
     */
    private String replyContent;

    /**
     * 是否匿名
     */
    private boolean isAnonymous;

    /**
     * 评价时间
     */
    private Date gmtCreate;

    private List urlImgs;

    /**
     * 用户
     */
    private Long memberId;

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

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public List getUrlImgs() {
        return urlImgs;
    }

    public void setUrlImgs(List urlImgs) {
        this.urlImgs = urlImgs;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
