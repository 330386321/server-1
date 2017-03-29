package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sunny
 * @date 2017/3/29
 */
@FeignClient(value = "property-srv")
public interface PropertyInfoService {

    /**
     * 修改支付密码
     *
     * @param userNo      会员编号
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/updatePayPwd/{userNo}")
    Result updatePayPwd(@PathVariable("userNo") String userNo, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd);
    
    /**
     * 根据用户编号获取资产余额
     *
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "propertyInfo/propertyBalance/{userNum}", method = RequestMethod.GET)
    Result<PropertyBalanceDTO> getPropertyBalance(@PathVariable("userNum") String userNum);
}
