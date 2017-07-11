package com.lawu.eshop.statistics.srv.mapper;

import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ReportSalesMonthDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    long countByExample(ReportSalesMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int deleteByExample(ReportSalesMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int insert(ReportSalesMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int insertSelective(ReportSalesMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    List<ReportSalesMonthDO> selectByExampleWithRowbounds(ReportSalesMonthDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    List<ReportSalesMonthDO> selectByExample(ReportSalesMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    ReportSalesMonthDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ReportSalesMonthDO record, @Param("example") ReportSalesMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ReportSalesMonthDO record, @Param("example") ReportSalesMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ReportSalesMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_sales_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ReportSalesMonthDO record);
}