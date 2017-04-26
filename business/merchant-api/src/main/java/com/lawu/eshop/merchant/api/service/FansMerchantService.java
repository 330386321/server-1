package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListInviteFansParam;
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
     * @param merchantId
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "fansMerchant/listInviteFans/{merchantId}")
    Result<List<FansMerchantDTO>> listInviteFans(@PathVariable("merchantId") Long merchantId, @ModelAttribute ListInviteFansParam param);

    /**
     * 粉丝列表
     *
     * @param merchantId    商户ID
     * @param listFansParam 查询参数
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "fansMerchant/listFans/{merchantId}")
    Result<Page<FansMerchantDTO>> listFans(@PathVariable("merchantId") Long merchantId, @ModelAttribute ListFansParam listFansParam);

    /**
     * 查询商家粉丝数量
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "fansMerchant/countFans/{merchantId}")
    Result<Integer> countFans(@PathVariable("merchantId") Long merchantId);
}
