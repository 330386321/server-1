package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;
import com.lawu.eshop.user.srv.domain.AddressDO;

/**
*
* 地址信息转换器
*
* @author zhangrc
* @date 2017/3/22
*/
public class AddressConverter {
	
	/**
     * BO转换
     *
     * @param addressDO
     * @return
     */
    public static AddressBO convertBO(AddressDO addressDO) {
    	
        if (addressDO == null) {
            return null;
        }

        AddressBO addressBO = new AddressBO();
        addressBO.setId(addressDO.getId());
        addressBO.setAddr(addressDO.getAddr());;
        addressBO.setIsDefault(addressDO.getIsDefault());
        addressBO.setMobile(addressDO.getMobile());
        addressBO.setName(addressDO.getName());
        addressBO.setPostcode(addressDO.getPostcode());
        addressBO.setRegionPath(addressDO.getRegionPath());

        return addressBO;
    }

	
	public static List<AddressBO> convertListBOS(List<AddressDO> addressDOS) {
		if (addressDOS == null) {
	       return null;
	    }
		List<AddressBO> addressBOS=new ArrayList<AddressBO>();
		for (AddressDO addressDO : addressDOS) {
			addressBOS.add(convertBO(addressDO));
		}
		return addressBOS;
	}
	
	
	/**
     * BO转换
     *
     * @param addressDO
     * @return
     */
    public static AddressDTO convertDTO(AddressBO addressBO) {
    	
        if (addressBO == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(addressBO.getId());
        addressDTO.setAddr(addressBO.getAddr());;
        addressDTO.setIsDefault(addressBO.getIsDefault());
        addressDTO.setMobile(addressBO.getMobile());
        addressDTO.setName(addressBO.getName());
        addressDTO.setPostcode(addressBO.getPostcode());
        addressDTO.setRegionPath(addressBO.getRegionPath());

        return addressDTO;
    }


	public static List<AddressDTO> convertListDOTS(List<AddressBO> addressBOS) {
		if (addressBOS == null) {
		       return null;
		    }
			List<AddressDTO> addressDTOS=new ArrayList<AddressDTO>();
			for (AddressBO addressBO : addressBOS) {
				addressDTOS.add(convertDTO(addressBO));
			}
		return addressDTOS;
	}
	
	
	/**
     * DO转换
     *
     * @param addressParam
     * @return
     */
    public static AddressDO convertDO(AddressParam addressParam) {
    	
        if (addressParam == null) {
            return null;
        }
        AddressDO addressDO = new AddressDO();
        addressDO.setAddr(addressParam.getAddr());;
        addressDO.setMobile(addressParam.getMobile());
        addressDO.setName(addressParam.getName());
        addressDO.setPostcode(addressParam.getPostcode());
        addressDO.setRegionPath(addressParam.getRegionPath());

        return addressDO;
    }

}
