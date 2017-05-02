package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.mall.srv.bo.RegionBO;
import com.lawu.eshop.mall.srv.converter.RegionConverter;
import com.lawu.eshop.mall.srv.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
