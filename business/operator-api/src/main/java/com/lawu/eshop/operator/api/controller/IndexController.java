package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.ListAdParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.user.constants.ManageTypeEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.ListMerchantStoreParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/5/10.
 */
@Api(tags = "index")
@RestController
@RequestMapping(value = "index/")
public class IndexController extends BaseController {

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AdService adService;

    @ApiOperation(value = "更新门店索引", notes = "更新门店索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateStoreIndex", method = RequestMethod.GET)
    public Result updateStoreIndex() {
        ListMerchantStoreParam listMerchantStoreParam = new ListMerchantStoreParam();
        listMerchantStoreParam.setStatus(MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val);
        listMerchantStoreParam.setManageType(ManageTypeEnum.ENTITY.val);
        listMerchantStoreParam.setPageSize(50);
        int currentPage = 0;
        while (true) {
            currentPage++;
            listMerchantStoreParam.setCurrentPage(currentPage);
            Result<List<MerchantStoreDTO>> result = merchantStoreService.listMerchantStore(listMerchantStoreParam);
            if (result == null || !isSuccess(result)) {
                return successCreated();
            }

            for (MerchantStoreDTO merchantStoreDTO : result.getModel()) {
                merchantStoreService.updateStoreIndex(merchantStoreDTO.getMerchantStoreId());
            }
        }
    }

    @ApiOperation(value = "更新商品索引", notes = "更新商品索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateProductIndex", method = RequestMethod.GET)
    public Result updateProductIndex() {
        ListProductParam listProductParam = new ListProductParam();
        listProductParam.setPageSize(50);
        int currentPage = 0;
        while (true) {
            currentPage++;
            listProductParam.setCurrentPage(currentPage);
            Result<List<ProductInfoDTO>> result = productService.listProduct(listProductParam);
            if (result == null || !isSuccess(result)) {
                return successCreated();
            }

            for (ProductInfoDTO productInfoDTO : result.getModel()) {
                productService.updateProductIndex(productInfoDTO.getId());
            }
        }
    }

    @ApiOperation(value = "更新广告索引", notes = "更新广告索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateAdIndex", method = RequestMethod.GET)
    public Result updateAdIndex() {
        ListAdParam listAdParam = new ListAdParam();
        listAdParam.setPageSize(50);
        int currentPage = 0;
        while (true) {
            currentPage++;
            listAdParam.setCurrentPage(currentPage);
            Result<List<AdDTO>> result = adService.listFlatVideoAd(listAdParam);
            if (result == null || !isSuccess(result)) {
                return successCreated();
            }

            for (AdDTO adDTO : result.getModel()) {
                adService.updateAdIndex(adDTO.getId());
            }
        }
    }
}
