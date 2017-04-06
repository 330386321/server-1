package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.CommentProductParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
@FeignClient(value = "mall-srv")
public interface CommentProductService {

    /**
     * 用户新增评价
     *
     * @param memberId
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "commentProduct/saveCommentProductInfo/{memberId}")
    Result saveCommentProductInfo(@PathVariable("memberId") Long memberId, @ModelAttribute CommentProductParam param, @RequestParam("headImg") String headImg);
}
