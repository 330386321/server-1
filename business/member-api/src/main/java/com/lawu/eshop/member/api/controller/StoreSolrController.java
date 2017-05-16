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
import com.lawu.eshop.member.api.service.StoreSolrService;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.StoreSearchWordDTO;
import com.lawu.eshop.user.dto.StoreSolrDTO;
import com.lawu.eshop.user.param.StoreSolrParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "搜索门店/猜你喜欢/更多商家", notes = "搜索门店(名称搜索)/猜你喜欢(全部商家)/更多商家(同行业商家)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listStore", method = RequestMethod.GET)
    public Result<Page<StoreSolrDTO>> listStore(@ModelAttribute @ApiParam StoreSolrParam storeSolrParam) {
        return storeSolrService.listStore(storeSolrParam);
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

        List<StoreSolrDTO> storeSolrDTOS = new ArrayList<>();
        for (AdPlatformDTO adPlatformDTO : recommendResult.getModel()) {
            StoreSolrDTO storeSolrDTO = new StoreSolrDTO();
            storeSolrDTO.setMerchantStoreId(adPlatformDTO.getMerchantStoreId());
            storeSolrDTO.setName(adPlatformDTO.getTitle());
            storeSolrDTO.setStorePic(adPlatformDTO.getMediaUrl());
            Result<MerchantStoreDTO> merchantStoreDTOResult = merchantStoreService.getMerchantStoreById(adPlatformDTO.getMerchantStoreId());
            if (isSuccess(merchantStoreDTOResult)) {
                storeSolrDTO.setMerchantId(merchantStoreDTOResult.getModel().getMerchantId());
                storeSolrDTO.setIndustryPath(merchantStoreDTOResult.getModel().getIndustryPath());
                storeSolrDTO.setIndustryName(merchantStoreDTOResult.getModel().getIndustryName());
            }
            storeSolrDTOS.add(storeSolrDTO);
        }
        return successGet(storeSolrDTOS);
    }

}
