package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.product.dto.FavoriteProductDTO;
import com.lawu.eshop.product.srv.bo.FavoriteProductBO;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.extend.FavoriteProductExtendDO;

public class FavoriteProductConverter {
	
	/**
	 * DO转BO
	 * @param FavoriteProductExtendDO
	 * @return
	 */
	public static FavoriteProductBO convertBO(FavoriteProductExtendDO favoriteProductExtendDO) {
        if (favoriteProductExtendDO == null) {
            return null;
        }
        FavoriteProductBO favoriteProductBO=new FavoriteProductBO();
        favoriteProductBO.setName(favoriteProductExtendDO.getName());
        favoriteProductBO.setFeatureImage(favoriteProductExtendDO.getFeatureImage());
        return favoriteProductBO;
    }
	
	/**
	 * DOS 转BOS
	 * @param bankDOS
	 * @return
	 */
	public static List<FavoriteProductBO> convertBOS(List<FavoriteProductExtendDO> favoriteProductExtendDOS,List<ProductModelDO> listPM) {
        if (favoriteProductExtendDOS == null) {
            return null;
        }
        List<FavoriteProductBO> BOS=new ArrayList<FavoriteProductBO>();
        for (FavoriteProductExtendDO favoriteProductExtendDO: favoriteProductExtendDOS) {
        	for (ProductModelDO productModelDO : listPM) {
        		if(favoriteProductExtendDO.getProductId().equals(productModelDO.getProductId())){
        			FavoriteProductBO favoriteProductBO=new FavoriteProductBO();
        	        favoriteProductBO.setName(favoriteProductExtendDO.getName());
        	        favoriteProductBO.setFeatureImage(favoriteProductExtendDO.getFeatureImage());
        	        favoriteProductBO.setOriginalPrice(productModelDO.getOriginalPrice());
        	        favoriteProductBO.setPrice(productModelDO.getPrice());
        	        favoriteProductBO.setSalesVolume(productModelDO.getSalesVolume());
        	        BOS.add(favoriteProductBO);
        		}
			}
        	
		}
        
        return BOS;
    }
	
	/**
	 * BO转DTO
	 * @param bankDO
	 * @return
	 */
	public static FavoriteProductDTO convertDTO(FavoriteProductBO favoriteProductBO ) {
        if (favoriteProductBO == null) {
            return null;
        }
        FavoriteProductDTO bankAccountDTO=new FavoriteProductDTO();
        bankAccountDTO.setName(favoriteProductBO.getName());
        bankAccountDTO.setFeatureImage(favoriteProductBO.getFeatureImage());
        bankAccountDTO.setOriginalPrice(favoriteProductBO.getOriginalPrice());
        bankAccountDTO.setPrice(favoriteProductBO.getPrice());
        bankAccountDTO.setSalesVolume(favoriteProductBO.getSalesVolume());
        return bankAccountDTO;
    }
	
	/**
	 * BOS 转DTOS
	 * @param bankDOS
	 * @return
	 */
	public static List<FavoriteProductDTO> convertDTOS(List<FavoriteProductBO> favoriteProductBOS ) {
        if (favoriteProductBOS == null) {
            return null;
        }
        List<FavoriteProductDTO> DTOS=new ArrayList<FavoriteProductDTO>();
        for (FavoriteProductBO favoriteProductBO: favoriteProductBOS) {
        	DTOS.add(convertDTO(favoriteProductBO));
		}
        
        return DTOS;
    }


}
