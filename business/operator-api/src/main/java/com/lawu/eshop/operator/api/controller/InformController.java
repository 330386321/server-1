/**
 * 
 */
package com.lawu.eshop.operator.api.controller;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.dto.InformDTO;
import com.lawu.eshop.mall.dto.InformEnum;
import com.lawu.eshop.mall.dto.InformStatusEnum;
import com.lawu.eshop.mall.param.InformDownParam;
import com.lawu.eshop.mall.param.InformEditParam;
import com.lawu.eshop.mall.param.InformQueryParam;
import com.lawu.eshop.operator.api.service.AdPlatformService;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.InformService;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.operator.api.service.UserService;
import com.lawu.eshop.product.constant.ProductStatusEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import om.lawu.eshop.shiro.util.UserUtil;

/**
 * 运营后台举报管理
 * 
 * @author lihj
 * @date 2017年7月28日
 */
@Api(tags = "inform")
@RestController
@RequestMapping(value = "inform/")
public class InformController extends BaseController {

	@Autowired
	private InformService informService;
	@Autowired
	private UserService userService;
	@Autowired
	private AdService adService;
	@Autowired
	private ProductAuditService productAuditService;
	@Autowired
	private AdPlatformService adPlatformService;// 广告

	@ApiOperation(value = "查询举报列表信息", notes = "查询举报列表信息,[]（李洪军）", httpMethod = "GET")
	@RequiresAuthentication
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@PageBody
	@RequestMapping(value = "selectInformList", method = RequestMethod.GET)
	public Result<Page<InformDTO>> selectInformList(@ModelAttribute @ApiParam(value = "查询信息") InformQueryParam param) {
		Result<Page<InformDTO>> page = informService.selectInformList(param);
		return page;
	}

	@ApiOperation(value = "处理举报信息", notes = "处理举报信息(李洪军)", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "editInform", method = RequestMethod.POST)
	public Result editInform(@ModelAttribute @ApiParam(value = "下架、不处理信息") InformDownParam param) {
		InformEditParam edit = new InformEditParam();
		edit.setId(param.getId());
		edit.setAuditorId(userService.getUserByAccount(UserUtil.getCurrentUserAccount()).getModel().getId());
		edit.setAuditorName(UserUtil.getCurrentUserAccount());
		edit.setAuditTime(new Date());
		edit.setGmtModified(new Date());
		edit.setRemark(param.getRemark());
		if (param.getStatus() == 1) {
			edit.setStatus(InformStatusEnum.INFORM_ALREADY_PROCESSED.getVal());
		} else if (param.getStatus() == 2) {
			edit.setStatus(InformStatusEnum.INFORM_NOT_HANDLED.getVal());
		}
		if (param.getInformType() == InformEnum.INFORM_TYPE_PLAT) {
			//adPlatformService.unShelve(param.getInformtItemId());// 平面广告
		} else if (param.getInformType() == InformEnum.INFORM_TYPE_GOODS) {// 商品
			/*productAuditService.updateProductStatus(param.getInformtItemId().toString(),
					ProductStatusEnum.PRODUCT_STATUS_DOWN);*/
		} else if (param.getInformType() == InformEnum.INFORM_TYPE_MERCHANT) {// 商家

		} else if (param.getInformType() == InformEnum.INFORM_TYPE_PRAISE) {// E赞
			adService.operatorUpdateAdStatus(param.getInformtItemId(), AdStatusEnum.AD_STATUS_OUT);
		}
		return informService.editInform(edit);
	}

}
