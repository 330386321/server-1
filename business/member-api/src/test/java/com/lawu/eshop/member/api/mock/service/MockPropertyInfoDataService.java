package com.lawu.eshop.member.api.mock.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.PropertyInfoDataService;
import com.lawu.eshop.property.param.PointDetailQueryData1Param;
import com.lawu.eshop.property.param.PropertyInfoDataParam;

@Service
public class MockPropertyInfoDataService extends BaseController implements PropertyInfoDataService {

	@Override
	public Result doHanlderMinusPoint(@RequestBody @Valid PropertyInfoDataParam param) {
		return successCreated();
	}

	@Override
	public Result<Integer> getPointDetailByUserNumAndPointTypeAndBizId(@RequestBody PointDetailQueryData1Param param) {
		return successCreated(new Integer(1));
	}

	@Override
	public Result doHanlderMinusPointWithLottery(@RequestBody @Valid PropertyInfoDataParam param) {
		return null;
	}
}
