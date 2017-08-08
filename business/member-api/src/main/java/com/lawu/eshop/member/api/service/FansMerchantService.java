package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user-srv")
public interface FansMerchantService {

	/**
	 * 查询用户是否是商家的粉丝
	 * 
	 * @param merchantId
	 * @param memberId
	 * @return
	 * @author Sunny
	 */
    @RequestMapping(value = "fansMerchant/isFansMerchant/{merchantId}", method = RequestMethod.GET)
    Result<Boolean> isFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId);
    
    /**
     * 粉丝列表
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "fansMerchant/findMerchant", method = RequestMethod.GET)
    public List<Long> findMerchant(@RequestParam("memberId") Long memberId);

    /**
     * 成为商家粉丝
     *
     * @param merchantId
     * @param memberId
     * @param channelEnum
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "fansMerchant/saveFansMerchant/{merchantId}", method = RequestMethod.PUT)
    Result saveFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId, @RequestParam("channelEnum") FansMerchantChannelEnum channelEnum);

    
    /**
     * 用户处理粉丝邀请
     *
     * @param merchantId
     * @param memberId
     * @param channelEnum
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "fansMerchant/saveFansMerchantFromInvite/{merchantId}", method = RequestMethod.PUT)
    Result saveFansMerchantFromInvite(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId, @RequestParam("messageId") Long messageId, @RequestParam("dealWay") Boolean dealWay);
    
    /**
     * 查询商家粉丝数量
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "fansMerchant/countFans/{merchantId}")
    Result<Integer> countFans(@PathVariable("merchantId") Long merchantId);
}
