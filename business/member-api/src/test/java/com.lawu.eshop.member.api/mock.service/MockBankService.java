package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.BankService;
import com.lawu.eshop.property.dto.BankDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockBankService implements BankService {


    @Override
    public Result<List<BankDTO>> findBank() {
        return null;
    }
}
