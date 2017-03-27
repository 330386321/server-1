package com.lawu.eshop.mall.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.mall.dto.OnlineActivityDTO;
import com.lawu.eshop.mall.srv.bo.OnlineActivityBO;
import com.lawu.eshop.mall.srv.domain.OnlineActivityDO;

/**
 *
 * 会员信息转换器
 *
 * @author Leach
 * @date 2017/3/13
 */
public class OnlineActivityConverter {
	
	/**
     * DO转换
     *
     * @param onlineActivityDO
     * @return
     */
    public static OnlineActivityDO convertDO(OnlineActivityBO onlineActivityBO) {
        if (onlineActivityBO == null) {
            return null;
        }

        OnlineActivityDO onlineActivityDO = new OnlineActivityDO();
        onlineActivityDO.setId(onlineActivityBO.getId());
        onlineActivityDO.setMerchantId(onlineActivityBO.getMerchantId());
        onlineActivityDO.setTitle(onlineActivityBO.getTitle());
        onlineActivityDO.setSummary(onlineActivityBO.getTitle());
        onlineActivityDO.setContent(onlineActivityBO.getContent());
        onlineActivityDO.setStatus(onlineActivityBO.getStatus());
        onlineActivityDO.setGmtCreate(onlineActivityBO.getGmtCreate());
        return onlineActivityDO;
    }
	
    /**
     * BO转换
     *
     * @param onlineActivityDO
     * @return
     */
    public static OnlineActivityBO convertBO(OnlineActivityDTO onlineActivityDTO) {
        if (onlineActivityDTO == null) {
            return null;
        }

        OnlineActivityBO onlineActivityBO = new OnlineActivityBO();
        onlineActivityBO.setId(onlineActivityDTO.getId());
        onlineActivityBO.setMerchantId(onlineActivityDTO.getMerchantId());
        onlineActivityBO.setTitle(onlineActivityDTO.getTitle());
        onlineActivityBO.setSummary(onlineActivityDTO.getTitle());
        onlineActivityBO.setContent(onlineActivityDTO.getContent());
        onlineActivityBO.setStatus(onlineActivityDTO.getStatus());
        onlineActivityBO.setGmtCreate(onlineActivityDTO.getGmtCreate());
        return onlineActivityBO;
    }
    
    public static OnlineActivityBO convertBO(OnlineActivityDO onlineActivityDO) {
        if (onlineActivityDO == null) {
            return null;
        }

        OnlineActivityBO onlineActivityBO = new OnlineActivityBO();
        onlineActivityBO.setId(onlineActivityDO.getId());
        onlineActivityBO.setMerchantId(onlineActivityDO.getMerchantId());
        onlineActivityBO.setTitle(onlineActivityDO.getTitle());
        onlineActivityBO.setSummary(onlineActivityDO.getTitle());
        onlineActivityBO.setContent(onlineActivityDO.getContent());
        onlineActivityBO.setStatus(onlineActivityDO.getStatus());
        onlineActivityBO.setGmtCreate(onlineActivityDO.getGmtCreate());
        return onlineActivityBO;
    }
    
    public static List<OnlineActivityBO> convertBOS(List<OnlineActivityDO> onlineActivityDOS) {
        if (onlineActivityDOS == null || onlineActivityDOS.isEmpty()) {
            return null;
        }
        
        
        List<OnlineActivityBO> onlineActivityBOS = new ArrayList<OnlineActivityBO>();
        for(OnlineActivityDO onlineActivityDO : onlineActivityDOS){
        	onlineActivityBOS.add(convertBO(onlineActivityDO));
        }
        
        return onlineActivityBOS;
    }

    /**
     * DTO转换
     *
     * @param onlineActivityBO
     * @return
     */
    public static OnlineActivityDTO convertDTO(OnlineActivityBO onlineActivityBO) {
        if (onlineActivityBO == null) {
            return null;
        }

        OnlineActivityDTO onlineActivityDTO = new OnlineActivityDTO();
        onlineActivityDTO.setId(onlineActivityBO.getId());
        onlineActivityDTO.setId(onlineActivityBO.getId());
        onlineActivityDTO.setMerchantId(onlineActivityBO.getMerchantId());
        onlineActivityDTO.setTitle(onlineActivityBO.getTitle());
        onlineActivityDTO.setSummary(onlineActivityBO.getTitle());
        onlineActivityDTO.setContent(onlineActivityBO.getContent());
        onlineActivityDTO.setStatus(onlineActivityBO.getStatus());
        onlineActivityDTO.setGmtCreate(onlineActivityBO.getGmtCreate());
        return onlineActivityDTO;
    }
    
    public static List<OnlineActivityDTO> convertDTOS(List<OnlineActivityBO> onlineActivityBOS) {
        if (onlineActivityBOS == null || onlineActivityBOS.isEmpty()) {
            return null;
        }
        
        
        List<OnlineActivityDTO> onlineActivityDTOS = new ArrayList<OnlineActivityDTO>();
        for(OnlineActivityBO onlineActivityBO : onlineActivityBOS){
        	onlineActivityDTOS.add(convertDTO(onlineActivityBO));
        }
        
        return onlineActivityDTOS;
    }

}
