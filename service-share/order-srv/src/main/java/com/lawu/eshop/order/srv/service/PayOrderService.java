package com.lawu.eshop.order.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.PayOrderListParam;
import com.lawu.eshop.mall.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;

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
    Integer savePayOrderInfo(Long memberId, PayOrderParam param);

    /**
     * 买单记录列表
     * @param memberId
     * @param param
     * @return
     */
    Page<PayOrderBO> getpayOrderList(Long memberId, PayOrderListParam param);

    void delPayOrderInfo(Long id);
}