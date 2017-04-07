package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import com.lawu.eshop.framework.web.Result;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@FeignClient(value = "mall-srv")
public interface IndustryTypeService {

    @RequestMapping(method = RequestMethod.GET, value = "industryType/listIndustryType")
    Result<List<IndustryTypeDTO>> listIndustryType();
}
