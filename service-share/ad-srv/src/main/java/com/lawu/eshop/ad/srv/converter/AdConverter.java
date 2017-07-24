package com.lawu.eshop.ad.srv.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdDetailDTO;
import com.lawu.eshop.ad.dto.AdEgainQueryDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.AdPointDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.ChoicenessAdDTO;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.AdDetailBO;
import com.lawu.eshop.ad.srv.bo.AdEgainBO;
import com.lawu.eshop.ad.srv.bo.AdPointBO;
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
        document.addField("mediaUrl_s", adDO.getMediaUrl());
        document.addField("merchantId_l", adDO.getMerchantId());
        document.addField("title_s", adDO.getTitle());
        document.addField("content_s", adDO.getContent());
		document.addField("latLon_p", adDO.getMerchantLatitude() + "," +  adDO.getMerchantLongitude());
        document.addField("status_i", adDO.getStatus());
        document.addField("count_i", adDO.getViewcount());
		document.addField("radius_i", adDO.getRadius());
        document.addField("type_i", adDO.getType());
        document.addField("videoImgUrl_s", adDO.getVideoImgUrl());
        if(adDO.getPutWay() == 1){
        	if(StringUtils.isNotEmpty(adDO.getAreas())){
        		String[] location=adDO.getAreas().split(",");
            	for (String area : location) {
            		document.addField("area_is", area);
    			}
        	}else{
        		document.addField("area_is", 0);
        	}
        }
        return document;
    }
    
    /**
     * SolrInputDocument
     *
     * @param solrDocumentList
     * @return
     */
    public static List<AdSolrDTO> convertDTO(SolrDocumentList solrDocumentList) {
    	List<AdSolrDTO> adSolrDTOS = new ArrayList<>();
        if (solrDocumentList==null) {
            return adSolrDTOS;
        }
        for (SolrDocument solrDocument : solrDocumentList) {
    		AdSolrDTO adSolrDTO = new AdSolrDTO();
        	adSolrDTO.setId(solrDocument.get("id") == null ? 0 : Long.valueOf(solrDocument.get("id").toString()));
        	adSolrDTO.setMediaUrl(solrDocument.get("mediaUrl_s") == null ? "" : solrDocument.get("mediaUrl_s").toString());
        	adSolrDTO.setTitle(solrDocument.get("title_s") == null ? "" : solrDocument.get("title_s").toString());
        	adSolrDTO.setContent(solrDocument.get("content_s") == null ? "" : solrDocument.get("content_s").toString());
        	adSolrDTO.setCount(solrDocument.get("count_i") == null ? 0 : Integer.valueOf(solrDocument.get("count_i").toString()));
        	int type=solrDocument.get("type_i") == null ? 0 : Integer.valueOf(solrDocument.get("type_i").toString());
        	adSolrDTO.setVideoImgUrl(solrDocument.get("videoImgUrl_s") == null ? "" : solrDocument.get("videoImgUrl_s").toString());
        	if(type==1){
        		adSolrDTO.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);	
        	}else{
        		adSolrDTO.setTypeEnum(AdTypeEnum.AD_TYPE_VIDEO);	
        	}
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
		return dto;
	}
	
	public static SolrInputDocument convertSolrUpdateDocument(AdDO adDO) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", adDO.getId());
        document.addField("mediaUrl_s", adDO.getMediaUrl());
        document.addField("merchantId_l", adDO.getMerchantId());
        document.addField("title_s", adDO.getTitle());
        document.addField("content_s", adDO.getContent());
		document.addField("latLon_p", adDO.getMerchantLatitude() + "," +  adDO.getMerchantLongitude());
        document.addField("status_i", adDO.getType());
        document.addField("count_i", adDO.getViewcount());
        document.addField("radius_i", adDO.getRadius());
        document.addField("type_i", adDO.getType());
        document.addField("videoImgUrl_s", adDO.getVideoImgUrl());
        if(adDO.getPutWay()==1){
        	if(adDO.getAreas()!=null){
        		String[] location=adDO.getAreas().split(",");
            	for (String area : location) {
            		document.addField("area_is", area);
    			}
        	}else{
        		document.addField("area_is", 0);
        	}
        }
        return document;
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
}
