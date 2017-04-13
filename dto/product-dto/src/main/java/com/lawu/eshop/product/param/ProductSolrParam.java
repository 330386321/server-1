package com.lawu.eshop.product.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/12.
 */
public class ProductSolrParam extends AbstractPageParam {

    @ApiModelProperty(value = "商品类型ID")
    private Integer categoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
