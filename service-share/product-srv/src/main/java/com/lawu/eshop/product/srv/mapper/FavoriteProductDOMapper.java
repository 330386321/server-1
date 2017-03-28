package com.lawu.eshop.product.srv.mapper;

import com.lawu.eshop.product.srv.domain.FavoriteProductDO;
import com.lawu.eshop.product.srv.domain.FavoriteProductDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FavoriteProductDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int countByExample(FavoriteProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int deleteByExample(FavoriteProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int insert(FavoriteProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int insertSelective(FavoriteProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    List<FavoriteProductDO> selectByExampleWithRowbounds(FavoriteProductDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    List<FavoriteProductDO> selectByExample(FavoriteProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    FavoriteProductDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int updateByExampleSelective(@Param("record") FavoriteProductDO record, @Param("example") FavoriteProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int updateByExample(@Param("record") FavoriteProductDO record, @Param("example") FavoriteProductDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int updateByPrimaryKeySelective(FavoriteProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_product
     *
     * @mbg.generated 2017-03-28 21:47:11
     */
    int updateByPrimaryKey(FavoriteProductDO record);
}