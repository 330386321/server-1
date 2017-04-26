package com.lawu.eshop.statistics.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
@FeignClient(value = "mall-srv")
public interface CommentMerchantService {

    /**
     * 商品好评率
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "commentMerchant/getCommentAvgGrade/{merchantId}",method = RequestMethod.GET)
    Result<CommentGradeDTO> getCommentAvgGrade(@PathVariable("merchantId") Long merchantId);
}