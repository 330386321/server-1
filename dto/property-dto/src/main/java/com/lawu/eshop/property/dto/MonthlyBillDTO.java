package com.lawu.eshop.property.dto;

import java.math.BigDecimal;

/**
 * 月结账单封装DTO
 * 
 * @author jiangxinjun
 * @date 2017年10月20日
 */
public class MonthlyBillDTO {
   
    /**
    * 收入总额
    */
    private BigDecimal totalIncome;
    
    /**
    * 支出总额
    */
    private BigDecimal totalExpenditure;

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(BigDecimal totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }
    
}