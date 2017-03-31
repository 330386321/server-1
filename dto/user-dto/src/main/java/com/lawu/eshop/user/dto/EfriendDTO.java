package com.lawu.eshop.user.dto;

import java.util.Date;

import com.lawu.eshop.user.constants.UserSexEnum;

import io.swagger.annotations.ApiModelProperty;

public class EfriendDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;
    
    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "生日")
    private String regionPath;

    @ApiModelProperty(value = "头像")
    private String headimg;
    
    @ApiModelProperty(value = "等级")
    private Integer level;
    
    @ApiModelProperty(value = "受邀者总数")
    private Integer inviterCount;
    
    @ApiModelProperty(value = "注册时间")
    private Date gmtCreate;


    private UserSexEnum userSex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }


    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

	public Integer getInviterCount() {
		return inviterCount;
	}

	public void setInviterCount(Integer inviterCount) {
		this.inviterCount = inviterCount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	

}
