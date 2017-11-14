package com.lawu.eshop.order.srv.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.jobsextend.AbstractTxPageJob;

/**
 * 商家发货，买家超时收货
 * 平台自动收货
 * 定时任务
 * 
 * @author Sunny
 * @createDate 2017年4月17日
 * @updateDate 2017年11月14日
 */
public class ShoppingOrderAutoReceiptJob extends AbstractTxPageJob<ShoppingOrderItemExtendDO> {

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Override
    public List<ShoppingOrderItemExtendDO> queryPage(int currentPage, int pageSize) {
        return shoppingOrderService.selectAutoCommentOrder(currentPage, pageSize);
    }

    @Override
    public void executeSingle(ShoppingOrderItemExtendDO entity) {
        shoppingOrderService.tradingSuccess(entity.getId(), true);
    }
    
}
