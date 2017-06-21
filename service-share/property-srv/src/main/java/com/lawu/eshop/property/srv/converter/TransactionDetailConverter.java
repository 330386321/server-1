package com.lawu.eshop.property.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.constants.ConsumptionTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.dto.TransactionDetailBackageDTO;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.dto.TransactionDetailToMemberDTO;
import com.lawu.eshop.property.dto.TransactionDetailToMerchantDTO;
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
		rtn.setAmount(transactionDetailDO.getAmount());
		rtn.setBizId(transactionDetailDO.getBizId());
		rtn.setGmtCreate(transactionDetailDO.getGmtCreate());
		rtn.setId(transactionDetailDO.getId());
		rtn.setRemark(transactionDetailDO.getRemark());
		rtn.setThirdTransactionNum(transactionDetailDO.getThirdTransactionNum());
		rtn.setTitle(transactionDetailDO.getTitle());
		rtn.setTransactionAccount(transactionDetailDO.getTransactionAccount());
		rtn.setTransactionNum(transactionDetailDO.getTransactionNum());
		rtn.setTransactionType(transactionDetailDO.getTransactionType());
		rtn.setUserNum(transactionDetailDO.getUserNum());
		
		rtn.setDirection(ConsumptionTypeEnum.getEnum(transactionDetailDO.getDirection()));
		rtn.setTransactionAccountType(TransactionPayTypeEnum.getEnum(transactionDetailDO.getTransactionType()));

		return rtn;
	}

	public static List<TransactionDetailBO> convertBOS(List<TransactionDetailDO> transactionDetailDOS) {
		List<TransactionDetailBO> rtn = null;

		if (transactionDetailDOS == null || transactionDetailDOS.isEmpty()) {
			return rtn;
		}

		rtn = new ArrayList<>();
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
		rtn.setAmount(transactionDetailBO.getAmount());
		rtn.setBizId(transactionDetailBO.getBizId());
		rtn.setRemark(transactionDetailBO.getRemark());
		rtn.setTitle(transactionDetailBO.getTitle());
		rtn.setDirection(transactionDetailBO.getDirection());
		
		rtn.setTransactionDate(transactionDetailBO.getGmtCreate());

		return rtn;
	}

	public static List<TransactionDetailDTO> convertDTOS(List<TransactionDetailBO> transactionDetailBOS) {
		List<TransactionDetailDTO> rtn = new ArrayList<>();

		if (transactionDetailBOS == null || transactionDetailBOS.isEmpty()) {
			return rtn;
		}

		for (TransactionDetailBO transactionDetailBO : transactionDetailBOS) {
			rtn.add(convert(transactionDetailBO));
		}

		return rtn;
	}

	public static Page<TransactionDetailDTO> convertDTOPage(Page<TransactionDetailBO> transactionDetailBOPage) {
		Page<TransactionDetailDTO> rtn = new Page<>();
		rtn.setCurrentPage(transactionDetailBOPage.getCurrentPage());
		rtn.setTotalCount(transactionDetailBOPage.getTotalCount());
		rtn.setRecords(convertDTOS(transactionDetailBOPage.getRecords()));
		return rtn;
	}
	
	public static TransactionDetailToMemberDTO convertTransactionDetailToMemberDTO(TransactionDetailBO transactionDetailBO) {
		TransactionDetailToMemberDTO rtn = null;

		if (transactionDetailBO == null) {
			return rtn;
		}

		rtn = new TransactionDetailToMemberDTO();
		rtn.setAmount(transactionDetailBO.getAmount());
		rtn.setBizId(transactionDetailBO.getBizId());
		rtn.setRemark(transactionDetailBO.getRemark());
		rtn.setTitle(transactionDetailBO.getTitle());
		rtn.setDirection(transactionDetailBO.getDirection());
		rtn.setTransactionType(MemberTransactionTypeEnum.getEnum(transactionDetailBO.getTransactionType()));
		rtn.setTransactionDate(transactionDetailBO.getGmtCreate());

		return rtn;
	}

	public static List<TransactionDetailToMemberDTO> convertTransactionDetailToMemberDTOS(List<TransactionDetailBO> transactionDetailBOS) {
		List<TransactionDetailToMemberDTO> rtn = new ArrayList<>();

		if (transactionDetailBOS == null || transactionDetailBOS.isEmpty()) {
			return rtn;
		}

		for (TransactionDetailBO transactionDetailBO : transactionDetailBOS) {
			rtn.add(convertTransactionDetailToMemberDTO(transactionDetailBO));
		}

		return rtn;
	}
	
	public static Page<TransactionDetailToMemberDTO> convertTransactionDetailToMemberDTOPage(Page<TransactionDetailBO> transactionDetailBOPage) {
		Page<TransactionDetailToMemberDTO> rtn = new Page<>();
		rtn.setCurrentPage(transactionDetailBOPage.getCurrentPage());
		rtn.setTotalCount(transactionDetailBOPage.getTotalCount());
		rtn.setRecords(convertTransactionDetailToMemberDTOS(transactionDetailBOPage.getRecords()));
		return rtn;
	}
	
	public static TransactionDetailToMerchantDTO convertTransactionDetailToMerchantDTO(TransactionDetailBO transactionDetailBO) {
		TransactionDetailToMerchantDTO rtn = null;

		if (transactionDetailBO == null) {
			return rtn;
		}

		rtn = new TransactionDetailToMerchantDTO();
		rtn.setAmount(transactionDetailBO.getAmount());
		rtn.setBizId(transactionDetailBO.getBizId());
		rtn.setRemark(transactionDetailBO.getRemark());
		rtn.setTitle(transactionDetailBO.getTitle());
		rtn.setDirection(transactionDetailBO.getDirection());
		rtn.setTransactionType(MerchantTransactionTypeEnum.getEnum(transactionDetailBO.getTransactionType()));
		rtn.setTransactionDate(transactionDetailBO.getGmtCreate());

		return rtn;
	}

	public static List<TransactionDetailToMerchantDTO> convertTransactionDetailToMerchantDTOS(List<TransactionDetailBO> transactionDetailBOS) {
		List<TransactionDetailToMerchantDTO> rtn = new ArrayList<>();

		if (transactionDetailBOS == null || transactionDetailBOS.isEmpty()) {
			return rtn;
		}

		for (TransactionDetailBO transactionDetailBO : transactionDetailBOS) {
			rtn.add(convertTransactionDetailToMerchantDTO(transactionDetailBO));
		}

		return rtn;
	}
	
	public static Page<TransactionDetailToMerchantDTO> convertTransactionDetailToMerchantDTOPage(Page<TransactionDetailBO> transactionDetailBOPage) {
		Page<TransactionDetailToMerchantDTO> rtn = new Page<>();
		rtn.setCurrentPage(transactionDetailBOPage.getCurrentPage());
		rtn.setTotalCount(transactionDetailBOPage.getTotalCount());
		rtn.setRecords(convertTransactionDetailToMerchantDTOS(transactionDetailBOPage.getRecords()));
		return rtn;
	}

	public static Page<TransactionDetailBackageDTO> convertTransactionDetailBackageDTOPage(Page<TransactionDetailBO> transactionDetailBOPage) {
		Page<TransactionDetailBackageDTO> rtn = new Page<>();
		rtn.setCurrentPage(transactionDetailBOPage.getCurrentPage());
		rtn.setTotalCount(transactionDetailBOPage.getTotalCount());
		List<TransactionDetailBackageDTO> transactionDetailBackageDTOS = new ArrayList<>();
		if(transactionDetailBOPage.getRecords() == null || transactionDetailBOPage.getRecords().isEmpty()){
			rtn.setRecords(transactionDetailBackageDTOS);
			return rtn;
		}
		for(TransactionDetailBO transactionDetailBO : transactionDetailBOPage.getRecords()){
			TransactionDetailBackageDTO transactionDetailBackageDTO = new TransactionDetailBackageDTO();
			transactionDetailBackageDTO.setTitle(transactionDetailBO.getTitle());
			transactionDetailBackageDTO.setUserNum(transactionDetailBO.getUserNum());
			transactionDetailBackageDTO.setAmount(transactionDetailBO.getAmount());
			transactionDetailBackageDTO.setTransactionDate(transactionDetailBO.getGmtCreate());
			transactionDetailBackageDTOS.add(transactionDetailBackageDTO);
		}
		rtn.setRecords(transactionDetailBackageDTOS);
		return rtn;
	}

}
