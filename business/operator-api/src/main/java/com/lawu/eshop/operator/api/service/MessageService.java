package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.OperatorMessageInfoParam;
import com.lawu.eshop.mall.param.OperatorMessageParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@FeignClient(value = "mall-srv")
public interface MessageService {

    @RequestMapping(value = "message/saveMessageOperator/{userNum}", method = RequestMethod.POST)
    public Result saveMessageOperator(@PathVariable("userNum") String userNum, @ModelAttribute OperatorMessageInfoParam messageInfoParam);

    @RequestMapping(value = "message/saveMessageToAll", method = RequestMethod.POST)
    Result saveMessageToAll(@ModelAttribute OperatorMessageParam param);
}
