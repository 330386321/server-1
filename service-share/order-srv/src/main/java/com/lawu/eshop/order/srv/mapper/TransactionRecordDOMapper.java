package com.lawu.eshop.order.srv.mapper;

import com.lawu.eshop.order.srv.domain.TransactionRecordDO;
import com.lawu.eshop.order.srv.domain.TransactionRecordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TransactionRecordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    long countByExample(TransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int deleteByExample(TransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int insert(TransactionRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int insertSelective(TransactionRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    List<TransactionRecordDO> selectByExampleWithRowbounds(TransactionRecordDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    List<TransactionRecordDO> selectByExample(TransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    TransactionRecordDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TransactionRecordDO record, @Param("example") TransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TransactionRecordDO record, @Param("example") TransactionRecordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TransactionRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TransactionRecordDO record);
}