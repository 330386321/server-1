package com.lawu.eshop.order.srv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class OrderSrvConfig {

    @Value(value = "${db.type}")
    private String type;

    @Value(value = "${db.driver}")
    private String driver;

    @Value(value = "${db.url}")
    private String url;

    @Value(value = "${db.username}")
    private String username;

    @Value(value = "${db.password}")
    private String password;

    //电商ID
    @Value("${express.kauidiniao.EBusinessID}")
    private String kdnEbusinessID;

    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    @Value("${express.kauidiniao.AppKey}")
    private String kdnAppKey;

    //请求url
    @Value("${express.kauidiniao.ReqURL}")
    private String kdnReqURL;

    public String getType() {
        return type;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

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
