package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.domain.MemberDO;

/**
 *
 * 会员信息转换器
 *
 * @author Leach
 * @date 2017/3/13
 */
public class MemberConverter {

    /**
     * BO转换
     *
     * @param memberDO
     * @return
     */
    public static MemberBO convertBO(MemberDO memberDO) {
        if (memberDO == null) {
            return null;
        }

        MemberBO memberBO = new MemberBO();
        memberBO.setId(memberDO.getId());
        memberBO.setAccount(memberDO.getAccount());
        return memberBO;
    }

    /**
     * DTO转换
     *
     * @param memberBO
     * @return
     */
    public static UserDTO convertDTO(MemberBO memberBO) {
        if (memberBO == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(memberBO.getId());
        userDTO.setAccount(memberBO.getAccount());
        return userDTO;
    }

}
