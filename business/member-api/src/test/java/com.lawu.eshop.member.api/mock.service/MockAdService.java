package com.lawu.eshop.member.api.mock.service;/**
 * Created by ${Yangqh} on 2017/7/24.
 */

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.dto.*;
import com.lawu.eshop.ad.param.*;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p> </p>
 *
 * @author yangqh
 * @date 2017/7/24 16:39
 */
@Service
public class MockAdService extends BaseController implements AdService{
    @Override
    public Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam, @RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<AdDTO> selectAbById(Long id, Long memberId) {
        AdDTO addto = new AdDTO();
        addto.setId(1L);
        addto.setViewCount(1);
        addto.setContent("content");
        addto.setIsFavorite(false);
        addto.setTitle("title");
        addto.setMediaUrl("/mediaurl/1.jpg");
        addto.setVideoImgUrl("/video/1.mp4");
        addto.setMerchantId(1L);
        addto.setStatusEnum(AdStatusEnum.AD_STATUS_PUTING);
        addto.setIsClickAd(false);
        return successCreated(addto);
    }

    @Override
    public Result<Page<AdDTO>> selectPraiseListByMember(@RequestBody AdPraiseParam adPraiseParam, @RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<List<PointPoolDTO>> selectMemberList(@RequestParam("id") Long id) {
        return null;
    }

    @Override
    public Result<PraisePointDTO> clickPraise(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId, @RequestParam("num") String num) {
        return null;
    }

    @Override
    public Result<ClickAdPointDTO> clickAd(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId, @RequestParam("num") String num) {
        return null;
    }

    @Override
    public Result<Page<AdSolrDTO>> queryAdByTitle(@ModelAttribute AdsolrFindParam adSolrParam) {
        return null;
    }

    @Override
    public Result<Page<AdDTO>> selectChoiceness(@ModelAttribute AdMemberParam param) {
        return null;
    }

    @Override
    public Result<PraisePointDTO> getRedPacket(@RequestParam("merchantId") Long merchantId, @RequestParam("memberId") Long memberId, @RequestParam("memberNum") String memberNum) {
        return null;
    }

    @Override
    public Result<RedPacketInfoDTO> getRedPacketInfo(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<Page<AdEgainQueryDTO>> selectPageAdEgain(@PathVariable("memberId") Long memberId, @RequestBody AdEgainInternalParam param) {
        return null;
    }

    @Override
    public Result<List<AdPointDTO>> selectAdPoint(@RequestBody AdPointInternalParam param) {
        return null;
    }

    @Override
    public Result<Page<ChoicenessAdDTO>> selectChoiceness(@PathVariable("memberId") Long memberId, @RequestBody AdChoicenessInternalParam param) {
        return null;
    }
}
