package com.lawu.eshop.property.dto;/**
 * Created by ${Yangqh} on 2017/8/14.
 */

import java.math.BigDecimal;

/**
 * <p> </p>
 *
 * @author yangqh
 * @date 2017/8/14 14:08
 */
public class AgentReportRechargeQueryDTO {
    private Byte userType;

    private Byte rechargeType;

    private BigDecimal rechargeMoney;

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Byte getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Byte rechargeType) {
        this.rechargeType = rechargeType;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }
}
