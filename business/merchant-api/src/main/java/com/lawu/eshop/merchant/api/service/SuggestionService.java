package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.SuggestionParam;

/**
 * @author Sunny
 * @date 2017/3/24
 */
@FeignClient(value= "mall-srv")
public interface SuggestionService {

    /**
     * 保存意见反馈
     * 
     * @param parm
     */
    @RequestMapping(method = RequestMethod.POST, value = "suggestion/")
    Result save(@RequestBody SuggestionParam parm);
    
}
