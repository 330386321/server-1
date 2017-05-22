package com.lawu.eshop.member.api.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantBaseInfoDTO;
import com.lawu.eshop.user.dto.RongYunDTO;

/**
 * @author meishuquan
 * @date 2017/5/19
 */
@FeignClient(value = "user-srv")
public interface MerchantService {

    /**
     * 根据商家编号查询融云需要的信息
     *
     * @param num
     * @return
     */
    @RequestMapping(value = "merchant/getRongYunInfo/{num}", method = RequestMethod.GET)
    Result<RongYunDTO> getRongYunInfoByNum(@PathVariable("num") String num);
    
    /**
     * 根据商家ID查询基本信息
     * @param merchantId
     * @return
     * @author yangqh
     * @date 2017年5月22日 上午10:56:28
     */
    @RequestMapping(value = "merchant/getMerchantById/{merchantId}", method = RequestMethod.GET)
    Result<MerchantBaseInfoDTO> getMerchantById(@PathVariable("merchantId") Long merchantId);

}
