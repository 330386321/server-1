package com.lawu.eshop.pay.sdk.weixin.sdk.service;

import com.lawu.eshop.pay.sdk.weixin.base.Configure;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
import com.lawu.eshop.pay.sdk.weixin.sdk.protocol.refund_protocol.RefundReqData;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月13日 下午1:47:09
 *
 */
public class RefundService extends BaseService{

    public RefundService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.REFUND_API);
    }

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param jsonResult 
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(RefundReqData refundReqData, JsonResult jsonResult) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(refundReqData,jsonResult);

        return responseString;
    }

}
