package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.member.api.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Service
public class MockRegionService extends BaseController implements RegionService {

    @Override
    public Result<List<RegionDTO>> getRegionList() {
        RegionDTO dto = new RegionDTO();
        List<RegionDTO> list = new ArrayList<>();
        list.add(dto);
        return successCreated(list);
    }

    @Override
    public Result<String> getAreaName(@RequestParam("regionPath") String regionPath) {
        return null;
    }
}
