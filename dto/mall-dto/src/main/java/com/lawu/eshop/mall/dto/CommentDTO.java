package com.lawu.eshop.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public class CommentDTO {
    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容")
    private String content;

    /**
     * 评价时间
     */
    @ApiModelProperty(value = "评价时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    /**
     * 评价用户
     */
    @ApiModelProperty(value = "评价用户")
    private Long memberId;
    /**
     * 评价ID
     */
    @ApiModelProperty(value = "评价ID")
    private Long id;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
