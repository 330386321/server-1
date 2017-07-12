package com.lawu.eshop.property.srv.mock.service;/**
 * Created by ${Yangqh} on 2017/7/12.
 */

import com.lawu.eshop.synchronization.lock.constants.LockConstant;
import com.lawu.eshop.synchronization.lock.service.LockService;
import org.springframework.stereotype.Service;

/**
 * <p> </p>
 *
 * @author Yangqh
 * @date 2017/7/12 11:13
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
