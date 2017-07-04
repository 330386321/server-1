package com.lawu.eshop.member.api.controller;

import java.util.List;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.DiscountPackageDetailDTO;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import com.lawu.eshop.member.api.service.DiscountPackageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny
 * @date 2017/3/27
 */
@Api(tags = "discountPackage")
@RestController
@RequestMapping(value = "discountPackage/")
public class DiscountPackageController extends BaseController {

	@Autowired
	private DiscountPackageService discountPackageService;

	/**
	 * 根据商家id查询商家的所有优惠套餐
	 * 
	 * @param merchantId 商家id
	 * @return
	 * @author Sunny
	 * @date 2017年6月26日
	 */
	@Audit(date = "2017-07-04", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询优惠套餐列表", notes = "根据商家id查询商家全部优惠套餐列表,不分页。[]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@Authorization
	@RequestMapping(value = "list/{merchantId}", method = RequestMethod.GET)
	public Result<Page<DiscountPackageQueryDTO>> list(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ApiParam(value = "商家id") @PathVariable("merchantId") Long merchantId) {
		Result<Page<DiscountPackageQueryDTO>> result = discountPackageService.listForMember(merchantId);
		return successGet(result);
	}
	
	/**
	 * 根据优惠套餐id查询优惠套餐详情
	 * 
	 * @param id 优惠套餐id
	 * @return
	 * @author Sunny
	 * @date 2017年6月26日
	 */
	@Audit(date = "2017-07-04", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询优惠套餐详情", notes = "查询单个优惠套餐详情。[]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@Authorization
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Result<DiscountPackageDetailDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,  @ApiParam(value = "优惠套餐id") @PathVariable("id") Long id) {
		Result<DiscountPackageDetailDTO> result = discountPackageService.get(id);
		return successGet(result);
	}
}
