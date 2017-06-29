package com.lawu.eshop.statistics.srv.mapper;

import com.lawu.eshop.statistics.srv.domain.ReportRechargeBalanceMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportRechargeBalanceMonthDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ReportRechargeBalanceMonthDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int countByExample(ReportRechargeBalanceMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int deleteByExample(ReportRechargeBalanceMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int insert(ReportRechargeBalanceMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int insertSelective(ReportRechargeBalanceMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    List<ReportRechargeBalanceMonthDO> selectByExampleWithRowbounds(ReportRechargeBalanceMonthDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    List<ReportRechargeBalanceMonthDO> selectByExample(ReportRechargeBalanceMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    ReportRechargeBalanceMonthDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ReportRechargeBalanceMonthDO record, @Param("example") ReportRechargeBalanceMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ReportRechargeBalanceMonthDO record, @Param("example") ReportRechargeBalanceMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ReportRechargeBalanceMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_recharge_balance_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ReportRechargeBalanceMonthDO record);
}