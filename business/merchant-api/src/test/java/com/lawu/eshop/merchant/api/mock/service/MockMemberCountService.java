package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.merchant.api.service.MemberCountService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockMemberCountService extends BaseController implements MemberCountService {
    @Override
    public Integer findMemberCount(@RequestParam("areas") String areas) {
        return 0;
    }

    @Override
    public Integer findFensCount(@RequestParam("merchantId") Long merchantId) {
        return 0;
    }
}
