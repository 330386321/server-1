package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.AdPlatformService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/8/3.
 */
@Service
public class MockAdPlatformService implements AdPlatformService {
    @Override
    public Result<Boolean> selectByProductIdAndStatus(@RequestParam("productId") Long productId) {
        return null;
    }
}
