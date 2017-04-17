package com.lawu.eshop.order.srv.converter;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.CommentOrderDTO;
import com.lawu.eshop.order.dto.ShoppingOrderIsNoOnGoingOrderDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderIsNoOnGoingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.utils.RandomUtil;

/**
 *
 * 购物订单转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderConverter {

	/**
	 * ShoppingOrderDO转换
	 * 
	 * @param param
	 * @return
	 */
	public static ShoppingOrderDO convert(ShoppingOrderSettlementParam param) {
		if (param == null) {
			return null;
		}

		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		BeanUtils.copyProperties(param, shoppingOrderDO,
				new String[] { "status", "isEvaluation", "gmtCreate", "gmtModified" });
		// 设置为待处理状态
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.PENDING.getValue());
		// 记录状态设置为正常
		shoppingOrderDO.setStatus((byte)0x01);
		// 设置为待评价
		shoppingOrderDO.setOrderNum(RandomUtil.getTableNumRandomString(""));
		shoppingOrderDO.setGmtCreate(new Date());
		shoppingOrderDO.setGmtModified(new Date());

		return shoppingOrderDO;
	}

	public static CommentOrderDTO coverCommentStatusDTO(ShoppingOrderItemBO shoppingOrderItemBO) {
		if(shoppingOrderItemBO == null){
			return null;
		}
		CommentOrderDTO commentOrderDTO = new CommentOrderDTO();
		commentOrderDTO.setEvaluation(shoppingOrderItemBO.getIsEvaluation());
		return commentOrderDTO;
	}
	
    /**
     * ShoppingOrderExpressDTO转换
     * 
     * @param shoppingOrderBO
     * @param expressInquiriesDetailBO
     * @return
     */
    public static ShoppingOrderExpressDTO covert(ShoppingOrderBO shoppingOrderBO, ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		if(shoppingOrderBO == null){
			return null;
		}
		
		ShoppingOrderExpressDTO shoppingOrderExpressDTO = new ShoppingOrderExpressDTO();
		BeanUtils.copyProperties(shoppingOrderBO, shoppingOrderExpressDTO, "expressInquiriesDetailDTO");
		
		shoppingOrderExpressDTO.setExpressInquiriesDetailDTO(ExpressInquiriesDetailConverter.convert(expressInquiriesDetailBO));
		
		return shoppingOrderExpressDTO;
    }
    
    public static ShoppingOrderBO convertShoppingOrderBO(ShoppingOrderDO shoppingOrderDO) {
		if(shoppingOrderDO == null){
			return null;
		}
		
		ShoppingOrderBO shoppingOrderBO = new ShoppingOrderBO();
		BeanUtils.copyProperties(shoppingOrderDO, shoppingOrderBO, "orderStatus", "paymentMethod");
		
		shoppingOrderBO.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderDO.getOrderStatus()));
		shoppingOrderBO.setPaymentMethod(TransactionPayTypeEnum.getEnum(shoppingOrderDO.getPaymentMethod()));
		
		return shoppingOrderBO;
    }
    
    /**
     * 组装更新订单参数
     * 
     * @param shoppingOrderDO
     * @return
     * @author Sunny
     */
    public static ShoppingOrderDO convert(ShoppingOrderDO shoppingOrderDO, ShoppingOrderUpdateInfomationParam param) {
		if(shoppingOrderDO == null || param == null){
			return null;
		}
		
		BeanUtils.copyProperties(param, shoppingOrderDO);
		
		return shoppingOrderDO;
    }
    
    /**
     * 组装订单是否有正在进行中的订单参数
     * 
     * @param count
     * @return
     * @author Sunny
     */
    public static ShoppingOrderIsNoOnGoingOrderBO convert(long count) {
    	ShoppingOrderIsNoOnGoingOrderBO shoppingOrderIsNoOnGoingOrderBO = new ShoppingOrderIsNoOnGoingOrderBO();
    			
    	if (count <= 0){
    		shoppingOrderIsNoOnGoingOrderBO.setIsNoOnGoingOrder(true);
    	} else {
    		shoppingOrderIsNoOnGoingOrderBO.setIsNoOnGoingOrder(false);
    	}
		
		return shoppingOrderIsNoOnGoingOrderBO;
    }
    
    /**
     * 组装订单是否有正在进行中的订单参数
     * 
     * @param ShoppingOrderIsNoOnGoingOrderBO
     * @return
     * @author Sunny
     */
    public static ShoppingOrderIsNoOnGoingOrderDTO convert(ShoppingOrderIsNoOnGoingOrderBO shoppingOrderIsNoOnGoingOrderBO) {
    	if (shoppingOrderIsNoOnGoingOrderBO == null) {
    		return null;
    	}
    	
    	ShoppingOrderIsNoOnGoingOrderDTO shoppingOrderIsNoOnGoingOrderDTO = new ShoppingOrderIsNoOnGoingOrderDTO();
    	BeanUtils.copyProperties(shoppingOrderIsNoOnGoingOrderBO, shoppingOrderIsNoOnGoingOrderDTO);
    	
		return shoppingOrderIsNoOnGoingOrderDTO;
    }
}
