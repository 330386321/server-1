package com.lawu.eshop.product.srv.mapper;

import com.lawu.eshop.product.srv.domain.SeckillActivityAttentionDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityAttentionDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SeckillActivityAttentionDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    long countByExample(SeckillActivityAttentionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int deleteByExample(SeckillActivityAttentionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int insert(SeckillActivityAttentionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int insertSelective(SeckillActivityAttentionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    List<SeckillActivityAttentionDO> selectByExampleWithRowbounds(SeckillActivityAttentionDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    List<SeckillActivityAttentionDO> selectByExample(SeckillActivityAttentionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    SeckillActivityAttentionDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SeckillActivityAttentionDO record, @Param("example") SeckillActivityAttentionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SeckillActivityAttentionDO record, @Param("example") SeckillActivityAttentionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SeckillActivityAttentionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SeckillActivityAttentionDO record);
}