package com.lawu.eshop.product.srv.db;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Leach
 * @date 2017/3/13
 */
@ConfigurationProperties(prefix = DataSourceProperties.PREFIX, ignoreUnknownFields = false)
public class DataSourceProperties {

    public DataSourceProperties() {
        super();
    }

    //对应配置文件里的配置键
    public final static String PREFIX = "jdbc";

    private String type = "mysql";
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://192.168.2.22:3306/eshop_product?useUnicode=true&characterEncoding=utf8";
    private String username = "root";
    private String password = "123456";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
