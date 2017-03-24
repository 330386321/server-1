package com.lawu.eshop.framework.core.page;

/**
 * @author Leach
 * @date 2017/3/23
 */
public abstract class PageParam {

    /**
     * 当前页码
     */
    private Integer currentPage = 0;

    /**
     * 每页数量
     */
    private Integer pageSize = 20;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序类型
     */
    private OrderType orderType = OrderType.ASC;

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

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
