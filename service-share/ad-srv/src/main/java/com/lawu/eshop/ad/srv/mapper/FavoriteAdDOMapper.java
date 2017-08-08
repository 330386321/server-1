package com.lawu.eshop.ad.srv.mapper;

import com.lawu.eshop.ad.srv.domain.FavoriteAdDO;
import com.lawu.eshop.ad.srv.domain.FavoriteAdDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FavoriteAdDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int countByExample(FavoriteAdDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int deleteByExample(FavoriteAdDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int insert(FavoriteAdDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int insertSelective(FavoriteAdDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    List<FavoriteAdDO> selectByExampleWithRowbounds(FavoriteAdDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    List<FavoriteAdDO> selectByExample(FavoriteAdDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    FavoriteAdDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") FavoriteAdDO record, @Param("example") FavoriteAdDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") FavoriteAdDO record, @Param("example") FavoriteAdDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FavoriteAdDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table favorite_ad
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FavoriteAdDO record);
}