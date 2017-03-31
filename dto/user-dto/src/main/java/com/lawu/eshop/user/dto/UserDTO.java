package com.lawu.eshop.user.dto;

import com.lawu.eshop.user.constants.UserSexEnum;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * @author Leach
 * @date 2017/3/13
 */
public class UserDTO {

    @ApiParam(name = "num",value = "编号")
    private String num;

    @ApiParam(name = "account",value = "账号")
    private String account;

    @ApiParam(name = "nickname",value = "昵称")
    private String nickname;

    @ApiParam(name = "regionPath",value = "地区路径")
    private String regionPath;

    @ApiParam(name = "birthday",value = "生日")
    private Date birthday;

    @ApiParam(name = "headimg",value = "头像")
    private String headimg;

    @ApiParam(name = "userSex",value = "性别")
    private UserSexEnum userSex;

    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

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
