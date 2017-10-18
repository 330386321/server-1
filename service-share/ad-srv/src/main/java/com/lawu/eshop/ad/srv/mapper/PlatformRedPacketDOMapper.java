package com.lawu.eshop.ad.srv.mapper;

import com.lawu.eshop.ad.srv.domain.PlatformRedPacketDO;
import com.lawu.eshop.ad.srv.domain.PlatformRedPacketDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PlatformRedPacketDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    long countByExample(PlatformRedPacketDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int deleteByExample(PlatformRedPacketDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int insert(PlatformRedPacketDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int insertSelective(PlatformRedPacketDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    List<PlatformRedPacketDO> selectByExampleWithRowbounds(PlatformRedPacketDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    List<PlatformRedPacketDO> selectByExample(PlatformRedPacketDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    PlatformRedPacketDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PlatformRedPacketDO record, @Param("example") PlatformRedPacketDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PlatformRedPacketDO record, @Param("example") PlatformRedPacketDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PlatformRedPacketDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform_red_packet
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlatformRedPacketDO record);
}