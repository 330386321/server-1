package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.CollectionUserRegParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
@FeignClient(value = "user-srv")
public interface CollectionUserRegService {

    /**
     * 按日统计会员注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMemberRegDaily", method = RequestMethod.POST)
    Result<Integer> collectionMemberRegDaily(@RequestBody CollectionUserRegParam param);

    /**
     * 按日统计商家注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMerchantRegDaily", method = RequestMethod.POST)
    Result<Integer> collectionMerchantRegDaily(@RequestBody CollectionUserRegParam param);

    /**
     * 按月统计会员注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMemberRegMonth", method = RequestMethod.POST)
    Result<Integer> collectionMemberRegMonth(@RequestBody CollectionUserRegParam param);

    /**
     * 按月统计商家注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMerchantRegMonth", method = RequestMethod.POST)
    Result<Integer> collectionMerchantRegMonth(@RequestBody CollectionUserRegParam param);

    /**
     * 按区域统计会员注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMemberRegArea", method = RequestMethod.POST)
    Result<Integer> collectionMemberRegArea(@RequestBody CollectionUserRegParam param);

    /**
     * 按区域统计普通商家注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMerchantCommonRegArea", method = RequestMethod.POST)
    Result<Integer> collectionMerchantCommonRegArea(@RequestBody CollectionUserRegParam param);

    /**
     * 按区域统计实体商家注册量
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "userReg/collectionMerchantEntityRegArea", method = RequestMethod.POST)
    Result<Integer> collectionMerchantEntityRegArea(@RequestBody CollectionUserRegParam param);

}
