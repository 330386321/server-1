package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.dto.ExpressCompanyQueryDTO;
import com.lawu.eshop.mall.dto.ExpressCompanyRetrieveDTO;
import com.lawu.eshop.merchant.api.service.ExpressCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockExpressCompanyService extends BaseController implements ExpressCompanyService {
    @Override
    public Result<List<ExpressCompanyDTO>> list() {
        return successGet();
    }

    @Override
    public Result<ExpressCompanyQueryDTO> group() {
        return successGet();
    }

    @Override
    public Result<ExpressCompanyDTO> get(@PathVariable("id") Integer id) {
        ExpressCompanyDTO dto = new ExpressCompanyDTO();
        return successGet(dto);
    }

    @Override
    public Result<ExpressCompanyRetrieveDTO> listByKeyWord(@RequestParam("keyWord") String keyWord) {
        return successGet();
    }
}
