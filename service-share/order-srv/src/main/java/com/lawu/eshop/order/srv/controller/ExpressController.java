package com.lawu.eshop.order.srv.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.constants.ExpressInquiriesDetailStateEnum;
import com.lawu.eshop.order.dto.ExpressRecognitionDetailDTO;
import com.lawu.eshop.order.dto.foreign.ExpressInquiriesDetailDTO;
import com.lawu.eshop.order.param.ExpressQueryParam;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ExpressRecognitionDetailBO;
import com.lawu.eshop.order.srv.bo.ShipperBO;
import com.lawu.eshop.order.srv.converter.ExpressConverter;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;

/**
 * 
 * @author jiangxinjun
 * @date 2017年9月5日
 */
@RestController
@RequestMapping(value = "express")
public class ExpressController extends BaseController {

	@Autowired
	private ExpressStrategy expressStrategy;

	/**
	 * 根据快递单号和快递公司编码查询物流轨迹
	 * 快递公司非必填，可单独通过快递单号查询
	 * 
	 * @param param 查询物流轨迹参数
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月5日
	 */
	@RequestMapping(value = "inquiries", method = RequestMethod.PUT)
	public Result<ExpressInquiriesDetailDTO> inquiries(@RequestBody @Validated ExpressQueryParam param, BindingResult bindingResult) {
		String message = validate(bindingResult);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
		ExpressInquiriesDetailBO expressInquiriesDetailBO = null;
		/*
		 * 如果快递公司编码为空，通过物流编码查询快递公司编码
		 * 再通过快递公司编码和快递单号查询物流轨迹
		 */
		if (StringUtils.isNotBlank(param.getExpCode())) {
			expressInquiriesDetailBO = expressStrategy.inquiries(param.getExpCode(), param.getExpNo());
		}
		
		/*
		 *  1.如果通过用户选择的快递公司编号查询不到
		 *  2.接口没有传入快递公司编号
		 *  通过快递单号查询快递公司编号
		 */
		ShipperBO actualShipper = null;
		if (expressInquiriesDetailBO == null || ExpressInquiriesDetailStateEnum.NO_INFO.equals(expressInquiriesDetailBO.getState())) {
			ExpressRecognitionDetailBO expressRecognitionDetailBO = expressStrategy.recognition(param.getExpNo());
			if (expressRecognitionDetailBO != null && expressRecognitionDetailBO.getShippers() != null) {
				// 根据可能的快递公司编码，由可信度从高到低遍历查询
				for (ShipperBO shipper : expressRecognitionDetailBO.getShippers()) {
					expressInquiriesDetailBO = expressStrategy.inquiries(shipper.getShipperCode(), param.getExpNo());
					// 如果返回结果有物流轨迹，则跳出循环
					if (!ExpressInquiriesDetailStateEnum.NO_INFO.equals(expressInquiriesDetailBO.getState())) {
						// 放入真实的快递公司编码
						actualShipper = shipper;
						break;
					}
				}
			}
		}
		if (expressInquiriesDetailBO == null) {
			successCreated(ResultCode.THIRD_PARTY_LOGISTICS_INTERFACE_EXCEPTION);
		}
		ExpressInquiriesDetailDTO rtn = ExpressConverter.convert(expressInquiriesDetailBO);
		if (actualShipper != null) {
			rtn.setShipperCode(actualShipper.getShipperCode());
		} else {
			rtn.setShipperCode(param.getExpCode());
		}
		return successCreated(rtn);
	}
	
	/**
	 * 根据快递单号识别快递公司
	 * 
	 * @param param 查询物流轨迹参数
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月5日
	 */
	@RequestMapping(value = "recognition/{expNo}", method = RequestMethod.GET)
	public Result<ExpressRecognitionDetailDTO> recognition(@PathVariable("expNo") String expNo) {
		ExpressRecognitionDetailBO expressRecognitionDetailBO = expressStrategy.recognition(expNo);
		if (expressRecognitionDetailBO == null) {
			successCreated(ResultCode.THIRD_PARTY_LOGISTICS_INTERFACE_EXCEPTION);
		}
		ExpressRecognitionDetailDTO rtn = ExpressConverter.convert(expressRecognitionDetailBO);
		return successGet(rtn);
	}
}
