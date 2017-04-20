package com.lawu.eshop.operator.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
import java.util.Set;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
public class PerssionDTO {


    @ApiModelProperty(value = "权限信息 key-value：权限键值:权限名称")
    private Set<Map<String,String>> permissionInfo;

    public Set<Map<String, String>> getPermissionInfo() {
        return permissionInfo;
    }

    public void setPermissionInfo(Set<Map<String, String>> permissionInfo) {
        this.permissionInfo = permissionInfo;
    }
}
