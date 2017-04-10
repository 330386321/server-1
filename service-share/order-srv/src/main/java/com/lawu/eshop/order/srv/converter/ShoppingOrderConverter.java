package com.lawu.eshop.order.srv.converter;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.mall.dto.CommentOrderDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExpressBO;
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
		// 设置为未付款状态
		shoppingOrderDO.setOrderStatus((byte) 0x00);
		// 记录状态设置为正常
		shoppingOrderDO.setStatus((byte)0x01);
		// 设置为待评价
		shoppingOrderDO.setIsEvaluation(false);
		shoppingOrderDO.setOrderNum(RandomUtil.getTableNumRandomString(""));
		shoppingOrderDO.setGmtCreate(new Date());
		shoppingOrderDO.setGmtModified(new Date());

		return shoppingOrderDO;
	}

    public static CommentOrderBO coverCommentStatusBO(ShoppingOrderDO shoppingOrderDO) {
		if(shoppingOrderDO == null){
			return null;
		}
		CommentOrderBO commentOrderBO = new CommentOrderBO();
		commentOrderBO.setId(shoppingOrderDO.getId());
		commentOrderBO.setEvaluation(shoppingOrderDO.getIsEvaluation());
		return commentOrderBO;
    }

	public static CommentOrderDTO coverCommentStatusDTO(CommentOrderBO commentOrderBO) {
		if(commentOrderBO == null){
			return null;
		}
		CommentOrderDTO commentOrderDTO = new CommentOrderDTO();
		commentOrderDTO.setEvaluation(commentOrderBO.getEvaluation());
		return commentOrderDTO;
	}
	
    public static ShoppingOrderExpressBO covert(ShoppingOrderDO shoppingOrderDO) {
		if(shoppingOrderDO == null){
			return null;
		}
		ShoppingOrderExpressBO shoppingOrderExpressBO = new ShoppingOrderExpressBO();
		BeanUtils.copyProperties(shoppingOrderDO, shoppingOrderExpressBO);
		return shoppingOrderExpressBO;
    }
    
    /**
     * ShoppingOrderExpressDTO转换
     * 
     * @param shoppingOrderExpressBO
     * @param expressInquiriesDetailBO
     * @return
     */
    public static ShoppingOrderExpressDTO covert(ShoppingOrderExpressBO shoppingOrderExpressBO, ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		if(shoppingOrderExpressBO == null){
			return null;
		}
		
		ShoppingOrderExpressDTO shoppingOrderExpressDTO = new ShoppingOrderExpressDTO();
		BeanUtils.copyProperties(shoppingOrderExpressBO, shoppingOrderExpressDTO, "expressInquiriesDetailDTO");
		
		shoppingOrderExpressDTO.setExpressInquiriesDetailDTO(ExpressInquiriesDetailConverter.convert(expressInquiriesDetailBO));
		
		return shoppingOrderExpressDTO;
    }
    
}
