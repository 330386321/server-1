package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.UserVisitService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Service
class MockUserVisitService implements UserVisitService {

    @Override
    public Result addUserVisitCount(@RequestParam("userNum") String userNum, @RequestParam("nowTimeStr") String nowTimeStr, @RequestParam("userId") Long userId, @RequestParam("type") UserType type) {
        return null;
    }
}
