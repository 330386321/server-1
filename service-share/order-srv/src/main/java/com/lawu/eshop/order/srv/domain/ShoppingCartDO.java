package com.lawu.eshop.order.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShoppingCartDO implements Serializable {
    /**
     *
     * 主键
     * shopping_cart.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 用户
     * shopping_cart.member_id
     *
     * @mbg.generated
     */
    private Long memberId;

    /**
     *
     * 商家
     * shopping_cart.merchant_id
     *
     * @mbg.generated
     */
    private Long merchantId;

    /**
     *
     * 商家门店id
     * shopping_cart.merchant_store_id
     *
     * @mbg.generated
     */
    private Long merchantStoreId;

    /**
     *
     * 商家名称
     * shopping_cart.merchant_name
     *
     * @mbg.generated
     */
    private String merchantName;

    /**
     *
     * 商品ID
     * shopping_cart.product_id
     *
     * @mbg.generated
     */
    private Long productId;

    /**
     *
     * 商品型号ID
     * shopping_cart.product_model_id
     *
     * @mbg.generated
     */
    private Long productModelId;

    /**
     *
     * 数量
     * shopping_cart.quantity
     *
     * @mbg.generated
     */
    private Integer quantity;

    /**
     *
     * 现价
     * shopping_cart.sales_price
     *
     * @mbg.generated
     */
    private BigDecimal salesPrice;

    /**
     *
     * 修改时间
     * shopping_cart.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * shopping_cart.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.id
     *
     * @return the value of shopping_cart.id
     *
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.merchant_store_id
     *
     * @return the value of shopping_cart.merchant_store_id
     *
     * @mbg.generated
     */
    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.merchant_store_id
     *
     * @param merchantStoreId the value for shopping_cart.merchant_store_id
     *
     * @mbg.generated
     */
    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.merchant_name
     *
     * @return the value of shopping_cart.merchant_name
     *
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_model_id
     *
     * @return the value of shopping_cart.product_model_id
     *
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setProductModelId(Long productModelId) {
        this.productModelId = productModelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.quantity
     *
     * @return the value of shopping_cart.quantity
     *
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.sales_price
     *
     * @return the value of shopping_cart.sales_price
     *
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}