package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MessagePushDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/24.
 */
@FeignClient(value = "user-srv")
public interface MerchantService {

    @RequestMapping(value = "merchant/findMessagePushList",method = RequestMethod.GET)
    Result<List<MessagePushDTO>> findMessagePushList(@RequestParam(value = "area") String area);
}
