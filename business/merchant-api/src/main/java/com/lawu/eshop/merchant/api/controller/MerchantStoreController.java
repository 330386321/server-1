package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.MerchantInfoService;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.MerchantProfileParam;
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




    @ApiOperation(value ="根据商家id查询门店信息", notes = "根据商家id查询门店信息，成功返回门店信息。（章勇）",httpMethod = "GET")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value ="findMerchantStoreInfo/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") @ApiParam(required = true, value = "商家主键") Long id){
        Result<MerchantStoreDTO> result = merchantStoreService.selectMerchantStore(id);
        return result;
    }


}
