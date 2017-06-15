package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.order.constants.ExpressInquiriesDetailStateEnum;
import com.lawu.eshop.order.dto.foreign.ExpressInquiriesDetailDTO;
import com.lawu.eshop.order.dto.foreign.TraceDTO;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.TraceBO;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.ExpressInquiriesDetail;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.Trace;

/**
 *
 * 物流实时查询数据转换器
 *
 * @author Sunny
 * @date 2017/4/10
 */
public class ExpressInquiriesDetailConverter {

	/**
	 * ExpressInquiriesDatailBO转换
	 *
	 * @param expressInquiriesDetail
	 *            快递鸟查询封装数据
	 * @return
	 */
	public static ExpressInquiriesDetailBO convert(ExpressInquiriesDetail expressInquiriesDetail) {
		if (expressInquiriesDetail == null) {
			return null;
		}

		ExpressInquiriesDetailBO expressInquiriesDetailBO = new ExpressInquiriesDetailBO();
		expressInquiriesDetailBO.setSuccess(expressInquiriesDetail.getSuccess());
		expressInquiriesDetailBO.setReason(expressInquiriesDetail.getReason());
		expressInquiriesDetailBO.setState(ExpressInquiriesDetailStateEnum.getEnum(expressInquiriesDetail.getState()));
		expressInquiriesDetailBO.setTraces(convert(expressInquiriesDetail.getTraces()));

		return expressInquiriesDetailBO;
	}

	/**
	 * 
	 * @param trace
	 * @return
	 * @author Sunny
	 * @date 2017年6月15日
	 */
	public static TraceBO convert(Trace trace) {
		TraceBO rtn = null;
		if (trace == null) {
			return rtn;
		}

		rtn = new TraceBO();
		rtn.setAcceptStation(trace.getAcceptStation());
		rtn.setAcceptTime(trace.getAcceptTime());
		rtn.setRemark(trace.getRemark());

		return rtn;
	}

	/**
	 * 
	 * @param trace
	 * @return
	 * @author Sunny
	 * @date 2017年6月15日
	 */
	public static List<TraceBO> convert(List<Trace> traceList) {
		List<TraceBO> rtn = null;
		if (traceList == null || traceList.isEmpty()) {
			return rtn;
		}

		rtn = new ArrayList<TraceBO>();
		for (Trace trace : traceList) {
			rtn.add(convert(trace));
		}

		return rtn;
	}

	/**
	 * ExpressInquiriesDatailDTO转换
	 *
	 * @param expressInquiriesDetail
	 *            快递鸟查询封装数据
	 * @return
	 */
	public static ExpressInquiriesDetailDTO convert(ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		ExpressInquiriesDetailDTO rtn = null;

		if (expressInquiriesDetailBO == null) {
			return rtn;
		}

		rtn = new ExpressInquiriesDetailDTO();
		rtn.setSuccess(expressInquiriesDetailBO.getSuccess());
		rtn.setReason(expressInquiriesDetailBO.getReason());
		rtn.setState(expressInquiriesDetailBO.getState());
		rtn.setTraces(convertTraceDTOList(expressInquiriesDetailBO.getTraces()));
		return rtn;
	}

	/**
	 * 
	 * @param traceBO
	 * @return
	 * @author Sunny
	 * @date 2017年6月15日
	 */
	public static TraceDTO convert(TraceBO traceBO) {
		TraceDTO rtn = null;
		if (traceBO == null) {
			return rtn;
		}

		rtn = new TraceDTO();
		rtn.setAcceptStation(traceBO.getAcceptStation());
		rtn.setAcceptTime(traceBO.getAcceptTime());
		rtn.setRemark(traceBO.getRemark());

		return rtn;
	}

	/**
	 * 
	 * @param trace
	 * @return
	 * @author Sunny
	 * @date 2017年6月15日
	 */
	public static List<TraceDTO> convertTraceDTOList(List<TraceBO> traceBOList) {
		List<TraceDTO> rtn = null;
		if (traceBOList == null || traceBOList.isEmpty()) {
			return rtn;
		}

		rtn = new ArrayList<TraceDTO>();
		for (TraceBO traceBO : traceBOList) {
			rtn.add(convert(traceBO));
		}

		return rtn;
	}

}
