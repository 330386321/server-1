package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Leach
 * @date 2017/3/13
 */
public class ProductCategoryDTO {

    @ApiModelProperty(value = "主键", required = true)
    private Integer id;

    @ApiModelProperty(value = "商品类型名称", required = true)
    private String name;

    @ApiModelProperty(value = "父ID", required = true)
    private Integer parentId;

    @ApiModelProperty(value = "全路径", required = true)
    private String path;

    @ApiModelProperty(value = "等级", required = true)
    private Integer level;

    @ApiModelProperty(value = "1热门分类", required = true)
    private Integer type;


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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
