package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.dto.FavoriteMerchantDTO;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;

/**
 * 我的商家收藏转化
 * @author zhangrc
 *
 */
public class FavoriteMerchantConverter {
	
	public static List<FavoriteMerchantBO> convertListBOS(List<MerchantStoreDO> merchantStoreDOS,List<FavoriteMerchantDO> fmdos) {
		if (fmdos==null) {
	       return null;
	    }
		List<FavoriteMerchantBO> FMBOS=new ArrayList<FavoriteMerchantBO>();
		
		for (FavoriteMerchantDO favoriteMerchantDO : fmdos) {
			FavoriteMerchantBO FBO=new FavoriteMerchantBO();
			FBO.setId(favoriteMerchantDO.getId());
			FBO.setGmtCreate(favoriteMerchantDO.getGmtCreate());
			for (MerchantStoreDO merchantStoreDO : merchantStoreDOS) {
				if(favoriteMerchantDO.getMerchantId().equals(merchantStoreDO.getMerchantId())){
					FBO.setName(merchantStoreDO.getName());
					FBO.setPrincipalName(merchantStoreDO.getPrincipalName());
					FBO.setRegionPath(merchantStoreDO.getRegionPath());
				}
			}
			FMBOS.add(FBO);
		}
		
		return FMBOS;
	}
	
	public static FavoriteMerchantDTO convertDTO(FavoriteMerchantBO favoriteMerchantBO) {
    	
        if (favoriteMerchantBO == null) {
            return null;
        }
        FavoriteMerchantDTO dto=new FavoriteMerchantDTO();
        dto.setId(favoriteMerchantBO.getId());
        dto.setName(favoriteMerchantBO.getName());
        dto.setPrincipalName(favoriteMerchantBO.getPrincipalName());
        dto.setRegionPath(favoriteMerchantBO.getRegionPath());
        dto.setGmtCreate(favoriteMerchantBO.getGmtCreate());
        return dto;
    }
	
	public static List<FavoriteMerchantDTO> convertListDOTS(List<FavoriteMerchantBO> favoriteMerchantBOS) {
		if (favoriteMerchantBOS == null) {
		       return null;
		    }
			List<FavoriteMerchantDTO> favoriteMerchantDTOS=new ArrayList<FavoriteMerchantDTO>();
			for (FavoriteMerchantBO favoriteMerchant : favoriteMerchantBOS) {
				favoriteMerchantDTOS.add(convertDTO(favoriteMerchant));
			}
		return favoriteMerchantDTOS;
	}

	public static Page<FavoriteMerchantDTO> convertPageDOTS(Page<FavoriteMerchantBO> pageBO) {
		Page<FavoriteMerchantDTO> pageDTO=new Page<FavoriteMerchantDTO>();
		List<FavoriteMerchantBO> BOS=pageBO.getRecords();
		pageDTO.setRecords(convertListDOTS(BOS));
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		return pageDTO;
	}

}
