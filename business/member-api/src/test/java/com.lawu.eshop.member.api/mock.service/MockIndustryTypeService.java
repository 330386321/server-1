package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import com.lawu.eshop.member.api.service.IndustryTypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class MockIndustryTypeService implements IndustryTypeService {


    @Override
    public Result<List<IndustryTypeDTO>> listIndustryType() {
        return null;
    }

    @Override
    public Result<List<IndustryTypeDTO>> listIndustryTypeByParentId(@PathVariable("parentId") Short parentId) {
        return null;
    }
}
