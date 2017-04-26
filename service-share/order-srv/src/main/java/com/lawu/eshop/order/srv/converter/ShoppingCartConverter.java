package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.order.dto.ShoppingCartDTO;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingCartUpdateParam;
import com.lawu.eshop.order.srv.bo.ShoppingCartBO;
import com.lawu.eshop.order.srv.domain.ShoppingCartDO;

/**
 *
 * 购物车转换器
 *
 * @author Sunny
 * @date 2017/3/27
 */
public class ShoppingCartConverter {

	/**
	 * BO转换
	 *
	 * @param shoppingCartDO
	 * @return
	 */
	public static ShoppingCartBO convert(ShoppingCartDO shoppingCartDO) {
		if (shoppingCartDO == null) {
			return null;
		}

		ShoppingCartBO bo = new ShoppingCartBO();
		BeanUtils.copyProperties(shoppingCartDO, bo);

		return bo;
	}

	public static List<ShoppingCartBO> convertBOS(List<ShoppingCartDO> dos) {
		if (dos == null || dos.isEmpty()) {
			return null;
		}

		List<ShoppingCartBO> bos = new ArrayList<ShoppingCartBO>();
		for (ShoppingCartDO shoppingCartDO : dos) {
			bos.add(convert(shoppingCartDO));
		}

		return bos;
	}

	/**
	 * DTO转换
	 *
	 * @param bo
	 * @return
	 */
	public static ShoppingCartDTO convert(ShoppingCartBO bo) {
		if (bo == null) {
			return null;
		}

		ShoppingCartDTO dto = new ShoppingCartDTO();
		BeanUtils.copyProperties(bo, dto);

		return dto;
	}
	
	public static List<ShoppingCartDTO> convertDTOS(List<ShoppingCartBO> bos) {
		List<ShoppingCartDTO> rtn = new ArrayList<ShoppingCartDTO>();
		if (bos == null || bos.isEmpty()) {
			return rtn;
		}

		for (ShoppingCartBO bo : bos) {
			rtn.add(convert(bo));
		}

		return rtn;
	}
	
	/**
	 * DO转换
	 * 
	 * @param param
	 * @return
	 */
	public static ShoppingCartDO convert(ShoppingCartSaveParam param) {
		if (param == null) {
			return null;
		}

		ShoppingCartDO shoppingCartDO = new ShoppingCartDO();
		BeanUtils.copyProperties(param, shoppingCartDO);

		return shoppingCartDO;
	}
	
	public static ShoppingCartDO convert(ShoppingCartUpdateParam param) {
		if (param == null) {
			return null;
		}

		ShoppingCartDO shoppingCartDO = new ShoppingCartDO();
		BeanUtils.copyProperties(param, shoppingCartDO);

		return shoppingCartDO;
	}
}
