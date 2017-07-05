package com.lawu.eshop.ad.srv.mapper.extend;

import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;

public interface MemberAdRecordDOMapperExtend {
	
	List<MemberAdRecordDO> selectPointToday(MemberAdRecordDO marDO);
	
	BigDecimal getTotlePoint(Long  adId);
  
}