package com.lawu.eshop.ad.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TakePlatformRedPacketDO implements Serializable {
    /**
     *
     * 主键
     * take_platform_red_packet.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 会员编号
     * take_platform_red_packet.user_num
     *
     * @mbg.generated
     */
    private String userNum;

    /**
     *
     * 红包ID
     * take_platform_red_packet.red_packet_id
     *
     * @mbg.generated
     */
    private Long redPacketId;

    /**
     *
     * 领取金额
     * take_platform_red_packet.point
     *
     * @mbg.generated
     */
    private BigDecimal point;

    /**
     *
     * 创建时间
     * take_platform_red_packet.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table take_platform_red_packet
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column take_platform_red_packet.id
     *
     * @return the value of take_platform_red_packet.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column take_platform_red_packet.id
     *
     * @param id the value for take_platform_red_packet.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column take_platform_red_packet.user_num
     *
     * @return the value of take_platform_red_packet.user_num
     *
     * @mbg.generated
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column take_platform_red_packet.user_num
     *
     * @param userNum the value for take_platform_red_packet.user_num
     *
     * @mbg.generated
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column take_platform_red_packet.red_packet_id
     *
     * @return the value of take_platform_red_packet.red_packet_id
     *
     * @mbg.generated
     */
    public Long getRedPacketId() {
        return redPacketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column take_platform_red_packet.red_packet_id
     *
     * @param redPacketId the value for take_platform_red_packet.red_packet_id
     *
     * @mbg.generated
     */
    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column take_platform_red_packet.point
     *
     * @return the value of take_platform_red_packet.point
     *
     * @mbg.generated
     */
    public BigDecimal getPoint() {
        return point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column take_platform_red_packet.point
     *
     * @param point the value for take_platform_red_packet.point
     *
     * @mbg.generated
     */
    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column take_platform_red_packet.gmt_create
     *
     * @return the value of take_platform_red_packet.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column take_platform_red_packet.gmt_create
     *
     * @param gmtCreate the value for take_platform_red_packet.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}