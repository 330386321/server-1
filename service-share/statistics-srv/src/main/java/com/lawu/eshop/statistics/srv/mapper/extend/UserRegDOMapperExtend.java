package com.lawu.eshop.statistics.srv.mapper.extend;

import com.lawu.eshop.statistics.param.UserRegParam;
import com.lawu.eshop.statistics.srv.domain.extend.ReportUserRegDOView;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
public interface UserRegDOMapperExtend {

	List<ReportUserRegDOView> getReportUserRegDaily(UserRegParam param);

	List<ReportUserRegDOView> getReportUserRegMonth(UserRegParam param);

}
