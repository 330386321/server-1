package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/3/27
 */
@FeignClient(value = "property-srv")
public interface PropertyInfoService {

    /**
     * 修改支付密码
     *
     * @param userNo      商户编号
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/updatePayPwd/{userNo}")
    Result updatePayPwd(@PathVariable("userNo") String userNo, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd);

    /**
     * 重置支付密码
     *
     * @param userNo 商户编号
     * @param newPwd 新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/resetPayPwd/{userNo}")
    Result resetPayPwd(@PathVariable("userNo") String userNo, @RequestParam("newPwd") String newPwd);

    /**
     * 设置支付密码
     *
     * @param userNo 商户编号
     * @param newPwd 新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/setPayPwd/{userNo}")
    Result setPayPwd(@PathVariable("userNo") String userNo, @RequestParam("newPwd") String newPwd);

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
     * 邀请粉丝消费积分
     *
     * @param propertyInfoDataParam
     * @return
     */
    @RequestMapping(value = "propertyInfoData/inviteFans/{userNum}", method = RequestMethod.POST)
    Result inviteFans(@ModelAttribute PropertyInfoDataParam propertyInfoDataParam);

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
