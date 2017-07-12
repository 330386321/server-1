package com.lawu.eshop.mall.srv.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.srv.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Leach
 * @date 2017/7/10
 */
@Service
public class MockTransactionService extends BaseController implements TransactionService {

    @Override
    public Result<Long> getCount(@PathVariable("type") String type) {
        return successGet(1);
    }

    @Override
    public Result addCount(@PathVariable("type") String type) {
        return successGet();
    }
}
