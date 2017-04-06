package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.srv.bo.NearStoreBO;
import com.lawu.eshop.user.srv.converter.NearStoreConverter;
import com.lawu.eshop.user.srv.service.NearStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@RestController
@RequestMapping(value = "nearStore/")
public class NearStoreController extends BaseController {

    @Autowired
    private NearStoreService nearStoreService;

    /**
     * 查询附近门店
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    @RequestMapping(value = "listNearStore", method = RequestMethod.GET)
    public Result listNearStore(@RequestParam Double longitude, @RequestParam Double latitude) {
        List<NearStoreBO> nearStoreBOS = nearStoreService.listNearStore(longitude, latitude);
        if (nearStoreBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(NearStoreConverter.convertDTO(nearStoreBOS));
    }
}
