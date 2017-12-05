package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.MemberMineInfoDTO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MemberProfileBO;
import com.lawu.eshop.user.srv.domain.MemberProfileDO;

/**
 * 
 * @author Sunny
 * @date 2017年6月16日
 */
public class MemberProfileConverter {

    /**
     * BO转换
     *
     * @param memberProfileDO
     * @return
     */
    public static MemberProfileBO convert(MemberProfileDO memberProfileDO) {
    	MemberProfileBO rtn = null;
        if (memberProfileDO == null || memberProfileDO.getId() == null || memberProfileDO.getId() <= 0) {
            return rtn;
        }
        
        rtn = new MemberProfileBO();
        rtn.setGmtCreate(memberProfileDO.getGmtCreate());
        rtn.setGmtModified(memberProfileDO.getGmtModified());
        rtn.setId(memberProfileDO.getId());
        rtn.setInviteMemberCount(memberProfileDO.getInviteMemberCount());
        rtn.setInviteMemberCount2(memberProfileDO.getInviteMemberCount2());
        rtn.setInviteMemberCount3(memberProfileDO.getInviteMemberCount3());
        rtn.setInviteMerchantCount(memberProfileDO.getInviteMerchantCount());
        rtn.setInviteMerchantCount2(memberProfileDO.getInviteMerchantCount2());
        rtn.setInviteMerchantCount3(memberProfileDO.getInviteMerchantCount3());
        return rtn;
    }
    
    /**
     * 转换
     *
     * @param memberDO
     * @return
     */
    public static MemberMineInfoDTO convert(MemberProfileBO memberProfileBO, MemberBO memberBO) {
    	MemberMineInfoDTO rtn = null;
        if (memberProfileBO == null || memberBO == null) {
            return rtn;
        }
        rtn = new MemberMineInfoDTO();
        rtn.setHeadimg(memberBO.getHeadimg());
        rtn.setLevel(memberBO.getLevel());
        rtn.setNickname(memberBO.getNickname());
        rtn.setInviteMemberCount(memberProfileBO.getInviteMemberCount());
        rtn.setInviteMerchantCount(memberProfileBO.getInviteMerchantCount());
        rtn.setGrade(memberBO.getGrade());
        rtn.setGrowthValue(memberBO.getGrowthValue());
        return rtn;
    }

}
