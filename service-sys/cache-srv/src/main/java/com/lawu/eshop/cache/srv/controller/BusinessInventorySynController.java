package com.lawu.eshop.cache.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.cache.srv.service.BusinessInventorySynService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

/**
 * 秒杀库存同步缓存服务控制器
 * 
 * @author jiangxinjun
 * @createDate 2017年11月30日
 * @updateDate 2017年11月30日
 */
@RestController
@RequestMapping(value = "businessInventorySyn/")
public class BusinessInventorySynController extends BaseController{
	
	@Autowired
	private BusinessInventorySynService businessInventorySynService;
	
	/**
	 * 从缓存中获取剩余库存
	 * @param businessKey
	 * @param id
	 * @return
	 * @author jiangxinjun
	 * @createDate 2017年11月30日
	 * @updateDate 2017年11月30日
	 */
	@RequestMapping(value = "inventory", method = RequestMethod.GET)
    Result<Integer> getInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
	     return successGet(businessInventorySynService.getInventory(businessKey, id));
    }

	/**
	 * 更新缓存中的库存
	 * @param businessKey
	 * @param id
	 * @param inventory
	 * @return
	 * @author jiangxinjun
	 * @createDate 2017年11月30日
	 * @updateDate 2017年11月30日
	 */
    @RequestMapping(value = "setInventory", method = RequestMethod.PUT)
    Result<?> setInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id, @RequestParam("inventory") Integer inventory) {
        businessInventorySynService.setInventory(businessKey, id, inventory);
        return successCreated();
    }

    /**
     * 缓存中的库存量减一
     * @param businessKey
     * @param id
     * @param inventory
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月30日
     * @updateDate 2017年11月30日
     */
	@RequestMapping(value = "decreaseInventory", method = RequestMethod.PUT)
    Result<Integer> decreaseInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
        return successCreated(businessInventorySynService.decreaseInventory(businessKey, id));
    }

    /**
	 * 缓存中的库存量加一
	 * 
	 * @param businessKey
	 * @param id
	 * @author jiangxinjun
	 * @createDate 2017年11月30日
	 * @updateDate 2017年11月30日
	 */
	@RequestMapping(value = "increaseInventory", method = RequestMethod.PUT)
    Result<?> increaseInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
	    businessInventorySynService.increaseInventory(businessKey, id);
        return successCreated();
    }

}
