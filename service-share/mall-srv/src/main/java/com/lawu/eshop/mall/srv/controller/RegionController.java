package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.mall.srv.bo.RegionBO;
import com.lawu.eshop.mall.srv.converter.RegionConverter;
import com.lawu.eshop.mall.srv.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@RestController
@RequestMapping(value = "region/")
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "getRegionList", method = RequestMethod.GET)
    public Result<List<RegionDTO>> getRegionList() {

        List<RegionBO> regionBOS = regionService.getRegionList();
        if (regionBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<RegionDTO> list = new ArrayList<>();
        for (RegionBO regionBO : regionBOS) {
            RegionDTO regionDTO = RegionConverter.coverDTO(regionBO);
            list.add(regionDTO);
        }
        return successGet(list);
    }

    /**
     * 根据区域ID查询区域完整名称
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getRegionFullName/{id}", method = RequestMethod.GET)
    public Result<String> getRegionFullName(@PathVariable Integer id) {
        return successGet(regionService.getRegionFullName(id));
    }

    /**
     * 根据区域路径查询区域名称
     *
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "getAreaName", method = RequestMethod.GET)
    public Result<String> getAreaName(@RequestParam String regionPath) {
        String areaPath = regionService.getAreaName(regionPath);
        return successGet(areaPath);
    }

    /**
     * 查询城市列表
     *
     * @return
     */
    @RequestMapping(value = "getRegionLevelTwo", method = RequestMethod.GET)
    public Result<List<RegionDTO>> getRegionLevelTwo() {
        List<RegionDTO> regionDTOS = new ArrayList<>();
        List<RegionBO> regionBOS = regionService.getRegionLevelTwo();
        if (regionBOS.isEmpty()) {
            return successGet(regionDTOS);
        }
        for (RegionBO regionBO : regionBOS) {
            RegionDTO regionDTO = RegionConverter.coverDTO(regionBO);
            regionDTOS.add(regionDTO);
        }
        return successGet(regionDTOS);
    }

    /**
     * 根据区域ID查询区域信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getRegion/{id}", method = RequestMethod.GET)
    public Result<RegionDTO> getRegion(@PathVariable Integer id) {
        RegionBO regionBO = regionService.getRegionById(id);
        return successGet(RegionConverter.coverDTO(regionBO));
    }

}
