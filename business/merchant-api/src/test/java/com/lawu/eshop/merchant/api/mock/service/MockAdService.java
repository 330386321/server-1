package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.dto.*;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.AdService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockAdService extends BaseController implements AdService {

    @Override
    public Result saveAd(@RequestBody AdSaveParam adSaveParam) {
        return successCreated();
    }

    @Override
    public Result<Page<AdMerchantDTO>> selectListByMerchant(@RequestBody AdMerchantParam adMerchantParam, @RequestParam("memberId") Long memberId) {
        return successCreated();
    }

    @Override
    public Result updateStatus(@PathVariable("id") Long id) {
        return successCreated();
    }

    @Override
    public Result remove(@PathVariable("id") Long id) {
        return successCreated();
    }

    @Override
    public Result<AdDTO> selectAbById(@PathVariable("id") Long id) {
        return successGet();
    }

    @Override
    public Result<AdMerchantDetailDTO> selectById(@PathVariable("id") Long id) {
        AdMerchantDetailDTO dto = new AdMerchantDetailDTO();
        dto.setTotalPoint(BigDecimal.valueOf(100));
        dto.setTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
        dto.setPutWayEnum(PutWayEnum.PUT_WAY_AREAS);
        return successGet(dto);
    }

    @Override
    public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable("merchantId") Long merchantId) {
        IsExistsRedPacketDTO dto = new IsExistsRedPacketDTO();
        dto.setIsExistsRedPacket(true);
        return successGet(dto);
    }

    @Override
    public Result batchDeleteAd(@RequestParam("ids") List<Long> ids, @RequestParam("merchantId") Long merchantId) {
        return successDelete();
    }

    @Override
    public Result<AdDetailDTO> selectDetail(@PathVariable("id") Long id) {
        return successGet();
    }

    @Override
    public Result<IsMyDateDTO> isMyData(@PathVariable("id") Long id, @RequestParam("merchantId") Long merchantId) {
        IsMyDateDTO dto = new IsMyDateDTO();
        dto.setFlag(true);
        return successGet(dto);
    }
}