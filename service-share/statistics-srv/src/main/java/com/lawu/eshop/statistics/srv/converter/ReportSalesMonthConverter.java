package com.lawu.eshop.statistics.srv.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.statistics.param.PlatformTotalSalesSaveParam;
import com.lawu.eshop.statistics.srv.bo.ReportSalesBO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDO;

/**
 * ReportSalesMonth转换器
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
public class ReportSalesMonthConverter {

	/**
     * PlatformTotalSalesSaveParam转ReportSalesMonthDO
     *
     * @param param
     * @return
     */
    public static ReportSalesMonthDO convert(PlatformTotalSalesSaveParam param, List<ReportSalesDailyDO> reportSalesDailyDOList) {
    	ReportSalesMonthDO rtn = new ReportSalesMonthDO();
    	
    	BigDecimal payOrderAmount = new BigDecimal(0);
    	BigDecimal shoppingOrderAmount = new BigDecimal(0);
    	for (ReportSalesDailyDO reportSalesDailyDO : reportSalesDailyDOList) {
    		payOrderAmount = payOrderAmount.add(reportSalesDailyDO.getPayOrderAmount());
    		shoppingOrderAmount = shoppingOrderAmount.add(reportSalesDailyDO.getShoppingOrderAmount());
    	}
        rtn.setPayOrderAmount(payOrderAmount);
        rtn.setShoppingOrderAmount(shoppingOrderAmount);
        rtn.setTotalAmount(payOrderAmount.add(shoppingOrderAmount));
        rtn.setGmtReport(param.getGmtReport());
        rtn.setGmtCreate(new Date());
        return rtn;
    }
    
    /**
     * ReportSalesMonthDO转ReportSalesBO
     *
     * @param reportSalesMonthDO
     * @return
     */
    public static ReportSalesBO convert(ReportSalesMonthDO reportSalesMonthDO) {
    	ReportSalesBO rtn = null;
    	if (reportSalesMonthDO == null) {
    		return rtn;
    	}
    	rtn = new ReportSalesBO();
        rtn.setPayOrderAmount(reportSalesMonthDO.getPayOrderAmount());
        rtn.setShoppingOrderAmount(reportSalesMonthDO.getShoppingOrderAmount());
        rtn.setTotalAmount(reportSalesMonthDO.getTotalAmount());
        rtn.setGmtReport(reportSalesMonthDO.getGmtReport());
        rtn.setGmtCreate(reportSalesMonthDO.getGmtCreate());
        return rtn;
    }
    
    /**
     * ReportSalesMonthDOList转ReportSalesBOList
     *
     * @param reportSalesDailyDO
     * @return
     */
    public static List<ReportSalesBO> convertReportSalesBOList(List<ReportSalesMonthDO> reportSalesMonthDOList) {
    	List<ReportSalesBO> rtn = null;
    	if (reportSalesMonthDOList == null || reportSalesMonthDOList.isEmpty()) {
    		return rtn;
    	}
    	rtn = new ArrayList<>();
    	for (ReportSalesMonthDO reportSalesDailyDO : reportSalesMonthDOList) {
    		rtn.add(convert(reportSalesDailyDO));
    	}
        return rtn;
    }

}
