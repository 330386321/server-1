package com.lawu.eshop.user.param;

import com.lawu.eshop.user.constants.UserSexEnum;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author
 * @date 2017/3/13
 */
public class UserParam {

    @ApiParam(name = "nickname", value = "昵称")
    private String nickname;

    @ApiParam(name = "regionPath", value = "地区路径")
    private String regionPath;

    @ApiParam(name = "regionName", value = "区域名称")
    private String regionName;

    @ApiParam(name = "sex", value = "性别", required = true)
    private UserSexEnum userSexEnum;

    @ApiParam(name = "birthday", value = "生日")
    @DateTimeFormat( pattern =  "yyyy-MM-dd")
    private Date birthday;



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

    public UserSexEnum getUserSexEnum() {
        return userSexEnum;
    }

    public void setUserSexEnum(UserSexEnum userSexEnum) {
        this.userSexEnum = userSexEnum;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
