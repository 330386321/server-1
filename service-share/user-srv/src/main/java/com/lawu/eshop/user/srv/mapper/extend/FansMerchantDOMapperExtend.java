package com.lawu.eshop.user.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lawu.eshop.user.param.ListFansRealParam;
import com.lawu.eshop.user.param.ListInviteFansRealParam;
import com.lawu.eshop.user.param.ListInviteFansRealWithContentParam;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOReportView;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOView;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public interface FansMerchantDOMapperExtend {

	List<FansMerchantDOView> listInviteFans(ListInviteFansRealParam listInviteFansRealParam);

	List<FansMerchantDOView> listInviteFansWithContent(ListInviteFansRealWithContentParam ListInviteFansRealWithContentParam);
	
	List<FansMerchantDOView> pageListInviteFans(ListInviteFansRealParam listInviteFansRealParam);

	int countInviteFans(ListInviteFansRealParam listInviteFansRealParam);

	List<FansMerchantDOView> listFans(ListFansRealParam listFansRealParam);

	int countFans(ListFansRealParam listFansRealParam);

	/**
	 * 粉丝增长量报表
	 * 
	 * @param formatDate
	 * @return
	 * @author yangqh
	 * @date 2017年5月2日 下午3:25:50
	 */
	List<FansMerchantDOReportView> fansRiseRate(@Param("formatDate") String formatDate, @Param("flag") Byte flag,
			@Param("merchantId") Long merchantId);

	/**
	 * 粉丝增长来源报表
	 * 
	 * @param dateFormat
	 * @param value
	 * @return
	 * @author yangqh
	 * @date 2017年5月2日 下午7:55:54
	 */
	List<FansMerchantDOReportView> fansRiseSource(@Param("formatDate") String formatDate, @Param("flag") Byte flag,
			@Param("merchantId") Long merchantId);

}
