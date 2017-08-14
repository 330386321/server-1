package com.lawu.eshop.statistics.srv.mapper;

import com.lawu.eshop.statistics.srv.domain.ReportAreaVolumeMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaVolumeMonthDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ReportAreaVolumeMonthDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int countByExample(ReportAreaVolumeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int deleteByExample(ReportAreaVolumeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int insert(ReportAreaVolumeMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int insertSelective(ReportAreaVolumeMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    List<ReportAreaVolumeMonthDO> selectByExampleWithRowbounds(ReportAreaVolumeMonthDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    List<ReportAreaVolumeMonthDO> selectByExample(ReportAreaVolumeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    ReportAreaVolumeMonthDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ReportAreaVolumeMonthDO record, @Param("example") ReportAreaVolumeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ReportAreaVolumeMonthDO record, @Param("example") ReportAreaVolumeMonthDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ReportAreaVolumeMonthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_area_volume_month
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ReportAreaVolumeMonthDO record);
}