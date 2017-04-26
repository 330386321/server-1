package com.lawu.eshop.member.api.service;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.dto.foreign.MemberShoppingCartGroupDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartSettlementDTO;
import com.lawu.eshop.order.param.ShoppingCartParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderSettlementForeignParam;

/**
 * 购物车服务接口
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public interface ShoppingCartExtendService {
	
	/**
	 * 保存到购物车
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result save(Long memberId, ShoppingCartParam param);
	
	/**
	 * 根据memberId查询用户的购物车列表。
	 * 
	 * @param memberId 会员id
	 * @return
	 */
	Result<List<MemberShoppingCartGroupDTO>> findListByMemberId(Long memberId);
	
	/**
	 * 根据购物车id列表生成结算数据
	 * 
	 * @param idList 购物车id列表
	 * @param memberNum 用户编号
	 * @return 返回结算数据
	 */
	Result<ShoppingCartSettlementDTO> settlement(List<Long> idList, String memberNum);
	
	/**
	 * 根据结算参数列表批量创建订单
	 * 
	 * @param params 订单参数列表
	 * @return 返回订单的id列表
	 */
	Result<List<Long>> createOrder(Long memberId, List<ShoppingOrderSettlementForeignParam> params);
	
}
