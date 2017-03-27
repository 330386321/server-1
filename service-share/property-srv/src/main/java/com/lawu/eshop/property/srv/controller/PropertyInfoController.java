package com.lawu.eshop.property.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 初始化用户资产信息
     *
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "savePropertyInfo/{userNo}", method = RequestMethod.POST)
    public Result savePropertyInfo(@PathVariable String userNo) {
        propertyInfoService.savePropertyInfo(userNo);
        return successCreated();
    }

    /**
     * 修改用户支付密码
     *
     * @param userNo      用户编号
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return
     */
    @RequestMapping(value = "updatePayPwd/{userNo}", method = RequestMethod.POST)
    public Result updatePayPwd(@PathVariable String userNo, @RequestParam String originalPwd, @RequestParam String newPwd) {
        PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNo(userNo);
        if (!MD5.MD5Encode(originalPwd).equals(propertyInfoBO.getPayPassword())) {
            return failVerify();
        }
        propertyInfoService.updatePayPwd(userNo, originalPwd, newPwd);
        return successCreated();
    }
}
