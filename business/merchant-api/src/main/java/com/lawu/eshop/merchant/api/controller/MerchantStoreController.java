package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家扩展信息
 * Created by Administrator on 2017/3/23.
 */
@Api(tags = "merchantStore")
@RestController
@RequestMapping(value = "/")
public class MerchantStoreController extends BaseController {

    @Autowired
    private MerchantStoreService merchantStoreService;


    @ApiOperation(value = "根据商家id查询门店信息", notes = "根据商家id查询门店信息，成功返回门店信息。（章勇）", httpMethod = "GET")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMerchantStoreInfo/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") @ApiParam(required = true, value = "商家主键") Long id) {
        Result<MerchantStoreDTO> result = merchantStoreService.selectMerchantStore(id);
        return result;
    }

    @ApiOperation(value = "新增门店信息", notes = "错误信息 [1012]（章勇）", httpMethod = "POST")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMerchantStoreInfo/{merchantId}", method = RequestMethod.POST)
    public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute @ApiParam MerchantStoreParam merchantStoreParam, @RequestParam("storeType") MerchantStoreTypeEnum storeType,
                                        @RequestParam("certifType") CertifTypeEnum certifType) {
        //TODO 上传图片
        return merchantStoreService.saveMerchantStoreInfo(merchantId, merchantStoreParam, storeType, certifType);
    }

    @ApiOperation(value = "修改门店信息", notes = "错误信息 [1012]（章勇）", httpMethod = "PUT")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMerchantStoreInfo/{merchantId}", method = RequestMethod.PUT)
    public Result updateMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute @ApiParam MerchantStoreParam merchantStoreParam, @RequestParam("storeType") MerchantStoreTypeEnum storeType,
                                          @RequestParam("certifType") CertifTypeEnum certifType) {
        //TODO 上传图片
        return merchantStoreService.updateMerchantStoreInfo(merchantId, merchantStoreParam, storeType, certifType);
    }

}
