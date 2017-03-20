package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public UserDTO login(@RequestParam String account, @RequestParam String pwd) {
        MemberBO memberBO = memberService.find(account, pwd);
        return MemberConverter.convertDTO(memberBO);
    }
}
