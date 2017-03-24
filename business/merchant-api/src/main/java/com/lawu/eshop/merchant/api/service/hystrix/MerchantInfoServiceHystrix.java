package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.merchant.api.service.MerchantInfoService;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Created by Administrator on 2017/3/23.
 */
@Component
public class MerchantInfoServiceHystrix implements MerchantInfoService {
    @Override
    public int updateMerchantSizeLink(@RequestBody MerchantProfileParam merchantProfileParam, @RequestParam Long id) {
        return -1;
    }

    @Override
    public MerchantInfoDTO findMerchantProfileInfo(@RequestParam("merchantProfileId") Long merchantProfileId) {
        System.out.println("服务调用失败");
        MerchantInfoDTO merchantProfileDTO = new MerchantInfoDTO();
        merchantProfileDTO.setAccount("-1");
        return  merchantProfileDTO;
    }
}
