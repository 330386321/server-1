package com.lawu.eshop.product.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.product.srv.bo.ShoppingCartSettlementNotification;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
//@Service
@CompensatingTransactionFollow(topic = "transaction-reg", tags = "product")
public class ShoppingCartSettlementTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingCartSettlementNotification> {
	
	@Autowired
	private ProductModelDOMapper productModelDOMapper;
	
    @Override
    public void execute(ShoppingCartSettlementNotification notification) {
    	ProductModelDO productModelDO = new ProductModelDO();
		productModelDO.setId(notification.getProductModelId());
		Integer inventory = productModelDOMapper.selectByPrimaryKey(notification.getProductModelId()).getInventory() - notification.getQuantity();
		productModelDO.setInventory(inventory);
		productModelDOMapper.updateByPrimaryKey(productModelDO);
    }
}
