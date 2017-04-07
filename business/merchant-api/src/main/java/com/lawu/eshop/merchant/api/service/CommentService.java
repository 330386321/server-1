package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}
