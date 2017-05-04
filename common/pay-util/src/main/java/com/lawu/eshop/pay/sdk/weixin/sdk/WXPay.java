package com.lawu.eshop.pay.sdk.weixin.sdk;

import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
import com.lawu.eshop.pay.sdk.weixin.sdk.protocol.refund_protocol.RefundReqData;
import com.lawu.eshop.pay.sdk.weixin.sdk.service.RefundService;
import com.lawu.eshop.property.param.WxPayConfigParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月13日 下午1:47:17
 *
 */
public class WXPay {

    /**
     * 初始化SDK依赖的几个关键配置
     * @param key 签名算法需要用到的秘钥
     * @param appID 公众账号ID
     * @param mchID 商户ID
     * @param sdbMchID 子商户ID，受理模式必填
     * @param certLocalPath HTTP证书在服务器中的路径，用来加载证书用
     * @param certPassword HTTP证书的密码，默认等于MCHID
     */
    public static void initSDKConfiguration(String key,String appID,String mchID,String sdbMchID,String certLocalPath,String certPassword){
        //Configure.setKey(key);
        //Configure.setAppID(appID);
        //Configure.setMchID(mchID);
        ////Configure.setSubMchID(sdbMchID);
        //Configure.setCertLocalPathMember(certLocalPath);
        //Configure.setCertPasswordMember(certPassword);
    }

   

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param jsonResult 
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundService(RefundReqData refundReqData, JsonResult jsonResult, WxPayConfigParam wxPayConfigParam) throws Exception{
        return new RefundService(wxPayConfigParam.getWxpay_refund_api(),wxPayConfigParam.getWxpay_https_request_class_name()).request(refundReqData,jsonResult, wxPayConfigParam);
    }

    


}
