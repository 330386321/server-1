package com.lawu.eshop.pay.srv.sdk.weixin.sdk.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.lawu.eshop.pay.srv.sdk.weixin.base.Configure;
import com.lawu.eshop.pay.srv.sdk.weixin.sdk.common.JsonResult;

/**
 * User: rizenguo
 * Date: 2014/12/10
 * Time: 15:44
 * 服务的基类
 */
@SuppressWarnings("rawtypes")
public class BaseService{

    //API的地址
    private String apiURL;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;

	public BaseService(String api) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        apiURL = api;
        Class c = Class.forName(Configure.HttpsRequestClassName);
        serviceRequest = (IServiceRequest) c.newInstance();
    }

    protected String sendPost(Object xmlObj, JsonResult jsonResult) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiURL,xmlObj,jsonResult);
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IServiceRequest request){
        serviceRequest = request;
    }
}
