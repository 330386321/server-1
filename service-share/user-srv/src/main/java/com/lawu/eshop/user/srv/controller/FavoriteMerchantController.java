package com.lawu.eshop.user.srv.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.FavoriteMerchantDTO;
import com.lawu.eshop.user.query.FavoriteMerchantParam;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;
import com.lawu.eshop.user.srv.converter.FavoriteMerchantConverter;
import com.lawu.eshop.user.srv.service.FavoriteMerchantService;

/**
 * 商家收藏
 * @author zhangrc
 * @date 2017/03/27
 *
 */
@RestController
@RequestMapping(value = "favoriteMerchant/")
public class FavoriteMerchantController extends BaseController{
	
	@Resource
	private FavoriteMerchantService favoriteMerchantService;
	
	/**
	 * 添加商家收藏
	 * @return
	 */
   @RequestMapping(value = "save", method = RequestMethod.POST)
   public Result save(@RequestParam  Long memberId ,@RequestParam Long merchantId ) {
   	   Integer i=favoriteMerchantService.save(memberId,merchantId);
	   if(i>0){
	   		return successCreated(ResultCode.SUCCESS);
	   }else{
	   		return successCreated(ResultCode.FAIL);
	   }
   	
   }
   
   @RequestMapping(value = "getMyFavoriteMerchant", method = RequestMethod.POST)
   public Result<Page<FavoriteMerchantDTO>> getMyFavoriteMerchant(@RequestParam  Long memberId ,@RequestBody FavoriteMerchantParam pageQuery) {
       Page<FavoriteMerchantBO> pageBO =favoriteMerchantService.getMyFavoriteMerchant(memberId,pageQuery);
       Page<FavoriteMerchantDTO> page=FavoriteMerchantConverter.convertPageDOTS(pageBO);
       return successGet(page);
   }

}
