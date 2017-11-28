package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.PraiseDoHanlderMinusPointService;

@Service
public class MockPraiseDoHanlderMinusPointService extends BaseController implements PraiseDoHanlderMinusPointService {

    @Override
    public Result setAdPraiseIsDoPointRecord(@RequestParam("adIdAndMemberId") String adIdAndMemberId) {
        return null;
    }
}
