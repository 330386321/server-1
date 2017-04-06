package com.lawu.eshop.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public class CommentMerchantDTO {
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headImg;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "回复评价")
    private  String replyContent;

    /**
     * 评价时间
     */
    @ApiModelProperty(value = "评价时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 评价图片
     */
    @ApiModelProperty(value = "评价图片")
    private List imgUrls;

    /**
     * 是否匿名
     */
    @ApiModelProperty(value = "是否匿名")
    private Boolean isAnonymous;

    @ApiModelProperty(value = "综合评分")
    private float grade;

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

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
