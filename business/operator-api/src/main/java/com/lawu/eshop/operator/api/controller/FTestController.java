package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.result.TableJson;
import com.lawu.eshop.operator.api.service.TestService;
import com.lawu.eshop.property.dto.QueryPropertyDTO;
import com.lawu.eshop.property.param.TestQueryParam;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "ftest/")
public class FTestController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(FTestController.class);
			
    @Autowired
    private TestService testService;

    @PageBody
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public Result page() throws Exception {
		Result<Page<QueryPropertyDTO>> dtos = new Result<>();
		Page<QueryPropertyDTO> page = new Page<>();
		dtos.setModel(page);
		return successGet(dtos);
	}

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
