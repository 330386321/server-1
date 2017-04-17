package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.property.constants.ConsumptionTypeEnum;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;

/**
 * 交易明细转换器
 *
 * @author Sunny
 * @date 2017/3/29
 */
public class TransactionDetailConverter {
	
	public static TransactionDetailBO convert(TransactionDetailDO transactionDetailDO) {
		if (transactionDetailDO == null) {
			return null;
		}

		TransactionDetailBO transactionDetailBO = new TransactionDetailBO();
		BeanUtils.copyProperties(transactionDetailDO, transactionDetailBO, "direction");
		
		transactionDetailBO.setDirection(ConsumptionTypeEnum.getEnum(transactionDetailDO.getDirection()));
		
		return transactionDetailBO;
	}

	public static List<TransactionDetailBO> convertBOS(List<TransactionDetailDO> transactionDetailDOS) {
		List<TransactionDetailBO> rtn = new ArrayList<TransactionDetailBO>();
		
		if (transactionDetailDOS == null) {
			return rtn;
		}

		for (TransactionDetailDO transactionDetailDO : transactionDetailDOS) {
			rtn.add(convert(transactionDetailDO));
		}

		return rtn;
	}
	
	public static TransactionDetailDTO convert(TransactionDetailBO transactionDetailBO) {
		if (transactionDetailBO == null) {
			return null;
		}

		TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
		BeanUtils.copyProperties(transactionDetailBO, transactionDetailDTO);

		return transactionDetailDTO;
	}
	
	public static List<TransactionDetailDTO> convertDTOS(List<TransactionDetailBO> transactionDetailBOS) {
		List<TransactionDetailDTO> rtn = new ArrayList<TransactionDetailDTO>();
		
		if (transactionDetailBOS == null) {
			return rtn;
		}

		for (TransactionDetailBO transactionDetailBO : transactionDetailBOS) {
			rtn.add(convert(transactionDetailBO));
		}

		return rtn;
	}

}
