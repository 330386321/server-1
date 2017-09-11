package com.lawu.eshop.merchant.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.UserVisitService;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockUserVisitService extends BaseController implements UserVisitService {
    @Override
    public Result addUserVisitCount(@RequestParam("userNum") String userNum, @RequestParam("nowTimeStr") String nowTimeStr, @RequestParam("userId") Long userId, @RequestParam("type") UserType type) {
        return successCreated();
    }

    @Override
    public Result addUserVisitTime(@RequestParam("userId") Long userId, @RequestParam("type") UserType type) {
        return null;
    }

    @Override
    public Result addUserVisitFrequency(@RequestParam("userId") Long userId, @RequestParam("type") UserType type, @RequestParam("expireTime") Integer expireTime) {
        return null;
    }

    @Override
    public Result<Long> getUserVisitTime(@RequestParam("userId") Long userId, @RequestParam("type") UserType type) {
        return null;
    }

    @Override
    public Result<Integer> getUserVisitFrequency(@RequestParam("userId") Long userId, @RequestParam("type") UserType type) {
        return null;
    }

    @Override
    public Result delUserVisitFrequency(@RequestParam("userId") Long userId, @RequestParam("type") UserType type) {
        return null;
    }
}
