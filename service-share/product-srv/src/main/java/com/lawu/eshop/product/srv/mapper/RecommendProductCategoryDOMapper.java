package com.lawu.eshop.product.srv.mapper;

import com.lawu.eshop.product.srv.domain.RecommendProductCategoryDO;
import com.lawu.eshop.product.srv.domain.RecommendProductCategoryDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RecommendProductCategoryDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int countByExample(RecommendProductCategoryDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int deleteByExample(RecommendProductCategoryDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int insert(RecommendProductCategoryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int insertSelective(RecommendProductCategoryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    List<RecommendProductCategoryDO> selectByExampleWithRowbounds(RecommendProductCategoryDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    List<RecommendProductCategoryDO> selectByExample(RecommendProductCategoryDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    RecommendProductCategoryDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RecommendProductCategoryDO record, @Param("example") RecommendProductCategoryDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RecommendProductCategoryDO record, @Param("example") RecommendProductCategoryDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RecommendProductCategoryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recommend_product_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RecommendProductCategoryDO record);
}