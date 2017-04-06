package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentMerchantParam;
import com.lawu.eshop.mall.srv.bo.CommentMerchantBO;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public interface CommentMerchantService {
    /**
     * 新增买单评价商家
     * @param memberId
     * @param param
     * @param commentPic
     * @return
     */
    Integer saveCommentMerchantInfo(Long memberId, CommentMerchantParam param, String commentPic);

    Page<CommentMerchantBO> getCommentMerchantAllList(CommentMerchantListParam listParam);

    Page<CommentMerchantBO> getCommentMerchantListWithImgs(CommentMerchantListParam listParam);
}
