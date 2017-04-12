package com.lawu.eshop.ad.srv.service;

import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
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
	Integer saveAd(AdParam adParam,Long merchantId,String mediaUrl,Integer count);
	
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
	Page<AdBO> selectListByMember(AdMemberParam adMemberParam);
	
	/**
	 * 查看E赚详情
	 * @param id
	 * @return
	 */
	AdBO selectAbById(Long id);
	
	
	/**
	 * 会员对E赚的观看
	 * @param adMerchantParam
	 * @return
	 */
	Page<AdBO> selectPraiseListByMember(AdPraiseParam adPraiseParam);
	
	/**
	 * 抢赞
	 * @param id
	 * @return
	 */
	Integer clickPraise(Long id);
	

}
