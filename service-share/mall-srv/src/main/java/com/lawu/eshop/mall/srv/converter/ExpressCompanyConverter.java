package com.lawu.eshop.mall.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.srv.bo.ExpressCompanyBO;
import com.lawu.eshop.mall.srv.domain.ExpressCompanyDO;

/**
 *
 * 反馈意见转换器
 *
 * @author Sunny
 * @date 2017/3/24
 */
public class ExpressCompanyConverter {

	/**
	 * BO转换
	 *
	 * @param expressCompanyDO
	 * @return
	 */
	public static ExpressCompanyBO convert(ExpressCompanyDO expressCompanyDO) {
		if (expressCompanyDO == null) {
			return null;
		}

		ExpressCompanyBO bo = new ExpressCompanyBO();
		BeanUtils.copyProperties(expressCompanyDO, bo);

		return bo;
	}

	public static List<ExpressCompanyBO> convertBOS(List<ExpressCompanyDO> dos) {
		if (dos == null || dos.isEmpty()) {
			return null;
		}

		List<ExpressCompanyBO> bos = new ArrayList<ExpressCompanyBO>();
		for (ExpressCompanyDO expressCompanyDO : dos) {
			bos.add(convert(expressCompanyDO));
		}

		return bos;
	}

	/**
	 * DTO转换
	 *
	 * @param bo
	 * @return
	 */
	public static ExpressCompanyDTO convert(ExpressCompanyBO bo) {
		if (bo == null) {
			return null;
		}

		ExpressCompanyDTO dto = new ExpressCompanyDTO();
		BeanUtils.copyProperties(bo, dto);

		return dto;
	}

	public static List<ExpressCompanyDTO> convertDTOS(List<ExpressCompanyBO> bos) {
		if (bos == null || bos.isEmpty()) {
			return null;
		}

		List<ExpressCompanyDTO> dtos = new ArrayList<ExpressCompanyDTO>();
		for (ExpressCompanyBO bo : bos) {
			dtos.add(convert(bo));
		}

		return dtos;
	}

}
