package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.ListFansParam;
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
     * @param inviteFansParam
     * @return
     */
    List<FansMerchantBO> listInviteFans(InviteFansParam inviteFansParam);

    /**
     * 查询粉丝列表
     *
     * @param merchantId
     * @param listFansParam
     * @return
     */
    List<FansMerchantBO> listFans(Long merchantId, ListFansParam listFansParam);

    /**
     * 根据会员ID和商家ID查询粉丝记录
     *
     * @param memberId
     * @param merchantId
     * @return
     */
    FansMerchantBO getFansMerchant(Long memberId, Long merchantId);

}
