package com.lawu.eshop.member.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.bizctrl.BusinessExecuteException;
import com.lawu.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;
import com.lawu.eshop.concurrentqueue.impl.BusinessKey;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ProductModelService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.ShoppingCartExtendService;
import com.lawu.eshop.member.api.service.ShoppingCartService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.ShoppingCartDTO;
import com.lawu.eshop.order.dto.foreign.MemberShoppingCartDTO;
import com.lawu.eshop.order.dto.foreign.MemberShoppingCartGroupDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartSettlementDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingCartSettlementItemDTO;
import com.lawu.eshop.order.param.ShoppingCartParam;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderBuyNowCreateOrderForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderSettlementForeignParam;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.SeckillActivityProductModelInfoDTO;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.dto.MerchantInfoForShoppingCartDTO;
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
    private AddressService addressService;
    
    @Autowired
    private PropertyInfoService propertyInfoService;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
	public Result save(Long memberId, ShoppingCartParam param) {
    	Result<ShoppingCartProductModelDTO> resultShoppingCartProductModelDTO = productModelService.getShoppingCartProductModel(param.getProductModelId());
    	
    	if (!isSuccess(resultShoppingCartProductModelDTO)) {
    		return successCreated(resultShoppingCartProductModelDTO.getRet());
    	}
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = resultShoppingCartProductModelDTO.getModel();
    	
    	Result<MerchantInfoForShoppingCartDTO> getMerchantInfoForShoppingCartResult = merchantStoreService.getMerchantInfoForShoppingCart(shoppingCartProductModelDTO.getMerchantId());
    	
    	if (!isSuccess(getMerchantInfoForShoppingCartResult)) {
    		return successCreated(getMerchantInfoForShoppingCartResult);
    	}
    	
    	ShoppingCartSaveParam shoppingCartSaveParam = new ShoppingCartSaveParam();
    	shoppingCartSaveParam.setMerchantName(getMerchantInfoForShoppingCartResult.getModel().getMerchantStoreName());
    	shoppingCartSaveParam.setMerchantStoreId(getMerchantInfoForShoppingCartResult.getModel().getMerchantStoreId());
    	shoppingCartSaveParam.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	shoppingCartSaveParam.setProductId(shoppingCartProductModelDTO.getProductId());
    	shoppingCartSaveParam.setProductModelId(shoppingCartProductModelDTO.getId());
    	shoppingCartSaveParam.setQuantity(param.getQuantity());
    	shoppingCartSaveParam.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    	
    	Result result = shoppingCartService.save(memberId, shoppingCartSaveParam);
    	if (!isSuccess(result)) {
    		return successCreated(result);
    	}
    			
    	return successCreated(result);
    }
    
	/**
	 * 根据memberId查询用户的购物车列表。
	 * 
	 * @param memberId 会员id
	 * @return
	 */
    @Override
    public Result<ShoppingCartQueryDTO> findListByMemberId(Long memberId){
    	ShoppingCartQueryDTO rtn = new ShoppingCartQueryDTO();
    	
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
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<>(ids));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successGet(resultShoppingCartProductModelDTOS.getRet());
    	}
    	
    	// 组装数据
    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {
    		if (!shoppingCartProductModelDTOMap.containsKey(shoppingCartProductModelDTO.getId())) {
    			shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    		}
    	}
    	
    	List<MemberShoppingCartDTO> shoppingCartInvalidList = new ArrayList<>();
    	Map<Long, List<MemberShoppingCartDTO>> map = new LinkedHashMap<>();
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = null;
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		MemberShoppingCartDTO memberShoppingCartDTO = new MemberShoppingCartDTO();
    		memberShoppingCartDTO.setId(shoppingCartDTO.getId());
    		memberShoppingCartDTO.setMerchantId(shoppingCartDTO.getMerchantId());
    		memberShoppingCartDTO.setMerchantStoreId(shoppingCartDTO.getMerchantStoreId());
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
    		memberShoppingCartDTO.setInventory(shoppingCartProductModelDTO.getInventory());
    		memberShoppingCartDTO.setGmtDown(shoppingCartProductModelDTO.getGmtDown());
    		
    		if (shoppingCartProductModelDTO.getStatus().equals(ProductStatusEnum.PRODUCT_STATUS_DEL) || shoppingCartProductModelDTO.getStatus().equals(ProductStatusEnum.PRODUCT_STATUS_DOWN)) {
    			memberShoppingCartDTO.setIsInvalid(true);
    			shoppingCartInvalidList.add(memberShoppingCartDTO);
    		} else {
    			memberShoppingCartDTO.setIsInvalid(false);
    			if (!map.containsKey(shoppingCartDTO.getMerchantId())) {
        			map.put(shoppingCartDTO.getMerchantId(), new ArrayList<>());
        		}
    			map.get(shoppingCartDTO.getMerchantId()).add(memberShoppingCartDTO);
    		}
    	}
    	
    	List<MemberShoppingCartGroupDTO> memberShoppingCartGroupDTOList = new ArrayList<>();
    	for (Map.Entry<Long, List<MemberShoppingCartDTO>> item : map.entrySet()) {
    		MemberShoppingCartGroupDTO memberShoppingCartGroupDTO = new MemberShoppingCartGroupDTO();
    		memberShoppingCartGroupDTO.setItem(item.getValue());
    		memberShoppingCartGroupDTOList.add(memberShoppingCartGroupDTO);
    	}
    	rtn.setShoppingCartGroupList(memberShoppingCartGroupDTOList);
    	
    	shoppingCartInvalidList.sort((o1, o2) -> o2.getGmtDown().compareTo(o1.getGmtDown()));
    	
    	rtn.setShoppingCartInvalidList(shoppingCartInvalidList);
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
		Map<Long, ShoppingOrderSettlementForeignParam> shoppingOrderSettlementForeignParamMap = new HashMap<>();
		List<Long> ids = new ArrayList<>();
		for (ShoppingOrderSettlementForeignParam shoppingOrderSettlementForeignParam : params) {
			ids.addAll(shoppingOrderSettlementForeignParam.getIds());
			shoppingOrderSettlementForeignParamMap.put(shoppingOrderSettlementForeignParam.getMerchantId(), shoppingOrderSettlementForeignParam);
		}
		
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByIds(memberId, ids);
    	
    	if (!isSuccess(resultShoppingCartDTOS)) {
    		return successCreated(resultShoppingCartDTOS.getRet());
    	}
    	
    	// 购物车分单,同一个商家的商品分在同一个订单当中
    	Map<Long, List<ShoppingCartDTO>> shoppingCartDTOMap = new HashMap<>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		if (!shoppingCartDTOMap.containsKey(shoppingCartDTO.getMerchantId())) {
    			shoppingCartDTOMap.put(shoppingCartDTO.getMerchantId(), new ArrayList<>());
    		}
    		shoppingCartDTOMap.get(shoppingCartDTO.getMerchantId()).add(shoppingCartDTO);
    	}
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> idSet = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		idSet.add(shoppingCartDTO.getProductModelId());
    	}
    	
    	// 通过商品型号id列表查询商品信息
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<>(idSet));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successCreated(resultShoppingCartProductModelDTOS.getRet());
    	}

    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {    	
    		shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    	}
    	
    	// 查询地址
    	Result<AddressDTO> resultAddressDTO = addressService.get(params.get(0).getAddressId());
    	if (!isSuccess(resultAddressDTO)) {
    		return successCreated(resultAddressDTO.getRet());
    	}
    	
    	// 查询商家是否支持七天退货
    	List<Long> merchantIdList = new ArrayList<>(shoppingCartDTOMap.keySet());
    	ShoppingOrderFindUserInfoParam shoppingOrderFindUserInfoParam = new ShoppingOrderFindUserInfoParam();
    	shoppingOrderFindUserInfoParam.setMerchantIdList(merchantIdList);
    	shoppingOrderFindUserInfoParam.setMemberId(memberId);
    	Result<ShoppingOrderFindUserInfoDTO> shoppingOrderFindUserInfoDTOResult = merchantStoreService.shoppingOrderFindUserInfo(shoppingOrderFindUserInfoParam);
    	if (!isSuccess(shoppingOrderFindUserInfoDTOResult)) {
    		return successCreated(shoppingOrderFindUserInfoDTOResult.getRet());
    	}
    	
    	// 把商家信息放入Map
    	Map<Long, ShoppingOrderFindMerchantInfoDTO> shoppingOrderFindMerchantInfoDTOMap =  new HashMap<>();
    	for (ShoppingOrderFindMerchantInfoDTO shoppingOrderFindMerchantInfoDTO : shoppingOrderFindUserInfoDTOResult.getModel().getShoppingOrderFindMerchantInfoDTOList()) {
    		shoppingOrderFindMerchantInfoDTOMap.put(shoppingOrderFindMerchantInfoDTO.getMerchantId(), shoppingOrderFindMerchantInfoDTO);
    	}
    	
    	// 组装订单
    	List<ShoppingOrderSettlementParam> shoppingOrderSettlementParams = new ArrayList<>();
    	
    	for (Map.Entry<Long, List<ShoppingCartDTO>> entry : shoppingCartDTOMap.entrySet()) {	
    		Long key = entry.getKey();
    		List<ShoppingCartDTO> value = entry.getValue();
    		ShoppingOrderSettlementParam shoppingOrderSettlementParam = new ShoppingOrderSettlementParam();
    		shoppingOrderSettlementParam.setMemberId(memberId);
    		shoppingOrderSettlementParam.setMemberNum(shoppingOrderFindUserInfoDTOResult.getModel().getMemberNum());
    		shoppingOrderSettlementParam.setMemberNickname(shoppingOrderFindUserInfoDTOResult.getModel().getMemberNickname());
    		shoppingOrderSettlementParam.setMerchantId(key);
    		shoppingOrderSettlementParam.setMerchantStoreId(shoppingOrderFindMerchantInfoDTOMap.get(key).getMerchantStoreId());
    		shoppingOrderSettlementParam.setMerchantStoreRegionPath(shoppingOrderFindMerchantInfoDTOMap.get(key).getMerchantStoreRegionPath());
    		shoppingOrderSettlementParam.setMerchantNum(shoppingOrderFindMerchantInfoDTOMap.get(key).getMerchantNum());
    		shoppingOrderSettlementParam.setMerchantName(value.get(0).getMerchantName());
    		shoppingOrderSettlementParam.setMessage(shoppingOrderSettlementForeignParamMap.get(key).getMessage());
    		shoppingOrderSettlementParam.setFreightPrice(shoppingOrderSettlementForeignParamMap.get(key).getFreightPrice());
    		
    		// 设置收货人信息,对应每个订单
    		shoppingOrderSettlementParam.setConsigneeAddress(resultAddressDTO.getModel().getRegionName() + " " + resultAddressDTO.getModel().getAddr());
    		shoppingOrderSettlementParam.setConsigneeName(resultAddressDTO.getModel().getName());
    		shoppingOrderSettlementParam.setConsigneeMobile(resultAddressDTO.getModel().getMobile());
    		
    		// 是否支持七天退货
    		shoppingOrderSettlementParam.setIsNoReasonReturn(shoppingOrderFindMerchantInfoDTOMap.get(key).getIsNoReasonReturn());
    		
    		// 用户是否是商家粉丝
    		shoppingOrderSettlementParam.setIsFans(shoppingOrderFindMerchantInfoDTOMap.get(key).getIsFans());
    		
    		BigDecimal commodityTotalPrice = new BigDecimal(0);
    		List<ShoppingOrderSettlementItemParam> items = new ArrayList<>();
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
    			
    			// 判断商品是否失效
    			if (!ProductStatusEnum.PRODUCT_STATUS_UP.equals(shoppingCartProductModelDTO.getStatus())) {
    				return successCreated(ResultCode.PRODUCT_HAS_EXPIRED);
    			}
    			
    			// 判断库存
    			if (shoppingCartProductModelDTO.getInventory() < shoppingCartDTO.getQuantity()) {
    				return successCreated(ResultCode.INVENTORY_SHORTAGE);
    			}
    			
    			shoppingOrderSettlementItemParam.setRegularPrice(shoppingCartProductModelDTO.getOriginalPrice());
    			shoppingOrderSettlementItemParam.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    			commodityTotalPrice = commodityTotalPrice.add(shoppingCartProductModelDTO.getPrice().multiply(new BigDecimal(shoppingCartDTO.getQuantity())));
    			items.add(shoppingOrderSettlementItemParam);
    		}
    		shoppingOrderSettlementParam.setItems(items);
    		// 订单总价等于货物总价+运费
    		shoppingOrderSettlementParam.setOrderTotalPrice(commodityTotalPrice);
    		shoppingOrderSettlementParam.setCommodityTotalPrice(commodityTotalPrice);
    		shoppingOrderSettlementParams.add(shoppingOrderSettlementParam);
    	}
    	
    	
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
	@SuppressWarnings("unchecked")
	@Override
	public Result<ShoppingCartSettlementDTO> settlement(List<Long> idList, String memberNum, Long memberId) {
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByIds(memberId, idList);
    	
    	if (!isSuccess(resultShoppingCartDTOS)) {
    		return successGet(resultShoppingCartDTOS);
    	}
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> ids = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		ids.add(shoppingCartDTO.getProductModelId());
    	}
    	
    	// 通过商品型号id列表查询商品信息
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<>(ids));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successGet(resultShoppingCartProductModelDTOS.getRet());
    	}
    	
    	
    	// 查找用户余额
    	Result<PropertyBalanceDTO> resultPropertyBalanceDTO = propertyInfoService.getPropertyBalance(memberNum);
    	if (!isSuccess(resultPropertyBalanceDTO)) {
    		return successGet(resultPropertyBalanceDTO.getRet());
    	}
    	
    	// 组装数据
    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {
    		if (!shoppingCartProductModelDTOMap.containsKey(shoppingCartProductModelDTO.getId())) {
    			shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    		}
    	}
    	
    	Map<Long, List<MemberShoppingCartDTO>> memberShoppingCartDTOMap = new HashMap<>();
    	
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
    		if (shoppingCartProductModelDTO.getStatus().equals(ProductStatusEnum.PRODUCT_STATUS_DEL) || shoppingCartProductModelDTO.getStatus().equals(ProductStatusEnum.PRODUCT_STATUS_DOWN)) {
    			memberShoppingCartDTO.setIsInvalid(true);
    		} else {
    			memberShoppingCartDTO.setIsInvalid(false);
    		}
    		
    		memberShoppingCartDTO.setInventory(shoppingCartProductModelDTO.getInventory());
    		
    		if (!memberShoppingCartDTOMap.containsKey(shoppingCartDTO.getMerchantId())) {
    			memberShoppingCartDTOMap.put(shoppingCartDTO.getMerchantId(), new ArrayList<>());
    		}
    		
    		memberShoppingCartDTOMap.get(shoppingCartDTO.getMerchantId()).add(memberShoppingCartDTO);
    		
    	}
    	
    	ShoppingCartSettlementDTO shoppingCartSettlementDTO = new ShoppingCartSettlementDTO();
    	
    	BigDecimal total = new BigDecimal(0);
    	// 每一个商家的商品会合并在一起，小计金额
    	List<ShoppingCartSettlementItemDTO> items = new ArrayList<>();
    	for (Map.Entry<Long, List<MemberShoppingCartDTO>> entry : memberShoppingCartDTOMap.entrySet()) {
    		ShoppingCartSettlementItemDTO shoppingCartSettlementItemDTO = new ShoppingCartSettlementItemDTO();
    		BigDecimal subtotal = new BigDecimal(0);
    		Integer productNumber = Integer.valueOf(0);
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

	@SuppressWarnings("unchecked")
	@Override
	public Result<ShoppingCartSettlementDTO> buyNow(Long memberId, String memberNum, ShoppingCartParam param) {
	    ShoppingCartSettlementDTO rtn = new ShoppingCartSettlementDTO();
	    ShoppingCartProductModelDTO shoppingCartProductModelDTO = null;
	    /*
	     *  查询产品信息
	     *  判断是否是抢购
	     */
	    if (param.getActivityProductModelId() == null) {
	        Result<ShoppingCartProductModelDTO> result = productModelService.getShoppingCartProductModel(param.getProductModelId());
	        if (!isSuccess(result)) {
	            return successCreated(result);
	        }
	        shoppingCartProductModelDTO = result.getModel();
	    } else {
	        Result<SeckillActivityProductModelInfoDTO> result = productModelService.seckillActivityProductModel(param.getActivityProductModelId());
	        if (!isSuccess(result)) {
                return successCreated(result);
            }
	        SeckillActivityProductModelInfoDTO model = result.getModel();
	        rtn.setActivityId(model.getActivityId());
	        rtn.setActivityProductId(model.getActivityProductId());
	        rtn.setActivityProductModelId(param.getActivityProductModelId());
	        param.setQuantity(1);
	        shoppingCartProductModelDTO = model;
	    }
    	// 查询商家名称
    	Result<MerchantInfoForShoppingCartDTO> getMerchantInfoForShoppingCartResult = merchantStoreService.getMerchantInfoForShoppingCart(shoppingCartProductModelDTO.getMerchantId());
    	if (!isSuccess(getMerchantInfoForShoppingCartResult)) {
    		return successCreated(getMerchantInfoForShoppingCartResult);
    	}
    	
    	List<MemberShoppingCartDTO> memberShoppingCartDTOList = new ArrayList<>();
    	MemberShoppingCartDTO memberShoppingCartDTO = new MemberShoppingCartDTO();
    	memberShoppingCartDTO.setMerchantName(getMerchantInfoForShoppingCartResult.getModel().getMerchantStoreName());
    	memberShoppingCartDTO.setMerchantStoreId(getMerchantInfoForShoppingCartResult.getModel().getMerchantStoreId());
    	memberShoppingCartDTO.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	memberShoppingCartDTO.setProductId(shoppingCartProductModelDTO.getProductId());
    	memberShoppingCartDTO.setProductModelId(shoppingCartProductModelDTO.getId());
    	memberShoppingCartDTO.setQuantity(param.getQuantity());
		memberShoppingCartDTO.setProductModelName(shoppingCartProductModelDTO.getName());
		memberShoppingCartDTO.setProductName(shoppingCartProductModelDTO.getProductName());
		memberShoppingCartDTO.setFeatureImage(shoppingCartProductModelDTO.getFeatureImage());
		memberShoppingCartDTO.setSalesPrice(shoppingCartProductModelDTO.getPrice());
		if (shoppingCartProductModelDTO.getStatus().equals(ProductStatusEnum.PRODUCT_STATUS_DEL) || shoppingCartProductModelDTO.getStatus().equals(ProductStatusEnum.PRODUCT_STATUS_DOWN)) {
			memberShoppingCartDTO.setIsInvalid(true);
		} else {
			memberShoppingCartDTO.setIsInvalid(false);
		}
		
		memberShoppingCartDTO.setInventory(shoppingCartProductModelDTO.getInventory());
		
		memberShoppingCartDTOList.add(memberShoppingCartDTO);
    	
    	// 查找用户余额
    	Result<PropertyBalanceDTO> resultPropertyBalanceDTO = propertyInfoService.getPropertyBalance(memberNum);
    	if (!isSuccess(resultPropertyBalanceDTO)) {
    		return successGet(resultPropertyBalanceDTO.getRet());
    	}
		
    	// 每一个商家的商品会合并在一起，小计金额
    	List<ShoppingCartSettlementItemDTO> items = new ArrayList<>();
		ShoppingCartSettlementItemDTO shoppingCartSettlementItemDTO = new ShoppingCartSettlementItemDTO();
		shoppingCartSettlementItemDTO.setSubtotal(memberShoppingCartDTO.getSalesPrice().multiply(new BigDecimal(memberShoppingCartDTO.getQuantity())));
		shoppingCartSettlementItemDTO.setProductNumber(memberShoppingCartDTO.getQuantity());
		shoppingCartSettlementItemDTO.setItems(memberShoppingCartDTOList);
		items.add(shoppingCartSettlementItemDTO);
		
    	rtn.setTotal(memberShoppingCartDTO.getSalesPrice().multiply(new BigDecimal(memberShoppingCartDTO.getQuantity())));
    	rtn.setItems(items);
    	
    	// 放入用户余额
    	rtn.setBalance(resultPropertyBalanceDTO.getModel().getBalance());
		
		return successCreated(rtn);
	}
	
	@BusinessInventoryCtrl(idParamIndex = 2, businessKey = BusinessKey.SECKILL_ACTIVITY_PRODUCT, using = SeckillActivityProductBusinessDecisionServiceImpl.class)
    @Override
    public Result<Long> buyNowCreateOrder(Long memberId, ShoppingOrderBuyNowCreateOrderForeignParam param, Long seckillActivityProductModelId) throws BusinessExecuteException {
	    Result<Long> result = buyNowCreateOrder(memberId, param);
	    if (!isSuccess(result)) {
	        throw new BusinessExecuteException(result.getRet(), result.getMsg());
	    }
        return result;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public Result<Long> buyNowCreateOrder(Long memberId, ShoppingOrderBuyNowCreateOrderForeignParam param) {
    	// 通过商品型号id列表查询商品信息
        ShoppingCartProductModelDTO shoppingCartProductModelDTO = null;
        /*
         *  查询产品信息
         *  判断是否是抢购
         */
        boolean isSeckillActivityProduct = param.getActivityProductModelId() != null;
        if (!isSeckillActivityProduct) {
            Result<ShoppingCartProductModelDTO> result = productModelService.getShoppingCartProductModel(param.getProductModelId());
            if (!isSuccess(result)) {
                return successCreated(result);
            }
            shoppingCartProductModelDTO = result.getModel();
        } else {
            Result<SeckillActivityProductModelInfoDTO> result = productModelService.seckillActivityProductModel(param.getActivityProductModelId());
            if (!isSuccess(result)) {
                return successCreated(result);
            }
            SeckillActivityProductModelInfoDTO model = result.getModel();
            param.setQuantity(1);
            shoppingCartProductModelDTO = model;
        }
    	
        // 判断商品是否失效
        if (param.getActivityProductModelId() == null && !ProductStatusEnum.PRODUCT_STATUS_UP.equals(shoppingCartProductModelDTO.getStatus())) {
            return successCreated(ResultCode.PRODUCT_HAS_EXPIRED);
        }
        
        // 判断库存
        if (shoppingCartProductModelDTO.getInventory() < param.getQuantity()) {
            return successCreated(ResultCode.INVENTORY_SHORTAGE);
        }
        
    	// 查询地址
    	Result<AddressDTO> resultAddressDTO = addressService.get(param.getAddressId());
    	if (!isSuccess(resultAddressDTO)) {
    		return successCreated(resultAddressDTO.getRet());
    	}
    	
    	// 查询商家是否支持七天退货
    	List<Long> merchantIdList = new ArrayList<>();
    	merchantIdList.add(shoppingCartProductModelDTO.getMerchantId());
    	ShoppingOrderFindUserInfoParam shoppingOrderFindUserInfoParam = new ShoppingOrderFindUserInfoParam();
    	shoppingOrderFindUserInfoParam.setMerchantIdList(merchantIdList);
    	shoppingOrderFindUserInfoParam.setMemberId(memberId);
    	Result<ShoppingOrderFindUserInfoDTO> shoppingOrderFindUserInfoDTOResult = merchantStoreService.shoppingOrderFindUserInfo(shoppingOrderFindUserInfoParam);
    	if (!isSuccess(shoppingOrderFindUserInfoDTOResult)) {
    		return successCreated(shoppingOrderFindUserInfoDTOResult.getRet());
    	}
    	
    	ShoppingOrderFindMerchantInfoDTO shoppingOrderFindMerchantInfoDTO = shoppingOrderFindUserInfoDTOResult.getModel().getShoppingOrderFindMerchantInfoDTOList().get(0);
    	
    	// 组装订单
    	List<ShoppingOrderSettlementParam> shoppingOrderSettlementParams = new ArrayList<>();
    	
		ShoppingOrderSettlementParam shoppingOrderSettlementParam = new ShoppingOrderSettlementParam();
		shoppingOrderSettlementParam.setMemberId(memberId);
		shoppingOrderSettlementParam.setMemberNum(shoppingOrderFindUserInfoDTOResult.getModel().getMemberNum());
		shoppingOrderSettlementParam.setMemberNickname(shoppingOrderFindUserInfoDTOResult.getModel().getMemberNickname());
		shoppingOrderSettlementParam.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
		shoppingOrderSettlementParam.setMerchantStoreId(shoppingOrderFindMerchantInfoDTO.getMerchantStoreId());
		shoppingOrderSettlementParam.setMerchantStoreRegionPath(shoppingOrderFindMerchantInfoDTO.getMerchantStoreRegionPath());
		shoppingOrderSettlementParam.setMerchantNum(shoppingOrderFindMerchantInfoDTO.getMerchantNum());
		shoppingOrderSettlementParam.setMerchantName(shoppingOrderFindMerchantInfoDTO.getMerchantStoreName());
		shoppingOrderSettlementParam.setMessage(param.getMessage());
		shoppingOrderSettlementParam.setFreightPrice(param.getFreightPrice());
		
		// 设置收货人信息,对应每个订单
		shoppingOrderSettlementParam.setConsigneeAddress(resultAddressDTO.getModel().getRegionName() + " " + resultAddressDTO.getModel().getAddr());
		shoppingOrderSettlementParam.setConsigneeName(resultAddressDTO.getModel().getName());
		shoppingOrderSettlementParam.setConsigneeMobile(resultAddressDTO.getModel().getMobile());
		
		// 是否支持七天退货
		shoppingOrderSettlementParam.setIsNoReasonReturn(shoppingOrderFindMerchantInfoDTO.getIsNoReasonReturn());
		
		// 用户是否是商家粉丝
		shoppingOrderSettlementParam.setIsFans(shoppingOrderFindMerchantInfoDTO.getIsFans());
		if (isSeckillActivityProduct) {
		    SeckillActivityProductModelInfoDTO seckillActivityProductModelInfoDTO = (SeckillActivityProductModelInfoDTO) shoppingCartProductModelDTO;
		    shoppingOrderSettlementParam.setActivityId(seckillActivityProductModelInfoDTO.getActivityId());
		    shoppingOrderSettlementParam.setActivityProductId(seckillActivityProductModelInfoDTO.getActivityProductId());
		}
		
		BigDecimal commodityTotalPrice = new BigDecimal(0);
		List<ShoppingOrderSettlementItemParam> items = new ArrayList<>();
		
		ShoppingOrderSettlementItemParam shoppingOrderSettlementItemParam = new ShoppingOrderSettlementItemParam();
		// 加入购物车id,用于在保存订单之后删除购物车记录
		shoppingOrderSettlementItemParam.setIsAllowRefund(shoppingCartProductModelDTO.getIsAllowRefund());
		shoppingOrderSettlementItemParam.setProductId(shoppingCartProductModelDTO.getProductId());
		shoppingOrderSettlementItemParam.setProductName(shoppingCartProductModelDTO.getProductName());
		shoppingOrderSettlementItemParam.setProductFeatureImage(shoppingCartProductModelDTO.getFeatureImage());
		shoppingOrderSettlementItemParam.setProductModelId(shoppingCartProductModelDTO.getId());
		if (isSeckillActivityProduct) {
		    shoppingOrderSettlementItemParam.setActivityProductModelId(param.getActivityProductModelId());
		}
		shoppingOrderSettlementItemParam.setProductModelName(shoppingCartProductModelDTO.getName());
		
		shoppingOrderSettlementItemParam.setQuantity(param.getQuantity());
		shoppingOrderSettlementItemParam.setRegularPrice(shoppingCartProductModelDTO.getOriginalPrice());
		shoppingOrderSettlementItemParam.setSalesPrice(shoppingCartProductModelDTO.getPrice());
		commodityTotalPrice = commodityTotalPrice.add(shoppingCartProductModelDTO.getPrice().multiply(new BigDecimal(param.getQuantity())));
		
		items.add(shoppingOrderSettlementItemParam);
		shoppingOrderSettlementParam.setItems(items);
		/*
		 *  订单总价等于货物总价+运费
		 *  暂时不把运费计算在内
		 */
		shoppingOrderSettlementParam.setOrderTotalPrice(commodityTotalPrice);
		shoppingOrderSettlementParam.setCommodityTotalPrice(commodityTotalPrice);
		shoppingOrderSettlementParams.add(shoppingOrderSettlementParam);
    	
    	// 保存订单
    	Result<List<Long>> resultIds = shoppingOrderService.save(shoppingOrderSettlementParams);
    	if (!isSuccess(resultIds)) {
    		return successCreated(resultIds.getRet());
    	}
    	
    	return successCreated(resultIds);
	}
}
