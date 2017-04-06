package com.lawu.eshop.product.srv.mapper.extend;

import com.lawu.eshop.product.param.ProductRecommendParam;
import com.lawu.eshop.product.srv.domain.extend.ProductDOView;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public interface ProductDOMapperExtend {

    List<ProductDOView> listProductBycategoryId(ProductRecommendParam productRecommendParam);
}
