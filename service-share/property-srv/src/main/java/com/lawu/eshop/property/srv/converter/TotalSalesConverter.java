package com.lawu.eshop.property.srv.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.dto.TotalSalesDTO;
import com.lawu.eshop.property.param.TotalSalesQueryParam;
import com.lawu.eshop.property.srv.bo.TotalSalesBO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesQueryExample;
import com.lawu.eshop.utils.DateUtil;

/**
 * 平台总销量转换
 * @author Sunny
 * @date 2017/04/20
 *
 */
public class TotalSalesConverter {

	private TotalSalesConverter() {
		throw new IllegalAccessError("Utility class");
	}

	/**
	 * TotalSalesQueryParam转TotalSalesQueryExample
	 * @param param
	 * @return
	 */
	public static TotalSalesQueryExample convert(TotalSalesQueryParam param) {
		TotalSalesQueryExample rtn = null;
		if (param == null) {
			return rtn;
		}
		rtn = new TotalSalesQueryExample();
		rtn.setStart(DateUtil.getFirstSecondOfDay(param.getDate()));
		rtn.setEnd(DateUtil.getLastSecondOfDay(param.getDate()));
		return rtn;
	}
	
	/**
	 * TotalSalesDO转TotalSalesBO
	 * @param totalSalesDO
	 * @return
	 */
	public static TotalSalesBO convert(TotalSalesDO totalSalesDO) {
		TotalSalesBO rtn = null;
		if (totalSalesDO == null) {
			return rtn;
		}
		rtn = new TotalSalesBO();
		rtn.setAmount(totalSalesDO.getAmount());
		rtn.setTransactionType(MerchantTransactionTypeEnum.getEnum(totalSalesDO.getTransactionType()));
		return rtn;
	}
	
	/**
	 * TotalSalesDOList转TotalSalesBOList
	 * @param totalSalesDOList
	 * @return
	 */
	public static List<TotalSalesBO> convertTotalSalesBOList(List<TotalSalesDO> totalSalesDOList) {
		List<TotalSalesBO> rtn = null;
		if (totalSalesDOList == null || totalSalesDOList.isEmpty()) {
			return rtn;
		}
		rtn = new ArrayList<>();
		for (TotalSalesDO totalSalesDO : totalSalesDOList) {
			rtn.add(convert(totalSalesDO));
		}
		return rtn;
	}
	
	/**
	 * TotalSalesBOList转TotalSalesDTO
	 * @param totalSalesBOList
	 * @return
	 */
	public static TotalSalesDTO convertTotalSalesDTO(List<TotalSalesBO> totalSalesBOList) {
		TotalSalesDTO rtn = new TotalSalesDTO();
		if (totalSalesBOList == null || totalSalesBOList.isEmpty()) {
			rtn.setPayOrderAmount(new BigDecimal(0));
			rtn.setShoppingOrderAmount(new BigDecimal(0));
			return rtn;
		}
		for (TotalSalesBO totalSalesBO : totalSalesBOList) {
			if (MerchantTransactionTypeEnum.PAY.equals(totalSalesBO.getTransactionType())) {
				rtn.setPayOrderAmount(totalSalesBO.getAmount());
			} else if (MerchantTransactionTypeEnum.ORDER.equals(totalSalesBO.getTransactionType())) {
				rtn.setShoppingOrderAmount(totalSalesBO.getAmount());
			}
		}
		return rtn;
	}
}
