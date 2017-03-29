package com.lawu.eshop.property.srv.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.MD5;

/**
 * @author meishuquan
 * @date 2017/3/27
 */
@RestController
@RequestMapping(value = "propertyInfo/")
public class PropertyInfoController extends BaseController {

    @Autowired
    private PropertyInfoService propertyInfoService;

    /**
     * 修改用户支付密码
     *
     * @param userNo      用户编号
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @param type        业务类型(1--设置密码，2--修改密码)
     * @return
     */
    @RequestMapping(value = "updatePayPwd/{userNo}", method = RequestMethod.POST)
    public Result updatePayPwd(@PathVariable String userNo, @RequestParam String originalPwd, @RequestParam String newPwd, @RequestParam Integer type) {
        PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNo(userNo);
        if (type == 2 && !MD5.MD5Encode(originalPwd).equals(propertyInfoBO.getPayPassword())) {
            return successGet(ResultCode.VERIFY_PWD_FAIL);
        }
        propertyInfoService.updatePayPwd(userNo, originalPwd, newPwd);
        return successCreated();
    }
    
    /**
     * 根据用户编号获取资产余额
     *
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "propertyBalance/{userNum}", method = RequestMethod.GET)
    public Result<BigDecimal> getPropertyBalance(@PathVariable("userNum") String userNum) {
        return successCreated(propertyInfoService.getPropertyBalanceByUserNum(userNum));
    }
    
    /**
     * 根据用户编号获取用户积分
     *
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "propertyPoint/{userNum}", method = RequestMethod.GET)
    public Result<Integer> getPropertyPoint(@PathVariable("userNum") String userNum) {
        return successCreated(propertyInfoService.getPropertyPointByUserNum(userNum));
    }
}
