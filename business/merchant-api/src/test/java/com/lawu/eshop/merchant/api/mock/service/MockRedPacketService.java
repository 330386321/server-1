package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.ad.param.RedPacketParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.RedPacketService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockRedPacketService extends BaseController implements RedPacketService {
    @Override
    public Result save(@RequestBody RedPacketParam param, @RequestParam("merchantId") Long merchantId, @RequestParam("num") String num) {
        return successCreated();
    }
}
