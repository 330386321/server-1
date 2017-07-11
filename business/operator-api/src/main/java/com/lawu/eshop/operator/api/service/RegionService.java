package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.RegionDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@FeignClient(value = "mall-srv")
public interface RegionService {

    /**
     * 根据区域ID查询区域完整名称
     *
     * @return
     */
    @RequestMapping(value = "region/getRegionFullName/{id}", method = RequestMethod.GET)
    Result<String> getRegionFullName(@PathVariable("id") Integer id);

    /**
     * 根据区域ID查询区域信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "region/getRegion/{id}", method = RequestMethod.GET)
    Result<RegionDTO> getRegionById(@PathVariable("id") Integer id);

}
