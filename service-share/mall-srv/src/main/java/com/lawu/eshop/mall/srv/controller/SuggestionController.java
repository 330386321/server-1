package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.SuggestionDTO;
import com.lawu.eshop.mall.param.SuggestionListParam;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.srv.bo.SuggestionBO;
import com.lawu.eshop.mall.srv.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	/**
	 * 运营后台查询意见反馈记录
	 * @param pageParam
	 * @return
	 */
	@RequestMapping(value = "getSuggestionList",method = RequestMethod.POST)
	public Result<Page<List<SuggestionDTO>>> getSuggestionList(@RequestBody SuggestionListParam pageParam){
		if(pageParam  == null){
			return successGet(ResultCode.REQUIRED_PARM_EMPTY);
		}
		//查询意见反馈记录
		Page<SuggestionBO> suggestionBOPage = suggestionService.getSuggestionList(pageParam);
		if(suggestionBOPage.getRecords().isEmpty()){
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		return successGet();
	}
	
}
