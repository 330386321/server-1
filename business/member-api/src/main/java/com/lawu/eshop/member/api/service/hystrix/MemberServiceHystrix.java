package com.lawu.eshop.member.api.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class MemberServiceHystrix  {

   /* public UserDTO find(@RequestParam String account, @RequestParam String pwd) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1L);
        return userDTO;
    }

    public Result<UserDTO> findMemberInfo(@RequestParam("memberId") Long memberId) {
        Result<UserDTO> userDTO = new Result<UserDTO>();
        return userDTO;
    }

    @Override
    public Result updateMemberInfo(@ModelAttribute UserParam memberParam, @PathVariable("id")  Long id) {
        Result r = new Result();
        r.setMsg("异常处理");
        return r;
    }

    @Override
    public Result updateLoginPwd(@RequestParam("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd) {
        return null;
    }

    @Override
    public Result<List<MemberDTO>> findMemberListByUser(@RequestBody MemberQuery query) {
        return null;
    }

    @Override
    public Result updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd) {
        Result r =
    }

    public Result<InviterDTO> getInviterByAccount(@RequestParam("account") String account) {
        Result<InviterDTO> inviterDTO = new Result<InviterDTO>();
        return inviterDTO;
    }

    public Result register(@RequestBody RegisterParam registerParam) {

    }

    public List<MemberDTO> findMemberListByUser(@RequestParam("inviterId") Long inviterId) {
        return null;
    }
	public Result<Page<MemberDTO>> findMemberListByUser(@RequestBody MemberQuery query) {
		return null;
	}*/
}
