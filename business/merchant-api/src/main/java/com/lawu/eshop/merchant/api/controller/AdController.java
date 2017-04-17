package com.lawu.eshop.merchant.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdSolrAddParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.AdService;
import com.lawu.eshop.merchant.api.service.MemberCountService;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;

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
@Api(tags = "ad")
@RestController
@RequestMapping(value = "ad/")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;
    
    @Autowired
    private PropertyInfoService propertyInfoService;
    
    @Autowired
    private MemberCountService memberCountService;

    
    @Autowired
    private MerchantStoreService merchantStoreService;
 


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "添加广告", notes = "添加广告[5000]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveAd", method = RequestMethod.POST)
    public Result saveAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "广告信息") AdParam adParam) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	Result<PropertyPointDTO>  rs=propertyInfoService.getPropertyPoint(userNum);
    	PropertyPointDTO propertyPointDTO=rs.getModel();
    	if(adParam.getTotalPoint().intValue()>propertyPointDTO.getPoint().intValue()){
    		return successCreated(ResultCode.AD_POINT_NOT_ENOUGH);
    	}
    	String mediaUrl="";
    	HttpServletRequest request = getRequest();
    	if(adParam.getPutWayEnum().val==1){ //平面投放
    		Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE);
            if(!"".equals(retMap.get("imgUrl"))){
            	mediaUrl = retMap.get("imgUrl").toString();
            }
    	}else{//视频投放
    		Map<String, String> retMap = UploadFileUtil.uploadVideo(request, FileDirConstant.DIR_AD_VIDEO);
    		if(!"".equals(retMap.get("videoUrl"))){
            	mediaUrl = retMap.get("videoUrl").toString();
            }
    	}
    	List<Long> fens=new ArrayList<>();
    	Integer count=0;
    	if(adParam.getPutWayEnum().val==1){
    		count=memberCountService.findMemberCount(adParam.getAreas());
    	}else if(adParam.getPutWayEnum().val==2){
    		count=memberCountService.findFensCount(merchantId);
    	}
    	Result<MerchantStoreDTO> storeRs=merchantStoreService.selectMerchantStore(merchantId);
    	MerchantStoreDTO storeDTO= storeRs.getModel();
    	AdSaveParam adSave=new AdSaveParam();
    	adSave.setAdParam(adParam);
    	adSave.setLatitude(storeDTO.getLatitude());
    	adSave.setLongitude(storeDTO.getLongitude());
    	adSave.setCount(count);
    	adSave.setMediaUrl(mediaUrl);
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
    	Result rsAd = adService.saveAd(adSave);
        return rsAd;
    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告列表", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectListByMerchant", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdMerchantParam adMerchantParam) {
    	Long memberId=UserUtil.getCurrentUserId(getRequest());
    	Result<Page<AdDTO>>  pageDTOS=adService.selectListByMerchant(adMerchantParam, memberId);
    	return pageDTOS;
    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告操作下架", notes = "广告操作下架,[5001]（张荣成）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "unShelve/{id}", method = RequestMethod.PUT)
    public Result unShelve(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.updateStatus(id);
    	return rs;
    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告操作删除", notes = "广告操作删除,[]（张荣成）", httpMethod = "DELETE")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.remove(id);
    	return rs;
    }


}
