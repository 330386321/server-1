package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdFlatVideoDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.param.AdChoicenessParam;
import com.lawu.eshop.ad.param.AdEgainParam;
import com.lawu.eshop.ad.param.AdPointParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

public interface AdExtendService {
	

	/**
	 * 查询广告
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
    Result<Page<AdDTO>> selectListByMember(AdEgainParam adEgainParam);
    
    /**
     * 今日精选
     * @param adChoicenessParam
     * @return
     */
    Result<Page<AdDTO>> selectChoiceness(AdChoicenessParam adChoicenessParam);
    
    /**
     * 积分榜，人气榜查询
     * @param adMemberParam
     * @return
     */
    Result<List<AdDTO>> selectAdTopList(AdPointParam adPointParam);
	
	/**
	 * E赞查询
	 * @param adPraiseParam
	 * @return
	 */
    Result<Page<AdPraiseDTO>> selectAdPraiseList(AdPraiseParam adPraiseParam);
    
    /**
     * E赞详情
     * @param id
     * @return
     */
    Result<AdPraiseDTO> selectAbPraiseById(Long id);
	
	/**
	 * 抢赞
	 * @param id
	 * @return
	 */
    Result<PraisePointDTO> clickPraise(Long id);
    
    /**
     * 精选广告
     * @param adEgainParam
     * @return
     */
    Result<Page<AdFlatVideoDTO>> selectEgainAd(AdEgainParam adEgainParam);
    
    /**
	 * 点击广告
	 * @param id
	 * @param memberId
	 * @return
	 */
    Result<ClickAdPointDTO> clickAd( Long id, Long memberId,String num);
}
