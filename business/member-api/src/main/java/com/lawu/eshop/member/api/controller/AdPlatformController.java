package com.lawu.eshop.member.api.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.AdPlatformFlatTypeEnum;
import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.dto.AdPlatformFlatDTO;
import com.lawu.eshop.ad.dto.AdPlatformVideoDTO;
import com.lawu.eshop.ad.param.AdPlatformFindEginParam;
import com.lawu.eshop.ad.param.AdPlatformInternalParam;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AdPlatformService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.AdQueryMemberInfoDTO;

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
    private MemberService memberService;

    /**
     * 根据位置查询广告
     * @param positionEnum  
     * @return
     */
    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "广告信息查询 (banner)", notes = "广告信息查询[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectByPosition", method = RequestMethod.GET)
    public Result<List<AdPlatformDTO>> selectByPosition(@ModelAttribute @ApiParam(required = true, value = "POSITON_RECOMMEND 人气推荐 POSITON_SHOP_TOP 要购物顶部广告 POSITON_SHOP_CHOOSE"
			+ "要购物今日推荐  POSITON_SHOP_GOODS 要购物精品 POSITON_AD_TOP 看广告顶部广告") PositionEnum positionEnum) {
        return adPlatformService.selectByPosition(positionEnum);
    }

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "广告位 (广告模块三个视频) ", notes = "广告位 (三个视频)[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selAdPlatformPositionTwo", method = RequestMethod.GET)
    public Result<List<AdPlatformVideoDTO>> selAdPlatformPositionTwo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPlatformFindEginParam query) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
    	AdPlatformInternalParam param = new AdPlatformInternalParam();
    	param.setLatitude(query.getLatitude());
    	param.setLongitude(query.getLongitude());
    	param.setMerchantIds(adQueryMemberInfoDTO.getFansList());
    	if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
    		param.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
        return adPlatformService.selAdPlatformPositionTwo(param);
    }
    
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "广告位 (广告模块五个平面) ", notes = "广告位 (五个平面)[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selAdPlatformFiveFlat", method = RequestMethod.GET)
    public Result<List<AdPlatformFlatDTO>> selAdPlatformFiveFlat(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPlatformFindEginParam query) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
    	AdPlatformInternalParam param = new AdPlatformInternalParam();
    	param.setLatitude(query.getLatitude());
    	param.setLongitude(query.getLongitude());
    	param.setMerchantIds(adQueryMemberInfoDTO.getFansList());
    	if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
    		param.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
    	param.setAdPlatformFlatTypeEnum(AdPlatformFlatTypeEnum.AD_PLAT_FORM_FIVE);
        return adPlatformService.selAdPlatformPositionFour(param);
    }
    
    
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "广告位 (广告模块一个平面) ", notes = "广告位 (一个平面)[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selAdPlatformOneFlat", method = RequestMethod.GET)
    public Result<AdPlatformFlatDTO> selAdPlatformOneFlat(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPlatformFindEginParam query) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
    	AdPlatformInternalParam param = new AdPlatformInternalParam();
    	param.setLatitude(query.getLatitude());
    	param.setLongitude(query.getLongitude());
    	param.setMerchantIds(adQueryMemberInfoDTO.getFansList());
    	if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
    		param.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
    	param.setAdPlatformFlatTypeEnum(AdPlatformFlatTypeEnum.AD_PLAT_FORM_ONE);
    	Result<List<AdPlatformFlatDTO>> result=adPlatformService.selAdPlatformPositionFour(param);
    	if(!isSuccess(result)){
    		return successCreated(result.getRet());
    	}
    	if(!result.getModel().isEmpty()){
    		return successGet(result.getModel().get(0));
    	}else{
    		return successGet(new AdPlatformFlatDTO());
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "广告位 (广告模块顶部两个平面) ", notes = "广告位 (广告模块顶部两个平面)[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selAdPlatformToopTwoFlat", method = RequestMethod.GET)
    public Result<List<AdPlatformFlatDTO>> selAdPlatformToopTwoFlat(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPlatformFindEginParam query) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
    	AdPlatformInternalParam param = new AdPlatformInternalParam();
    	param.setLatitude(query.getLatitude());
    	param.setLongitude(query.getLongitude());
    	param.setMerchantIds(adQueryMemberInfoDTO.getFansList());
    	if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
    		param.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
    	param.setAdPlatformFlatTypeEnum(AdPlatformFlatTypeEnum.AD_PLAT_FORM_TWO);
    	return adPlatformService.selAdPlatformPositionFour(param);
    	
    	
    }

}
