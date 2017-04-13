package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;

import java.math.BigDecimal;

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
     * @param userNum 用户编号
     * @return
     */
    PropertyInfoBO getPropertyInfoByUserNum(String userNum);

    /**
     * 根据用户编号修改支付密码
     *
     * @param userNum 用户编号
     * @param newPwd  新密码
     */
    void updatePayPwd(String userNum, String newPwd);

    /**
     * 根据用户编号查询余额
     *
     * @param userNum 用户编号
     * @return
     */
    PropertyBalanceBO getPropertyBalanceByUserNum(String userNum);

    /**
     * 根据用户编号查询积分
     *
     * @param userNum 用户编号
     * @return
     */
    PropertyPointBO getPropertyPointByUserNum(String userNum);

    /**
     * @param userNum 用户编号
     * @param column  列名：B-余额，P-积分，L-爱心账户
     * @param flag    标记:A-加，M-减
     * @param number  余额、积分、爱心账户的数额
     * @return
     */
    int updatePropertyNumbers(String userNum, String column, String flag, BigDecimal number);

    /**
     * 校验余额是否足够
     *
     * @param userNum 用户编号
     * @param amount  金额
     * @return
     */
    int validateBalance(String userNum, String amount);

    /**
     * 校验积分是否足够
     *
     * @param userNum 用户编号
     * @param point  金额
     * @return
     */
    int validatePoint(String userNum, String point);

}
