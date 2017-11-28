package com.lawu.eshop.member.api.mock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.SeckillActivityService;
import com.lawu.eshop.product.dto.SeckillActivityThatDayDTO;

@Service
public class MockSeckillActivityService extends BaseController implements SeckillActivityService {

    @Override
    public Result<List<SeckillActivityThatDayDTO>> thatDayList() {
        return null;
    }

    @Override
    public Result<List<SeckillActivityThatDayDTO>> recentlyList() {
        return null;
    }
}
