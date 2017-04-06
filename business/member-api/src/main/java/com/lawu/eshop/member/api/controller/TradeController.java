package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Api(tags = "trade")
@RestController
@RequestMapping(value = "trade/")
public class TradeController extends BaseController {

    @Autowired
    private TradeService tradeService;

    @ApiOperation(value = "查询行业", notes = "查询所有行业。 [1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "listTrade", method = RequestMethod.GET)
    public Result listTrade() {
        return tradeService.listTrade();
    }
}
