package com.lawu.eshop.framework.core.page;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页参数
 * @author Leach
 * @date 2017/4/1
 */
public abstract class AbstractPageParam {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码（从0开始）", required = true)
    private Integer currentPage = 0;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize = 20;


    public int getOffset() {
        return this.pageSize * this.currentPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
