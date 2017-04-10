package com.lawu.eshop.ad.srv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.ad.srv.converter.AdPlatformConverter;
import com.lawu.eshop.ad.srv.service.AdPlatformService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * 运营平台接口提供
 * @author zhangrc
 * @date 2017/4/5
 *
 */
@RestController
@RequestMapping(value = "adPlatform/")
public class AdPlatformController extends BaseController{
	
	@Resource
	private AdPlatformService adPlatformService;
	
	/**
	 * 添加广告
	 * @param adPlatformParam
	 * @return
	 */
	@RequestMapping(value = "saveAdPlatform", method = RequestMethod.POST)
    public Result saveAdPlatform(@RequestBody AdPlatformParam adPlatformParam, @RequestParam String url) {
		Integer id= adPlatformService.saveAdPlatform(adPlatformParam,url);
		if(id>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
    }
	
	/**
	 * 根据位置查询广告
	 * @param positionEnum
	 * @return
	 */
	@RequestMapping(value = "selectByPosition", method = RequestMethod.POST)
    public Result<List<AdPlatformDTO>> selectByPosition(@RequestBody PositionEnum positionEnum) {
		List<AdPlatformBO> BOS = adPlatformService.selectByPosition(positionEnum);
		List<AdPlatformDTO> list;
		list=AdPlatformConverter.convertDTOS(BOS);
		if(list==null){
			list=new ArrayList<AdPlatformDTO>();
		}
		return  successAccepted(list);
    }
	
	/**
	 * 删除广告
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "removeAdPlatform/{id}", method = RequestMethod.DELETE)
    public Result removeAdPlatform(@PathVariable Long id) {
		Integer i = adPlatformService.removeAdPlatform(id);
		if(id>0){
    		return successDelete();
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
    }


}