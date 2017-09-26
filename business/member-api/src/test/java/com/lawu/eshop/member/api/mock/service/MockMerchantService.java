package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MerchantService;
import com.lawu.eshop.user.dto.MerchantBaseInfoDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.dto.RongYunDTO;

@Service
public class MockMerchantService extends BaseController implements MerchantService {

    @Override
    public Result<RongYunDTO> getRongYunInfoByNum(@PathVariable("num") String num) {
        return successCreated();
    }

    @Override
    public Result<MerchantBaseInfoDTO> getMerchantById(@PathVariable("merchantId") Long merchantId) {
        MerchantBaseInfoDTO dto = new MerchantBaseInfoDTO();
        dto.setUserNum("B00001");
        return successCreated(dto);
    }

    @Override
    public Result<MerchantDTO> getMerchantByAccount(@PathVariable("account") String account) {
        MerchantDTO dto = new MerchantDTO();
        dto.setId(200L);
        dto.setNum("B0002");
        return null;
    }
}
