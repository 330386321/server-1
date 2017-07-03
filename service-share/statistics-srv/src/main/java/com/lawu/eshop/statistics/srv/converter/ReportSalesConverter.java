package com.lawu.eshop.statistics.srv.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lawu.eshop.statistics.dto.ReportDataDTO;
import com.lawu.eshop.statistics.dto.ReportGroupDTO;
import com.lawu.eshop.statistics.dto.ReportSalesDTO;
import com.lawu.eshop.statistics.param.PlatformTotalSalesQueryParam;
import com.lawu.eshop.statistics.srv.bo.ReportSalesBO;
import com.lawu.eshop.utils.DateUtil;

/**
 * ReportSales转换器
 * 
 * @author Sunny
 * @date 2017年7月3日
 */
public class ReportSalesConverter {

    /**
     * ReportSalesBO转ReportSalesDTO
     *
     * @param reportSalesBO
     * @return
     */
    public static ReportSalesDTO convert(ReportSalesBO reportSalesBO) {
    	ReportSalesDTO rtn = null;
    	if (reportSalesBO == null) {
    		return rtn;
    	}
    	rtn = new ReportSalesDTO();
        rtn.setPayOrderAmount(reportSalesBO.getPayOrderAmount());
        rtn.setShoppingOrderAmount(reportSalesBO.getShoppingOrderAmount());
        return rtn;
    }
    
    /**
     * ReportSalesBO转ReportDataDTO
     *
     * @param reportSalesBO
     * @return
     */
    public static ReportDataDTO convert(List<ReportSalesBO> reportSalesBOList, PlatformTotalSalesQueryParam param) {
    	ReportDataDTO rtn = new ReportDataDTO();
    	rtn.setData(new ArrayList<>());
    	if (reportSalesBOList == null || reportSalesBOList.isEmpty()) {
    		return rtn;
    	}
    	Map<Integer, ReportSalesDTO> reportSalesDTOMap = new HashMap<>();
    	int maxValue = 0;
    	// 把reportSalesBOList按照X轴坐标分组
    	for (ReportSalesBO reportSalesBO : reportSalesBOList) {
    		int key = 0;
    		switch(param.getType()) {
				case DAILY:
					key = DateUtil.getFieldValue(reportSalesBO.getGmtReport(), Calendar.DAY_OF_MONTH);
					maxValue = DateUtil.getFieldMaxValue(param.getDate(), Calendar.DAY_OF_MONTH);
					break;
				case MONTH:
					key = DateUtil.getFieldValue(reportSalesBO.getGmtReport(), Calendar.MONTH) + 1;
					maxValue = DateUtil.getFieldMaxValue(param.getDate(), Calendar.MONTH) + 1;
					break;
				default:
					break;
    		}
    		reportSalesDTOMap.put(key, convert(reportSalesBO));
    	}
    	
    	for (int i = 1; i <= maxValue; i++) {
    		ReportGroupDTO<ReportSalesDTO> reportGroupDTO = new ReportGroupDTO<>();
    		reportGroupDTO.setX(String.valueOf(i));
    		ReportSalesDTO reportSalesDTO = reportSalesDTOMap.get(i);
    		if (reportSalesDTO == null) {
    			// 如果X轴对应的节点没有数据,设置为0
    			reportSalesDTO = new ReportSalesDTO();
    			reportSalesDTO.setPayOrderAmount(new BigDecimal(0));
    			reportSalesDTO.setShoppingOrderAmount(new BigDecimal(0));
    		}
    		reportGroupDTO.setY(reportSalesDTO);
    		rtn.getData().add(reportGroupDTO);
    	}
        return rtn;
    }
}
