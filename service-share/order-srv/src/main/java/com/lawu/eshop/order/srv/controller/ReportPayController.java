package com.lawu.eshop.order.srv.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.lawu.eshop.user.param.ReportDataParam;

/**
 * 
 * <p>
 * Description: 买单交易统计
 * </p>
 * @author Yangqh
 * @date 2017年5月3日 下午12:01:54
 *
 */
@RestController
@RequestMapping(value = "reportPay/")
public class ReportPayController extends BaseController {

	@RequestMapping(value = "payVolumeRiseRate", method = RequestMethod.POST)
	public Result<ReportRiseRateDTO> payVolumeRiseRate(@RequestBody @Valid ReportDataParam dparam,
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
		ReportRiseRateDTO dto = null;//reportFansService.fansRiseRate(dparam);
		return successCreated(dto);
	}
}
