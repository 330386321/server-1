package com.lawu.eshop.ad.srv.controller;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.dto.AdPlatformOperatorDTO;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.ad.srv.converter.AdPlatformConverter;
import com.lawu.eshop.ad.srv.service.AdPlatformService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 运营平台接口提供
 *
 * @author zhangrc
 * @date 2017/4/5
 */
@RestController
@RequestMapping(value = "adPlatform/")
public class AdPlatformController extends BaseController {

    @Resource
    private AdPlatformService adPlatformService;

    /**
     * 添加广告
     *
     * @param adPlatformParam
     * @return
     */
    @RequestMapping(value = "saveAdPlatform", method = RequestMethod.POST)
    public Result saveAdPlatform(@RequestBody AdPlatformParam adPlatformParam, @RequestParam String url) {
        Integer id = adPlatformService.saveAdPlatform(adPlatformParam, url);
        if (id > 0) {
            return successCreated(ResultCode.SUCCESS);
        } else {
            return successCreated(ResultCode.FAIL);
        }
    }

    /**
     * 根据位置查询广告
     *
     * @param positionEnum
     * @return
     */
    @RequestMapping(value = "selectByPosition", method = RequestMethod.POST)
    public Result<List<AdPlatformDTO>> selectByPosition(@RequestBody PositionEnum positionEnum) {
        List<AdPlatformBO> BOS = adPlatformService.selectByPosition(positionEnum);
        List<AdPlatformDTO> list;
        list = AdPlatformConverter.convertDTOS(BOS);
        if (list == null) {
            list = new ArrayList<AdPlatformDTO>();
        }
        return successAccepted(list);
    }

    /**
     * 根据位置查询广告
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    public Result<Page<AdPlatformOperatorDTO>> selectList(@RequestBody AdPlatformFindParam param) {
        Page<AdPlatformBO> page = adPlatformService.selectList(param);
        List<AdPlatformOperatorDTO> list;
        list = AdPlatformConverter.convertOperatorDTOS(page.getRecords());
        if (list == null) {
            list = new ArrayList<AdPlatformOperatorDTO>();
        }
        Page<AdPlatformOperatorDTO> newPage = new Page<>();
        newPage.setCurrentPage(page.getCurrentPage());
        newPage.setTotalCount(page.getTotalCount());
        newPage.setRecords(list);
        return successAccepted(newPage);
    }

    /**
     * 删除广告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "removeAdPlatform/{id}", method = RequestMethod.DELETE)
    public Result removeAdPlatform(@PathVariable Long id) {
        Integer i = adPlatformService.removeAdPlatform(id);
        if (id > 0) {
            return successDelete();
        } else {
            return successCreated(ResultCode.FAIL);
        }
    }

    /**
     * 发布广告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "issueAd/{id}", method = RequestMethod.PUT)
    public Result issueAd(@PathVariable Long id) {
        Integer i = adPlatformService.issueAd(id);
        if (id > 0) {
            return successCreated(ResultCode.SUCCESS);
        } else {
            return successCreated(ResultCode.FAIL);
        }
    }

    /**
     * 广告下架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "unShelve/{id}", method = RequestMethod.PUT)
    public Result unShelve(@PathVariable Long id) {
        adPlatformService.unShelve(id);
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 广告上架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "onShelve/{id}", method = RequestMethod.PUT)
    public Result onShelve(@PathVariable Long id) {
        adPlatformService.onShelve(id);
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 设置广告位
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "setPosition/{id}", method = RequestMethod.POST)
    public Result setPosition(@PathVariable Long id, @RequestBody PositionEnum positionEnum) {
        Integer i = adPlatformService.setPosition(id, positionEnum);
        if (id > 0) {
            return successCreated(ResultCode.SUCCESS);
        } else {
            return successCreated(ResultCode.FAIL);
        }
    }

    /**
     * 修改
     *
     * @param id
     * @param adPlatformParam
     * @param url
     * @return
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public Result update(@PathVariable Long id, @RequestBody AdPlatformParam adPlatformParam, @RequestParam String url) {
        Integer i = adPlatformService.update(id, adPlatformParam, url);
        if (id > 0) {
            return successCreated(ResultCode.SUCCESS);
        } else {
            return successCreated(ResultCode.FAIL);
        }
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "select/{id}", method = RequestMethod.GET)
    public Result<AdPlatformOperatorDTO> select(@PathVariable Long id) {
        AdPlatformBO adPlatformBO = adPlatformService.select(id);
        return successGet(AdPlatformConverter.convertOperatorDTO(adPlatformBO));
    }

    /**
     * 根据类型位置查询广告
     *
     * @param typeEnum
     * @param positionEnum
     * @return
     */
    @RequestMapping(value = "getAdPlatformByTypePosition", method = RequestMethod.GET)
    public Result<List<AdPlatformDTO>> getAdPlatformByTypePosition(@RequestParam TypeEnum typeEnum, @RequestParam PositionEnum positionEnum) {
        List<AdPlatformBO> adPlatformBOS = adPlatformService.getAdPlatformByTypePosition(typeEnum, positionEnum);
        if (adPlatformBOS == null || adPlatformBOS.isEmpty()) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        return successGet(AdPlatformConverter.convertDTOS(adPlatformBOS));
    }

}
