package com.lawu.eshop.order.srv.mock.service;

import org.springframework.stereotype.Service;

import com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule;
import com.lawu.eshop.synchronization.lock.service.LockService;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月12日
 */
@Service
public class MockLockService implements LockService {

	@Override
	public boolean tryLock(String lockKey) {
		return true;
	}

	@Override
	public boolean tryLock(LockModule lockModule, String lockKey, Long relatedId) {
		return true;
	}

	@Override
	public boolean tryLock(LockModule lockModule, String lockKey) {
		return true;
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
