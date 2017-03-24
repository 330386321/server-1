package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.user.dto.InviterDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@Component
public class MerchantServiceHystrix implements MerchantService {

    @Override
    public void updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd) {

    }

    @Override
    public InviterDTO getInviterByAccount(@RequestParam("account") String account) {
        InviterDTO inviterDTO = new InviterDTO();
        inviterDTO.setInviterId(-1L);
        return inviterDTO;
    }
}
