package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.constants.UserInviterTypeEnum;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;

/**
 * 邀请人信息转换器
 *
 * @author meishuquan
 * @date 2017/3/23
 */
public class InviterConverter {

    /**
     * BO转换
     *
     * @param memberDO
     * @return
     */
    public static InviterBO convertBO(MemberDO memberDO) {
        if (memberDO == null) {
            return null;
        }

        InviterBO inviterBO = new InviterBO();
        inviterBO.setInviterId(memberDO.getId());
        inviterBO.setInviterType(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val);
        inviterBO.setInviterName(memberDO.getName());
        return inviterBO;
    }

    /**
     * BO转换
     *
     * @param merchantDO
     * @return
     */
    public static InviterBO convertBO(MerchantDO merchantDO) {
        if (merchantDO == null) {
            return null;
        }

        InviterBO inviterBO = new InviterBO();
        inviterBO.setInviterId(merchantDO.getId());
        inviterBO.setInviterType(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val);
        return inviterBO;
    }

    /**
     * BO转换
     *
     * @param merchantStoreDO
     * @return
     */
    public static InviterBO convertBO(MerchantStoreDO merchantStoreDO) {
        if (merchantStoreDO == null) {
            return null;
        }

        InviterBO inviterBO = new InviterBO();
        inviterBO.setInviterId(merchantStoreDO.getMerchantId());
        inviterBO.setInviterType(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val);
        inviterBO.setInviterName(merchantStoreDO.getName());
        return inviterBO;
    }

    /**
     * DTO转换
     *
     * @param inviterBO
     * @return
     */
    public static InviterDTO convertDTO(InviterBO inviterBO) {
        if (inviterBO == null) {
            return null;
        }

        InviterDTO inviterDTO = new InviterDTO();
        inviterDTO.setInviterId(inviterBO.getInviterId());
        inviterDTO.setInviterType(inviterBO.getInviterType());
        inviterDTO.setInviterName(inviterBO.getInviterName());
        return inviterDTO;
    }
}
