package com.lawu.eshop.statistics.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.statistics.param.PlatformTotalSalesSaveParam;
import com.lawu.eshop.statistics.srv.bo.ReportSalesBO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDO;
import com.lawu.eshop.utils.DateUtil;

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
    public static ReportSalesMonthDO convert(PlatformTotalSalesSaveParam param) {
    	ReportSalesMonthDO rtn = new ReportSalesMonthDO();
        rtn.setPayOrderAmount(param.getPayOrderAmount());
        rtn.setShoppingOrderAmount(param.getShoppingOrderAmount());
        rtn.setTotalAmount(param.getPayOrderAmount().add(param.getShoppingOrderAmount()));
        rtn.setGmtReport(DateUtil.getFirstDayOfMonth(DateUtil.getMonthBefore(DateUtil.getNowDate())));
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
