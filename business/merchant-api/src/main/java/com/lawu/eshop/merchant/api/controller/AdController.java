package com.lawu.eshop.merchant.api.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.ClientTypeEnum;
import com.lawu.eshop.ad.constants.ManageTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.dto.AdDetailDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.AdSaveInfoDTO;
import com.lawu.eshop.ad.dto.IsMyDateDTO;
import com.lawu.eshop.ad.param.AdAgainPutParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.param.UserRedpacketValue;
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
import com.lawu.eshop.framework.web.util.HeaderUtil;
import com.lawu.eshop.mall.constants.MobileTypeEnum;
import com.lawu.eshop.merchant.api.MerchantApiConfig;
import com.lawu.eshop.merchant.api.service.AdCountCacheService;
import com.lawu.eshop.merchant.api.service.AdService;
import com.lawu.eshop.merchant.api.service.MemberCountService;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.product.dto.ProductRelateAdInfoDTO;
import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;
import com.lawu.eshop.property.dto.PropertyInfoFreezeDTO;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.user.dto.MerchantStoreAdInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;

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
	
	private static Logger logger = LoggerFactory.getLogger(AdController.class);

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

    private final static String ALL_PLACE="ALL_PLACE";
    
    @Autowired
   	private ProductService productService;

    @Autowired
   	private AdCountCacheService adCountCacheService;

    @Deprecated
    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "添加广告", notes = "添加广告[1011|5000|5003|5010|5011|5012|6024|6026]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveAd", method = RequestMethod.POST)
    public Result saveAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "广告信息") AdParam adParam) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
		if(adParam.getTypeEnum()!=AdTypeEnum.AD_TYPE_PACKET){
			if(StringUtils.isEmpty(adParam.getBeginTime()) || adParam.getBeginTime()==""){
				return successCreated(ResultCode.AD_BEGIN_TIME_NOT_EXIST);
			}
		}else{
			if(adParam.getAdCount()>1000000){
				return successCreated(ResultCode.AD_RED_PACKET_COUNT_ERROR);
			}
			if(adParam.getTotalPoint().divide(BigDecimal.valueOf(adParam.getAdCount())).compareTo(BigDecimal.valueOf(0.01))==-1){
				return successCreated(ResultCode.AD_RED_PACKET_POINT_ERROR);
			}
		}
    	Result<PropertyInfoFreezeDTO> resultFreeze = propertyInfoService.getPropertyinfoFreeze(userNum);
    	if (isSuccess(resultFreeze)){
    		if(PropertyinfoFreezeEnum.YES.equals(resultFreeze.getModel().getStatus())){
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
    	if(adParam.getTypeEnum()==AdTypeEnum.AD_TYPE_FLAT || adParam.getTypeEnum()==AdTypeEnum.AD_TYPE_PRAISE){ //平面投放
    		Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE, merchantApiConfig.getImageUploadUrl());
            if(!"".equals(retMap.get("imgUrl"))){
            	mediaUrl = retMap.get("imgUrl");
            }
    	}else if(adParam.getTypeEnum()==AdTypeEnum.AD_TYPE_VIDEO){//视频投放
	        Map<String, String> retMap = UploadFileUtil.uploadImageAndVideo(request, FileDirConstant.DIR_AD_IMAGE,FileDirConstant.DIR_AD_VIDEO, merchantApiConfig.getImageUploadUrl(),merchantApiConfig.getVideoUploadUrl());
	        if(!"".equals(retMap.get("videoUrl"))){
            	mediaUrl = retMap.get("videoUrl");
            }
	        if(!"".equals(retMap.get("imgUrl"))){
            	videoImgUrl = retMap.get("imgUrl");
            }
	        
	        if(StringUtils.isEmpty(videoImgUrl) || videoImgUrl == ""){ //商家没有上传图片，系统默认自动截取一张
            	String ffmpegUrl=merchantApiConfig.getFfmpegUrl();  //ffmpeg安装路径
            	String veido_path= merchantApiConfig.getVideoUploadUrl()+"/"+mediaUrl; //视频路径
            	//截取视频图片
            	videoImgUrl=VideoCutImgUtil.processImg(veido_path,FileDirConstant.DIR_AD_VIDEO_IMAGE, merchantApiConfig.getImageUploadUrl(),ffmpegUrl);
            }
    	}
    	Integer count=0;
    	if(adParam.getPutWayEnum()!=null && adParam.getPutWayEnum()==PutWayEnum.PUT_WAY_AREAS){
    		String areas=adParam.getAreas();
    		if(areas==null || areas==""){
    			areas=ALL_PLACE;
    		}
    		count=memberCountService.findMemberCount(areas);
    	}else if(adParam.getPutWayEnum()!=null && adParam.getPutWayEnum()==PutWayEnum.PUT_WAY_FENS){
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
            	adSave.setLogoUrl(storeDTO.getLogoUrl());
            	adSave.setManageType(ManageTypeEnum.getEnum(storeDTO.getManageType().val));
            	adSave.setMerchantStoreId(storeDTO.getMerchantStoreId());
            	adSave.setMerchantStoreName(storeDTO.getName());
        	}
    	}
    	adSave.setCount(count);
    	adSave.setMediaUrl(mediaUrl);
    	adSave.setVideoImgUrl(videoImgUrl);
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
        return adService.saveAd(adSave);
    }


	@Audit(date = "2017-08-08", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "添加广告(2.4)", notes = "添加广告[1011|5000|5003|5010|5011|5012|6024|6026]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveAdvert", method = RequestMethod.POST)
    public Result saveAdvert(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "广告信息") AdParam adParam) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	
    	if(adParam.getTypeEnum()!=AdTypeEnum.AD_TYPE_PACKET){
			if(StringUtils.isEmpty(adParam.getBeginTime()) || adParam.getBeginTime()==""){
				return successCreated(ResultCode.AD_BEGIN_TIME_NOT_EXIST);
			}
		}else{
			if(adParam.getTypeEnum()==AdTypeEnum.AD_TYPE_FLAT || adParam.getTypeEnum()==AdTypeEnum.AD_TYPE_VIDEO){
				
				if(adParam.getTotalPoint().compareTo(adParam.getPoint().multiply(BigDecimal.valueOf(adParam.getAdCount())))!=0){
					return successCreated(ResultCode.AD_RED_PACKET_POINT_ERROR);
				}
				
				//判断积分是否足够
				Result<PropertyInfoFreezeDTO> resultFreeze = propertyInfoService.getPropertyinfoFreeze(userNum);
		    	if (isSuccess(resultFreeze)){
		    		if(PropertyinfoFreezeEnum.YES.equals(resultFreeze.getModel().getStatus())){
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
			}
			if(adParam.getAdCount()>1000000){
				return successCreated(ResultCode.AD_RED_PACKET_COUNT_ERROR);
			}
			if(adParam.getTotalPoint().divide(new BigDecimal(adParam.getAdCount()), 4, RoundingMode.HALF_UP).compareTo(new BigDecimal(UserRedpacketValue.MIN_USERREDPACKET_COUNT))==-1){
				return successCreated(ResultCode.AD_RED_PACKET_POINT_ERROR);
			}
			
		}
    	
    	Integer count=0;
    	if(adParam.getTypeEnum()==AdTypeEnum.AD_TYPE_PRAISE){
    		
    		if(adParam.getPutWayEnum()!=null && adParam.getPutWayEnum()==PutWayEnum.PUT_WAY_AREAS){
    			String areas=adParam.getAreas();
        		if(areas==null || areas==""){
        			areas=ALL_PLACE;
        		}
    			count=memberCountService.findMemberCount(areas);
    			
    		}else if(adParam.getPutWayEnum()!=null && adParam.getPutWayEnum()==PutWayEnum.PUT_WAY_FENS){
        		
    			count=memberCountService.findFensCount(merchantId);
        	}
    		
    		count =(int)Math.ceil(count * (merchantApiConfig.getAdPraiseAllotProb()));
			
    		count = count>10?count:10;
    		
    		if(adParam.getTotalPoint().divide(new BigDecimal(count), 2, RoundingMode.HALF_UP).compareTo(new BigDecimal(UserRedpacketValue.MIN_USERREDPACKET_COUNT))==-1){
				return successCreated(ResultCode.AD_RED_PACKET_POINT_ERROR);
			}
    		adParam.setAdCount(count);
    		
    	}
    	
    	Result<MerchantStoreAdInfoDTO> storeRs=merchantStoreService.selectMerchantStoreAdInfo(merchantId);
    	AdSaveParam adSave=new AdSaveParam();
    	adSave.setAdParam(adParam);
    	if(!isSuccess(storeRs)){
    		 return successCreated(storeRs.getRet());
    	}
    	String  platform = HeaderUtil.getRequestPlatform(getRequest());
		
		if(platform==""){
			return successCreated(ResultCode.GET_HEADER_ERROR);
		}
		if(Byte.valueOf(platform)==MobileTypeEnum.Android.val || Byte.valueOf(platform)==MobileTypeEnum.IOS.val){
			adSave.setClentType(ClientTypeEnum.MOBLIE);
		}else{
			adSave.setClentType(ClientTypeEnum.PC);
		}
    	MerchantStoreAdInfoDTO storeDTO= storeRs.getModel();
    	if(storeDTO!=null){
    		adSave.setLatitude(storeDTO.getLatitude());
        	adSave.setLongitude(storeDTO.getLongitude());
            adSave.setLogoUrl(StringUtils.isEmpty(storeDTO.getLogoUrl()) ? merchantApiConfig.getDefaultHeadimg() : storeDTO.getLogoUrl());
            if(storeDTO.getManageType()!=null){
        		adSave.setManageType(ManageTypeEnum.getEnum(storeDTO.getManageType().val));
        	}
        	adSave.setMerchantStoreId(storeDTO.getMerchantStoreId());
            adSave.setMerchantStoreName(StringUtils.isEmpty(storeDTO.getName()) ? "E店商家" : storeDTO.getName());
            adSave.setMerchantRegionPath(storeDTO.getRegionPath());
    	}
    	adSave.setCount(count);
    	adSave.setMediaUrl(adParam.getMediaUrl());
    	adSave.setVideoImgUrl(adParam.getVideoImgUrl());
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
    	Result<AdSaveInfoDTO> result = adService.saveAd(adSave);

    	//将广告总数存入缓存
		if(isSuccess(result) && result.getModel().getId()>0){
		     adCountCacheService.setAdCountRecord(Long.parseLong(String.valueOf(result.getModel().getId())), result.getModel().getAdCount());
		}
        return result;
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
    	Long merchantId=UserUtil.getCurrentUserId(getRequest());
    	Result<IsMyDateDTO> result=adService.isMyData(id, merchantId);
    	if(isSuccess(result)){
    		if(result.getModel().isFlag()){
    			return adService.updateStatus(id);
    		}else{
    			return successCreated(ResultCode.NOT_FOUND_DATA);
    		}
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}

    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "广告操作删除", notes = "广告操作删除,[]（张荣成）", httpMethod = "DELETE")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Long merchantId=UserUtil.getCurrentUserId(getRequest());
    	Result<IsMyDateDTO> result=adService.isMyData(id, merchantId);
    	if(isSuccess(result)){
    		if(result.getModel().isFlag()){
    			adService.remove(id);
    			return successDelete();
    		}else{
    			return successCreated(ResultCode.NOT_FOUND_DATA);
    		}
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}


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
    	if(!isSuccess(rs)){
    		return successCreated(rs.getRet());
    	}
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
    			areas=ALL_PLACE;
    		}
    		count=memberCountService.findMemberCount(areas);
    	}else if(adDTO.getPutWayEnum().val==2){
    		count=memberCountService.findFensCount(merchantId);
    	}
    	Result<MerchantStoreAdInfoDTO> storeRs=merchantStoreService.selectMerchantStoreAdInfo(merchantId);
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
    	adParam.setRelateId(adDTO.getProductId());
    	adParam.setRelateType(adDTO.getRelateType());
    	adParam.setRegionName(adDTO.getRegionName());
    	adParam.setFileSize(adDTO.getFileSize());
    	adParam.setFileTime(adDTO.getVideoTime());
    	adSave.setAdParam(adParam);
    	if(isSuccess(storeRs)){
    		MerchantStoreAdInfoDTO storeDTO= storeRs.getModel();
        	if(storeDTO!=null){
        		adSave.setLatitude(storeDTO.getLatitude());
            	adSave.setLongitude(storeDTO.getLongitude());
                adSave.setLogoUrl(StringUtils.isEmpty(storeDTO.getLogoUrl()) ? merchantApiConfig.getDefaultHeadimg() : storeDTO.getLogoUrl());
                if(storeDTO.getManageType()!=null){
            		adSave.setManageType(ManageTypeEnum.getEnum(storeDTO.getManageType().val));
            	}
            	adSave.setMerchantStoreId(storeDTO.getMerchantStoreId());
                adSave.setMerchantStoreName(StringUtils.isEmpty(storeDTO.getName()) ? "E店商家" : storeDTO.getName());
                adSave.setMerchantRegionPath(storeDTO.getRegionPath());
        	}
    	}
    	String  platform = HeaderUtil.getRequestPlatform(getRequest());
    	if(platform==""){
			return successCreated(ResultCode.GET_HEADER_ERROR);
		}
		if(Byte.valueOf(platform)==MobileTypeEnum.Android.val || Byte.valueOf(platform)==MobileTypeEnum.IOS.val){
			adSave.setClentType(ClientTypeEnum.MOBLIE);
		}else{
			adSave.setClentType(ClientTypeEnum.PC);
		}
    	adSave.setCount(count);
    	adSave.setMediaUrl(adDTO.getMediaUrl());
    	adSave.setVideoImgUrl(adDTO.getVideoImgUrl());
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
    	Result<AdSaveInfoDTO> result = adService.saveAd(adSave);

    	//将广告总数存入缓存
		if(isSuccess(result) && result.getModel().getId()>0){
		     adCountCacheService.setAdCountRecord(Long.parseLong(String.valueOf(result.getModel().getId())), result.getModel().getAdCount());
		}
    	return result;
	}

	@Audit(date = "2017-05-12", reviewer = "孙林青")
    @ApiOperation(value = "广告详情(再次投放回显)", notes = "广告详情,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectById/{id}", method = RequestMethod.GET)
    public Result<AdMerchantDetailDTO> selectById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
		Result<AdMerchantDetailDTO> result = adService.selectById(id);
		if(!isSuccess(result)){
			return successCreated(result.getRet());
		}
		if(result.getModel().getProductId()!=null && result.getModel().getProductId() > 0){
			Result<ProductRelateAdInfoDTO>  proResult = productService.selectProductRelateAdInfo(result.getModel().getProductId());
			if(!isSuccess(proResult)){
				return successCreated(proResult.getRet());
			}
			result.getModel().setProductImgUrl(proResult.getModel().getImgUrl());
			result.getModel().setProductName(proResult.getModel().getName());
		}
		return result;
    }

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-07-04", reviewer = "孙林青")
	@ApiOperation(value = "广告批量删除", notes = "广告批量删除,[]（张荣成）", httpMethod = "DELETE")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "batchDeleteAd", method = RequestMethod.DELETE)
	public Result batchDeleteAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@RequestParam @ApiParam(required = true, value = "广告ids,中间以“/”隔开  如100/101") String ids) {
		String[] strIds = ids.split("/");
		List<Long> adIds = new ArrayList<>();
		for (String str : strIds) {
			adIds.add(Long.valueOf(str));
		}
		Long merchantId=UserUtil.getCurrentUserId(getRequest());
		Result rs = adService.batchDeleteAd(adIds,merchantId);
		return successDelete();
	}


	@Deprecated
	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-07-04", reviewer = "孙林青")
    @ApiOperation(value = "广告再次投放(2.2.0)", notes = "广告再次投放,[1011|5000|5003|5010|6024|6026](张荣成)", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "againSaveAd/{id}", method = RequestMethod.POST)
    public Result againSaveAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@PathVariable @ApiParam(required = true, value = "广告id") Long id,
    		@ModelAttribute @ApiParam(required = true, value = "广告信息") AdAgainPutParam adAgainParam) {
		Result<AdMerchantDetailDTO> result= adService.selectById(id);
    	if(!isSuccess(result)){
    		return successCreated(result.getRet());
    	}
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	if(StringUtils.isEmpty(adAgainParam.getBeginTime())){
			return successCreated(ResultCode.AD_BEGIN_TIME_NOT_EXIST);
		}
    	Result<PropertyInfoFreezeDTO> resultFreeze = propertyInfoService.getPropertyinfoFreeze(userNum);
    	if (isSuccess(resultFreeze)){
    		if(PropertyinfoFreezeEnum.YES.equals(resultFreeze.getModel())){
    			return successCreated(ResultCode.PROPERTYINFO_FREEZE_YES);
    		}
    	} else {
    		return successCreated(resultFreeze.getRet());
    	}
    	Result<PropertyPointDTO>  rs=propertyInfoService.getPropertyPoint(userNum);
    	PropertyPointDTO propertyPointDTO=rs.getModel();
    	if(adAgainParam.getTotalPoint().intValue()>propertyPointDTO.getPoint().intValue()){
    		return successCreated(ResultCode.AD_POINT_NOT_ENOUGH);
    	}
    	String mediaUrl="";
    	String videoImgUrl="";
    	HttpServletRequest request = getRequest();
    	if(result.getModel().getTypeEnum()==AdTypeEnum.AD_TYPE_FLAT || result.getModel().getTypeEnum()==AdTypeEnum.AD_TYPE_PRAISE){ //平面投放
    		Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE, merchantApiConfig.getImageUploadUrl());
            if(!"".equals(retMap.get("imgUrl"))){
            	mediaUrl = retMap.get("imgUrl");
            }
    	}else if(result.getModel().getTypeEnum()==AdTypeEnum.AD_TYPE_VIDEO){//视频投放
	        Map<String, String> retMap = UploadFileUtil.uploadImageAndVideo(request, FileDirConstant.DIR_AD_IMAGE,FileDirConstant.DIR_AD_VIDEO, merchantApiConfig.getImageUploadUrl(),merchantApiConfig.getVideoUploadUrl());
	        if(!"".equals(retMap.get("videoUrl"))){
            	mediaUrl = retMap.get("videoUrl");
            }
	        if(!"".equals(retMap.get("imgUrl"))){
            	videoImgUrl = retMap.get("imgUrl");
            }
	        
	        if(StringUtils.isEmpty(videoImgUrl) || videoImgUrl == ""){ //商家没有上传图片，系统默认自动截取一张
            	String ffmpegUrl=merchantApiConfig.getFfmpegUrl();  //ffmpeg安装路径
            	String veido_path= merchantApiConfig.getVideoUploadUrl()+"/"+mediaUrl; //视频路径
            	//截取视频图片
            	videoImgUrl=VideoCutImgUtil.processImg(veido_path,FileDirConstant.DIR_AD_VIDEO_IMAGE, merchantApiConfig.getImageUploadUrl(),ffmpegUrl);
            }
    	}
    	Integer count=0;
    	if(adAgainParam.getPutWayEnum()!=null && adAgainParam.getPutWayEnum()==PutWayEnum.PUT_WAY_AREAS){
    		String areas=adAgainParam.getAreas();
    		if(areas==null || areas==""){
    			areas=ALL_PLACE;
    		}
    		count=memberCountService.findMemberCount(areas);
    	}else if(adAgainParam.getPutWayEnum()!=null && adAgainParam.getPutWayEnum()==PutWayEnum.PUT_WAY_FENS){
    		count=memberCountService.findFensCount(merchantId);
    	}
    	Result<MerchantStoreAdInfoDTO> storeRs=merchantStoreService.selectMerchantStoreAdInfo(merchantId);
    	AdSaveParam adSave=new AdSaveParam();
    	AdParam adParam=new AdParam();
    	adParam.setTitle(adAgainParam.getTitle());
    	adParam.setAdCount(adAgainParam.getAdCount());
    	adParam.setAreas(adAgainParam.getAreas());
    	adParam.setBeginTime(adAgainParam.getBeginTime());
    	adParam.setContent(adAgainParam.getContent());
    	adParam.setPoint(adAgainParam.getPoint());
    	adParam.setTotalPoint(adAgainParam.getTotalPoint());
    	adParam.setPutWayEnum(adAgainParam.getPutWayEnum());
    	adParam.setRadius(adAgainParam.getRadius());
    	adParam.setTypeEnum(result.getModel().getTypeEnum());
    	adParam.setRegionName(adAgainParam.getRegionName());
    	adSave.setAdParam(adParam);
    	if(isSuccess(storeRs)){
    		MerchantStoreAdInfoDTO storeDTO= storeRs.getModel();
        	if(storeDTO!=null){
        		adSave.setLatitude(storeDTO.getLatitude());
            	adSave.setLongitude(storeDTO.getLongitude());
            	adSave.setLogoUrl(storeDTO.getLogoUrl());
            	adSave.setManageType(ManageTypeEnum.getEnum(storeDTO.getManageType().val));
            	adSave.setMerchantStoreId(storeDTO.getMerchantStoreId());
            	adSave.setMerchantStoreName(storeDTO.getName());
        	}
    	}
    	adSave.setCount(count);
    	adSave.setMediaUrl(mediaUrl);
    	adSave.setVideoImgUrl(videoImgUrl);
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
    	Result rsAd = adService.saveAd(adSave);
    	if (!isSuccess(rsAd)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
	}

	@Audit(date = "2017-07-04", reviewer = "孙林青")
    @ApiOperation(value = "广告详情", notes = "广告详情,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectDetail/{id}", method = RequestMethod.GET)
    public Result<AdDetailDTO> selectDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
		Result<AdDetailDTO> result = adService.selectDetail(id);
		if(!isSuccess(result)){
			return successCreated(result.getRet());
		}
		if (result.getModel().getProductId() != null && result.getModel().getProductId() > 0) {
			Result<ProductRelateAdInfoDTO>  proResult = productService.selectProductRelateAdInfo(result.getModel().getProductId());
			if(!isSuccess(proResult)){
				return successCreated(proResult.getRet());
			}
			result.getModel().setProductImgUrl(proResult.getModel().getImgUrl());
			result.getModel().setProductName(proResult.getModel().getName());
		}
		
		return result;
    }


	@Deprecated
	@Audit(date = "2017-08-08", reviewer = "孙林青")
	@ApiOperation(value = "广告再次投放(2.4)", notes = "广告再次投放,[1011|5000|5003|5010|6024|6026](张荣成)", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "againPutAdvert/{id}", method = RequestMethod.POST)
    public Result againPutAdvert(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@PathVariable @ApiParam(required = true, value = "广告id") Long id,
    		@ModelAttribute @ApiParam(required = true, value = "广告信息") AdAgainPutParam adAgainParam) {
		Result<AdMerchantDetailDTO> result= adService.selectById(id);
    	if(!isSuccess(result)){
    		return successCreated(result.getRet());
    	}
		Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	if(StringUtils.isEmpty(adAgainParam.getBeginTime())){
			return successCreated(ResultCode.AD_BEGIN_TIME_NOT_EXIST);
		}
    	Result<PropertyInfoFreezeDTO> resultFreeze = propertyInfoService.getPropertyinfoFreeze(userNum);
    	if (isSuccess(resultFreeze)){
    		if(PropertyinfoFreezeEnum.YES.equals(resultFreeze.getModel())){
    			return successCreated(ResultCode.PROPERTYINFO_FREEZE_YES);
    		}
    	} else {
    		return successCreated(resultFreeze.getRet());
    	}
    	Result<PropertyPointDTO>  rs=propertyInfoService.getPropertyPoint(userNum);
    	PropertyPointDTO propertyPointDTO=rs.getModel();
    	if(adAgainParam.getTotalPoint().intValue()>propertyPointDTO.getPoint().intValue()){
    		return successCreated(ResultCode.AD_POINT_NOT_ENOUGH);
    	}
    	Integer count=0;
    	if(adAgainParam.getPutWayEnum()!=null && adAgainParam.getPutWayEnum()==PutWayEnum.PUT_WAY_AREAS){
    		String areas=adAgainParam.getAreas();
    		if(areas==null || areas==""){
    			areas=ALL_PLACE;
    		}
    		count=memberCountService.findMemberCount(areas);
    	}else if(adAgainParam.getPutWayEnum()!=null && adAgainParam.getPutWayEnum()==PutWayEnum.PUT_WAY_FENS){
    		count=memberCountService.findFensCount(merchantId);
    	}
    	Result<MerchantStoreAdInfoDTO> storeRs=merchantStoreService.selectMerchantStoreAdInfo(merchantId);
    	AdSaveParam adSave=new AdSaveParam();
    	AdParam adParam=new AdParam();
    	adParam.setTitle(adAgainParam.getTitle());
    	adParam.setAdCount(adAgainParam.getAdCount());
    	adParam.setAreas(adAgainParam.getAreas());
    	adParam.setBeginTime(adAgainParam.getBeginTime());
    	adParam.setContent(adAgainParam.getContent());
    	adParam.setPoint(adAgainParam.getPoint());
    	adParam.setTotalPoint(adAgainParam.getTotalPoint());
    	adParam.setPutWayEnum(adAgainParam.getPutWayEnum());
    	adParam.setRadius(adAgainParam.getRadius());
    	adParam.setTypeEnum(result.getModel().getTypeEnum());
		adParam.setRelateType(adAgainParam.getRelateType());
    	adParam.setRegionName(adAgainParam.getRegionName());
    	adSave.setAdParam(adParam);
    	if(isSuccess(storeRs)){
    		MerchantStoreAdInfoDTO storeDTO= storeRs.getModel();
        	if(storeDTO!=null){
        		adSave.setLatitude(storeDTO.getLatitude());
            	adSave.setLongitude(storeDTO.getLongitude());
				adSave.setLogoUrl(StringUtils.isEmpty(storeDTO.getLogoUrl()) ? merchantApiConfig.getDefaultHeadimg() : storeDTO.getLogoUrl());
            	adSave.setManageType(ManageTypeEnum.getEnum(storeDTO.getManageType().val));
            	adSave.setMerchantStoreId(storeDTO.getMerchantStoreId());
				adSave.setMerchantStoreName(StringUtils.isEmpty(storeDTO.getName()) ? "E店商家" : storeDTO.getName());
            	adSave.setMerchantRegionPath(storeDTO.getRegionPath());
        	}
    	}
    	adSave.setCount(count);
    	adSave.setMediaUrl(adAgainParam.getMediaUrl());
    	adSave.setVideoImgUrl(adAgainParam.getVideoImgUrl());
    	adSave.setMerchantId(merchantId);
    	adSave.setUserNum(userNum);
    	Result rsAd = adService.saveAd(adSave);
    	if (!isSuccess(rsAd)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
	}

	@Audit(date = "2017-10-20", reviewer = "杨清华")
    @ApiOperation(value = "广告是否支付成功", notes = "广告是否支付成功,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "isPay/{id}", method = RequestMethod.GET)
    public Result<Boolean> isPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
		return adService.isPay(id);
    }

}
