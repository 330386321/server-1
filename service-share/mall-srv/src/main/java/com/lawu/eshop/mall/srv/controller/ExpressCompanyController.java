package com.lawu.eshop.mall.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.srv.bo.ExpressCompanyBO;
import com.lawu.eshop.mall.srv.converter.ExpressCompanyConverter;
import com.lawu.eshop.mall.srv.service.ExpressCompanyService;

/**
 * @author Sunny
 * @date 2017/3/27
 */
@RestController
@RequestMapping(value = "expressCompany/")
public class ExpressCompanyController extends BaseController {

	@Autowired
	private ExpressCompanyService expressCompanyService;

	/**
	 * 查询全部快递公司，根据ordinal排序
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Result<List<ExpressCompanyDTO>> list() {
		List<ExpressCompanyBO> bos = expressCompanyService.list();
		return successGet(ExpressCompanyConverter.convertDTOS(bos));
	}
	
	/**
	 * 根据id查询快递公司
	 * 
	 * @param id 快递公司id
	 * @return
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Result<ExpressCompanyDTO> get(@PathVariable("id") Integer id) {
		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}
		
		ExpressCompanyBO expressCompanyBO = expressCompanyService.get(id);
		
		if (expressCompanyBO == null || expressCompanyBO.getId() == null || expressCompanyBO.getId() <= 0) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		return successGet(ExpressCompanyConverter.convert(expressCompanyBO));
	}
	
}
