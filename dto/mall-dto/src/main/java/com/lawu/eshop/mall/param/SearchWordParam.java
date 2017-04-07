package com.lawu.eshop.mall.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.mall.constants.SearchWordTypeEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
public class SearchWordParam extends AbstractPageParam {

    @ApiModelProperty(value = "词条类型")
    private SearchWordTypeEnum searchWordTypeEnum;

    public SearchWordTypeEnum getSearchWordTypeEnum() {
        return searchWordTypeEnum;
    }

    public void setSearchWordTypeEnum(SearchWordTypeEnum searchWordTypeEnum) {
        this.searchWordTypeEnum = searchWordTypeEnum;
    }
}
