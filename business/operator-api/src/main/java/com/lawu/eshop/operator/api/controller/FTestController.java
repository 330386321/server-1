package com.lawu.eshop.operator.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.api.result.TableJson;
import com.lawu.eshop.operator.api.service.TestService;
import com.lawu.eshop.property.dto.QueryPropertyDTO;
import com.lawu.eshop.property.param.TestQueryParam;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "ftest/")
public class FTestController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(FTestController.class);
			
    @Autowired
    private TestService testService;

	@RequestMapping(value = "jsondata", method = RequestMethod.POST)
	public @ResponseBody TableJson<QueryPropertyDTO> jsondata(@RequestBody TestQueryParam param)
			throws Exception {
		logger.info(param.getName());
		Result<Page<QueryPropertyDTO>> dtos = testService.query(param);
		Page<QueryPropertyDTO> page = dtos.getModel();
		return new TableJson<QueryPropertyDTO>(page);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Result save(@Valid TestQueryParam param,BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		int retcode = testService.save(param);
		logger.info("retcode="+retcode);
		return successCreated(retcode);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "delete/{propertyIds}", method = RequestMethod.DELETE)
	public @ResponseBody Result delete(@PathVariable @ApiParam(required = true, value = "主键") String propertyIds) {
		if(propertyIds == null ||"".equals(propertyIds)){
			return successCreated(ResultCode.ID_EMPTY);
		}
		int retcode = testService.delete(propertyIds);
		return successCreated(retcode);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "get/{propertyId}", method = RequestMethod.GET)
	public @ResponseBody Result get(@PathVariable @ApiParam(required = true, value = "主键") Long propertyId) {
		if(propertyId == null || propertyId == 0){
			return successCreated(ResultCode.ID_EMPTY);
		}
		QueryPropertyDTO dto = testService.get(propertyId);
		return successCreated(dto);
	}
}
