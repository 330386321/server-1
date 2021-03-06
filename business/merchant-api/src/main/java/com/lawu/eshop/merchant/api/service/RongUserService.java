package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.RongYunOnlineDTO;
import com.lawu.eshop.user.dto.RongYunRefreshDTO;
import com.lawu.eshop.user.dto.RongYunTokenDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyong
 * @date 2017/4/14.
 */
@FeignClient(value = "user-srv")
public interface RongUserService {
    /**
     * 获取融云token
     * @param userId 用户 Id
     * @param name 用户名称
     * @param portraitUri 用户头像 URI
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rongUser/getRongToken",method = RequestMethod.GET)
    Result<RongYunTokenDTO> getRongToken(@RequestParam("userId") String userId, @RequestParam("name") String name, @RequestParam("portraitUri") String portraitUri) throws Exception;

    /**
     * 检查融云用户在线状态
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rongUser/checkOnline/{userId}", method = RequestMethod.GET)
    Result<RongYunOnlineDTO> checkOnline(@PathVariable("userId") String userId) throws Exception;

    /**
     * 刷新用户信息
     * @param userId
     * @param name
     * @param portraitUri
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "rongUser/refreshUserInfo", method = RequestMethod.GET)
    Result<RongYunRefreshDTO> refreshUserInfo(@RequestParam("userId") String userId, @RequestParam("name") String name, @RequestParam("portraitUri") String portraitUri) throws Exception;

}
