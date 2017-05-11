package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@FeignClient(value = "order-srv")
public interface PayOrderService {

    @RequestMapping(value = "payOrder/savePayOrderInfo/{memberId}", method = RequestMethod.POST)
    public Result<PayOrderIdDTO> savePayOrderInfo(@PathVariable("memberId") Long memberId, @ModelAttribute PayOrderParam param,@RequestParam("param") String numNum);

    @RequestMapping(value = "payOrder/getpayOrderList/{memberId}", method = RequestMethod.POST)
    public Result<Page<PayOrderDTO>> getpayOrderList(@PathVariable("memberId") Long memberId, @ModelAttribute PayOrderListParam param);

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "payOrder/delPayOrderInfo/{id}", method = RequestMethod.DELETE)
    public Result delPayOrderInfo(@PathVariable("id") Long id);
    
    /**
     * 第三方支付时获取买单的实际总金额，用于调用第三方支付平台
     * @param orderId
     * @return
     * @author Yangqh
     */
	@RequestMapping(method = RequestMethod.GET, value = "payOrder/selectThirdPayCallBackQueryPayOrder")
	ThirdPayCallBackQueryPayOrderDTO selectThirdPayCallBackQueryPayOrder(@RequestParam("orderId") String orderId);
}
