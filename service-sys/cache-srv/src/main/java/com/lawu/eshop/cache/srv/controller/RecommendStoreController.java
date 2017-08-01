package com.lawu.eshop.cache.srv.controller;

import com.lawu.eshop.cache.srv.service.RecommendStoreService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/7/28.
 */
@RestController
@RequestMapping(value = "recommendStore/")
public class RecommendStoreController extends BaseController {

    @Autowired
    private RecommendStoreService recommendStoreService;

    /**
     * 新店推荐
     *
     * @param regionPath
     * @param storeInfo
     * @return
     */
    @RequestMapping(value = "saveNewMerchant", method = RequestMethod.POST)
    public Result saveNewMerchant(@RequestParam String regionPath, @RequestParam String storeInfo) {
        recommendStoreService.setNewMerchant(regionPath, storeInfo);
        return successCreated();
    }

    /**
     * 优选美食-人气最高
     *
     * @param regionPath
     * @param storeInfo
     * @return
     */
    @RequestMapping(value = "saveRecommendFoodConsume", method = RequestMethod.POST)
    public Result saveRecommendFoodConsume(@RequestParam String regionPath, @RequestParam String storeInfo) {
        recommendStoreService.setRecommendFoodConsume(regionPath, storeInfo);
        return successCreated();
    }

    /**
     * 优选美食-评价最高
     *
     * @param regionPath
     * @param storeInfo
     * @return
     */
    @RequestMapping(value = "saveRecommendFoodComment", method = RequestMethod.POST)
    public Result saveRecommendFoodComment(@RequestParam String regionPath, @RequestParam String storeInfo) {
        recommendStoreService.setRecommendFoodComment(regionPath, storeInfo);
        return successCreated();
    }

    /**
     * 新店推荐
     *
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "getNewMerchant", method = RequestMethod.GET)
    public Result<String> getNewMerchant(@RequestParam String regionPath) {
        String storeInfo = recommendStoreService.getNewMerchant(regionPath);
        return successGet(storeInfo);
    }

    /**
     * 优选美食-人气最高
     *
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "getRecommendFoodConsume", method = RequestMethod.GET)
    public Result<String> getRecommendFoodConsume(@RequestParam String regionPath) {
        String storeInfo = recommendStoreService.getRecommendFoodConsume(regionPath);
        return successGet(storeInfo);
    }

    /**
     * 优选美食-评价最高
     *
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "getRecommendFoodComment", method = RequestMethod.GET)
    public Result<String> getRecommendFoodComment(@RequestParam String regionPath) {
        String storeInfo = recommendStoreService.getRecommendFoodComment(regionPath);
        return successGet(storeInfo);
    }

}
