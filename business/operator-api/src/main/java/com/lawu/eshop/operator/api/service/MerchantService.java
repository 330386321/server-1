package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.dto.MerchantSNSDTO;
import com.lawu.eshop.user.dto.MerchantViewDTO;
import com.lawu.eshop.user.dto.MessagePushDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/24.
 */
@FeignClient(value = "user-srv")
public interface MerchantService {

    @RequestMapping(value = "merchant/findMessagePushList", method = RequestMethod.GET)
    Result<List<MessagePushDTO>> findMessagePushList(@RequestParam(value = "area") String area);

    @RequestMapping(value = "merchant/findMessagePushByMobile", method = RequestMethod.GET)
    MessagePushDTO findMessagePushByMobile(@RequestParam("moblie") String moblie);

    /**
     * 商家基本信息
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchant/selectMerchantInfo")
    Result<MerchantSNSDTO> selectMerchantInfo(@RequestParam("merchantId") Long merchantId);

    /**
     * 根据账号查询商户信息
     *
     * @param account 商家账号
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchant/getMerchant/{account}")
    Result<MerchantDTO> getMerchantByAccount(@PathVariable("account") String account);

    /**
     * 根据编号查询商家信息
     *
     * @param num
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchant/getMerchantByNum")
    Result<MerchantDTO> getMerchantByNum(@RequestParam("num") String num);

    /**
     * 根据商家ID查询商家基本信息
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchant/getMerchantView/{id}")
    Result<MerchantViewDTO> getMerchantView(@PathVariable("id") Long id);

}
