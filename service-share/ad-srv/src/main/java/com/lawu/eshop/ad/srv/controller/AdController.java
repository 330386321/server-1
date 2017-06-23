package com.lawu.eshop.ad.srv.controller;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.dto.*;
import com.lawu.eshop.ad.param.*;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.ClickAdPointBO;
import com.lawu.eshop.ad.srv.bo.RedPacketInfoBO;
import com.lawu.eshop.ad.srv.bo.ViewBO;
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
import java.util.ArrayList;
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
	@RequestMapping(value = "clickAd/{id}", method = RequestMethod.PUT)
    public Result<ClickAdPointDTO> clickAd(@PathVariable Long id, @RequestParam Long memberId ,@RequestParam String num) {
		boolean flag=memberAdRecordService.isClickToDay(memberId, id);
		if(flag){
			return successCreated(ResultCode.AD_CLICK_EXIST);
		}else{
			BigDecimal point=adService.clickAd(id, memberId,num);
			ClickAdPointBO clickAdPointBO=adService.getClickAdPoint(memberId,point);
	    	ClickAdPointDTO dto=new ClickAdPointDTO();
	    	dto.setAddPoint(clickAdPointBO.getAddPoint());
	    	dto.setPoint(clickAdPointBO.getAdTotlePoint());
			if(point.compareTo(BigDecimal.valueOf(0))==1){
	     		return successCreated(dto);
	     	}else{
	     		return successCreated(ResultCode.AD_CLICK_PUTED);
	     	}
		}
	}

	/**
	 * 对视频广告的审核
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "auditVideo/{id}", method = RequestMethod.PUT)
	public Result auditVideo(@PathVariable Long id, @RequestParam Integer auditorId, @RequestParam String remark, @RequestBody AuditEnum auditEnum) {
		AdBO adBO = adService.selectById(id);
		if(adBO != null && adBO.getStatusEnum().val.byteValue() != AdStatusEnum.AD_STATUS_AUDIT.val){
			return successGet(ResultCode.AD_AUDITED);
		}
		Integer i = adService.auditVideo(id, auditorId, remark, auditEnum);
		if (i > 0) {
			return successCreated(ResultCode.SUCCESS);
		} else {
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
    public Result<Page<AdDTO>> selectPraiseListByMember(@RequestBody AdPraiseParam adPraiseParam,@RequestParam Long memberId) {
		Page<AdBO> pageBO=  adService.selectPraiseListByMember(adPraiseParam,memberId);
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
	@RequestMapping(value = "clickPraise/{id}", method = RequestMethod.PUT)
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
    	
        SolrQuery query = new SolrQuery();
        query.setParam("q", "*:*");
        if (StringUtils.isNotEmpty(adSolrParam.getAdSolrParam().getTitle())) {  //标题过滤
            query.setParam("q", "title_s:" + adSolrParam.getAdSolrParam().getTitle() + "*");
        }
        if(adSolrParam.getLatitude()!=null || adSolrParam.getLongitude()!=null){
        	String latLon = adSolrParam.getLatitude() + "," + adSolrParam.getLongitude();
        	query.setParam("pt", latLon);
        	query.setParam("fq", "{!geofilt}");
            query.setParam("sfield", "latLon_p");
            query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
    	}
        query.setSort("status_i", SolrQuery.ORDER.desc);
        List<Long> merchantIds=adSolrParam.getMerchantIds();
        String str="";
        if(merchantIds.size()>0){
        	for (Long id : merchantIds) {
    			str+="merchantId_l:"+id +" or ";
    		}
        	str=str.substring(0,str.length()-3);
        }
        if(adSolrParam.getRegionPath()!=null){
        	String[] path=adSolrParam.getRegionPath().split("/");
        	if(str!=""){
        		query.setParam(""+str+" or ('area_is:"+path[0]+"') or ('area_is:"+path[1]+"') or ('area_is:"+path[2]+"')");
        	}else{
        		query.setParam("area_is:"+path[0] +" or "+ "area_is:"+path[1] +" or "+ "area_is:"+path[2]);
        	}
        	 
        }else{
        	query.setParam(""+str+" or ('area_is:"+0+"') ");
        }
        query.setStart(adSolrParam.getOffset());
        query.setRows(adSolrParam.getPageSize());
        SolrDocumentList solrDocumentList =new SolrDocumentList();
        solrDocumentList = SolrUtil.getSolrDocsByQuery(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
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
	@RequestMapping(value = "getRedPacket", method = RequestMethod.PUT)
    public Result<PraisePointDTO> getRedPacket(@RequestParam  Long  merchantId,@RequestParam  Long  memberId,@RequestParam String memberNum) {
		Boolean flag=pointPoolService.isGetRedPacket(merchantId, memberNum);
		if(flag){
			return successCreated(ResultCode.AD_RED_PACKGE_GET);
		}
    	BigDecimal point=adService.getRedPacket(merchantId,memberId,memberNum);
    	PraisePointDTO dto=new PraisePointDTO();
    	dto.setPoint(point);
    	return successGet(dto);
    }
	
	
	/**
	 * 获取所有的广告ids
	 * @return
	 */
	@RequestMapping(value = "getAllAd", method = RequestMethod.GET)
    public Result<List<ViewDTO>> getAllAd() {
    	List<ViewBO> bos=adService.getAllAd();
    	List<ViewDTO> dtos=new ArrayList<>();
    	for (ViewBO viewBO : bos) {
    		ViewDTO dto=new ViewDTO();
    		dto.setId(viewBO.getId());
    		dto.setViewCount(viewBO.getViewCount());
    		dtos.add(dto);
		}
    	return successGet(dtos);
    }
	
	/**
	 * 修改广告浏览次数
	 * @param id
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "updateViewCount/{id}", method = RequestMethod.PUT)
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
	
	
	/**
	 * 商家端广告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectById/{id}", method = RequestMethod.GET)
    public Result<AdMerchantDetailDTO> selectById(@PathVariable Long id) {
		AdBO bo=adService.selectById(id);
 		return successAccepted(AdConverter.convertMerchantDetailAdDTO(bo));
    }

	/**
	 * 运营后台操作广告(下架、删除)
	 *
	 * @param id
	 * @param adStatusEnum
	 * @return
	 */
	@RequestMapping(value = "operatorUpdateAdStatus/{id}", method = RequestMethod.PUT)
	public Result operatorUpdateAdStatus(@PathVariable Long id, @RequestParam AdStatusEnum adStatusEnum) {
		AdBO adBO = adService.get(id);
		if(adBO == null){
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		adService.operatorUpdateAdStatus(id, adStatusEnum);
		return successCreated();
	}

	/**
	 * 查询上架中的平面视频广告
	 *
	 * @param listAdParam
	 * @return
	 */
	@RequestMapping(value = "listFlatVideoAd", method = RequestMethod.POST)
	public Result<List<AdDTO>> listFlatVideoAd(@RequestBody ListAdParam listAdParam) {
		List<AdBO> adBOS = adService.listFlatVideoAd(listAdParam);
		if(adBOS == null || adBOS.isEmpty()){
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		return successGet(AdConverter.convertDTOS(adBOS));
	}

	/**
	 * 更新平面视频广告索引
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateAdIndex/{id}", method = RequestMethod.PUT)
	public Result updateAdIndex(@PathVariable Long id) {
		adService.updateAdIndex(id);
		return successCreated();
	}

	/**
	 * 重建平面视频广告索引
	 * @return
	 */
	@RequestMapping(value = "rebuildAdIndex", method = RequestMethod.GET)
	public Result rebuildAdIndex() {
		adService.rebuildAdIndex();
		return successCreated();
	}

	/**
	 * 删除无效的平面视频广告索引
	 * @return
	 */
	@RequestMapping(value = "delInvalidAdIndex", method = RequestMethod.GET)
	public Result delInvalidAdIndex() {
		adService.delInvalidAdIndex();
		return successCreated();
	}
	
	/**
	 * 根据商家获取红包相关信息
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "getRedPacketInfo/{merchantId}", method = RequestMethod.GET)
	public Result<RedPacketInfoDTO> getRedPacketInfo(@PathVariable Long merchantId) {
		RedPacketInfoBO redPacketInfoBO= adService.getRedPacketInfo(merchantId);
		if(redPacketInfoBO==null){
			return successCreated(ResultCode.AD_RED_PACKGE_PUTED);
		}else{
			RedPacketInfoDTO redPacketInfoDTO=new RedPacketInfoDTO();
			redPacketInfoDTO.setPoint(redPacketInfoBO.getPoint());
			return successCreated(redPacketInfoDTO);
		}
		
	}
	
	/**
	 * 判断红包是否领取完成
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "isExistsRedPacket/{merchantId}", method = RequestMethod.GET)
	public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable Long merchantId) {
		Boolean flag= adService.isExistsRedPacket(merchantId);
		IsExistsRedPacketDTO dto=new IsExistsRedPacketDTO();
		dto.setIsExistsRedPacket(flag);
		return successCreated(dto);
	}
	
	
	/**
	 * 商家批量删除广告
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "batchDeleteAd", method = RequestMethod.DELETE)
	public Result batchDeleteAd(@RequestParam("ids") List<Long> ids) {
		adService.batchDeleteAd(ids);
		return successDelete();
	}

}
