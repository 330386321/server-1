package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lawu.eshop.property.srv.domain.extend.AreaRechargePointDOView;
import com.lawu.eshop.property.srv.domain.extend.ReportAreaRechargeDOExtend;
import com.lawu.eshop.property.srv.domain.extend.ReportAreaRechargeDOView;

public interface RechargeDOMapperExtend {

	List<ReportAreaRechargeDOExtend> selectAgentAreaReportRechargeListByDate(ReportAreaRechargeDOView recharge);
	List<AreaRechargePointDOView> selectAreaRechargePoint(@Param("bdate")String bdate, @Param("edate")String edate);
}
