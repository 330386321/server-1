package com.lawu.eshop.property.srv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.QueryPropertyDTO;
import com.lawu.eshop.property.param.TestQuery1Param;
import com.lawu.eshop.property.param.TestQueryParam;
import com.lawu.eshop.property.srv.bo.QueryPropertyBO;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.utils.BeanUtil;

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
    
    ////////////////////////////////////////////////////////////////////////////////
    
    
    @RequestMapping(value = "query", method = RequestMethod.POST)
	public Result<Page<QueryPropertyDTO>> query(@RequestBody TestQuery1Param param) throws Exception {
		
		Page<QueryPropertyBO> page = propertyService.query(param);
		List<QueryPropertyBO> cbos = page.getRecords();
		List<QueryPropertyDTO> dtos = new ArrayList<QueryPropertyDTO>();
		for (QueryPropertyBO bo : cbos) {
			QueryPropertyDTO dto = new QueryPropertyDTO();
			BeanUtil.copyProperties(bo, dto);
			dtos.add(dto);
		}
		Page<QueryPropertyDTO> pageResult = new Page<QueryPropertyDTO>();
		pageResult.setTotalCount(page.getTotalCount());
		pageResult.setCurrentPage(page.getCurrentPage());
		pageResult.setRecords(dtos);
		return successCreated(pageResult);
	}

    @RequestMapping(value = "save", method = RequestMethod.POST)
   	public int save(@RequestBody TestQueryParam param) {
    	return propertyService.save(param);
    }
    
    @RequestMapping(value = "delete/{propertyIds}", method = RequestMethod.DELETE)
   	public int delete(@PathVariable String propertyIds) {
    	return propertyService.delete(propertyIds);
    }
    
    @RequestMapping(value = "get/{propertyId}", method = RequestMethod.GET)
   	public QueryPropertyDTO get(@PathVariable Long propertyId) throws Exception {
    	QueryPropertyBO bo = propertyService.get(propertyId);
    	QueryPropertyDTO dto = new QueryPropertyDTO();
    	BeanUtil.copyProperties(bo, dto);
    	return dto;
    }
}
