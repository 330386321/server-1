package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.RegionDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
@FeignClient(value = "mall-srv")
public interface RegionService {

    /**
     * 查询城市列表
     *
     * @return
     */
    @RequestMapping(value = "region/getRegionLevelTwo", method = RequestMethod.GET)
    Result<List<RegionDTO>> getRegionLevelTwo();

    @RequestMapping(value = "region/getRegion/{id}", method = RequestMethod.GET)
    Result<RegionDTO> getRegion(@PathVariable Integer id);

}
