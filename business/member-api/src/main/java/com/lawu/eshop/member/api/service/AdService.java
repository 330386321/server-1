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
import com.lawu.eshop.ad.dto.AdEgainQueryDTO;
import com.lawu.eshop.ad.dto.AdPointDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.ChoicenessAdDTO;
import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.PointPoolDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.dto.RedPacketInfoDTO;
import com.lawu.eshop.ad.param.AdChoicenessInternalParam;
import com.lawu.eshop.ad.param.AdEgainInternalParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPointInternalParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdsolrFindParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "ad-srv", path = "ad/")
public interface AdService {
	

	/**
	 * 查询广告
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "selectListByMember")
    Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam,@RequestParam("memberId") Long memberId);
	
	
	 /**
	  * 单个查询广告
	  * @return
	  */
	@RequestMapping(method = RequestMethod.GET, value = "selectAbById/{id}")
	Result<AdDTO> selectAbById(@PathVariable("id") Long id,@RequestParam("memberId") Long memberId);
	
	/**
	 * E赞查询
	 * @param adPraiseParam
	 * @return
	 */
	@RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.GET)
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
	@RequestMapping(value = "clickPraise/{id}", method = RequestMethod.PUT)
    public Result<PraisePointDTO> clickPraise(@PathVariable("id") Long id,@RequestParam("memberId") Long memberId,@RequestParam("num") String num);
	
	/**
	 * 点击广告
	 * @param id
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "clickAd/{id}", method = RequestMethod.PUT)
    public Result<ClickAdPointDTO> clickAd(@PathVariable("id") Long id,@RequestParam("memberId") Long memberId,@RequestParam("num") String num);
	
	
	/**
     * 会员APP广告搜索
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "queryAdByTitle")
    Result<Page<AdSolrDTO>> queryAdByTitle(@ModelAttribute AdsolrFindParam adSolrParam);



    /**
     * 今日精选
     * @param adChoicenessParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "selectChoiceness")
	Result<Page<AdDTO>> selectChoiceness(@ModelAttribute AdMemberParam param);
    
    /**
	 * 领取红包
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "getRedPacket", method = RequestMethod.PUT)
    public Result<PraisePointDTO> getRedPacket(@RequestParam("merchantId")  Long  merchantId,@RequestParam("memberId")  Long  memberId,@RequestParam("memberNum") String memberNum);
	
	
	/**
	 * 获取红包相关信息
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "getRedPacketInfo/{merchantId}", method = RequestMethod.GET)
	public Result<RedPacketInfoDTO> getRedPacketInfo(@PathVariable("merchantId") Long merchantId) ;
	
	/**
	 * 判断红包是否领取完
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "isExistsRedPacket/{merchantId}", method = RequestMethod.GET)
	public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable("merchantId") Long merchantId);
	
	/**
	 * 分页查询E赚广告
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月18日
	 */
	@RequestMapping(value = "pageAdEgain/{memberId}", method = RequestMethod.PUT)
	Result<Page<AdEgainQueryDTO>> selectPageAdEgain(@PathVariable("memberId") Long memberId, @RequestBody AdEgainInternalParam param);
	
	/**
	 * 查询积分排行榜广告
	 * 
	 * @param param
	 *            查询参数
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月19日
	 */
	@RequestMapping(value = "adPoint", method = RequestMethod.PUT)
	Result<List<AdPointDTO>> selectAdPoint(@RequestBody AdPointInternalParam param);
	
	/**
	 * 分页查询精选推荐广告
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月19日
	 */
	@RequestMapping(value = "choiceness/{memberId}", method = RequestMethod.PUT)
	Result<Page<ChoicenessAdDTO>> selectChoiceness(@PathVariable("memberId") Long memberId, @RequestBody AdChoicenessInternalParam param);
}
