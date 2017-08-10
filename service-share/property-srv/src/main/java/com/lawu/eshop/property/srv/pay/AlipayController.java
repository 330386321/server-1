package com.lawu.eshop.property.srv.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.sdk.alipay.util.AlipaySubmit;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.pay.sdk.alipay.AliPayConfigParam;
import com.lawu.eshop.property.param.PcAlipayDataParam;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.srv.PropertySrvConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author Yangqh
 * @date 2017年4月7日 下午8:00:08
 */
@RestController
@RequestMapping(value = "alipay/")
public class AlipayController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AlipayController.class);

    public static final String split = "|";

    @Autowired
    private PropertySrvConfig propertySrvConfig;

    /**
     * 客户端调用支付宝获取请求参数
     *
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "getAppAlipayReqParams", method = RequestMethod.POST)
    public Result getAppAlipayReqParams(@RequestBody @Valid ThirdPayDataParam param, BindingResult result) {

        String message = validate(result);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }

        String appId = "";
        String public_key = "";
        if (param.getUserTypeEnum().getVal().equals(UserTypeEnum.MEMBER.getVal())) {
            appId = propertySrvConfig.getAlipayAppIdMember();
            public_key = propertySrvConfig.getAlipayEdianMemberPublicKey();
        } else if (param.getUserTypeEnum().getVal().equals(UserTypeEnum.MEMCHANT.getVal())) {
            appId = propertySrvConfig.getAlipayAppIdBusiness();
            public_key = propertySrvConfig.getAlipayEdianBusinessPublicKey();
        }
        String passback_params = param.getBizFlagEnum().getVal() + split + param.getUserNum() + split
                + param.getThirdPayBodyEnum().getVal() + split + param.getBizIds() + split + param.getSideUserNum() + split + param.getMerchantId() + split + param.getRegionPath();
        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(propertySrvConfig.getAlipayGateway(), appId,
                propertySrvConfig.getAlipayPrivateKey(), "json", "utf-8", public_key, "RSA");
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(param.getSubject());
        model.setOutTradeNo(param.getOutTradeNo());
        model.setTotalAmount(param.getTotalAmount());
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setPassbackParams(passback_params);
        request.setBizModel(model);
        request.setNotifyUrl(propertySrvConfig.getAlipayNotifyUrl());
        String msg = "";
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            msg = response.getBody();// 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            logger.error("支付宝支付封装参数错误，错误信息：{}", e.getMessage(), e);
            return successCreated(ResultCode.FAIL);
        }
        return successCreated(msg);
    }

    /**
     * pc端生成预支付订单返回扫码二维码
     *
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "initPcPay", method = RequestMethod.POST)
    public Result initPcPay(@RequestBody @Valid PcAlipayDataParam param, BindingResult result) {

        String message = validate(result);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("service", "create_direct_pay_by_user");
        paramMap.put("partner", propertySrvConfig.getAlipayPartner());
        paramMap.put("seller_id", propertySrvConfig.getAlipaySellerId());
        paramMap.put("_input_charset", propertySrvConfig.getAlipayInputCharset());
        paramMap.put("payment_type", "1");
        paramMap.put("notify_url", propertySrvConfig.getAlipayNotifyUrlPc());
        paramMap.put("return_url", propertySrvConfig.getAlipayReturnUrlPc());
        paramMap.put("anti_phishing_key", "");
        paramMap.put("exter_invoke_ip", "");

        paramMap.put("out_trade_no", param.getOutTradeNo());
        paramMap.put("subject", param.getSubject());
        if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.getVal().equals(param.getBizFlagEnum().getVal())) {
            paramMap.put("extra_common_param", param.getBizFlagEnum().getVal() + split + param.getUserNum() + split
                    + "商家缴纳保证金P" + split + param.getBizId() + split + param.getMerchantId());
        } else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.getVal().equals(param.getBizFlagEnum().getVal())) {
            paramMap.put("extra_common_param", param.getBizFlagEnum().getVal() + split + param.getUserNum() + split
                    + "商家充值余额P" + split + param.getBizId());
        } else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.getVal().equals(param.getBizFlagEnum().getVal())) {
            paramMap.put("extra_common_param", param.getBizFlagEnum().getVal() + split + param.getUserNum() + split
                    + "商家充值积分P" + split + param.getBizId());
        }
        paramMap.put("total_fee", param.getTotalAmount());

        AliPayConfigParam aliPayConfigParam = new AliPayConfigParam();
        aliPayConfigParam.setAlipaySignType(propertySrvConfig.getAlipaySignType());
        aliPayConfigParam.setAlipayPrivateKey(propertySrvConfig.getAlipayPrivateKey());
        aliPayConfigParam.setAlipayInputCharset(propertySrvConfig.getAlipayInputCharset());
        String sHtmlText = AlipaySubmit.buildRequest(paramMap, "get", "确认", aliPayConfigParam);

        String html = "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
        sHtmlText = html + sHtmlText;

        return successCreated(sHtmlText);
    }

}
