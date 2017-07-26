package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackageDetailDTO;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import com.lawu.eshop.mall.param.DiscountPackageSaveParam;
import com.lawu.eshop.mall.param.DiscountPackageUpdateParam;
import com.lawu.eshop.mall.param.foreign.DiscountPackageQueryForeignParam;
import com.lawu.eshop.merchant.api.service.DiscountPackageService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockDiscountPackageService extends BaseController implements DiscountPackageService {
    @Override
    public Result<Page<DiscountPackageQueryDTO>> listForMerchant(@PathVariable("merchantId") Long merchantId, @RequestBody DiscountPackageQueryForeignParam param) {
        return successCreated();
    }

    @Override
    public Result<DiscountPackageDetailDTO> get(@PathVariable("id") Long id) {
        return successGet();
    }

    @Override
    public Result save(@PathVariable("merchantId") Long merchantId, @RequestBody DiscountPackageSaveParam discountPackageSaveParam) {
        return successCreated();
    }

    @Override
    public Result update(@PathVariable("merchantId") Long merchantId, @PathVariable("id") Long id, @RequestBody DiscountPackageUpdateParam discountPackageUpdateParam) {
        return successCreated();
    }

    @Override
    public Result delete(@PathVariable("merchantId") Long merchantId, @PathVariable("id") Long id) {
        return successCreated();
    }

    @Override
    public Result up(@PathVariable("merchantId") Long merchantId, @PathVariable("id") Long id) {
        return successCreated();
    }

    @Override
    public Result down(@PathVariable("merchantId") Long merchantId, @PathVariable("id") Long id) {
        return successCreated();
    }

    @Override
    public Result delete(@PathVariable("merchantId") Long merchantId, @RequestBody List<Long> idList) {
        return successCreated();
    }

    @Override
    public Result up(@PathVariable("merchantId") Long merchantId, @RequestBody List<Long> idList) {
        return successCreated();
    }

    @Override
    public Result down(@PathVariable("merchantId") Long merchantId, @RequestBody List<Long> idList) {
        return successCreated();
    }
}
