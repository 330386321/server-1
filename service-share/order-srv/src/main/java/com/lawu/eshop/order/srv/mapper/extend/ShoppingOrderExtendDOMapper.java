package com.lawu.eshop.order.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;

/**
 * 关联查询订单和订单项表
 * 
 * @author Sunny
 * @date 2017/04/07
 */
public interface ShoppingOrderExtendDOMapper {
	
	/**
	 * 分页查询订单表以及级联订单项表
	 * 
	 * @param example 查询参数
	 * @param rowBounds 分页参数
	 * @return
	 */
	List<ShoppingOrderExtendDO> selectByExampleWithRowbounds(ShoppingOrderExtendDOExample example, RowBounds rowBounds);
	
	/**
	 * 根据参数查询总记录数
	 * 订单表与订单项表关联会有重复记录，需要distinct操作
	 * 
	 * @param example 查询参数
	 * @return
	 */
    long countByExample(ShoppingOrderExtendDOExample example);
    
    /**
     * 根据id查询购物订单以及订单项
     * 
     * @param id 订单id
     * @return
     */
    ShoppingOrderExtendDO selectByPrimaryKey(Long id);
    
	/**
	 * 查询订单表
	 * 
	 * @param example 查询参数
	 * @return
	 */
	List<ShoppingOrderExtendDO> selectByExample(ShoppingOrderExtendDOExample example);
	
	/**
	 * 
	 * @param record
	 * @return
	 * @author Sunny
	 */
    int updateByPrimaryKeySelective(ShoppingOrderExtendDO record);
    
	/**
	 * 查询交易数据
	 * 
	 * @param example 查询参数
	 * @return
	 */
    List<ReportRiseRateView> selectByTransactionData(ShoppingOrderExtendDOExample example);
    
	/**
	 * 查询交易数据
	 * 
	 * @param example 查询参数
	 * @return
	 */
    List<ReportRiseRateView> selectByTransactionTotalAmount(ShoppingOrderExtendDOExample example);
}