package com.lawu.eshop.product.srv.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.service.ProductService;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    /**
     * 查询商品列表
     * @param query
     * @return
     */
    @RequestMapping(value = "selectProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> selectProduct(@RequestBody ProductQuery query) {
    	Page<ProductQueryBO> page = productService.selectProduct(query);
    	List<ProductQueryBO> list = page.getRecords();
    	List<ProductQueryDTO> dtos = ProductConverter.convertDTOS(list);
    	
    	Page<ProductQueryDTO> retPage = new Page<>();
    	retPage.setCurrentPage(query.getCurrentPage());
    	retPage.setTotalCount(page.getTotalCount());
    	retPage.setRecords(dtos);
    	
    	return successAccepted(retPage);
    }
    
    /**
     * 商品批量操作
     * @param ids	商品ID字符串
     * @param status 目标修改的状态
     * @return
     */
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.GET)
    public Result updateProductStatus(@RequestParam String ids , @RequestParam Integer status){
    	int counts = productService.updateProductStatus(ids,status);
    	if(counts == 0 || counts != ids.split(",").length){
    		return failCreated(ResultCode.PRODUCT_WRONG_ID, null);
    	}
    	return successCreated();
    }
    
    /**
     * 根据ID查询商品详情
     * @param Long
     * @return
     */
    @RequestMapping(value = "selectProductById", method = RequestMethod.GET)
    public Result selectProductById(@RequestParam Long id){
    	if(id == null){
    		return failCreated(ResultCode.PRODUCT_WRONG_ID, null);
    	}
    	
    	//商品基本信息 
    	ProductInfoBO productBO = productService.selectProductById(id);
    	ProductInfoDTO productDTO = ProductConverter.convertInfoDTO(productBO);
    	
    	return successCreated(productDTO);
    }
    
    /**
     * 添加、编辑商品
     * @param id
     * @param product
     * @return
     */
    @RequestMapping(value = "saveProduct", method = RequestMethod.POST)
    public Result saveProduct(@RequestParam Long id ,@RequestBody EditProductParam product){
    	if(id == 0L){
    		//productService.saveProduct(product);
    	}
    	return null;
    }
    
    
}
