package com.lawu.eshop.merchant.api.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.query.ProductQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import util.UploadFileUtil;

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
	@ApiOperation(value = "商品批量处理", notes = "商品批量处理，[1002]。(杨清华)", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.POST)
    public Result updateProductStatus(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    							      @RequestParam @ApiParam(required = true, value = "商家ID(多个英文逗号分开)") String ids,
    								  ProductStatusEnum productStatus) {
    	
    	return productService.updateProductStatus(ids,productStatus);
    }
    
    @ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[1002|1003]，（杨清华）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    													    @RequestParam @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {
    	
    	return productService.selectEditProductById(productId);
    	
    }
    
    @SuppressWarnings({ "rawtypes" })
	@ApiOperation(value = "添加、编辑商品", notes = "添加、编辑商品接口，[1017|3000]，（杨清华）", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "saveProduct/{productId}", method = RequestMethod.POST)
    public Result saveProduct(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    						  @PathVariable @ApiParam(required = true, value = "商品ID(新增时传0)") Long productId, 
    						  @ModelAttribute @ApiParam EditProductParam product,MultipartFile file1) {
    	
    	StringBuffer featureImageStr = new StringBuffer();
    	StringBuffer productImageStr = new StringBuffer();
    	StringBuffer productDetailImageStr = new StringBuffer();
    	HttpServletRequest request = getRequest();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if((productId == null || productId == 0L || productId < 0) && !multipartResolver.isMultipart(request)){
			return successCreated(ResultCode.IMAGE_IS_NULL);
		}
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            Iterator<String> iter = multiRequest.getFileNames();
        	while(iter.hasNext()){
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    String originalFilename = file.getOriginalFilename();
                    String fieldName = file.getName();
                	String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
                	prefix = prefix.toLowerCase();
                	Map<String, String> retMap =  UploadFileUtil.uploadOnePic(request, file, FileDirConstant.DIR_PRODUCT);
                	String resultFlag = retMap.get("resultFlag");
                	if(!"0".equals(resultFlag)){
                		return successCreated(resultFlag);	
                	}
                	
                	String imgUrl = retMap.get("imgUrl");
                	if(fieldName.contains("featureImage")){
                		featureImageStr.append(imgUrl).append(",");
                	}else if(fieldName.contains("productIamge")){
                		productImageStr.append(imgUrl).append(",");
                	}else if(fieldName.contains("productDetailImage")){
                		productDetailImageStr.append(imgUrl).append(",");
                	}
                }
            }
        }
        String featureImage = "";
        String productImage = "";
        String productDetailImage = "";
        if(productId == null || productId == 0L || productId < 0){
        	featureImage = featureImageStr.toString();
        	productImage = productImageStr.toString();
        	productDetailImage = productDetailImageStr.toString();
        	if("".equals(productImage)){
        		return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
        	}
        	if("".equals(productDetailImage)){
        		return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL);
        	}
        	
        	if("".equals(featureImage)){
        		featureImage = productImage.split(",")[0];
        	}
        }else{
        	if("".equals(product.getBackProductImageUrls()) && "".equals(productImage)){
        		return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_HEAD);
        	}
        	if("".equals(product.getBackProductDetailImageUrls()) && "".equals(productDetailImage)){
        		return successCreated(ResultCode.IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL);
        	}
        	
        	if(!"".equals(product.getBackProductImageUrls())){
        		productImage = product.getBackProductImageUrls() + "," + productImage;
        	}
        	if(!"".equals(product.getBackProductDetailImageUrls())){
        		productDetailImage = product.getBackProductDetailImageUrls() + "," + productDetailImage;
        	}
        }
    	productImage = productImage.substring(0, productImage.lastIndexOf(","));
    	productDetailImage = productDetailImage.substring(0, productDetailImage.lastIndexOf(","));
        
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	
    	EditProductDataParam dataProduct = new EditProductDataParam();
    	dataProduct.setMerchantId(merchantId);
    	dataProduct.setName(product.getName());
    	dataProduct.setCategoryId(product.getCategoryId());
    	dataProduct.setContent(product.getContent());
    	dataProduct.setSpec(product.getSpec());
    	dataProduct.setFeatureImage(featureImage);
    	dataProduct.setProductImage(productImage);
    	dataProduct.setBackProductDetailImageUrls(productDetailImage);
    	
    	return productService.saveProduct(productId,dataProduct);
    	
    }
    
    
}
