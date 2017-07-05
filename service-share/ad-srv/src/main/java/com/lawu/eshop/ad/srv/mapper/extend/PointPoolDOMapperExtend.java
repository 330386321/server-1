package com.lawu.eshop.ad.srv.mapper.extend;

import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.ad.srv.domain.PointPoolDO;

public interface PointPoolDOMapperExtend {
	
	List<PointPoolDO> selectMember(Long adId);
	
	
	PointPoolDO selectPoint(Long adId);
	
	
	BigDecimal getTotlePoint(Long  adId);
}