package com.lawu.eshop.product.srv.mapper;

import com.lawu.eshop.product.srv.domain.SeckillActivityProductDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SeckillActivityProductDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    long countByExample(SeckillActivityProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int deleteByExample(SeckillActivityProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int insert(SeckillActivityProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int insertSelective(SeckillActivityProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    List<SeckillActivityProductDO> selectByExampleWithRowbounds(SeckillActivityProductDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    List<SeckillActivityProductDO> selectByExample(SeckillActivityProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    SeckillActivityProductDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SeckillActivityProductDO record, @Param("example") SeckillActivityProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SeckillActivityProductDO record, @Param("example") SeckillActivityProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SeckillActivityProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seckill_activity_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SeckillActivityProductDO record);
}