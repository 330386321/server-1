package com.lawu.eshop.mall.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
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
	@RequestMapping(value = "{userNum}", method = RequestMethod.POST)
	public Result save(@PathVariable("userNum") String userNum, @RequestBody SuggestionParam parm) {
		if (parm == null || parm.getClientType() == null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}
		
		Integer id = suggestionService.save(userNum, parm);
		
		if (id == null || id <= 0) {
			successCreated(ResultCode.SAVE_FAIL);
		}
		
		return successCreated(ResultCode.SUCCESS);
	}
	
}
