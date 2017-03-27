package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.RegisterParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@FeignClient(value = "user-srv")
public interface MerchantService {

    /**
     * 修改登录密码
     *
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "merchant/updateLoginPwd/{id}")
    Result updateLoginPwd(@PathVariable("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd);

    /**
     * 根据邀请人账号查询邀请人信息
     *
     * @param account 邀请人账号
     */
    @RequestMapping(method = RequestMethod.GET, value = "user/common/getInviter/{account}")
    Result getInviterByAccount(@PathVariable("account") String account);

    /**
     * 商户注册
     *
     * @param registerParam 商户注册信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchant/register")
    Result register(@ModelAttribute RegisterParam registerParam);

    @RequestMapping(method = RequestMethod.GET, value = "merchant/getMerchant/{account}")
    Result getMerchantByAccount(@PathVariable String account);

}
