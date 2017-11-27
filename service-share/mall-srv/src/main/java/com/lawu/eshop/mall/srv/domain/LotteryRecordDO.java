package com.lawu.eshop.mall.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class LotteryRecordDO implements Serializable {
    /**
     *
     * 
     * lottery_record.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 用户id
     * lottery_record.user_id
     *
     * @mbg.generated
     */
    private Long userId;

    /**
     *
     * 用户编号
     * lottery_record.user_num
     *
     * @mbg.generated
     */
    private String userNum;

    /**
     *
     * 账号
     * lottery_record.account
     *
     * @mbg.generated
     */
    private String account;

    /**
     *
     * 抽奖活动id
     * lottery_record.lottery_activity_id
     *
     * @mbg.generated
     */
    private Long lotteryActivityId;

    /**
     *
     * 奖品名称
     * lottery_record.prize_name
     *
     * @mbg.generated
     */
    private String prizeName;

    /**
     *
     * 抽奖次数
     * lottery_record.lottery_count
     *
     * @mbg.generated
     */
    private Integer lotteryCount;

    /**
     *
     * 抽奖结果(0--未中奖，1--中奖)
     * lottery_record.lottery_result
     *
     * @mbg.generated
     */
    private Boolean lotteryResult;

    /**
     *
     * 修改时间
     * lottery_record.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * lottery_record.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lottery_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.id
     *
     * @return the value of lottery_record.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.id
     *
     * @param id the value for lottery_record.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.user_id
     *
     * @return the value of lottery_record.user_id
     *
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.user_id
     *
     * @param userId the value for lottery_record.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.user_num
     *
     * @return the value of lottery_record.user_num
     *
     * @mbg.generated
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.user_num
     *
     * @param userNum the value for lottery_record.user_num
     *
     * @mbg.generated
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.account
     *
     * @return the value of lottery_record.account
     *
     * @mbg.generated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.account
     *
     * @param account the value for lottery_record.account
     *
     * @mbg.generated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.lottery_activity_id
     *
     * @return the value of lottery_record.lottery_activity_id
     *
     * @mbg.generated
     */
    public Long getLotteryActivityId() {
        return lotteryActivityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.lottery_activity_id
     *
     * @param lotteryActivityId the value for lottery_record.lottery_activity_id
     *
     * @mbg.generated
     */
    public void setLotteryActivityId(Long lotteryActivityId) {
        this.lotteryActivityId = lotteryActivityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.prize_name
     *
     * @return the value of lottery_record.prize_name
     *
     * @mbg.generated
     */
    public String getPrizeName() {
        return prizeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.prize_name
     *
     * @param prizeName the value for lottery_record.prize_name
     *
     * @mbg.generated
     */
    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.lottery_count
     *
     * @return the value of lottery_record.lottery_count
     *
     * @mbg.generated
     */
    public Integer getLotteryCount() {
        return lotteryCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.lottery_count
     *
     * @param lotteryCount the value for lottery_record.lottery_count
     *
     * @mbg.generated
     */
    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.lottery_result
     *
     * @return the value of lottery_record.lottery_result
     *
     * @mbg.generated
     */
    public Boolean getLotteryResult() {
        return lotteryResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.lottery_result
     *
     * @param lotteryResult the value for lottery_record.lottery_result
     *
     * @mbg.generated
     */
    public void setLotteryResult(Boolean lotteryResult) {
        this.lotteryResult = lotteryResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.gmt_modified
     *
     * @return the value of lottery_record.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.gmt_modified
     *
     * @param gmtModified the value for lottery_record.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_record.gmt_create
     *
     * @return the value of lottery_record.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_record.gmt_create
     *
     * @param gmtCreate the value for lottery_record.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}