package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.RegionDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@FeignClient(value = "mall-srv")
public interface RegionService {
    /**
     * 城市列表
     * @return
     */
    @RequestMapping(value = "region/getRegionList", method = RequestMethod.GET)
    Result<List<RegionDTO>> getRegionList();

    /**
     * 根据区域路径查询区域名称
     *
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "region/getAreaName", method = RequestMethod.GET)
    Result<String> getAreaName(@RequestParam("regionPath") String regionPath);

}
