package com.lawu.eshop.product.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.SeckillActivityJoinDTO;
import com.lawu.eshop.product.dto.SeckillActivityManagerDTO;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;
import com.lawu.eshop.product.param.SeckillActivityManageParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityJoinBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityManageBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityJoinConverter;
import com.lawu.eshop.product.srv.service.SeckillActivityJoinService;

/**
 * 参加活动服务接口
 * 
 * @author zhangrc
 * @date 2017/3/30.
 */
@RestController
@RequestMapping(value = "seckillActivityJoin/")
public class SeckillActivityJoinController extends BaseController{
	
	@Autowired SeckillActivityJoinService seckillActivityJoinService;
	
	/**
	 * 活动专场列表查询
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "queryPage", method = RequestMethod.POST)
	public Result<Page<SeckillActivityJoinDTO>> queryPage(@RequestBody SeckillActivityJoinParam param){
		
		Page<SeckillActivityJoinBO> page =seckillActivityJoinService.queryPage(param);
		List<SeckillActivityJoinBO> list = page.getRecords();
		Page<SeckillActivityJoinDTO> pageActivity = new Page<>();
		pageActivity.setRecords(SeckillActivityJoinConverter.seckillActivityJoinDTOConverter(list));
		pageActivity.setCurrentPage(page.getCurrentPage());
		pageActivity.setTotalCount(page.getTotalCount());
		
		return successGet(pageActivity);
		
	}
	
	
	/**
	 * 活动管理列表查询
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "queryManagePage", method = RequestMethod.POST)
	public Result<Page<SeckillActivityManagerDTO>> queryManagePage(@RequestBody SeckillActivityManageParam param){
		
		Page<SeckillActivityManageBO> page =seckillActivityJoinService.queryManagePage(param);
		List<SeckillActivityManageBO> list = page.getRecords();
		Page<SeckillActivityManagerDTO> pageActivity = new Page<>();
		pageActivity.setRecords(SeckillActivityJoinConverter.seckillActivityJoinManageDTOConverter(list));
		pageActivity.setCurrentPage(page.getCurrentPage());
		pageActivity.setTotalCount(page.getTotalCount());
		
		return successGet(pageActivity);
		
	}

}
