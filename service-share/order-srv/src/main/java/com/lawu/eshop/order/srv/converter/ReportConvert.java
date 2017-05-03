package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;
import com.lawu.eshop.user.dto.ReportRiseRateDTO;

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
				int num = Integer.valueOf(view.getKeyTxt()).intValue();
				if (num == j) {
					f = false;
					break;
				}
			}
			if (f) {
				ReportRiseRateView view = new ReportRiseRateView();
				view.setKeyTxt(j + "");
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

}
