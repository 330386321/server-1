package com.lawu.eshop.utils.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leach
 * @date 2017/3/23
 */
public class Page<T> {

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 数据总条数
     */
    private Integer totalCount;

    private List<T> records = new ArrayList<>();

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
