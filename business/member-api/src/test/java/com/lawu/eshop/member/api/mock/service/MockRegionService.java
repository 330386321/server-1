package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.member.api.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
public class MockRegionService implements RegionService {

    @Override
    public Result<List<RegionDTO>> getRegionList() {
        return null;
    }

    @Override
    public Result<String> getAreaName(@RequestParam("regionPath") String regionPath) {
        return null;
    }
}
