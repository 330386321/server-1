package com.lawu.eshop.product.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lawu.eshop.product.srv.domain.extend.SeckillActivityDOView;

public interface SeckillActivityDOMapperExtend {
	
	List<SeckillActivityDOView> queryManagePage(Long merchantId, RowBounds rowBounds);
	
	int countManage(Long merchantId);

}
