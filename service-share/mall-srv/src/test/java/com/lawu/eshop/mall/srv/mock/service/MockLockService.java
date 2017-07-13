package com.lawu.eshop.mall.srv.mock.service;

import com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule;
import com.lawu.eshop.synchronization.lock.service.LockService;
import org.springframework.stereotype.Service;

/**
 * 
 * @author zhangyong
 * @date 2017年7月13日
 */
@Service
public class MockLockService implements LockService {

	@Override
	public boolean tryLock(String lockKey) {
		return false;
	}

	@Override
	public boolean tryLock(LockModule lockModule, String lockKey, Long relatedId) {
		return false;
	}

	@Override
	public boolean tryLock(LockModule lockModule, String lockKey) {
		return false;
	}

	@Override
	public void unLock(String lockKey) {
		
	}

	@Override
	public void unLock(LockModule lockModule, String lockKey, Long relatedId) {
		
	}

	@Override
	public void unLock(LockModule lockModule, String lockKey) {
		
	}
	
}
