package com.lawu.eshop.mall.dto;

import java.util.Date;
import java.util.List;

/**
 * 用户评论返回
 * @author zhangyong
 * @date 2017/4/5.
 */
public class CommentProductDTO {

    /**
     * 头像
     */
    private String headImg;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价时间
     */
    private Date gmtCreate;

    /**
     * 评价图片
     */
    private List imgUrls;

    /**
     * 是否匿名
     */
    private Boolean isAnonymous;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public List getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }
}
