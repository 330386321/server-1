package com.lawu.eshop.jobs.mock.service;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.PayOrderService;
import com.lawu.eshop.order.dto.PayOrderAutoCommentDTO;
import org.springframework.stereotype.Service;

@Service
public class MockPayOrderServiceImpl implements PayOrderService {


	@Override
	public Result<List<PayOrderAutoCommentDTO>> getAutoCommentPayOrderList() {
		return null;
	}
}
