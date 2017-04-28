package com.lawu.eshop.operator.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.operator.api.service.AdPlatformService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductPlatDTO;
import com.lawu.eshop.product.param.ProductParam;
import com.lawu.eshop.user.dto.MerchantStorePlatDTO;
import com.lawu.eshop.user.param.MerchantStorePlatParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import util.UploadFileUtil;

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
    private MerchantStoreService merchantStoreService;

    @PageBody
    @ApiOperation(value = "广告信息查询", notes = "广告信息查询[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    public Result<Page<AdPlatformDTO>> selectByPosition(@ModelAttribute @ApiParam( value = "查询信息") AdPlatformFindParam queryParams) {
        Result<Page<AdPlatformDTO>> adPlatformDTOS = adPlatformService.selectList(queryParams);
        return adPlatformDTOS;
    }

    
    @ApiOperation(value = "删除广告", notes = "删除广告[]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "removeAdPlatform/{id}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adPlatformService.removeAdPlatform(id);
        return successDelete();
    }
    
    
    @ApiOperation(value = "发布广告", notes = "发布广告[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "issueAd/{id}", method = RequestMethod.PUT)
    public Result issueAd(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adPlatformService.issueAd(id);
        return rs;
    }
    
    @ApiOperation(value = "下架广告", notes = "下架广告[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "unShelve/{id}", method = RequestMethod.PUT)
    public Result unShelve(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adPlatformService.unShelve(id);
        return rs;
    }
    
    
    @ApiOperation(value = "设置广告位", notes = "设置广告位[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "setPosition/{id}", method = RequestMethod.PUT)
    public Result setPositon(@PathVariable @ApiParam(required = true, value = "广告id") Long id,@ModelAttribute @ApiParam(required = true, value = "位置") PositionEnum positionEnum) {
        Result rs = adPlatformService.setPosition(id,positionEnum);
        return rs;
    }
 
    
    @ApiOperation(value = "添加广告", notes = "添加广告[1011|1015|1010]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveAdPlatform", method = RequestMethod.POST)
    public Result saveAdPlatform(@ModelAttribute @ApiParam(required = true, value = "广告信息") AdPlatformParam adPlatform) {
    	 HttpServletRequest request = getRequest();
    	 String mediaUrl = "";
         Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE);
         if(!"".equals(retMap.get("imgUrl"))){
        	 mediaUrl = retMap.get("imgUrl").toString();
         }
        Result rs = adPlatformService.saveAdPlatform(adPlatform,mediaUrl);
        return rs;
    }
    
    @ApiOperation(value = "修改广告", notes = "修改广告[1011|1015|1010]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable @ApiParam(required = true, value = "广告id") Long id,@ModelAttribute @ApiParam(required = true, value = "广告信息") AdPlatformParam adPlatform) {
    	 HttpServletRequest request = getRequest();
    	 String mediaUrl = "";
         Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE);
         if(!"".equals(retMap.get("imgUrl"))){
        	 mediaUrl = retMap.get("imgUrl").toString();
         }
        Result rs = adPlatformService.update(id, adPlatform, mediaUrl);
        return rs;
    }

    
    @ApiOperation(value = "单个广告查询", notes = "单个广告查询[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "select/{id}", method = RequestMethod.GET)
    public Result<AdPlatformDTO> setPositon(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adPlatformService.select(id);
        return rs;
    }
    
    


    @ApiOperation(value = "查询所有商家的商品", notes = "查询所有商家的商品  [](张荣成)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "selectProductByPlat", method = RequestMethod.GET)
    public Result<List<ProductPlatDTO>> selectProductByPlat(@ModelAttribute @ApiParam ProductParam param) {
    	Result<List<ProductPlatDTO>> rs=productService.selectProductByPlat(param);
    	return rs;
    }
    
    

    @ApiOperation(value = "查询所有店铺", notes = "查询所有店铺  [](张荣成)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "selectAllMerchantStore", method = RequestMethod.GET)
    public Result<List<MerchantStorePlatDTO>> selectAllMerchantStore(@ModelAttribute @ApiParam MerchantStorePlatParam param) {
    	return merchantStoreService.selectAllMerchantStore(param);
    }

}
