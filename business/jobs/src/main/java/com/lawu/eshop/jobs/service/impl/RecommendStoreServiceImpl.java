package com.lawu.eshop.jobs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.JobsConfig;
import com.lawu.eshop.jobs.service.*;
import com.lawu.eshop.mall.constants.MerchantFavoredTypeEnum;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.user.dto.NewMerchantStoreDTO;
import com.lawu.eshop.user.dto.RecommendFoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/27.
 */
@Service
public class RecommendStoreServiceImpl implements RecommendStoreService {

    @Autowired
    private RegionService regionService;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    @Autowired
    private DiscountPackageService discountPackageService;

    @Autowired
    private RecommendStoreCacheService recommendStoreCacheService;

    @Autowired
    private JobsConfig jobsConfig;

    @Override
    public void executeRecommendStoreStatistics() {
        Result<List<RegionDTO>> regionResult = regionService.getRegionLevelTwo();
        if (regionResult.getModel().isEmpty()) {
            return;
        }

        Result<List<NewMerchantStoreDTO>> storeResult;
        Result<List<RecommendFoodDTO>> consumeResult;
        Result<List<RecommendFoodDTO>> commentResult;
        for (RegionDTO regionDTO : regionResult.getModel()) {
            storeResult = merchantStoreService.listNewMerchant(regionDTO.getPath());
            consumeResult = merchantStoreService.listRecommendFoodConsume(jobsConfig.getIndustryTypeId(), regionDTO.getPath());
            commentResult = merchantStoreService.listRecommendFoodComment(jobsConfig.getIndustryTypeId(), regionDTO.getPath());

            //新增门店
            if (!storeResult.getModel().isEmpty()) {
                String jsonStr = JSONArray.toJSONString(storeResult.getModel());
                recommendStoreCacheService.saveNewMerchant(regionDTO.getPath(), jsonStr);
            }

            //优选美食-人气最高
            if (!consumeResult.getModel().isEmpty()) {
                for (RecommendFoodDTO foodDTO : consumeResult.getModel()) {
                    getDiscountInfo(foodDTO);
                }
                String jsonStr = JSONArray.toJSONString(consumeResult.getModel());
                recommendStoreCacheService.saveRecommendFoodConsume(regionDTO.getPath(), jsonStr);
            }

            //优选美食-评价最高
            if (!commentResult.getModel().isEmpty()) {
                for (RecommendFoodDTO foodDTO : commentResult.getModel()) {
                    getDiscountInfo(foodDTO);
                }
                String jsonStr = JSONArray.toJSONString(consumeResult.getModel());
                recommendStoreCacheService.saveRecommendFoodComment(regionDTO.getPath(), jsonStr);
            }
        }
    }

    /**
     * 查询商家优惠信息
     *
     * @param foodDTO
     */
    private void getDiscountInfo(RecommendFoodDTO foodDTO) {
        //查询商家优惠信息
        Result<MerchantFavoredDTO> favoredDTOResult = merchantFavoredService.findFavoredByMerchantId(foodDTO.getMerchantId());
        String favoreInfo = "";
        if (favoredDTOResult.getRet() == ResultCode.SUCCESS) {
            if (favoredDTOResult.getModel().getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL.val) {
                favoreInfo = "买单每满" + favoredDTOResult.getModel().getReachAmount() + "减" + favoredDTOResult.getModel().getFavoredAmount() + "元";
            } else if (favoredDTOResult.getModel().getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val) {
                favoreInfo = "买单满" + favoredDTOResult.getModel().getReachAmount() + "减" + favoredDTOResult.getModel().getFavoredAmount() + "元";
            } else if (favoredDTOResult.getModel().getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val) {
                favoreInfo = "买单" + favoredDTOResult.getModel().getDiscountRate() + "折";
            }
        }
        foodDTO.setFavoreInfo(favoreInfo);

        //查询商家优惠套餐
        Result<Page<DiscountPackageQueryDTO>> discountResult = discountPackageService.listForMember(foodDTO.getMerchantId());
        String discountPackage = "";
        if (discountResult.getRet() == ResultCode.SUCCESS) {
            discountPackage = discountResult.getModel().getRecords().get(0).getName();
        }
        foodDTO.setFavoreInfo(discountPackage);
    }

}
