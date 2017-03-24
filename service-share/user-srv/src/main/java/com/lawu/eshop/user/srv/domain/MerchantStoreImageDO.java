package com.lawu.eshop.user.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class MerchantStoreImageDO implements Serializable {
    /**
     *
     * 主键
     * merchant_store_image.id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private Long id;

    /**
     *
     * 商家
     * merchant_store_image.merchant_id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private Long merchantId;

    /**
     *
     * 门店
     * merchant_store_image.merchant_store_id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private Long merchantStoreId;

    /**
     *
     * 图片类别
     * merchant_store_image.type
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private Byte type;

    /**
     *
     * 图片路径
     * merchant_store_image.path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private String path;

    /**
     *
     * 修改时间
     * merchant_store_image.gmt_modified
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * merchant_store_image.gmt_create
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_store_image
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.id
     *
     * @return the value of merchant_store_image.id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.id
     *
     * @param id the value for merchant_store_image.id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.merchant_id
     *
     * @return the value of merchant_store_image.merchant_id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.merchant_id
     *
     * @param merchantId the value for merchant_store_image.merchant_id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.merchant_store_id
     *
     * @return the value of merchant_store_image.merchant_store_id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.merchant_store_id
     *
     * @param merchantStoreId the value for merchant_store_image.merchant_store_id
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.type
     *
     * @return the value of merchant_store_image.type
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.type
     *
     * @param type the value for merchant_store_image.type
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.path
     *
     * @return the value of merchant_store_image.path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.path
     *
     * @param path the value for merchant_store_image.path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.gmt_modified
     *
     * @return the value of merchant_store_image.gmt_modified
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.gmt_modified
     *
     * @param gmtModified the value for merchant_store_image.gmt_modified
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_image.gmt_create
     *
     * @return the value of merchant_store_image.gmt_create
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_image.gmt_create
     *
     * @param gmtCreate the value for merchant_store_image.gmt_create
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}