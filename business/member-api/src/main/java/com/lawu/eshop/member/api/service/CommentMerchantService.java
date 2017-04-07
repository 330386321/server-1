package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
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

    /**
     * 新增商品评价
     * @param memberId
     * @param param
     * @param commentPic
     * @return
     */
    @RequestMapping(value = "commentMerchant/saveCommentMerchantInfo/{memberId}", method = RequestMethod.POST)
    public Result saveCommentMerchantInfo(@PathVariable("memberId") Long memberId, @ModelAttribute CommentMerchantParam param,
                                          @RequestParam("commentPic") String commentPic);

    /**
     * 查询商品评价全部列表
     * @param listParam
     * @return
     */
    @RequestMapping(value = "commentMerchant/getCommentMerchantAllList", method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentMerchantAllList(@ModelAttribute CommentMerchantListParam listParam);

    /**
     * 查询商品评价列表（有图）
     * @param listParam
     * @return
     */
    @RequestMapping(value = "commentMerchant/getCommentMerchantListWithImgs", method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentMerchantListWithImgs(@ModelAttribute CommentMerchantListParam listParam);


    /**
     * 商品好评率
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "commentMerchant/getCommentAvgGrade/{merchantId}",method = RequestMethod.GET)
    public Result<CommentGradeDTO> getCommentAvgGrade(@PathVariable("merchantId") Long merchantId);
}
