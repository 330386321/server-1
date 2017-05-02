package com.lawu.eshop.user.srv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.ReportRiseRateDTO;
import com.lawu.eshop.user.dto.ReportRiseRerouceDTO;
import com.lawu.eshop.user.param.ReportFansDataParam;
import com.lawu.eshop.user.srv.service.ReportFansService;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Yangqh
 * @date 2017年5月2日 下午2:36:03
 *
 */
@RestController
@RequestMapping(value = "reportFans/")
public class ReportFnasController extends BaseController {

	@Autowired
	private ReportFansService reportFansService;
	
	@RequestMapping(value = "fansRiseRate", method = RequestMethod.POST)
	public Result<ReportRiseRateDTO> fansRiseRate(@RequestBody @Valid ReportFansDataParam dparam,
			BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		ReportRiseRateDTO dto = reportFansService.fansRiseRate(dparam);
		return successCreated(dto);
	}
	
	/**
	 * 增长来源
	 * @param dparam
	 * @param result
	 * @return
	 * @author yangqh
	 * @date 2017年5月2日 下午7:16:27
	 */
	@RequestMapping(value = "fansRiseSource", method = RequestMethod.POST)
	public Result<List<ReportRiseRerouceDTO>> fansRiseSource(@RequestBody @Valid ReportFansDataParam dparam,
			BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		List<ReportRiseRerouceDTO> dtos = reportFansService.fansRiseSource(dparam);
		return successCreated(dtos);
	}

}
