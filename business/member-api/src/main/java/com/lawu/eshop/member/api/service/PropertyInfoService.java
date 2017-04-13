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
     * @param userNum     会员编号
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/updatePayPwd/{userNum}")
    Result updatePayPwd(@PathVariable("userNum") String userNum, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd);

    /**
     * 重置支付密码
     *
     * @param userNum 会员编号
     * @param newPwd  新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/resetPayPwd/{userNum}")
    Result resetPayPwd(@PathVariable("userNum") String userNum, @RequestParam("newPwd") String newPwd);

    /**
     * 设置支付密码
     *
     * @param userNum 会员编号
     * @param newPwd  新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/setPayPwd/{userNum}")
    Result setPayPwd(@PathVariable("userNum") String userNum, @RequestParam("newPwd") String newPwd);

    /**
     * 查询是否设置支付密码
     *
     * @param userNum
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "propertyInfo/isSetPayPwd/{userNum}")
    Result isSetPayPwd(@PathVariable("userNum") String userNum);

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
    
    /**
     * 验证支付密码
     *
     * @param userNum
     * @param payPwd
     * @return
     */
    @RequestMapping(value = "propertyInfo/varifyPayPwd", method = RequestMethod.GET)
    Result varifyPayPwd(@RequestParam("userNum") String userNum, @RequestParam("payPwd") String payPwd);
}
