package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.AlipayService;
import com.lawu.eshop.property.param.PcAlipayDataParam;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockAlipayService extends BaseController implements AlipayService {
    @Override
    public Result getAppAlipayReqParams(@RequestBody ThirdPayDataParam param) {
        return successCreated();
    }

    @Override
    public Result initPcPay(PcAlipayDataParam param) {
        return successCreated("test");
    }
}
