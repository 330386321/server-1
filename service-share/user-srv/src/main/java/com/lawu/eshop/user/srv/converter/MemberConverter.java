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
        memberBO.setBirthday(memberDO.getBirthday());
        memberBO.setNum(memberDO.getNum());
        memberBO.setPwd(memberDO.getPwd());
        memberBO.setMobile(memberDO.getMobile());
        memberBO.setGmtCreate(memberDO.getGmtCreate());
        memberBO.setGmtModified(memberDO.getGmtModified());
        memberBO.setHeadimg(memberDO.getHeadimg());
        memberBO.setInviterId(memberDO.getInviterId());
        memberBO.setInviterType(memberDO.getInviterType());
        memberBO.setLevel(memberDO.getLevel());
        memberBO.setStatus(memberDO.getStatus());
        memberBO.setSex(memberDO.getSex());
        memberBO.setRegionPath(memberDO.getRegionPath());
        memberBO.setNickname(memberDO.getNickname());
        memberBO.setName(memberDO.getName());
        return memberBO;
    }

    /**
     * DO转换
     *
     * @param memberDO
     * @return
     */
    public static MemberDO convertDO(MemberBO memberBO) {
        if (memberBO == null) {
            return null;
        }

        MemberDO memberDO = new MemberDO();
        memberDO.setId(memberBO.getId());
        memberDO.setAccount(memberBO.getAccount());
        memberDO.setBirthday(memberBO.getBirthday());
        memberDO.setNum(memberBO.getNum());
        memberDO.setPwd(memberBO.getPwd());
        memberDO.setMobile(memberBO.getMobile());
        memberDO.setGmtCreate(memberBO.getGmtCreate());
        memberDO.setGmtModified(memberBO.getGmtModified());
        memberDO.setHeadimg(memberBO.getHeadimg());
        memberDO.setInviterId(memberBO.getInviterId());
        memberDO.setInviterType(memberDO.getInviterType());
        memberDO.setLevel(memberDO.getLevel());
        memberDO.setStatus(memberDO.getStatus());
        memberDO.setSex(memberDO.getSex());
        memberDO.setRegionPath(memberDO.getRegionPath());
        memberDO.setNickname(memberDO.getNickname());
        memberDO.setName(memberDO.getName());
        return memberDO;
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
        userDTO.setBirthday(memberBO.getBirthday());
        userDTO.setNum(memberBO.getNum());
        userDTO.setPwd(memberBO.getPwd());
        userDTO.setMobile(memberBO.getMobile());
        userDTO.setGmtCreate(memberBO.getGmtCreate());
        userDTO.setGmtModified(memberBO.getGmtModified());
        userDTO.setHeadimg(memberBO.getHeadimg());
        userDTO.setInviterId(memberBO.getInviterId());
        userDTO.setInviterType(memberBO.getInviterType());
        userDTO.setLevel(memberBO.getLevel());
        userDTO.setStatus(memberBO.getStatus());
        userDTO.setSex(memberBO.getSex());
        userDTO.setRegionPath(memberBO.getRegionPath());
        userDTO.setNickname(memberBO.getNickname());
        userDTO.setName(memberBO.getName());
        return userDTO;
    }



    /**
     * DTO转DO
     *
     * @param userDTO
     * @return
     */
    public static MemberBO convertDTOOther(UserDTO userDTO){
        if(userDTO == null){
            return null;
        }
        MemberBO memberBO = new MemberBO();
        memberBO.setId(userDTO.getId());
        memberBO.setName(userDTO.getName());
        memberBO.setNickname(userDTO.getNickname());
        memberBO.setRegionPath(userDTO.getRegionPath());
        memberBO.setSex(userDTO.getSex());
        memberBO.setAccount(userDTO.getAccount());
        memberBO.setBirthday(userDTO.getBirthday());
        memberBO.setGmtCreate(userDTO.getGmtCreate());
        memberBO.setGmtModified(userDTO.getGmtModified());
        memberBO.setInviterId(userDTO.getInviterId());
        memberBO.setInviterType(userDTO.getInviterType());
        memberBO.setHeadimg(userDTO.getHeadimg());
        memberBO.setStatus(userDTO.getStatus());
        memberBO.setPwd(userDTO.getPwd());
        memberBO.setMobile(userDTO.getMobile());
        memberBO.setLevel(userDTO.getLevel());
        memberBO.setNum(userDTO.getNum());

        return memberBO;
    }

}
