package com.lawu.eshop.user.srv.mapper.extend;

import com.lawu.eshop.user.param.InviteInviteFansRealParam;
import com.lawu.eshop.user.param.ListFansRealParam;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOView;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public interface FansMerchantDOMapperExtend {

    List<FansMerchantDOView> listInviteFans(InviteInviteFansRealParam inviteFansRealParam);

    List<FansMerchantDOView> listFans(ListFansRealParam listFansRealParam);

}
