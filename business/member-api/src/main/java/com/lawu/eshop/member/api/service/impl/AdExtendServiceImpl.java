package com.lawu.eshop.member.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdPage;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.param.AdChoicenessParam;
import com.lawu.eshop.ad.param.AdEgainParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPointParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
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
    
    /**
     * 核心线程数，指保留的线程池大小
     */
    private static final Integer CORE_POOL_SIZE=20;
    
    /**
     *  指的是线程池的最大数
     */
    private static final Integer MAXIMUM_POLL_SIZE=60;
    
    /**
     * 指的是空闲线程结束的超时时间
     */ 
    private static final Integer KEEP_ALIVE_TIME=1;
    
    /**
     * 分子
     */
    private static final Integer A=5;
    
    /**
     * 分母
     */
    private static final Integer B=1000;
    
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(); 
    
    private ExecutorService  service=new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POLL_SIZE, KEEP_ALIVE_TIME, TimeUnit.DAYS, queue);
    
    private static Logger logger = LoggerFactory.getLogger(AdExtendServiceImpl.class);

	@Override
	public Result<Page<AdDTO>> selectListByMember(AdEgainParam adEgainParam) {
		Long memberId=UserUtil.getCurrentUserId(getRequest());
		AdMemberParam param=new AdMemberParam();
   	    param.setCurrentPage(adEgainParam.getCurrentPage());
   	    param.setPageSize(adEgainParam.getPageSize());
   	    param.setTypeEnum(adEgainParam.getTypeEnum());
   	    param.setLatitude(adEgainParam.getLatitude());
   	    param.setLongitude(adEgainParam.getLongitude());
		Result<Page<AdDTO>>  pageDTOS=adService.selectListByMember(param);
    	List<AdDTO> list =pageDTOS.getModel().getRecords();
    	List<AdDTO> newList=screem(param,list,memberId);
    	AdPage<AdDTO> adpage=new AdPage<>();
    	List<AdDTO>  screenList=adpage.page(newList, param.getPageSize(), param.getCurrentPage());
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
	public Result<List<AdDTO>> selectListPointTotle(AdPointParam adPointParam) {
		Long memberId=UserUtil.getCurrentUserId(getRequest());
		 AdMemberParam param=new AdMemberParam();
    	 param.setCurrentPage(adPointParam.getCurrentPage());
    	 param.setPageSize(adPointParam.getPageSize());
    	 param.setOrderTypeEnum(adPointParam.getOrderTypeEnum());
    	 param.setLatitude(adPointParam.getLatitude());
    	 param.setLongitude(adPointParam.getLongitude());
		Result<Page<AdDTO>>  pageDTOS=adService.selectListByMember(param);
     	List<AdDTO> list =pageDTOS.getModel().getRecords();
     	List<AdDTO> newList= new ArrayList<AdDTO>();
     	newList=screem(param,list,memberId);
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
		Long memberId=UserUtil.getCurrentUserId(getRequest());
		String num = UserUtil.getCurrentUserNum(getRequest());
		
		Future<Result<PraisePointDTO>> future=null;
		try {
			 Random random = new Random();  
			 Integer r=random.nextInt()*B;
			 if(r>0 && r<A){
				 future=service.submit(new AdClickPraiseThread(adService,id, memberId, num));
			 }else{
				 PraisePointDTO dto=new PraisePointDTO();
				 dto.setPoint(new BigDecimal(0));
				 return successCreated(dto);
			 }
			 
		} catch (RejectedExecutionException  e) {
			// 队列已满，直接失败
			return successCreated(ResultCode.FAIL);
		}
		try {
			Result<PraisePointDTO> rs=future.get();
			return rs;
				
		} catch (InterruptedException | ExecutionException e) {
			logger.error("抢赞失败",e);
		}
		return null;
	}

	@Override
	public Result<Page<AdDTO>> selectChoiceness(AdChoicenessParam adChoicenessParam) {
		Long memberId=UserUtil.getCurrentUserId(getRequest());
		AdMemberParam param=new AdMemberParam();
   	    param.setCurrentPage(adChoicenessParam.getCurrentPage());
   	    param.setPageSize(adChoicenessParam.getPageSize());
   	    param.setLatitude(adChoicenessParam.getLatitude());
   	    param.setLongitude(adChoicenessParam.getLongitude());
		Result<Page<AdDTO>>  pageDTOS=adService.selectChoiceness(param);
    	List<AdDTO> list =pageDTOS.getModel().getRecords();
    	List<AdDTO> newList=screem(param,list,memberId);
    	AdPage<AdDTO> adpage=new AdPage<>();
    	List<AdDTO>  screenList=adpage.page(newList, param.getPageSize(), param.getCurrentPage());
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
	

	

}
