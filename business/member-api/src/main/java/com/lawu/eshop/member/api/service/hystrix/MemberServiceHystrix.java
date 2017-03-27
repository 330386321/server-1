package com.lawu.eshop.member.api.service.hystrix;

import org.springframework.stereotype.Component;


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
<<<<<<< HEAD
    }*/
	/*@Override
=======
    }
>>>>>>> 9a4ec6b8d0a3c1986beb613ac3fbf088cf0c7732
	public Result<Page<MemberDTO>> findMemberListByUser(@RequestBody MemberQuery query) {
		Result<Page<MemberDTO>> page=new 
		return null;
	}*/
}
