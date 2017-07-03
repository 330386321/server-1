package com.lawu.eshop.statistics.srv.mapper;

import com.lawu.eshop.statistics.srv.domain.ReportAdEarningsDO;
import com.lawu.eshop.statistics.srv.domain.ReportAdEarningsDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ReportAdEarningsDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    long countByExample(ReportAdEarningsDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int deleteByExample(ReportAdEarningsDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int insert(ReportAdEarningsDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int insertSelective(ReportAdEarningsDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    List<ReportAdEarningsDO> selectByExampleWithRowbounds(ReportAdEarningsDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    List<ReportAdEarningsDO> selectByExample(ReportAdEarningsDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    ReportAdEarningsDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ReportAdEarningsDO record, @Param("example") ReportAdEarningsDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ReportAdEarningsDO record, @Param("example") ReportAdEarningsDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ReportAdEarningsDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_ad_earnings
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ReportAdEarningsDO record);
}