package com.lawu.eshop.order.srv.mapper;

import com.lawu.eshop.order.srv.domain.ShoppingCartDO;
import com.lawu.eshop.order.srv.domain.ShoppingCartDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ShoppingCartDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int countByExample(ShoppingCartDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int deleteByExample(ShoppingCartDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int insert(ShoppingCartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int insertSelective(ShoppingCartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    List<ShoppingCartDO> selectByExampleWithRowbounds(ShoppingCartDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    List<ShoppingCartDO> selectByExample(ShoppingCartDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    ShoppingCartDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ShoppingCartDO record, @Param("example") ShoppingCartDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ShoppingCartDO record, @Param("example") ShoppingCartDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ShoppingCartDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ShoppingCartDO record);
}