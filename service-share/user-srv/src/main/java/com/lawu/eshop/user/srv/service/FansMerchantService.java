package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListInviteFansParam;
import com.lawu.eshop.user.param.PageListInviteFansParam;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public interface FansMerchantService {

    /**
     * 查询会员邀请粉丝
     *
     * @param merchantId
     * @param param
     * @return
     */
    List<FansMerchantBO> listInviteFans(Long merchantId, ListInviteFansParam param);

    /**
     * 分页查询会员邀请粉丝
     *
     * @param merchantId
     * @param param
     * @return
     */
    Page<FansMerchantBO> pageListInviteFans(Long merchantId, PageListInviteFansParam param);

    /**
     * 查询粉丝列表
     *
     * @param merchantId
     * @param listFansParam
     * @return
     */
    Page<FansMerchantBO> listFans(Long merchantId, ListFansParam listFansParam);

    /**
     * 根据会员ID和商家ID查询粉丝记录
     *
     * @param memberId
     * @param merchantId
     * @return
     */
    FansMerchantBO getFansMerchant(Long memberId, Long merchantId);

    /**
     * 查询当前商家的所有粉丝
     *
     * @param memberId
     * @return
     */
    List<Long> findMerchant(Long memberId);

    /**
     * 根据商家查询粉丝数量
     *
     * @param merchantId
     * @return
     */
    Integer findFensCount(Long merchantId);

    /**
     * 成为商家粉丝
     *
     * @param merchantId
     * @param memberId
     * @param channelEnum
     */
    void saveFansMerchant(Long merchantId, Long memberId, FansMerchantChannelEnum channelEnum);

}
