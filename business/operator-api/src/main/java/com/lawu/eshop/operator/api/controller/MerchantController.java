package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.user.dto.MerchantDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/5/16.
 */
@Api(tags = "merchant")
@RestController
@RequestMapping(value = "merchant/")
public class MerchantController extends BaseController{

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "根据商家账号查询商家信息", notes = "根据商家账号查询商家信息。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@RequiresPermissions("index:store")
    @RequestMapping(value = "getMerchant/{account}", method = RequestMethod.GET)
    public Result<MerchantDTO> getMerchant(@PathVariable @ApiParam(value = "商家账号") String account) {
        return merchantService.getMerchantByAccount(account);
    }
}
