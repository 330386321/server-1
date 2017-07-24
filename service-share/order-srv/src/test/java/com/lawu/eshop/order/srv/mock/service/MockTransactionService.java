package com.lawu.eshop.order.srv.mock.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	
	private Map<String, Long> redis = new ConcurrentHashMap<>();
	
	@Override
    public Result<Long> getCount(@PathVariable("type") String type) {
    	Long count = redis.get(type);
    	Result<Long> rtn = new Result<>();
    	rtn.setModel(count == null ? 0L : count);
        return rtn;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Result addCount(@PathVariable("type") String type) {
    	Long count = redis.get(type);
		redis.put(type, count == null ? 1L : count + 1);
        return new Result();
    }
}
