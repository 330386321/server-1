package com.lawu.eshop.member.api.service;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.MemberShoppingCartDTO;
import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementForeignParam;

/**
 * 购物车服务接口
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public interface ShoppingCartExtendService {
	
	/**
	 * 
	 * 
	 * @param param
	 * @return
	 */
	public Result save(Long memberId, ShoppingCartParam param);
	
	/**
	 * 根据memberId查询用户的购物车列表。
	 * 
	 * @param memberId 会员id
	 * @return
	 */
	Result<List<MemberShoppingCartDTO>> findListByMemberId(Long memberId);
	
	/**
	 * 根据购物车id列表结算购物车的商品。
	 * 生成多个订单
	 * 
	 * @return 返回订单的id列表
	 */
	Result<List<Long>> settlement(Long memberId, List<ShoppingOrderSettlementForeignParam> params);
	
}
