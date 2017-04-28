package com.lawu.eshop.product.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiParam;

/**
 * @author meishuquan
 * @date 2017/4/27.
 */
public class ListProductParam extends AbstractPageParam {

    @ApiParam(value = "商品名称")
    private String name;

    @ApiParam(value = "排序字段")
    private String sortName;

    @ApiParam(value = "排序方式")
    private String sortOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
