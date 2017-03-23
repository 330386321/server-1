package com.lawu.eshop.member.api.service.hystrix;

import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
    public void updateMemberInfo(@ModelAttribute UserDTO memberParam) {

    }

    @Override
    public void updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd) {

    }
}
