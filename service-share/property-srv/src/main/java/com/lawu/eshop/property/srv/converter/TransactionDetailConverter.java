package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.constants.ConsumptionTypeEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
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
		TransactionDetailBO rtn = null;

		if (transactionDetailDO == null || transactionDetailDO.getId() == null || transactionDetailDO.getId() <= 0) {
			return rtn;
		}

		rtn = new TransactionDetailBO();
		BeanUtils.copyProperties(transactionDetailDO, rtn, "direction", "transactionAccountType");

		rtn.setDirection(ConsumptionTypeEnum.getEnum(transactionDetailDO.getDirection()));
		rtn.setTransactionAccountType(TransactionPayTypeEnum.getEnum(transactionDetailDO.getTransactionType()));

		return rtn;
	}

	public static List<TransactionDetailBO> convertBOS(List<TransactionDetailDO> transactionDetailDOS) {
		List<TransactionDetailBO> rtn = null;

		if (transactionDetailDOS == null || transactionDetailDOS.isEmpty()) {
			return rtn;
		}

		rtn = new ArrayList<TransactionDetailBO>();
		for (TransactionDetailDO item : transactionDetailDOS) {
			rtn.add(convert(item));
		}

		return rtn;
	}

	public static TransactionDetailDTO convert(TransactionDetailBO transactionDetailBO) {
		TransactionDetailDTO rtn = null;

		if (transactionDetailBO == null) {
			return rtn;
		}

		rtn = new TransactionDetailDTO();
		BeanUtils.copyProperties(transactionDetailBO, rtn);

		rtn.setTransactionDate(transactionDetailBO.getGmtCreate());

		return rtn;
	}

	public static List<TransactionDetailDTO> convertDTOS(List<TransactionDetailBO> transactionDetailBOS) {
		List<TransactionDetailDTO> rtn = new ArrayList<TransactionDetailDTO>();

		if (transactionDetailBOS == null || transactionDetailBOS.isEmpty()) {
			return rtn;
		}

		for (TransactionDetailBO transactionDetailBO : transactionDetailBOS) {
			rtn.add(convert(transactionDetailBO));
		}

		return rtn;
	}

	public static Page<TransactionDetailDTO> convertDTOPage(Page<TransactionDetailBO> transactionDetailBOPage) {
		Page<TransactionDetailDTO> rtn = new Page<TransactionDetailDTO>();
		rtn.setCurrentPage(transactionDetailBOPage.getCurrentPage());
		rtn.setTotalCount(transactionDetailBOPage.getTotalCount());
		rtn.setRecords(convertDTOS(transactionDetailBOPage.getRecords()));
		return rtn;
	}

}
