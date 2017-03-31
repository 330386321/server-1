package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.property.dto.PropertyPointDTO;

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
     * @param type        业务类型
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/updatePayPwd/{userNo}")
    Result updatePayPwd(@PathVariable("userNo") String userNo, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd, @RequestParam("type") Integer type);

    /**
     * 根据用户编号获取资产余额
     *
     * @param userNum 用户编号
     * @return
     */
    @RequestMapping(value = "propertyInfo/propertyBalance/{userNum}", method = RequestMethod.GET)
    Result<PropertyBalanceDTO> getPropertyBalance(@PathVariable("userNum") String userNum);
    
    /**
     * 根据用户编号获取用户积分
     *
     * @param userNum 用户编号
     * @return
     */
    @RequestMapping(value = "propertyInfo/propertyPoint/{userNum}", method = RequestMethod.GET)
    public Result<PropertyPointDTO> getPropertyPoint(@PathVariable("userNum") String userNum);
}
