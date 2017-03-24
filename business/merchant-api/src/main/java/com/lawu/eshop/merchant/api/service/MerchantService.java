package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.merchant.api.service.hystrix.MerchantServiceHystrix;
import com.lawu.eshop.user.dto.InviterDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@FeignClient(value = "user-srv", fallback = MerchantServiceHystrix.class)
public interface MerchantService {

    /**
     * 修改密码
     *
     * @param id  主键
     * @param pwd 密码
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchant/updatePwd")
    void updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd);

    /**
     * 根据邀请人账号查询邀请人信息
     *
     * @param account 邀请人账号
     */
    @RequestMapping(method = RequestMethod.POST, value = "user/common/getInviterByAccount")
    InviterDTO getInviterByAccount(@RequestParam("account") String account);
}
