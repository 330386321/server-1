package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.AdPage;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.AdService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.utils.DistanceUtil;

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
    private MemberService memberService;
    
    @Autowired
    private MerchantStoreService merchantStoreService;
    

    @ApiOperation(value = "会员查询广告列表(精选推荐,E赚平面和视频)", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
   // @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectListByMember", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMember(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdMemberParam adMemberParam) {
    	Long memberId=UserUtil.getCurrentUserId(getRequest());
    	Result<Page<AdDTO>>  pageDTOS=adService.selectListByMember(adMemberParam);
    	List<AdDTO> list =pageDTOS.getModel().getRecords();
    	List<AdDTO> newList=screem(adMemberParam,list,memberId);
    	AdPage<AdDTO> adpage=new AdPage<>();
    	List<AdDTO>  screenList=adpage.page(newList, adMemberParam.getPageSize(), adMemberParam.getCurrentPage());
    	for (AdDTO adDTO : screenList) {
    		Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
     		adDTO.setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
     		adDTO.setName(merchantStoreDTO.getModel().getName());
		}
    	Page<AdDTO> newPage=new Page<AdDTO>();
    	newPage.setCurrentPage(pageDTOS.getModel().getCurrentPage());
    	newPage.setTotalCount(newList.size());
    	newPage.setRecords(screenList);
    	return successGet(newPage);
    }
    
     @ApiOperation(value = "会员查询广告列表(积分榜，人气榜)", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
    // @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectListPointTotle", method = RequestMethod.POST)
     public Result<List<AdDTO>> selectListPointTotle(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/
                                                                  @ModelAttribute @ApiParam( value = "查询信息") AdMemberParam adMemberParam) {
     	Long memberId=UserUtil.getCurrentUserId(getRequest());
     	Result<Page<AdDTO>>  pageDTOS=adService.selectListByMember(adMemberParam);
     	List<AdDTO> list =pageDTOS.getModel().getRecords();
     	List<AdDTO> newList= new ArrayList<AdDTO>();
     	newList=screem(adMemberParam,list,memberId);
     	if(newList.size()>9)
     		newList=newList.subList(0, 9);
     	else
     		newList=newList.subList(0,newList.size());
     	
     	for(int i=0;i<newList.size();i++){
     		Calendar calendar = Calendar.getInstance();  //得到日历  
   		    calendar.setTime(new Date());//把当前时间赋给日历  
   		    calendar.add(Calendar.DAY_OF_MONTH, -14);  //设置为14天前  
   	        Date before14days = calendar.getTime();   //得到14天前的时间  
   	        if(before14days.getTime() > newList.get(i).getBeginTime().getTime()){  
   	        	newList.remove(i);
   	        }
     	}
     	for (AdDTO adDTO : newList) {
     		Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
     		adDTO.setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
     		adDTO.setName(merchantStoreDTO.getModel().getName());
 		}
   
     	return successGet(newList);
     }
    
    /**
     * 公用方法广告过滤
     * @param list
     * @param memberId
     * @return
     */
    public List<AdDTO> screem(AdMemberParam adMemberParam,List<AdDTO> list,Long memberId ){
    	List<AdDTO> newList =new ArrayList<AdDTO>();
    	for (AdDTO adDTO : list) {
    		if(adDTO.getPutWayEnum().val==1){ //区域
    			if(adDTO.getAreas()==null){
    				newList.add(adDTO);
    			}else{
    				Result<UserDTO> memberDTO=memberService.findMemberInfo(memberId);
    				String memberPath=memberDTO.getModel().getRegionPath();
    				if(memberPath!=null){
    					String[] memberPaths=memberPath.split("/");
    					String[] path=adDTO.getAreas().split("/");
    					for (String s : path) {
        					for (String mp : memberPaths) {
        						if(s.equals(mp)){
        							newList.add(adDTO);
        						}
        						continue;
        					}
        				}
    				}
    			}
    			
    		}else if(adDTO.getPutWayEnum().val==2){
    			//获取商家粉丝，判断当前用户是否属于商家粉丝
        		//fensService
    			if(memberId==1l)
    				newList.add(adDTO);
    		}else{
    			 Result<MerchantStoreDTO> rs= merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
    			 MerchantStoreDTO dto= rs.getModel();
    			 int distance= DistanceUtil.getDistance(adMemberParam.getLongitude1(), adMemberParam.getLatitude1(), 
    					 dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue());
    			 if(adDTO.getRadius()>distance)
    				 newList.add(adDTO);
    		}
			
		}
    	return newList;
    	
    }
    
    
    @Authorization
    @ApiOperation(value = "查询单个广告", notes = "查询单个广告[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAbById/{id}", method = RequestMethod.GET)
    public Result<AdDTO> selectAbById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result<AdDTO> adDTO = adService.selectAbById(id);
        Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getModel().getMerchantId());
        adDTO.getModel().setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
        adDTO.getModel().setName(merchantStoreDTO.getModel().getName());
        return adDTO;
    }
    
    
     @ApiOperation(value = "会员查询广告列表(E赞)", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.POST)
     public Result<Page<AdPraiseDTO>> selectPraiseListByMember(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                  @ModelAttribute @ApiParam( value = "查询信息") AdPraiseParam adPraiseParam) {
     	Long memberId=UserUtil.getCurrentUserId(getRequest());
     	Result<Page<AdDTO>>  pageDTOS=adService.selectPraiseListByMember(adPraiseParam);
     	List<AdDTO> list =pageDTOS.getModel().getRecords();
     	List<AdDTO> newList=screem(null,list,memberId);
     	AdPage<AdDTO> adpage=new AdPage<AdDTO>();
    	List<AdDTO>  screenList=adpage.page(newList, adPraiseParam.getPageSize(), adPraiseParam.getCurrentPage());
     	List<AdPraiseDTO> adPraiseDTOS=new ArrayList<AdPraiseDTO>();
     	for (AdDTO adDTO : screenList) {
     		Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
     		AdPraiseDTO praise=new AdPraiseDTO();
     		praise.setName(merchantStoreDTO.getModel().getName());
     		praise.setId(adDTO.getId());
			praise.setTitle(adDTO.getTitle());
			praise.setBeginTime(adDTO.getBeginTime());
			praise.setEndTime(adDTO.getEndTime());
			praise.setTotalPoint(adDTO.getTotalPoint());
			praise.setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
			praise.setCount(adDTO.getNumber());
			adPraiseDTOS.add(praise);
 		}
     	Page<AdPraiseDTO> newPage=new Page<AdPraiseDTO>();
     	newPage.setCurrentPage(pageDTOS.getModel().getCurrentPage());
     	newPage.setTotalCount(newList.size());
     	newPage.setRecords(adPraiseDTOS);
     	return successGet(newPage);
     }
    
    
    @Authorization
    @ApiOperation(value = "查询单个E赞", notes = "查询单个E赞[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectAbPraiseById/{id}", method = RequestMethod.GET)
    public Result<AdPraiseDTO> selectAbPraiseById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result<AdDTO> adDTO = adService.selectAbById(id);
        Result<MerchantStoreDTO> merchantStoreDTO= merchantStoreService.selectMerchantStoreByMId(adDTO.getModel().getMerchantId());
        AdDTO ad=adDTO.getModel();
        ad.setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
        ad.setName(merchantStoreDTO.getModel().getName());
        AdPraiseDTO praise=new AdPraiseDTO();
 		praise.setName(merchantStoreDTO.getModel().getName());
 		praise.setId(ad.getId());
		praise.setTitle(ad.getTitle());
		praise.setBeginTime(ad.getBeginTime());
		praise.setEndTime(ad.getEndTime());
		praise.setTotalPoint(ad.getTotalPoint());
		praise.setMerchantStoreId(ad.getMerchantStoreId());
		praise.setCount(ad.getNumber());
        return successGet(praise);
    }
    
    
     @ApiOperation(value = "Top3排行榜", notes = "Top3排行榜,[]（张荣成）", httpMethod = "POST")
     @Authorization
     @ApiResponse(code = HttpCode.SC_OK, message = "success")
     @RequestMapping(value = "selectMemberList/{id}", method = RequestMethod.POST)
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

}
