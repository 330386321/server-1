package com.lawu.eshop.merchant.api.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.PropertySrvPropertyService;
import com.lawu.eshop.merchant.api.service.RechargeService;
import com.lawu.eshop.merchant.api.service.WxPayService;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.ThirdPayBodyEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.param.ThirdPayParam;
import com.lawu.eshop.utils.QrCodeUtil;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 微信
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月7日 下午9:01:21
 *
 */
@Api(tags = "wxPay")
@RestController
@RequestMapping(value = "wxPay/")
public class WxPayController extends BaseController {

	@Autowired
	private WxPayService wxPayService;
	@Autowired
	private PropertySrvPropertyService propertyService;
	@Autowired
	private RechargeService rechargeService;

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "app调用微信生成预支付订单返回签名加密参数", notes = "app调用微信生成预支付订单返回签名加密参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "getPrepayInfo", method = RequestMethod.POST)
	public Result getPrepayInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ThirdPayParam param) {

		ThirdPayDataParam aparam = new ThirdPayDataParam();
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setSubject(param.getThirdPayBodyEnum().val);
		aparam.setBizIds(param.getBizIds());
		aparam.setThirdPayBodyEnum(param.getThirdPayBodyEnum());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMCHANT);
		aparam.setMerchantId(UserUtil.getCurrentUserId(getRequest()));

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(param.getBizFlagEnum().val)) {
			Result bondRet = propertyService.getValue(PropertyType.MERCHANT_BONT);
			String bond = bondRet.getModel().toString();
			if ("".equals(bond)) {
				bond = PropertyType.MERCHANT_BONT_DEFAULT;
			}
			aparam.setTotalAmount(bond);
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(param.getBizFlagEnum().val)
				|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(param.getBizFlagEnum().val)) {
			ThirdPayCallBackQueryPayOrderDTO recharge = rechargeService.getRechargeMoney(param.getBizIds());
			double money = recharge.getActualMoney();
			if (StringUtil.doubleCompareTo(money, 0) == 0) {
				return successCreated(ResultCode.MONEY_IS_ZERO);
			}
			aparam.setTotalAmount(String.valueOf(money));
		}

		return wxPayService.getPrepayInfo(aparam);

	}

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "PC端商家充值余额、积分、缴纳保证金接口返回扫码支付二维码", notes = "PC端商家充值余额、积分、缴纳保证金接口返回扫码支付二维码，[]，(杨清华)", httpMethod = "GET")
	@RequestMapping(value = "initPcPay", method = RequestMethod.GET)
	public void initPcPay(@RequestParam @ApiParam(required = true, value = "token") String token,
			@RequestParam @ApiParam(required = true, value = "业务表ID") String bizIds,
			@RequestParam @ApiParam(required = true, value = "参考链接：http://192.168.1.21:8090/pages/viewpage.action?pageId=1998868") ThirdPayBodyEnum thirdPayBodyEnum,
			@RequestParam @ApiParam(required = true, value = "参考链接：http://192.168.1.21:8090/pages/viewpage.action?pageId=1998868") ThirdPartyBizFlagEnum bizFlagEnum)
			throws IOException {
		String userNum = UserUtil.getCurrentUserNumByToken(token);
		ThirdPayDataParam aparam = new ThirdPayDataParam();
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setSubject(thirdPayBodyEnum.val);
		aparam.setBizIds(bizIds);
		aparam.setThirdPayBodyEnum(thirdPayBodyEnum);
		aparam.setBizFlagEnum(bizFlagEnum);
		aparam.setUserNum(userNum);
		aparam.setUserTypeEnum(UserTypeEnum.MEMCHANT_PC);
		aparam.setMerchantId(Long.valueOf(UserUtil.getCurrentUserIdByToken(token) == null ? "0" : UserUtil.getCurrentUserIdByToken(token)));

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(bizFlagEnum.val)) {
			Result bondRet = propertyService.getValue(PropertyType.MERCHANT_BONT);
			String bond = bondRet.getModel().toString();
			if ("".equals(bond)) {
				bond = PropertyType.MERCHANT_BONT_DEFAULT;
			}
			aparam.setTotalAmount(bond);
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(bizFlagEnum.val)
				|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(bizFlagEnum.val)) {
			ThirdPayCallBackQueryPayOrderDTO recharge = rechargeService.getRechargeMoney(bizIds);
			double money = recharge.getActualMoney();
			if (StringUtil.doubleCompareTo(money, 0) == 0) {
				return;
			}
			aparam.setTotalAmount(String.valueOf(money));
		}
		Result ret = wxPayService.getPrepayInfo(aparam);
		HttpServletResponse response = getResponse();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		Map<Object, Object> map = (Map<Object, Object>) ret.getModel();
		BufferedImage buffImg = QrCodeUtil.generateQrCode(map.get("codeUrl").toString());
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

}
