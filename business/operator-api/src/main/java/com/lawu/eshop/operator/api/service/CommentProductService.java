package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentOperatorDTO;
import com.lawu.eshop.mall.param.CommentListParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@FeignClient(value = "mall-srv")
public interface CommentProductService {

    @RequestMapping(value = "commentProduct/getCommentProductListOperator",method = RequestMethod.POST)
    public Result<Page<CommentOperatorDTO>> getCommentProductListOperator(@ModelAttribute CommentListParam listParam);

    @RequestMapping(value = "commentMerchant/getCommentMerchantListOperator",method = RequestMethod.POST)
    public Result<Page<CommentOperatorDTO>> getCommentMerchantListOperator(@ModelAttribute CommentListParam listParam);

    @RequestMapping(value = "commentProduct/delCommentProductInfo/{commentId}",method = RequestMethod.DELETE)
    public Result delCommentProductInfo(@PathVariable("commentId") Long commentId);

    @RequestMapping(value = "commentMerchant/delCommentMerchantInfo/{commentId}",method = RequestMethod.DELETE)
    public Result delCommentMerchantInfo(@PathVariable("commentId") Long commentId);
}
