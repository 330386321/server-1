package com.lawu.eshop.mall.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.srv.service.SuggestionService;

/**
 * @author Sunny
 * @date 2017/3/24
 */
@RestController
@RequestMapping(value = "suggestion/")
public class SuggestionController extends BaseController {

	@Autowired
	private SuggestionService suggestionService;

	/**
	 * 保存反馈意见
	 * 
	 * @param parm
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result<Integer> save(@RequestBody SuggestionParam parm) {
		if (parm == null || parm.getUserNum() == null || parm.getClientType() == null || parm.getUserType() == null) {
			return failCreated("userNum|userType|clientType信息为空");
		}
		
		return successCreated(suggestionService.save(parm));
	}
	
}
