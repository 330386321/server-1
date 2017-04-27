package com.lawu.eshop.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.service.AdSrvService;
import com.lawu.eshop.statistics.service.AdViewExtendService;
import com.lawu.eshop.statistics.service.AdViewService;

@Service
public class AdViewServiceImpl implements AdViewExtendService{
	
	@Autowired
	private AdViewService adViewService;
	
	@Autowired
	private AdSrvService adSrvService;
	
	@Override
	public void updateViewCount() {
		Result<List<Long>> rs=adSrvService.getAllAd();
		 List<Long> list=rs.getModel();
		 if(!list.isEmpty()){
			 for (Long id : list) {
				 List<String> count=adViewService.getAdviews(id.toString());
				 adSrvService.updateViewCount(id, count.size());
			}
		 }
	}

}
