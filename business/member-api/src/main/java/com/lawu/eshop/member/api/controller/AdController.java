package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdEgainDTO;
import com.lawu.eshop.ad.dto.AdLexiconDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.PointPoolDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.dto.UserTopDTO;
import com.lawu.eshop.ad.param.AdChoicenessParam;
import com.lawu.eshop.ad.param.AdEgainParam;
import com.lawu.eshop.ad.param.AdPointParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdSolrParam;
import com.lawu.eshop.ad.param.AdsolrFindParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AdExtendService;
import com.lawu.eshop.member.api.service.AdService;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantProfileService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.UserDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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
    private AdExtendService adExtendService;
    
    @Autowired
    private MemberService memberService;
	    
    @Autowired
    private MerchantStoreService merchantStoreService;
    
    @Autowired
    private FansMerchantService fansMerchantService;
    
    @Autowired
    private MerchantProfileService merchantProfileService;



    @Audit(date = "2017-04-17", reviewer = "孙林青")
    @ApiOperation(value = "E赚列表(E赚平面和视频)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectEgain", method = RequestMethod.GET)
    public Result<Page<AdDTO>> selectEgain(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdEgainParam adEgainParam) {
    	Result<Page<AdDTO>>  pageDTOS=adExtendService.selectListByMember(adEgainParam);
    	return pageDTOS;
    }

    @Audit(date = "2017-04-17", reviewer = "孙林青")
    @ApiOperation(value = "会员查询广告列表(精选推荐)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectChoiceness", method = RequestMethod.GET)
    public Result<Page<AdDTO>> selectChoiceness(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdChoicenessParam param) {
    	Result<Page<AdDTO>>  pageDTOS=adExtendService.selectChoiceness(param);
    	return pageDTOS;
    }

    @Audit(date = "2017-04-17", reviewer = "孙林青")
    @ApiOperation(value = "会员查询广告列表(积分榜，人气榜)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectListPointTotle", method = RequestMethod.GET)
    public Result<List<AdDTO>> selectListPointTotle(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                    @ModelAttribute @ApiParam(value = "查询信息") AdPointParam adPointParam) {
        Result<List<AdDTO>> rs = adExtendService.selectListPointTotle(adPointParam);
        return rs;
    }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "查询单个广告", notes = "查询单个广告[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAb/{id}", method = RequestMethod.GET)
    public Result<AdEgainDTO> selectAbById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Long memberId=UserUtil.getCurrentUserId(getRequest());
        Result<AdDTO> adRs = adService.selectAbById(id,memberId);
        AdEgainDTO adEgainDTO=new AdEgainDTO();
        if(isSuccess(adRs)){
        	AdDTO  adDTO=adRs.getModel();
        	adEgainDTO.setId(adDTO.getId());
        	adEgainDTO.setAttenCount(adDTO.getAttenCount());
        	adEgainDTO.setContent(adDTO.getContent());
        	adEgainDTO.setIsFavorite(adDTO.getIsFavorite());
        	adEgainDTO.setTitle(adDTO.getTitle());
        	adEgainDTO.setMediaUrl(adDTO.getMediaUrl());
        	adEgainDTO.setMerchantId(adDTO.getMerchantId());
        	adEgainDTO.setStatusEnum(adDTO.getStatusEnum());
        	Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
        	if(isSuccess(merchantStoreDTO)){
        		adEgainDTO.setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
            	adEgainDTO.setName(merchantStoreDTO.getModel().getName());
            	adEgainDTO.setLogoUrl(merchantStoreDTO.getModel().getLogoUrl());
        	}
        	Result<MerchantProfileDTO> mpRs=merchantProfileService.getMerchantProfile(adDTO.getMerchantId());
        	if(isSuccess(mpRs)){
        		adEgainDTO.setJdUrl(mpRs.getModel().getJdUrl());
        		adEgainDTO.setTaobaoUrl(mpRs.getModel().getTaobaoUrl());
        		adEgainDTO.setTmallUrl(mpRs.getModel().getTmallUrl());
        		adEgainDTO.setWebsiteUrl(mpRs.getModel().getWebsiteUrl());
        	}
        }
       
        return successAccepted(adEgainDTO);
    }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
    @ApiOperation(value = "会员查询广告列表(E赞)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.GET)
    public Result<Page<AdPraiseDTO>> selectPraiseListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                              @ModelAttribute @ApiParam(value = "查询信息") AdPraiseParam adPraiseParam) {
        Result<Page<AdPraiseDTO>> rs = adExtendService.selectPraiseListByMember(adPraiseParam);
        return rs;
    }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "查询单个E赞", notes = "查询单个E赞[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAbPraise/{id}", method = RequestMethod.GET)
    public Result<AdPraiseDTO> selectAbPraiseById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result<AdPraiseDTO> adDTO = adExtendService.selectAbPraiseById(id);
        return adDTO;
    }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
     @ApiOperation(value = "Top3排行榜", notes = "Top3排行榜,[]（张荣成）", httpMethod = "GET")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "memberRanking/{id}", method = RequestMethod.GET)
     public Result<List<UserTopDTO>> selectListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		 @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
     
     	Result<List<PointPoolDTO>>  member=adService.selectMemberList(id);
     	List<PointPoolDTO>  memberIds=member.getModel();
     	List<UserTopDTO> user=new ArrayList<>();
     	if(memberIds!=null)
     		for (PointPoolDTO pointPoolDTO : memberIds) {
     			UserTopDTO userTop=new UserTopDTO();
     			userTop.setMoney(pointPoolDTO.getPoint());
     			MemberDTO userDTO=memberService.findMemberInfoById(pointPoolDTO.getMemberId()).getModel();
     			userTop.setHeadimg(userDTO.getHeadimg());
     			if(userDTO.getRegionPath()!=null)
     				userTop.setRegionPath(userDTO.getRegionPath());
     			if(userDTO.getMobile()!=null){
     				userTop.setMobile(userDTO.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
     			}
     			user.add(userTop);
			}
     	
     	return successGet(user);
     }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
     @Authorization
     @ApiOperation(value = "抢赞", notes = "抢赞[]（张荣成）", httpMethod = "GET")
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "clickPraise/{id}", method = RequestMethod.GET)
     public Result<PraisePointDTO> clickPraise(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
         Result rs = adExtendService.clickPraise(id);
         return rs;
     }

    @Audit(date = "2017-04-13", reviewer = "孙林青")
     @Authorization
     @ApiOperation(value = "点击广告", notes = "点击广告[]（张荣成）", httpMethod = "GET")
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "clickAd/{id}", method = RequestMethod.GET)
     public Result clickAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	 Long memberId=UserUtil.getCurrentUserId(getRequest());
    	 String num = UserUtil.getCurrentUserNum(getRequest());
    	 Result rs = adService.clickAd(id,memberId,num);
         return rs;
     }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
     @Authorization
     @ApiOperation(value = "广告词库查询", notes = "广告词库查询[]（张荣成）", httpMethod = "GET")
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectLexicon", method = RequestMethod.GET)
     public Result<List<AdLexiconDTO>> selectList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@RequestParam @ApiParam(required = true, value = "广告id") Long adId) {
         Result<List<AdLexiconDTO>> adLexiconDTOS = adService.selectList(adId);
         return adLexiconDTOS;
     }


    @Audit(date = "2017-04-13", reviewer = "孙林青")
     @ApiOperation(value = "广告搜索", notes = "广告搜索,[]（张荣成）", httpMethod = "GET")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectAdByTitle", method = RequestMethod.GET)
     public Result<Page<AdSolrDTO>> selectListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		 @ModelAttribute @ApiParam(value = "查询信息") AdSolrParam adSolrParam) {
    	 Long memberId=UserUtil.getCurrentUserId(getRequest());
    	 List<Long> merchantIds=fansMerchantService.findMerchant(memberId);
    	 Result<UserDTO> userRS= memberService.findMemberInfo(memberId);
    	 AdsolrFindParam findParam=new AdsolrFindParam();
    	 findParam.setAdSolrParam(adSolrParam);
    	 findParam.setMerchantIds(merchantIds);
    	 findParam.setMemberId(memberId);
    	 if(userRS.getModel().getRegionPath()!=null){
    		 findParam.setRegionPath(userRS.getModel().getRegionPath());
    	 }
     	 Result<Page<AdSolrDTO>>  page=adService.queryAdByTitle(findParam);
     	 return page;
     }
    
    
    @Authorization
    @ApiOperation(value = "领取红包", notes = "领取红包[5004]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "getRedPacket", method = RequestMethod.GET)
    public Result<PraisePointDTO> getRedPacket(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@RequestParam @ApiParam(required = true, value = "商家id") Long merchantId) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	Result<PraisePointDTO> rs=adService.getRedPacket(merchantId,memberId,userNum);
    	return rs;
    }

} 
