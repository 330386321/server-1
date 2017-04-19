package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.member.api.service.MerchantFavoredService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
@Api(tags = "favored")
@RestController
@RequestMapping(value = "merchantFavored")
public class MerchantFavoredController extends BaseController {

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    @ApiOperation(value = "会员根据商家ID查询优惠买单信息", notes = "会员根据商家ID查询优惠买单信息。[1004]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findFavoredByMerchantId/{merchantId}", method = RequestMethod.GET)
    public Result<MerchantFavoredDTO> findFavoredByMerchantId(@PathVariable("merchantId") Long merchantId) {
        if (merchantId == null || merchantId < 0) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<MerchantFavoredDTO> result = merchantFavoredService.findFavoredByMerchantId(merchantId);
        return result;
    }
}