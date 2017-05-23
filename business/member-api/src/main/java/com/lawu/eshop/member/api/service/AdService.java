package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdLexiconDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.PointPoolDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.dto.RedPacketInfoDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdsolrFindParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "ad-srv")
public interface AdService {
	

	/**
	 * 查询广告
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "ad/selectListByMember")
    Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam,@RequestParam("memberId") Long memberId);
	
	
	 /**
	  * 单个查询广告
	  * @return
	  */
	@RequestMapping(method = RequestMethod.GET, value = "ad/selectAbById/{id}")
	Result<AdDTO> selectAbById(@PathVariable("id") Long id,@RequestParam("memberId") Long memberId);
	
	/**
	 * E赞查询
	 * @param adPraiseParam
	 * @return
	 */
	@RequestMapping(value = "ad/selectPraiseListByMember", method = RequestMethod.GET)
    public Result<Page<AdDTO>> selectPraiseListByMember(@RequestBody AdPraiseParam adPraiseParam,@RequestParam("memberId") Long memberId);
	
	/**
	 * E赞前三名
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "pointPool/selectMemberList", method = RequestMethod.GET)
    public Result<List<PointPoolDTO>> selectMemberList(@RequestParam("id") Long id);
	
	/**
	 * 抢赞
	 * @param id
	 * @param memberId
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "ad/clickPraise/{id}", method = RequestMethod.PUT)
    public Result<PraisePointDTO> clickPraise(@PathVariable("id") Long id,@RequestParam("memberId") Long memberId,@RequestParam("num") String num);
	
	/**
	 * 点击广告
	 * @param id
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "ad/clickAd/{id}", method = RequestMethod.PUT)
    public Result<ClickAdPointDTO> clickAd(@PathVariable("id") Long id,@RequestParam("memberId") Long memberId,@RequestParam("num") String num);
	
	
	/**
	 * 根据不同的位置查询不同的广告
	 * @param positionEnum
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "adLexicon/selectList")
	Result<List<AdLexiconDTO>> selectList(@RequestParam("adId") Long  adId);
	
	/**
     * 会员APP广告搜索
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "ad/queryAdByTitle")
    Result<Page<AdSolrDTO>> queryAdByTitle(@ModelAttribute AdsolrFindParam adSolrParam);



    /**
     * 今日精选
     * @param adChoicenessParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "ad/selectChoiceness")
	Result<Page<AdDTO>> selectChoiceness(AdMemberParam param);
    
    /**
	 * 领取红包
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "ad/getRedPacket", method = RequestMethod.PUT)
    public Result<PraisePointDTO> getRedPacket(@RequestParam("merchantId")  Long  merchantId,@RequestParam("memberId")  Long  memberId,@RequestParam("memberNum") String memberNum);
	
	/**
	 * 点击广告获取的积分
	 * @param memberId
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ad/getClickAdPoint/{id}", method = RequestMethod.GET)
    public Result<ClickAdPointDTO> getClickAdPoint(@RequestParam("memberId") Long memberId,@PathVariable("id") Long id);
	
	/**
	 * 获取红包相关信息
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "ad/getRedPacketInfo/{merchantId}", method = RequestMethod.GET)
	public Result<RedPacketInfoDTO> getRedPacketInfo(@PathVariable("merchantId") Long merchantId) ;
	
	/**
	 * 判断红包是否领取完
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "ad/isExistsRedPacket/{merchantId}", method = RequestMethod.GET)
	public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable("merchantId") Long merchantId);
}
