package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.CashUserInfoDTO;
import com.lawu.eshop.user.dto.MerchantAuditInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreAdInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.ApplyStoreParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockMerchantStoreService extends BaseController implements MerchantStoreService {
    @Override
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id) {
        return successGet();
    }

    @Override
    public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute MerchantStoreParam merchantStoreParam) {
        return successCreated();
    }

    @Override
    public Result saveMerchantStoreAuditInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @ModelAttribute MerchantStoreParam merchantStoreParam) {
        return successCreated();
    }

    @Override
    public CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id) {
        CashUserInfoDTO dto = new CashUserInfoDTO();
        dto.setName("test");
        dto.setAccount("13888888888");
        dto.setProvinceId(44);
        dto.setCityId(4403);
        dto.setAreaId(440303);
        dto.setRegionFullName("广东省深圳南山区");
        return dto;
    }

    @Override
    public Result<MerchantAuditInfoDTO> getMerchantAuditInfo(@PathVariable(value = "merchantId") Long merchantId) {
        return successGet();
    }

    @Override
    public Result updateNoReasonReturn(@PathVariable("merchantId") Long merchantId) {
        return successCreated();
    }

    @Override
    public Result<Boolean> findIsNoReasonReturnById(@RequestParam("merchantId") Long merchantId) {
        return successGet();
    }

    @Override
    public Result applyPhysicalStore(@PathVariable(value = "merchantId") Long merchantId, @ModelAttribute ApplyStoreParam param) {
        return successCreated();
    }

    @Override
    public Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long id) {
        MerchantStoreDTO dto = new MerchantStoreDTO();
        dto.setName("test");
        dto.setManageType(MerchantStoreTypeEnum.NORMAL_MERCHANT);
        dto.setRegionPath("44/4403/440303");
        return successGet(dto);
    }

    @Override
    public Result<String> getNameBymerchantId(@PathVariable("merchantId") Long merchantId) {
        return successGet();
    }

    @Override
    public Result<Long> getMerchantStoreId(@PathVariable("merchantId") Long merchantId) {
        return successGet();
    }

	@Override
	public Result<MerchantStoreAdInfoDTO> selectMerchantStoreAdInfo(@PathVariable("merchantId") Long merchantId) {
		return successGet();
	}

    @Override
    public Result updateKeywordsById(@PathVariable("id") Long id, @RequestParam("merchantId") Long merchantId, @RequestParam("keywords") String keywords) {
        return successCreated();
    }
}
