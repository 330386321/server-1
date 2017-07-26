package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.BankAccountService;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockBankAccountService extends BaseController implements BankAccountService {
    @Override
    public Result saveBankAccount(@RequestParam("userNum") String userNum, @RequestBody BankAccountParam bankAccountParam) {
        return successCreated();
    }

    @Override
    public Result<List<BankAccountDTO>> selectMyBank(@RequestParam("userNum") String userNum) {
        return successGet();
    }

    @Override
    public Result delete(@PathVariable("id") Long id) {
        return successDelete();
    }

    @Override
    public Result<BankAccountDTO> selectAccount(@PathVariable("id") Long id) {
        return successGet();
    }

    @Override
    public Result updateBankAccount(@PathVariable("id") Long id, @RequestParam("userNum") String userNum, @RequestBody BankAccountParam bankAccountParam) {
        return successCreated();
    }
}
