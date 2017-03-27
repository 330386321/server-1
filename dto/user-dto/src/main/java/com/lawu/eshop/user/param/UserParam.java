package com.lawu.eshop.user.param;

import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * @author
 * @date 2017/3/13
 */
public class UserParam {

    @ApiParam(name="nickname", value = "昵称")
    private String nickname;

    @ApiParam(name="regionPath", value = "地区路径")
    private String regionPath;

    @ApiParam(name="sex", value = "性别")
    private Byte sex;

    @ApiParam(name="birthday", value = "生日")
    private Date birthday;

    @ApiParam(name="headimg", value = "头像")
    private String headimg;
   
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
