package com.lawu.eshop.product.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.product.constant.ProductModelInventoryTypeEnum;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.bo.transaction.ProductModeUpdateInventoryBO;
import com.lawu.eshop.product.srv.bo.transaction.ShoppingCartCreateOrderNotification;
import com.lawu.eshop.product.srv.converter.ShoppingCartProductModelConverter;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.ProductModelDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelInventoryDO;
import com.lawu.eshop.product.srv.domain.ProductModelInventoryDOExample;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelInventoryDOMapper;
import com.lawu.eshop.product.srv.service.ProductModelService;

@Service
public class ProductModelServiceImpl implements ProductModelService {

	@Autowired
	private ProductDOMapper productDOMapper;
	
	@Autowired
	private ProductModelDOMapper productModelDOMapper;
	
	@Autowired
	private ProductModelInventoryDOMapper productModelInventoryDOMapper;

	@Override
	public ShoppingCartProductModelBO getShoppingCartProductModel(Long id) {
		ProductModelDO productModelDO =  productModelDOMapper.selectByPrimaryKey(id);
		
		ProductDO productDO = null;
		if (productModelDO != null && productModelDO.getProductId() != null) {
			productDO = productDOMapper.selectByPrimaryKey(productModelDO.getProductId());
		}
		
		return ShoppingCartProductModelConverter.convert(productModelDO, productDO);
	}
	
	@Override
	public List<ShoppingCartProductModelBO> getShoppingCartProductModel(List<Long> ids) {
		
		ProductModelDOExample productModelDOExample = new ProductModelDOExample();
		productModelDOExample.createCriteria().andIdIn(ids);
		
		List<ProductModelDO> productModelDOS =  productModelDOMapper.selectByExample(productModelDOExample);
		
		// 剔除重复
		Set<Long> productIds = new HashSet<Long>();
		for (ProductModelDO productModelDO : productModelDOS) {
			productIds.add(productModelDO.getProductId());
		}
		
		ProductDOExample productDOExample = new ProductDOExample();
		productDOExample.createCriteria().andIdIn(new ArrayList<Long>(productIds));
		
		List<ProductDO> productDOs = productDOMapper.selectByExample(productDOExample);
		
		return ShoppingCartProductModelConverter.convert(productModelDOS, productDOs);
	}
	
	/**
	 * 订单模块发送消息更新商品库存，并且保存商品库存流水记录
	 * 
	 * @param shoppingCartCreateOrderNotification 接收的数据
	 * @author Sunny
	 */
	@Transactional
	@Override
	public void updateInventory(ShoppingCartCreateOrderNotification shoppingCartCreateOrderNotification) {
		if (shoppingCartCreateOrderNotification == null) {
			return;
		}
		
		for (ProductModeUpdateInventoryBO param : shoppingCartCreateOrderNotification.getParams()) {
			
			/*
	    	 * 可能重复收到MQ消息
	    	 * 需要实现幂等性
	    	 */
			ProductModelInventoryDOExample productModelInventoryDOExample = new ProductModelInventoryDOExample();
			ProductModelInventoryDOExample.Criteria criteria = productModelInventoryDOExample.createCriteria();
			criteria.andShoppingOrderIdEqualTo(param.getProdecutModelid());
			criteria.andTypeEqualTo(ProductModelInventoryTypeEnum.CREATE_ORDER.getValue());
			criteria.andProductModelIdEqualTo(param.getProdecutModelid());
			int count = productModelInventoryDOMapper.countByExample(productModelInventoryDOExample);
			
			// 如果记录已经存在。继续循环
			if (count >= 0) {
				continue;
			}
			
			
			// 获取商品型号之前的库存数据
			ProductModelDO productModelDO = productModelDOMapper.selectByPrimaryKey(param.getProdecutModelid());
			
			if (productModelDO == null || productModelDO.getId() == null || productModelDO.getId() <= 0) {
				return;
			}
			
			// 计算库存
			Integer inventory = productModelDO.getInventory() - param.getQuantity();
			
			productModelDO.setInventory(inventory);
			productModelDO.setGmtModified(new Date());
			productModelDOMapper.updateByPrimaryKeySelective(productModelDO);
			
			// 保存库存流程记录
			ProductModelInventoryDO productModelInventoryDO = new ProductModelInventoryDO();
			productModelInventoryDO.setProductModelId(param.getProdecutModelid());
			productModelInventoryDO.setQuantity(param.getQuantity());
			productModelInventoryDO.setShoppingOrderId(shoppingCartCreateOrderNotification.getShoppingOrderId());
			// 类型为创建订单
			productModelInventoryDO.setType(ProductModelInventoryTypeEnum.CREATE_ORDER.getValue());
			productModelInventoryDO.setGmtCreate(new Date());
			productModelInventoryDO.setGmtModified(new Date());
			productModelInventoryDOMapper.insertSelective(productModelInventoryDO);
			
		}
	}
}
