package com.lawu.eshop.ad.srv.controller;

import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.dto.*;
import com.lawu.eshop.ad.param.*;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.ClickAdPointBO;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.ad.srv.service.MemberAdRecordService;
import com.lawu.eshop.ad.srv.service.PointPoolService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.solr.SolrUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private AdSrvConfig adSrvConfig;
	
	/**
	 * 添加E赚
	 * @param adSaveParam
	 * @return
	 */
	@RequestMapping(value = "saveAd", method = RequestMethod.POST)
    public Result saveAd(@RequestBody AdSaveParam adSaveParam) {
		if(adSaveParam.getAdParam().getTypeEnum().val==4){
			Integer count=adService.selectRPIsSend(adSaveParam.getMerchantId());
			if(count>0){
				return successCreated(ResultCode.AD_RED_PACKGE_EXIST);
			}
		}
		Integer id= adService.saveAd(adSaveParam);
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
    public Result<Page<AdMerchantDTO>> selectListByMerchant(@RequestBody AdMerchantParam adMerchantParam,@RequestParam Long memberId) {
		Page<AdBO> pageBO=  adService.selectListByMerchant(adMerchantParam, memberId);
		Page<AdMerchantDTO> pageDTO=new Page<AdMerchantDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertMerchantAdDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	/**
	 * 对广告的操作，下架和删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus/{id}", method = RequestMethod.PUT)
    public Result updateStatus(@PathVariable Long id ) {
		 AdBO BO= adService.get(id);
		 Calendar calendar = Calendar.getInstance();  //得到日历  
		 calendar.setTime(new Date());//把当前时间赋给日历  
		 calendar.add(Calendar.DAY_OF_MONTH, -14);  //设置为14天前  
	     Date before14days = calendar.getTime();   //得到14天前的时间  
	     if(BO.getBeginTime()!=null && before14days.getTime() < BO.getBeginTime().getTime()){  
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
	@RequestMapping(value = "remove/{id}", method = RequestMethod.PUT)
    public Result remove(@PathVariable Long id) {
    	Integer i= adService.remove(id);
 		if(i>0){
     		return successCreated();
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
    public Result<AdDTO> selectAbById(@PathVariable Long id,@RequestParam Long memberId) {
		AdBO bo=adService.selectAbById(id,memberId);
 		return successAccepted(AdConverter.convertDTO(bo));
    }
	
	/**
	 * 点击广告
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "clickAd/{id}", method = RequestMethod.GET)
    public Result<ClickAdPointDTO> clickAd(@PathVariable Long id, @RequestParam Long memberId ,@RequestParam String num) {
		boolean flag=memberAdRecordService.isClickToDay(memberId, id);
		if(flag){
			return successCreated(ResultCode.AD_CLICK_EXIST);
		}else{
			Integer i=adService.clickAd(id, memberId,num);
			ClickAdPointBO clickAdPointBO=adService.getClickAdPoint(memberId,id);
	    	ClickAdPointDTO dto=new ClickAdPointDTO();
	    	dto.setAddPoint(clickAdPointBO.getAddPoint());
	    	dto.setAdTotlePoint(clickAdPointBO.getAdTotlePoint());
			if(i>0){
	     		return successCreated(dto);
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
    public Result auditVideo(@PathVariable Long id,@RequestBody AuditEnum auditEnum) {
    	Integer i= adService.auditVideo(id,auditEnum);
 		if(i>0){
     		return successCreated(ResultCode.SUCCESS);
     	}else{
     		return successCreated(ResultCode.FAIL);
     	}
    }
	
	/**
	 * 运营查询广告
	 * @param adPlatParam
	 * @return
	 */
	@RequestMapping(value = "selectListByPlatForm", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestBody AdFindParam adPlatParam) {
		Page<AdBO> pageBO=  adService.selectListByPlatForm(adPlatParam);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	
	/**
	 * 会员查询广告
	 * @param adMemberParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "selectListByMember", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam,@RequestParam Long memberId) {
		Page<AdBO> pageBO=  adService.selectListByMember(adMemberParam,memberId);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	
	/**
	 * 会员查询广告
	 * @param adMemberParam
	 * @return
	 */
	@RequestMapping(value = "selectChoiceness", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectChoiceness(@RequestBody AdMemberParam adMemberParam) {
		Page<AdBO> pageBO=  adService.selectChoiceness(adMemberParam);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return  successAccepted(pageDTO);
    }
	
	
	/**
	 * 会员E赞
	 * @param adPraiseParam
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
	 * @param id
	 * @param memberId
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "clickPraise/{id}", method = RequestMethod.GET)
    public Result<PraisePointDTO> clickPraise(@PathVariable Long id,@RequestParam Long memberId,@RequestParam String num) {
		Boolean flag=pointPoolService.selectStatusByMember(id, memberId);
		if(flag)
			return successCreated(ResultCode.AD_PRAISE_POINT_GET);
		BigDecimal  point=adService.clickPraise(id, memberId, num);
		if(point.compareTo(new BigDecimal(0))==0){
			return successCreated(ResultCode.AD_PRAISE_PUTED);
		}else{
			PraisePointDTO dto=new PraisePointDTO();
			dto.setPoint(point);
			return successCreated(dto);
		}
	
	}
	
	
	 /**
     * 根据广告名称查询广告
     *
     * @param adSolrParam
     * @return
     */
    @RequestMapping(value = "queryAdByTitle", method = RequestMethod.POST)
    public Result<Page<AdSolrDTO>> queryAdByTitle(@RequestBody AdsolrFindParam adSolrParam) {
    	String latLon = adSolrParam.getLatitude() + "," + adSolrParam.getLongitude();
        SolrQuery query = new SolrQuery();
        query.setParam("q", "*:*");
        if (StringUtils.isNotEmpty(adSolrParam.getTitle())) {  //标题过滤
            query.setParam("q", "title_s:" + adSolrParam.getTitle() + "*");
        }
        query.setSort("status_s", SolrQuery.ORDER.desc);
        query.setParam("pt", latLon);
        query.setParam("fq", "{!geofilt}");
        query.setParam("sfield", "latLon_p");
        query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
        String[] path=adSolrParam.getRegionPath().split("/");
        query.setQuery("area_ss : 0");
        List<Long> merchantIds=adSolrParam.getMerchantIds();
        String str="";
        if(!merchantIds.isEmpty()){
        	for (Long id : merchantIds) {
    			str+="merchantId_s:"+id +" or ";
    		}
        	str=str.substring(0,str.length()-3);
        }
        query.setQuery(""+str+" or ('area_ss:"+path[0]+"') or ('area_ss:"+path[1]+"') or ('area_ss:"+path[2]+"')");
        query.setStart(adSolrParam.getOffset());
        query.setRows(adSolrParam.getPageSize());
        SolrDocumentList solrDocumentList =new SolrDocumentList();
        solrDocumentList = SolrUtil.getSolrDocsByQuery(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore());
        Page<AdSolrDTO> page = new Page<AdSolrDTO>();
        page.setRecords(AdConverter.convertDTO(solrDocumentList));
        if(solrDocumentList==null){
        	page.setTotalCount(0);
        }else{
        	page.setTotalCount((int) solrDocumentList.getNumFound());
        }
        page.setCurrentPage(adSolrParam.getCurrentPage());
        return successGet(page);
    }
    
    
    /**
     * 领取红包
     * @param merchantId
     * @param memberId
     * @param memberNum
     * @return
     */
	@RequestMapping(value = "getRedPacket", method = RequestMethod.GET)
    public Result<PraisePointDTO> getRedPacket(@RequestParam  Long  merchantId,@RequestParam  Long  memberId,@RequestParam String memberNum) {
    	BigDecimal point=adService.getRedPacket(merchantId,memberId,memberNum);
    	PraisePointDTO dto=new PraisePointDTO();
    	dto.setPoint(point);
    	return successGet(dto);
    }
	
	/**
	 * 点击广告获取到的积分
	 * @param memberId
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getClickAdPoint/{id}", method = RequestMethod.GET)
    public Result<ClickAdPointDTO> getClickAdPoint(@RequestParam  Long  memberId,@PathVariable Long id) {
		ClickAdPointBO clickAdPointBO=adService.getClickAdPoint(memberId,id);
    	ClickAdPointDTO dto=new ClickAdPointDTO();
    	dto.setAddPoint(clickAdPointBO.getAddPoint());
    	dto.setAdTotlePoint(clickAdPointBO.getAdTotlePoint());
    	return successGet(dto);
    }
	
	/**
	 * 获取所有的广告ids
	 * @return
	 */
	@RequestMapping(value = "getAllAd", method = RequestMethod.GET)
    public Result<List<Long>> getAllAd() {
    	List<Long> ids=adService.getAllAd();
    	return successGet(ids);
    }
	
	/**
	 * 修改广告浏览次数
	 * @param id
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "updateViewCount/{id}", method = RequestMethod.GET)
    public Result<List<Long>> updateViewCount(@PathVariable  Long  id,@RequestParam Integer count) {
    	adService.updateViewCount(id, count);
    	return successCreated();
    }

	/**
	 * 运营后台查询广告列表
	 *
	 * @param listAdParam
	 * @return
	 */
	@RequestMapping(value = "listAllAd", method = RequestMethod.POST)
	public Result<Page<AdDTO>> listAllAd(@RequestBody ListAdParam listAdParam) {
		Page<AdBO> adBOPage = adService.listAllAd(listAdParam);
		Page<AdDTO> page = new Page<>();
		page.setCurrentPage(adBOPage.getCurrentPage());
		page.setRecords(AdConverter.convertDTOS(adBOPage.getRecords()));
		page.setTotalCount(adBOPage.getTotalCount());
		return successGet(page);
	}

	/**
	 * 根据ID查询广告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getAd/{id}", method = RequestMethod.GET)
	public Result<AdDTO> getAdById(@PathVariable Long id) {
		AdBO adBO = adService.get(id);
		if(adBO == null){
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		return successGet(AdConverter.convertDTO(adBO));
	}

}
