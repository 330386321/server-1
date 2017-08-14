package com.lawu.eshop.statistics.srv.mapper;

import com.lawu.eshop.statistics.srv.domain.ReportAreaRechargeMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaRechargeMonthDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ReportAreaRechargeMonthDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int countByExample(ReportAreaRechargeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int deleteByExample(ReportAreaRechargeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int insert(ReportAreaRechargeMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int insertSelective(ReportAreaRechargeMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    List<ReportAreaRechargeMonthDO> selectByExampleWithRowbounds(ReportAreaRechargeMonthDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    List<ReportAreaRechargeMonthDO> selectByExample(ReportAreaRechargeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    ReportAreaRechargeMonthDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ReportAreaRechargeMonthDO record, @Param("example") ReportAreaRechargeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ReportAreaRechargeMonthDO record, @Param("example") ReportAreaRechargeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ReportAreaRechargeMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_recharge_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ReportAreaRechargeMonthDO record);
}