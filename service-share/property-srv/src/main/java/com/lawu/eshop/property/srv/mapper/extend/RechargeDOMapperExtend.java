package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.property.srv.domain.RechargeDO;
import com.lawu.eshop.property.srv.domain.RechargeDOExample;
import com.lawu.eshop.property.srv.domain.extend.ReportAreaRechargeDOExtend;
import com.lawu.eshop.property.srv.domain.extend.ReportAreaRechargeDOView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RechargeDOMapperExtend {

    List<ReportAreaRechargeDOExtend> selectAgentAreaReportRechargeListByDate(ReportAreaRechargeDOView recharge);
}