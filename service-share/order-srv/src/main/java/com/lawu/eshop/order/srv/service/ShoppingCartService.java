package com.lawu.eshop.order.srv.service;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingCartUpdateParam;
import com.lawu.eshop.order.srv.bo.ShoppingCartBO;

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
	 * 
	 * @param memberId 会员id
	 * @param param 保存参数
	 * @return
	 * @author Sunny
	 */
	Result<Long> save(Long memberId, ShoppingCartSaveParam param);
	
	/**
	 * 根据id更新购物车
	 * 
	 * @param id 购物车id
	 * @param memberId 会员id
	 * @param param 更新参数
	 * @return
	 * @author Sunny
	 */
	int update(Long id, Long memberId, ShoppingCartUpdateParam param);
	
	/**
	 * 根据id删除购物车的商品
	 * 
	 * @param id 购物车id
	 * @param memberId 会员id
	 * @return
	 * @author Sunny
	 */
	int remove(Long id, Long memberId);
	
	/**
	 * 根据购物车id列表查询购物车列表
	 * 
	 * @param ids 购物车id列表
	 * @return
	 */
	List<ShoppingCartBO> findListByIds(List<Long> ids);
	
}
