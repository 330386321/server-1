package com.lawu.eshop.merchant.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.merchant.api.service.ExpressCompanyService;
import com.lawu.eshop.merchant.api.service.ExpressService;
import com.lawu.eshop.order.dto.ExpressRecognitionDetailDTO;
import com.lawu.eshop.order.dto.foreign.ExpressInquiriesDetailDTO;
import com.lawu.eshop.order.dto.foreign.ExpressInquiriesDetailForeignDTO;
import com.lawu.eshop.order.dto.foreign.ShipperDTO;
import com.lawu.eshop.order.param.ExpressQueryParam;
import com.lawu.eshop.order.param.foreign.ExpressQueryForeignParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny
 * @date 2017/3/27
 */
@Api(tags = "express")
@RestController
@RequestMapping(value = "express/")
public class ExpressController extends BaseController {

	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private ExpressCompanyService expressCompanyService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询物流轨迹", notes = "根据快递单号和快递公司编码查询物流轨迹[1004|4030]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@Authorization
	@RequestMapping(value = "inquiries", method = RequestMethod.GET)
	public Result<ExpressInquiriesDetailForeignDTO> list(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @Validated ExpressQueryForeignParam param, BindingResult bindingResult) {
		String message = validate(bindingResult);
    	if (message != null) {
    		return successGet(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	
    	ExpressCompanyDTO expressCompanyDTO  = null;
    	// 如果有传入快递公司id，通过快递公司id获取快递公司编号
    	if (param.getExpId() != null) {
    		Result<ExpressCompanyDTO> getResult = expressCompanyService.get(param.getExpId());
    		if (!isSuccess(getResult)) {
    			return successGet(getResult);
    		}
    		expressCompanyDTO = getResult.getModel();
    	}
    	ExpressQueryParam expressQueryParam = new ExpressQueryParam();
    	expressQueryParam.setExpCode(expressCompanyDTO != null ? expressCompanyDTO.getCode() : null);
    	expressQueryParam.setExpNo(param.getExpNo());
    	Result<ExpressInquiriesDetailDTO> inquiriesResult = expressService.inquiries(expressQueryParam);
    	if (!isSuccess(inquiriesResult)) {
			return successGet(inquiriesResult);
		}
    	ExpressInquiriesDetailDTO expressInquiriesDetailDTO = inquiriesResult.getModel();
    	ExpressInquiriesDetailForeignDTO rtn = new ExpressInquiriesDetailForeignDTO();
    	rtn.setShipperCode(expressInquiriesDetailDTO.getShipperCode());
    	rtn.setState(expressInquiriesDetailDTO.getState());
    	rtn.setTraces(expressInquiriesDetailDTO.getTraces());
    	if (inquiriesResult.getModel().getShipperCode().equals(expressQueryParam.getExpCode())) {
    		rtn.setShipperCode(expressCompanyDTO.getCode());
    		rtn.setShipperName(expressCompanyDTO.getName());
    	} else {
    		Result<ExpressCompanyDTO> codeResult = expressCompanyService.code(rtn.getShipperCode());
    		if (!isSuccess(codeResult)) {
    			return successGet(codeResult);
    		}
    		rtn.setShipperName(codeResult.getModel().getName());
    	}
		return successGet(rtn);
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "快递单号识别", notes = "根据快递单号识别快递公司。[1004|4030]（蒋鑫俊）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@Authorization
	@RequestMapping(value = "recognition/{expNo}", method = RequestMethod.GET)
	public Result<List<ExpressCompanyDTO>> code(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("expNo") String expNo) {
		List<ExpressCompanyDTO> rtn = new ArrayList<>();
		Result<ExpressRecognitionDetailDTO> recognitionResult = expressService.recognition(expNo);
		if (!isSuccess(recognitionResult)) {
			return successGet(recognitionResult);
		}
		// 如果第三方没有返回快递公司编码集合，直接返回一个空集合
		if (recognitionResult.getModel().getShippers() == null || recognitionResult.getModel().getShippers().isEmpty()) {
			return successGet(rtn);
		}
		// 根据第三方接口返回的快递编码集合，查询快递公司信息
		List<String> codeList = new ArrayList<>();
		for (ShipperDTO shipperDTO : recognitionResult.getModel().getShippers()) {
			codeList.add(shipperDTO.getShipperCode());
		}
		Result<List<ExpressCompanyDTO>> result =  expressCompanyService.codeList(codeList);
		return successGet(result);
	}
}
