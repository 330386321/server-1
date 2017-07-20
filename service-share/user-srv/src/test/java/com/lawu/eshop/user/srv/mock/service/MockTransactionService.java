package com.lawu.eshop.user.srv.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.srv.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@Service
public class MockTransactionService extends BaseController implements TransactionService {

    @Override
    public Result<Long> getCount(@PathVariable("type") String type) {
        return successGet();
    }

    @Override
    public Result addCount(@PathVariable("type") String type) {
        return successGet();
    }
}
