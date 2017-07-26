package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import com.lawu.eshop.merchant.api.service.IndustryTypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockIndustryTypeService extends BaseController implements IndustryTypeService {
    @Override
    public Result<List<IndustryTypeDTO>> listIndustryType() {
        return successGet();
    }

    @Override
    public Result<List<IndustryTypeDTO>> listIndustryTypeByParentId(@PathVariable("parentId") Short parentId) {
        return successGet();
    }

    @Override
    public Result<List<IndustryTypeDTO>> getAllIndustryList() {
        return successGet();
    }
}
