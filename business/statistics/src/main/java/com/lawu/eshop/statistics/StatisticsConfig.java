package com.lawu.eshop.statistics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangyong
 * @date 2017/5/22.
 */
@Component
public class StatisticsConfig {

    @Value(value = "${rongyun_message_download_url}")
    private String downLoadDir;

    public String getDownLoadDir() {
        return downLoadDir;
    }
}
