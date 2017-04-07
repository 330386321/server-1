package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.property.dto.PropertyPointDTO;

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
     * @param type        业务类型
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "propertyInfo/updatePayPwd/{userNo}")
    Result updatePayPwd(@PathVariable("userNo") String userNo, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd, @RequestParam("type") Integer type);

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
     * @param userNum      商户编号
     * @param consumePoint 消费积分
     * @return
     */
    @RequestMapping(value = "inviteFans/inviteFans/{userNum}", method = RequestMethod.POST)
    public Result inviteFans(@PathVariable("userNum") String userNum, @RequestParam("consumePoint") Integer consumePoint);
}
