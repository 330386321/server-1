package com.lawu.eshop.ad.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;

public interface MemberAdRecordDOMapperExtend {
	
	List<MemberAdRecordDO> selectPointToday(Long memberId);
  
}