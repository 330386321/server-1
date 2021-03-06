package com.lawu.eshop.mall.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class TransactionRecordDO implements Serializable {
    /**
     *
     * 主键
     * transaction_record.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 关联ID
     * transaction_record.relate_id
     *
     * @mbg.generated
     */
    private Long relateId;

    /**
     *
     * 事务类型
     * transaction_record.type
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     *
     * 已处理，0否，1是
     * transaction_record.is_processed
     *
     * @mbg.generated
     */
    private Boolean isProcessed;

    /**
     *
     * 执行次数
     * transaction_record.times
     *
     * @mbg.generated
     */
    private Long times;

    /**
     *
     * 修改时间
     * transaction_record.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * transaction_record.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.id
     *
     * @return the value of transaction_record.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.id
     *
     * @param id the value for transaction_record.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.relate_id
     *
     * @return the value of transaction_record.relate_id
     *
     * @mbg.generated
     */
    public Long getRelateId() {
        return relateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.relate_id
     *
     * @param relateId the value for transaction_record.relate_id
     *
     * @mbg.generated
     */
    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.type
     *
     * @return the value of transaction_record.type
     *
     * @mbg.generated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.type
     *
     * @param type the value for transaction_record.type
     *
     * @mbg.generated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.is_processed
     *
     * @return the value of transaction_record.is_processed
     *
     * @mbg.generated
     */
    public Boolean getIsProcessed() {
        return isProcessed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.is_processed
     *
     * @param isProcessed the value for transaction_record.is_processed
     *
     * @mbg.generated
     */
    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.times
     *
     * @return the value of transaction_record.times
     *
     * @mbg.generated
     */
    public Long getTimes() {
        return times;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.times
     *
     * @param times the value for transaction_record.times
     *
     * @mbg.generated
     */
    public void setTimes(Long times) {
        this.times = times;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.gmt_modified
     *
     * @return the value of transaction_record.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.gmt_modified
     *
     * @param gmtModified the value for transaction_record.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transaction_record.gmt_create
     *
     * @return the value of transaction_record.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transaction_record.gmt_create
     *
     * @param gmtCreate the value for transaction_record.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}