package com.lawu.eshop.merchant.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;

/**
 * @author Sunny
 * @date 2017/3/27
 */
@FeignClient(value= "mall-srv")
public interface ExpressCompanyService {

    /**
     * 查询全部快递公司，根据ordinal排序
     */
    @RequestMapping(method = RequestMethod.GET, value = "expressCompany/list")
    Result<List<ExpressCompanyDTO>> list();
    
}
