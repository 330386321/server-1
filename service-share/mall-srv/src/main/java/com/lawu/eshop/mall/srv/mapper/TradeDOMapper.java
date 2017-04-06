package com.lawu.eshop.mall.srv.mapper;

import com.lawu.eshop.mall.srv.domain.TradeDO;
import com.lawu.eshop.mall.srv.domain.TradeDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TradeDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int countByExample(TradeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int deleteByExample(TradeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int insert(TradeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int insertSelective(TradeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    List<TradeDO> selectByExampleWithRowbounds(TradeDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    List<TradeDO> selectByExample(TradeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    TradeDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TradeDO record, @Param("example") TradeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TradeDO record, @Param("example") TradeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TradeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TradeDO record);
}