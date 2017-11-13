package com.lawu.eshop.jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangyong
 * @date 2017/5/22.
 */
@Component
public class JobsConfig {

    @Value(value = "${rongyun_message_download_url}")
    private String downLoadDir;

    @Value(value = "${industry_type_id}")
    private Integer industryTypeId;

    @Value(value = "${page.size}")
    private Integer pageSize;

    public String getDownLoadDir() {
        return downLoadDir;
    }

    public Integer getIndustryTypeId() {
        return industryTypeId;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
