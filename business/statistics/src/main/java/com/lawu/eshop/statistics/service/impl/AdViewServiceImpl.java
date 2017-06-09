package com.lawu.eshop.statistics.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.dto.ViewDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.service.AdSrvService;
import com.lawu.eshop.statistics.service.AdViewExtendService;
import com.lawu.eshop.statistics.service.AdViewService;

@Service
public class AdViewServiceImpl extends BaseController implements AdViewExtendService{
	
	@Autowired
	private AdViewService adViewService;
	
	@Autowired
	private AdSrvService adSrvService;
	
	@Override
	public void updateViewCount() {
		Result<List<ViewDTO>> rs=adSrvService.getAllAd();
		 List<ViewDTO> list=rs.getModel();
		 if(!list.isEmpty()){
			 for (ViewDTO dto : list) {
				 Result<Set<String>> result=adViewService.getAdviews(dto.getId().toString());
				 if(isSuccess(result)){
					 Integer count=result.getModel().size();
					 if(dto.getViewCount()!=count){
						 adSrvService.updateViewCount(dto.getId(), count);
					 }
				 }
			}
		 }
	}

}
