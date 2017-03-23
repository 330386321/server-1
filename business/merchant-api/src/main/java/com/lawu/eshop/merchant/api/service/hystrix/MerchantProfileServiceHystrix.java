package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.merchant.api.service.MerchantProfileService;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Created by Administrator on 2017/3/23.
 */
@Component
public class MerchantProfileServiceHystrix implements MerchantProfileService{
    @Override
    public void updateMerchantSizeLink(@ModelAttribute MerchantProfileParam merchantProfileParam) {
        System.out.println("服务调用失败");
    }

    @Override
    public MerchantProfileDTO findMerchantProfileInfo(@RequestParam("merchantProfileId") Long merchantProfileId) {
        System.out.println("服务调用失败");
        MerchantProfileDTO merchantProfileDTO = new MerchantProfileDTO();
        merchantProfileDTO.setId(-1L);
        return  merchantProfileDTO;
    }
}
