package com.lawu.eshop.merchant.api.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditDataProductParam;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.query.ProductQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Yangqh
 * @date 2017/3/11
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "分页查询商品", notes = "分页查询商品，[201|400]。(杨清华)", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "selectProduct", method = RequestMethod.POST)
    public Result selectProduct(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    							ProductStatusEnum productStatus,
    					        @ModelAttribute @ApiParam ProductQuery query) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	ProductDataQuery queryData = new ProductDataQuery();
    	queryData.setStatus(productStatus);
    	queryData.setMerchantId(merchantId);
    	queryData.setName(query.getName());
    	Result<Page<ProductQueryDTO>> page = productService.selectProduct(queryData);
        return successGet(page);
    }
    
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "商品批量处理", notes = "商品批量处理，[1000|1002]。(杨清华)", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.POST)
    public Result updateProductStatus(@RequestParam @ApiParam(required = true, value = "商家ID(多个英文逗号分开)") String ids,
    								  ProductStatusEnum productStatus) {
    	
    	return productService.updateProductStatus(ids,productStatus);
    }
    
    @ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[1000|1002|1003]，（杨清华）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {
    	
    	return productService.selectEditProductById(productId);
    	
    }
    
    @SuppressWarnings({ "rawtypes", "unused" })
	@ApiOperation(value = "添加、编辑商品", notes = "添加、编辑商品接口，[]，（杨清华）", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "saveProduct", method = RequestMethod.POST)
    public Result saveProduct(@ModelAttribute @ApiParam EditProductParam product,HttpServletRequest request) {
    	
    	StringBuffer iamgeSb = new StringBuffer();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            Iterator<String> iter = multiRequest.getFileNames();
            if(iter.hasNext()){
            	while(iter.hasNext()){
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if(file != null){
                        String originalFilename = file.getOriginalFilename();
                        String fieldName = file.getName();
                    	String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
                    	prefix = prefix.toLowerCase();
                    	Map<String, String> retMap = null;//uploadService.uploadOnePic(request, file, FileDirConstant.DIR_PRODUCT);
                    	String resultFlag = retMap.get("resultFlag");
                    	if(!"0".equals(resultFlag)){
                    		return successCreated(resultFlag);	
                    	}
                    	
                    }
                }
            }
        }
        
        
    	EditDataProductParam dataParam = new EditDataProductParam();
    	
    	return productService.saveProduct(dataParam);
    	
    }
}
