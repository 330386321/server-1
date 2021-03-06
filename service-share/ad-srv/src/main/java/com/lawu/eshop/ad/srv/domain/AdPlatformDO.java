package com.lawu.eshop.ad.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class AdPlatformDO implements Serializable {
    /**
     *
     * 主键
     * ad_platform.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 商品ID
     * ad_platform.product_id
     *
     * @mbg.generated
     */
    private Long productId;

    /**
     *
     * 广告id
     * ad_platform.ad_id
     *
     * @mbg.generated
     */
    private Long adId;

    /**
     *
     * 名称
     * ad_platform.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * 广附件路径
     * ad_platform.media_url
     *
     * @mbg.generated
     */
    private String mediaUrl;

    /**
     *
     * 链接地址
     * ad_platform.link_url
     *
     * @mbg.generated
     */
    private String linkUrl;

    /**
     *
     * 门店id
     * ad_platform.merchant_store_id
     *
     * @mbg.generated
     */
    private Long merchantStoreId;

    /**
     *
     * 广告类型(1-纯链接2-商品 3-门店)
     * ad_platform.type
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     *
     * 广告位置(1-人气推荐2-要购物顶部广告 3-要购物今日推荐4-要购物精品5-看广告顶部广告 6-E店必够 7-特色好货 8-实惠单品  9-热门商品)
     * ad_platform.position
     *
     * @mbg.generated
     */
    private Byte position;

    /**
     *
     * 地区
     * ad_platform.region_path
     *
     * @mbg.generated
     */
    private String regionPath;

    /**
     *
     * 地区名称
     * ad_platform.region_name
     *
     * @mbg.generated
     */
    private String regionName;

    /**
     *
     * 状态(0-删除1-上架2-下架)
     * ad_platform.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 广告内容
     * ad_platform.content
     *
     * @mbg.generated
     */
    private String content;

    /**
     *
     * 修改时间
     * ad_platform.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * ad_platform.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ad_platform
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.id
     *
     * @return the value of ad_platform.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.id
     *
     * @param id the value for ad_platform.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.product_id
     *
     * @return the value of ad_platform.product_id
     *
     * @mbg.generated
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.product_id
     *
     * @param productId the value for ad_platform.product_id
     *
     * @mbg.generated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.ad_id
     *
     * @return the value of ad_platform.ad_id
     *
     * @mbg.generated
     */
    public Long getAdId() {
        return adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.ad_id
     *
     * @param adId the value for ad_platform.ad_id
     *
     * @mbg.generated
     */
    public void setAdId(Long adId) {
        this.adId = adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.title
     *
     * @return the value of ad_platform.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.title
     *
     * @param title the value for ad_platform.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.media_url
     *
     * @return the value of ad_platform.media_url
     *
     * @mbg.generated
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.media_url
     *
     * @param mediaUrl the value for ad_platform.media_url
     *
     * @mbg.generated
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl == null ? null : mediaUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.link_url
     *
     * @return the value of ad_platform.link_url
     *
     * @mbg.generated
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.link_url
     *
     * @param linkUrl the value for ad_platform.link_url
     *
     * @mbg.generated
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.merchant_store_id
     *
     * @return the value of ad_platform.merchant_store_id
     *
     * @mbg.generated
     */
    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.merchant_store_id
     *
     * @param merchantStoreId the value for ad_platform.merchant_store_id
     *
     * @mbg.generated
     */
    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.type
     *
     * @return the value of ad_platform.type
     *
     * @mbg.generated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.type
     *
     * @param type the value for ad_platform.type
     *
     * @mbg.generated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.position
     *
     * @return the value of ad_platform.position
     *
     * @mbg.generated
     */
    public Byte getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.position
     *
     * @param position the value for ad_platform.position
     *
     * @mbg.generated
     */
    public void setPosition(Byte position) {
        this.position = position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.region_path
     *
     * @return the value of ad_platform.region_path
     *
     * @mbg.generated
     */
    public String getRegionPath() {
        return regionPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.region_path
     *
     * @param regionPath the value for ad_platform.region_path
     *
     * @mbg.generated
     */
    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath == null ? null : regionPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.region_name
     *
     * @return the value of ad_platform.region_name
     *
     * @mbg.generated
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.region_name
     *
     * @param regionName the value for ad_platform.region_name
     *
     * @mbg.generated
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.status
     *
     * @return the value of ad_platform.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.status
     *
     * @param status the value for ad_platform.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.content
     *
     * @return the value of ad_platform.content
     *
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.content
     *
     * @param content the value for ad_platform.content
     *
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.gmt_modified
     *
     * @return the value of ad_platform.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.gmt_modified
     *
     * @param gmtModified the value for ad_platform.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad_platform.gmt_create
     *
     * @return the value of ad_platform.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad_platform.gmt_create
     *
     * @param gmtCreate the value for ad_platform.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}