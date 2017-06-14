package com.lawu.eshop.merchant.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
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
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.MerchantApiConfig;
import com.lawu.eshop.merchant.api.service.AdService;
import com.lawu.eshop.merchant.api.service.MemberCountService;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import util.UploadFileUtil;
import util.VideoCutImgUtil;

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

    @Autowired
	private MerchantApiConfig merchantApiConfig;

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "添加广告", notes = "添加广告[1011|5000|5003|5010|6024|6026]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveAd", method = RequestMethod.POST)
    public Result saveAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "广告信息") AdParam adParam) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	if(adParam.getTypeEnum().val!=4){
    		if(!StringUtils.isNotEmpty(adParam.getBeginTime())){
    			return successCreated(ResultCode.AD_BEGIN_TIME_NOT_EXIST);
    		}
    	}
    	Result<PropertyinfoFreezeEnum> resultFreeze = propertyInfoService.getPropertyinfoFreeze(userNum);
    	if (isSuccess(resultFreeze)){
    		if(PropertyinfoFreezeEnum.YES.equals(resultFreeze.getModel())){
    			return successCreated(ResultCode.PROPERTYINFO_FREEZE_YES);
    		}
    	} else {
    		return successCreated(resultFreeze.getRet());
    	}
    	Result<PropertyPointDTO>  rs=propertyInfoService.getPropertyPoint(userNum);
    	PropertyPointDTO propertyPointDTO=rs.getModel();
    	if(adParam.getTotalPoint().intValue()>propertyPointDTO.getPoint().intValue()){
    		return successCreated(ResultCode.AD_POINT_NOT_ENOUGH);
    	}
    	String mediaUrl="";
    	String videoImgUrl="";
    	HttpServletRequest request = getRequest();
    	if(adParam.getTypeEnum().val==1 || adParam.getTypeEnum().val==3){ //平面投放
    		Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE, merchantApiConfig.getImageUploadUrl());
            if(!"".equals(retMap.get("imgUrl"))){
            	mediaUrl = retMap.get("imgUrl");
            }
    	}else if(adParam.getTypeEnum().val==2){//视频投放
    		Map<String, String> retMap = UploadFileUtil.uploadVideo(request, FileDirConstant.DIR_AD_VIDEO, merchantApiConfig.getVideoUploadUrl());
    		if(!"".equals(retMap.get("videoUrl"))){
            	mediaUrl = retMap.get("videoUrl");
            	//截取视频图片
            	String veido_path= merchantApiConfig.getVideoUploadUrl()+"/"+mediaUrl; //视频路径
            	
            	String ffmpegUrl=merchantApiConfig.getFfmpegUrl();  //ffmpeg安装路径
            	videoImgUrl=VideoCutImgUtil.processImg(veido_path,FileDirConstant.DIR_AD_VIDEO_IMAGE, merchantApiConfig.getImageUploadUrl(),ffmpegUrl);
            }
    	}
    	Integer count=0;
    	if(adParam.getPutWayEnum()!=null && adParam.getPutWayEnum().val==1){
    		String areas=adParam.getAreas();
    		if(areas==null || areas==""){
    			areas="ALL_PLACE";
    		}
    		count=memberCountService.findMemberCount(areas);
    	}else if(adParam.getPutWayEnum()!=null && adParam.getPutWayEnum().val==2){
    		count=memberCountService.findFensCount(merchantId);
    	}
    	Result<MerchantStoreDTO> storeRs=merchantStoreService.selectMerchantStoreByMId(merchantId);
    	AdSaveParam adSave=new AdSaveParam();
    	adSave.setAdParam(adParam);
    	if(isSuccess(storeRs)){
    		MerchantStoreDTO storeDTO= storeRs.getModel();
        	if(storeDTO!=null){
        		adSave.setLatitude(storeDTO.getLatitude());
            	adSave.setLongitude(storeDTO.getLongitude());
        	}
    	}
    	adSave.setCount(count);
    	adSave.setMediaUrl(mediaUrl);
    	adSave.setVideoImgUrl(videoImgUrl);
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
    	Result rsAd = adService.saveAd(adSave);
        return rsAd;
    }
    
    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告列表", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectListByMerchant", method = RequestMethod.GET)
    public Result<Page<AdMerchantDTO>> selectListByMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdMerchantParam adMerchantParam) {
    	Long memberId=UserUtil.getCurrentUserId(getRequest());
    	return adService.selectListByMerchant(adMerchantParam, memberId);
    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告操作下架", notes = "广告操作下架,[5001]（张荣成）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "unShelve/{id}", method = RequestMethod.PUT)
    public Result unShelve(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	return adService.updateStatus(id);
    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告操作删除", notes = "广告操作删除,[]（张荣成）", httpMethod = "DELETE")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.remove(id);
    	return successDelete();
    }

	@Audit(date = "2017-05-03", reviewer = "孙林青")
    @ApiOperation(value = "广告再次投放", notes = "广告再次投放,[]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "againPutAd/{id}", method = RequestMethod.POST)
    public Result againPutAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@PathVariable @ApiParam(required = true, value = "广告id") Long id,
    		@RequestParam @ApiParam(required = true, value = "广告投放时间") String beginTime) {
		Result<AdMerchantDetailDTO> rs= adService.selectById(id);
    	if(isSuccess(rs)){
    		Long merchantId = UserUtil.getCurrentUserId(getRequest());
        	String userNum = UserUtil.getCurrentUserNum(getRequest());
        	Result<PropertyPointDTO>  propertyPointRs=propertyInfoService.getPropertyPoint(userNum);
        	PropertyPointDTO propertyPointDTO=propertyPointRs.getModel();
        	AdMerchantDetailDTO adDTO=rs.getModel();
        	if(adDTO.getTotalPoint().intValue()>propertyPointDTO.getPoint().intValue()){
        		return successCreated(ResultCode.AD_POINT_NOT_ENOUGH);
        	}
        	Integer count=0;
        	if(adDTO.getPutWayEnum().val==1){
        		String areas=rs.getModel().getAreas();
        		if(areas==null){
        			areas="ALL_PLACE";
        		}
        		count=memberCountService.findMemberCount(areas);
        	}else if(adDTO.getPutWayEnum().val==2){
        		count=memberCountService.findFensCount(merchantId);
        	}
        	Result<MerchantStoreDTO> storeRs=merchantStoreService.selectMerchantStoreByMId(merchantId);
        	MerchantStoreDTO storeDTO= storeRs.getModel();
        	AdSaveParam adSave=new AdSaveParam();
        	AdParam adParam=new AdParam();
        	adParam.setTitle(adDTO.getTitle());
        	adParam.setAdCount(adDTO.getAdCount());
        	adParam.setAreas(adDTO.getAreas());
        	adParam.setBeginTime(beginTime);  
        	adParam.setContent(adDTO.getContent());
        	adParam.setPoint(adDTO.getPoint());
        	adParam.setTotalPoint(adDTO.getTotalPoint());
        	adParam.setPutWayEnum(adDTO.getPutWayEnum());
        	adParam.setRadius(adDTO.getRadius());
        	adParam.setTypeEnum(adDTO.getTypeEnum());
        	adSave.setAdParam(adParam);
        	adSave.setLatitude(storeDTO.getLatitude());
        	adSave.setLongitude(storeDTO.getLongitude());
        	adSave.setCount(count);
        	adSave.setMediaUrl(adDTO.getMediaUrl());
        	adSave.setVideoImgUrl(adDTO.getVideoImgUrl());
        	adSave.setMerchantId(merchantId);
        	adSave.setUserNum(userNum);
        	Result rsAd = adService.saveAd(adSave);
        	return rsAd;
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
	}

	@Audit(date = "2017-05-12", reviewer = "孙林青")
    @ApiOperation(value = "广告详情", notes = "广告详情,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectById/{id}", method = RequestMethod.GET)
    public Result<AdMerchantDetailDTO> selectById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	return adService.selectById(id);
    }

}
