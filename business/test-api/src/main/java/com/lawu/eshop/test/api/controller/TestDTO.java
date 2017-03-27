package com.lawu.eshop.test.api.controller;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Leach
 * @date 2017/3/27
 */
class TestDTO {
    @ApiModelProperty(value = "ID", required = true)
    private Long id;
    @ApiModelProperty(value = "备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
