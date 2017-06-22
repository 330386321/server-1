package com.lawu.eshop.mall.srv.converter;

import java.util.ArrayList;
import java.util.List;

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
	 * 隐藏默认的构造函数
	 */
	private ExpressCompanyConverter() {
		throw new IllegalAccessError("Utility class");
	}
	
	/**
	 * BO转换
	 *
	 * @param expressCompanyDO
	 * @return
	 */
	public static ExpressCompanyBO convert(ExpressCompanyDO expressCompanyDO) {
		ExpressCompanyBO rtn = null;
		if (expressCompanyDO == null || expressCompanyDO.getId() == null || expressCompanyDO.getId() <= 0) {
			return rtn;
		}

		rtn = new ExpressCompanyBO();
		rtn.setCode(expressCompanyDO.getCode());
		rtn.setHomepage(expressCompanyDO.getHomepage());
		rtn.setName(expressCompanyDO.getName());
		rtn.setOrdinal(expressCompanyDO.getOrdinal());
		rtn.setTel(expressCompanyDO.getTel());

		return rtn;
	}

	public static List<ExpressCompanyBO> convertBOS(List<ExpressCompanyDO> dos) {
		List<ExpressCompanyBO> rtn = null;
		if (dos == null || dos.isEmpty()) {
			return rtn;
		}

		rtn = new ArrayList<>();
		for (ExpressCompanyDO expressCompanyDO : dos) {
			rtn.add(convert(expressCompanyDO));
		}

		return rtn;
	}

	/**
	 * DTO转换
	 *
	 * @param bo
	 * @return
	 */
	public static ExpressCompanyDTO convert(ExpressCompanyBO bo) {
		ExpressCompanyDTO rtn = null;
		if (bo == null) {
			return null;
		}

		rtn = new ExpressCompanyDTO();
		rtn.setCode(bo.getCode());
		rtn.setId(bo.getId());
		rtn.setName(bo.getName());

		return rtn;
	}

	public static List<ExpressCompanyDTO> convertDTOS(List<ExpressCompanyBO> bos) {
		List<ExpressCompanyDTO> rtn = new ArrayList<>();
		if (bos == null || bos.isEmpty()) {
			return rtn;
		}
		
		for (ExpressCompanyBO bo : bos) {
			rtn.add(convert(bo));
		}

		return rtn;
	}

}
