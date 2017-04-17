package com.lawu.eshop.ad.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.FavoriteAdDOViewDTO;
import com.lawu.eshop.ad.param.FavoriteAdParam;
import com.lawu.eshop.ad.srv.bo.FavoriteAdDOViewBO;
import com.lawu.eshop.ad.srv.converter.FavoriteAdConverter;
import com.lawu.eshop.ad.srv.service.FavoriteAdService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * 广告收藏
 * @author zhangrc
 * @date 2017/4/8
 *
 */
@RestController
@RequestMapping(value = "favoriteAd/")
public class FavoriteAdController extends BaseController{

	@Autowired
	private FavoriteAdService favoriteAdService;
	
	/**
	 * 收藏
	 * @param memberId
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.PUT)
    public Result save(@RequestParam Long memberId,@RequestParam  Long  adId ) {
    	Integer i=favoriteAdService.save(memberId,adId);
    	if(i>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else if(i==0){
    		return successCreated(ResultCode.AD_FACORITE_EXIST);
    	}else{
    		return successCreated(ResultCode.SAVE_FAIL);
    	}
    	
    }
	
	/**
	 * 取消收藏
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable Long id) {
    	favoriteAdService.remove(id);
    	return successDelete();
    	
    }
	
	/**
	 * 我收藏的商品列表
	 * @param memberId
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "selectMyFavoriteAd", method = RequestMethod.POST)
    public Result<Page<FavoriteAdDOViewDTO>> selectMyFavoriteAd(@RequestParam Long memberId,@RequestBody FavoriteAdParam param ) {
		Page<FavoriteAdDOViewBO>   pageBO=favoriteAdService.selectMyFavoriteAd(param, memberId);
		Page<FavoriteAdDOViewDTO> pageDTO=new Page<FavoriteAdDOViewDTO>();
		pageDTO.setRecords(FavoriteAdConverter.convertDTOS(pageBO.getRecords()));
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
    	return successGet(pageDTO);
    	
    }

}
