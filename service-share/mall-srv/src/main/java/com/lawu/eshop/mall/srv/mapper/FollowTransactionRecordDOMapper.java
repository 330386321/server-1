package com.lawu.eshop.mall.srv.mapper;

import com.lawu.eshop.mall.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.mall.srv.domain.FollowTransactionRecordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FollowTransactionRecordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    long countByExample(FollowTransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int deleteByExample(FollowTransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int insert(FollowTransactionRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int insertSelective(FollowTransactionRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    List<FollowTransactionRecordDO> selectByExampleWithRowbounds(FollowTransactionRecordDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    List<FollowTransactionRecordDO> selectByExample(FollowTransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    FollowTransactionRecordDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") FollowTransactionRecordDO record, @Param("example") FollowTransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") FollowTransactionRecordDO record, @Param("example") FollowTransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FollowTransactionRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow_transaction_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FollowTransactionRecordDO record);
}