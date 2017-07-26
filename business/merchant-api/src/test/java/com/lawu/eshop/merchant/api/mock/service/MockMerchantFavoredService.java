package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.mall.param.MerchantFavoredParam;
import com.lawu.eshop.merchant.api.service.MerchantFavoredService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockMerchantFavoredService extends BaseController implements MerchantFavoredService {
    @Override
    public Result saveMerchantFavoredInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute MerchantFavoredParam param) {
        return successCreated();
    }

    @Override
    public Result<MerchantFavoredDTO> findFavoredByMerchantId(@PathVariable("merchantId") Long merchantId) {
        return successGet();
    }

    @Override
    public Result delMerchantFavoredInfo(@PathVariable("id") Long id, @RequestParam("merchantId") Long merchantId) {
        return successDelete();
    }

    @Override
    public Result updateMerchantFavoredInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute MerchantFavoredParam param) {
        return successCreated();
    }
}
