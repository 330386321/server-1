package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentProductIdDTO;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@FeignClient(value = "mall-srv")
public interface CommentService {
    /**
     * 商家回复商品评论
     * @param commentId
     * @param replyContent
     * @return
     */
    @RequestMapping(value = "commentProduct/replyProductComment/{commentId}", method = RequestMethod.PUT)
    public Result replyProductComment(@PathVariable("commentId") Long commentId, @RequestParam("replyContent") String replyContent);

    /**
     * 商家回复评论商家
     * @param commentId
     * @param replyContent
     * @return
     */
    @RequestMapping(value = "commentMerchant/replyMerchantComment/{commentId}", method = RequestMethod.PUT)
    Result replyMerchantComment(@PathVariable("commentId") Long commentId, @RequestParam("replyContent") String replyContent);

    /**
     * 根据商家ID查询商品评论信息
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "commentProduct/getProductCommentListByMerchantId",method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getProductCommentListByMerchantId(@ModelAttribute CommentMerchantListParam pageParam);

    /**
     * 获取用户评价商品ids
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "commentProduct/getProductCommentIdsByMerchantId",method = RequestMethod.POST)
    public Result<Page<CommentProductIdDTO>> getProductCommentIdsByMerchantId(@ModelAttribute CommentMerchantListParam pageParam);

}
