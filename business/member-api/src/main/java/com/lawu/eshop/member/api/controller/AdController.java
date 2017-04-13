package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.AdExtendService;
import com.lawu.eshop.member.api.service.AdService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
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
    
    

    @ApiOperation(value = "会员查询广告列表(精选推荐,E赚平面和视频)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectListByMember", method = RequestMethod.GET)
    public Result<Page<AdDTO>> selectListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdMemberParam adMemberParam) {
    	Result<Page<AdDTO>>  pageDTOS=adExtendService.selectListByMember(adMemberParam);
    	return pageDTOS;
    }
    
     @ApiOperation(value = "会员查询广告列表(积分榜，人气榜)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectListPointTotle", method = RequestMethod.GET)
     public Result<List<AdDTO>> selectListPointTotle(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                  @ModelAttribute @ApiParam( value = "查询信息") AdMemberParam adMemberParam) {
    	 Result<List<AdDTO>> rs= adExtendService.selectListPointTotle(adMemberParam);
     	return rs;
     }
    
    
    @Authorization
    @ApiOperation(value = "查询单个广告", notes = "查询单个广告[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAb/{id}", method = RequestMethod.GET)
    public Result<AdDTO> selectAbById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result<AdDTO> adDTO = adService.selectAbById(id);
        Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getModel().getMerchantId());
        adDTO.getModel().setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
        adDTO.getModel().setName(merchantStoreDTO.getModel().getName());
        return adDTO;
    }
    
    
     @ApiOperation(value = "会员查询广告列表(E赞)", notes = "广告列表,[]（张荣成）", httpMethod = "GET")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.GET)
     public Result<Page<AdPraiseDTO>> selectPraiseListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                  @ModelAttribute @ApiParam( value = "查询信息") AdPraiseParam adPraiseParam) {
    	 Result<Page<AdPraiseDTO>> rs= adExtendService.selectPraiseListByMember(adPraiseParam);
     	return rs;
     }
    
    
    @Authorization
    @ApiOperation(value = "查询单个E赞", notes = "查询单个E赞[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAbPraise/{id}", method = RequestMethod.GET)
    public Result<AdPraiseDTO> selectAbPraiseById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result<AdPraiseDTO> adDTO = adExtendService.selectAbPraiseById(id);
        return adDTO;
    }
    
    
     @ApiOperation(value = "Top3排行榜", notes = "Top3排行榜,[]（张荣成）", httpMethod = "GET")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectMemberList/{id}", method = RequestMethod.GET)
     public Result<List<UserDTO>> selectListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		 @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
     
     	Result<List<Long>>  member=adService.selectMemberList(id);
     	List<Long>  memberIds=member.getModel();
     	Result<UserDTO> userRs=new Result<UserDTO>();
     	List<UserDTO> user=new ArrayList<>();
     	if(memberIds!=null)
     		for (Long mid : memberIds) {
     			userRs=memberService.findMemberInfo(mid);
     			user.add(userRs.getModel());
			}
     	
     	return successGet(user);
     }
     
     
    // @Authorization
     @ApiOperation(value = "抢赞", notes = "抢赞[]（张荣成）", httpMethod = "GET")
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "clickPraise/{id}", method = RequestMethod.GET)
     public Result clickPraise(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/
                                   @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
         Result rs = adExtendService.clickPraise(id);
         return rs;
     }

}
