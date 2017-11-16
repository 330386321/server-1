package com.lawu.eshop.jobs.mock.service;

import com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule;
import com.lawu.eshop.synchronization.lock.service.LockService;
import org.springframework.stereotype.Service;

@Service
public class MockLockServiceImpl implements LockService {

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
