package com.lawu.eshop.member.api.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdPage;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdExtendService;
import com.lawu.eshop.member.api.service.AdService;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.utils.DistanceUtil;

@Service
public class AdExtendServiceImpl extends BaseController implements AdExtendService{
	
	@Autowired
	private AdService adService;
	
	@Autowired
    private MemberService memberService;
	    
    @Autowired
    private MerchantStoreService merchantStoreService;
    
    @Autowired
    private FansMerchantService fansMerchantService;

	@Override
	public Result<Page<AdDTO>> selectListByMember(AdMemberParam adMemberParam) {
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
	
	@Override
	public Result<List<AdDTO>> selectListPointTotle(AdMemberParam adMemberParam) {
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

	@Override
	public Result<Page<AdPraiseDTO>> selectPraiseListByMember(AdPraiseParam adPraiseParam) {
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
	
	@Override
	public Result<AdPraiseDTO> selectAbPraiseById(Long id) {
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
    			Result<Boolean> rs=fansMerchantService.isFansMerchant(adDTO.getMerchantId(), memberId);
    			if(rs.getModel())
    				newList.add(adDTO);
    		}else{
    			 Result<MerchantStoreDTO> rs= merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
    			 MerchantStoreDTO dto= rs.getModel();
    			 int distance= DistanceUtil.getDistance(adMemberParam.getLongitude(), adMemberParam.getLatitude(), 
    					 dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue());
    			 if(adDTO.getRadius()>distance)
    				 newList.add(adDTO);
    		}
			
		}
    	return newList;
    	
    }

	
	

	@Override
	public Result clickPraise(Long id) {
		int d = new Random().nextInt(200);
		if (d ==1){
			
		}
		Long memberId=UserUtil.getCurrentUserId(getRequest());
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		AdClickPraiseThread thread=new AdClickPraiseThread(id, memberId,userNum);
		ClickPraisePoolManager.getInstance().addThread(thread);
		return null;
	}
	

	

}
