package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.srv.bo.TradeBO;
import com.lawu.eshop.mall.srv.converter.TradeConverter;
import com.lawu.eshop.mall.srv.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@RestController
@RequestMapping(value = "trade/")
public class TradeController extends BaseController {

    @Autowired
    private TradeService tradeService;

    /**
     * 查询行业
     * @return
     */
    @RequestMapping(value = "listTrade", method = RequestMethod.GET)
    public Result listTrade() {
        List<TradeBO> tradeBOS = tradeService.listTrade();
        if (tradeBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(TradeConverter.convertDTO(tradeBOS));
    }

}
