package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.TransactionDetailService;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.dto.TransactionDetailToMerchantDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockTransactionDetailService extends BaseController implements TransactionDetailService {
    @Override
    public Result<Page<TransactionDetailToMerchantDTO>> findPageByUserNumForMerchant(@PathVariable("userNum") String userNum, @RequestBody TransactionDetailQueryForMerchantParam param) {
        TransactionDetailToMerchantDTO dto = new TransactionDetailToMerchantDTO();
        dto.setBizId("10");
        dto.setTransactionType(MerchantTransactionTypeEnum.WITHDRAW);

        List<TransactionDetailToMerchantDTO> list = new ArrayList<>();
        list.add(dto);

        Page<TransactionDetailToMerchantDTO> page = new Page<>();
        page.setCurrentPage(1);
        page.setTotalCount(10);
        page.setRecords(list);
        return successCreated(page);
    }
}
