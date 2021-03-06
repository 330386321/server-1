package com.lawu.eshop.ad.srv.converter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.lawu.eshop.ad.constants.AdEgainTypeEnum;
import com.lawu.eshop.ad.constants.AdPraiseStatusEnum;
import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.ManageTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.constants.RelateTypeEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdDetailDTO;
import com.lawu.eshop.ad.dto.AdEgainDTO;
import com.lawu.eshop.ad.dto.AdEgainQueryDTO;
import com.lawu.eshop.ad.dto.AdFlatVideoDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.AdPointDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.ChoicenessAdDTO;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.AdDetailBO;
import com.lawu.eshop.ad.srv.bo.AdEgainBO;
import com.lawu.eshop.ad.srv.bo.AdEgainDetailBO;
import com.lawu.eshop.ad.srv.bo.AdPointBO;
import com.lawu.eshop.ad.srv.bo.AdPraiseBO;
import com.lawu.eshop.ad.srv.bo.ChoicenessAdBO;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.framework.core.page.Page;

/**
 * E赚实体转化
 * @author zhangrc
 * @date 2017/4/6
 */
public class AdConverter {
	
	/**
	 * DO转BO
	 * @param adDO
	 * @return
	 */
	public static AdBO convertBO(AdDO adDO){
		AdBO adBO=new AdBO();
		if(adDO==null){
			return adBO;
		}
		adBO.setId(adDO.getId());
		adBO.setAdCount(adDO.getAdCount());
		adBO.setBeginTime(adDO.getBeginTime());
		adBO.setMediaUrl(adDO.getMediaUrl());
		adBO.setMerchantId(adDO.getMerchantId());
		adBO.setGmtCreate(adDO.getGmtCreate());
		adBO.setAdCount(adDO.getAdCount());
		adBO.setPoint(adDO.getPoint());
		adBO.setTitle(adDO.getTitle());
		adBO.setTypeEnum(AdTypeEnum.getEnum(adDO.getType()));
		adBO.setPutWayEnum(PutWayEnum.getEnum(adDO.getPutWay()));
		adBO.setStatusEnum(AdStatusEnum.getEnum(adDO.getStatus()));
		adBO.setTotalPoint(adDO.getTotalPoint());
		adBO.setViewCount(adDO.getViewcount());
		adBO.setMerchantLatitude(adDO.getMerchantLatitude());
		adBO.setMerchantLongitude(adDO.getMerchantLongitude());
		adBO.setMerchantRegionPath(adDO.getMerchantRegionPath());
		adBO.setAreas(adDO.getAreas());
		adBO.setRadius(adDO.getRadius());
		adBO.setContent(adDO.getContent());
		adBO.setVideoImgUrl(adDO.getVideoImgUrl());
		adBO.setAreas(adDO.getAreas());
		adBO.setRadius(adDO.getRadius());
		adBO.setContent(adDO.getContent());
		adBO.setRegionName(adDO.getRegionName());
		adBO.setAuditorId(adDO.getAuditorId());
		adBO.setRemark(adDO.getRemark());
		adBO.setRelateId(adDO.getRelateId());
		adBO.setRelateType(RelateTypeEnum.getEnum(adDO.getRelateType()));
		adBO.setMerchantStoreId(adDO.getMerchantStoreId());
		adBO.setMerchantStoreName(adDO.getMerchantStoreName());
		adBO.setManageType(ManageTypeEnum.getEnum(adDO.getManageType()));
		adBO.setLogoUrl(adDO.getLogoUrl());
		adBO.setFileSize(adDO.getFileSize());
		adBO.setVideoTime(adDO.getFileTime());
        return adBO;
		
	}
	
	/**
	 * DOS转BOS
	 * @param adDOS
	 * @return
	 */
	public static List<AdBO> convertBOS(List<AdDO> adDOS){
		List<AdBO> BOS=new ArrayList<AdBO>();
		if(adDOS==null){
			return BOS;
		}
		for (AdDO adDO : adDOS) {
			AdBO BO=convertBO(adDO);
			BOS.add(BO);
		}
		return BOS;
	}
	
	/**
	 * BO转DTO
	 * @param adBO
	 * @return
	 */
	public static AdDTO convertDTO(AdBO adBO){
		AdDTO adDTO=new AdDTO();
		if(adBO==null){
			return adDTO;
		}
		adDTO.setId(adBO.getId());
		adDTO.setAdCount(adBO.getAdCount());
		adDTO.setBeginTime(adBO.getBeginTime());
		adDTO.setMediaUrl(adBO.getMediaUrl());
		adDTO.setMerchantId(adBO.getMerchantId());
		adDTO.setGmtCreate(adBO.getGmtCreate());
		adDTO.setAdCount(adBO.getAdCount());
		adDTO.setPoint(adBO.getPoint());
		adDTO.setTitle(adBO.getTitle());
		adDTO.setTypeEnum(adBO.getTypeEnum());
		adDTO.setPutWayEnum(adBO.getPutWayEnum());
		adDTO.setStatusEnum(adBO.getStatusEnum());
		adDTO.setTotalPoint(adBO.getTotalPoint());
		adDTO.setNumber(adBO.getNumber());
		adDTO.setIsFavorite(adBO.getIsFavorite());
		adDTO.setViewCount(adBO.getViewCount());
		adDTO.setIsPraise(adBO.getIsPraise());
		adDTO.setAreas(adBO.getAreas());
		adDTO.setRadius(adBO.getRadius());
		adDTO.setContent(adBO.getContent());
		adDTO.setIsFavorite(adBO.getIsFavorite());
		adDTO.setIsPraise(adBO.getIsPraise());
		adDTO.setVideoImgUrl(adBO.getVideoImgUrl());
		adDTO.setAuditorId(adBO.getAuditorId());
		adDTO.setRemark(adBO.getRemark());
		adDTO.setIsClickAd(adBO.getIsClickAd());
		adDTO.setMerchantStoreId(adBO.getMerchantStoreId());
		adDTO.setName(adBO.getMerchantStoreName());
		adDTO.setManageTypeEnum(adBO.getManageType());
		adDTO.setLogoUrl(adBO.getLogoUrl());
		Date date=new Date();
		if(adBO.getTypeEnum().getVal()==3 && adBO.getStatusEnum().val==2){ //结束倒计时
			Long time=adBO.getBeginTime().getTime()+ (20*60*1000)-date.getTime();
			if(time>0){
				adDTO.setNeedBeginTime(time);
			}else{
				adDTO.setNeedBeginTime(Long.parseLong("0"));
			}
		}else if(adBO.getTypeEnum().getVal()==3 && adBO.getStatusEnum().val==1){ //开枪倒计时
			Long time=adBO.getBeginTime().getTime()-date.getTime();
			adDTO.setNeedBeginTime(time);
			
		}
		if(adBO.getAreas()!=null){
			adDTO.setAreas(adBO.getAreas());
		}
		if(adBO.getContent()!=null){
			adDTO.setContent(adBO.getContent());
		}
		if(adBO.getRadius()!=null){
			adDTO.setRadius(adBO.getRadius());
		}
        return adDTO;
	}
	
	/**
	 * BOS转DTOS
	 * @param adBOS
	 * @return
	 */
	public static List<AdDTO> convertDTOS(List<AdBO> adBOS){
		List<AdDTO> DTOS=new ArrayList<AdDTO>();
		if(adBOS==null){
			return DTOS;
		}
		for (AdBO adBO : adBOS) {
			AdDTO DTO=convertDTO(adBO);
			DTOS.add(DTO);
		}
		return DTOS;
	}
	
	public static List<AdMerchantDTO> convertMerchantAdDTOS(List<AdBO> adBOS){
		List<AdMerchantDTO> DTOS=new ArrayList<AdMerchantDTO>();
		if(adBOS==null){
			return DTOS;
		}
		for (AdBO adBO : adBOS) {
			AdMerchantDTO dto=new AdMerchantDTO();
			dto.setId(adBO.getId());
			dto.setTitle(adBO.getTitle());
			dto.setMerchantId(adBO.getMerchantId());
			dto.setGmtCreate(adBO.getGmtCreate());
			dto.setPutWayEnum(adBO.getPutWayEnum());
			dto.setStatusEnum(adBO.getStatusEnum());
			dto.setTotalPoint(adBO.getTotalPoint());
			dto.setTypeEnum(adBO.getTypeEnum());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
			dto.setExpandOrder(sdf.format(adBO.getGmtCreate())+adBO.getId());
			dto.setMemberCount(adBO.getAdCount());
			dto.setMediaUrl(adBO.getMediaUrl());
			dto.setVideoImgUrl(adBO.getVideoImgUrl());
			dto.setRadius(adBO.getRadius());
			dto.setRegionName(adBO.getRegionName());
			dto.setAdCount(adBO.getAdCount());
			Calendar calendar = Calendar.getInstance();  //得到日历
			calendar.setTime(new Date());//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -14);  //设置为14天前
		    Date before14days = calendar.getTime();   //得到14天前的时间
		    if(adBO.getBeginTime()!=null && before14days.getTime() < adBO.getBeginTime().getTime()){
		    	dto.setIsShowDown(false);
		    }else{
		    	dto.setIsShowDown(true);
		    }
			DTOS.add(dto);
		}
		return DTOS;
	}
	
	public static AdMerchantDTO convertMerchantAdDTO(AdBO adBO){
		AdMerchantDTO dto=new AdMerchantDTO();
		if(adBO==null){
			return dto;
		}
		dto.setId(adBO.getId());
		dto.setTitle(adBO.getTitle());
		dto.setMerchantId(adBO.getMerchantId());
		dto.setGmtCreate(adBO.getGmtCreate());
		dto.setPutWayEnum(adBO.getPutWayEnum());
		dto.setStatusEnum(adBO.getStatusEnum());
		dto.setTotalPoint(adBO.getTotalPoint());
		dto.setTypeEnum(adBO.getTypeEnum());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
		dto.setExpandOrder(sdf.format(adBO.getGmtCreate())+adBO.getId());
		dto.setMemberCount(adBO.getAdCount());
		return dto;
	}

	public static List<AdPraiseDTO> convertPraiseDTOS(List<AdBO> records) {
		
		List<AdPraiseDTO> DTOS=new ArrayList<AdPraiseDTO>();
		if(records==null){
			return DTOS;
		}
		for (AdBO adBO : records) {
			AdPraiseDTO praise=new AdPraiseDTO();
			praise.setId(adBO.getId());
			praise.setTitle(adBO.getTitle());
			praise.setBeginTime(adBO.getBeginTime());
			praise.setTotalPoint(adBO.getTotalPoint());
		}
		return DTOS;
	}
	
	
	 /**
     * SolrInputDocument
     *
     * @param adDO
     * @return
     */
	 public static SolrInputDocument convertSolrInputDocument(AdDO adDO) {
		 SolrInputDocument document = new SolrInputDocument();
		 document.addField("id", adDO.getId());
		 document.addField("merchantId_l", adDO.getMerchantId());
		 document.addField("mediaUrl_s", adDO.getMediaUrl());
		 document.addField("videoImgUrl_s", adDO.getVideoImgUrl());
		 document.addField("title_s", adDO.getTitle());
		 document.addField("content_s", adDO.getContent());
		 document.addField("status_i", adDO.getStatus());
		 document.addField("hits_i", adDO.getHits());
		 document.addField("viewCount_i", adDO.getViewcount());
		 document.addField("radius_i", adDO.getRadius());
		 document.addField("type_i", adDO.getType());
		 document.addField("putWay_i", adDO.getPutWay());
		 document.addField("point_d", adDO.getPoint() == null ? 0.0 : adDO.getPoint().doubleValue());
		 document.addField("totalPoint_d", adDO.getTotalPoint() == null ? 0.0 : adDO.getTotalPoint().doubleValue());
		 document.addField("storeName_s", adDO.getMerchantStoreName());
		 document.addField("storeLogo_s", adDO.getLogoUrl());
		 if (adDO.getMerchantLatitude() != null && adDO.getMerchantLongitude() != null) {
			 document.addField("latLon_p", adDO.getMerchantLatitude() + "," + adDO.getMerchantLongitude());
		 }
		 if (adDO.getType().byteValue() == AdTypeEnum.AD_TYPE_PRAISE.getVal() && adDO.getBeginTime() != null) {
			 document.addField("beginTime_l", adDO.getBeginTime().getTime());
		 }
		 if (adDO.getPutWay().byteValue() == PutWayEnum.PUT_WAY_AREAS.val) {
			 if (StringUtils.isNotEmpty(adDO.getAreas())) {
				 String[] location = adDO.getAreas().split(",");
				 for (String area : location) {
					 document.addField("area_is", area);
				 }
			 } else {
				 document.addField("area_is", 0);
			 }
		 }
		 return document;
	 }

	public static AdSolrDTO convertDTO(SolrDocument solrDocument) {
		if (solrDocument == null) {
			return null;
		}
		AdSolrDTO adSolrDTO = new AdSolrDTO();
		adSolrDTO.setId(solrDocument.get("id") == null ? 0 : Long.valueOf(solrDocument.get("id").toString()));
		adSolrDTO.setMerchantStoreId(solrDocument.get("merchantId_l") == null ? 0 : Long.valueOf(solrDocument.get("merchantId_l").toString()));
		adSolrDTO.setMediaUrl(solrDocument.get("mediaUrl_s") == null ? "" : solrDocument.get("mediaUrl_s").toString());
		adSolrDTO.setVideoImgUrl(solrDocument.get("videoImgUrl_s") == null ? "" : solrDocument.get("videoImgUrl_s").toString());
		adSolrDTO.setTitle(solrDocument.get("title_s") == null ? "" : solrDocument.get("title_s").toString());
		adSolrDTO.setContent(solrDocument.get("content_s") == null ? "" : solrDocument.get("content_s").toString());
		adSolrDTO.setMerchantStoreName(solrDocument.get("storeName_s").toString());
		adSolrDTO.setLogoUrl(solrDocument.get("storeLogo_s").toString());
		adSolrDTO.setTypeEnum(AdTypeEnum.getEnum(Byte.valueOf(solrDocument.get("type_i").toString())));
		adSolrDTO.setStatusEnum(AdStatusEnum.getEnum(Byte.valueOf(solrDocument.get("status_i").toString())));
		adSolrDTO.setHits(solrDocument.get("hits_i") == null ? 0 : Integer.valueOf(solrDocument.get("hits_i").toString()));
		adSolrDTO.setCount(solrDocument.get("viewCount_i") == null ? 0 : Integer.valueOf(solrDocument.get("viewCount_i").toString()));
		adSolrDTO.setPoint(solrDocument.get("point_d") == null ? 0.0 : Double.valueOf(solrDocument.get("point_d").toString()));
		adSolrDTO.setTotalPoint(solrDocument.get("totalPoint_d") == null ? 0.0 : Double.valueOf(solrDocument.get("totalPoint_d").toString()));
		return adSolrDTO;
	}

	/**
     * SolrInputDocument
     *
     * @param solrDocumentList
     * @return
     */
	public static List<AdSolrDTO> convertDTO(SolrDocumentList solrDocumentList) {
		List<AdSolrDTO> adSolrDTOS = new ArrayList<>();
		if (solrDocumentList == null) {
			return adSolrDTOS;
		}
		for (SolrDocument solrDocument : solrDocumentList) {
			AdSolrDTO adSolrDTO = convertDTO(solrDocument);
			adSolrDTOS.add(adSolrDTO);
		}
		return adSolrDTOS;
	}

	public static AdMerchantDetailDTO convertMerchantDetailAdDTO(AdBO adBO) {
		AdMerchantDetailDTO dto=new AdMerchantDetailDTO();
		if(adBO==null){
			return dto;
		}
		dto.setId(adBO.getId());
		dto.setTitle(adBO.getTitle());
		dto.setPutWayEnum(adBO.getPutWayEnum());
		dto.setStatusEnum(adBO.getStatusEnum());
		dto.setTotalPoint(adBO.getTotalPoint());
		dto.setTypeEnum(adBO.getTypeEnum());
		dto.setBeginTime(adBO.getBeginTime());
		dto.setAdCount(adBO.getAdCount());
		dto.setAreas(adBO.getAreas());
		dto.setMediaUrl(adBO.getMediaUrl());
		dto.setPoint(adBO.getPoint());
		dto.setRadius(adBO.getRadius());
		dto.setContent(adBO.getContent());
		dto.setVideoImgUrl(adBO.getVideoImgUrl());
		dto.setRegionName(adBO.getRegionName());
		dto.setProductId(adBO.getRelateId());
		dto.setRelateType(adBO.getRelateType());
		dto.setFileSize(adBO.getFileSize());
		dto.setVideoTime(adBO.getVideoTime());
		return dto;
	}

	public static AdDetailBO convertDetailBO(AdDO adDO) {
		AdDetailBO adDetailBO=new AdDetailBO();
		if(adDO==null){
			return adDetailBO;
		}
		adDetailBO.setId(adDO.getId());
		adDetailBO.setAdCount(adDO.getAdCount());
		adDetailBO.setMediaUrl(adDO.getMediaUrl());
		adDetailBO.setGmtCreate(adDO.getGmtCreate());
		adDetailBO.setAdCount(adDO.getAdCount());
		adDetailBO.setPoint(adDO.getPoint());
		adDetailBO.setTitle(adDO.getTitle());
		adDetailBO.setTypeEnum(AdTypeEnum.getEnum(adDO.getType()));
		adDetailBO.setPutWayEnum(PutWayEnum.getEnum(adDO.getPutWay()));
		adDetailBO.setStatusEnum(AdStatusEnum.getEnum(adDO.getStatus()));
		adDetailBO.setTotalPoint(adDO.getTotalPoint());
		adDetailBO.setRadius(adDO.getRadius());
		adDetailBO.setContent(adDO.getContent());
		adDetailBO.setVideoImgUrl(adDO.getVideoImgUrl());
		adDetailBO.setRadius(adDO.getRadius());
		adDetailBO.setRegionName(adDO.getRegionName());
		adDetailBO.setRemark(adDO.getRemark());
		adDetailBO.setAuditTime(adDO.getAuditTime());
		adDetailBO.setBeginTime(adDO.getBeginTime());
		adDetailBO.setProductId(adDO.getRelateId());
		adDetailBO.setStoreName(adDO.getMerchantStoreName());
        return adDetailBO;
		
		
	}
	
	public static AdDetailDTO convertDetailDTO(AdDetailBO adDetailBO) {
		AdDetailDTO adDetailDTO=new AdDetailDTO();
		if(adDetailBO==null){
			return adDetailDTO;
		}
		adDetailDTO.setId(adDetailBO.getId());
		adDetailDTO.setAdCount(adDetailBO.getAdCount());
		adDetailDTO.setMediaUrl(adDetailBO.getMediaUrl());
		adDetailDTO.setGmtCreate(adDetailBO.getGmtCreate());
		adDetailDTO.setAdCount(adDetailBO.getAdCount());
		adDetailDTO.setPoint(adDetailBO.getPoint());
		adDetailDTO.setTitle(adDetailBO.getTitle());
		adDetailDTO.setTypeEnum(adDetailBO.getTypeEnum());
		adDetailDTO.setPutWayEnum(adDetailBO.getPutWayEnum());
		adDetailDTO.setStatusEnum(adDetailBO.getStatusEnum());
		adDetailDTO.setTotalPoint(adDetailBO.getTotalPoint());
		adDetailDTO.setRadius(adDetailBO.getRadius());
		adDetailDTO.setContent(adDetailBO.getContent());
		adDetailDTO.setVideoImgUrl(adDetailBO.getVideoImgUrl());
		adDetailDTO.setRadius(adDetailBO.getRadius());
		adDetailDTO.setRegionName(adDetailBO.getRegionName());
		adDetailDTO.setRemark(adDetailBO.getRemark());
		adDetailDTO.setAuditTime(adDetailBO.getAuditTime());
		adDetailDTO.setAlreadyGetCount(adDetailBO.getAlreadyGetCount());
		adDetailDTO.setNotGetCount(adDetailBO.getNotGetCount());
		adDetailDTO.setAlreadyGetPoint(adDetailBO.getAlreadyGetPoint());
		adDetailDTO.setNotGetPoint(adDetailBO.getNotGetPoint());
		adDetailDTO.setBeginTime(adDetailBO.getBeginTime());
		adDetailDTO.setProductId(adDetailBO.getProductId());
		adDetailDTO.setRedPacketAdFileUrl(adDetailBO.getRedPacketAdFileUrl());
		adDetailDTO.setFileType(adDetailBO.getFileType());
		adDetailDTO.setStoreName(adDetailBO.getStoreName());
        return adDetailDTO;
		
		
	}

	/**
	 * AdDO转AdEgainBO
	 * @param adDO
	 * @param isFavorite
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月18日
	 */
	public static AdEgainBO convert(AdDO adDO, Boolean isFavorite) {
		AdEgainBO rtn = null;
		if (adDO == null) {
			return rtn;
		}
		rtn = new AdEgainBO();
		rtn.setId(adDO.getId());
		rtn.setMediaUrl(adDO.getMediaUrl());
		rtn.setTitle(adDO.getTitle());
		rtn.setType(AdEgainTypeEnum.getEnum(adDO.getType()));
		rtn.setContent(adDO.getContent());
		rtn.setVideoImgUrl(adDO.getVideoImgUrl());
		rtn.setViewcount(adDO.getViewcount());
		rtn.setIsFavorite(isFavorite);
		rtn.setLogoUrl(adDO.getLogoUrl());
		rtn.setManageType(ManageTypeEnum.getEnum(adDO.getManageType()));
		rtn.setMerchantStoreId(adDO.getMerchantStoreId());
		rtn.setMerchantStoreName(adDO.getMerchantStoreName());
        return rtn;
	}

	/**
	 * Page&lt;AdEgainBO&gt;转Page&lt;AdEgainQueryDTO&gt;
	 * @param pageAdBO
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月18日
	 */
	public static Page<AdEgainQueryDTO> convertAdEgainQueryDTOPage(Page<AdEgainBO> pageAdBO) {
		Page<AdEgainQueryDTO> rtn = new Page<>();
		rtn.setCurrentPage(pageAdBO.getCurrentPage());
		rtn.setTotalCount(pageAdBO.getTotalCount());
		rtn.setRecords(new ArrayList<>());
		for (AdEgainBO item : pageAdBO.getRecords()) {
			AdEgainQueryDTO adEgainQueryDTO = new AdEgainQueryDTO();
			adEgainQueryDTO.setContent(item.getContent());
			adEgainQueryDTO.setId(item.getId());
			adEgainQueryDTO.setIsFavorite(item.getIsFavorite());
			adEgainQueryDTO.setLogoUrl(item.getLogoUrl());
			adEgainQueryDTO.setManageType(item.getManageType());
			adEgainQueryDTO.setMediaUrl(item.getMediaUrl());
			adEgainQueryDTO.setMerchantStoreId(adEgainQueryDTO.getMerchantStoreId());
			adEgainQueryDTO.setMerchantStoreName(adEgainQueryDTO.getMerchantStoreName());
			adEgainQueryDTO.setTitle(item.getTitle());
			adEgainQueryDTO.setType(adEgainQueryDTO.getType());
			adEgainQueryDTO.setVideoImgUrl(item.getVideoImgUrl());
			adEgainQueryDTO.setViewcount(item.getViewcount());
			rtn.getRecords().add(adEgainQueryDTO);
		}
        return rtn;
	}

	/**
	 * &lt;List&gt;AdDO转&lt;List&gt;AdPointBO
	 * @param adDOList
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月19日
	 */
	public static List<AdPointBO> convert(List<AdDO> adDOList) {
		List<AdPointBO> rtn = null;
		if (adDOList == null || adDOList.isEmpty()) {
			return rtn;
		}
		rtn = new ArrayList<>();
		for (AdDO item : adDOList) {
			AdPointBO adPointBO = new AdPointBO();
			adPointBO.setId(item.getId());
			if (AdTypeEnum.AD_TYPE_FLAT.getVal().equals(item.getType())) {
				adPointBO.setImgUrl(item.getMediaUrl());
			} else if (AdTypeEnum.AD_TYPE_VIDEO.getVal().equals(item.getType())) {
				adPointBO.setImgUrl(item.getVideoImgUrl());
			}
			adPointBO.setTitle(item.getTitle());
			adPointBO.setLogoUrl(item.getLogoUrl());
			adPointBO.setManageType(ManageTypeEnum.getEnum(item.getManageType()));
			adPointBO.setMerchantStoreId(item.getMerchantStoreId());
			adPointBO.setTotalPoint(item.getTotalPoint());
			rtn.add(adPointBO);
		}
        return rtn;
	}

	/**
	 * &lt;List&gt;AdPointBO转&lt;List&gt;AdPointDTO
	 * @param adPointBOList
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月19日
	 */
	public static List<AdPointDTO> convertAdPointDTOList(List<AdPointBO> adPointBOList) {
		List<AdPointDTO> rtn = new ArrayList<>();
		if (adPointBOList == null || adPointBOList.isEmpty()) {
			return rtn;
		}
		for (AdPointBO item : adPointBOList) {
			AdPointDTO adPointDTO = new AdPointDTO();
			adPointDTO.setId(item.getId());
			adPointDTO.setImgUrl(item.getImgUrl());
			adPointDTO.setTitle(item.getTitle());
			adPointDTO.setLogoUrl(item.getLogoUrl());
			adPointDTO.setManageType(item.getManageType());
			adPointDTO.setMerchantStoreId(item.getMerchantStoreId());
			adPointDTO.setTotalPoint(item.getTotalPoint());
			rtn.add(adPointDTO);
		}
        return rtn;
	}

	/**
	 * AdDO转ChoicenessAdBO
	 * @param adDO
	 * @param isFavorite
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月18日
	 */
	public static ChoicenessAdBO convertChoicenessAdBO(AdDO adDO, Boolean isFavorite) {
		ChoicenessAdBO rtn = null;
		if (adDO == null) {
			return rtn;
		}
		rtn = new ChoicenessAdBO();
		rtn.setId(adDO.getId());
		rtn.setMediaUrl(adDO.getMediaUrl());
		rtn.setTitle(adDO.getTitle());
		rtn.setType(AdEgainTypeEnum.getEnum(adDO.getType()));
		rtn.setContent(adDO.getContent());
		rtn.setVideoImgUrl(adDO.getVideoImgUrl());
		rtn.setViewcount(adDO.getViewcount());
		rtn.setIsFavorite(isFavorite);
		rtn.setLogoUrl(adDO.getLogoUrl());
		rtn.setManageType(ManageTypeEnum.getEnum(adDO.getManageType()));
		rtn.setMerchantStoreId(adDO.getMerchantStoreId());
		rtn.setMerchantStoreName(adDO.getMerchantStoreName());
		rtn.setBeginTime(adDO.getBeginTime());
		rtn.setTotalPoint(adDO.getTotalPoint());
		rtn.setStatus(AdPraiseStatusEnum.getEnum(adDO.getStatus()));
        return rtn;
	}

	/**
	 * Page&lt;ChoicenessAdBO&gt;转Page&lt;ChoicenessAdDTO&gt;
	 * @param pageChoicenessAdBO
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月19日
	 */
	public static Page<ChoicenessAdDTO> convertChoicenessAdDTOPage(Page<ChoicenessAdBO> pageChoicenessAdBO) {
		Page<ChoicenessAdDTO> rtn = new Page<>();
		rtn.setCurrentPage(pageChoicenessAdBO.getCurrentPage());
		rtn.setTotalCount(pageChoicenessAdBO.getTotalCount());
		rtn.setRecords(new ArrayList<>());
		Date now = new Date();
		for (ChoicenessAdBO item : pageChoicenessAdBO.getRecords()) {
			ChoicenessAdDTO choicenessAdDTO = new ChoicenessAdDTO();
			choicenessAdDTO.setContent(item.getContent());
			choicenessAdDTO.setId(item.getId());
			choicenessAdDTO.setIsFavorite(item.getIsFavorite());
			choicenessAdDTO.setLogoUrl(item.getLogoUrl());
			choicenessAdDTO.setManageType(item.getManageType());
			choicenessAdDTO.setMediaUrl(item.getMediaUrl());
			choicenessAdDTO.setMerchantStoreId(choicenessAdDTO.getMerchantStoreId());
			choicenessAdDTO.setMerchantStoreName(choicenessAdDTO.getMerchantStoreName());
			choicenessAdDTO.setTitle(item.getTitle());
			choicenessAdDTO.setType(choicenessAdDTO.getType());
			choicenessAdDTO.setVideoImgUrl(item.getVideoImgUrl());
			choicenessAdDTO.setViewcount(item.getViewcount());
			choicenessAdDTO.setBeginTime(item.getBeginTime());
			choicenessAdDTO.setTotalPoint(item.getTotalPoint());
			choicenessAdDTO.setStatus(item.getStatus());
			// 加入倒计时
			if (AdPraiseStatusEnum.AD_STATUS_SHOOT.equals(item.getStatus())) {
				// 抢赞20分钟自动结束
				choicenessAdDTO.setNeedBeginTime(item.getBeginTime().getTime() + 20 * 60 * 1000 - now.getTime());
			} else if (AdPraiseStatusEnum.AD_STATUS_TOBEGIN.equals(item.getStatus())) {
				choicenessAdDTO.setNeedBeginTime(now.getTime() - item.getBeginTime().getTime());
			}
			rtn.getRecords().add(choicenessAdDTO);
		}
        return rtn;
	}


	/**
	 * DO转BO
	 * @param adDO
	 * @return
	 */
	public static AdPraiseBO convertPraiseBO(AdDO adDO){
		AdPraiseBO adBO=new AdPraiseBO();
		if(adDO==null){
			return adBO;
		}
		adBO.setId(adDO.getId());
		adBO.setBeginTime(adDO.getBeginTime());
		adBO.setMediaUrl(adDO.getMediaUrl());
		adBO.setMerchantId(adDO.getMerchantId());
		adBO.setTitle(adDO.getTitle());
		adBO.setTotalPoint(adDO.getTotalPoint());
		adBO.setContent(adDO.getContent());
		adBO.setLogoUrl(adDO.getLogoUrl());
		adBO.setName(adDO.getMerchantStoreName());
		adBO.setManageTypeEnum(ManageTypeEnum.getEnum(adDO.getManageType()));
		adBO.setMerchantStoreId(adDO.getMerchantStoreId());
		adBO.setStatusEnum(AdStatusEnum.getEnum(adDO.getStatus()));
		adBO.setRelateId(adDO.getRelateId());
		adBO.setRelateType(RelateTypeEnum.getEnum(adDO.getRelateType()));
		adBO.setStatusEnum(AdStatusEnum.getEnum(adDO.getStatus()));
        return adBO;

	}


	/**
	 * DO转BO
	 * @param adBO
	 * @return
	 */
	public static AdPraiseDTO convertPraiseDTO(AdPraiseBO adBO){
		AdPraiseDTO dto=new AdPraiseDTO();
		dto.setId(adBO.getId());
		dto.setBeginTime(adBO.getBeginTime());
		dto.setMediaUrl(adBO.getMediaUrl());
		dto.setMerchantId(adBO.getMerchantId());
		dto.setTitle(adBO.getTitle());
		dto.setTotalPoint(adBO.getTotalPoint());
		dto.setContent(adBO.getContent());
		dto.setLogoUrl(adBO.getLogoUrl());
		dto.setName(adBO.getName());
		dto.setManageTypeEnum(adBO.getManageTypeEnum());
		dto.setMerchantStoreId(adBO.getMerchantStoreId());
		dto.setIsFavorite(adBO.getIsFavorite());
		dto.setIsPraise(adBO.getIsPraise());
		dto.setCount(adBO.getNumber());
		dto.setRelateId(adBO.getRelateId());
		dto.setRelateType(adBO.getRelateType());
		dto.setStatusEnum(adBO.getStatusEnum());
		Date date=new Date();
		if(adBO.getStatusEnum().val==2){ //结束倒计时
			Long time=adBO.getBeginTime().getTime()+ (20*60*1000)-date.getTime();
			if(time>0){
				dto.setNeedBeginTime(time);
			}else{
				dto.setNeedBeginTime(Long.parseLong("0"));
			}
		}else if( adBO.getStatusEnum().val==1){ //开枪倒计时
			Long time=adBO.getBeginTime().getTime()-date.getTime();
			dto.setNeedBeginTime(time);

		}
        return dto;

	}

	public static AdEgainDetailBO convertAdEgainDetailBO(AdDO adDO) {
		AdEgainDetailBO rtn = null;
		if (adDO == null) {
			return rtn;
		}
		rtn = new AdEgainDetailBO();
		rtn.setId(adDO.getId());
		rtn.setMediaUrl(adDO.getMediaUrl());
		rtn.setTitle(adDO.getTitle());
		rtn.setType(AdEgainTypeEnum.getEnum(adDO.getType()));
		rtn.setContent(adDO.getContent());
		rtn.setVideoImgUrl(adDO.getVideoImgUrl());
		rtn.setViewCount(adDO.getViewcount());
		rtn.setLogoUrl(adDO.getLogoUrl());
		rtn.setManageType(ManageTypeEnum.getEnum(adDO.getManageType()));
		rtn.setMerchantStoreId(adDO.getMerchantStoreId());
		rtn.setMerchantStoreName(adDO.getMerchantStoreName());
		rtn.setContent(adDO.getContent());
		rtn.setStatusEnum(AdStatusEnum.getEnum(adDO.getStatus()));
		rtn.setMerchantId(adDO.getMerchantId());
		rtn.setRelateId(adDO.getRelateId());
		rtn.setRelateType(RelateTypeEnum.getEnum(adDO.getRelateType()));
		rtn.setFileSize(adDO.getFileSize());
		rtn.setFileTime(adDO.getFileTime());
		return rtn;
	}


	public static AdEgainDTO convertAdEgainDTO(AdEgainDetailBO adBO) {
		AdEgainDTO rtn = null;
		if (adBO == null) {
			return rtn;
		}
		rtn = new AdEgainDTO();
		rtn.setId(adBO.getId());
		rtn.setMediaUrl(adBO.getMediaUrl());
		rtn.setTitle(adBO.getTitle());
		rtn.setContent(adBO.getContent());
		rtn.setVideoImgUrl(adBO.getVideoImgUrl());
		rtn.setViewCount(adBO.getViewCount());
		rtn.setLogoUrl(adBO.getLogoUrl());
		rtn.setManageTypeEnum(adBO.getManageType());
		rtn.setMerchantStoreId(adBO.getMerchantStoreId());
		rtn.setName(adBO.getMerchantStoreName());
		rtn.setContent(adBO.getContent());
		rtn.setStatusEnum(adBO.getStatusEnum());
		rtn.setMerchantId(adBO.getMerchantId());
		rtn.setIsClickAd(adBO.getIsClickAd());
		rtn.setIsFavorite(adBO.getIsFavorite());
		rtn.setRelateId(adBO.getRelateId());
		rtn.setRelateType(adBO.getRelateType());
		if(adBO.getFileSize()!=null){
			rtn.setFileSize(Double.parseDouble(adBO.getFileSize()));
		}
		rtn.setVideoTime(adBO.getFileTime());
		return rtn;
	}

	public static AdPraiseDTO convertDTO(AdSolrDTO solrDTO, AdBO adBO) {
		if (solrDTO == null || adBO == null) {
			return null;
		}
		AdPraiseDTO adPraiseDTO = new AdPraiseDTO();
		adPraiseDTO.setId(solrDTO.getId());
		adPraiseDTO.setMerchantStoreId(solrDTO.getMerchantStoreId());
		adPraiseDTO.setMediaUrl(solrDTO.getMediaUrl());
		adPraiseDTO.setTitle(solrDTO.getTitle());
		adPraiseDTO.setContent(solrDTO.getContent());
		adPraiseDTO.setName(solrDTO.getMerchantStoreName());
		adPraiseDTO.setTotalPoint(BigDecimal.valueOf(solrDTO.getTotalPoint()));
		adPraiseDTO.setCount(solrDTO.getHits());
		adPraiseDTO.setLogoUrl(solrDTO.getLogoUrl());
		adPraiseDTO.setBeginTime(adBO.getBeginTime());
		adPraiseDTO.setIsPraise(adBO.getIsPraise());
		Date date = new Date();
		if (adBO.getStatusEnum().val.byteValue() == AdStatusEnum.AD_STATUS_PUTING.val) { //结束倒计时
			Long time = adBO.getBeginTime().getTime() + (20 * 60 * 1000) - date.getTime();
			if (time > 0) {
				adPraiseDTO.setNeedBeginTime(time);
			} else {
				adPraiseDTO.setNeedBeginTime(0L);
			}
		} else if (adBO.getStatusEnum().val.byteValue() == AdStatusEnum.AD_STATUS_ADD.val) { //开抢倒计时
			Long time = adBO.getBeginTime().getTime() - date.getTime();
			adPraiseDTO.setNeedBeginTime(time);
		}
		return adPraiseDTO;
	}

	public static List<AdFlatVideoDTO> convertAdFlatVideoDTOS(List<AdSolrDTO> solrDTOS) {
		List<AdFlatVideoDTO> dtos = new ArrayList<>();
		if (solrDTOS == null || solrDTOS.isEmpty()) {
			return dtos;
		}

		for (AdSolrDTO adSolrDTO : solrDTOS) {
			AdFlatVideoDTO dto = new AdFlatVideoDTO();
			dto.setId(adSolrDTO.getId());
			dto.setMerchantStoreId(adSolrDTO.getMerchantStoreId());
			dto.setMediaUrl(adSolrDTO.getMediaUrl());
			dto.setVideoImgUrl(adSolrDTO.getVideoImgUrl());
			dto.setTitle(adSolrDTO.getTitle());
			dto.setContent(adSolrDTO.getContent());
			dto.setTypeEnum(adSolrDTO.getTypeEnum());
			dto.setName(adSolrDTO.getMerchantStoreName());
			dto.setLogoUrl(adSolrDTO.getLogoUrl());
			dto.setViewCount(adSolrDTO.getCount());
			dtos.add(dto);
		}
		return dtos;
	}

	public static List<AdDTO> convertAdDTOS(List<AdSolrDTO> solrDTOS) {
		List<AdDTO> dtos = new ArrayList<>();
		if (solrDTOS == null || solrDTOS.isEmpty()) {
			return dtos;
		}

		for (AdSolrDTO adSolrDTO : solrDTOS) {
			AdDTO dto = new AdDTO();
			dto.setId(adSolrDTO.getId());
			dto.setMerchantStoreId(adSolrDTO.getMerchantStoreId());
			dto.setMediaUrl(adSolrDTO.getMediaUrl());
			dto.setVideoImgUrl(adSolrDTO.getVideoImgUrl());
			dto.setTitle(adSolrDTO.getTitle());
			dto.setContent(adSolrDTO.getContent());
			dto.setTypeEnum(adSolrDTO.getTypeEnum());
			dto.setStatusEnum(adSolrDTO.getStatusEnum());
			dto.setName(adSolrDTO.getMerchantStoreName());
			dto.setLogoUrl(adSolrDTO.getLogoUrl());
			dto.setViewCount(adSolrDTO.getCount());
			dto.setPoint(BigDecimal.valueOf(adSolrDTO.getPoint()));
			dto.setTotalPoint(BigDecimal.valueOf(adSolrDTO.getTotalPoint()));
			dtos.add(dto);
		}
		return dtos;
	}



}
