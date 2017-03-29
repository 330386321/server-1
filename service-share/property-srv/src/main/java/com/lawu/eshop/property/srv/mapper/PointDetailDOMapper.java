package com.lawu.eshop.property.srv.mapper;

import com.lawu.eshop.property.srv.domain.PointDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PointDetailDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int countByExample(PointDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int deleteByExample(PointDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int insert(PointDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int insertSelective(PointDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    List<PointDetailDO> selectByExampleWithRowbounds(PointDetailDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    List<PointDetailDO> selectByExample(PointDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    PointDetailDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByExampleSelective(@Param("record") PointDetailDO record, @Param("example") PointDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByExample(@Param("record") PointDetailDO record, @Param("example") PointDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByPrimaryKeySelective(PointDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByPrimaryKey(PointDetailDO record);
}