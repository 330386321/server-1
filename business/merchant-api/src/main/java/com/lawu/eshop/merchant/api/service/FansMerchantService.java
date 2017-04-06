package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.ListFansParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@FeignClient(value = "user-srv")
public interface FansMerchantService {

    /**
     * 查询可邀请为粉丝的会员
     *
     * @param inviteFansParam 查询参数
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "fansMerchant/listInviteFans")
    Result<List<FansMerchantDTO>> listInviteFans(@ModelAttribute InviteFansParam inviteFansParam);

    /**
     * 粉丝列表
     *
     * @param merchantId    商户ID
     * @param listFansParam 查询参数
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "fansMerchant/listFans/{merchantId}")
    Result<List<FansMerchantDTO>> listFans(@PathVariable("merchantId") Long merchantId, @ModelAttribute ListFansParam listFansParam);
}
