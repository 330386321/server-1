package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.UserGradeService;

@Service
public class MockUserGradeService extends BaseController implements UserGradeService {

    @Override
    public Result<Integer> selectLotteryActivityPointByGradeValue(@RequestParam("gradeValue") Byte gradeValue) {
        Integer point = 10;
        return successGet(point);
    }
}
