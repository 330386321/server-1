package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.order.srv.bo.ShoppingCartBO;
import com.lawu.eshop.order.srv.domain.ShoppingCartDO;

/**
 * 购物车服务接口
 *
 * @author Sunny
 * @date 2017/3/27
 */
public interface ShoppingCartService {
	
	
	/**
	 * 根据用户id查询用户的购物车
	 * 
	 * @param memberId
	 * @return
	 */
	List<ShoppingCartBO> findListByMemberId(Long memberId);
	
	/**
	 * 加入购物车
	 * @param param
	 * 
	 * @return 返回生成的id
	 */
	Long save(Long memberId, ShoppingCartParam param);
	
	/**
	 * 根据id更新购物车
	 * 
	 * @param param
	 */
	Integer update(Long id, ShoppingCartParam param);
	
	/**
	 * 根据id删除购物车的商品
	 * 
	 * @param id
	 */
	Integer remove(Long id);
	
	/**
	 * 根据id查询购物车的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public ShoppingCartDO get(Long id);
	
}
