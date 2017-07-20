package com.lawu.eshop.user.srv.mock.service;

import com.lawu.eshop.synchronization.lock.constants.LockConstant;
import com.lawu.eshop.synchronization.lock.service.LockService;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2017/7/12.
 */
@Service
public class MockLockService implements LockService {
    @Override
    public boolean tryLock(String lockKey) {
        return false;
    }

    @Override
    public boolean tryLock(LockConstant.LockModule lockModule, String lockKey, Long relatedId) {
        return false;
    }

    @Override
    public boolean tryLock(LockConstant.LockModule lockModule, String lockKey) {
        return false;
    }

    @Override
    public void unLock(String lockKey) {

    }

    @Override
    public void unLock(LockConstant.LockModule lockModule, String lockKey, Long relatedId) {

    }

    @Override
    public void unLock(LockConstant.LockModule lockModule, String lockKey) {

    }
}
