package com.lawu.eshop.operator.api.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdPlatformOperatorDTO;
import com.lawu.eshop.ad.dto.OperatorAdDTO;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.param.OperatorAdParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.OperatorApiConfig;
import com.lawu.eshop.operator.api.service.AdPlatformService;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductPlatDTO;
import com.lawu.eshop.product.param.ProductParam;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStorePlatDTO;
import com.lawu.eshop.user.param.MerchantStorePlatParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 描述：广告管理
 * @author zhangrc
 * @date 2017/04/5
 */
@Api(tags = "adPlatform")
@RestController
@RequestMapping(value = "adPlatform/")
public class AdPlatformController extends BaseController {

    @Autowired
    private AdPlatformService adPlatformService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductAuditService productAuditService;
    
    
    @Autowired
    private MerchantStoreService merchantStoreService;
    
    @Autowired
    private AdService adService;
   

    @PageBody
    @ApiOperation(value = "广告信息查询", notes = "广告信息查询[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    @RequiresPermissions("adPlatForm:list")
    public Result<Page<AdPlatformOperatorDTO>> list(@RequestBody @ApiParam AdPlatformFindParam queryParams) {
    	if (queryParams.getTypeEnum().val == TypeEnum.TYPE_ALL.val) {
    		queryParams.setTypeEnum(null);
        }
    	if (queryParams.getPositionEnum().val == PositionEnum.TYPE_ALL.val) {
    		queryParams.setPositionEnum(null);
        }
    	Result<Page<AdPlatformOperatorDTO>> adPlatformDTOS = adPlatformService.selectList(queryParams);
        if(!isSuccess(adPlatformDTOS)){
        	return successCreated(adPlatformDTOS.getRet());
   	    }
        Page<AdPlatformOperatorDTO> page=adPlatformDTOS.getModel();
  		List<AdPlatformOperatorDTO> list= page.getRecords();
  		for (AdPlatformOperatorDTO adPlatformDTO : list) {
  			 if(adPlatformDTO.getProductId()!=null){
  				 Result<ProductEditInfoDTO>  productRs=productAuditService.selectEditProductById(adPlatformDTO.getProductId());
  	   			 if(isSuccess(productRs)){
  	   				adPlatformDTO.setRelateName(productRs.getModel().getName());
  	   			    adPlatformDTO.setMerchantName(productRs.getModel().getName());
  	   			 }
  			 }
  			if(adPlatformDTO.getMerchantStoreId()!=null){
  				Result<MerchantStoreDTO>  merchantRs=merchantStoreService.selectMerchantStore(adPlatformDTO.getMerchantStoreId());
  				if(isSuccess(merchantRs)){
  				  adPlatformDTO.setMerchantName(merchantRs.getModel().getName());
  				}
  			}
  			if(adPlatformDTO.getAdId()!=null){
  				Result<AdDTO>  adRs=adService.getAdById(adPlatformDTO.getAdId());
  				if(!isSuccess(adRs)){
  					return successCreated(adRs.getRet());
  				}
  				adPlatformDTO.setRelateName(adRs.getModel().getTitle());
 				adPlatformDTO.setMerchantName(adRs.getModel().getName());
 				if(adRs.getModel().getTypeEnum()!=AdTypeEnum.AD_TYPE_VIDEO){
 					if(!StringUtils.isEmpty(adPlatformDTO.getMediaUrl())){
 						adPlatformDTO.setMediaUrl(adPlatformDTO.getMediaUrl());
 					}else{
 						adPlatformDTO.setMediaUrl(adRs.getModel().getMediaUrl());
 					}
 					
 				}else{
 					adPlatformDTO.setMediaUrl(adRs.getModel().getVideoImgUrl());
 				}
  			}
  			 
		}
        return adPlatformDTOS;
    }

    
    @ApiOperation(value = "删除广告", notes = "删除广告[]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequiresPermissions("adPlatForm:del")
    @RequestMapping(value = "removeAdPlatform/{id}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        adPlatformService.removeAdPlatform(id);
        return successDelete();
    }
    
    
    @ApiOperation(value = "下架广告", notes = "下架广告[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequiresPermissions("adPlatForm:soldOut")
    @RequestMapping(value = "unShelve/{id}", method = RequestMethod.PUT)
    public Result unShelve(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        return adPlatformService.unShelve(id);
    }

    @ApiOperation(value = "上架广告", notes = "上架广告。（梅述全）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("adPlatForm:putAway")
    @RequestMapping(value = "onShelve/{id}", method = RequestMethod.PUT)
    public Result onShelve(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        return adPlatformService.onShelve(id);
    }
    
 
    
    @ApiOperation(value = "添加广告", notes = "添加广告[1011|1015|1010]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("adPlatForm:add")
    @RequestMapping(value = "saveAdPlatform", method = RequestMethod.POST)
    public Result saveAdPlatform(@ModelAttribute @ApiParam(required = true, value = "广告信息") AdPlatformParam adPlatform) {
        return adPlatformService.saveAdPlatform(adPlatform);
    }
    
    @ApiOperation(value = "修改广告", notes = "修改广告[1011|1015|1010]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("adPlatForm:edit")
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public Result update(@PathVariable @ApiParam(required = true, value = "广告id") Long id,
                         @ModelAttribute @ApiParam(required = true, value = "广告信息") AdPlatformParam adPlatform) {
        return adPlatformService.update(id, adPlatform);
    }

    
    @ApiOperation(value = "单个广告查询", notes = "单个广告查询[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequiresAuthentication
    @RequestMapping(value = "select/{id}", method = RequestMethod.GET)
    public Result<AdPlatformOperatorDTO> setPositon(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        return adPlatformService.select(id);
    }
    
    


    @ApiOperation(value = "查询所有商家的商品", notes = "查询所有商家的商品  [](张荣成)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "selectProductByPlat", method = RequestMethod.GET)
    public Result<List<ProductPlatDTO>> selectProductByPlat(@ModelAttribute @ApiParam ProductParam param) {
        return productService.selectProductByPlat(param);
    }
    

    @ApiOperation(value = "查询所有店铺", notes = "查询所有店铺  [](张荣成)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "selectAllMerchantStore", method = RequestMethod.GET)
    public Result<List<MerchantStorePlatDTO>> selectAllMerchantStore(@ModelAttribute @ApiParam MerchantStorePlatParam param) {
        return merchantStoreService.selectAllMerchantStore(param);
    }

    
    @ApiOperation(value = "广告列表", notes = "查询广告列表（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectOperatorAdAll", method = RequestMethod.GET)
    public Result<List<OperatorAdDTO>> selectOperatorAdAll(@ModelAttribute @ApiParam OperatorAdParam operatorAdParam) {
        Result<List<OperatorAdDTO>> result = adService.selectOperatorAdAll(operatorAdParam);
        
        if(!isSuccess(result)){
        	return successCreated(result.getRet());
        }
        return result;
    }

}
