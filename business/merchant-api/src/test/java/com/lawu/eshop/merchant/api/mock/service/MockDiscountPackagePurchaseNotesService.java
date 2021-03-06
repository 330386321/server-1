package com.lawu.eshop.merchant.api.mock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackagePurchaseNotesDTO;
import com.lawu.eshop.merchant.api.service.DiscountPackagePurchaseNotesService;

/**
 * @author meishuquan
 * @date 2017/8/3.
 */
@Service
public class MockDiscountPackagePurchaseNotesService extends BaseController implements DiscountPackagePurchaseNotesService {
    @Override
    public Result<List<DiscountPackagePurchaseNotesDTO>> list() {
        return successGet();
    }
}
