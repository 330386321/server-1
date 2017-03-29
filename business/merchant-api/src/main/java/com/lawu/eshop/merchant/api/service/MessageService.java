package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.MessageStatisticsDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 站内信息接口
 * Created by zhangyong on 2017/3/29.
 */
@FeignClient(value = "mall-srv")
public interface MessageService {

    @RequestMapping(method = RequestMethod.GET, value = "message/getMessageStatistics/{userNum}")
    Result<MessageStatisticsDTO> getMessageStatistics(@PathVariable("userNum") String userNum);
}
