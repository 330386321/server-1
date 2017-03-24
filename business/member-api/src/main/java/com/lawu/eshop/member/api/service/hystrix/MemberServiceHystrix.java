package com.lawu.eshop.member.api.service.hystrix;

import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class MemberServiceHystrix implements MemberService {

    @Override
    public UserDTO find(@RequestParam String account, @RequestParam String pwd) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1L);
        return userDTO;
    }

    @Override
    public UserDTO findMemberInfo(@RequestParam("memberId") Long memberId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1L);
        return userDTO;
    }

    @Override
    public int updateMemberInfo(@RequestBody UserParam memberParam, @PathVariable("id")  Long id) {
       return -1;
    }

    @Override
    public void updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd) {

    }

    @Override
    public InviterDTO getInviterByAccount(@RequestParam("account") String account) {
        InviterDTO inviterDTO = new InviterDTO();
        inviterDTO.setInviterId(-1L);
        return inviterDTO;
    }

	@Override
	public List<MemberDTO> findMemberListByUser(@RequestParam("inviterId") Long inviterId) {
		return null;
	}
}
