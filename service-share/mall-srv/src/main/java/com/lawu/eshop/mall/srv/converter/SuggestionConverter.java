package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.constants.SuggestionClientType;
import com.lawu.eshop.mall.constants.SuggestionUserType;
import com.lawu.eshop.mall.dto.SuggestionDTO;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.srv.bo.SuggestionBO;
import com.lawu.eshop.mall.srv.domain.SuggestionDO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 反馈意见转换器
 *
 * @author Sunny
 * @date 2017/3/24
 */
public class SuggestionConverter {

	/**
	 * BO转换
	 *
	 * @param suggestionDO
	 * @return
	 */
	public static SuggestionBO convert(SuggestionDO suggestionDO) {
		if (suggestionDO == null) {
			return null;
		}

		SuggestionBO bo = new SuggestionBO();
		BeanUtils.copyProperties(suggestionDO, bo);
		bo.setClientType(SuggestionClientType.getEnum(suggestionDO.getClientType()));
		bo.setUserType(SuggestionUserType.getEnum(suggestionDO.getUserType()));
		return bo;
	}

	public static List<SuggestionBO> convertBOS(List<SuggestionDO> dos) {
		if (dos == null || dos.isEmpty()) {
			return null;
		}

		List<SuggestionBO> bos = new ArrayList<SuggestionBO>();
		for (SuggestionDO suggestionDO : dos) {
			bos.add(convert(suggestionDO));
		}

		return bos;
	}

	/**
	 * DTO转换
	 *
	 * @param bo
	 * @return
	 */
	public static SuggestionDTO convert(SuggestionBO bo) {
		if (bo == null) {
			return null;
		}

		SuggestionDTO dto = new SuggestionDTO();
		BeanUtils.copyProperties(bo, dto);

		return dto;
	}

	public static List<SuggestionDTO> convertDTOS(List<SuggestionBO> bos) {
		if (bos == null || bos.isEmpty()) {
			return null;
		}

		List<SuggestionDTO> dtos = new ArrayList<SuggestionDTO>();
		for (SuggestionBO bo : bos) {
			dtos.add(convert(bo));
		}

		return dtos;
	}
	
	public static SuggestionDO convert(String userNum, SuggestionParam param) {
		if (param == null) {
			return null;
		}

		SuggestionDO suggestionDO = new SuggestionDO();
		BeanUtils.copyProperties(param, suggestionDO, new String[]{"userType","clientType"});
		
		suggestionDO.setUserNum(userNum);
		
		// 用户类型，1是商家，2是会员
		if (userNum.startsWith("B")) {
			suggestionDO.setUserType((byte)1);
		}
		
		if (userNum.startsWith("M")) {
			suggestionDO.setUserType((byte)2);
		}
		
		if (param.getClientType() != null) {
			suggestionDO.setClientType(param.getClientType().value);
		}
		
		return suggestionDO;
	}

}
