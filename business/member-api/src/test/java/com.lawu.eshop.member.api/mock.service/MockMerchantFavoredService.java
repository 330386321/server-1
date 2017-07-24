package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.member.api.service.MerchantFavoredService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class MockMerchantFavoredService implements MerchantFavoredService {


    @Override
    public Result<MerchantFavoredDTO> findFavoredByMerchantId(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<MerchantFavoredDTO> findFavoredById(@PathVariable("id") Long id) {
        return null;
    }
}
