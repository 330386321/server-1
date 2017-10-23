package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.ThirdPayRefundParam;
import com.lawu.eshop.pay.handle.AlipayBusinessHandle;
import com.lawu.eshop.pay.handle.WxpayBusinessHandle;
import com.lawu.eshop.pay.sdk.alipay.AliPayConfigParam;
import com.lawu.eshop.pay.sdk.weixin.base.WxPayConfigParam;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.MerchantAdRefundDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.PropertySrvConfig;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.service.AdService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdServiceImpl implements AdService {

    private static Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

    @Autowired
    private TransactionDetailService transactionDetailService;

    @Autowired
    @Qualifier("merchantAdPaymentTransactionMainServiceImpl")
    private TransactionMainService<Reply> merchantAdPaymentTransactionMainServiceImpl;
    @Autowired
    private PropertySrvConfig propertySrvConfig;
    @Autowired
    private TransactionDetailDOMapper transactionDetailDOMapper;

    @Override
    @Transactional
    public int doHandleMerchantAdNotify(NotifyCallBackParam param) {
        boolean isPay = transactionDetailService.verifyOrderIsPaySuccess(param);
        if (isPay) {
            logger.info("重复回调(判断幂等)-商家发E咻&红包");
            return ResultCode.PROCESSED_RETURN_SUCCESS;
        }

        // 新增商家交易记录
        TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
        tdsParam.setTitle(MerchantTransactionTypeEnum.RED_PACKET.getName());
        tdsParam.setUserNum(param.getUserNum());
        tdsParam.setTransactionAccount(param.getBuyerLogonId());
        tdsParam.setTransactionType(MerchantTransactionTypeEnum.RED_PACKET.getValue());
        tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().getVal());
        tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
        tdsParam.setBizId(param.getBizIds());
        tdsParam.setThirdTransactionNum(param.getTradeNo());
        tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.getVal());
        tdsParam.setBizNum(param.getOutTradeNo());
        tdsParam.setRegionPath(param.getRegionPath());
        transactionDetailService.save(tdsParam);

        merchantAdPaymentTransactionMainServiceImpl.sendNotice(tdsParam.getId());

        return ResultCode.SUCCESS;
    }

    @Override
    @Transactional
    public int doRefund(MerchantAdRefundDataParam param) throws Exception {
        TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
        tdsParam.setTitle(MerchantTransactionTypeEnum.AD_DOWN.getName());
        tdsParam.setUserNum(param.getUserNum());
        tdsParam.setTransactionType(MerchantTransactionTypeEnum.AD_DOWN.getValue());
        tdsParam.setTransactionAccount("");
        tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().getVal());
        tdsParam.setAmount(new BigDecimal(param.getRefundMoney()));
        tdsParam.setBizId(param.getAdId());
        tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
        tdsParam.setBizNum(param.getTradeNo() == null ? "" : param.getTradeNo());
        transactionDetailService.save(tdsParam);

        JsonResult jsonResult = new JsonResult();
        ThirdPayRefundParam rparam = new ThirdPayRefundParam();
        rparam.setRefundId(StringUtil.getRandomNumAppend(param.getAdId()));
        rparam.setRefundMoney(param.getRefundMoney());
        rparam.setTradeNo(param.getTradeNo());
        if (TransactionPayTypeEnum.ALIPAY.getVal().equals(param.getTransactionPayTypeEnum().getVal())) {
            AliPayConfigParam aliPayConfigParam = new AliPayConfigParam();
            aliPayConfigParam.setAlipayRefundUrl(propertySrvConfig.getAlipayRefundUrl());
            aliPayConfigParam.setAlipayAppIdMember(propertySrvConfig.getAlipayAppIdMember());
            aliPayConfigParam.setAlipayPrivateKey(propertySrvConfig.getAlipayPrivateKey());
            aliPayConfigParam.setAlipayEdianMemberPublicKey(propertySrvConfig.getAlipayEdianMemberPublicKey());
            aliPayConfigParam.setAlipayPublicKey(propertySrvConfig.getAlipayPublicKey());
            aliPayConfigParam.setAlipayPartner(propertySrvConfig.getAlipayPartner());
            aliPayConfigParam.setAlipayHttpsVerifyUrl(propertySrvConfig.getAlipayHttpsVerifyUrl());
            aliPayConfigParam.setAlipaySignType(propertySrvConfig.getAlipaySignType());
            aliPayConfigParam.setAlipayInputCharset(propertySrvConfig.getAlipayInputCharset());
            AlipayBusinessHandle.refund(rparam, jsonResult, aliPayConfigParam);

        } else if (TransactionPayTypeEnum.WX.getVal().equals(param.getTransactionPayTypeEnum().getVal())) {

            TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
            transactionDetailDOExample.createCriteria().andThirdTransactionNumEqualTo(param.getTradeNo()).andUserNumEqualTo(param.getUserNum());
            List<TransactionDetailDO> transactionDetailList = transactionDetailDOMapper.selectByExample(transactionDetailDOExample);
            if (transactionDetailList == null || transactionDetailList.isEmpty()) {
                logger.error("微信发广告({})退款根据第三方订单号({})和用户编号({})查询交易明细支付总金额时返回为空！", param.getAdId(), param.getTradeNo(), param.getUserNum());
                return ResultCode.FAIL;
            }
            TransactionDetailDO transactionDetailDO = transactionDetailList.get(0);
            rparam.setTotalMoney(transactionDetailDO.getAmount().toString());

            WxPayConfigParam wxPayConfigParam = new WxPayConfigParam();
            wxPayConfigParam.setWxpayAppIdMember(propertySrvConfig.getWxpayAppIdBusiness());
            wxPayConfigParam.setWxpayMchIdMember(propertySrvConfig.getWxpayMchIdBusiness());
            wxPayConfigParam.setWxpayKey(propertySrvConfig.getWxpayKey());
            wxPayConfigParam.setWxpayAppId(propertySrvConfig.getWxpayAppId());
            wxPayConfigParam.setWxpayMchId(propertySrvConfig.getWxpayMchId());
            wxPayConfigParam.setWxpayCertLocalPathMember(propertySrvConfig.getWxpayCertLocalPathBusinessPc());
            wxPayConfigParam.setWxpayCertPasswordMember(propertySrvConfig.getWxpayCertPasswordBusinessPc());
            wxPayConfigParam.setWxpayCertBasePath(propertySrvConfig.getWxpayCertLocalPathMember());
            wxPayConfigParam.setWxpayRefundApi(propertySrvConfig.getWxpayRefundApi());
            wxPayConfigParam.setWxpayHttpsRequestClassName(propertySrvConfig.getWxpayHttpsRequestClassName());
            wxPayConfigParam.setWxpayKeyApp(propertySrvConfig.getWxpayKeyApp());
            WxpayBusinessHandle.refund(rparam, jsonResult, wxPayConfigParam);
        }

        if (!jsonResult.isSuccess()) {
            logger.error(jsonResult.getMessage());
            throw new RuntimeException(jsonResult.getMessage());
        }
        return ResultCode.SUCCESS;
    }
}
