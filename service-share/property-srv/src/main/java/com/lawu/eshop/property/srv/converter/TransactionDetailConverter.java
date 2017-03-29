package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

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
		BeanUtils.copyProperties(transactionDetailDO, transactionDetailBO);

		return transactionDetailBO;
	}

	public static List<TransactionDetailBO> convertBOS(List<TransactionDetailDO> transactionDetailDOS) {
		if (transactionDetailDOS == null || transactionDetailDOS.isEmpty()) {
			return null;
		}

		List<TransactionDetailBO> transactionDetailBOS = new ArrayList<TransactionDetailBO>();
		for (TransactionDetailDO transactionDetailDO : transactionDetailDOS) {
			transactionDetailBOS.add(convert(transactionDetailDO));
		}

		return transactionDetailBOS;
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
		if (transactionDetailBOS == null || transactionDetailBOS.isEmpty()) {
			return null;
		}

		List<TransactionDetailDTO> transactionDetailDTOS = new ArrayList<TransactionDetailDTO>();
		for (TransactionDetailBO transactionDetailBO : transactionDetailBOS) {
			transactionDetailDTOS.add(convert(transactionDetailBO));
		}

		return transactionDetailDTOS;
	}

}
