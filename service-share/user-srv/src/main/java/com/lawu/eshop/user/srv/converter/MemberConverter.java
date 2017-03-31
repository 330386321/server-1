package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.UserSexEnum;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.domain.MemberDO;

/**
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
        memberBO.setAccount(memberDO.getAccount());
        memberBO.setBirthday(memberDO.getBirthday());
        memberBO.setNum(memberDO.getNum());
        memberBO.setMobile(memberDO.getMobile());
        memberBO.setHeadimg(memberDO.getHeadimg());
        memberBO.setInviterId(memberDO.getInviterId());
        memberBO.setInviterType(memberDO.getInviterType());
        memberBO.setLevel(memberDO.getLevel());
        memberBO.setUserSex(UserSexEnum.getEnum(memberDO.getSex()));
        memberBO.setRegionPath(memberDO.getRegionPath());
        memberBO.setNickname(memberDO.getNickname());
        return memberBO;
    }

    /**
     * DO转换
     *
     * @param memberBO
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
        userDTO.setNum(memberBO.getNum());
        userDTO.setAccount(memberBO.getAccount());
        userDTO.setBirthday(memberBO.getBirthday());
        userDTO.setUserSex(memberBO.getUserSex());
        userDTO.setRegionPath(memberBO.getRegionPath());
        userDTO.setNickname(memberBO.getNickname());
        return userDTO;
    }


    /**
     * param转DO
     *
     * @param userParam
     * @return
     */
    public static MemberDO convertDOOther(UserParam userParam) {
        if (userParam == null) {
            return null;
        }
        MemberDO memberDO = new MemberDO();
        memberDO.setNickname(userParam.getNickname());
        memberDO.setRegionPath(userParam.getRegionPath());
        memberDO.setSex(userParam.getUserSexEnum().val);
        memberDO.setBirthday(userParam.getBirthday());

        return memberDO;
    }


    /**
     * 描述：将DOS转成BOS
     *
     * @param memberDOS
     * @return
     * @author zhangrc
     * @date 2017/03/23
     */
    public static List<MemberBO> convertListBOS(List<MemberDO> memberDOS) {
        if (memberDOS == null) {
            return null;
        }
        List<MemberBO> memberBOS = new ArrayList<MemberBO>();
        for (MemberDO memberDO : memberDOS) {
            memberBOS.add(convertBO(memberDO));
        }
        return memberBOS;
    }


    /**
     * DTO转换
     *
     * @param memberBO
     * @return
     */
    public static MemberDTO convertMDTO(MemberBO memberBO) {
        if (memberBO == null) {
            return null;
        }
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberBO.getId());
        memberDTO.setNum(memberBO.getNum());
        memberDTO.setName(memberBO.getName());
        memberDTO.setNickname(memberBO.getNickname());
        memberDTO.setBirthday(memberBO.getBirthday());
        memberDTO.setHeadimg(memberBO.getHeadimg());
        memberDTO.setLevel(memberBO.getLevel());
        return memberDTO;
    }
    
    
    public static EfriendDTO convertEDTO(MemberBO memberBO) {
        if (memberBO == null) {
            return null;
        }
        EfriendDTO memberDTO = new EfriendDTO();
        memberDTO.setId(memberBO.getId());
        memberDTO.setNum(memberBO.getNum());
        memberDTO.setName(memberBO.getName());
        memberDTO.setNickname(memberBO.getNickname());
        memberDTO.setHeadimg(memberBO.getHeadimg());
        memberDTO.setLevel(memberBO.getLevel());
        return memberDTO;
    }

    /**
     * 描述：将BOS转成DTOS
     *
     * @param memberBOS
     * @return
     * @author zhangrc
     * @date 2017/03/23
     */
    public static List<EfriendDTO> convertListDOTS(List<MemberBO> memberBOS) {
		if (memberBOS == null) {
		       return null;
		    }
			List<EfriendDTO> memberDTOS=new ArrayList<EfriendDTO>();
			for (MemberBO MemberBO : memberBOS) {
				memberDTOS.add(convertEDTO(MemberBO));
			}
		return memberDTOS;
	}

    /**
     * 描述：将pageBOS转成pageDTOS
     *
     * @param pageMemberBOS
     * @return
     */
	public static Page<EfriendDTO> convertPageDOTS(Page<MemberBO> pageMemberBOS) {
		Page<EfriendDTO> pageDTO=new Page<EfriendDTO>();
		List<MemberBO> BOS=pageMemberBOS.getRecords();
		List<EfriendDTO> DTOS=pageDTO.getRecords();
		for (MemberBO memberBO : BOS) {
			DTOS.add(convertEDTO(memberBO));
		}
		pageDTO.setTotalCount(pageMemberBOS.getTotalCount());
		pageDTO.setCurrentPage(pageMemberBOS.getCurrentPage());
		return pageDTO;
	}

}
