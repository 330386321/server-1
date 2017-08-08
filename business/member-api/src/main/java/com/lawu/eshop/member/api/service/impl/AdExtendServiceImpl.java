package com.lawu.eshop.member.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdPage;
import com.lawu.eshop.ad.constants.AdPraiseConfig;
import com.lawu.eshop.ad.constants.AdPraiseWords;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdEgainQueryDTO;
import com.lawu.eshop.ad.dto.AdFlatVideoDTO;
import com.lawu.eshop.ad.dto.AdLexiconDTO;
import com.lawu.eshop.ad.dto.AdPointDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.ChoicenessAdDTO;
import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.param.AdChoicenessInternalParam;
import com.lawu.eshop.ad.param.AdChoicenessParam;
import com.lawu.eshop.ad.param.AdEgainInternalParam;
import com.lawu.eshop.ad.param.AdEgainParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPointForeignParam;
import com.lawu.eshop.ad.param.AdPointInternalParam;
import com.lawu.eshop.ad.param.AdPointParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.AdExtendService;
import com.lawu.eshop.member.api.service.AdLexiconService;
import com.lawu.eshop.member.api.service.AdService;
import com.lawu.eshop.member.api.service.FansMerchantService;
import com.lawu.eshop.member.api.service.FavoriteAdService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantProfileService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.PropertyInfoDataService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PointDetailQueryData1Param;
import com.lawu.eshop.user.constants.ManageTypeEnum;
import com.lawu.eshop.user.dto.AdQueryMemberInfoDTO;
import com.lawu.eshop.user.dto.MerchantAdInfoDTO;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.utils.DistanceUtil;

@Service
public class AdExtendServiceImpl extends BaseController implements AdExtendService {

	@Autowired
	private AdService adService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MerchantStoreService merchantStoreService;

	@Autowired
	private FansMerchantService fansMerchantService;

	@Autowired
	private MerchantProfileService merchantProfileService;

	@Autowired
	private MemberApiConfig memberApiConfig;

	@Autowired
	private FavoriteAdService favoriteAdService;

	@Autowired
	private PropertyInfoDataService propertyInfoDataService;

	@Autowired
	private AdLexiconService adLexiconService;

	private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

	private ExecutorService service = new ThreadPoolExecutor(AdPraiseConfig.CORE_POOL_SIZE, AdPraiseConfig.MAXIMUM_POLL_SIZE, AdPraiseConfig.KEEP_ALIVE_TIME, TimeUnit.DAYS, queue);

	private static Logger logger = LoggerFactory.getLogger(AdExtendServiceImpl.class);

	@Override
	public Result<Page<AdDTO>> selectListByMember(AdEgainParam adEgainParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		AdMemberParam param = new AdMemberParam();
		param.setCurrentPage(adEgainParam.getCurrentPage());
		param.setPageSize(adEgainParam.getPageSize());
		param.setTypeEnum(AdTypeEnum.getEnum(adEgainParam.getTypeEnum().getVal()));
		param.setLatitude(adEgainParam.getLatitude());
		param.setLongitude(adEgainParam.getLongitude());
		Result<Page<AdDTO>> pageDTOS = adService.selectListByMember(param, memberId);
		List<AdDTO> list = pageDTOS.getModel().getRecords();
		List<AdDTO> newList = adFilter(param, list, memberId);
		AdPage<AdDTO> adpage = new AdPage<>();
		List<AdDTO> screenList = adpage.page(newList, param.getPageSize(), param.getCurrentPage());
		for (AdDTO adDTO : screenList) {
			Result<Boolean> resultFavoriteAd = favoriteAdService.isFavoriteAd(adDTO.getId(), memberId);
			if (isSuccess(resultFavoriteAd)) {
				adDTO.setIsFavorite(resultFavoriteAd.getModel());
			}
			Result<MerchantStoreDTO> merchantStoreDTO = merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
			Result<ManageTypeEnum> manageType = merchantStoreService.getManageType(adDTO.getMerchantId());
			if (merchantStoreDTO.getModel() != null) {
				adDTO.setName(merchantStoreDTO.getModel().getName());
				adDTO.setMerchantStoreId(merchantStoreDTO.getModel().getMerchantStoreId());
				adDTO.setLogoUrl(merchantStoreDTO.getModel().getLogoUrl());
			} else {
				adDTO.setName("E店商家");
				adDTO.setLogoUrl(memberApiConfig.getDefaultHeadimg());
			}
			if (manageType.getModel() != null) {
				adDTO.setManageTypeEnum(com.lawu.eshop.ad.constants.ManageTypeEnum.getEnum(manageType.getModel().val));
			}
			Date date = new Date();
			Long time = adDTO.getBeginTime().getTime() - date.getTime();
			if (time > 0) {
				adDTO.setNeedBeginTime(time);
			} else {
				adDTO.setNeedBeginTime(0l);
			}
		}
		Page<AdDTO> newPage = new Page<>();
		newPage.setCurrentPage(pageDTOS.getModel().getCurrentPage());
		newPage.setTotalCount(newList.size());
		newPage.setRecords(screenList);
		return successGet(newPage);
	}

	@Deprecated
	@Override
	public Result<List<AdDTO>> selectAdTopList(AdPointParam adPointParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		AdMemberParam param = new AdMemberParam();
		param.setCurrentPage(adPointParam.getCurrentPage());
		param.setPageSize(adPointParam.getPageSize());
		param.setOrderTypeEnum(adPointParam.getOrderTypeEnum());
		param.setLatitude(adPointParam.getLatitude());
		param.setLongitude(adPointParam.getLongitude());
		Result<Page<AdDTO>> pageDTOS = adService.selectListByMember(param, memberId);
		List<AdDTO> newList = adFilter(param, pageDTOS.getModel().getRecords(), memberId);
		if (newList.size() > 9) {
			newList = newList.subList(0, 9);
		} else {
			newList = newList.subList(0, newList.size());
		}
		List<Long> merchantIds = new ArrayList<>();
		for (AdDTO adDTO : newList) {
			merchantIds.add(adDTO.getMerchantId());
		}
		List<Long> newMerchantIdsList = new ArrayList(new TreeSet(merchantIds));
		if (newMerchantIdsList.isEmpty()) {
			return successGet(newList);
		}
		Result<List<MerchantAdInfoDTO>> merchantResult = merchantStoreService.getAdMerchantStoreByIds(newMerchantIdsList);
		if (!isSuccess(merchantResult)) {
			return successCreated(merchantResult.getRet());
		}
		List<MerchantAdInfoDTO> merchantList = merchantResult.getModel();
		for (AdDTO adDTO : newList) {
			for (MerchantAdInfoDTO merchantAdInfoDTO : merchantList) {
				if (adDTO.getMerchantId().longValue() == merchantAdInfoDTO.getMerchantId().longValue()) {
					Result<Boolean> resultFavoriteAd = favoriteAdService.isFavoriteAd(adDTO.getId(), memberId);
					if (isSuccess(resultFavoriteAd)) {
						adDTO.setIsFavorite(resultFavoriteAd.getModel());
					}
					adDTO.setMerchantStoreId(merchantAdInfoDTO.getMerchantStoreId());
					if (merchantAdInfoDTO.getName() != null) {
						adDTO.setName(merchantAdInfoDTO.getName());
					} else {
						adDTO.setName("E店商家");
					}
					if (merchantAdInfoDTO.getPath() != null) {
						adDTO.setLogoUrl(merchantAdInfoDTO.getPath());
					} else {
						adDTO.setLogoUrl(memberApiConfig.getDefaultHeadimg());
					}
					if (merchantAdInfoDTO.getManageTypeEnum() != null) {
						adDTO.setManageTypeEnum(com.lawu.eshop.ad.constants.ManageTypeEnum.getEnum(merchantAdInfoDTO.getManageTypeEnum().val));
					}
				}
			}
		}
		return successGet(newList);
	}

	@Override
	public Result<Page<AdPraiseDTO>> selectAdPraiseList(AdPraiseParam adPraiseParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		Result<Page<AdDTO>> pageDTOS = adService.selectPraiseListByMember(adPraiseParam, memberId);
		List<AdDTO> list = pageDTOS.getModel().getRecords();
		List<AdDTO> newList = adFilter(null, list, memberId);
		AdPage<AdDTO> adpage = new AdPage<>();
		List<AdDTO> screenList = adpage.page(newList, adPraiseParam.getPageSize(), adPraiseParam.getCurrentPage());
		List<AdPraiseDTO> adPraiseDTOS = new ArrayList<>();
		List<Long> merchantIds = new ArrayList<>();
		for (AdDTO adDTO : screenList) {
			merchantIds.add(adDTO.getMerchantId());
		}
		List<Long> newMerchantIdsList = new ArrayList(new TreeSet(merchantIds));
		if (!newMerchantIdsList.isEmpty()) {
			Result<List<MerchantAdInfoDTO>> merchantResult = merchantStoreService.getAdMerchantStoreByIds(newMerchantIdsList);
			if (!isSuccess(merchantResult)) {
				return successCreated(merchantResult.getRet());
			}
			List<MerchantAdInfoDTO> merchantList = merchantResult.getModel();
			for (AdDTO adDTO : screenList) {
				for (MerchantAdInfoDTO merchantAdInfoDTO : merchantList) {
					if (adDTO.getMerchantId().longValue() == merchantAdInfoDTO.getMerchantId().longValue()) {
						AdPraiseDTO praise = new AdPraiseDTO();
						praise.setId(adDTO.getId());
						praise.setTitle(adDTO.getTitle());
						praise.setBeginTime(adDTO.getBeginTime());
						praise.setTotalPoint(adDTO.getTotalPoint());
						if (adDTO.getNumber() == null) {
							praise.setCount(0);
						} else {
							praise.setCount(adDTO.getNumber());
						}
						praise.setNeedBeginTime(adDTO.getNeedBeginTime());
						praise.setMediaUrl(adDTO.getMediaUrl());
						praise.setIsPraise(adDTO.getIsPraise());
						praise.setMerchantId(adDTO.getMerchantId());
						praise.setCount(adDTO.getNumber());
						Result<Boolean> resultFavoriteAd = favoriteAdService.isFavoriteAd(adDTO.getId(), memberId);
						if (isSuccess(resultFavoriteAd)) {
							praise.setIsFavorite(resultFavoriteAd.getModel());
						}
						praise.setMerchantStoreId(merchantAdInfoDTO.getMerchantStoreId());
						if (merchantAdInfoDTO.getName() != null) {
							praise.setName(merchantAdInfoDTO.getName());
						} else {
							praise.setName("E店商家");
						}
						if (merchantAdInfoDTO.getPath() != null) {
							praise.setLogoUrl(merchantAdInfoDTO.getPath());
						} else {
							praise.setLogoUrl(memberApiConfig.getDefaultHeadimg());
						}
						if (merchantAdInfoDTO.getManageTypeEnum() != null) {
							praise.setManageTypeEnum(com.lawu.eshop.ad.constants.ManageTypeEnum.getEnum(merchantAdInfoDTO.getManageTypeEnum().val));
						}
						adPraiseDTOS.add(praise);
					}
				}
			}
		}

		Page<AdPraiseDTO> newPage = new Page<>();
		newPage.setCurrentPage(pageDTOS.getModel().getCurrentPage());
		newPage.setTotalCount(newList.size());
		newPage.setRecords(adPraiseDTOS);
		return successGet(newPage);
	}

	@Override
	public Result<AdPraiseDTO> selectAbPraiseById(Long id) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		Result<AdPraiseDTO> resultAd = adService.selectAdPraiseById(id, memberId);
		
		AdPraiseDTO praise = resultAd.getModel();
		if (StringUtils.isEmpty(praise.getName())) {
			praise.setName("E店商家");
		} else if(StringUtils.isEmpty(praise.getLogoUrl())) {
			praise.setLogoUrl(memberApiConfig.getDefaultHeadimg());
		}
		PointDetailQueryData1Param param = new PointDetailQueryData1Param();
		param.setBizId(id.toString());
		param.setPointType(MemberTransactionTypeEnum.PRAISE_AD.getValue());
		param.setUserNum(userNum);
		Result<Integer> result = propertyInfoDataService.getPointDetailByUserNumAndPointTypeAndBizId(param);
		if (isSuccess(result)) {
			if (result.getModel() == 1)
				praise.setIsDoHanlderMinusPoint(true);
			else
				praise.setIsDoHanlderMinusPoint(false);
		}
		praise.setClickPraiseAdTimes(memberApiConfig.getClickPraiseAdTimes());
		praise.setPraiseProb(memberApiConfig.getClickPraiseProb());
		List<String> list = new ArrayList<>();
		list.add(AdPraiseWords.WORD_A);
		list.add(AdPraiseWords.WORD_B);
		list.add(AdPraiseWords.WORD_C);
		list.add(AdPraiseWords.WORD_D);
		praise.setWords(list);
		return successGet(praise);
	}

	/**
	 * 公用方法广告过滤
	 *
	 * @param list
	 * @param memberId
	 * @return
	 */
	public List<AdDTO> adFilter(AdMemberParam adMemberParam, List<AdDTO> list, Long memberId) {
		Result<UserDTO> memberDTO = memberService.findMemberInfo(memberId);
		String memberPath = memberDTO.getModel().getRegionPath();
		List<AdDTO> newList = new ArrayList<>();
		for (AdDTO adDTO : list) {
			if (adDTO.getPutWayEnum().val == 1) { // 区域
				if (adDTO.getAreas() == null || adDTO.getAreas() == "") {
					newList.add(adDTO);
				} else {
					if (memberPath != null) {
						String[] memberPaths = memberPath.split("/");
						String[] path = adDTO.getAreas().split(",");
						for (String s : path) {
							for (String mp : memberPaths) {
								if (s.equals(mp)) {
									newList.add(adDTO);
								}
								continue;
							}
						}
					}
				}

			} else if (adDTO.getPutWayEnum().val == 2) {
				// 获取商家粉丝，判断当前用户是否属于商家粉丝
				Result<Boolean> rs = fansMerchantService.isFansMerchant(adDTO.getMerchantId(), memberId);
				if (rs.getModel())
					newList.add(adDTO);
			} else {
				if (adMemberParam.getLongitude() == null || adMemberParam.getLatitude() == null) {
					continue;
				} else {
					Result<MerchantStoreDTO> rs = merchantStoreService.selectMerchantStoreByMId(adDTO.getMerchantId());
					MerchantStoreDTO dto = rs.getModel();
					if (dto != null) {
						int distance = DistanceUtil.getDistance(adMemberParam.getLongitude(), adMemberParam.getLatitude(), dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue());
						if (adDTO.getRadius() != null && adDTO.getRadius() > distance / 1000) {
							newList.add(adDTO);
						}
					}
				}
			}

		}
		return newList;

	}

	@Override
	public Result<PraisePointDTO> clickPraise(Long id) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		String num = UserUtil.getCurrentUserNum(getRequest());
		Future<Result<PraisePointDTO>> future = null;
		try {
			Random random = new Random();
			Integer r = random.nextInt(memberApiConfig.getClickPraiseSumProb()) % (memberApiConfig.getClickPraiseSumProb() + 1);
			if (r > 0 && r < memberApiConfig.getClickPraiseProb()) {
				future = service.submit(new AdClickPraiseThread(adService, id, memberId, num));
			} else {
				PraisePointDTO dto = new PraisePointDTO();
				dto.setPoint(new BigDecimal(0));
				return successCreated(dto);
			}

		} catch (RejectedExecutionException e) {
			// 队列已满，直接失败
			return successCreated(ResultCode.FAIL);
		}
		try {
			Result<PraisePointDTO> rs = future.get();
			return rs;

		} catch (InterruptedException | ExecutionException e) {
			logger.error("抢赞失败", e);
		}
		return null;
	}

	@Deprecated
	@Override
	public Result<Page<AdDTO>> selectChoiceness(AdChoicenessParam adChoicenessParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());

		AdMemberParam param = new AdMemberParam();
		param.setCurrentPage(adChoicenessParam.getCurrentPage());
		param.setPageSize(adChoicenessParam.getPageSize());
		param.setLatitude(adChoicenessParam.getLatitude());
		param.setLongitude(adChoicenessParam.getLongitude());
		Result<Page<AdDTO>> pageDTOS = adService.selectChoiceness(param);
		List<AdDTO> newList = adFilter(param, pageDTOS.getModel().getRecords(), memberId);
		AdPage<AdDTO> adpage = new AdPage<>();
		List<AdDTO> screenList = adpage.page(newList, param.getPageSize(), param.getCurrentPage());

		List<Long> merchantIds = new ArrayList<>();
		for (AdDTO adDTO : screenList) {
			merchantIds.add(adDTO.getMerchantId());
		}
		List<Long> newMerchantIdsList = new ArrayList(new TreeSet(merchantIds));
		if (!newMerchantIdsList.isEmpty()) {
			Result<List<MerchantAdInfoDTO>> merchantResult = merchantStoreService.getAdMerchantStoreByIds(newMerchantIdsList);

			if (!isSuccess(merchantResult)) {
				return successCreated(merchantResult.getRet());
			}

			List<MerchantAdInfoDTO> merchantList = merchantResult.getModel();
			for (AdDTO adDTO : screenList) {
				for (MerchantAdInfoDTO merchantAdInfoDTO : merchantList) {

					if (adDTO.getMerchantId().longValue() == merchantAdInfoDTO.getMerchantId().longValue()) {
						Result<Boolean> resultFavoriteAd = favoriteAdService.isFavoriteAd(adDTO.getId(), memberId);
						if (isSuccess(resultFavoriteAd)) {
							adDTO.setIsFavorite(resultFavoriteAd.getModel());
						}
						adDTO.setMerchantStoreId(merchantAdInfoDTO.getMerchantStoreId());
						if (merchantAdInfoDTO.getName() != null) {
							adDTO.setName(merchantAdInfoDTO.getName());
						} else {
							adDTO.setName("E店商家");
						}
						if (merchantAdInfoDTO.getPath() != null) {
							adDTO.setLogoUrl(merchantAdInfoDTO.getPath());
						} else {
							adDTO.setLogoUrl(memberApiConfig.getDefaultHeadimg());
						}
						if (merchantAdInfoDTO.getManageTypeEnum() != null) {
							adDTO.setManageTypeEnum(com.lawu.eshop.ad.constants.ManageTypeEnum.getEnum(merchantAdInfoDTO.getManageTypeEnum().val));
						}
			     		adDTO.setNeedBeginTime(adDTO.getNeedBeginTime());
					}
				}
			}

		}
		Page<AdDTO> newPage = new Page<>();
		newPage.setCurrentPage(adChoicenessParam.getCurrentPage());
		newPage.setTotalCount(newList.size());
		newPage.setRecords(screenList);

		return successGet(newPage);
	}

	@Deprecated
	@Override
	public Result<Page<AdFlatVideoDTO>> selectEgainAd(AdEgainParam adEgainParam) {

		Long memberId = UserUtil.getCurrentUserId(getRequest());

		AdMemberParam param = new AdMemberParam();
		param.setCurrentPage(adEgainParam.getCurrentPage());
		param.setPageSize(adEgainParam.getPageSize());
		param.setTypeEnum(AdTypeEnum.getEnum(adEgainParam.getTypeEnum().getVal()));
		param.setLatitude(adEgainParam.getLatitude());
		param.setLongitude(adEgainParam.getLongitude());

		Result<Page<AdDTO>> pageDTOS = adService.selectListByMember(param, memberId);

		List<AdDTO> newList = adFilter(param, pageDTOS.getModel().getRecords(), memberId);

		AdPage<AdDTO> adpage = new AdPage<>();
		List<AdDTO> screenList = adpage.page(newList, param.getPageSize(), param.getCurrentPage());

		List<AdFlatVideoDTO> egainList = new ArrayList<>();
		List<Long> merchantIds = new ArrayList<>();
		for (AdDTO adDTO : screenList) {
			merchantIds.add(adDTO.getMerchantId());
		}
		List<Long> newMerchantIdsList = new ArrayList(new TreeSet(merchantIds));
		if (!newMerchantIdsList.isEmpty()) {
			Result<List<MerchantAdInfoDTO>> merchantResult = merchantStoreService.getAdMerchantStoreByIds(newMerchantIdsList);
			if (!isSuccess(merchantResult)) {
				return successCreated(merchantResult.getRet());
			}
			List<MerchantAdInfoDTO> merchantList = merchantResult.getModel();
			for (AdDTO adDTO : screenList) {
				AdFlatVideoDTO adFlatVideoDTO = new AdFlatVideoDTO();
				adFlatVideoDTO.setId(adDTO.getId());
				adFlatVideoDTO.setContent(adDTO.getContent());
				adFlatVideoDTO.setGmtCreate(adDTO.getGmtCreate());
				adFlatVideoDTO.setMediaUrl(adDTO.getMediaUrl());
				adFlatVideoDTO.setVideoImgUrl(adDTO.getVideoImgUrl());
				adFlatVideoDTO.setMerchantId(adDTO.getMerchantId());
				adFlatVideoDTO.setTitle(adDTO.getTitle());
				adFlatVideoDTO.setPutWayEnum(adDTO.getPutWayEnum());
				adFlatVideoDTO.setStatusEnum(adDTO.getStatusEnum());
				adFlatVideoDTO.setTypeEnum(adDTO.getTypeEnum());
				adFlatVideoDTO.setBeginTime(adDTO.getBeginTime());
				adFlatVideoDTO.setViewCount(adDTO.getViewCount());
				adFlatVideoDTO.setVideoImgUrl(adDTO.getVideoImgUrl());
				Result<Boolean> resultFavoriteAd = favoriteAdService.isFavoriteAd(adDTO.getId(), memberId);
				if (isSuccess(resultFavoriteAd)) {
					adFlatVideoDTO.setIsFavorite(resultFavoriteAd.getModel());
				}
				for (MerchantAdInfoDTO merchantAdInfoDTO : merchantList) {
					if (adDTO.getMerchantId().longValue() == merchantAdInfoDTO.getMerchantId().longValue()) {
						adDTO.setMerchantStoreId(merchantAdInfoDTO.getMerchantStoreId());
						if (merchantAdInfoDTO.getName() != null) {
							adFlatVideoDTO.setName(merchantAdInfoDTO.getName());
						} else {
							adFlatVideoDTO.setName("E店商家");
						}
						if (merchantAdInfoDTO.getPath() != null) {
							adFlatVideoDTO.setLogoUrl(merchantAdInfoDTO.getPath());
						} else {
							adFlatVideoDTO.setLogoUrl(memberApiConfig.getDefaultHeadimg());
						}
						if (merchantAdInfoDTO.getManageTypeEnum() != null) {
							adFlatVideoDTO.setManageTypeEnum(com.lawu.eshop.ad.constants.ManageTypeEnum.getEnum(merchantAdInfoDTO.getManageTypeEnum().val));
						}
					}
				}
				// 广告词
				Result<List<AdLexiconDTO>> adLexiconDTOS = adLexiconService.selectList(adDTO.getId());
				if (isSuccess(adLexiconDTOS)) {
					adFlatVideoDTO.setLexiconList(adLexiconDTOS.getModel());
				}
				Result<MerchantProfileDTO> mpRs = merchantProfileService.getMerchantProfile(adDTO.getMerchantId());
				if (isSuccess(mpRs)) {
					adFlatVideoDTO.setJdUrl(mpRs.getModel().getJdUrl());
					adFlatVideoDTO.setTaobaoUrl(mpRs.getModel().getTaobaoUrl());
					adFlatVideoDTO.setTmallUrl(mpRs.getModel().getTmallUrl());
					adFlatVideoDTO.setWebsiteUrl(mpRs.getModel().getWebsiteUrl());
				}
				egainList.add(adFlatVideoDTO);
			}

		}
		Page<AdFlatVideoDTO> newPage = new Page<>();
		newPage.setCurrentPage(pageDTOS.getModel().getCurrentPage());
		newPage.setTotalCount(newList.size());
		newPage.setRecords(egainList);
		return successGet(newPage);
	}

	@Override
	public Result<ClickAdPointDTO> clickAd(Long id, Long memberId, String num) {

		Future<Result<ClickAdPointDTO>> future = null;
		try {

			future = service.submit(new AdClickThread(adService, id, memberId, num));

		} catch (RejectedExecutionException e) {
			// 队列已满，直接失败
			return successCreated(ResultCode.FAIL);
		}
		try {
			Result<ClickAdPointDTO> rs = future.get();
			if (!isSuccess(rs)) {
				return successCreated(rs.getRet());
			}
			return rs;

		} catch (InterruptedException | ExecutionException e) {
			logger.error("点广告失败", e);
		}
		return null;
	}

	/**
	 * 查询E赚广告
	 *
	 * @param adEgainParam
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月18日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result<Page<AdEgainQueryDTO>> selectEgain(AdEgainParam adEgainParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
		AdEgainInternalParam adEgainInternalParam = new AdEgainInternalParam();
		adEgainInternalParam.setCurrentPage(adEgainParam.getCurrentPage());
		adEgainInternalParam.setLatitude(adEgainParam.getLatitude());
		adEgainInternalParam.setLongitude(adEgainParam.getLongitude());
		adEgainInternalParam.setPageSize(adEgainParam.getPageSize());
		adEgainInternalParam.setTypeEnum(adEgainParam.getTypeEnum());
		adEgainInternalParam.setMerchantIds(adQueryMemberInfoDTO.getFansList());
		if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
			adEgainInternalParam.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
		Result<Page<AdEgainQueryDTO>> result = adService.selectPageAdEgain(memberId, adEgainInternalParam);
		return successGet(result);
	}

    /**
     * 积分榜，人气榜查询
     *
     * @param adPointParam
     * @return
     * @author jiangxinjun
     * @date 2017年7月19日
     */
	@SuppressWarnings("unchecked")
	@Override
	public Result<List<AdPointDTO>> selectAdTop(AdPointForeignParam adPointParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
		AdPointInternalParam adPointInternalParam = new AdPointInternalParam();
		adPointInternalParam.setLatitude(adPointParam.getLatitude());
		adPointInternalParam.setLongitude(adPointParam.getLongitude());
		adPointInternalParam.setOrderTypeEnum(adPointParam.getOrderTypeEnum());
		adPointInternalParam.setMerchantIds(adQueryMemberInfoDTO.getFansList());
		if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
			adPointInternalParam.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
		Result<List<AdPointDTO>> result = adService.selectAdPoint(adPointInternalParam);
		return successGet(result);
	}

    /**
     * 查询精品推荐广告列表
     *
     * @param adChoicenessParam
     * @return
     * @author jiangxinjun
     * @date 2017年7月19日
     */
	@SuppressWarnings("unchecked")
	@Override
	public Result<Page<ChoicenessAdDTO>> selectChoicenessAd(AdChoicenessParam adChoicenessParam) {
		Long memberId = UserUtil.getCurrentUserId(getRequest());
		// 获取查询广告所需要的信息
		Result<AdQueryMemberInfoDTO> adQueryMemberInfoResult = memberService.adQueryMemberInfo(memberId);
		if (!isSuccess(adQueryMemberInfoResult)) {
			return successGet(adQueryMemberInfoResult);
		}
		AdQueryMemberInfoDTO adQueryMemberInfoDTO = adQueryMemberInfoResult.getModel();
		AdChoicenessInternalParam adChoicenessInternalParam = new AdChoicenessInternalParam();
		adChoicenessInternalParam.setLatitude(adChoicenessParam.getLatitude());
		adChoicenessInternalParam.setLongitude(adChoicenessParam.getLongitude());
		adChoicenessInternalParam.setMerchantIds(adQueryMemberInfoDTO.getFansList());
		if (StringUtils.isNotBlank(adQueryMemberInfoDTO.getRegionPath())) {
			adChoicenessInternalParam.setAreas(Arrays.asList(StringUtils.split(adQueryMemberInfoDTO.getRegionPath(), "/")));
		}
		Result<Page<ChoicenessAdDTO>> result = adService.selectChoiceness(memberId, adChoicenessInternalParam);
		return successGet(result);
	}
}
