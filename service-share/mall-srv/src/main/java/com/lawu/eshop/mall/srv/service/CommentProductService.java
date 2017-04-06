package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
public interface CommentProductService {
    /**
     * 新增评价信息
     * @param memberId
     * @param param
     * @param headImg
     * @return
     */
    Integer saveCommentProductInfo(Long memberId, CommentProductParam param,String headImg);

    /**
     * 商品评价列表
     * @param listParam
     * @return
     */
    Page<CommentProductBO> getCommentProducts(CommentProductListParam listParam);

    /**
     * 查询有图评价
     * @param listParam
     * @return
     */
    Page<CommentProductBO> getCommentProductsWithImgs(CommentProductListParam listParam);
}
