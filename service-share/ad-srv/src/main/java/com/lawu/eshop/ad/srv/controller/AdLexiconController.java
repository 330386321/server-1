package com.lawu.eshop.ad.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdLexiconDTO;
import com.lawu.eshop.ad.srv.bo.AdLexiconBO;
import com.lawu.eshop.ad.srv.converter.AdLexiconConverter;
import com.lawu.eshop.ad.srv.service.AdLexiconService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * 广告词库
 * @author zhangrc
 * @date 2017/4/5
 *
 */
@RestController
@RequestMapping(value = "adLexicon/")
public class AdLexiconController extends BaseController{
	
	@Autowired
	private AdLexiconService adLexiconService;
	
	/**
	 * 添加词库
	 * @param adPlatformParam
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save( @RequestParam String title) {
		Integer id= adLexiconService.save(title);
		if(id>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
    }
	
	/**
	 * 查询广告词
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "selectList", method = RequestMethod.POST)
    public Result<List<AdLexiconDTO>> selectList(@RequestParam Long adId) {
		List<AdLexiconBO> BOS = adLexiconService.selectList(adId);
		return  successAccepted(AdLexiconConverter.convertDTOS(BOS));
    }

}
