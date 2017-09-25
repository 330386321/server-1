package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;

import com.lawu.eshop.order.constants.ExpressInquiriesDetailStateEnum;
import com.lawu.eshop.order.dto.ExpressInquiriesDTO;
import com.lawu.eshop.order.dto.ExpressRecognitionDetailDTO;
import com.lawu.eshop.order.dto.foreign.ExpressInquiriesDetailDTO;
import com.lawu.eshop.order.dto.foreign.ShipperDTO;
import com.lawu.eshop.order.dto.foreign.TraceDTO;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ExpressRecognitionDetailBO;
import com.lawu.eshop.order.srv.bo.ShipperBO;
import com.lawu.eshop.order.srv.bo.TraceBO;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.ExpressInquiriesDetail;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.ExpressRecognitionDetail;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.Shipper;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.Trace;
import com.lawu.eshop.order.srv.utils.express.kdniao.constants.CodeEnum;
import com.lawu.eshop.order.srv.utils.express.kdniao.constants.StateEnum;

/**
 *
 * 物流实时查询数据转换器
 *
 * @author Sunny
 * @date 2017/4/10
 */
public class ExpressConverter {

	/**
	 * 隐藏构造方法
	 */
	private ExpressConverter() {
		throw new IllegalAccessError("Utility class");
	}

	/**
	 * ExpressInquiriesDatailBO转换
	 *
	 * @param expressInquiriesDetail
	 *            快递鸟查询封装数据
	 * @return
	 */
	public static ExpressInquiriesDetailBO convert(ExpressInquiriesDetail expressInquiriesDetail) {
		ExpressInquiriesDetailBO rtn = null;
		if (expressInquiriesDetail == null) {
			return rtn;
		}
		rtn = new ExpressInquiriesDetailBO();
		rtn.setState(expressInquiriesDetail.getState() != null ? StateEnum.getEnum(expressInquiriesDetail.getState()).getState() : ExpressInquiriesDetailStateEnum.NO_INFO);
		if (expressInquiriesDetail.getTraces() != null && !expressInquiriesDetail.getTraces().isEmpty()) {
			rtn.setTraces(new ArrayList<>());
			for (Trace item : expressInquiriesDetail.getTraces()) {
				rtn.getTraces().add(convert(item));
			}
		}
		return rtn;
	}
	
	/**
	 * ExpressInquiriesDatailBO转换
	 *
	 * @param expressInquiriesDetail 快递100查询封装数据
	 * @return
	 */
	public static ExpressInquiriesDetailBO convert(com.lawu.eshop.order.srv.utils.express.kuaidi100.bo.ExpressInquiriesDetail expressInquiriesDetail) {
		ExpressInquiriesDetailBO rtn = null;
		if (expressInquiriesDetail == null) {
			return rtn;
		}
		rtn = new ExpressInquiriesDetailBO();
		rtn.setState(expressInquiriesDetail.getState() != null ? com.lawu.eshop.order.srv.utils.express.kuaidi100.constants.StateEnum.getEnum(expressInquiriesDetail.getState()).getState() : ExpressInquiriesDetailStateEnum.NO_INFO);
		if (expressInquiriesDetail.getData() != null && !expressInquiriesDetail.getData().isEmpty()) {
			rtn.setTraces(new ArrayList<>());
			for (com.lawu.eshop.order.srv.utils.express.kuaidi100.bo.Trace item : expressInquiriesDetail.getData()) {
				rtn.getTraces().add(convert(item));
			}
		}
		return rtn;
	}

	/**
	 * 
	 * @param trace
	 * @return
	 * @author Sunny
	 * @date 2017年6月15日
	 */
	public static TraceBO convert(com.lawu.eshop.order.srv.utils.express.kuaidi100.bo.Trace trace) {
		TraceBO rtn = null;
		if (trace == null) {
			return rtn;
		}
		rtn = new TraceBO();
		rtn.setAcceptStation(trace.getContext());
		rtn.setAcceptTime(trace.getTime());
		return rtn;
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
		rtn.setState(expressInquiriesDetailBO.getState());
		rtn.setTraces(new ArrayList<>());
		if (expressInquiriesDetailBO.getTraces() != null && !expressInquiriesDetailBO.getTraces().isEmpty()) {
			for (TraceBO item : expressInquiriesDetailBO.getTraces()) {
				rtn.getTraces().add(convert(item));
			}
		}
		return rtn;
	}
	
	/**
	 * ExpressInquiriesDTO转换
	 *
	 * @param expressInquiriesDetailBO
	 * @return
	 */
	public static ExpressInquiriesDTO convertExpressInquiriesDTO(ExpressInquiriesDetailBO expressInquiriesDetailBO) {
		ExpressInquiriesDTO rtn = null;
		if (expressInquiriesDetailBO == null) {
			return rtn;
		}
		rtn = new ExpressInquiriesDTO();
		rtn.setState(expressInquiriesDetailBO.getState());
		rtn.setTraces(new ArrayList<>());
		if (expressInquiriesDetailBO.getTraces() != null && !expressInquiriesDetailBO.getTraces().isEmpty()) {
			for (TraceBO item : expressInquiriesDetailBO.getTraces()) {
				rtn.getTraces().add(convert(item));
			}
		}
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
	 * @param expressRecognitionDetail
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月5日
	 */
	public static ExpressRecognitionDetailBO convert(ExpressRecognitionDetail expressRecognitionDetail) {
		ExpressRecognitionDetailBO rtn = null;
		if (expressRecognitionDetail == null) {
			return rtn;
		}
		rtn = new ExpressRecognitionDetailBO();
		rtn.setCode(CodeEnum.getEnum(expressRecognitionDetail.getCode()));
		rtn.seteBusinessId(expressRecognitionDetail.geteBusinessId());
		rtn.setLogisticCode(expressRecognitionDetail.getLogisticCode());
		rtn.setSuccess(expressRecognitionDetail.getSuccess());
		rtn.setShippers(new ArrayList<>());
		for (Shipper shipper : expressRecognitionDetail.getShippers()) {
			ShipperBO shipperBO = new ShipperBO();
			shipperBO.setShipperCode(shipper.getShipperCode());
			shipperBO.setShipperName(shipper.getShipperName());
			rtn.getShippers().add(shipperBO);
		}
		return rtn;
	}
	
	/**
	 * 
	 * @param expressRecognitionDetailBO
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月5日
	 */
	public static ExpressRecognitionDetailDTO convert(ExpressRecognitionDetailBO expressRecognitionDetailBO) {
		ExpressRecognitionDetailDTO rtn = null;
		rtn = new ExpressRecognitionDetailDTO();
		rtn.setShippers(new ArrayList<>());
		for (ShipperBO shipperBO : expressRecognitionDetailBO.getShippers()) {
			ShipperDTO shipperDTO = new ShipperDTO();
			shipperDTO.setShipperCode(shipperBO.getShipperCode());
			rtn.getShippers().add(shipperDTO);
		}
		return rtn;
	}

}
