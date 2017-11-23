package com.lawu.eshop.user.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberDO implements Serializable {
    /**
     *
     * 主键
     * member.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 会员编号
     * member.num
     *
     * @mbg.generated
     */
    private String num;

    /**
     *
     * 登录账号
     * member.account
     *
     * @mbg.generated
     */
    private String account;

    /**
     *
     * 登录密码
     * member.pwd
     *
     * @mbg.generated
     */
    private String pwd;

    /**
     *
     * 手机号码
     * member.mobile
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     *
     * 姓名
     * member.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * 昵称
     * member.nickname
     *
     * @mbg.generated
     */
    private String nickname;

    /**
     *
     * 地区路径
     * member.region_path
     *
     * @mbg.generated
     */
    private String regionPath;

    /**
     *
     * 区域名称
     * member.region_name
     *
     * @mbg.generated
     */
    private String regionName;

    /**
     *
     * 性别 (0--男，2--女，1--保密)
     * member.sex
     *
     * @mbg.generated
     */
    private Byte sex;

    /**
     *
     * 出生年月
     * member.birthday
     *
     * @mbg.generated
     */
    private Date birthday;

    /**
     *
     * 头像
     * member.headimg
     *
     * @mbg.generated
     */
    private String headimg;

    /**
     *
     * 状态 (0--无效，1--有效)
     * member.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 是否冻结（0：未冻结 1：冻结）
     * member.is_freeze
     *
     * @mbg.generated
     */
    private Boolean isFreeze;

    /**
     *
     * 冻结原因
     * member.freeze_reason
     *
     * @mbg.generated
     */
    private String freezeReason;

    /**
     *
     * 邀请者ID
     * member.inviter_id
     *
     * @mbg.generated
     */
    private Long inviterId;

    /**
     *
     * 邀请者类型 (0--无推荐，1--会员，2--商户)
     * member.inviter_type
     *
     * @mbg.generated
     */
    private Byte inviterType;

    /**
     *
     * 等级
     * member.level
     *
     * @mbg.generated
     */
    private Integer level;

    /**
     *
     * 个推CID
     * member.gt_cid
     *
     * @mbg.generated
     */
    private String gtCid;

    /**
     *
     * 融云token
     * member.ry_token
     *
     * @mbg.generated
     */
    private String ryToken;

    /**
     *
     * 会员等级（一个数字对应一个级别，从低到高）
     * member.grade
     *
     * @mbg.generated
     */
    private Boolean grade;

    /**
     *
     * 等级成长值
     * member.growth_value
     *
     * @mbg.generated
     */
    private Integer growthValue;

    /**
     *
     * 修改时间
     * member.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * member.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table member
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.id
     *
     * @return the value of member.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.id
     *
     * @param id the value for member.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.num
     *
     * @return the value of member.num
     *
     * @mbg.generated
     */
    public String getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.num
     *
     * @param num the value for member.num
     *
     * @mbg.generated
     */
    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.account
     *
     * @return the value of member.account
     *
     * @mbg.generated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.account
     *
     * @param account the value for member.account
     *
     * @mbg.generated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.pwd
     *
     * @return the value of member.pwd
     *
     * @mbg.generated
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.pwd
     *
     * @param pwd the value for member.pwd
     *
     * @mbg.generated
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.mobile
     *
     * @return the value of member.mobile
     *
     * @mbg.generated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.mobile
     *
     * @param mobile the value for member.mobile
     *
     * @mbg.generated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.name
     *
     * @return the value of member.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.name
     *
     * @param name the value for member.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.nickname
     *
     * @return the value of member.nickname
     *
     * @mbg.generated
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.nickname
     *
     * @param nickname the value for member.nickname
     *
     * @mbg.generated
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.region_path
     *
     * @return the value of member.region_path
     *
     * @mbg.generated
     */
    public String getRegionPath() {
        return regionPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.region_path
     *
     * @param regionPath the value for member.region_path
     *
     * @mbg.generated
     */
    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath == null ? null : regionPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.region_name
     *
     * @return the value of member.region_name
     *
     * @mbg.generated
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.region_name
     *
     * @param regionName the value for member.region_name
     *
     * @mbg.generated
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.sex
     *
     * @return the value of member.sex
     *
     * @mbg.generated
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.sex
     *
     * @param sex the value for member.sex
     *
     * @mbg.generated
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.birthday
     *
     * @return the value of member.birthday
     *
     * @mbg.generated
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.birthday
     *
     * @param birthday the value for member.birthday
     *
     * @mbg.generated
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.headimg
     *
     * @return the value of member.headimg
     *
     * @mbg.generated
     */
    public String getHeadimg() {
        return headimg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.headimg
     *
     * @param headimg the value for member.headimg
     *
     * @mbg.generated
     */
    public void setHeadimg(String headimg) {
        this.headimg = headimg == null ? null : headimg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.status
     *
     * @return the value of member.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.status
     *
     * @param status the value for member.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.is_freeze
     *
     * @return the value of member.is_freeze
     *
     * @mbg.generated
     */
    public Boolean getIsFreeze() {
        return isFreeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.is_freeze
     *
     * @param isFreeze the value for member.is_freeze
     *
     * @mbg.generated
     */
    public void setIsFreeze(Boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.freeze_reason
     *
     * @return the value of member.freeze_reason
     *
     * @mbg.generated
     */
    public String getFreezeReason() {
        return freezeReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.freeze_reason
     *
     * @param freezeReason the value for member.freeze_reason
     *
     * @mbg.generated
     */
    public void setFreezeReason(String freezeReason) {
        this.freezeReason = freezeReason == null ? null : freezeReason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.inviter_id
     *
     * @return the value of member.inviter_id
     *
     * @mbg.generated
     */
    public Long getInviterId() {
        return inviterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.inviter_id
     *
     * @param inviterId the value for member.inviter_id
     *
     * @mbg.generated
     */
    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.inviter_type
     *
     * @return the value of member.inviter_type
     *
     * @mbg.generated
     */
    public Byte getInviterType() {
        return inviterType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.inviter_type
     *
     * @param inviterType the value for member.inviter_type
     *
     * @mbg.generated
     */
    public void setInviterType(Byte inviterType) {
        this.inviterType = inviterType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.level
     *
     * @return the value of member.level
     *
     * @mbg.generated
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.level
     *
     * @param level the value for member.level
     *
     * @mbg.generated
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.gt_cid
     *
     * @return the value of member.gt_cid
     *
     * @mbg.generated
     */
    public String getGtCid() {
        return gtCid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.gt_cid
     *
     * @param gtCid the value for member.gt_cid
     *
     * @mbg.generated
     */
    public void setGtCid(String gtCid) {
        this.gtCid = gtCid == null ? null : gtCid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.ry_token
     *
     * @return the value of member.ry_token
     *
     * @mbg.generated
     */
    public String getRyToken() {
        return ryToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.ry_token
     *
     * @param ryToken the value for member.ry_token
     *
     * @mbg.generated
     */
    public void setRyToken(String ryToken) {
        this.ryToken = ryToken == null ? null : ryToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.grade
     *
     * @return the value of member.grade
     *
     * @mbg.generated
     */
    public Boolean getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.grade
     *
     * @param grade the value for member.grade
     *
     * @mbg.generated
     */
    public void setGrade(Boolean grade) {
        this.grade = grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.growth_value
     *
     * @return the value of member.growth_value
     *
     * @mbg.generated
     */
    public Integer getGrowthValue() {
        return growthValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.growth_value
     *
     * @param growthValue the value for member.growth_value
     *
     * @mbg.generated
     */
    public void setGrowthValue(Integer growthValue) {
        this.growthValue = growthValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.gmt_modified
     *
     * @return the value of member.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.gmt_modified
     *
     * @param gmtModified the value for member.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.gmt_create
     *
     * @return the value of member.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.gmt_create
     *
     * @param gmtCreate the value for member.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}