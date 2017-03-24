package com.lawu.eshop.member.api.service.hystrix;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.param.MemberParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;

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
    public Result<UserDTO> findMemberInfo(@RequestParam("memberId") Long memberId) {
        Result<UserDTO> userDTO = new Result<UserDTO>();
        userDTO.setMsg("异常处理");
        return userDTO;
    }

    @Override
    public Result updateMemberInfo(@RequestBody UserParam memberParam, @PathVariable("id")  Long id) {
        Result r = new Result();
        r.setMsg("异常处理");
        return r;
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
	public Result<List<MemberDTO>> findMemberListByUser(@ModelAttribute MemberQuery query) {
		return null;
	}
}
