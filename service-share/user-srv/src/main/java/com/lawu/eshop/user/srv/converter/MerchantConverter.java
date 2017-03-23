package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MerchantDO;

/**
 * 商户信息转换器
 *
 * @author meishuquan
 * @date 2017/3/23
 */
public class MerchantConverter {

    /**
     * BO转换
     *
     * @param merchantDO
     * @return
     */
    public static MerchantBO convertBO(MerchantDO merchantDO) {
        if (merchantDO == null) {
            return null;
        }

        MerchantBO merchantBO = new MerchantBO();
        merchantBO.setId(merchantDO.getId());
        merchantBO.setNum(merchantDO.getNum());
        merchantBO.setAccount(merchantDO.getAccount());
        merchantBO.setPwd(merchantDO.getPwd());
        merchantBO.setMobile(merchantDO.getMobile());
        merchantBO.setHeadimg(merchantDO.getHeadimg());
        merchantBO.setStatus(merchantDO.getStatus());
        merchantBO.setInviterId(merchantDO.getInviterId());
        merchantBO.setInviterType(merchantDO.getInviterType());
        merchantBO.setLevel(merchantDO.getLevel());
        merchantBO.setGmtModified(merchantDO.getGmtModified());
        merchantBO.setGmtCreate(merchantDO.getGmtCreate());
        return merchantBO;
    }

    /**
     * DTO转换
     *
     * @param merchantBO
     * @return
     */
    public static MerchantDTO convertDTO(MerchantBO merchantBO) {
        if (merchantBO == null) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(merchantBO.getId());
        merchantDTO.setNum(merchantBO.getNum());
        merchantDTO.setAccount(merchantBO.getAccount());
        merchantDTO.setPwd(merchantBO.getPwd());
        merchantDTO.setMobile(merchantBO.getMobile());
        merchantDTO.setHeadimg(merchantBO.getHeadimg());
        merchantDTO.setStatus(merchantBO.getStatus());
        merchantDTO.setInviterId(merchantBO.getInviterId());
        merchantDTO.setInviterType(merchantBO.getInviterType());
        merchantDTO.setLevel(merchantBO.getLevel());
        merchantDTO.setGmtModified(merchantBO.getGmtModified());
        merchantDTO.setGmtCreate(merchantBO.getGmtCreate());
        return merchantDTO;
    }

    /**
     * DOS 转BOS
     * @author zhangrc
     * @date 2013/03/23
     * @param merchantDOS
     * @return
     */
	public static List<MerchantBO> convertBOS(List<MerchantDO> merchantDOS) {
		if (merchantDOS == null) {
	       return null;
	    }
		List<MerchantBO> merchantBOS=new ArrayList<MerchantBO>();
		for (MerchantDO merchantDO : merchantDOS) {
			merchantBOS.add(convertBO(merchantDO));
		}
		return merchantBOS;
	}

	
	public static List<MerchantDTO> convertListDOTS(List<MerchantBO> merchantBOS) {
		if (merchantBOS == null) {
	       return null;
	    }
		List<MerchantDTO> memberDTOS=new ArrayList<MerchantDTO>();
		for (MerchantBO merchantBO : merchantBOS) {
			memberDTOS.add(convertDTO(merchantBO));
		}
		return memberDTOS;
	}
}
