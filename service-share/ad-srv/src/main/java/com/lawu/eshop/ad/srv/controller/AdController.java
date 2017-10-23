package com.lawu.eshop.ad.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.constants.OrderTypeEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdDetailDTO;
import com.lawu.eshop.ad.dto.AdEgainDTO;
import com.lawu.eshop.ad.dto.AdEgainQueryDTO;
import com.lawu.eshop.ad.dto.AdFlatVideoDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.AdPayInfoDTO;
import com.lawu.eshop.ad.dto.AdPointDTO;
import com.lawu.eshop.ad.dto.AdPraiseDTO;
import com.lawu.eshop.ad.dto.AdSaveInfoDTO;
import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.dto.ChoicenessAdDTO;
import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.IsMyDateDTO;
import com.lawu.eshop.ad.dto.MerchantInfoDTO;
import com.lawu.eshop.ad.dto.OperatorAdDTO;
import com.lawu.eshop.ad.dto.PraisePointDTO;
import com.lawu.eshop.ad.dto.RedPacketInfoDTO;
import com.lawu.eshop.ad.dto.ReportAdDTO;
import com.lawu.eshop.ad.dto.ViewDTO;
import com.lawu.eshop.ad.param.AdChoicenessInternalParam;
import com.lawu.eshop.ad.param.AdEgainInternalParam;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdPointInternalParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.param.AdSolrRealParam;
import com.lawu.eshop.ad.param.AdsolrFindParam;
import com.lawu.eshop.ad.param.ListAdParam;
import com.lawu.eshop.ad.param.OperatorAdParam;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.AdClickPraiseInfoBO;
import com.lawu.eshop.ad.srv.bo.AdEgainBO;
import com.lawu.eshop.ad.srv.bo.AdEgainDetailBO;
import com.lawu.eshop.ad.srv.bo.AdPointBO;
import com.lawu.eshop.ad.srv.bo.AdPraiseBO;
import com.lawu.eshop.ad.srv.bo.AdSaveInfoBO;
import com.lawu.eshop.ad.srv.bo.ChoicenessAdBO;
import com.lawu.eshop.ad.srv.bo.ClickAdPointBO;
import com.lawu.eshop.ad.srv.bo.ClickPointBO;
import com.lawu.eshop.ad.srv.bo.GetRedPacketBO;
import com.lawu.eshop.ad.srv.bo.MerchantInfoBO;
import com.lawu.eshop.ad.srv.bo.OperatorAdBO;
import com.lawu.eshop.ad.srv.bo.RedPacketInfoBO;
import com.lawu.eshop.ad.srv.bo.RedPacketIsSendBO;
import com.lawu.eshop.ad.srv.bo.ReportAdBO;
import com.lawu.eshop.ad.srv.bo.ViewBO;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.ad.srv.service.FavoriteAdService;
import com.lawu.eshop.ad.srv.service.MemberAdRecordService;
import com.lawu.eshop.ad.srv.service.PointPoolService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.solr.service.SolrService;

/**
 * E赚接口提供
 *
 * @author zhangrc
 * @date 2017/4/6
 *
 */
@RestController
@RequestMapping(value = "ad/")
public class AdController extends BaseController {

	@Resource
	private AdService adService;

	@Resource
	private MemberAdRecordService memberAdRecordService;

	@Resource
	private PointPoolService pointPoolService;

	@Autowired
	private AdSrvConfig adSrvConfig;

	@Autowired
	private SolrService solrService;
	

	@Autowired
	private FavoriteAdService favoriteAdService;

	/**
	 * 添加E赚
	 *
	 * @param adSaveParam
	 * @return
	 */
	@RequestMapping(value = "saveAd", method = RequestMethod.POST)
	public Result<AdSaveInfoDTO> saveAd(@RequestBody AdSaveParam adSaveParam) {

		AdSaveInfoBO bo = adService.saveAd(adSaveParam);

		if (bo.getId() == null || bo.getId() < 0) {
			successCreated(ResultCode.SAVE_FAIL);
		}
		
		AdSaveInfoDTO dto = new AdSaveInfoDTO();
		dto.setAdCount(bo.getAdCount());
		dto.setId(bo.getId());
		dto.setAdOrderNum(bo.getAdOrderNum());
		
		return successCreated(dto);

	}

	/**
	 * 查询商家E赚,E赞
	 *
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "selectListByMerchant", method = RequestMethod.POST)
	public Result<Page<AdMerchantDTO>> selectListByMerchant(@RequestBody AdMerchantParam adMerchantParam, @RequestParam Long memberId) {
		Page<AdBO> pageBO = adService.selectListByMerchant(adMerchantParam, memberId);
		Page<AdMerchantDTO> pageDTO = new Page<>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertMerchantAdDTOS(pageBO.getRecords()));
		return successAccepted(pageDTO);
	}

	/**
	 * 对广告的操作，下架和删除
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus/{id}", method = RequestMethod.PUT)
	public Result updateStatus(@PathVariable Long id) {
		AdBO BO = adService.get(id);
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(new Date());// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -14); // 设置为14天前
		Date before14days = calendar.getTime(); // 得到14天前的时间
		if (BO.getBeginTime() != null && before14days.getTime() < BO.getBeginTime().getTime()) {
			return successCreated(ResultCode.AD_PUT_NOT_TIME);
		} else {
			adService.updateStatus(id);
			return successCreated();

		}

	}

	/**
	 * 对广告的操作，删除
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "remove/{id}", method = RequestMethod.PUT)
	public Result remove(@PathVariable Long id) {
		adService.remove(id);
		return successCreated();
	}

	/**
	 * 广告详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectAbById/{id}", method = RequestMethod.GET)
	public Result<AdEgainDTO> selectAbById(@PathVariable Long id, @RequestParam Long memberId) {
		AdEgainDetailBO bo = adService.selectAbById(id, memberId);
		return successAccepted(AdConverter.convertAdEgainDTO(bo));
	}

	/**
	 * 抢赞详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectAdPraiseById/{id}", method = RequestMethod.GET)
	public Result<AdPraiseDTO> selectAdPraiseById(@PathVariable Long id, @RequestParam Long memberId) {
		AdPraiseBO bo = adService.selectAdPraiseById(id, memberId);
		return successAccepted(AdConverter.convertPraiseDTO(bo));
	}

	/**
	 * 点击广告
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "clickAd/{id}", method = RequestMethod.PUT)
	public Result<ClickAdPointDTO> clickAd(@PathVariable Long id, @RequestParam Long memberId, @RequestParam String num) {
		ClickPointBO bo = adService.clickAd(id, memberId, num);
		if(bo.isOverClick()){
			return successCreated(ResultCode.AD_CLICK_PUTED);
		}
		
		ClickAdPointDTO dto = new ClickAdPointDTO();
		if(!bo.isOverClick() && !bo.isSysWords()){
			ClickAdPointBO clickAdPointBO = adService.getClickAdPoint(memberId, bo.getPoint());
			dto.setAddPoint(clickAdPointBO.getAddPoint());
			dto.setPoint(clickAdPointBO.getAdTotlePoint());
		}
		
		return successCreated(dto);
	}

	/**
	 * 对视频广告的审核
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "auditVideo/{id}", method = RequestMethod.PUT)
	public Result auditVideo(@PathVariable Long id, @RequestParam Integer auditorId, @RequestParam String remark, @RequestBody AuditEnum auditEnum) {
		AdBO adBO = adService.selectById(id);
		if (adBO != null && adBO.getStatusEnum().val.byteValue() != AdStatusEnum.AD_STATUS_AUDIT.val) {
			return successGet(ResultCode.AD_AUDITED);
		}
		Integer i = adService.auditVideo(id, auditorId, remark, auditEnum);
		if (i == null || i < 0) {
			successCreated(ResultCode.SAVE_FAIL);
		}
		return successCreated();
	}

	/**
	 * 运营查询广告
	 *
	 * @param adPlatParam
	 * @return
	 */
	@RequestMapping(value = "selectListByPlatForm", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByPlatForm(@RequestBody AdFindParam adPlatParam) {
		Page<AdBO> pageBO=  adService.selectListByPlatForm(adPlatParam);
		Page<AdDTO> pageDTO=new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return successAccepted(pageDTO);
	}

	/**
	 * 会员查询广告
	 *
	 * @param adMemberParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "selectListByMember", method = RequestMethod.POST)
	public Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam, @RequestParam Long memberId) {
		Page<AdBO> pageBO = adService.selectListByMember(adMemberParam, memberId);
		Page<AdDTO> pageDTO = new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return successAccepted(pageDTO);
	}

	/**
	 * 会员查询广告
	 * @see
	 * @param param
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "selectChoiceness", method = RequestMethod.POST)
	public Result<Page<AdDTO>> selectChoiceness(@RequestBody AdSolrRealParam param) {
		List<AdSolrDTO> solrDTOS = new ArrayList<>();
		String areaQueryStr = "putWay_i:1 AND -status_i:3";
		if (StringUtils.isEmpty(param.getRegionPath())) {
			areaQueryStr += " AND area_is:0";
		} else {
			String[] path = param.getRegionPath().split("/");
			if (path.length == 3) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:" + path[2] + " OR area_is:0)";
			} else if (path.length == 2) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:0)";
			} else if (path.length == 1) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:0)";
			}
		}

		String fansQueryStr = "";
		StringBuilder stringBuilder = new StringBuilder();
		if (param.getMemberId() != null && param.getMemberId() > 0 && !param.getMerchantIds().isEmpty()) {
			List<Long> merchantIds = param.getMerchantIds();
			for (Long id : merchantIds) {
				stringBuilder.append(id).append(" OR ");
			}
			fansQueryStr = stringBuilder.substring(0, stringBuilder.length() - 4);
			fansQueryStr = "putWay_i:2 AND -status_i:3 AND merchantId_l:(" + fansQueryStr + ")";
		}

		int hitMax = 0;
		int hitMin = 0;
        int totalCount = 0;
        SolrQuery query = new SolrQuery();
		if (StringUtils.isEmpty(fansQueryStr)) {
			query.setParam("q", areaQueryStr);
		} else {
			query.setParam("q", "(" + areaQueryStr + ") OR (" + fansQueryStr + ")");
		}
		query.setSort("hits_i", SolrQuery.ORDER.desc);
		query.setStart(param.getOffset());
		query.setRows(param.getPageSize());
		SolrDocumentList solrDocumentList = solrService.getSolrDocsByQueryPost(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		if (solrDocumentList != null) {
			totalCount = (int) solrDocumentList.getNumFound();
			if (!solrDocumentList.isEmpty()) {
				solrDTOS = AdConverter.convertDTO(solrDocumentList);
				hitMax = Integer.valueOf(solrDocumentList.get(0).get("hits_i").toString());
				hitMin = Integer.valueOf(solrDocumentList.get(solrDocumentList.size() - 1).get("hits_i").toString());
			}
		}

		List<AdSolrDTO> latLonDTOS = new ArrayList<>();
		if (param.getLatitude() != null && param.getLongitude() != null) {
			double lat = BigDecimal.valueOf(param.getLatitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			double lon = BigDecimal.valueOf(param.getLongitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			String latLon = lat + "," + lon;
			query = new SolrQuery();
			query.setParam("pt", latLon);
			query.setParam("fq", "{!geofilt}");
			query.setParam("sfield", "latLon_p");
			query.setParam("d", "30");
			query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
			String radiusQueryStr = "putWay_i:3 AND -status_i:3";
			query.setParam("q", radiusQueryStr);
			SolrDocumentList radiusList = solrService.getSolrDocsByQuery(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
			if (radiusList != null && !radiusList.isEmpty()) {
				for (SolrDocument solrDocument : radiusList) {
                    if (Double.valueOf(solrDocument.get("distance").toString()) <= Double.valueOf(solrDocument.get("radius_i").toString())) {
                        AdSolrDTO adSolrDTO = AdConverter.convertDTO(solrDocument);
						latLonDTOS.add(adSolrDTO);
                    }
                }
			}
		}

		Page<AdDTO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());

		//获取总页数
		int totalPage;
		if (totalCount > 0 && totalCount % param.getPageSize() == 0) {
			totalPage = totalCount / param.getPageSize();
		} else {
			totalPage = totalCount / param.getPageSize() + 1;
		}

		//总条数
		totalCount += latLonDTOS.size();
		page.setTotalCount(totalCount);
		//如果当前页大于总页数，则直接返回
		if (page.getCurrentPage() > totalPage) {
			page.setRecords(AdConverter.convertAdDTOS(new ArrayList<>()));
			return successAccepted(page);
		}

		if (param.getCurrentPage() == totalPage) {
			if (param.getCurrentPage() == 1) {
				solrDTOS.addAll(latLonDTOS);
			} else {
				for (AdSolrDTO solrDTO : latLonDTOS) {
					if (hitMax > 0 && solrDTO.getHits() <= hitMax) {
						solrDTOS.add(solrDTO);
					}
				}
			}
		} else {
			for (AdSolrDTO solrDTO : latLonDTOS) {
				if ((hitMin > 0 || hitMax > 0) && solrDTO.getHits() >= hitMin && solrDTO.getHits() <= hitMax) {
					solrDTOS.add(solrDTO);
				}
			}
		}

		if (!solrDTOS.isEmpty()) {
			Collections.sort(solrDTOS, new Comparator<AdSolrDTO>() {
				@Override
				public int compare(AdSolrDTO o1, AdSolrDTO o2) {
					return o2.getHits() - o1.getHits();
				}
			});
		}

		page.setRecords(AdConverter.convertAdDTOS(solrDTOS));
		return successAccepted(page);
	}

	/**
	 * 会员E赞
	 *
	 * @param adPraiseParam
	 * @return
	 */
	@RequestMapping(value = "selectPraiseListByMember", method = RequestMethod.POST)
	public Result<Page<AdDTO>> selectPraiseListByMember(@RequestBody AdPraiseParam adPraiseParam, @RequestParam Long memberId) {
		Page<AdBO> pageBO = adService.selectPraiseListByMember(adPraiseParam, memberId);
		Page<AdDTO> pageDTO = new Page<AdDTO>();
		pageDTO.setCurrentPage(pageBO.getCurrentPage());
		pageDTO.setTotalCount(pageBO.getTotalCount());
		pageDTO.setRecords(AdConverter.convertDTOS(pageBO.getRecords()));
		return successAccepted(pageDTO);
	}

	/**
	 * 会员E赞
	 *
	 * @param id
	 * @param memberId
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "clickPraise/{id}", method = RequestMethod.PUT)
	public Result<PraisePointDTO> clickPraise(@PathVariable Long id, @RequestParam Long memberId, @RequestParam String num) {
		Boolean flag = pointPoolService.selectStatusByMember(id, memberId);
		if (flag) return successCreated(ResultCode.AD_PRAISE_POINT_GET);
		AdClickPraiseInfoBO bo = adService.clickPraise(id, memberId, num);
		
		if (bo.getPoint().compareTo(new BigDecimal(0)) == 0 && !bo.isSysWordFlag()) {
			return successCreated(ResultCode.AD_PRAISE_PUTED);
		} 
		
		PraisePointDTO dto = new PraisePointDTO();
		dto.setPoint(bo.getPoint());
		return successCreated(dto);

	}

	/**
	 * 根据广告名称查询广告
	 *
	 * @param adSolrParam
	 * @return
	 */
	@RequestMapping(value = "queryAdByTitle", method = RequestMethod.POST)
	public Result<Page<AdSolrDTO>> queryAdByTitle(@RequestBody AdsolrFindParam adSolrParam) {
		List<AdSolrDTO> solrDTOS = new ArrayList<>();
		String areaQueryStr = "putWay_i:1 AND -status_i:3 AND -type_i:3";
		if (StringUtils.isEmpty(adSolrParam.getRegionPath())) {
			areaQueryStr += " AND area_is:0";
		} else {
			String[] path = adSolrParam.getRegionPath().split("/");
			if (path.length == 3) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:" + path[2] + " OR area_is:0)";
			} else if (path.length == 2) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:0)";
			} else if (path.length == 1) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:0)";
			}
		}
		if (StringUtils.isNotEmpty(adSolrParam.getAdSolrParam().getTitle())) {
			areaQueryStr += " AND title_s:*" + adSolrParam.getAdSolrParam().getTitle() + "*";
		}

		String fansQueryStr = "";
		StringBuilder stringBuilder = new StringBuilder();
		if (adSolrParam.getMemberId() != null && adSolrParam.getMemberId() > 0 && !adSolrParam.getMerchantIds().isEmpty()) {
            List<Long> merchantIds = adSolrParam.getMerchantIds();
            for (Long id : merchantIds) {
				stringBuilder.append(id).append(" OR ");
            }
			fansQueryStr = stringBuilder.substring(0, stringBuilder.length() - 4);
            fansQueryStr = "putWay_i:2 AND -status_i:3 AND -type_i:3 AND merchantId_l:(" + fansQueryStr + ")";
            if (StringUtils.isNotEmpty(adSolrParam.getAdSolrParam().getTitle())) {
                fansQueryStr += " AND title_s:*" + adSolrParam.getAdSolrParam().getTitle() + "*";
            }
        }

		int hitMax = 0;
		int hitMin = 0;
        int totalCount = 0;
		SolrQuery query = new SolrQuery();
		if (StringUtils.isEmpty(fansQueryStr)) {
			query.setParam("q", areaQueryStr);
		} else {
			query.setParam("q", "(" + areaQueryStr + ") OR (" + fansQueryStr + ")");
		}
		query.setSort("hits_i", SolrQuery.ORDER.desc);
		query.setStart(adSolrParam.getAdSolrParam().getOffset());
		query.setRows(adSolrParam.getAdSolrParam().getPageSize());
		SolrDocumentList solrDocumentList = solrService.getSolrDocsByQueryPost(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		if (solrDocumentList != null){
			totalCount = (int) solrDocumentList.getNumFound();
			if (!solrDocumentList.isEmpty()) {
				solrDTOS = AdConverter.convertDTO(solrDocumentList);
				hitMax = Integer.valueOf(solrDocumentList.get(0).get("hits_i").toString());
				hitMin = Integer.valueOf(solrDocumentList.get(solrDocumentList.size() - 1).get("hits_i").toString());
			}
		}

		List<AdSolrDTO> latLonDTOS = new ArrayList<>();
		if (adSolrParam.getAdSolrParam().getLatitude() != null && adSolrParam.getAdSolrParam().getLongitude() != null) {
			double lat = BigDecimal.valueOf(adSolrParam.getAdSolrParam().getLatitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			double lon = BigDecimal.valueOf(adSolrParam.getAdSolrParam().getLongitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			String latLon = lat + "," + lon;
			query = new SolrQuery();
			query.setParam("pt", latLon);
			query.setParam("fq", "{!geofilt}");
			query.setParam("sfield", "latLon_p");
			query.setParam("d", "30");
			query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
			String radiusQueryStr = "putWay_i:3 AND -status_i:3 AND -type_i:3";
			if (StringUtils.isNotEmpty(adSolrParam.getAdSolrParam().getTitle())) {
				radiusQueryStr += " AND title_s:*" + adSolrParam.getAdSolrParam().getTitle() + "*";
			}
			query.setParam("q", radiusQueryStr);
			SolrDocumentList radiusList = solrService.getSolrDocsByQuery(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
			if (radiusList != null && !radiusList.isEmpty()) {
				for (SolrDocument solrDocument : radiusList) {
					if (Double.valueOf(solrDocument.get("distance").toString()) <= Double.valueOf(solrDocument.get("radius_i").toString())) {
						AdSolrDTO adSolrDTO = AdConverter.convertDTO(solrDocument);
						latLonDTOS.add(adSolrDTO);
					}
				}
			}
		}

		Page<AdSolrDTO> page = new Page<>();
		page.setCurrentPage(adSolrParam.getAdSolrParam().getCurrentPage());

		//获取总页数
		int totalPage;
		if (totalCount > 0 && totalCount % adSolrParam.getAdSolrParam().getPageSize() == 0) {
			totalPage = totalCount / adSolrParam.getAdSolrParam().getPageSize();
		} else {
			totalPage = totalCount / adSolrParam.getAdSolrParam().getPageSize() + 1;
		}

		//总条数
		totalCount += latLonDTOS.size();
		page.setTotalCount(totalCount);
		//如果当前页大于总页数，则直接返回
		if (page.getCurrentPage() > totalPage) {
			page.setRecords(new ArrayList<>());
			return successGet(page);
		}

		if (adSolrParam.getAdSolrParam().getCurrentPage() == totalPage) {
			if (adSolrParam.getAdSolrParam().getCurrentPage() == 1) {
				solrDTOS.addAll(latLonDTOS);
			} else {
				for (AdSolrDTO solrDTO : latLonDTOS) {
					if (hitMax > 0 && solrDTO.getHits() <= hitMax) {
						solrDTOS.add(solrDTO);
					}
				}
			}
		} else {
			for (AdSolrDTO solrDTO : latLonDTOS) {
				if ((hitMin > 0 || hitMax > 0) && solrDTO.getHits() >= hitMin && solrDTO.getHits() <= hitMax) {
					solrDTOS.add(solrDTO);
				}
			}
		}

		if (!solrDTOS.isEmpty()) {
			Collections.sort(solrDTOS, new Comparator<AdSolrDTO>() {
				@Override
				public int compare(AdSolrDTO o1, AdSolrDTO o2) {
					return o2.getHits() - o1.getHits();
				}
			});
		}

		page.setRecords(solrDTOS);
		return successGet(page);
	}

	/**
	 * 推荐广告
	 *
	 * @param param
	 * @return
	 * @author meishuquan
	 */
	@RequestMapping(value = "recommendAdByType", method = RequestMethod.POST)
	public Result<Page<AdFlatVideoDTO>> getRecommendAdByType(@RequestBody AdSolrRealParam param) {
		List<AdSolrDTO> solrDTOS = new ArrayList<>();
		String areaQueryStr = "putWay_i:1 AND status_i:2";
		if (StringUtils.isEmpty(param.getRegionPath())) {
			areaQueryStr += " AND area_is:0";
		} else {
			String[] path = param.getRegionPath().split("/");
			if (path.length == 3) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:" + path[2] + " OR area_is:0)";
			} else if (path.length == 2) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:0)";
			} else if (path.length == 1) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:0)";
			}
		}
		if (param.getTypeEnum().equals(AdTypeEnum.AD_TYPE_FLAT)) {
			areaQueryStr += " AND type_i:1";
		} else if (param.getTypeEnum().equals(AdTypeEnum.AD_TYPE_VIDEO)) {
			areaQueryStr += " AND type_i:2";
		}

		String fansQueryStr = "";
		StringBuilder stringBuilder = new StringBuilder();
		if (param.getMemberId() != null && param.getMemberId() > 0 && !param.getMerchantIds().isEmpty()) {
			List<Long> merchantIds = param.getMerchantIds();
            for (Long id : merchantIds) {
				stringBuilder.append(id).append(" OR ");
            }
			fansQueryStr = stringBuilder.substring(0, stringBuilder.length() - 4);
            fansQueryStr = "putWay_i:2 AND status_i:2 AND merchantId_l:(" + fansQueryStr + ")";
            if (param.getTypeEnum().equals(AdTypeEnum.AD_TYPE_FLAT)) {
                fansQueryStr += " AND type_i:1";
            } else if (param.getTypeEnum().equals(AdTypeEnum.AD_TYPE_VIDEO)) {
                fansQueryStr += " AND type_i:2";
            }
        }

		int hitMax = 0;
		int hitMin = 0;
        int totalCount = 0;
		SolrQuery query = new SolrQuery();
		if (StringUtils.isEmpty(fansQueryStr)) {
			query.setParam("q", areaQueryStr);
		} else {
			query.setParam("q", "(" + areaQueryStr + ") OR (" + fansQueryStr + ")");
		}
		query.setSort("hits_i", SolrQuery.ORDER.desc);
		query.setStart(param.getOffset());
		query.setRows(param.getPageSize());
		SolrDocumentList solrDocumentList = solrService.getSolrDocsByQueryPost(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		if (solrDocumentList != null){
			totalCount = (int) solrDocumentList.getNumFound();
			if (!solrDocumentList.isEmpty()) {
				solrDTOS = AdConverter.convertDTO(solrDocumentList);
				hitMax = Integer.valueOf(solrDocumentList.get(0).get("hits_i").toString());
				hitMin = Integer.valueOf(solrDocumentList.get(solrDocumentList.size() - 1).get("hits_i").toString());
			}
		}

		List<AdSolrDTO> latLonDTOS = new ArrayList<>();
		if (param.getLatitude() != null && param.getLongitude() != null) {
			double lat = BigDecimal.valueOf(param.getLatitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			double lon = BigDecimal.valueOf(param.getLongitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			String latLon = lat + "," + lon;
			query = new SolrQuery();
			query.setParam("pt", latLon);
			query.setParam("fq", "{!geofilt}");
			query.setParam("sfield", "latLon_p");
			query.setParam("d", "30");
			query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
			String radiusQueryStr = "putWay_i:3 AND status_i:2";
			if (param.getTypeEnum().equals(AdTypeEnum.AD_TYPE_FLAT)) {
				radiusQueryStr += " AND type_i:1";
			} else if (param.getTypeEnum().equals(AdTypeEnum.AD_TYPE_VIDEO)) {
				radiusQueryStr += " AND type_i:2";
			}
			query.setParam("q", radiusQueryStr);
			SolrDocumentList radiusList = solrService.getSolrDocsByQuery(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
			if (radiusList != null && !radiusList.isEmpty()) {
				for (SolrDocument solrDocument : radiusList) {
					if (Double.valueOf(solrDocument.get("distance").toString()) <= Double.valueOf(solrDocument.get("radius_i").toString())) {
						AdSolrDTO adSolrDTO = AdConverter.convertDTO(solrDocument);
						latLonDTOS.add(adSolrDTO);
					}
				}
			}
		}

		Page<AdFlatVideoDTO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());

		//获取总页数
		int totalPage;
		if (totalCount > 0 && totalCount % param.getPageSize() == 0) {
			totalPage = totalCount / param.getPageSize();
		} else {
			totalPage = totalCount / param.getPageSize() + 1;
		}

		//总条数
		totalCount += latLonDTOS.size();
		page.setTotalCount(totalCount);
		//如果当前页大于总页数，则直接返回
		if (page.getCurrentPage() > totalPage) {
			page.setRecords(AdConverter.convertAdFlatVideoDTOS(new ArrayList<>()));
			return successGet(page);
		}

		if (param.getCurrentPage() == totalPage) {
			if (param.getCurrentPage() == 1) {
				solrDTOS.addAll(latLonDTOS);
			} else {
				for (AdSolrDTO solrDTO : latLonDTOS) {
					if (hitMax > 0 && solrDTO.getHits() <= hitMax) {
						solrDTOS.add(solrDTO);
					}
				}
			}
		} else {
			for (AdSolrDTO solrDTO : latLonDTOS) {
				if ((hitMin > 0 || hitMax > 0) && solrDTO.getHits() >= hitMin && solrDTO.getHits() <= hitMax) {
					solrDTOS.add(solrDTO);
				}
			}
		}

		if (!solrDTOS.isEmpty()) {
			Collections.sort(solrDTOS, new Comparator<AdSolrDTO>() {
				@Override
				public int compare(AdSolrDTO o1, AdSolrDTO o2) {
					return o2.getHits() - o1.getHits();
				}
			});
		}

		page.setRecords(AdConverter.convertAdFlatVideoDTOS(solrDTOS));
		return successGet(page);
	}

	/**
	 * 推荐抢赞
	 *
	 * @param param
	 * @return
	 * @author meishuquan
	 */
	@RequestMapping(value = "recommendEgain", method = RequestMethod.POST)
	public Result<Page<AdPraiseDTO>> getRecommendEgain(@RequestBody AdSolrRealParam param) {
		List<AdPraiseDTO> adPraiseDTOS = new ArrayList<>();
		String areaQueryStr = "putWay_i:1 AND type_i:3";
		if (StringUtils.isEmpty(param.getRegionPath())) {
			areaQueryStr += " AND area_is:0";
		} else {
			String[] path = param.getRegionPath().split("/");
			if (path.length == 3) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:" + path[2] + " OR area_is:0)";
			} else if (path.length == 2) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:0)";
			} else if (path.length == 1) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:0)";
			}
		}
		if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_PUTING)) {
			areaQueryStr += " AND status_i:2";
		} else if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_ADD)) {
			areaQueryStr += " AND status_i:1";
		} else if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_PUTED)) {
			areaQueryStr += " AND status_i:3";
		}

		String fansQueryStr = "";
		StringBuilder stringBuilder = new StringBuilder();
		if (param.getMemberId() != null && param.getMemberId() > 0 && !param.getMerchantIds().isEmpty()) {
			List<Long> merchantIds = param.getMerchantIds();
            for (Long id : merchantIds) {
				stringBuilder.append(id).append(" OR ");
            }
			fansQueryStr = stringBuilder.substring(0, stringBuilder.length() - 4);
            fansQueryStr = "putWay_i:2 AND type_i:3 AND merchantId_l:(" + fansQueryStr + ")";
            if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_PUTING)) {
                fansQueryStr += " AND status_i:2";
            } else if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_ADD)) {
                fansQueryStr += " AND status_i:1";
            } else if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_PUTED)) {
                fansQueryStr += " AND status_i:3";
            }
        }

        int totalCount = 0;
		SolrQuery query = new SolrQuery();
		if (StringUtils.isEmpty(fansQueryStr)) {
			query.setParam("q", areaQueryStr);
		} else {
			query.setParam("q", "(" + areaQueryStr + ") OR (" + fansQueryStr + ")");
		}
		if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_PUTING) || param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_ADD)) {
			query.setSort("beginTime_l", SolrQuery.ORDER.asc);
		} else if (param.getStatusEnum().equals(AdStatusEnum.AD_STATUS_PUTED)) {
			query.setSort("id", SolrQuery.ORDER.desc);
		}
		query.setStart(param.getOffset());
		query.setRows(param.getPageSize());
		SolrDocumentList solrDocumentList = solrService.getSolrDocsByQueryPost(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		if (solrDocumentList != null){
			totalCount = (int) solrDocumentList.getNumFound();
			if (!solrDocumentList.isEmpty()) {
				List<AdSolrDTO> solrDTOS = AdConverter.convertDTO(solrDocumentList);
				for (AdSolrDTO solrDTO : solrDTOS) {
					AdBO adBO = adService.selectById(solrDTO.getId());
					if (adBO.getStatusEnum().val.byteValue() == AdStatusEnum.AD_STATUS_ADD.val) {
						int favoriteCount = favoriteAdService.getFavoriteCount(adBO.getId());
						solrDTO.setHits(favoriteCount);
					}
					AdPraiseDTO adPraiseDTO = AdConverter.convertDTO(solrDTO, adBO);
					adPraiseDTOS.add(adPraiseDTO);
				}
			}
		}

		Page<AdPraiseDTO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(totalCount);
		page.setRecords(adPraiseDTOS);
		return successGet(page);
	}

	/**
	 * 平面广告排行榜
	 *
	 * @param param
	 * @return
	 * @author meishuquan
	 */
	@RequestMapping(value = "listAdRank", method = RequestMethod.POST)
	public Result<List<AdDTO>> listAdRank(@RequestBody AdSolrRealParam param) {
		List<AdSolrDTO> solrDTOS = new ArrayList<>();
		String areaQueryStr = "putWay_i:1 AND type_i:1";
		if (StringUtils.isEmpty(param.getRegionPath())) {
			areaQueryStr += " AND area_is:0";
		} else {
			String[] path = param.getRegionPath().split("/");
			if (path.length == 3) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:" + path[2] + " OR area_is:0)";
			} else if (path.length == 2) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:" + path[1] + " OR area_is:0)";
			} else if (path.length == 1) {
				areaQueryStr += " AND (area_is:" + path[0] + " OR area_is:0)";
			}
		}

		String fansQueryStr = "";
		StringBuilder stringBuilder = new StringBuilder();
		if (param.getMemberId() != null && param.getMemberId() > 0 && !param.getMerchantIds().isEmpty()) {
			List<Long> merchantIds = param.getMerchantIds();
            for (Long id : merchantIds) {
				stringBuilder.append(id).append(" OR ");
            }
			fansQueryStr = stringBuilder.substring(0, stringBuilder.length() - 4);
            fansQueryStr = "putWay_i:2 AND type_i:1 AND merchantId_l:(" + fansQueryStr + ")";
        }

		double pointMax = 0;
		double pointMin = 0;
		SolrQuery query = new SolrQuery();
		if (StringUtils.isEmpty(fansQueryStr)) {
			query.setParam("q", areaQueryStr);
		} else {
			query.setParam("q", "(" + areaQueryStr + ") OR (" + fansQueryStr + ")");
		}
		if (param.getOrderTypeEnum().equals(OrderTypeEnum.AD_TORLEPOINT_DESC)) {
			query.setSort("totalPoint_d", SolrQuery.ORDER.desc);
		} else {
			query.setSort("point_d", SolrQuery.ORDER.desc);
		}
		query.setStart(param.getOffset());
		query.setRows(param.getPageSize());
		SolrDocumentList solrDocumentList = solrService.getSolrDocsByQueryPost(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		if (solrDocumentList != null && !solrDocumentList.isEmpty()) {
			solrDTOS = AdConverter.convertDTO(solrDocumentList);
			if (param.getOrderTypeEnum().equals(OrderTypeEnum.AD_TORLEPOINT_DESC)) {
				pointMax = Double.valueOf(solrDocumentList.get(0).get("totalPoint_d").toString());
				pointMin = Double.valueOf(solrDocumentList.get(solrDocumentList.size() - 1).get("totalPoint_d").toString());
			} else {
				pointMax = Double.valueOf(solrDocumentList.get(0).get("point_d").toString());
				pointMin = Double.valueOf(solrDocumentList.get(solrDocumentList.size() - 1).get("point_d").toString());
			}
		}

		if (param.getLatitude() != null && param.getLongitude() != null) {
			double lat = BigDecimal.valueOf(param.getLatitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			double lon = BigDecimal.valueOf(param.getLongitude()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			String latLon = lat + "," + lon;
			query = new SolrQuery();
			query.setParam("pt", latLon);
			query.setParam("fq", "{!geofilt}");
			query.setParam("sfield", "latLon_p");
			query.setParam("d", "30");
			query.setParam("fl", "*,distance:geodist(latLon_p," + latLon + ")");
			String radiusQueryStr = "putWay_i:3 AND type_i:1";
			if (pointMax > 0) {
				if (param.getOrderTypeEnum().equals(OrderTypeEnum.AD_TORLEPOINT_DESC)) {
					radiusQueryStr += " AND totalPoint_d:[" + pointMin + " TO " + pointMax + "]";
				} else {
					radiusQueryStr += " AND point_d:[" + pointMin + " TO " + pointMax + "]";
				}
			}
			query.setParam("q", radiusQueryStr);
			SolrDocumentList radiusList = solrService.getSolrDocsByQuery(query, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
			if (radiusList != null && !radiusList.isEmpty()) {
				for (SolrDocument solrDocument : radiusList) {
					if (Double.valueOf(solrDocument.get("distance").toString()) <= Double.valueOf(solrDocument.get("radius_i").toString())) {
						AdSolrDTO adSolrDTO = AdConverter.convertDTO(solrDocument);
						solrDTOS.add(adSolrDTO);
					}
				}
			}
		}

		if (!solrDTOS.isEmpty()) {
			Collections.sort(solrDTOS, new Comparator<AdSolrDTO>() {
				@Override
				public int compare(AdSolrDTO o1, AdSolrDTO o2) {
					if (param.getOrderTypeEnum().equals(OrderTypeEnum.AD_TORLEPOINT_DESC)) {
						if (o2.getTotalPoint() - o1.getTotalPoint() >= 0) {
							return 1;
						} else {
							return 0;
						}
					} else {
						if (o2.getPoint() - o1.getPoint() >= 0) {
							return 1;
						} else {
							return 0;
						}
					}
				}
			});
		}

		List<AdSolrDTO> resultDTOS = new ArrayList<>();
		if (solrDTOS.size() <= param.getPageSize()) {
			resultDTOS.addAll(solrDTOS);
		} else {
			resultDTOS = solrDTOS.subList(param.getOffset(), param.getPageSize());
		}
		return successGet(AdConverter.convertAdDTOS(resultDTOS));
	}

	/**
	 * 领取红包
	 *
	 * @param merchantId
	 * @param memberId
	 * @param memberNum
	 * @return
	 */
	@RequestMapping(value = "getRedPacket", method = RequestMethod.PUT)
	public Result<PraisePointDTO> getRedPacket(@RequestParam Long merchantId, @RequestParam Long memberId, @RequestParam String memberNum) {
		GetRedPacketBO redPacketBO = pointPoolService.isGetRedPacket(merchantId, memberNum);
		if (redPacketBO.isGetRedPacket()) {
			return successCreated(ResultCode.AD_RED_PACKGE_GET);
		}
		if (redPacketBO.isNullRedPacket()) {
			return successCreated(ResultCode.AD_RED_PACKGE_PUTED);
		}
		BigDecimal point = adService.getRedPacket(merchantId, memberId, memberNum);
		PraisePointDTO dto = new PraisePointDTO();
		dto.setPoint(point);
		return successGet(dto);
	}

	/**
	 * 获取所有的广告ids
	 *
	 * @return
	 */
	@RequestMapping(value = "getAllAd", method = RequestMethod.GET)
	public Result<List<ViewDTO>> getAllAd() {
		List<ViewBO> bos = adService.getAllAd();
		List<ViewDTO> dtos = new ArrayList<>();
		for (ViewBO viewBO : bos) {
			ViewDTO dto = new ViewDTO();
			dto.setId(viewBO.getId());
			dto.setViewCount(viewBO.getViewCount());
			dtos.add(dto);
		}
		return successGet(dtos);
	}

	/**
	 * 修改广告浏览次数
	 *
	 * @param id
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "updateViewCount/{id}", method = RequestMethod.PUT)
	public Result<List<Long>> updateViewCount(@PathVariable Long id, @RequestParam Integer count) {
		adService.updateViewCount(id, count);
		return successCreated();
	}

	/**
	 * 运营后台查询广告列表
	 *
	 * @param listAdParam
	 * @return
	 */
	@RequestMapping(value = "listAllAd", method = RequestMethod.POST)
	public Result<Page<AdDTO>> listAllAd(@RequestBody ListAdParam listAdParam) {
		Page<AdBO> adBOPage = adService.listAllAd(listAdParam);
		Page<AdDTO> page = new Page<>();
		page.setCurrentPage(adBOPage.getCurrentPage());
		page.setRecords(AdConverter.convertDTOS(adBOPage.getRecords()));
		page.setTotalCount(adBOPage.getTotalCount());
		return successGet(page);
	}

	/**
	 * 根据ID查询广告详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getAd/{id}", method = RequestMethod.GET)
	public Result<AdDTO> getAdById(@PathVariable Long id) {
		AdBO adBO = adService.get(id);
		if (adBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		return successGet(AdConverter.convertDTO(adBO));
	}

	/**
	 * 商家端广告详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectById/{id}", method = RequestMethod.GET)
	public Result<AdMerchantDetailDTO> selectById(@PathVariable Long id) {
		AdBO bo = adService.selectById(id);
		return successAccepted(AdConverter.convertMerchantDetailAdDTO(bo));
	}

	/**
	 * 运营后台操作广告(下架、删除)
	 *
	 * @param id
	 * @param adStatusEnum
	 * @return
	 */
	@RequestMapping(value = "operatorUpdateAdStatus/{id}", method = RequestMethod.PUT)
	public Result operatorUpdateAdStatus(@PathVariable Long id, @RequestParam AdStatusEnum adStatusEnum) {
		AdBO adBO = adService.get(id);
		if (adBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		adService.operatorUpdateAdStatus(id, adStatusEnum);
		return successCreated();
	}

	/**
	 * 查询上架中的平面视频广告
	 *
	 * @param listAdParam
	 * @return
	 */
	@RequestMapping(value = "listFlatVideoAd", method = RequestMethod.POST)
	public Result<List<AdDTO>> listFlatVideoAd(@RequestBody ListAdParam listAdParam) {
		List<AdBO> adBOS = adService.listFlatVideoAd(listAdParam);
		if (adBOS == null || adBOS.isEmpty()) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		return successGet(AdConverter.convertDTOS(adBOS));
	}

	/**
	 * 重建平面视频广告索引
	 *
	 * @return
	 */
	@RequestMapping(value = "rebuildAdIndex", method = RequestMethod.GET)
	public Result rebuildAdIndex() {
		adService.rebuildAdIndex();
		return successCreated();
	}

	/**
	 * 删除无效的平面视频广告索引
	 *
	 * @return
	 */
	@RequestMapping(value = "delInvalidAdIndex", method = RequestMethod.GET)
	public Result delInvalidAdIndex() {
		adService.delInvalidAdIndex();
		return successCreated();
	}

	/**
	 * 根据商家获取红包相关信息
	 *
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "getRedPacketInfo/{merchantId}", method = RequestMethod.GET)
	public Result<RedPacketInfoDTO> getRedPacketInfo(@PathVariable Long merchantId) {
		RedPacketInfoBO redPacketInfoBO = adService.getRedPacketInfo(merchantId);
		if (redPacketInfoBO == null) {
			return successCreated(ResultCode.AD_RED_PACKGE_PUTED);
		} else {
			RedPacketInfoDTO redPacketInfoDTO = new RedPacketInfoDTO();
			redPacketInfoDTO.setPoint(redPacketInfoBO.getPoint());
			redPacketInfoDTO.setMediaUrl(redPacketInfoBO.getMediaUrl());
			redPacketInfoDTO.setName(redPacketInfoBO.getName());
			redPacketInfoDTO.setLogoUrl(redPacketInfoBO.getLogoUrl());
			redPacketInfoDTO.setFileType(redPacketInfoBO.getFileType());
			return successCreated(redPacketInfoDTO);
		}

	}

	/**
	 * 判断红包是否领取完成
	 *
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "isExistsRedPacket/{merchantId}", method = RequestMethod.GET)
	public Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable Long merchantId) {
		Boolean flag = adService.isExistsRedPacket(merchantId);
		IsExistsRedPacketDTO dto = new IsExistsRedPacketDTO();
		dto.setIsExistsRedPacket(flag);
		return successCreated(dto);
	}

	/**
	 * 商家批量删除广告
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "batchDeleteAd", method = RequestMethod.DELETE)
	public Result batchDeleteAd(@RequestParam("ids") List<Long> ids, @RequestParam Long merchantId) {
		for (int i = 0; i < ids.size(); i++) {
			Boolean flag = adService.isMyData(ids.get(i), merchantId);
			if (!flag)
				ids.remove(i);
		}
		adService.batchDeleteAd(ids);
		return successCreated();
	}

	/**
	 * 商家详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectDetail/{id}", method = RequestMethod.GET)
	public Result<AdDetailDTO> selectDetail(@PathVariable Long id) {
		return successCreated(AdConverter.convertDetailDTO(adService.selectDetail(id)));
	}

	/**
	 * 判断数据是否是当前用户的
	 *
	 * @param id
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "isMyData/{id}", method = RequestMethod.GET)
	public Result<IsMyDateDTO> isMyData(@PathVariable Long id, @RequestParam Long merchantId) {
		Boolean flag = adService.isMyData(id, merchantId);
		IsMyDateDTO dto = new IsMyDateDTO();
		dto.setFlag(flag);
		return successCreated(dto);
	}

	/**
	 * 广告收益统计
	 *
	 * @return
	 */
	@RequestMapping(value = "selectReportAdEarnings", method = RequestMethod.GET)
	public Result<List<ReportAdDTO>> selectReportAdEarnings() {
		List<ReportAdBO> list = adService.selectReportAdEarnings();
		List<ReportAdDTO> listDTO = new ArrayList<>();
		for (ReportAdBO reportAdBO : list) {
			ReportAdDTO dto = new ReportAdDTO();
			dto.setGmtCreate(reportAdBO.getGmtCreate());
			dto.setId(reportAdBO.getId());
			dto.setMerchantId(reportAdBO.getMerchantId());
			dto.setMerchantNum(reportAdBO.getMerchantNum());
			dto.setStatusEnum(reportAdBO.getStatusEnum());
			dto.setTypeEnum(reportAdBO.getTypeEnum());
			dto.setTotalPoint(reportAdBO.getTotalPoint());
			dto.setTitle(reportAdBO.getTitle());
			listDTO.add(dto);
		}
		return successCreated(listDTO);
	}

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
	public Result<Page<AdEgainQueryDTO>> selectPageAdEgain(@PathVariable("memberId") Long memberId, @RequestBody AdEgainInternalParam param) {
		Page<AdEgainBO> pageAdEgainBO = adService.selectPageAdEgain(memberId, param);
		Page<AdEgainQueryDTO> rtn = AdConverter.convertAdEgainQueryDTOPage(pageAdEgainBO);
		return successCreated(rtn);
	}

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
	public Result<List<AdPointDTO>> selectAdPoint(@RequestBody AdPointInternalParam param) {
		List<AdPointBO> adPointBOList = adService.selectAdPoint(param);
		List<AdPointDTO> rtn = AdConverter.convertAdPointDTOList(adPointBOList);
		return successCreated(rtn);
	}

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
	public Result<Page<ChoicenessAdDTO>> selectChoiceness(@PathVariable("memberId") Long memberId, @RequestBody AdChoicenessInternalParam param) {
		Page<ChoicenessAdBO> pageChoicenessAdBO = adService.selectPageChoicenessAd(memberId, param);
		Page<ChoicenessAdDTO> rtn = AdConverter.convertChoicenessAdDTOPage(pageChoicenessAdBO);
		return successCreated(rtn);
	}

	/**
	 * 运营平台查询广告
	 * @param operatorAdParam
	 * @return
	 */
	@RequestMapping(value = "selectOperatorAdAll", method = RequestMethod.POST)
	public Result<List<OperatorAdDTO>> selectOperatorAdAll(@RequestBody OperatorAdParam operatorAdParam) {
		List<OperatorAdBO> list = adService.selectOperatorAdAll(operatorAdParam);

		List<OperatorAdDTO>  dtoList = new ArrayList<>();

		for (OperatorAdBO operatorAdBO : list) {
			OperatorAdDTO dto = new OperatorAdDTO();
			dto.setId(operatorAdBO.getId());
			dto.setTitle(operatorAdBO.getTitle());
			dtoList.add(dto);
		}

		return successCreated(dtoList);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "soldOutAdByMerchantId")
	public Result soldOutAdByMerchantId(@RequestParam(value = "merchantId") Long merchantId){
		adService.soldOutAdByMerchantId(merchantId);
		return successCreated();
	}

	/**
	 * 删除全部索引数据
	 *
	 * @return
	 * @author meishuquan
	 */
	@RequestMapping(value = "delAllAdIndex", method = RequestMethod.GET)
	public Result delAllAdIndex() {
		solrService.delAllSolrDocs(adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		return successGet();
	}
	
	@RequestMapping(value = "selectMerchantNumByAdId", method = RequestMethod.GET)
	public Result<MerchantInfoDTO> selectMerchantNumByAdId(@RequestParam Long id){
		
		MerchantInfoBO bo = adService.selectMerchantNumByAdId(id);
		MerchantInfoDTO dto = new MerchantInfoDTO();
		dto.setMerchantNum(bo.getMerchantNum());
		dto.setTitle(bo.getTitle());
		
		return successGet(dto);
	}
	
	/**
	 * 运营平台强制下架广告
	 * @param id
	 * @param auditorId
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "downOperatorById", method = RequestMethod.PUT)
	public Result downOperatorById(@RequestParam Long id, @RequestParam Integer auditorId, @RequestParam String remark) {
		adService.downOperatorById(id, auditorId, remark);
		return successCreated();
	}
	
	/**
	 * 根据ID查询第三方支付时需要的参数
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectAdPayInfoById/{id}", method = RequestMethod.GET)
	public Result<AdPayInfoDTO> selectAdPayInfoById(@PathVariable Long id) {
		AdBO bo = adService.get(id);
		AdPayInfoDTO dto = new AdPayInfoDTO();
		dto.setMerchantRegionPath(bo.getMerchantRegionPath());
		dto.setTotalPoint(bo.getTotalPoint());
		return successCreated(dto);
	}
	
	/**
	 * 广告是否支付成功
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "isPay/{id}", method = RequestMethod.GET)
	public Result<Boolean> isPay(@PathVariable Long id) {
		Boolean flag = adService.isPay(id);
		return successCreated(flag);
	}
}
