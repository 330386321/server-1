package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.ExpressInquiriesDetailStateEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToOperatorDTO;
import com.lawu.eshop.order.dto.foreign.TraceDTO;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.TraceBO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;

/**
 *
 * 购物订单扩展转换器
 *
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderExtendConverter {
	
	/**
	 * ShoppingOrderExtendDetailBO转换
	 * 
	 * @param shoppingOrderExtendDO
	 * @return
	 */
	public static ShoppingOrderExtendBO convertShoppingOrderExtendDetailBO(ShoppingOrderExtendDO shoppingOrderExtendDO) {
		if (shoppingOrderExtendDO == null) {
			return null;
		}

		ShoppingOrderExtendBO shoppingOrderExtendDetailBO = new ShoppingOrderExtendBO();
		BeanUtils.copyProperties(shoppingOrderExtendDO, shoppingOrderExtendDetailBO, new String[]{"items", "orderStatus", "commissionStatus"});
		
		// 转换为枚举类型
		shoppingOrderExtendDetailBO.setStatus(StatusEnum.getEnum(shoppingOrderExtendDO.getStatus()));
		shoppingOrderExtendDetailBO.setOrderStatus(ShoppingOrderStatusEnum.getEnum(shoppingOrderExtendDO.getOrderStatus()));
		shoppingOrderExtendDetailBO.setPaymentMethod(TransactionPayTypeEnum.getEnum(shoppingOrderExtendDO.getPaymentMethod()));
		shoppingOrderExtendDetailBO.setCommissionStatus(CommissionStatusEnum.getEnum(shoppingOrderExtendDO.getCommissionStatus()));
		
		if (shoppingOrderExtendDO.getItems() != null) {
			shoppingOrderExtendDetailBO.setItems(ShoppingOrderItemConverter.convert(shoppingOrderExtendDO.getItems()));
		}
		
		return shoppingOrderExtendDetailBO;
	}
	
	public static List<ShoppingOrderExtendBO> convertShoppingOrderExtendBO(List<ShoppingOrderExtendDO> shoppingOrderExtendDOList) {
		if (shoppingOrderExtendDOList == null || shoppingOrderExtendDOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderExtendBO> shoppingOrderExtendBOList = new ArrayList<ShoppingOrderExtendBO>();
		for (ShoppingOrderExtendDO shoppingOrderExtendDO : shoppingOrderExtendDOList) {
			shoppingOrderExtendBOList.add(convertShoppingOrderExtendDetailBO(shoppingOrderExtendDO));
		}
		
		return shoppingOrderExtendBOList;
	}
	
	/**
	 * ShoppingOrderExtendDetailDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderExtendDetailDTO convert(ShoppingOrderExtendBO shoppingOrderExtendBO, ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		ShoppingOrderExtendDetailDTO rtn = null;
		
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		rtn = new ShoppingOrderExtendDetailDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, rtn, new String[]{"items"});
		
		// 如果物流信息存在
		if (expressInquiriesDetailBO != null && expressInquiriesDetailBO.getTraces() != null && !expressInquiriesDetailBO.getTraces().isEmpty()) {
			rtn.setState(ExpressInquiriesDetailStateEnum.getEnum(expressInquiriesDetailBO.getState()));
			TraceBO traceBO = expressInquiriesDetailBO.getTraces().get(0);
			TraceDTO traceDTO = new TraceDTO();
			BeanUtils.copyProperties(traceBO, traceDTO);
			rtn.setTrace(traceDTO);
		}
		
		int quantity = 0;
		if (shoppingOrderExtendBO.getItems() != null) {
			if (shoppingOrderExtendBO.getItems() != null) {
				rtn.setItems(new ArrayList<ShoppingOrderItemDTO>());
				for (ShoppingOrderItemBO item : shoppingOrderExtendBO.getItems()) {
					quantity += item.getQuantity();
					rtn.getItems().add(ShoppingOrderItemConverter.convert(item));
				}
			}
		}
		rtn.setAmountOfGoods(quantity);
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderExtendQueryDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderExtendQueryDTO convertShoppingOrderExtendQueryDTO(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		ShoppingOrderExtendQueryDTO rtn = null;
		if (shoppingOrderExtendBO == null) {
			return rtn;
		}

		rtn = new ShoppingOrderExtendQueryDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, rtn, new String[]{"items"});
		
		int quantity = 0;
		if (shoppingOrderExtendBO.getItems() != null) {
			rtn.setItems(new ArrayList<ShoppingOrderItemDTO>());
			for (ShoppingOrderItemBO item : shoppingOrderExtendBO.getItems()) {
				quantity += item.getQuantity();
				rtn.getItems().add(ShoppingOrderItemConverter.convert(item));
			}
		}
		
		// 添加商品总数量
		rtn.setAmountOfGoods(quantity);
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderExtendQueryDTO List转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static List<ShoppingOrderExtendQueryDTO> convertShoppingOrderExtendQueryDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		if (shoppingOrderExtendBOList == null || shoppingOrderExtendBOList.isEmpty()) {
			return null;
		}
		
		List<ShoppingOrderExtendQueryDTO> shoppingOrderExtendQueryDTOList = new ArrayList<ShoppingOrderExtendQueryDTO>();
		for (ShoppingOrderExtendBO shoppingOrderExtendBO : shoppingOrderExtendBOList) {
			shoppingOrderExtendQueryDTOList.add(convertShoppingOrderExtendQueryDTO(shoppingOrderExtendBO));
		}
		
		return shoppingOrderExtendQueryDTOList;
	}
	
	/**
	 * ShoppingOrderQueryToMerchantDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderQueryToMerchantDTO convertShoppingOrderQueryToMerchantDTO(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		ShoppingOrderQueryToMerchantDTO shoppingOrderQueryToMerchantDTO = new ShoppingOrderQueryToMerchantDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, shoppingOrderQueryToMerchantDTO, new String[]{"items"});
		
		if (shoppingOrderExtendBO.getItems() != null && !shoppingOrderExtendBO.getItems().isEmpty()) {
			shoppingOrderQueryToMerchantDTO.setProductFeatureImage(shoppingOrderExtendBO.getItems().get(0).getProductFeatureImage());
		}
		
		
		return shoppingOrderQueryToMerchantDTO;
	}
	
	/**
	 * ShoppingOrderQueryToMerchantDTO List转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static List<ShoppingOrderQueryToMerchantDTO> convertShoppingOrderQueryToMerchantDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		List<ShoppingOrderQueryToMerchantDTO> rtn = new ArrayList<ShoppingOrderQueryToMerchantDTO>();
		if (shoppingOrderExtendBOList == null) {
			return rtn;
		}
		
		for (ShoppingOrderExtendBO shoppingOrderExtendBO : shoppingOrderExtendBOList) {
			rtn.add(convertShoppingOrderQueryToMerchantDTO(shoppingOrderExtendBO));
		}
		
		return rtn;
	}
	
	/**
	 * ShoppingOrderQueryToOperatorDTO转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static ShoppingOrderQueryToOperatorDTO convertShoppingOrderQueryToOperatorDTO(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		if (shoppingOrderExtendBO == null) {
			return null;
		}

		ShoppingOrderQueryToOperatorDTO shoppingOrderQueryToOperatorDTO = new ShoppingOrderQueryToOperatorDTO();
		BeanUtils.copyProperties(shoppingOrderExtendBO, shoppingOrderQueryToOperatorDTO, new String[]{"items"});
		
		if (shoppingOrderExtendBO.getItems() != null && !shoppingOrderExtendBO.getItems().isEmpty()) {
			shoppingOrderQueryToOperatorDTO.setProductFeatureImage(shoppingOrderExtendBO.getItems().get(0).getProductFeatureImage());
		}
		
		return shoppingOrderQueryToOperatorDTO;
	}
	
	/**
	 * ShoppingOrderQueryToOperatorDTO List转换
	 * 
	 * @param shoppingOrderExtendBO
	 * @return
	 */
	public static List<ShoppingOrderQueryToOperatorDTO> convertShoppingOrderQueryToOperatorDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		List<ShoppingOrderQueryToOperatorDTO> rtn = new ArrayList<ShoppingOrderQueryToOperatorDTO>();
		if (shoppingOrderExtendBOList == null || shoppingOrderExtendBOList.isEmpty()) {
			return rtn;
		}
		
		for (ShoppingOrderExtendBO shoppingOrderExtendBO : shoppingOrderExtendBOList) {
			rtn.add(convertShoppingOrderQueryToOperatorDTO(shoppingOrderExtendBO));
		}
		
		return rtn;
	}
	
	/**
	 * @param shoppingOrderExtendBO
	 * @return
	 * @author Sunny
	 */
	public static ShoppingOrderCommissionDTO convert(ShoppingOrderExtendBO shoppingOrderExtendBO) {
		ShoppingOrderCommissionDTO rtn = null;
		
		if (shoppingOrderExtendBO == null) {
			return rtn;
		}
		
		rtn = new ShoppingOrderCommissionDTO();
		
		BeanUtils.copyProperties(shoppingOrderExtendBO, rtn);
		
		return rtn;
	}
	
	/**
	 * @param shoppingOrderExtendBOList
	 * @return
	 * @author Sunny
	 */
	public static List<ShoppingOrderCommissionDTO> convertShoppingOrderCommissionDTOList(List<ShoppingOrderExtendBO> shoppingOrderExtendBOList) {
		List<ShoppingOrderCommissionDTO> rtn = null;
		
		if (shoppingOrderExtendBOList == null || shoppingOrderExtendBOList.isEmpty()) {
			return rtn;
		}
		
		rtn = new ArrayList<ShoppingOrderCommissionDTO>();
		
		for (ShoppingOrderExtendBO item : shoppingOrderExtendBOList) {
			rtn.add(convert(item));
		}

		return rtn;
	}
	
}
