package com.lawu.eshop.product.srv.controller;

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
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.FavoriteProductDTO;
import com.lawu.eshop.product.query.FavoriteProductQuery;
import com.lawu.eshop.product.srv.bo.FavoriteProductBO;
import com.lawu.eshop.product.srv.converter.FavoriteProductConverter;
import com.lawu.eshop.product.srv.service.FavoriteProductService;

/**
 * 商品收藏
 * @author zhangrc
 * @date 2017/03/31
 *
 */
@RestController
@RequestMapping(value = "favoriteProduct/")
public class FavoriteProductController extends BaseController{
	
	@Autowired
	private FavoriteProductService favoriteProductService;
	
	/**
	 * 收藏
	 * @param memberId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestParam Long memberId,@RequestParam  Long  productId ) {
    	Integer i=favoriteProductService.save(memberId,productId);
    	if(i>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else if(i==0){
    		return successCreated(ResultCode.GOODS_PRODUCT_FACORITE_EXIST);
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
    	Integer i=favoriteProductService.remove(id);
    	return successDelete();
    	
    }
	
	/**
	 * 我收藏的商品列表
	 * @param memberId
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "selectMyFavoriteProduct", method = RequestMethod.POST)
    public Result<Page<FavoriteProductDTO>> selectMyFavoriteProduct(@RequestParam Long memberId,@RequestBody FavoriteProductQuery query ) {
		Page<FavoriteProductBO>  pageBO=favoriteProductService.selectMyFavoriteProduct(memberId, query);
		Page<FavoriteProductDTO> pageDTO=new Page<FavoriteProductDTO>();
		pageDTO.setRecords(FavoriteProductConverter.convertDTOS(pageBO.getRecords()));
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
    	return successGet(pageDTO);
    	
    }

}
