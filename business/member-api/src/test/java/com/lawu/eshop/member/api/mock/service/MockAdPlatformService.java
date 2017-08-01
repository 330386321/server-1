package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.dto.AdPlatformProductDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdPlatformService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockAdPlatformService extends BaseController implements AdPlatformService {


    @Override
    public Result<List<AdPlatformDTO>> selectByPosition(@RequestBody PositionEnum positionEnum) {
        AdPlatformDTO dto = new AdPlatformDTO();
        List<AdPlatformDTO> list = new ArrayList<>();
        list.add(dto);
        return successCreated(list);
    }

    @Override
    public Result<List<AdPlatformProductDTO>> getAdPlatformByTypePosition(@RequestParam("typeEnum") TypeEnum typeEnum, @RequestParam("positionEnum") PositionEnum positionEnum) {
        AdPlatformProductDTO dto = new AdPlatformProductDTO();
        dto.setMediaUrl("1.jpg");
        dto.setProductId(1L);
        dto.setTitle("title");
        List<AdPlatformProductDTO> list = new ArrayList<>();
        list.add(dto);
        return successCreated(list);
    }

    @Override
    public Result<List<AdPlatformDTO>> getAdPlatformByTypePositionRegionPath(@RequestParam("typeEnum") TypeEnum typeEnum, @RequestParam("positionEnum") PositionEnum positionEnum, @RequestParam("regionPath") String regionPath) {
        return null;
    }
}
