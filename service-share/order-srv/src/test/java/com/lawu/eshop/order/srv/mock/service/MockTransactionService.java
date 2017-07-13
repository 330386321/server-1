package com.lawu.eshop.order.srv.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.srv.service.TransactionService;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月12日
 */
@Service
public class MockTransactionService extends BaseController implements TransactionService {

    @Override
    public Result<Long> getCount(@PathVariable("type") String type) {
        return successGet(1L);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Result addCount(@PathVariable("type") String type) {
        return successGet();
    }
}
