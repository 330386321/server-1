package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.param.RegisterParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@Component
public class MerchantServiceHystrix   {

    public void updateLoginPwd(@RequestParam("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd) {

    }

    public InviterDTO getInviterByAccount(@RequestParam("account") String account) {
        InviterDTO inviterDTO = new InviterDTO();
        inviterDTO.setInviterId(-1L);
        return inviterDTO;
    }

    public void register(@RequestBody RegisterParam registerParam) {

    }
}
