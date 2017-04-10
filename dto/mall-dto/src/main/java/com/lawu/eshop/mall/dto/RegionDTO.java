package com.lawu.eshop.mall.dto;

import com.lawu.eshop.mall.constants.RegionLevelEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
public class RegionDTO {

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "父级区域")
    private Integer parentId;

    @ApiModelProperty(value = "区域ID")
    private Integer id;

    @ApiModelProperty(value = "区域名称")
    private String name;

    @ApiModelProperty(value = "层级")
    private RegionLevelEnum levelEnum;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionLevelEnum getLevelEnum() {
        return levelEnum;
    }

    public void setLevelEnum(RegionLevelEnum levelEnum) {
        this.levelEnum = levelEnum;
    }
}
