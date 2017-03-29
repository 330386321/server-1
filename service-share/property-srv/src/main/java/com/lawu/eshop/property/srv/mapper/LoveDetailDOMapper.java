package com.lawu.eshop.property.srv.mapper;

import com.lawu.eshop.property.srv.domain.LoveDetailDO;
import com.lawu.eshop.property.srv.domain.LoveDetailDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LoveDetailDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int countByExample(LoveDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int deleteByExample(LoveDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int insert(LoveDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int insertSelective(LoveDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    List<LoveDetailDO> selectByExampleWithRowbounds(LoveDetailDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    List<LoveDetailDO> selectByExample(LoveDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    LoveDetailDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByExampleSelective(@Param("record") LoveDetailDO record, @Param("example") LoveDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByExample(@Param("record") LoveDetailDO record, @Param("example") LoveDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByPrimaryKeySelective(LoveDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table love_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByPrimaryKey(LoveDetailDO record);
}