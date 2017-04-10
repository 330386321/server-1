package com.lawu.eshop.ad.srv.service;

import java.util.List;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.framework.core.page.Page;

/**
 * E赚接口
 * @author zhangrc
 * @date 2017/4/6
 *
 */
public interface AdService {
	
	/**
	 * 商家发布E赚
	 * @param adParam
	 * @param merchantId
	 * @return
	 */
	Integer saveAd(AdParam adParam,Long merchantId,String mediaUrl);
	
	/**
	 * 商家E赚查询
	 * @param adMerchantParam
	 * @param merchantId
	 * @return
	 */
	Page<AdBO> selectListByMerchant(AdMerchantParam adMerchantParam,Long merchantId);
	
	/**
	 * 运营平台(商家)对E赚的管理(下架)
	 * @param statusEnum
	 * @return
	 */
	Integer updateStatus(Long id);
	
	/**
	 * 运营平台(商家)对E赚的管理(删除)
	 * @param id
	 * @return
	 */
	Integer remove(Long id);
	
	/**
	 * 审核视频广告
	 * @param id
	 * @return
	 */
	Integer auditVideo(Long id);
	
	/**
	 * 运营平台对E赚的查询
	 * @param adMerchantParam
	 * @return
	 */
	Page<AdBO> selectListByPlatForm(AdMerchantParam adMerchantParam);
	
	/**
	 * 会员对E赚的观看
	 * @param adMerchantParam
	 * @return
	 */
	Page<AdBO> selectListByMember(AdMerchantParam adMerchantParam,Long memberId);
	
	/**
	 * 查看E赚详情
	 * @param id
	 * @return
	 */
	AdBO selectAbById(Long id);
	
	/**
	 * 人气榜
	 * @param userNum
	 * @return
	 */
	List<AdBO> selectMoods(Long memberId);
	
	/**
	 * 积分榜
	 * @param userNum
	 * @return
	 */
	List<AdBO> selectPoint(Long memberId);

}