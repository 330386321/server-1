package com.lawu.eshop.ad.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.ad.srv.domain.AdPlatformDO;

/**
 * 平台广告实体转化
 * @author zhangrc
 * @date 2017/3/5
 *
 */
public class AdPlatformConverter {

	/**
	 * DOS转BOS
	 * @param list
	 * @return
	 */
	public static List<AdPlatformBO> convertBOS(List<AdPlatformDO> list){
		if (list == null) {
	            return null;
	    }
		List<AdPlatformBO> BOS=new ArrayList<AdPlatformBO>();
		for (AdPlatformDO adPlatformDO : list) {
			AdPlatformBO adPlatformBO=new AdPlatformBO();
			adPlatformBO.setId(adPlatformDO.getId());
			adPlatformBO.setTitle(adPlatformDO.getTitle());
			adPlatformBO.setType(adPlatformDO.getType());
			adPlatformBO.setMediaUrl(adPlatformDO.getMediaUrl());
			if(adPlatformDO.getType()==1){
				adPlatformBO.setLinkUrl(adPlatformDO.getLinkUrl());
			}else{
				adPlatformBO.setProductId(adPlatformDO.getProductId());
			}
			BOS.add(adPlatformBO);
		}
		return BOS;
		
	}

	/**
	 * BOS 转DTOS
	 * @param bOS
	 * @return
	 */
	public static List<AdPlatformDTO> convertDTOS(List<AdPlatformBO> bOS) {
		if (bOS == null) {
            return null;
	    }
		List<AdPlatformDTO> DTOS=new ArrayList<AdPlatformDTO>();
		for (AdPlatformBO adPlatformBO : bOS) {
			AdPlatformDTO adPlatformDTO=new AdPlatformDTO();
			adPlatformDTO.setId(adPlatformBO.getId());
			adPlatformDTO.setTitle(adPlatformBO.getTitle());
			adPlatformDTO.setMediaUrl(adPlatformBO.getMediaUrl());
			if(adPlatformBO.getType()==1){
				adPlatformDTO.setLinkUrl(adPlatformBO.getLinkUrl());
			}else{
				adPlatformDTO.setProductId(adPlatformBO.getProductId());
			}
			DTOS.add(adPlatformDTO);
		}
		return DTOS;
	}
}
