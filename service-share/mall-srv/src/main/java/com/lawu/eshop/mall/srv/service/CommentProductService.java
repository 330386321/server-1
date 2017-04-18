package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentGradeBO;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mq.dto.order.ShoppingOrderAutoCommentNotification;

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

    CommentProductBO findProductComment(Long commentId);

    Integer replyProductComment(Long commentId, String replyContent);

    void delCommentProductInfo(Long commentId);

    CommentGradeBO getCommentAvgGrade(Long productId);

    Page<CommentProductBO> getCommentProductListOperator(CommentListParam listParam);

    /**
     * 定时器增加默认好评
     * @param memberId
     * @param productId
     */
    void saveCommentProductInfoOrderJob(ShoppingOrderAutoCommentNotification notification);

    Page<CommentProductBO> getProductCommentListByMerchantId(CommentMerchantListParam pageParam);

    Page<CommentProductBO> getProductCommentIdsByMerchantId(CommentMerchantListParam pageParam);

    void delCommentByProductModelId(Long productModelId);

    void delCommentByOrderItemId(Long orderItemId);
}
