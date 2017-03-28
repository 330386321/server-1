package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.dto.MerchantInviterDTO;
import com.lawu.eshop.user.srv.bo.MerchantInviterBO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;

public class MerchantInviterConverter {
	/**
	 * 商家收藏展示信息转化
	 * @param merchantDOS
	 * @param storeList
	 * @return
	 */
	public static List<MerchantInviterBO> convertMerchantInviterBOS(List<MerchantDO> merchantDOS,
			List<MerchantStoreDO> storeList) {
		if(merchantDOS==null){
			return null;
		}
		List<MerchantInviterBO> FIBOS=new ArrayList<MerchantInviterBO>();
		for (MerchantDO merchantDO : merchantDOS) {
			MerchantInviterBO FIO=new MerchantInviterBO();
			FIO.setAccount(merchantDO.getAccount());
			for (MerchantStoreDO merchantStoreDO : storeList) {
				if(merchantDO.getId().equals(merchantStoreDO.getMerchantId())){
					FIO.setName(merchantStoreDO.getName());
					FIO.setPrincipalName(merchantStoreDO.getPrincipalName());
					FIO.setRegionPath(merchantStoreDO.getRegionPath());
					FIO.setAddress(merchantStoreDO.getAddress());
					FIO.setGmtCreate(merchantStoreDO.getGmtCreate());
					FIO.setStatus(merchantStoreDO.getStatus());
				}
			}
			FIBOS.add(FIO);
			
		}
		return FIBOS;
	}
	
	/**
     * DTO转换
     *
     * @param merchantInviterBO
     * @return
     */
    public static MerchantInviterDTO convertMDTO(MerchantInviterBO merchantInviterBO) {
        if (merchantInviterBO == null) {
            return null;
        }
        MerchantInviterDTO DTO = new MerchantInviterDTO();
        DTO.setAccount(merchantInviterBO.getAccount());
        DTO.setRegionPath(merchantInviterBO.getRegionPath());
        DTO.setAddress(merchantInviterBO.getAddress());
        DTO.setGmtCreate(merchantInviterBO.getGmtCreate());
        DTO.setName(merchantInviterBO.getName());
        DTO.setPrincipalMobile(merchantInviterBO.getPrincipalMobile());
        DTO.setPrincipalName(merchantInviterBO.getPrincipalName());
        DTO.setStatus(merchantInviterBO.getStatus());
        return DTO;
    }
	
    /**
     * BOS 转DTOS
     * @param merchantInviterBOS
     * @return
     */
	public static List<MerchantInviterDTO> convertMIListDOTS(List<MerchantInviterBO> merchantInviterBOS) {
		if (merchantInviterBOS == null) {
		       return null;
		    }
			List<MerchantInviterDTO> DTOS=new ArrayList<MerchantInviterDTO>();
			for (MerchantInviterBO merchantInviterBO : merchantInviterBOS) {
				DTOS.add(convertMDTO(merchantInviterBO));
			}
		return DTOS;
	}

	/**
	 * pageBO 转 pageDO
	 * @param pageBO
	 * @return
	 */
	public static Page<MerchantInviterDTO> convertPageMIDOTS(Page<MerchantInviterBO> pageBO) {
		Page<MerchantInviterDTO> pageDTO=new Page<MerchantInviterDTO>();
		List<MerchantInviterBO> BOS=pageBO.getRecords();
		pageDTO.setRecords(convertMIListDOTS(BOS));
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		return pageDTO;
	}

}
