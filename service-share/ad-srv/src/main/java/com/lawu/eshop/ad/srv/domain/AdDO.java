package com.lawu.eshop.ad.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AdDO implements Serializable {
    /**
     *
     * 主键
     * ad.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 商家ID
     * ad.merchant_id
     *
     * @mbg.generated
     */
    private Long merchantId;

    /**
     *
     * 商家编号
     * ad.merchant_num
     *
     * @mbg.generated
     */
    private String merchantNum;

    /**
     *
     * 经度
     * ad.merchant_longitude
     *
     * @mbg.generated
     */
    private BigDecimal merchantLongitude;

    /**
     *
     * 纬度
     * ad.merchant_latitude
     *
     * @mbg.generated
     */
    private BigDecimal merchantLatitude;

    /**
     *
     * 名称
     * ad.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * 广附件路径
     * ad.media_url
     *
     * @mbg.generated
     */
    private String mediaUrl;

    /**
     *
     * 广告内容
     * ad.content
     *
     * @mbg.generated
     */
    private String content;

    /**
     *
     * 广告类型(1-平面广告2-视频广告)
     * ad.type
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     *
     * 投放方式(1-区域2-粉丝 3-雷达)
     * ad.put_way
     *
     * @mbg.generated
     */
    private Byte putWay;

    /**
     *
     * 开始时间
     * ad.begin_time
     *
     * @mbg.generated
     */
    private Date beginTime;

    /**
     *
     * 结束时间
     * ad.end_time
     *
     * @mbg.generated
     */
    private Date endTime;

    /**
     *
     * 投放区域
     * ad.areas
     *
     * @mbg.generated
     */
    private String areas;

    /**
     *
     * 半径，单位米
     * ad.radius
     *
     * @mbg.generated
     */
    private Integer radius;

    /**
     *
     * 单个积分
     * ad.point
     *
     * @mbg.generated
     */
    private BigDecimal point;

    /**
     *
     * 总投放积分
     * ad.total_point
     *
     * @mbg.generated
     */
    private BigDecimal totalPoint;

    /**
     *
     * 广告数量
     * ad.ad_count
     *
     * @mbg.generated
     */
    private Integer adCount;

    /**
     *
     * 已点击次数
     * ad.hits
     *
     * @mbg.generated
     */
    private Integer hits;

    /**
     *
     * 状态(0-删除1-上架2-投放中3-投放结束4-下架)
     * ad.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 修改时间
     * ad.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * ad.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ad
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.id
     *
     * @return the value of ad.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.id
     *
     * @param id the value for ad.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.merchant_id
     *
     * @return the value of ad.merchant_id
     *
     * @mbg.generated
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.merchant_id
     *
     * @param merchantId the value for ad.merchant_id
     *
     * @mbg.generated
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.merchant_num
     *
     * @return the value of ad.merchant_num
     *
     * @mbg.generated
     */
    public String getMerchantNum() {
        return merchantNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.merchant_num
     *
     * @param merchantNum the value for ad.merchant_num
     *
     * @mbg.generated
     */
    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum == null ? null : merchantNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.merchant_longitude
     *
     * @return the value of ad.merchant_longitude
     *
     * @mbg.generated
     */
    public BigDecimal getMerchantLongitude() {
        return merchantLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.merchant_longitude
     *
     * @param merchantLongitude the value for ad.merchant_longitude
     *
     * @mbg.generated
     */
    public void setMerchantLongitude(BigDecimal merchantLongitude) {
        this.merchantLongitude = merchantLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.merchant_latitude
     *
     * @return the value of ad.merchant_latitude
     *
     * @mbg.generated
     */
    public BigDecimal getMerchantLatitude() {
        return merchantLatitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.merchant_latitude
     *
     * @param merchantLatitude the value for ad.merchant_latitude
     *
     * @mbg.generated
     */
    public void setMerchantLatitude(BigDecimal merchantLatitude) {
        this.merchantLatitude = merchantLatitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.title
     *
     * @return the value of ad.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.title
     *
     * @param title the value for ad.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.media_url
     *
     * @return the value of ad.media_url
     *
     * @mbg.generated
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.media_url
     *
     * @param mediaUrl the value for ad.media_url
     *
     * @mbg.generated
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl == null ? null : mediaUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.content
     *
     * @return the value of ad.content
     *
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.content
     *
     * @param content the value for ad.content
     *
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.type
     *
     * @return the value of ad.type
     *
     * @mbg.generated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.type
     *
     * @param type the value for ad.type
     *
     * @mbg.generated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.put_way
     *
     * @return the value of ad.put_way
     *
     * @mbg.generated
     */
    public Byte getPutWay() {
        return putWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.put_way
     *
     * @param putWay the value for ad.put_way
     *
     * @mbg.generated
     */
    public void setPutWay(Byte putWay) {
        this.putWay = putWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.begin_time
     *
     * @return the value of ad.begin_time
     *
     * @mbg.generated
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.begin_time
     *
     * @param beginTime the value for ad.begin_time
     *
     * @mbg.generated
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.end_time
     *
     * @return the value of ad.end_time
     *
     * @mbg.generated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.end_time
     *
     * @param endTime the value for ad.end_time
     *
     * @mbg.generated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.areas
     *
     * @return the value of ad.areas
     *
     * @mbg.generated
     */
    public String getAreas() {
        return areas;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.areas
     *
     * @param areas the value for ad.areas
     *
     * @mbg.generated
     */
    public void setAreas(String areas) {
        this.areas = areas == null ? null : areas.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.radius
     *
     * @return the value of ad.radius
     *
     * @mbg.generated
     */
    public Integer getRadius() {
        return radius;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.radius
     *
     * @param radius the value for ad.radius
     *
     * @mbg.generated
     */
    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.point
     *
     * @return the value of ad.point
     *
     * @mbg.generated
     */
    public BigDecimal getPoint() {
        return point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.point
     *
     * @param point the value for ad.point
     *
     * @mbg.generated
     */
    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.total_point
     *
     * @return the value of ad.total_point
     *
     * @mbg.generated
     */
    public BigDecimal getTotalPoint() {
        return totalPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.total_point
     *
     * @param totalPoint the value for ad.total_point
     *
     * @mbg.generated
     */
    public void setTotalPoint(BigDecimal totalPoint) {
        this.totalPoint = totalPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.ad_count
     *
     * @return the value of ad.ad_count
     *
     * @mbg.generated
     */
    public Integer getAdCount() {
        return adCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.ad_count
     *
     * @param adCount the value for ad.ad_count
     *
     * @mbg.generated
     */
    public void setAdCount(Integer adCount) {
        this.adCount = adCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.hits
     *
     * @return the value of ad.hits
     *
     * @mbg.generated
     */
    public Integer getHits() {
        return hits;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.hits
     *
     * @param hits the value for ad.hits
     *
     * @mbg.generated
     */
    public void setHits(Integer hits) {
        this.hits = hits;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.status
     *
     * @return the value of ad.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.status
     *
     * @param status the value for ad.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.gmt_modified
     *
     * @return the value of ad.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.gmt_modified
     *
     * @param gmtModified the value for ad.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ad.gmt_create
     *
     * @return the value of ad.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ad.gmt_create
     *
     * @param gmtCreate the value for ad.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}