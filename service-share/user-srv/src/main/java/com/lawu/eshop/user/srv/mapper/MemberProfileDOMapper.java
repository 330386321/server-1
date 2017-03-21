package com.lawu.eshop.user.srv.mapper;

import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.domain.MemberProfileDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberProfileDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int countByExample(MemberProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int deleteByExample(MemberProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int insert(MemberProfileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int insertSelective(MemberProfileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    List<MemberProfileDO> selectByExampleWithRowbounds(MemberProfileDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    List<MemberProfileDO> selectByExample(MemberProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    MemberProfileDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int updateByExampleSelective(@Param("record") MemberProfileDO record, @Param("example") MemberProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int updateByExample(@Param("record") MemberProfileDO record, @Param("example") MemberProfileDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int updateByPrimaryKeySelective(MemberProfileDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_profile
     *
     * @mbg.generated 2017-03-22 02:28:01
     */
    int updateByPrimaryKey(MemberProfileDO record);
}