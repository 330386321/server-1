package com.lawu.eshop.order.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;

public interface PayOrderExtendDOMapper {

	List<ReportRiseRateView> payVolumeRiseRate(@Param("formatDate") String formatDate, @Param("flag") Byte flag,
			@Param("merchantId") Long merchantId, @Param("status") Byte status);

	String payVolumeTotal(@Param("formatDate") String formatDate, @Param("flag") Byte flag,
			@Param("merchantId") Long merchantId, @Param("status") Byte status);

}