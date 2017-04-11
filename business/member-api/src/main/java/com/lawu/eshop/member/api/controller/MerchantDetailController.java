package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.MerchantStoreImageService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.MerchantStoreImageDTO;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.StoreDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
@Api(tags = "merchantDetail")
@RestController
@RequestMapping(value = "merchantDetail/")
public class MerchantDetailController extends BaseController {

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantStoreImageService merchantStoreImageService;

    @ApiOperation(value = "会员查看商家门店详情", notes = "会员查看商家门店详情(用户评价、更多商家查询其他接口)。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "store/{id}", method = RequestMethod.GET)
    public Result<StoreDetailDTO> storeDetail(@PathVariable @ApiParam(required = true, value = "门店ID") Long id) {
        Result<StoreDetailDTO> stoResult = merchantStoreService.getStoreDetailById(id);
        if (!isSuccess(stoResult)) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        StoreDetailDTO storeDetailDTO = stoResult.getModel();
        //TODO 人均消费、优惠信息、综合评分，好评率
        return successGet(storeDetailDTO);
    }

    @ApiOperation(value = "会员查看商家相册", notes = "会员查看商家相册(店内环境照)。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listMerchantStoreImage/{merchantId}", method = RequestMethod.GET)
    public Result<List<MerchantStoreImageDTO>> listMerchantStoreImage(@PathVariable @ApiParam(required = true, value = "商家ID") Long merchantId,
                                                                      MerchantStoreImageEnum merchantStoreImageEnum) {
        return merchantStoreImageService.listMerchantStoreImageByType(merchantId, merchantStoreImageEnum);
    }
}
