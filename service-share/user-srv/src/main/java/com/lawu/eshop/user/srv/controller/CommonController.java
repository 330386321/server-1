package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.converter.InviterConverter;
import com.lawu.eshop.user.srv.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/23
 */
@RestController
@RequestMapping(value = "user/common/")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "getInviterByAccount", method = RequestMethod.GET)
    public InviterDTO getInviterByAccount(@RequestParam String account) {
        InviterBO inviterBO = commonService.getInviterByAccount(account);
        return InviterConverter.convertDTO(inviterBO);
    }
}
