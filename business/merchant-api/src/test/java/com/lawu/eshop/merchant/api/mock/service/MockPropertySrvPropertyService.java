package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.PropertySrvPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockPropertySrvPropertyService extends BaseController implements PropertySrvPropertyService {
    @Override
    public Result getValue(@RequestParam("name") String name) {
        return successGet();
    }
}
