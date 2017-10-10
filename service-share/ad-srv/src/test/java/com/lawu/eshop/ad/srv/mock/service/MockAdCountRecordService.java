package com.lawu.eshop.ad.srv.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.srv.service.AdCountRecordService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

@Service
public class MockAdCountRecordService extends BaseController implements AdCountRecordService {
	
	@RequestMapping(value = "adCount/getAdCountRecord", method = RequestMethod.GET)
    public Result<Object> getAdCountRecord(@RequestParam("id") Long id) {
		return successGet(Long.valueOf(0L));
	}
	
	@RequestMapping(value = "userRedPacketCount/getUserRedPacketCountRecord", method = RequestMethod.GET)
    public Result<Object> getUserRedPacketCountRecord(@RequestParam("id") Long id) {
    	return successGet(Long.valueOf(0L));
	 }
	

}
