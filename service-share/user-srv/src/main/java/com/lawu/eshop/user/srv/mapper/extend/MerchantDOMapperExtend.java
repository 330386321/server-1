package com.lawu.eshop.user.srv.mapper.extend;

import com.lawu.eshop.user.srv.domain.extend.InviteMerchantInfoView;
import com.lawu.eshop.user.srv.domain.extend.MerchantDOView;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/5.
 */
public interface MerchantDOMapperExtend {

    int delMerchantGtPush(Long merchantId);

    MerchantDOView getMerchantViewById(Long id);

    List<String> selectNumLikeContent(String queryContent);

    /**
     * 我的E友查询商家的信息
     * @param inviteUserNum
     * @return
     */
    List<InviteMerchantInfoView> selectInviteMerchantInfo(String inviteUserNum);
}
