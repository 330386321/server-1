package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lawu.eshop.order.dto.ReportRiseRateDTO;
import com.lawu.eshop.order.dto.ReportRiseRerouceDTO;
import com.lawu.eshop.order.srv.domain.extend.ReportFansSaleTransFormDO;
import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;

public class ReportConvert {

	/**
	 * 增量折线图转换，没数据的x轴默认填充并赋值0
	 * @param list
	 * @param x
	 * @return ReportRiseRateDTO
	 * @author yangqh
	 * @date 2017年5月2日 下午4:29:43
	 */
	public static ReportRiseRateDTO reportBrokeLineShow(List<ReportRiseRateView> list, int x) {
		for (int i = 0; i < x; i++) {
			boolean f = true;
			int j = i + 1;
			for (ReportRiseRateView view : list) {
				int num = Integer.parseInt(view.getKeyTxt());
				if (num == j) {
					f = false;
					break;
				}
			}
			if (f) {
				ReportRiseRateView view = new ReportRiseRateView();
				view.setKeyTxt(Integer.toString(j));
				view.setNum("0");
				list.add(i, view);
			}
		}
		
		List<String> dates = new ArrayList<String>();
		List<String> nums = new ArrayList<String>();
		for (ReportRiseRateView view : list) {
			dates.add(Integer.valueOf(view.getKeyTxt()).toString());
			nums.add(view.getNum());
		}
		ReportRiseRateDTO dto = new ReportRiseRateDTO();
		dto.setX(dates);
		dto.setY(nums);
		return dto;
	}
	
	/**
	 * 粉丝转化饼图
	 * 
	 * @param list
	 * @return
	 * @author Sunny
	 */
	public static List<ReportRiseRerouceDTO> convert(List<ReportFansSaleTransFormDO> list) {
		List<ReportRiseRerouceDTO> rtn = new ArrayList<ReportRiseRerouceDTO>();
		
		Map<String, ReportFansSaleTransFormDO> reportFansSaleTransFormDOMap = new HashMap<String, ReportFansSaleTransFormDO>();
		for (ReportFansSaleTransFormDO item : list) {
			reportFansSaleTransFormDOMap.put(item.getIsFans(), item);
		}
		
		// 粉丝订单数量
		ReportRiseRerouceDTO reportRiseRerouceDTO = new ReportRiseRerouceDTO();
		reportRiseRerouceDTO.setName("is_fans");
		ReportFansSaleTransFormDO reportFansSaleTransFormDO = reportFansSaleTransFormDOMap.get("1");
		reportRiseRerouceDTO.setValue(reportFansSaleTransFormDO == null ? "0" : reportFansSaleTransFormDO.getCount().toString());
		rtn.add(reportRiseRerouceDTO);
		
		// 非粉丝订单数量
		reportRiseRerouceDTO = new ReportRiseRerouceDTO();
		reportRiseRerouceDTO.setName("no_fans");
		reportFansSaleTransFormDO = reportFansSaleTransFormDOMap.get("0");
		reportRiseRerouceDTO.setValue(reportFansSaleTransFormDO == null ? "0" : reportFansSaleTransFormDO.getCount().toString());
		rtn.add(reportRiseRerouceDTO);
		
		return rtn;
	}

}
