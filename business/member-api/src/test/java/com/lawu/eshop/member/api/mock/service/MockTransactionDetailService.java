package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.TransactionDetailService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.dto.TransactionDetailToMemberDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockTransactionDetailService extends BaseController implements TransactionDetailService {


	@Override
	public Result<Page<TransactionDetailToMemberDTO>> findPageByUserNumForMember(@PathVariable("userNum") String userNum, @RequestBody TransactionDetailQueryForMemberParam param) {
		TransactionDetailToMemberDTO dto = new TransactionDetailToMemberDTO();
		dto.setBizId("1");
		dto.setTransactionType(MemberTransactionTypeEnum.AD_QZ);
		List<TransactionDetailToMemberDTO> list = new ArrayList<>();
		list.add(dto);
		Page<TransactionDetailToMemberDTO> page = new Page<>();
		page.setCurrentPage(1);
		page.setTotalCount(10);
		page.setRecords(list);
		return successCreated(page);
	}
}
