package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.product.dto.MemberProductStoreDTO;
import com.lawu.eshop.user.constants.ManageTypeEnum;
import com.lawu.eshop.user.dto.*;
import com.lawu.eshop.user.param.ShoppingOrderFindUserInfoParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Service
public class MockMerchantStoreService implements MerchantStoreService {

    @Override
    public Result<MerchantInfoForShoppingCartDTO> getMerchantInfoForShoppingCart(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result findIsNoReasonReturnById(@RequestParam("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<ShoppingOrderFindUserInfoDTO> shoppingOrderFindUserInfo(@RequestBody ShoppingOrderFindUserInfoParam param) {
        return null;
    }

    @Override
    public Result<StoreDetailDTO> getStoreDetailById(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<MerchantStoreDTO> selectMerchantStoreByMerchantId(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public MerchantStoreDTO findStoreNameAndImgByMerchantId(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<ShoppingStoreDetailDTO> getShoppingStoreDetailById(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<MerchantStoreDTO> getMerchantStoreById(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public Result<MemberProductStoreDTO> getMemberProductDetailStore(@RequestParam("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<ManageTypeEnum> getManageType(@RequestParam("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<List<PayOrderStoreInfoDTO>> getPayOrderStoreInfo(@RequestParam("merchantIds") List<Long> merchantIds) {
        return null;
    }

    @Override
    public Result<List<StoreSolrInfoDTO>> getMerchantStoreByIds(@RequestParam("merchantStoreIds") List<Long> merchantStoreIds) {
        return null;
    }

    @Override
    public Result<List<MerchantAdInfoDTO>> getAdMerchantStoreByIds(@RequestParam("merchantIds") List<Long> merchantIds) {
        return null;
    }

    @Override
    public MerchantStoreStatusDTO merchantStoreIsExist(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public String getLogoUrlByStoreId(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public String getStoreUrlByStoreId(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public PayOrderMerchantStoreInfoDTO getPayOrderDetailStoreInfo(@RequestParam("merchantId") Long merchantId) {
        return null;
    }
}
