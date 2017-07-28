package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AdPlatformService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.RegionService;
import com.lawu.eshop.member.api.service.StoreSolrService;
import com.lawu.eshop.user.dto.StoreSearchWordDTO;
import com.lawu.eshop.user.dto.StoreSolrDTO;
import com.lawu.eshop.user.dto.StoreSolrInfoDTO;
import com.lawu.eshop.user.param.DiscountStoreParam;
import com.lawu.eshop.user.param.StoreSolrParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@Api(tags = "storeSolr")
@RestController
@RequestMapping(value = "storeSolr/")
public class StoreSolrController extends BaseController {

    @Autowired
    private StoreSolrService storeSolrService;

    @Autowired
    private AdPlatformService adPlatformService;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private RegionService regionService;

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "搜索门店/猜你喜欢/更多商家", notes = "搜索门店(名称搜索)/猜你喜欢(全部商家)/更多商家(同行业商家)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listStore", method = RequestMethod.GET)
    public Result<Page<StoreSolrDTO>> listStore(@ModelAttribute @ApiParam StoreSolrParam storeSolrParam) {
        Result<Page<StoreSolrDTO>> result = storeSolrService.listStore(storeSolrParam);
        if (!isSuccess(result)) {
            return result;
        }
        for (StoreSolrDTO storeSolrDTO : result.getModel().getRecords()) {
            if (StringUtils.isNotEmpty(storeSolrDTO.getRegionPath())) {
                String areaName = regionService.getAreaName(storeSolrDTO.getRegionPath()).getModel();
                storeSolrDTO.setAreaName(areaName);
            }
        }
        return result;
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "搜索词关联词频查询", notes = "根据搜索词推荐关联词和频率查询。 (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listStoreSearchWord", method = RequestMethod.GET)
    public Result<List<StoreSearchWordDTO>> listStoreSearchWord(@RequestParam @ApiParam(name = "name", required = true, value = "门店名称") String name) {
        return storeSolrService.listStoreSearchWord(name);
    }

    @Audit(date = "2017-04-26", reviewer = "孙林青")
    @ApiOperation(value = "要买单人气推荐", notes = "要买单人气推荐。[1100]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listRecommendStore", method = RequestMethod.GET)
    public Result<List<StoreSolrDTO>> listRecommendStore(@RequestParam @ApiParam(name = "regionPath", required = true, value = "区域路径") String regionPath) {
        Result<List<AdPlatformDTO>> recommendResult = adPlatformService.getAdPlatformByTypePositionRegionPath(TypeEnum.TYPE_STORE, PositionEnum.POSITON_RECOMMEND, regionPath);
        if (!isSuccess(recommendResult)) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        // 把要查询的id放入list,统一一次性查询
        Set<Long> merchantStoreIds = new HashSet<Long>();
        List<AdPlatformDTO> adPlatformDTOS = recommendResult.getModel();
        for (AdPlatformDTO adPlatformDTO : adPlatformDTOS) {
            merchantStoreIds.add(adPlatformDTO.getMerchantStoreId());
        }

        Result<List<StoreSolrInfoDTO>> listResult = merchantStoreService.getMerchantStoreByIds(new ArrayList<Long>(merchantStoreIds));
        Map<Long,StoreSolrInfoDTO> mapResult = new HashMap<Long,StoreSolrInfoDTO>();
        if(!listResult.getModel().isEmpty()){
            for (StoreSolrInfoDTO storeInfoDTO : listResult.getModel()) {
                if (!mapResult.containsKey(storeInfoDTO.getMerchantStoreId())) {
                    mapResult.put(storeInfoDTO.getMerchantStoreId(), storeInfoDTO);
                }
            }
        }
        List<StoreSolrDTO> storeSolrDTOS = new ArrayList<>();
        StoreSolrInfoDTO storeInfo;
        for (AdPlatformDTO adPlatformDTO : recommendResult.getModel()) {
            StoreSolrDTO storeSolrDTO = new StoreSolrDTO();
            storeSolrDTO.setMerchantStoreId(adPlatformDTO.getMerchantStoreId());
            storeSolrDTO.setName(adPlatformDTO.getTitle());
            storeSolrDTO.setStorePic(adPlatformDTO.getMediaUrl());
            storeInfo = mapResult.get(adPlatformDTO.getMerchantStoreId());
            if(storeInfo == null){
                storeSolrDTO.setMerchantId(0L);
                storeSolrDTO.setIndustryPath("");
                storeSolrDTO.setIndustryName("");
            }else{
                storeSolrDTO.setMerchantId(storeInfo.getMerchantId());
                storeSolrDTO.setIndustryPath(storeInfo.getIndustryPath());
                storeSolrDTO.setIndustryName(storeInfo.getIndustryName());
            }
            storeSolrDTOS.add(storeSolrDTO);
        }
        return successGet(storeSolrDTOS);
    }

    @ApiOperation(value = "专属特惠", notes = "专属特惠(优惠系数升序)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "discountStore", method = RequestMethod.GET)
    public Result<Page<StoreSolrDTO>> listStore(@ModelAttribute @ApiParam DiscountStoreParam discountStoreParam) {
        Result<Page<StoreSolrDTO>> result = storeSolrService.discountStore(discountStoreParam);
        if (!isSuccess(result)) {
            return result;
        }
        for (StoreSolrDTO storeSolrDTO : result.getModel().getRecords()) {
            if (StringUtils.isNotEmpty(storeSolrDTO.getRegionPath())) {
                String areaName = regionService.getAreaName(storeSolrDTO.getRegionPath()).getModel();
                storeSolrDTO.setAreaName(areaName);
            }
        }
        return result;
    }

}
