package com.lawu.eshop.order.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class OrderSrvConfig {

    //电商ID
    @Value("${express.kauidiniao.EBusinessID}")
    private String kdnEbusinessID;

    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    @Value("${express.kauidiniao.AppKey}")
    private String kdnAppKey;

    //请求url
    @Value("${express.kauidiniao.ReqURL}")
    private String kdnReqURL;

    public String getKdnEbusinessID() {
        return kdnEbusinessID;
    }

    public String getKdnAppKey() {
        return kdnAppKey;
    }

    public String getKdnReqURL() {
        return kdnReqURL;
    }
}
