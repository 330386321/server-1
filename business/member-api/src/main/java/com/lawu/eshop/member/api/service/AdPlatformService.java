package com.lawu.eshop.member.api.service;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 平台广告管理
 *
 * @author zhangrc
 * @date 2017/4/5
 */
@FeignClient(value = "ad-srv")
public interface AdPlatformService {

    /**
     * 根据不同的位置查询不同的广告
     *
     * @param positionEnum
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "adPlatform/selectByPosition")
    Result<List<AdPlatformDTO>> selectByPosition(@RequestBody PositionEnum positionEnum);

    /**
     * 根据类型位置查询广告
     *
     * @param typeEnum
     * @param positionEnum
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "adPlatform/getAdPlatformByTypePosition")
    Result<List<AdPlatformDTO>> getAdPlatformByTypePosition(@RequestParam("typeEnum") TypeEnum typeEnum, @RequestParam("positionEnum") PositionEnum positionEnum);

}
