package com.lawu.eshop.order.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.bo.ThirdPayCallBackQueryPayOrderBO;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public interface PayOrderService {
    /**
     * 增加买单记录
     * @param memberId
     * @param param
     * @return
     */
    PayOrderBO savePayOrderInfo(Long memberId, PayOrderParam param);

    /**
     * 买单记录列表
     * @param memberId
     * @param param
     * @return
     */
    Page<PayOrderBO> getpayOrderList(Long memberId, PayOrderListParam param);

    void delPayOrderInfo(Long id);

    /**
     * 第三方支付时获取买单的实际总金额，用于调用第三方支付平台
     * @param orderId
     * @return
     * @author Yangqh
     */
    ThirdPayCallBackQueryPayOrderBO selectThirdPayCallBackPayOrder(String orderId);
}
