package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserParam;
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
        userDTO.setId(memberBO.getId());
        userDTO.setNum(memberBO.getNum());
        userDTO.setAccount(memberBO.getAccount());
        userDTO.setBirthday(memberBO.getBirthday());
        userDTO.setSex(memberBO.getSex());
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
    public static MemberDO convertDOOther(UserParam userParam){
        if(userParam == null){
            return null;
        }
        MemberDO memberDO = new MemberDO();
        memberDO.setNickname(userParam.getNickname());
        memberDO.setRegionPath(userParam.getRegionPath());
        memberDO.setSex(userParam.getSex());
        memberDO.setBirthday(userParam.getBirthday());

        return memberDO;
    }
    
    
    /**
     * 描述：将DOS转成BOS
     * @author zhangrc
     * @date 2017/03/23
     * @param memberDOS
     * @return
     */
    public static List<MemberBO> convertListBOS(List<MemberDO> memberDOS) {
		if (memberDOS == null) {
	       return null;
	    }
		List<MemberBO> memberBOS=new ArrayList<MemberBO>();
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
        memberDTO.setAccount(memberBO.getAccount());
        memberDTO.setBirthday(memberBO.getBirthday());
        memberDTO.setHeadimg(memberBO.getHeadimg());
        memberDTO.setSex(memberBO.getSex());
        memberDTO.setRegionPath(memberBO.getRegionPath());
        memberDTO.setNickname(memberBO.getNickname());
        memberDTO.setName(memberBO.getName());
        memberDTO.setGmtCreate(memberBO.getGmtCreate());
        return memberDTO;
    }
    
    /**
     * 描述：将BOS转成DTOS
     * @author zhangrc
     * @date 2017/03/23
     * @param memberBOS
     * @return
     */
    public static List<MemberDTO> convertListDOTS(List<MemberBO> memberBOS) {
		if (memberBOS == null) {
		       return null;
		    }
			List<MemberDTO> memberDTOS=new ArrayList<MemberDTO>();
			for (MemberBO MemberBO : memberBOS) {
				memberDTOS.add(convertMDTO(MemberBO));
			}
		return memberDTOS;
	}

    /**
     * 描述：将pageBOS转成pageDTOS
     * @param pageMemberBOS
     * @return
     */
	public static Page<MemberDTO> convertPageDOTS(Page<MemberBO> pageMemberBOS) {
		Page<MemberDTO> pageDTO=new Page<MemberDTO>();
		List<MemberBO> BOS=pageMemberBOS.getRecords();
		List<MemberDTO> DTOS=pageDTO.getRecords();
		for (MemberBO memberBO : BOS) {
			DTOS.add(convertMDTO(memberBO));
		}
		pageDTO.setTotalCount(pageMemberBOS.getTotalCount());
		pageDTO.setCurrentPage(pageMemberBOS.getCurrentPage());
		return pageDTO;
	}

}
