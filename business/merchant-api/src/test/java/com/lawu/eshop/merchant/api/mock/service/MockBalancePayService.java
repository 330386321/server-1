package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.BalancePayService;
import com.lawu.eshop.property.param.BalancePayParam;
import com.lawu.eshop.property.param.BalancePayValidateDataParam;
import org.springframework.stereotype.Service;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockBalancePayService extends BaseController implements BalancePayService {
    @Override
    public Result balancePayPoint(BalancePayParam param) {
        return successCreated();
    }

    @Override
    public Result balancePayPointValidatePwd(BalancePayValidateDataParam param) {
        return null;
    }
}
