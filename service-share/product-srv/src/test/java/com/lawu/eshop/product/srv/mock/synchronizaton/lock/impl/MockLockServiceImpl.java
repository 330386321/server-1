/**
 * 
 */
package com.lawu.eshop.product.srv.mock.synchronizaton.lock.impl;

import org.springframework.stereotype.Service;

import com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule;
import com.lawu.eshop.synchronization.lock.service.LockService;

/**
 * @author lihj
 * @date 2017年7月12日
 */
@Service
public class MockLockServiceImpl implements LockService {

	/* (non-Javadoc)
	 * @see com.lawu.eshop.synchronization.lock.service.LockService#tryLock(java.lang.String)
	 */
	@Override
	public boolean tryLock(String lockKey) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lawu.eshop.synchronization.lock.service.LockService#tryLock(com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule, java.lang.String, java.lang.Long)
	 */
	@Override
	public boolean tryLock(LockModule lockModule, String lockKey, Long relatedId) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lawu.eshop.synchronization.lock.service.LockService#tryLock(com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule, java.lang.String)
	 */
	@Override
	public boolean tryLock(LockModule lockModule, String lockKey) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lawu.eshop.synchronization.lock.service.LockService#unLock(java.lang.String)
	 */
	@Override
	public void unLock(String lockKey) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.lawu.eshop.synchronization.lock.service.LockService#unLock(com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule, java.lang.String, java.lang.Long)
	 */
	@Override
	public void unLock(LockModule lockModule, String lockKey, Long relatedId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.lawu.eshop.synchronization.lock.service.LockService#unLock(com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule, java.lang.String)
	 */
	@Override
	public void unLock(LockModule lockModule, String lockKey) {
		// TODO Auto-generated method stub
		
	}

}
