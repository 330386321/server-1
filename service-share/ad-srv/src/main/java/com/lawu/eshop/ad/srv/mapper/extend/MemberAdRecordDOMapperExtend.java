package com.lawu.eshop.ad.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;
import com.lawu.eshop.ad.srv.domain.extend.MemberAdRecordDOView;

public interface MemberAdRecordDOMapperExtend {
	
	List<MemberAdRecordDO> selectPointToday(MemberAdRecordDO marDO);
	
	MemberAdRecordDOView getTotlePoint(Long  adId);
  
}