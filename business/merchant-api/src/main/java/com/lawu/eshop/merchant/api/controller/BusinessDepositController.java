package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.BusinessDepositService;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.merchant.api.service.OrderService;
import com.lawu.eshop.order.dto.ShoppingOrderIsNoOnGoingOrderDTO;
import com.lawu.eshop.property.dto.BusinessDepositDetailDTO;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.BusinessDepositSaveParam;
import com.lawu.eshop.property.param.BusinessRefundDepositDataParam;
import com.lawu.eshop.property.param.BusinessRefundDepositParam;
import com.lawu.eshop.user.dto.MerchantStoreDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 商家保证金
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月15日 上午10:57:34
 *
 */
@Api(tags = "businessDeposit")
@RestController
@RequestMapping(value = "businessDeposit/")
public class BusinessDepositController extends BaseController {

	@Autowired
	private BusinessDepositService businessDepositService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MerchantStoreService merchantStoreService;

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商家缴纳保证金初始化记录", notes = "商家缴纳保证金初始化记录，支付前需要调该接口初始化一条保证金记录，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BusinessDepositSaveParam param) {
		BusinessDepositSaveDataParam bparam = new BusinessDepositSaveDataParam();
		bparam.setBusinessId(UserUtil.getCurrentUserId(getRequest()));
		bparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		bparam.setBusinessAccount(UserUtil.getCurrentAccount(getRequest()));
		Result<MerchantStoreDTO> store = merchantStoreService.selectMerchantStoreByMId(UserUtil.getCurrentUserId(getRequest()));
		if(store.getRet() != ResultCode.SUCCESS){
			return store;
		}
		String region = store.getModel().getRegionPath();
		if(region == null || region.split("/").length != 3){
			return successCreated(ResultCode.STORE_REGION_PATH_ERROR);
		}
		bparam.setBusinessName(store.getModel().getName());
		bparam.setProvinceId(region.split("/")[0]);
		bparam.setCityId(region.split("/")[1]);
		bparam.setAreaId(region.split("/")[2]);
		return businessDepositService.save(bparam);
	}

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@ApiOperation(value = "查看我的保证金", notes = "查看我的保证金,[]（杨清华）", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "selectDeposit/{merchantId}", method = RequestMethod.GET)
	public Result<BusinessDepositDetailDTO> selectDeposit(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
		return businessDepositService.selectDeposit(UserUtil.getCurrentUserId(getRequest()).toString());
	}

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "申请退保证金操作", notes = "申请退保证金,[]（杨清华）", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "refundDeposit", method = RequestMethod.POST)
	public Result refundDeposit(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BusinessRefundDepositParam param) {

		// 需要判断是否满足退保证金条件：无未完结订单、三个月(已财务核实时间为准)
		Result<ShoppingOrderIsNoOnGoingOrderDTO> dto = orderService
				.isNoOnGoingOrder(UserUtil.getCurrentUserId(getRequest()));
		if (!dto.getModel().getIsNoOnGoingOrder()) {
			return successCreated(ResultCode.DEPOSIT_EXIST_ING_ORDER);
		}
		
		BusinessRefundDepositDataParam dparam = new BusinessRefundDepositDataParam();
		dparam.setId(param.getId());
		dparam.setBusinessBankAccountId(param.getBusinessBankAccountId());
		dparam.setUserName(UserUtil.getCurrentUserNum(getRequest()));

		return businessDepositService.refundDeposit(dparam);
	}

	@Audit(date = "2017-05-12", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "获取保证金", notes = "获取保证金,[]（杨清华）", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "getDepositValue", method = RequestMethod.GET)
	public Result getDepositValue(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
		return businessDepositService.getDepositValue();
	}
}
