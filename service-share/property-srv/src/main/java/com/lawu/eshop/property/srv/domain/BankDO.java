package com.lawu.eshop.property.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class BankDO implements Serializable {
    /**
     *
     * 主键
     * bank.id
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private Byte id;

    /**
     *
     * 银行名称
     * bank.name
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private String name;

    /**
     *
     * 1为启用,0为停用
     * bank.status
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private Byte status;

    /**
     *
     * 序号
     * bank.ordinal
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private Integer ordinal;

    /**
     *
     * 备注
     * bank.remark
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private String remark;

    /**
     *
     * 修改时间
     * bank.gmt_modified
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * bank.gmt_create
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bank
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.id
     *
     * @return the value of bank.id
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public Byte getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.id
     *
     * @param id the value for bank.id
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setId(Byte id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.name
     *
     * @return the value of bank.name
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.name
     *
     * @param name the value for bank.name
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.status
     *
     * @return the value of bank.status
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.status
     *
     * @param status the value for bank.status
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.ordinal
     *
     * @return the value of bank.ordinal
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public Integer getOrdinal() {
        return ordinal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.ordinal
     *
     * @param ordinal the value for bank.ordinal
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.remark
     *
     * @return the value of bank.remark
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.remark
     *
     * @param remark the value for bank.remark
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.gmt_modified
     *
     * @return the value of bank.gmt_modified
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.gmt_modified
     *
     * @param gmtModified the value for bank.gmt_modified
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank.gmt_create
     *
     * @return the value of bank.gmt_create
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank.gmt_create
     *
     * @param gmtCreate the value for bank.gmt_create
     *
     * @mbg.generated 2017-03-27 12:39:45
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}