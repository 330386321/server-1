package com.lawu.eshop.property.srv.service;

import java.math.BigDecimal;

import com.lawu.eshop.property.srv.bo.PropertyInfoBO;

/**
 * 资产管理服务接口
 *
 * @author meishuquan
 * @date 2017/3/27
 */
public interface PropertyInfoService {

    /**
     * 根据用户编号查询用户信息
     *
     * @param userNo 用户编号
     * @return
     */
    PropertyInfoBO getPropertyInfoByUserNo(String userNo);

    /**
     * 根据用户编号修改支付密码
     *
     * @param userNo      用户编号
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     */
    void updatePayPwd(String userNo, String originalPwd, String newPwd);

    /**
     * 根据用户编号查询余额
     *
     * @param userNum 用户编号
     * @return
     */
    BigDecimal getPropertyBalanceByUserNum(String userNum);
    
    /**
     * 根据用户编号查询积分
     * 
     * @param userNo 用户编号
     * @return
     */
    Integer getPropertyPointByUserNum(String userNum);
}
