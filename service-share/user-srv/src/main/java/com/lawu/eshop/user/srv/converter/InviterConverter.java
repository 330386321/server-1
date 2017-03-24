package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.domain.MemberDO;

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
        inviterBO.setInviterType(UserCommonConstant.INVITER_TYPE_MEMBER);
        inviterBO.setInviterName(memberDO.getName());
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
