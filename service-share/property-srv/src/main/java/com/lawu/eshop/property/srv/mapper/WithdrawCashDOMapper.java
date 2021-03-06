package com.lawu.eshop.property.srv.mapper;

import com.lawu.eshop.property.srv.domain.WithdrawCashDO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WithdrawCashDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int countByExample(WithdrawCashDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int deleteByExample(WithdrawCashDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int insert(WithdrawCashDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int insertSelective(WithdrawCashDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    List<WithdrawCashDO> selectByExampleWithRowbounds(WithdrawCashDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    List<WithdrawCashDO> selectByExample(WithdrawCashDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    WithdrawCashDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WithdrawCashDO record, @Param("example") WithdrawCashDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WithdrawCashDO record, @Param("example") WithdrawCashDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WithdrawCashDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_cash
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WithdrawCashDO record);
}