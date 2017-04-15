package com.lawu.eshop.ad.srv.controller;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.ad.srv.service.MemberAdRecordService;
import com.lawu.eshop.ad.srv.service.PointPoolService;
import com.lawu.eshop.ad.srv.thread.AdClickPraiseThread;
import com.lawu.eshop.ad.srv.thread.ClickPraisePoolManager;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * E赚接口提供
 * @author zhangrc
 * @date 2017/4/6
 *
 */
@RestController
@RequestMapping(value = "ad/")
public class AdController extends BaseController{
	
	@Resource
	private AdService adService;
	
	@Resource
	private MemberAdRecordService memberAdRecordService;
	
	@Resource
	private PointPoolService pointPoolService;
	
	/**
	 * 添加E赚
	 * @param adParam
	 * @param merchantId
	 * @param mediaUrl
	 * @return
	 */
	@RequestMapping(value = "saveAd", method = RequestMethod.POST)
    public Result saveAd(@RequestBody AdParam adParam,@RequestParam Long merchantId, @RequestParam String mediaUrl,@RequestParam Integer count,@RequestParam String num) {
		Integer id= adService.saveAd(adParam, merchantId, mediaUrl,count,num);
		if(id>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else{
    		return successCreated(ResultCode.FAIL);
    	}
    }
	
	/**
	 * 查询商家E赚,E赞
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "selectListByMerchant", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestBody AdMerchantParam adMerchantParam,@RequestParam Long memberId) {
		Page<AdBO> pageBO=  adService.selectListByMerchant(adMerchantParam, memberId);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	/**
	 * 对广告的操作，下架和删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus/{id}", method = RequestMethod.PUT)
    public Result updateStatus(@PathVariable Long id) {
		 AdBO BO= adService.selectAbById(id);
		 Calendar calendar = Calendar.getInstance();  //得到日历  
		 calendar.setTime(new Date());//把当前时间赋给日历  
		 calendar.add(Calendar.DAY_OF_MONTH, -14);  //设置为14天前  
	     Date before14days = calendar.getTime();   //得到14天前的时间  
	     if(before14days.getTime() < BO.getBeginTime().getTime()){  
	        return successCreated(ResultCode.AD_PUT_NOT_TIME);  
	     }else{  
	    	 Integer i= adService.updateStatus(id);
	 		if(i>0){
	 			//
	     		return successCreated(ResultCode.SUCCESS);
	     	}else{
	     		return successCreated(ResultCode.FAIL);
	     	}
	     }  
		
    }
	
	/**
	 * 对广告的操作，删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable Long id) {
    	Integer i= adService.remove(id);
 		if(i>0){
     		return successDelete();
     	}else{
     		return successCreated(ResultCode.FAIL);
     	}
    }
	
	/**
	 * 广告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectAbById/{id}", method = RequestMethod.GET)
    public Result<AdDTO> selectAbById(@PathVariable Long id) {
		AdBO bo=adService.selectAbById(id);
 		return successAccepted(AdConverter.convertDTO(bo));
    }
	
	/**
	 * 点击广告
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "clickAd/{id}", method = RequestMethod.GET)
    public Result clickAd(@PathVariable Long id, @RequestParam Long memberId) {
		boolean flag=memberAdRecordService.isClickToDay(memberId, id);
		if(flag){
			return successCreated(ResultCode.AD_CLICK_EXIST);
		}else{
			Integer i=adService.clickAd(id, memberId);
			if(i>0){
	     		return successCreated(ResultCode.SUCCESS);
	     	}else{
	     		return successCreated(ResultCode.FAIL);
	     	}
		}
		
	}
	
	/**
	 * 对视频广告的审核
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "auditVideo/{id}", method = RequestMethod.PUT)
    public Result auditVideo(@PathVariable Long id) {
    	Integer i= adService.auditVideo(id);
 		if(i>0){
     		return successCreated(ResultCode.SUCCESS);
     	}else{
     		return successCreated(ResultCode.FAIL);
     	}
    }
	
	/**
	 * 运营查询广告
	 * @param adMerchantParam
	 * @return
	 */
	@RequestMapping(value = "selectListByPlatForm", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestBody AdMerchantParam adMerchantParam) {
		Page<AdBO> pageBO=  adService.selectListByPlatForm(adMerchantParam);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	
	/**
	 * 会员查询广告
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "selectListByMember", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam) {
		Page<AdBO> pageBO=  adService.selectListByMember(adMemberParam);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	
	/**
	 * 会员E赞
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectPraiseListByMember(@RequestBody AdPraiseParam adPraiseParam) {
		Page<AdBO> pageBO=  adService.selectPraiseListByMember(adPraiseParam);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	
	/**
	 * 会员E赞
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "clickPraise/{id}", method = RequestMethod.GET)
    public Result clickPraise(@PathVariable Long id,@RequestParam Long memberId,@RequestParam String num) {
		Boolean flag=pointPoolService.selectStatusByMember(id, memberId);
		if(flag)
			return successCreated(ResultCode.AD_PRAISE_POINT_GET);
		Integer  i=adService.clickPraise(id, memberId, num);
		if(i==1){
			return successCreated(ResultCode.AD_PRAISE_PUTED);
		}else if(i==2){
			return successCreated(ResultCode.SUCCESS);
		}else{
			return successCreated(ResultCode.FAIL);
		}
	
    }

}
