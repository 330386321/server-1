package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.result.TableJson;
import com.lawu.eshop.operator.api.service.TestService;
import com.lawu.eshop.property.dto.QueryPropertyDTO;
import com.lawu.eshop.property.param.TestQueryParam;


@RestController
@RequestMapping(value = "ftest/")
public class FTestController extends BaseController {

    @Autowired
    private TestService testService;

	@RequestMapping(value = "jsondata", method = RequestMethod.POST)
	public @ResponseBody TableJson<QueryPropertyDTO> jsondata(@ModelAttribute TestQueryParam param, Model model)
			throws Exception {
		Result<Page<QueryPropertyDTO>> dtos = testService.query(param);
		Page<QueryPropertyDTO> page = dtos.getModel();
		page.setCurrentPage(page.getCurrentPage()+1);
		return new TableJson<QueryPropertyDTO>(page);
	}
}
