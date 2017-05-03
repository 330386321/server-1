package com.lawu.eshop.member.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ProductModelService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.ShoppingCartExtendService;
import com.lawu.eshop.member.api.service.ShoppingCartService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.ShoppingCartDTO;
import com.lawu.eshop.order.dto.foreign.MemberShoppingCartDTO;
import com.lawu.eshop.order.dto.foreign.MemberShoppingCartGroupDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartSettlementDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartSettlementItemDTO;
import com.lawu.eshop.order.param.ShoppingCartParam;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderSettlementForeignParam;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.dto.MemberInfoForShoppingOrderDTO;
import com.lawu.eshop.user.dto.ShoppingOrderFindMerchantInfoDTO;
import com.lawu.eshop.user.dto.ShoppingOrderFindUserInfoDTO;
import com.lawu.eshop.user.param.ShoppingOrderFindUserInfoParam;

/**
 * 购物车扩展服务实现类
 * 
 * @author Sunny
 * @date 2017/4/6
 */
@Service
public class ShoppingCartExtendServiceImpl extends BaseController implements ShoppingCartExtendService {
	
    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Autowired
    private ProductModelService productModelService;
    
    @Autowired
    private MerchantStoreService merchantStoreService;
    
    @Autowired
    private MemberService memberService;
	
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private PropertyInfoService propertyInfoService;
    
    /**
     *  加入购物车。
     */
    @SuppressWarnings({ "unchecked" })
	public Result<Long> save(Long memberId, ShoppingCartParam param) {
    	Result<ShoppingCartProductModelDTO> resultShoppingCartProductModelDTO = productModelService.getShoppingCartProductModel(param.getProductModelId());
    	
    	if (!isSuccess(resultShoppingCartProductModelDTO)) {
    		return successCreated(resultShoppingCartProductModelDTO.getRet());
    	}
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = resultShoppingCartProductModelDTO.getModel();
    	
    	Result<String> resultMerchantName = merchantStoreService.getNameByMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	
    	if (!isSuccess(resultMerchantName)) {
    		return successCreated(resultMerchantName.getRet());
    	}
    	
    	ShoppingCartSaveParam shoppingCartSaveParam = new ShoppingCartSaveParam();
    	shoppingCartSaveParam.setMerchantName(resultMerchantName.getModel());
    	shoppingCartSaveParam.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	shoppingCartSaveParam.setProductId(shoppingCartProductModelDTO.getProductId());
    	shoppingCartSaveParam.setProductModelId(shoppingCartProductModelDTO.getId());
    	shoppingCartSaveParam.setQuantity(param.getQuantity());
    	shoppingCartSaveParam.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    	
    	Result<Long> result = shoppingCartService.save(memberId, shoppingCartSaveParam);
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    			
    	return successCreated(result);
    }
    
	/**
	 * 根据memberId查询用户的购物车列表。
	 * 
	 * @param memberId 会员id
	 * @return
	 */
    public Result<List<MemberShoppingCartGroupDTO>> findListByMemberId(Long memberId){
    	List<MemberShoppingCartGroupDTO> rtn = new ArrayList<MemberShoppingCartGroupDTO>();
    	
    	// 通过memberId查询用户购物车资料
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByMemberId(memberId);
    	if (!isSuccess(resultShoppingCartDTOS)) {
    		return successGet(resultShoppingCartDTOS.getRet());
    	}
    	
    	// 如果购物车列表为空直接返回
    	if (resultShoppingCartDTOS.getModel() == null || resultShoppingCartDTOS.getModel().isEmpty()) {
    		return successGet(rtn);
    	}
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> ids = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		ids.add(shoppingCartDTO.getProductModelId());
    	}
    	
    	// 通过商品型号id列表查询商品信息
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<Long>(ids));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successGet(resultShoppingCartProductModelDTOS.getRet());
    	}
    	
    	// 组装数据
    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<Long, ShoppingCartProductModelDTO>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {
    		if (!shoppingCartProductModelDTOMap.containsKey(shoppingCartProductModelDTO.getId())) {
    			shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    		}
    	}
    	
    	Map<Long, List<MemberShoppingCartDTO>> map = new HashMap<Long, List<MemberShoppingCartDTO>>();
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = null;
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		MemberShoppingCartDTO memberShoppingCartDTO = new MemberShoppingCartDTO();
    		memberShoppingCartDTO.setId(shoppingCartDTO.getId());
    		memberShoppingCartDTO.setMerchantId(shoppingCartDTO.getMerchantId());
    		memberShoppingCartDTO.setMerchantName(shoppingCartDTO.getMerchantName());
    		memberShoppingCartDTO.setProductId(shoppingCartDTO.getProductId());
    		memberShoppingCartDTO.setProductModelId(shoppingCartDTO.getProductModelId());
    		memberShoppingCartDTO.setQuantity(shoppingCartDTO.getQuantity());
    		memberShoppingCartDTO.setSalesPrice(shoppingCartDTO.getSalesPrice());
    		
    		shoppingCartProductModelDTO = shoppingCartProductModelDTOMap.get(shoppingCartDTO.getProductModelId());
    		if (shoppingCartProductModelDTO == null) {
    			continue;
    		}
    		memberShoppingCartDTO.setProductModelName(shoppingCartProductModelDTO.getName());
    		memberShoppingCartDTO.setProductName(shoppingCartProductModelDTO.getProductName());
    		memberShoppingCartDTO.setFeatureImage(shoppingCartProductModelDTO.getFeatureImage());
    		memberShoppingCartDTO.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    		// 计算差价(商品表的现价减去购物车表价格,正为涨价,负为降价)
    		memberShoppingCartDTO.setDifference(shoppingCartProductModelDTO.getPrice().subtract(shoppingCartDTO.getSalesPrice()));
    		if (shoppingCartProductModelDTO.getStatus().equals(((byte)0x01)) || shoppingCartProductModelDTO.getStatus().equals(((byte)0x03))) {
    			memberShoppingCartDTO.setIsInvalid(true);
    		} else {
    			memberShoppingCartDTO.setIsInvalid(false);
    		}
    		
    		if (!map.containsKey(shoppingCartDTO.getMerchantId())) {
    			map.put(shoppingCartDTO.getMerchantId(), new ArrayList<MemberShoppingCartDTO>());
    		}
    		
    		map.get(shoppingCartDTO.getMerchantId()).add(memberShoppingCartDTO);
    	}
    	
    	for (Map.Entry<Long, List<MemberShoppingCartDTO>> item : map.entrySet()) {
    		MemberShoppingCartGroupDTO memberShoppingCartGroupDTO = new MemberShoppingCartGroupDTO();
    		memberShoppingCartGroupDTO.setItem(item.getValue());
    		rtn.add(memberShoppingCartGroupDTO);
    	}
    	
    	return successGet(rtn);
    }
    
	/**
	 * 根据购物车id列表结算购物车的商品。
	 * 生成多个订单
	 * 
	 * @return 返回订单的id列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result<List<Long>> createOrder(Long memberId, List<ShoppingOrderSettlementForeignParam> params) {
		
		// 组装ids查询购物车列表
		Map<Long, ShoppingOrderSettlementForeignParam> shoppingOrderSettlementForeignParamMap = new HashMap<Long, ShoppingOrderSettlementForeignParam>();
		List<Long> ids = new ArrayList<Long>();
		for (ShoppingOrderSettlementForeignParam shoppingOrderSettlementForeignParam : params) {
			ids.addAll(shoppingOrderSettlementForeignParam.getIds());
			shoppingOrderSettlementForeignParamMap.put(shoppingOrderSettlementForeignParam.getMerchantId(), shoppingOrderSettlementForeignParam);
		}
		
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByIds(ids);
    	
    	if (!isSuccess(resultShoppingCartDTOS)) {
    		return successCreated(resultShoppingCartDTOS.getRet());
    	}
    	
    	// 购物车分单,同一个商家的商品分在同一个订单当中
    	Map<Long, List<ShoppingCartDTO>> shoppingCartDTOMap = new HashMap<Long, List<ShoppingCartDTO>>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		if (!shoppingCartDTOMap.containsKey(shoppingCartDTO.getMerchantId())) {
    			shoppingCartDTOMap.put(shoppingCartDTO.getMerchantId(), new ArrayList<ShoppingCartDTO>());
    		}
    		shoppingCartDTOMap.get(shoppingCartDTO.getMerchantId()).add(shoppingCartDTO);
    	}
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> idSet = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		idSet.add(shoppingCartDTO.getProductModelId());
    	}
    	
    	// 通过商品型号id列表查询商品信息
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<Long>(idSet));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successCreated(resultShoppingCartProductModelDTOS.getRet());
    	}

    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<Long, ShoppingCartProductModelDTO>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {    	
    		shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    	}
    	
    	// 查询地址
    	Result<AddressDTO> resultAddressDTO = addressService.get(params.get(0).getAddressId());
    	if (!isSuccess(resultAddressDTO)) {
    		return successCreated(resultAddressDTO.getRet());
    	}
    	
    	// 查询商家是否支持七天退货
    	List<Long> merchantIdList = new ArrayList<Long>(shoppingCartDTOMap.keySet());
    	ShoppingOrderFindUserInfoParam shoppingOrderFindUserInfoParam = new ShoppingOrderFindUserInfoParam();
    	shoppingOrderFindUserInfoParam.setMerchantIdList(merchantIdList);
    	shoppingOrderFindUserInfoParam.setMemberId(memberId);
    	Result<ShoppingOrderFindUserInfoDTO> shoppingOrderFindUserInfoDTOResult = merchantStoreService.shoppingOrderFindUserInfo(shoppingOrderFindUserInfoParam);
    	if (!isSuccess(shoppingOrderFindUserInfoDTOResult)) {
    		return successCreated(shoppingOrderFindUserInfoDTOResult.getRet());
    	}
    	
    	// 把商家信息放入Map
    	Map<Long, ShoppingOrderFindMerchantInfoDTO> shoppingOrderFindMerchantInfoDTOMap =  new HashMap<Long, ShoppingOrderFindMerchantInfoDTO>();
    	for (ShoppingOrderFindMerchantInfoDTO shoppingOrderFindMerchantInfoDTO : shoppingOrderFindUserInfoDTOResult.getModel().getShoppingOrderFindMerchantInfoDTOList()) {
    		shoppingOrderFindMerchantInfoDTOMap.put(shoppingOrderFindMerchantInfoDTO.getMerchantId(), shoppingOrderFindMerchantInfoDTO);
    	}
    	
    	Result<MemberInfoForShoppingOrderDTO> resultMemberInfoForShoppingOrderDTO = memberService.getMemberInfoForShoppingOrder(memberId);
    	if (!isSuccess(resultMemberInfoForShoppingOrderDTO)) {
    		return successCreated(resultMemberInfoForShoppingOrderDTO.getRet());
    	}
    	
    	// 组装订单
    	List<ShoppingOrderSettlementParam> shoppingOrderSettlementParams = new ArrayList<ShoppingOrderSettlementParam>();
    	shoppingCartDTOMap.forEach( (key,value) -> {
    		ShoppingOrderSettlementParam shoppingOrderSettlementParam = new ShoppingOrderSettlementParam();
    		shoppingOrderSettlementParam.setMemberId(memberId);
    		shoppingOrderSettlementParam.setMemberNum(shoppingOrderFindUserInfoDTOResult.getModel().getMemberNum());
    		shoppingOrderSettlementParam.setMerchantId(key);
    		shoppingOrderSettlementParam.setMerchantNum(shoppingOrderFindMerchantInfoDTOMap.get(key).getMerchantNum());
    		shoppingOrderSettlementParam.setMerchantName(value.get(0).getMerchantName());
    		shoppingOrderSettlementParam.setMessage(shoppingOrderSettlementForeignParamMap.get(key).getMessage());
    		shoppingOrderSettlementParam.setFreightPrice(shoppingOrderSettlementForeignParamMap.get(key).getFreightPrice());
    		
    		// 设置收货人信息,对应每个订单
    		shoppingOrderSettlementParam.setConsigneeAddress(resultAddressDTO.getModel().getRegionPath() + " " + resultAddressDTO.getModel().getAddr());
    		shoppingOrderSettlementParam.setConsigneeName(resultAddressDTO.getModel().getName());
    		shoppingOrderSettlementParam.setConsigneeMobile(resultAddressDTO.getModel().getMobile());
    		
    		// 是否支持七天退货
    		shoppingOrderSettlementParam.setIsNoReasonReturn(shoppingOrderFindMerchantInfoDTOMap.get(key).getIsNoReasonReturn());
    		
    		// 用户是否是商家粉丝
    		shoppingOrderSettlementParam.setIsFans(shoppingOrderFindMerchantInfoDTOMap.get(key).getIsFans());
    		
    		BigDecimal commodityTotalPrice = new BigDecimal(0);
    		List<ShoppingOrderSettlementItemParam> items = new ArrayList<ShoppingOrderSettlementItemParam>();
    		for (ShoppingCartDTO shoppingCartDTO : value) {
    			ShoppingOrderSettlementItemParam shoppingOrderSettlementItemParam = new ShoppingOrderSettlementItemParam();
    			ShoppingCartProductModelDTO shoppingCartProductModelDTO = shoppingCartProductModelDTOMap.get(shoppingCartDTO.getProductModelId());
    			// 加入购物车id,用于在保存订单之后删除购物车记录
    			shoppingOrderSettlementItemParam.setIsAllowRefund(shoppingCartProductModelDTO.getIsAllowRefund());
    			shoppingOrderSettlementItemParam.setShoppingCartId(shoppingCartDTO.getId());
    			shoppingOrderSettlementItemParam.setProductId(shoppingCartProductModelDTO.getProductId());
    			shoppingOrderSettlementItemParam.setProductName(shoppingCartProductModelDTO.getProductName());
    			shoppingOrderSettlementItemParam.setProductFeatureImage(shoppingCartProductModelDTO.getFeatureImage());
    			shoppingOrderSettlementItemParam.setProductModelId(shoppingCartProductModelDTO.getId());
    			shoppingOrderSettlementItemParam.setProductModelName(shoppingCartProductModelDTO.getName());
    			shoppingOrderSettlementItemParam.setQuantity(shoppingCartDTO.getQuantity());
    			shoppingOrderSettlementItemParam.setRegularPrice(shoppingCartProductModelDTO.getOriginalPrice());
    			shoppingOrderSettlementItemParam.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    			commodityTotalPrice = commodityTotalPrice.add(shoppingCartProductModelDTO.getPrice().multiply(new BigDecimal(shoppingCartDTO.getQuantity())));
    			items.add(shoppingOrderSettlementItemParam);
    		}
    		shoppingOrderSettlementParam.setItems(items);
    		// 订单总价等于货物总价+运费
    		shoppingOrderSettlementParam.setOrderTotalPrice(commodityTotalPrice.add(shoppingOrderSettlementForeignParamMap.get(key).getFreightPrice()));
    		shoppingOrderSettlementParam.setCommodityTotalPrice(commodityTotalPrice);
    		shoppingOrderSettlementParams.add(shoppingOrderSettlementParam);
    	});
    	
    	
    	// 保存订单
    	Result<List<Long>> resultIds = shoppingOrderService.save(shoppingOrderSettlementParams);
    	if (!isSuccess(resultIds)) {
    		return successCreated(resultIds.getRet());
    	}
    	
    	return successCreated(resultIds);
	}
	
	/**
	 * 根据购物车id列表生成结算数据
	 * 
	 * @param idList 购物车id列表
	 * @param memberNum 用户编号
	 * @return 返回结算数据
	 */
	@Override
	public Result<ShoppingCartSettlementDTO> settlement(List<Long> idList, String memberNum) {
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByIds(idList);
    	
    	if (!isSuccess(resultShoppingCartDTOS)) {
    		return successGet(resultShoppingCartDTOS.getRet());
    	}
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> ids = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		ids.add(shoppingCartDTO.getProductModelId());
    	}
    	
    	// 通过商品型号id列表查询商品信息
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<Long>(ids));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successGet(resultShoppingCartProductModelDTOS.getRet());
    	}
    	
    	
    	// 查找用户余额
    	Result<PropertyBalanceDTO> resultPropertyBalanceDTO = propertyInfoService.getPropertyBalance(memberNum);
    	if (!isSuccess(resultPropertyBalanceDTO)) {
    		return successGet(resultPropertyBalanceDTO.getRet());
    	}
    	
    	// 组装数据
    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<Long, ShoppingCartProductModelDTO>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {
    		if (!shoppingCartProductModelDTOMap.containsKey(shoppingCartProductModelDTO.getId())) {
    			shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    		}
    	}
    	
    	Map<Long, List<MemberShoppingCartDTO>> memberShoppingCartDTOMap = new HashMap<Long, List<MemberShoppingCartDTO>>();
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = null;
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		MemberShoppingCartDTO memberShoppingCartDTO = new MemberShoppingCartDTO();
    		memberShoppingCartDTO.setId(shoppingCartDTO.getId());
    		memberShoppingCartDTO.setMerchantId(shoppingCartDTO.getMerchantId());
    		memberShoppingCartDTO.setMerchantName(shoppingCartDTO.getMerchantName());
    		memberShoppingCartDTO.setProductId(shoppingCartDTO.getProductId());
    		memberShoppingCartDTO.setProductModelId(shoppingCartDTO.getProductModelId());
    		memberShoppingCartDTO.setQuantity(shoppingCartDTO.getQuantity());
    		
    		shoppingCartProductModelDTO = shoppingCartProductModelDTOMap.get(shoppingCartDTO.getProductModelId());
    		if (shoppingCartProductModelDTO == null) {
    			continue;
    		}
    		memberShoppingCartDTO.setProductModelName(shoppingCartProductModelDTO.getName());
    		memberShoppingCartDTO.setProductName(shoppingCartProductModelDTO.getProductName());
    		memberShoppingCartDTO.setFeatureImage(shoppingCartProductModelDTO.getFeatureImage());
    		memberShoppingCartDTO.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    		// 计算差价(商品表的现价减去购物车表价格,正为涨价,负为降价)
    		memberShoppingCartDTO.setDifference(shoppingCartProductModelDTO.getPrice().subtract(shoppingCartDTO.getSalesPrice()));
    		if (shoppingCartProductModelDTO.getStatus().equals(((byte)0x01)) || shoppingCartProductModelDTO.getStatus().equals(((byte)0x03))) {
    			memberShoppingCartDTO.setIsInvalid(true);
    		} else {
    			memberShoppingCartDTO.setIsInvalid(false);
    		}
    		
    		if (!memberShoppingCartDTOMap.containsKey(shoppingCartDTO.getMerchantId())) {
    			memberShoppingCartDTOMap.put(shoppingCartDTO.getMerchantId(), new ArrayList<MemberShoppingCartDTO>());
    		}
    		
    		memberShoppingCartDTOMap.get(shoppingCartDTO.getMerchantId()).add(memberShoppingCartDTO);
    		
    	}
    	
    	ShoppingCartSettlementDTO shoppingCartSettlementDTO = new ShoppingCartSettlementDTO();
    	
    	BigDecimal total = new BigDecimal(0);
    	Integer productNumber = new Integer(0);
    	// 每一个商家的商品会合并在一起，小计金额
    	List<ShoppingCartSettlementItemDTO> items = new ArrayList<ShoppingCartSettlementItemDTO>();
    	for (Map.Entry<Long, List<MemberShoppingCartDTO>> entry : memberShoppingCartDTOMap.entrySet()) {
    		ShoppingCartSettlementItemDTO shoppingCartSettlementItemDTO = new ShoppingCartSettlementItemDTO();
    		BigDecimal subtotal = new BigDecimal(0);
    		for (MemberShoppingCartDTO memberShoppingCartDTO : entry.getValue()) {
    			subtotal = subtotal.add(memberShoppingCartDTO.getSalesPrice().multiply(new BigDecimal(memberShoppingCartDTO.getQuantity())));
    			productNumber += memberShoppingCartDTO.getQuantity();
    		}
    		shoppingCartSettlementItemDTO.setSubtotal(subtotal);
    		shoppingCartSettlementItemDTO.setProductNumber(productNumber);
    		shoppingCartSettlementItemDTO.setItems(entry.getValue());
    		items.add(shoppingCartSettlementItemDTO);
    		
    		total = total.add(subtotal);
    	}
    	
    	shoppingCartSettlementDTO.setTotal(total);
    	shoppingCartSettlementDTO.setItems(items);
    	// 放入用户余额
    	shoppingCartSettlementDTO.setBalance(resultPropertyBalanceDTO.getModel().getBalance());
    	
    	return successCreated(shoppingCartSettlementDTO);
	}

	/**
	 * 立即购买
	 * @param param 购物参数
	 * @return 返回订单的结算数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result<ShoppingCartSettlementDTO> buyNow(Long memberId, String memberNum, ShoppingCartParam param) {
		
		// 1.保存购物车记录
		Result<Long> resultSave = save(memberId, param);
		if (!isSuccess(resultSave)) {
			return successGet(resultSave.getRet());
		}
		
		// 2.生成结算数据
		List<Long> idList = new ArrayList<Long>();
		idList.add(resultSave.getModel());
		Result<ShoppingCartSettlementDTO> settlementResult = settlement(idList, memberNum);
		if (!isSuccess(settlementResult)) {
			return successGet(settlementResult.getRet());
		}
		
		return successCreated(settlementResult);
	}

}
