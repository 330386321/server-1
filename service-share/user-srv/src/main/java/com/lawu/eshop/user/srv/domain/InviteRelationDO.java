package com.lawu.eshop.user.srv.domain;

import java.io.Serializable;

public class InviteRelationDO implements Serializable {
    /**
     *
     * 
     * invite_relation.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 用户编号
     * invite_relation.user_num
     *
     * @mbg.generated
     */
    private String userNum;

    /**
     *
     * 被邀请用户编号
     * invite_relation.invite_user_num
     *
     * @mbg.generated
     */
    private String inviteUserNum;

    /**
     *
     * 深度
     * invite_relation.depth
     *
     * @mbg.generated
     */
    private Integer depth;

    /**
     *
     * 邀请类型
     * invite_relation.type
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table invite_relation
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invite_relation.id
     *
     * @return the value of invite_relation.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invite_relation.id
     *
     * @param id the value for invite_relation.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invite_relation.user_num
     *
     * @return the value of invite_relation.user_num
     *
     * @mbg.generated
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invite_relation.user_num
     *
     * @param userNum the value for invite_relation.user_num
     *
     * @mbg.generated
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invite_relation.invite_user_num
     *
     * @return the value of invite_relation.invite_user_num
     *
     * @mbg.generated
     */
    public String getInviteUserNum() {
        return inviteUserNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invite_relation.invite_user_num
     *
     * @param inviteUserNum the value for invite_relation.invite_user_num
     *
     * @mbg.generated
     */
    public void setInviteUserNum(String inviteUserNum) {
        this.inviteUserNum = inviteUserNum == null ? null : inviteUserNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invite_relation.depth
     *
     * @return the value of invite_relation.depth
     *
     * @mbg.generated
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invite_relation.depth
     *
     * @param depth the value for invite_relation.depth
     *
     * @mbg.generated
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invite_relation.type
     *
     * @return the value of invite_relation.type
     *
     * @mbg.generated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invite_relation.type
     *
     * @param type the value for invite_relation.type
     *
     * @mbg.generated
     */
    public void setType(Byte type) {
        this.type = type;
    }
}