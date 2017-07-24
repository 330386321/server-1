package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.ad.dto.*;
import com.lawu.eshop.ad.param.*;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdExtendService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockAdExtendService extends BaseController implements AdExtendService {


    @Override
    public Result<Page<AdDTO>> selectListByMember(AdEgainParam adEgainParam) {
        return null;
    }

    @Override
    public Result<Page<AdDTO>> selectChoiceness(AdChoicenessParam adChoicenessParam) {
        return null;
    }

    @Override
    public Result<List<AdDTO>> selectAdTopList(AdPointParam adPointParam) {
        return null;
    }

    @Override
    public Result<Page<AdPraiseDTO>> selectAdPraiseList(AdPraiseParam adPraiseParam) {
        return null;
    }

    @Override
    public Result<AdPraiseDTO> selectAbPraiseById(Long id) {
        return null;
    }

    @Override
    public Result<PraisePointDTO> clickPraise(Long id) {
        return null;
    }

    @Override
    public Result<Page<AdFlatVideoDTO>> selectEgainAd(AdEgainParam adEgainParam) {
        return null;
    }

    @Override
    public Result<ClickAdPointDTO> clickAd(Long id, Long memberId, String num) {
        return null;
    }

    @Override
    public Result<Page<AdEgainQueryDTO>> selectEgain(AdEgainParam adEgainParam) {
        return null;
    }

    @Override
    public Result<List<AdPointDTO>> selectAdTop(AdPointForeignParam adPointParam) {
        return null;
    }

    @Override
    public Result<Page<ChoicenessAdDTO>> selectChoicenessAd(AdChoicenessParam adChoicenessParam) {
        return null;
    }
}
