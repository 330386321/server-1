package com.lawu.eshop.user.srv.mapper;

import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantProfileDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MerchantProfileDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int countByExample(MerchantProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int deleteByExample(MerchantProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int insert(MerchantProfileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int insertSelective(MerchantProfileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    List<MerchantProfileDO> selectByExampleWithRowbounds(MerchantProfileDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    List<MerchantProfileDO> selectByExample(MerchantProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    MerchantProfileDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int updateByExampleSelective(@Param("record") MerchantProfileDO record, @Param("example") MerchantProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int updateByExample(@Param("record") MerchantProfileDO record, @Param("example") MerchantProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int updateByPrimaryKeySelective(MerchantProfileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_profile
     *
     * @mbg.generated 2017-03-31 10:53:01
     */
    int updateByPrimaryKey(MerchantProfileDO record);
}