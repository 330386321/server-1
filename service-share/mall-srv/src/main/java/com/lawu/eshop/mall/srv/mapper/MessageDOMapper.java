package com.lawu.eshop.mall.srv.mapper;

import com.lawu.eshop.mall.srv.domain.MessageDO;
import com.lawu.eshop.mall.srv.domain.MessageDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MessageDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int countByExample(MessageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int deleteByExample(MessageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int insert(MessageDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int insertSelective(MessageDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    List<MessageDO> selectByExampleWithRowbounds(MessageDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    List<MessageDO> selectByExample(MessageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    MessageDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int updateByExampleSelective(@Param("record") MessageDO record, @Param("example") MessageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int updateByExample(@Param("record") MessageDO record, @Param("example") MessageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int updateByPrimaryKeySelective(MessageDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated 2017-03-29 11:30:47
     */
    int updateByPrimaryKey(MessageDO record);
}