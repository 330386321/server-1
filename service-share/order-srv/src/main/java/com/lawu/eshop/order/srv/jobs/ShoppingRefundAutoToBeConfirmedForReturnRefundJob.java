package com.lawu.eshop.order.srv.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;
import com.lawu.jobsextend.AbstractTxPageJob;

/**
 * 退款中-待商家处理
 * 退款类型-退货退款
 * 商家处理超时定时任务
 * 如果商家还未操作自动退款给买家 
 * 
 * @author Sunny
 * @createDate 2017年4月17日
 * @createDate 2017年11月15日
 */
public class ShoppingRefundAutoToBeConfirmedForReturnRefundJob extends AbstractTxPageJob<ShoppingOrderItemExtendDO> {
    
    @Autowired
    private ShoppingRefundDetailService shoppingRefundDetailService;
    
    @Override
    public List<ShoppingOrderItemExtendDO> queryPage(int offset, int pageSize) {
        return shoppingRefundDetailService.selectAutoRefundToBeConfirmedForReturnRefund(offset, pageSize);
    }

    @Override
    public void executeSingle(ShoppingOrderItemExtendDO entity) {
        shoppingRefundDetailService.agreeToRefund(entity.getShoppingRefundDetail().getId());;
    }

    @Override
    public boolean isStatusData() {
        return true;
    }

    @Override
    public boolean continueWhenSinglePageFail() {
        return true;
    }
}
