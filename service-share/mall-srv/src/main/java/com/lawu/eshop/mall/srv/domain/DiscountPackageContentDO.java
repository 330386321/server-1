package com.lawu.eshop.mall.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DiscountPackageContentDO implements Serializable {
    /**
     *
     * 主键
     * discount_package_content.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 优惠套餐id
     * discount_package_content.discount_package_id
     *
     * @mbg.generated
     */
    private Long discountPackageId;

    /**
     *
     * 内容名称
     * discount_package_content.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * 单价
     * discount_package_content.unit_price
     *
     * @mbg.generated
     */
    private BigDecimal unitPrice;

    /**
     *
     * 数量
     * discount_package_content.quantity
     *
     * @mbg.generated
     */
    private Integer quantity;

    /**
     *
     * 单位
     * discount_package_content.unit
     *
     * @mbg.generated
     */
    private String unit;

    /**
     *
     * 小计
     * discount_package_content.subtotal
     *
     * @mbg.generated
     */
    private BigDecimal subtotal;

    /**
     *
     * 状态(0-删除|1-正常)
     * discount_package_content.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 创建时间
     * discount_package_content.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     *
     * 更新时间
     * discount_package_content.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table discount_package_content
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.id
     *
     * @return the value of discount_package_content.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.id
     *
     * @param id the value for discount_package_content.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.discount_package_id
     *
     * @return the value of discount_package_content.discount_package_id
     *
     * @mbg.generated
     */
    public Long getDiscountPackageId() {
        return discountPackageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.discount_package_id
     *
     * @param discountPackageId the value for discount_package_content.discount_package_id
     *
     * @mbg.generated
     */
    public void setDiscountPackageId(Long discountPackageId) {
        this.discountPackageId = discountPackageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.name
     *
     * @return the value of discount_package_content.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.name
     *
     * @param name the value for discount_package_content.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.unit_price
     *
     * @return the value of discount_package_content.unit_price
     *
     * @mbg.generated
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.unit_price
     *
     * @param unitPrice the value for discount_package_content.unit_price
     *
     * @mbg.generated
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.quantity
     *
     * @return the value of discount_package_content.quantity
     *
     * @mbg.generated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.quantity
     *
     * @param quantity the value for discount_package_content.quantity
     *
     * @mbg.generated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.unit
     *
     * @return the value of discount_package_content.unit
     *
     * @mbg.generated
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.unit
     *
     * @param unit the value for discount_package_content.unit
     *
     * @mbg.generated
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.subtotal
     *
     * @return the value of discount_package_content.subtotal
     *
     * @mbg.generated
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.subtotal
     *
     * @param subtotal the value for discount_package_content.subtotal
     *
     * @mbg.generated
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.status
     *
     * @return the value of discount_package_content.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.status
     *
     * @param status the value for discount_package_content.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.gmt_create
     *
     * @return the value of discount_package_content.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.gmt_create
     *
     * @param gmtCreate the value for discount_package_content.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_package_content.gmt_modified
     *
     * @return the value of discount_package_content.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_package_content.gmt_modified
     *
     * @param gmtModified the value for discount_package_content.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}