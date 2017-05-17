package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/5/16.
 */
@FeignClient(value = "mall-srv")
public interface IndustryTypeService {
    /**
     * 查询所有行业
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "industryType/listIndustryType")
    Result<List<IndustryTypeDTO>> listIndustryType();

    /**
     * 查询父行业下的所有行业
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "industryType/listIndustryType/{parentId}")
    Result<List<IndustryTypeDTO>> listIndustryTypeByParentId(@PathVariable("parentId") Short parentId);

    /**
     * 查询所有行业(不分组)
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "industryType/getAllIndustryList")
    Result<List<IndustryTypeDTO>> getAllIndustryList();
}
