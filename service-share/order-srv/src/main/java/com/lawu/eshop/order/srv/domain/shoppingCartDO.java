package com.lawu.eshop.order.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class shoppingCartDO implements Serializable {
    /**
     *
     * 主键
     * shopping_cart.id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Long id;

    /**
     *
     * 用户
     * shopping_cart.member_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Long memberId;

    /**
     *
     * 商家
     * shopping_cart.merchant_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Long merchantId;

    /**
     *
     * 商家名称
     * shopping_cart.merchant_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private String merchantName;

    /**
     *
     * 商品ID
     * shopping_cart.product_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Long productId;

    /**
     *
     * 商品名称
     * shopping_cart.product_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private String productName;

    /**
     *
     * 商品型号ID
     * shopping_cart.product_model_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Long productModelId;

    /**
     *
     * 商品型号名称
     * shopping_cart.product_model_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private String productModelName;

    /**
     *
     * 数量
     * shopping_cart.quantity
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Integer quantity;

    /**
     *
     * 原价
     * shopping_cart.regular_price
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private BigDecimal regularPrice;

    /**
     *
     * 现价
     * shopping_cart.sales_price
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private BigDecimal salesPrice;

    /**
     *
     * 修改时间
     * shopping_cart.gmt_modified
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * shopping_cart.gmt_create
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table shopping_cart
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.id
     *
     * @return the value of shopping_cart.id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.id
     *
     * @param id the value for shopping_cart.id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.member_id
     *
     * @return the value of shopping_cart.member_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.member_id
     *
     * @param memberId the value for shopping_cart.member_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.merchant_id
     *
     * @return the value of shopping_cart.merchant_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.merchant_id
     *
     * @param merchantId the value for shopping_cart.merchant_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.merchant_name
     *
     * @return the value of shopping_cart.merchant_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.merchant_name
     *
     * @param merchantName the value for shopping_cart.merchant_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_id
     *
     * @return the value of shopping_cart.product_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.product_id
     *
     * @param productId the value for shopping_cart.product_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_name
     *
     * @return the value of shopping_cart.product_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.product_name
     *
     * @param productName the value for shopping_cart.product_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_model_id
     *
     * @return the value of shopping_cart.product_model_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Long getProductModelId() {
        return productModelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.product_model_id
     *
     * @param productModelId the value for shopping_cart.product_model_id
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setProductModelId(Long productModelId) {
        this.productModelId = productModelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_model_name
     *
     * @return the value of shopping_cart.product_model_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public String getProductModelName() {
        return productModelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.product_model_name
     *
     * @param productModelName the value for shopping_cart.product_model_name
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setProductModelName(String productModelName) {
        this.productModelName = productModelName == null ? null : productModelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.quantity
     *
     * @return the value of shopping_cart.quantity
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.quantity
     *
     * @param quantity the value for shopping_cart.quantity
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.regular_price
     *
     * @return the value of shopping_cart.regular_price
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.regular_price
     *
     * @param regularPrice the value for shopping_cart.regular_price
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.sales_price
     *
     * @return the value of shopping_cart.sales_price
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.sales_price
     *
     * @param salesPrice the value for shopping_cart.sales_price
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.gmt_modified
     *
     * @return the value of shopping_cart.gmt_modified
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.gmt_modified
     *
     * @param gmtModified the value for shopping_cart.gmt_modified
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.gmt_create
     *
     * @return the value of shopping_cart.gmt_create
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.gmt_create
     *
     * @param gmtCreate the value for shopping_cart.gmt_create
     *
     * @mbg.generated 2017-03-24 10:21:55
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}