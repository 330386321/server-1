package com.lawu.eshop.cache.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.cache.srv.service.AdCountService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

@RestController
@RequestMapping(value = "adCount/")
public class AdCountController extends BaseController{
	
	@Autowired
	private AdCountService adCountService;
	
	@RequestMapping(value = "setAdCountRecord", method = RequestMethod.GET)
    public Result setAdCountRecord(@RequestParam Long id, @RequestParam Integer count) {
		adCountService.setAdCountRecord(id, count);
		return successCreated();
    }

    @RequestMapping(value = "getAdCountRecord", method = RequestMethod.GET)
    public Result<Integer> getAdCountRecord(@RequestParam Long id) {
        return successAccepted(adCountService.getAdCountRecord(id));
    }
    
    @RequestMapping(value = "updateAdCountRecord", method = RequestMethod.GET)
    public Result updateAdCountRecord(@RequestParam Long  id) {
    	adCountService.updateAdCountRecord(id);
        return successCreated();
    }

}
