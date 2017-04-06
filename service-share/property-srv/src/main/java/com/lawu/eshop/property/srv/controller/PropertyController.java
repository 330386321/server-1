package com.lawu.eshop.property.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.srv.service.PropertyService;

/**
 * 
 * <p>
 * Description: 系统参数服务
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午6:49:08
 *
 */
@RestController
@RequestMapping(value = "property/")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;

    /**
     * 根据name获取Value
     * @param name
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "getValue", method = RequestMethod.GET)
    public Result getValue(@RequestParam String name) {
    	
    	String value = propertyService.getValue(name);
        return successCreated(value);
    }

    /**
     * 
     * @param name
     * @return
     */
    @RequestMapping(value = "getValues", method = RequestMethod.GET)
    public List<String> getValues(@PathVariable("name") String name) {
    	
    	List<String> values = propertyService.getValues(name);
        return values;
    }

}
