package com.lawu.eshop.ad.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.utils.RandomUtil;

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
		adBO.setEndTime(adDO.getEndTime());
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
		adDTO.setEndTime(adBO.getEndTime());
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
		Date date=new Date();
		if(adBO.getTypeEnum().val==3 && adBO.getStatusEnum().val==2){ //结束倒计时
			Long time=adBO.getBeginTime().getTime()+ 300000-date.getTime();
			if(time>0){
				adDTO.setNeedBeginTime(time);
			}else{
				adDTO.setNeedBeginTime(Long.parseLong("0"));
			}
		}else if(adBO.getTypeEnum().val==3 && adBO.getStatusEnum().val==1){ //开枪倒计时
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
			dto.setExpandOrder(RandomUtil.expandOrder());
			dto.setMemberCount(adBO.getAdCount());
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
		dto.setExpandOrder(RandomUtil.expandOrder());
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
			praise.setEndTime(adBO.getEndTime());
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
        document.addField("merchantId_s", adDO.getMerchantId());
        document.addField("title_s", adDO.getTitle());
        document.addField("content_s", adDO.getContent());
		document.addField("latLon_p", adDO.getMerchantLatitude() + "," +  adDO.getMerchantLongitude());
        document.addField("status_s", adDO.getStatus());
        document.addField("count_i", adDO.getViewcount());
        document.addField("radius_i", adDO.getRadius());
        document.addField("type_i", adDO.getType());
        if(adDO.getPutWay()==1){
        	if(adDO.getAreas()!=null){
        		String[] location=adDO.getAreas().split(",");
            	for (String area : location) {
            		document.addField("area_ss", area);
    			}
        	}else{
        		document.addField("area_ss", 0);
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
        	adSolrDTO.setId(Long.valueOf(solrDocument.get("id").toString()));
        	adSolrDTO.setMediaUrl(solrDocument.get("mediaUrl_s").toString());
        	adSolrDTO.setTitle(solrDocument.get("title_s").toString());
        	adSolrDTO.setContent(solrDocument.get("content_s").toString());
        	adSolrDTO.setCount(Integer.valueOf(solrDocument.get("count_i").toString()));
        	int type=(int)solrDocument.get("type_i");
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
        document.addField("merchantId_s", adDO.getMerchantId());
        document.addField("title_s", adDO.getTitle());
        document.addField("content_s", adDO.getContent());
		document.addField("latLon_p", adDO.getMerchantLatitude() + "," +  adDO.getMerchantLongitude());
        document.addField("status_s", "3");
        document.addField("count_i", adDO.getViewcount());
        document.addField("radius_i", adDO.getRadius());
        document.addField("type_i", adDO.getType());
        if(adDO.getPutWay()==1){
        	if(adDO.getAreas()!=null){
        		String[] location=adDO.getAreas().split(",");
            	for (String area : location) {
            		document.addField("area_ss", area);
    			}
        	}else{
        		document.addField("area_ss", 0);
        	}
        }
        return document;
    }

}
