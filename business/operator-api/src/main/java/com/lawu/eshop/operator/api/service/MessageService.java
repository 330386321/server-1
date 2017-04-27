package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.OperatorMessageDTO;
import com.lawu.eshop.mall.param.MessageQueryParam;
import com.lawu.eshop.mall.param.OperatorMessageInfoParam;
import com.lawu.eshop.mall.param.OperatorMessageParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "message/getOperatorMessageList",method = RequestMethod.POST)
    Result<Page<OperatorMessageDTO>> getOperatorMessageList(@ModelAttribute MessageQueryParam param);
}
