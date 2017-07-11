package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.MerchantPayOrderListDTO;
import com.lawu.eshop.order.param.MerchantPayOrderListParam;

/**
 * @author zhangyong
 * @date 2017/6/22.
 */
@FeignClient(value = "order-srv", path = "payOrder/")
public interface PayOrderService {

    @RequestMapping(value = "getMerchantPayOrderList", method = RequestMethod.POST)
    Result<Page<MerchantPayOrderListDTO>> getMerchantPayOrderList(@RequestParam("userId") Long userId, @RequestBody MerchantPayOrderListParam param);
}
