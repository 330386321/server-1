package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

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
	 * @param expressInquiriesDetail 快递鸟查询封装数据
	 * @return
	 */
	public static ExpressInquiriesDetailBO convert(ExpressInquiriesDetail expressInquiriesDetail) {
		if (expressInquiriesDetail == null) {
			return null;
		}
		
		ExpressInquiriesDetailBO expressInquiriesDetailBO = new ExpressInquiriesDetailBO();
		expressInquiriesDetailBO.setSuccess(expressInquiriesDetail.getSuccess());
		expressInquiriesDetailBO.setReason(expressInquiriesDetail.getReason());
		expressInquiriesDetailBO.setState(expressInquiriesDetail.getState());
		
		List<TraceBO> TraceBOList = new ArrayList<TraceBO>();
		for (Trace trace : expressInquiriesDetail.getTraces()) {
			TraceBO traceBO = new TraceBO();
			traceBO.setAcceptStation(trace.getAcceptStation());
			traceBO.setAcceptTime(trace.getAcceptTime());
			traceBO.setRemark(trace.getRemark());
			TraceBOList.add(traceBO);
		}
		
		expressInquiriesDetailBO.setTraces(TraceBOList);
		
		return expressInquiriesDetailBO;
	}
	
	/**
	 * ExpressInquiriesDatailDTO转换
	 *
	 * @param expressInquiriesDetail 快递鸟查询封装数据
	 * @return
	 */
	public static ExpressInquiriesDetailDTO convert(ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		if (expressInquiriesDetailBO == null) {
			return null;
		}
		
		ExpressInquiriesDetailDTO expressInquiriesDetailDTO = new ExpressInquiriesDetailDTO();
		BeanUtils.copyProperties(expressInquiriesDetailBO, expressInquiriesDetailDTO, "traces");
		expressInquiriesDetailDTO.setSuccess(expressInquiriesDetailBO.getSuccess());
		expressInquiriesDetailDTO.setReason(expressInquiriesDetailBO.getReason());
		expressInquiriesDetailDTO.setState(ExpressInquiriesDetailStateEnum.getEnum(expressInquiriesDetailBO.getState()));
		
		List<TraceDTO> TraceDTOList = new ArrayList<TraceDTO>();
		for (TraceBO traceBO : expressInquiriesDetailBO.getTraces()) {
			TraceDTO TraceDTO = new TraceDTO();
			BeanUtils.copyProperties(traceBO, TraceDTO);
			TraceDTOList.add(TraceDTO);
		}
		
		expressInquiriesDetailDTO.setTraces(TraceDTOList);
		
		return expressInquiriesDetailDTO;
	}
}
