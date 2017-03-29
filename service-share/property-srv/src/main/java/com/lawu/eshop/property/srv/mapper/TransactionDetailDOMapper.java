package com.lawu.eshop.property.srv.mapper;

import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TransactionDetailDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int countByExample(TransactionDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int deleteByExample(TransactionDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int insert(TransactionDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int insertSelective(TransactionDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    List<TransactionDetailDO> selectByExampleWithRowbounds(TransactionDetailDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    List<TransactionDetailDO> selectByExample(TransactionDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    TransactionDetailDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByExampleSelective(@Param("record") TransactionDetailDO record, @Param("example") TransactionDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByExample(@Param("record") TransactionDetailDO record, @Param("example") TransactionDetailDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByPrimaryKeySelective(TransactionDetailDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction_detail
     *
     * @mbg.generated 2017-03-29 10:45:56
     */
    int updateByPrimaryKey(TransactionDetailDO record);
}