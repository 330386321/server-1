/**
 * 
 */
package com.lawu.eshop.operator.api.controller;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.MerchantInfoDTO;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.dto.InformDTO;
import com.lawu.eshop.mall.dto.InformEnum;
import com.lawu.eshop.mall.dto.InformStatusEnum;
import com.lawu.eshop.mall.param.InformDownParam;
import com.lawu.eshop.mall.param.InformEditParam;
import com.lawu.eshop.mall.param.InformQueryParam;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.InformService;
import com.lawu.eshop.operator.api.service.MessageService;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.operator.api.service.UserService;
import com.lawu.eshop.operator.dto.UserListDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;

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
	private MessageService messageService ;

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "查询举报列表信息", notes = "查询举报列表信息,[]（李洪军）", httpMethod = "GET")
	@RequiresAuthentication
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@PageBody
	@RequiresPermissions("inform:list")
	@RequestMapping(value = "selectInformList", method = RequestMethod.GET)
	public Result<Page<InformDTO>> selectInformList(@ModelAttribute @ApiParam(value = "查询信息") InformQueryParam param) {
		Result<Page<InformDTO>> page = informService.selectInformList(param);
		return page;
	}

	@ApiOperation(value = "处理举报信息", notes = "处理举报信息(李洪军)", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequiresPermissions("inform:edit")
	@RequestMapping(value = "editInform", method = RequestMethod.POST)
	public Result editInform(@ModelAttribute @ApiParam(value = "下架、不处理信息") InformDownParam param) {
		Integer auditorId = 0;
		Result<UserListDTO> userResult = userService.getUserByAccount(UserUtil.getCurrentUserAccount());
		if(isSuccess(userResult)){
			auditorId = userResult.getModel().getId();
		}

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
		String merchantNum="" , title ="";
		MessageTypeEnum enumType=null;

		if (param.getInformType() == InformEnum.INFORM_TYPE_GOODS) {// 商品
			Result result = productService.downOperatorById(param.getInformtItemId(), param.getRemark());
			Result<ProductInfoDTO> res = productService.selectProductById(param.getInformtItemId());
			merchantNum=res.getModel().getMerchantUserNum();
			enumType =MessageTypeEnum.MESSAGE_TYPE_PRODUCT_FORCE_DOWN;
			title=res.getModel().getName();
		} else if (param.getInformType() == InformEnum.INFORM_TYPE_MERCHANT) {// 商家

		} else {// 广告
			Result rs = adService.downOperatorById(param.getInformtItemId(), auditorId, param.getRemark());
			Result<MerchantInfoDTO>  res = adService.selectMerchantNumByAdId(param.getInformtItemId());
			merchantNum=res.getModel().getMerchantNum();
			enumType =MessageTypeEnum.MESSAGE_TYPE_AD_FORCE_DOWN;
			title= res.getModel().getTitle();
		}
		Result  result = informService.editInform(edit);
		if(isSuccess(result)){

			MessageInfoParam messageInfoParam = new MessageInfoParam();
			MessageTempParam messageTempParam = new MessageTempParam();
			messageTempParam.setAdTypeName(title);
			messageInfoParam.setRelateId(param.getInformtItemId());
			messageTempParam.setFailReason(param.getRemark());
			messageInfoParam.setTypeEnum(enumType);
			messageInfoParam.setMessageParam(messageTempParam);

			messageService.saveMessage(merchantNum, messageInfoParam);
		}
		return result;
	}

}