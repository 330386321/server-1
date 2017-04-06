package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentMerchantParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
@FeignClient(value = "mall-srv")
public interface CommentMerchantService {

    @RequestMapping(value = "commentMerchant/saveCommentMerchantInfo/{memberId}", method = RequestMethod.POST)
    public Result saveCommentMerchantInfo(@PathVariable("memberId") Long memberId, @ModelAttribute CommentMerchantParam param,
                                          @RequestParam("commentPic") String commentPic);

    @RequestMapping(value = "commentMerchant/getCommentMerchantAllList", method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentMerchantAllList(@ModelAttribute CommentMerchantListParam listParam);
}
