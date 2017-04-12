package com.lawu.eshop.member.api.service;

import java.util.List;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
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
    Result<Page<AdDTO>> selectListByMember(AdMemberParam adMemberParam);
    
    /**
     * 积分榜，人气榜查询
     * @param adMemberParam
     * @return
     */
    Result<List<AdDTO>> selectListPointTotle(AdMemberParam adMemberParam);
	
	/**
	 * E赞查询
	 * @param adPraiseParam
	 * @return
	 */
    Result<Page<AdPraiseDTO>> selectPraiseListByMember(AdPraiseParam adPraiseParam);
    
    /**
     * E赞详情
     * @param id
     * @return
     */
    Result<AdPraiseDTO> selectAbPraiseById(Long id);
	
	

}