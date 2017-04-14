package com.lawu.eshop.order.srv.service.impl.transaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.transaction.ProductModeUpdateInventoryBO;
import com.lawu.eshop.order.srv.bo.transaction.ShoppingCartCreateOrderNotification;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@Service("shoppingCartCreateOrderTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.CREATEORDER, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_CREATEORDER)
public class ShoppingOrderCreateOrderTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingCartCreateOrderNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingCartCreateOrderNotification selectNotification(Long shoppingOrderId) {
        // 获取购物订单以及订单项
        ShoppingOrderExtendBO shoppingOrderExtendBO = shoppingOrderService.get(shoppingOrderId);
        
        // 如果没有相关记录返回null
        if (shoppingOrderExtendBO == null || shoppingOrderExtendBO.getId() == null || shoppingOrderExtendBO.getId() <= 0) {
        	return null;
        }
        
        ShoppingCartCreateOrderNotification shoppingCartCreateOrderNotification = new ShoppingCartCreateOrderNotification();
        
        // 组装发送数据
        shoppingCartCreateOrderNotification.setShoppingOrderId(shoppingOrderId);
        
        List<ProductModeUpdateInventoryBO> params = new ArrayList<ProductModeUpdateInventoryBO>();
        for (ShoppingOrderItemBO shoppingOrderItemBO : shoppingOrderExtendBO.getItems()) {
        	ProductModeUpdateInventoryBO productModeUpdateInventoryParam = new ProductModeUpdateInventoryBO();
        	productModeUpdateInventoryParam.setProdecutModelid(shoppingOrderItemBO.getProductModelId());
        	productModeUpdateInventoryParam.setQuantity(shoppingOrderItemBO.getQuantity());
        	params.add(productModeUpdateInventoryParam);
        }
        
        shoppingCartCreateOrderNotification.setParams(params);
        
        return shoppingCartCreateOrderNotification;
    }
    
    /**
     * 事务成功回调时
     * 根据购物订单id更新订单状态
     * 删除对应的购物车记录
     */
    @Transactional
    @Override
    public void afterSuccess(Long shoppingOrderId, Reply reply) {
    	/*
    	 * 更新购物订单以及购物订单的状态为待支付状态
    	 * 删除购物车记录
    	 */
    	shoppingOrderService.minusInventorySuccess(shoppingOrderId);
    }
}
