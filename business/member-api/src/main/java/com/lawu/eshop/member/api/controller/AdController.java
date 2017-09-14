package com.lawu.eshop.member.api.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.FileTypeEnum;
import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdEgainDTO;
import com.lawu.eshop.ad.dto.AdEgainQueryDTO;
import com.lawu.eshop.ad.dto.AdFlatVideoDTO;
import com.lawu.eshop.ad.dto.AdLexiconDTO;
import com.lawu.eshop.ad.dto.AdPointDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.ChoicenessAdDTO;
import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.PointPoolDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.dto.RedPacketInfoDTO;
import com.lawu.eshop.ad.dto.UserTopDTO;
import com.lawu.eshop.ad.param.AdChoicenessParam;
import com.lawu.eshop.ad.param.AdEgainParam;
import com.lawu.eshop.ad.param.AdPointForeignParam;
import com.lawu.eshop.ad.param.AdPointParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdSolrParam;
import com.lawu.eshop.ad.param.AdSolrRealParam;
import com.lawu.eshop.ad.param.AdsolrFindParam;
import com.lawu.eshop.ad.param.RegisterGetRedPacketParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.AdExtendService;
import com.lawu.eshop.member.api.service.AdLexiconService;
import com.lawu.eshop.member.api.service.AdService;
import com.lawu.eshop.member.api.service.AdViewService;
import com.lawu.eshop.member.api.service.ClickAdRecordService;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantProfileService;
import com.lawu.eshop.member.api.service.MerchantService;
import com.lawu.eshop.member.api.service.PropertyInfoDataService;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PointDetailQueryData1Param;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantBaseInfoDTO;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.UserRedPacketDTO;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.utils.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import util.VideoCutImgUtil;

/**
 * 描述：广告管理
 *
 * @author zhangrc
 * @date 2017/04/5
 */
@Api(tags = "ad", value = "广告接口")
@RestController
@RequestMapping(value = "ad/")
public class AdController extends BaseController {

	@Autowired
	private AdService adService;

	@Autowired
	private AdExtendService adExtendService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private FansMerchantService fansMerchantService;

	@Autowired
	private MerchantProfileService merchantProfileService;

	@Autowired
	private AdViewService adViewService;

	@Autowired
	private PropertyInfoDataService propertyInfoDataService;

	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private MemberApiConfig memberApiConfig;

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private ClickAdRecordService clickAdRecordService;
	
	@Autowired
	private AdLexiconService adLexiconService;
	
	/**
	 * @see selectEgainAd
	 */
	@Deprecated
	@Audit(date = "2017-04-17", reviewer = "孙林青")
	@ApiOperation(value = "E赚列表(E赚平面和视频)[Deprecated]", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
//	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectEgain", method = RequestMethod.GET)
	public Result<Page<AdDTO>> selectEgain(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdEgainParam adEgainParam) {
		return adExtendService.selectListByMember(adEgainParam);
	}

	@Deprecated
	@Audit(date = "2017-04-17", reviewer = "孙林青")
	@ApiOperation(value = "会员查询广告列表(精选推荐)[Deprecated]", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
	//@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectChoiceness", method = RequestMethod.GET)
	public Result<Page<AdDTO>> selectChoiceness(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdChoicenessParam param) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		List<Long> merchantIds = fansMerchantService.findMerchant(memberId);
		AdSolrRealParam realParam = new AdSolrRealParam();
		realParam.setMemberId(memberId);
		realParam.setLongitude(param.getLongitude());
		realParam.setLatitude(param.getLatitude());
		realParam.setRegionPath(param.getTransRegionPath());
		realParam.setCurrentPage(param.getCurrentPage());
		realParam.setPageSize(param.getPageSize());
		realParam.setMerchantIds(merchantIds);
		return adService.listAd(realParam);
		//return adExtendService.selectChoiceness(param);
	}

	@Deprecated
	@Audit(date = "2017-04-17", reviewer = "孙林青")
	@ApiOperation(value = "会员查询广告列表(积分榜，人气榜)[Deprecated]", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
//	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectListPointTotle", method = RequestMethod.GET)
	public Result<List<AdDTO>> selectListPointTotle(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPointParam adPointParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		List<Long> merchantIds = fansMerchantService.findMerchant(memberId);
		AdSolrRealParam param = new AdSolrRealParam();
		param.setMemberId(memberId);
		param.setLongitude(adPointParam.getLongitude());
		param.setLatitude(adPointParam.getLatitude());
		param.setRegionPath(adPointParam.getTransRegionPath());
		param.setOrderTypeEnum(adPointParam.getOrderTypeEnum());
		param.setCurrentPage(adPointParam.getCurrentPage());
		param.setPageSize(adPointParam.getPageSize());
		param.setMerchantIds(merchantIds);
		return adService.listAdRank(param);
		//return adExtendService.selectAdTopList(adPointParam);
	}


    @Audit(date = "2017-04-13", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "查询单个广告", notes = "查询单个广告[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAb/{id}", method = RequestMethod.GET)
    public Result<AdEgainDTO> selectAdDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Long memberId=UserUtil.getCurrentUserId(getRequest());
    	
        Result<AdEgainDTO> adRs = adService.selectAbById(id,memberId);
        
        if(!isSuccess(adRs)){
        	return successCreated(adRs.getRet());
        }
        AdEgainDTO  adEgainDTO=adRs.getModel();
        
        if(StringUtils.isEmpty(adEgainDTO.getName())){
        	adEgainDTO.setName("E店商家");
		}else if(StringUtils.isEmpty(adEgainDTO.getLogoUrl())){
        	adEgainDTO.setLogoUrl(memberApiConfig.getDefaultHeadimg());
		}
    	
    	Result<MerchantProfileDTO> mpRs=merchantProfileService.getMerchantProfile(adEgainDTO.getMerchantId());
    	if(isSuccess(mpRs)){
    		adEgainDTO.setJdUrl(mpRs.getModel().getJdUrl());
    		adEgainDTO.setTaobaoUrl(mpRs.getModel().getTaobaoUrl());
    		adEgainDTO.setTmallUrl(mpRs.getModel().getTmallUrl());
    		adEgainDTO.setWebsiteUrl(mpRs.getModel().getWebsiteUrl());
    	}
    	if(StringUtils.isNotEmpty(adEgainDTO.getVideoImgUrl())){
    		String url=memberApiConfig.getVideoUploadUrl()+"/"+adEgainDTO.getMediaUrl();
    		File f= new File(url); 
    		if (f.exists() && f.isFile()){  
    	        adEgainDTO.setFileSize(f.length()/1024/1024);
    	    }
    		adEgainDTO.setVideoTime(VideoCutImgUtil.getVideoTime(url, memberApiConfig.getFfmpegUrl()));
    	}
	    
		Result<Set<String>> rs= adViewService.getAdviews(id.toString());
		 
		if(!isSuccess(rs)){
			 return successCreated(rs.getRet());
    	}
		if(!rs.getModel().isEmpty()){
			boolean flag = false;
			for (String str : rs.getModel()) {
				flag = memberId.toString().equals(str);
			}
			if (!flag) {
				adViewService.setAdView(id.toString(), memberId.toString());
			}
		 }else{
			 adViewService.setAdView(id.toString(), memberId.toString());
		 }
       
        return successAccepted(adEgainDTO);
    }

	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@ApiOperation(value = "会员查询广告列表(E赞)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
//	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.GET)
	public Result<Page<AdPraiseDTO>> selectAdPraiseList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPraiseParam adPraiseParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		List<Long> merchantIds = fansMerchantService.findMerchant(memberId);
		AdSolrRealParam param = new AdSolrRealParam();
		param.setMemberId(memberId);
		param.setRegionPath(adPraiseParam.getTransRegionPath());
		param.setStatusEnum(AdStatusEnum.getEnum(adPraiseParam.getStatusEnum().getVal()));
		param.setCurrentPage(adPraiseParam.getCurrentPage());
		param.setPageSize(adPraiseParam.getPageSize());
		param.setMerchantIds(merchantIds);
		return adService.getRecommendEgain(param);
		//return adExtendService.selectAdPraiseList(adPraiseParam);
	}

	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "查询单个E赞", notes = "查询单个E赞[]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectAbPraise/{id}", method = RequestMethod.GET)
	public Result<AdPraiseDTO> selectAbPraiseById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
		return adExtendService.selectAbPraiseById(id);
	}

	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@ApiOperation(value = "Top3排行榜", notes = "Top3排行榜,[]（张荣成）", httpMethod = "GET")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "memberRanking/{id}", method = RequestMethod.GET)
	public Result<List<UserTopDTO>> memberRanking(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "广告id") Long id) {

		Result<List<PointPoolDTO>> member = adService.selectMemberList(id);
		List<UserTopDTO> user = new ArrayList<>();
		if(!isSuccess(member)){
			return successCreated(member.getRet());
		}
		List<PointPoolDTO> top3 = member.getModel();
		
		if(top3.isEmpty()){
			return successGet(user);
		}
		List<Long> memberIds=new ArrayList<>();
		for (PointPoolDTO pointPoolDTO : top3) {
			memberIds.add(pointPoolDTO.getMemberId());
		}
		
		Result<List<MemberDTO>> resultUser=memberService.getMemberByIds(memberIds);
		
		if(!isSuccess(resultUser)){
			return successCreated(resultUser.getRet());
		}
		List<MemberDTO> listUser=resultUser.getModel();
		for (PointPoolDTO pointPoolDTO : top3) {
			for (MemberDTO memberDTO : listUser) {
				if(memberDTO.getId().intValue()==pointPoolDTO.getMemberId().intValue()){
					UserTopDTO userTop = new UserTopDTO();
					userTop.setMoney(pointPoolDTO.getPoint());
					userTop.setHeadimg(memberDTO.getHeadimg());
					userTop.setRegionPath(memberDTO.getRegionPath());
					userTop.setMobile(memberDTO.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
					user.add(userTop);
				}
			}
			
		}
		return successGet(user);
	}

	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "抢赞", notes = "抢赞[]（张荣成）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "clickPraise/{id}", method = RequestMethod.PUT)
	public Result<PraisePointDTO> clickPraise(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
		return adExtendService.clickPraise(id);
	}

	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "点击广告", notes = "点击广告[5008]（张荣成）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "clickAd/{id}", method = RequestMethod.PUT)
	public Result<ClickAdPointDTO> clickAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		String num = UserUtil.getCurrentUserNum(getRequest());
		Result<Boolean> result= clickAdRecordService.getClickAdRecord(memberId+num+id+DateUtil.getIntDate());
		if(!isSuccess(result)){
			return successCreated(result.getRet());
		}
		if(result.getModel()){
			return successCreated(ResultCode.AD_CLICK_EXIST);
		}
		Result<ClickAdPointDTO> res=adService.clickAd(id, memberId, num);
		if(res.getModel()!=null && res.getModel().getPoint().compareTo(BigDecimal.valueOf(0))==1){ 
			clickAdRecordService.setClickAdRecord(memberId+num+id+DateUtil.getIntDate());
		}
		return res;
		
	}

	@SuppressWarnings("unchecked")
	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "广告词库查询", notes = "广告词库查询[]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectLexicon", method = RequestMethod.GET)
	public Result<List<AdLexiconDTO>> selectList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @RequestParam @ApiParam(required = true, value = "广告id") Long adId) {
		Result<List<AdLexiconDTO>> result = adLexiconService.selectList(adId);
		return successGet(result);
	}

	@Audit(date = "2017-04-13", reviewer = "孙林青")
	@ApiOperation(value = "广告搜索", notes = "广告搜索,[]（张荣成）", httpMethod = "GET")
//	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectAdByTitle", method = RequestMethod.GET)
	public Result<Page<AdSolrDTO>> selectAdByTitle(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdSolrParam adSolrParam) {
		
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		AdsolrFindParam findParam = new AdsolrFindParam();
		findParam.setAdSolrParam(adSolrParam);
		findParam.setMemberId(memberId);
		List<Long> merchantIds = fansMerchantService.findMerchant(memberId);
		findParam.setMerchantIds(merchantIds);
		if(adSolrParam.getTransRegionPath()!=null){
			findParam.setRegionPath(adSolrParam.getTransRegionPath());
		}
		return adService.queryAdByTitle(findParam);
	}

	@Audit(date = "2017-04-26", reviewer = "孙林青")
	@ApiOperation(value = "领取红包", notes = "领取红包[1002|5004]（张荣成）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "getRedPacket", method = RequestMethod.PUT)
	public Result<PraisePointDTO> getRedPacket(@RequestParam @ApiParam(required = true, value = "商家id") Long merchantId, @RequestParam @ApiParam(required = true, value = "用户电话") String mobile) {
		Result<UserRedPacketDTO> userRs = memberService.isRegister(mobile);
		if (userRs != null) { // 直接领取红包 并成为粉丝
			Long memberId = userRs.getModel().getMemberId();
			String userNum = userRs.getModel().getUserNum();
			Result<Boolean> result = fansMerchantService.isFansMerchant(merchantId, memberId);
			if (!isSuccess(result)) {
				return successCreated(result.getRet());
			}
			if (!result.getModel()) {
				fansMerchantService.saveFansMerchant(merchantId, memberId, FansMerchantChannelEnum.REDPACKET);
			}
			return adService.getRedPacket(merchantId, memberId, userNum);
		}else {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}

	}

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-05-02", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "抢赞扣除用户积分", notes = "抢赞扣除用户积分[6010|6024|6026]（张荣成）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "doHanlderMinusPoint", method = RequestMethod.POST)
	public Result doHanlderMinusPoint(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@RequestParam @ApiParam(required = true, value = "广告id") Long id) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		PointDetailQueryData1Param query = new PointDetailQueryData1Param();
		query.setBizId(id.toString());
		query.setPointType(MemberTransactionTypeEnum.PRAISE_AD.getValue());
		query.setUserNum(userNum);
		Result<Integer> isDoResult = propertyInfoDataService.getPointDetailByUserNumAndPointTypeAndBizId(query);
		if (!isSuccess(isDoResult)) {
			return successCreated(isDoResult.getRet());
		}
		if (isDoResult.getModel() == 1){
			return successCreated();
		}
		PropertyInfoDataParam param = new PropertyInfoDataParam();
		param.setUserNum(userNum);
		param.setPoint("20");
		param.setBizId(id.toString());
		param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.PRAISE_AD);
		return propertyInfoDataService.doHanlderMinusPoint(param);

	}

	@Deprecated
	@Audit(date = "2017-05-12", reviewer = "孙林青")
	@ApiOperation(value = "E赚列表(E赚平面和视频)[Deprecated]", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
//	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectEgainAd", method = RequestMethod.GET)
	public Result<Page<AdFlatVideoDTO>> selectEgainAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdEgainParam adEgainParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		List<Long> merchantIds = fansMerchantService.findMerchant(memberId);
		AdSolrRealParam param = new AdSolrRealParam();
		param.setMemberId(memberId);
		param.setLongitude(adEgainParam.getLongitude());
		param.setLatitude(adEgainParam.getLatitude());
		param.setRegionPath(adEgainParam.getTransRegionPath());
		param.setTypeEnum(AdTypeEnum.getEnum(adEgainParam.getTypeEnum().getVal()));
		param.setCurrentPage(adEgainParam.getCurrentPage());
		param.setPageSize(adEgainParam.getPageSize());
		param.setMerchantIds(merchantIds);
		return adService.getRecommendAdByType(param);
		//return adExtendService.selectEgainAd(adEgainParam);
	}

	@Audit(date = "2017-05-23", reviewer = "孙林青")
	@ApiOperation(value = "获取领取红包之前的信息", notes = "红包信息,[5009]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "getRedPacketInfo/{merchantId}", method = RequestMethod.GET)
	public Result<RedPacketInfoDTO> getRedPacketInfo(@PathVariable @ApiParam(required = true, value = "商家id") Long merchantId) {
		Result<RedPacketInfoDTO> result = adService.getRedPacketInfo(merchantId);
		if(!isSuccess(result)){
			successCreated(result.getRet());
		}
		if(StringUtils.isEmpty(result.getModel().getName())){
			result.getModel().setName("E店商家");
		}
		if(StringUtils.isEmpty(result.getModel().getLogoUrl())){
			result.getModel().setLogoUrl(memberApiConfig.getDefaultHeadimg());

		}
		if(result.getModel().getFileType()==FileTypeEnum.VIDEO){
			 String str = VideoCutImgUtil.getVideoTime(result.getModel().getMediaUrl(), memberApiConfig.getFfmpegUrl());
			 result.getModel().setVideoTime(str);
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-05-23", reviewer = "孙林青")
	@ApiOperation(value = "注册并领取红包", notes = "注册并领取红包,[1012|1013|1016|1025|1026|1027]（张荣成）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "registerGetRedPacket", method = RequestMethod.POST)
	public Result<PraisePointDTO> registerGetRedPacket(@ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterGetRedPacketParam param) {
		RegisterRealParam registerRealParam = new RegisterRealParam();
		Result<MerchantBaseInfoDTO> inviterResult = merchantService.getMerchantById(param.getMerchantId());
		if (!isSuccess(inviterResult)) {
			return successGet(ResultCode.INVITER_NO_EXIST);
		}
		registerRealParam.setInviterId(param.getMerchantId());
		registerRealParam.setUserNum(inviterResult.getModel().getUserNum());
		Result accountResult = memberService.getMemberByAccount(param.getAccount());
		if (isSuccess(accountResult)) {
			return successGet(ResultCode.ACCOUNT_EXIST);
		}
		if (isSuccess(accountResult)) {
			return successGet(ResultCode.RECORD_EXIST);
		}
		Result<VerifyCodeDTO> smsResult = verifyCodeService.verifySmsCode(param.getVerifyCodeId(), param.getSmsCode());
		if (!isSuccess(smsResult)) {
			return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
		}
		VerifyCodeDTO verifyCodeDTO = smsResult.getModel();
		if (!param.getAccount().equals(verifyCodeDTO.getMobile())) {
			return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
		}
		if (DateUtil.smsIsOverdue(verifyCodeDTO.getGmtCreate(), memberApiConfig.getSmsValidMinutes())) {
			return successGet(ResultCode.VERIFY_SMS_CODE_OVERTIME);
		}
		
		registerRealParam.setAccount(param.getAccount());
		registerRealParam.setPwd(param.getPwd());
		Result rs = memberService.register(registerRealParam);
		
		if (!isSuccess(rs)) { 
			return successGet(rs.getRet());
		}
		// 注册成功，领取红包
		Result<UserRedPacketDTO> userRs = memberService.isRegister(param.getAccount());
		Long memberId = userRs.getModel().getMemberId();
		String userNum = userRs.getModel().getUserNum();
		Result<PraisePointDTO> rsPoint = adService.getRedPacket(param.getMerchantId(), memberId, userNum);
		Result<Boolean> result = fansMerchantService.isFansMerchant(param.getMerchantId(), memberId);
		if (!isSuccess(result)) {
			return successGet(result.getRet());
		}
		if (!result.getModel()) {
			fansMerchantService.saveFansMerchant(param.getMerchantId(), memberId, FansMerchantChannelEnum.REDPACKET);
		}
		return rsPoint;

	}

	@Audit(date = "2017-06-15", reviewer = "孙林青")
	@ApiOperation(value = "红包是否领取完", notes = "红包是否领取完,[]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "isExistsRedPacket/{merchantId}", method = RequestMethod.GET)
	public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable @ApiParam(required = true, value = "商家id") Long merchantId) {
		return adService.isExistsRedPacket(merchantId);
	}
	
	@Audit(date = "2017-08-18", reviewer = "李洪军")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询E赚列表", notes = "分页查询E赚列表(平面和视频)[]（蒋鑫俊）", httpMethod = "GET")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "egainAd", method = RequestMethod.GET)
	public Result<Page<AdEgainQueryDTO>> egainAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdEgainParam adEgainParam) {
		Result<Page<AdEgainQueryDTO>> result = adExtendService.selectEgain(adEgainParam);
		return successGet(result);
	}
	
	@Audit(date = "2017-08-18", reviewer = "李洪军")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询积分排行榜广告列表", notes = "查询积分排行榜广告列表(积分榜，人气榜),[]（蒋鑫俊）", httpMethod = "GET")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectPointTotle", method = RequestMethod.GET)
	public Result<List<AdPointDTO>> selectPointTotle(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdPointForeignParam adPointParam) {
		Result<List<AdPointDTO>> result = adExtendService.selectAdTop(adPointParam);
		return successGet(result);
	}
	
	@Audit(date = "2017-08-18", reviewer = "李洪军")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询精选推荐广告列表", notes = "分页查询精选推荐广告列表,[]（蒋鑫俊）", httpMethod = "GET")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "choiceness", method = RequestMethod.GET)
	public Result<Page<ChoicenessAdDTO>> selectChoicenessAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(value = "查询信息") AdChoicenessParam param) {
		Result<Page<ChoicenessAdDTO>> result = adExtendService.selectChoicenessAd(param);
		return successGet(result);
	}
}
