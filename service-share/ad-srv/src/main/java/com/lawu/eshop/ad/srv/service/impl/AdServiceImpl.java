package com.lawu.eshop.ad.srv.service.impl;

import com.lawu.eshop.ad.constants.*;
import com.lawu.eshop.ad.param.*;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.bo.*;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.*;
import com.lawu.eshop.ad.srv.domain.AdDOExample.Criteria;
import com.lawu.eshop.ad.srv.domain.extend.*;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.FavoriteAdDOMapper;
import com.lawu.eshop.ad.srv.mapper.MemberAdRecordDOMapper;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.mapper.extend.AdDOMapperExtend;
import com.lawu.eshop.ad.srv.mapper.extend.MemberAdRecordDOMapperExtend;
import com.lawu.eshop.ad.srv.mapper.extend.PointPoolDOMapperExtend;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.ad.srv.service.FavoriteAdService;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.solr.service.SolrService;
import com.lawu.eshop.utils.AdArithmeticUtil;
import com.lawu.eshop.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * E赚接口实现类
 *
 * @author zhangrc
 * @date 2017/4/6
 */
@Service
public class AdServiceImpl implements AdService {

	@Autowired
	private AdDOMapper adDOMapper;

	@Autowired
	private AdDOMapperExtend adDOMapperExtend;

	@Autowired
	private FavoriteAdDOMapper favoriteAdDOMapper;

	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;

	@Autowired
	private PointPoolDOMapperExtend pointPoolDOMapperExtend;

	@Autowired
	private MemberAdRecordDOMapper memberAdRecordDOMapper;

	@Autowired
	private MemberAdRecordDOMapperExtend MemberAdRecordDOMapperExtend;

	@Autowired
	private FavoriteAdService favoriteAdService;

	@Autowired
	@Qualifier("adMerchantCutPointTransactionMainServiceImpl")
	private TransactionMainService<Reply> mctransactionMainAddService;

	@Autowired
	@Qualifier("adMerchantAddPointTransactionMainServiceImpl")
	private TransactionMainService<Reply> matransactionMainAddService;

	@Autowired
	@Qualifier("adUserAddPointTransactionMainServiceImpl")
	private TransactionMainService<Reply> adtransactionMainAddService;

	@Autowired
	@Qualifier("userClickAdTransactionMainServiceImpl")
	private TransactionMainService<Reply> userClicktransactionMainAddService;

	@Autowired
	@Qualifier("userSweepRedTransactionMainServiceImpl")
	private TransactionMainService<Reply> userSweepRedtransactionMainAddService;

	@Autowired
	private AdSrvConfig adSrvConfig;

	@Autowired
	private SolrService solrService;

	private int currentPage = 1;

	private int reportCurrentPage = 1;

	/**
	 * 商家发布E赚
	 *
	 * @param adSaveParam
	 * @return
	 */
	@Override
	@Transactional
	public Integer saveAd(AdSaveParam adSaveParam) {
		AdParam adParam = adSaveParam.getAdParam();
		AdDO adDO = new AdDO();
		adDO.setTitle(adParam.getTitle());
		adDO.setMerchantId(adSaveParam.getMerchantId());
		adDO.setMerchantNum(adSaveParam.getUserNum());
		adDO.setMerchantStoreId(adSaveParam.getMerchantStoreId());
		adDO.setMerchantStoreName(adSaveParam.getMerchantStoreName());
		adDO.setManageType(adSaveParam.getManageType().getVal());
		adDO.setLogoUrl(adSaveParam.getLogoUrl());
		adDO.setMerchantLatitude(adSaveParam.getLatitude());
		adDO.setMerchantLongitude(adSaveParam.getLongitude());
		adDO.setMediaUrl(adSaveParam.getMediaUrl());
		adDO.setVideoImgUrl(adSaveParam.getVideoImgUrl());
		adDO.setType(adParam.getTypeEnum().getVal());
		adDO.setPutWay(adParam.getPutWayEnum().val);
		adDO.setViewcount(0);
		adDO.setHits(0);
		if(adParam.getBeginTime()!=null){
			adDO.setBeginTime(DateUtil.formatDate(adParam.getBeginTime(), "yyyy-MM-dd HH:mm"));
		}
		adDO.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
		adDO.setPoint(adParam.getPoint());
		adDO.setAdCount(adParam.getAdCount());
		adDO.setRadius(adParam.getRadius());
		if (adParam.getTypeEnum().getVal() == AdTypeEnum.AD_TYPE_VIDEO.getVal()) {
			adDO.setStatus(AdStatusEnum.AD_STATUS_AUDIT.val); // 视频广告默认为审核中
		}
		if (adParam.getTypeEnum().getVal() == AdTypeEnum.AD_TYPE_PRAISE.getVal()) {
			adDO.setAdCount(adSaveParam.getCount());
		}
		adDO.setTotalPoint(adParam.getTotalPoint());
		adDO.setGmtCreate(new Date());
		adDO.setGmtModified(new Date());
		adDO.setAreas(adParam.getAreas());
		adDO.setRegionName(adParam.getRegionName());
		adDO.setContent(adParam.getContent());
		Integer i = adDOMapper.insert(adDO);
		if (adParam.getTypeEnum().getVal() == 3) { // E赞 红包
			savePointPool(adDO, adSaveParam.getCount());
		} else if (adParam.getTypeEnum().getVal() == 4) {
			saveRPPool(adDO);
		}
		// 发送消息，通知其他模块处理事务 积分的处理
		mctransactionMainAddService.sendNotice(adDO.getId());
		return i;
	}

	/**
	 * 分配积分
	 */
	public void savePointPool(AdDO adDO, Integer count) {
		// 算法生成积分明细
		Integer piontCount = count % 10 == 0 ? count / 10 : count / 10 + 1;
		if (piontCount <= 10)
			piontCount = 10;
		double[] points = AdArithmeticUtil.getMoney(adDO.getTotalPoint().doubleValue(), piontCount);
		for (int j = 0; j < piontCount; j++) {
			PointPoolDO pointPool = new PointPoolDO();
			pointPool.setAdId(adDO.getId());
			pointPool.setMerchantId(adDO.getMerchantId());
			pointPool.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
			pointPool.setType(PointPoolTypeEnum.AD_TYPE_PRAISE.val);
			pointPool.setGmtCreate(new Date());
			pointPool.setGmtModified(new Date());
			pointPool.setOrdinal(j);
			pointPool.setPoint(BigDecimal.valueOf(points[j]));
			pointPoolDOMapper.insert(pointPool);
		}
	}

	/**
	 * 分配红包
	 *
	 * @param adDO
	 */
	public void saveRPPool(AdDO adDO) {
		if (adDO.getPutWay() == 4) { // 普通红包
			BigDecimal point = adDO.getTotalPoint().divide(new BigDecimal(adDO.getAdCount()), 2, RoundingMode.HALF_UP);
			for (int j = 0; j < adDO.getAdCount(); j++) {
				PointPoolDO pointPool = new PointPoolDO();
				pointPool.setAdId(adDO.getId());
				pointPool.setMerchantId(adDO.getMerchantId());
				pointPool.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
				pointPool.setType(PointPoolTypeEnum.AD_TYPE_PACKET.val);
				pointPool.setGmtCreate(new Date());
				pointPool.setGmtModified(new Date());
				pointPool.setOrdinal(j);
				pointPool.setPoint(point);
				pointPoolDOMapper.insert(pointPool);
			}
		} else if (adDO.getPutWay() == 5) { // 手气红包
			List<Double> points = RedPacketArithmetic.spiltRedPackets(adDO.getTotalPoint().doubleValue(), adDO.getAdCount());
			for (int j = 0; j < adDO.getAdCount(); j++) {
				PointPoolDO pointPool = new PointPoolDO();
				pointPool.setAdId(adDO.getId());
				pointPool.setMerchantId(adDO.getMerchantId());
				pointPool.setStatus(PointPoolStatusEnum.AD_POINT_NO_GET.val);
				pointPool.setType(PointPoolTypeEnum.AD_TYPE_PACKET.val);
				pointPool.setGmtCreate(new Date());
				pointPool.setGmtModified(new Date());
				pointPool.setOrdinal(j);
				pointPool.setPoint(new BigDecimal(points.get(j)));
				pointPoolDOMapper.insert(pointPool);
			}
		}

	}

	/**
	 * 商家广告查询
	 *
	 * @param adMerchantParam
	 * @param merchantId
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMerchant(AdMerchantParam adMerchantParam, Long merchantId) {
		AdDOExample example = new AdDOExample();
		if (adMerchantParam.getStatusEnum() == null && adMerchantParam.getTypeEnum() == null && adMerchantParam.getPutWayEnum() == null) {
			example.createCriteria().andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val).andMerchantIdEqualTo(merchantId);
		} else {
			Criteria c1 = example.createCriteria();
			List<Byte> status = new ArrayList<>();

			if (adMerchantParam.getStatusEnum() != null) {
				if (adMerchantParam.getStatusEnum().val == 3) {
					status.add(AdStatusEnum.AD_STATUS_PUTED.val);
					status.add(AdStatusEnum.AD_STATUS_OUT.val);
					c1.andStatusIn(status).andMerchantIdEqualTo(merchantId);
					if (adMerchantParam.getTypeEnum() != null) {
						c1.andTypeEqualTo(adMerchantParam.getTypeEnum().getVal());
					}
					if (adMerchantParam.getPutWayEnum() != null) {
						c1.andPutWayEqualTo(adMerchantParam.getPutWayEnum().val);
					}

				} else {
					status.add(AdStatusEnum.AD_STATUS_ADD.val);
					status.add(AdStatusEnum.AD_STATUS_PUTING.val);
					status.add(AdStatusEnum.AD_STATUS_AUDIT.val);
					c1.andStatusIn(status).andMerchantIdEqualTo(merchantId);
					if (adMerchantParam.getTypeEnum() != null) {
						c1.andTypeEqualTo(adMerchantParam.getTypeEnum().getVal());
					}
					if (adMerchantParam.getPutWayEnum() != null) {
						c1.andPutWayEqualTo(adMerchantParam.getPutWayEnum().val);
					}
				}

			} else {
				c1.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val).andMerchantIdEqualTo(merchantId);
				if (adMerchantParam.getTypeEnum() != null) {
					c1.andTypeEqualTo(adMerchantParam.getTypeEnum().getVal());
				}
				if (adMerchantParam.getPutWayEnum() != null) {
					c1.andPutWayEqualTo(adMerchantParam.getPutWayEnum().val);
				}

			}

		}
		example.setOrderByClause("gmt_create " + adMerchantParam.getOrderType() + "");
		RowBounds rowBounds = new RowBounds(adMerchantParam.getOffset(), adMerchantParam.getPageSize());
		int count = (int) adDOMapper.countByExample(example);
		List<AdDO> DOS = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		for (AdDO adDO : DOS) {
			if (adDO.getType() == AdTypeEnum.AD_TYPE_PACKET.getVal()) {
				adDO.setMediaUrl(adSrvConfig.getAdDefaultMediaUrl());
			}
		}
		Page<AdBO> page = new Page<AdBO>();
		page.setCurrentPage(adMerchantParam.getCurrentPage());
		page.setTotalCount(count);
		page.setRecords(AdConverter.convertBOS(DOS));
		return page;
	}

	/**
	 * 运营平台(商家)对广告的管理(下架)
	 *
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public Integer updateStatus(Long id) {
		AdDO adDO = new AdDO();
		adDO.setId(id);
		adDO.setStatus(AdStatusEnum.AD_STATUS_OUT.val);
		Integer i = adDOMapper.updateByPrimaryKeySelective(adDO);
		AdDO ad = adDOMapper.selectByPrimaryKey(id);
		matransactionMainAddService.sendNotice(ad.getId());
		// 删除solr中的数据
		solrService.delSolrDocsById(adDO.getId(), adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		return i;
	}

	/**
	 * 运营平台(商家)对E赚的管理(删除)
	 *
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public Integer remove(Long id) {
		AdDO adDO = new AdDO();
		adDO.setId(id);
		adDO.setStatus(AdStatusEnum.AD_STATUS_DELETE.val);
		Integer i = adDOMapper.updateByPrimaryKeySelective(adDO);
		// 删除solr中的数据
		solrService.delSolrDocsById(adDO.getId(), adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		return i;
	}

	/**
	 * 运营平台对广告的查询
	 *
	 * @param adPlatParam
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByPlatForm(AdFindParam adPlatParam) {
		AdDOExample example = new AdDOExample();
		Criteria cr = example.createCriteria();
		cr.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
		if (adPlatParam.getPutWayEnum() != null) {
			cr.andPutWayEqualTo(adPlatParam.getPutWayEnum().val);
		} else if (adPlatParam.getTypeEnum() != null) {
			cr.andTypeEqualTo(adPlatParam.getTypeEnum().getVal());
		} else if (adPlatParam.getStatusEnum() != null) {
			cr.andStatusEqualTo(adPlatParam.getStatusEnum().val);
		} else if (adPlatParam.getBeginTime() != null && adPlatParam.getEndTime() != null) {
			cr.andGmtCreateBetween(adPlatParam.getBeginTime(), adPlatParam.getEndTime());
		}
		RowBounds rowBounds = new RowBounds(adPlatParam.getOffset(), adPlatParam.getPageSize());
		int count = (int) adDOMapper.countByExample(example);
		List<AdDO> DOS = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		Page<AdBO> page = new Page<AdBO>();
		page.setCurrentPage(adPlatParam.getCurrentPage());
		page.setTotalCount(count);
		page.setRecords(AdConverter.convertBOS(DOS));
		return page;
	}

	/**
	 * 会员对广告的观看
	 *
	 * @param adMemberParam
	 * @param memberId
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMember(AdMemberParam adMemberParam, Long memberId) {
		AdDOView adView = new AdDOView();
		if (adMemberParam.getTypeEnum() != null) { // E赚
			adView.setType(adMemberParam.getTypeEnum().getVal());
		}
		if (adMemberParam.getOrderTypeEnum() != null) { // 积分榜、人气榜
			adView.setTopType(adMemberParam.getOrderTypeEnum().getVal());
		}

		List<AdDO> DOS = adDOMapperExtend.selectAdAll(adView);
		List<AdBO> BOS = new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			AdBO BO = AdConverter.convertBO(adDO);
			BOS.add(BO);
		}
		Page<AdBO> page = new Page<AdBO>();
		page.setCurrentPage(adMemberParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}

	/**
	 * 查看E赚详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AdBO selectAbById(Long id,Long memberId) {
		AdDO adDO = adDOMapper.selectByPrimaryKey(id);
		FavoriteAdDOExample example = new FavoriteAdDOExample();
		example.createCriteria().andAdIdEqualTo(adDO.getId()).andMemberIdEqualTo(memberId);
		Long count = favoriteAdDOMapper.countByExample(example);
		AdBO adBO = AdConverter.convertBO(adDO);
		Long praiseCount = 0l;
		boolean isPraise = false;
		if (adDO.getType() == 3) { // 获取E赞的抢赞人数
			PointPoolDOExample ppexample = new PointPoolDOExample();
			ppexample.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PRAISE.val)
					.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_GET.val);
			praiseCount = pointPoolDOMapper.countByExample(ppexample);

			PointPoolDOExample ppexample2 = new PointPoolDOExample();
			ppexample2.createCriteria().andAdIdEqualTo(adDO.getId())
					.andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PRAISE.val)
					.andStatusEqualTo(PointPoolStatusEnum.AD_POINT_GET.val).andMemberIdEqualTo(memberId);
			Long number = pointPoolDOMapper.countByExample(ppexample2);
			if (number > 0) {
				isPraise = true;
			}
		}else{

			MemberAdRecordDOExample memberAdRecordDOExample=new MemberAdRecordDOExample();
			memberAdRecordDOExample.createCriteria().andAdIdEqualTo(id).andMemberIdEqualTo(memberId);
			Long clickCount= memberAdRecordDOMapper.countByExample(memberAdRecordDOExample);
			if (clickCount.intValue() > 0) {
				adBO.setIsClickAd(true);
			} else {
			 	adBO.setIsClickAd(false);
			}
		}
		adBO.setIsPraise(isPraise);
		adBO.setNumber(praiseCount.intValue());
		if (count.intValue() > 0) {
			adBO.setIsFavorite(true);
		} else {
			adBO.setIsFavorite(false);
		}
		return adBO;
	}

	@Override
	@Transactional
	public BigDecimal clickAd(Long id, Long memberId, String num) {
		AdDO adDO = adDOMapper.selectByPrimaryKey(id);

		MemberAdRecordDO memberAdRecordD = new MemberAdRecordDO();
		memberAdRecordD.setAdId(adDO.getId());
		memberAdRecordD.setPoint(adDO.getPoint().multiply(new BigDecimal(PropertyType.ad_commission_0_default)).multiply(new BigDecimal(PropertyType.ad_account_scale_default)));
		memberAdRecordD.setMemberId(memberId);
		memberAdRecordD.setMemberNum(num);
		memberAdRecordD.setStatus(new Byte("0"));
		memberAdRecordD.setGmtCreate(new Date());
		memberAdRecordD.setClickDate(new Date());
		memberAdRecordD.setOriginalPoint(adDO.getPoint());
		memberAdRecordDOMapper.insert(memberAdRecordD);
		
		//当前广告点击次数
		MemberAdRecordDOExample example = new MemberAdRecordDOExample();
		example.createCriteria().andAdIdEqualTo(id);
		Long clickCount= memberAdRecordDOMapper.countByExample(example);
		
		if (clickCount.intValue() + 1 >= adDO.getAdCount()) {
			adDO.setStatus(AdStatusEnum.AD_STATUS_PUTED.val); // 投放结束
			adDO.setGmtModified(new Date());
			adDOMapper.updateByPrimaryKey(adDO);
			// 删除solr中的数据
			solrService.delSolrDocsById(adDO.getId(), adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		}
		
		//异步修改广告点击次数和为用户添加积分
		new Thread(){
			public void run(){
				// 修改点击次数记录
				adDOMapperExtend.updateHitsByPrimaryKey(id);
				//发送消息修改积分
				userClicktransactionMainAddService.sendNotice(memberAdRecordD.getId());
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		return adDO.getPoint();
	}

	/**
	 * 视频审核
	 */
	@Override
	@Transactional
	public Integer auditVideo(Long id, Integer auditorId, String remark, AuditEnum auditEnum) {
		AdDO adDO = new AdDO();
		if (auditEnum.val.byteValue() == AuditEnum.AD_AUDIT_PASS.val) {
			adDO.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
			adDO.setRemark("");
		} else {
			adDO.setStatus(AdStatusEnum.AD_STATUS_AUDIT_FAIL.val);
			adDO.setRemark(remark);
		}
		adDO.setId(id);
		adDO.setAuditorId(auditorId);
		adDO.setAuditTime(new Date());
		Integer i = adDOMapper.updateByPrimaryKeySelective(adDO);
		if (auditEnum.val.byteValue() == AuditEnum.AD_AUDIT_PASS.val) {
			adDO = adDOMapper.selectByPrimaryKey(id);
			SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
			solrService.addSolrDocs(document, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		} else {
			// 审核不通过退还积分
			matransactionMainAddService.sendNotice(id);
		}
		return i;
	}

	/**
	 * e赞查询
	 */
	@Override
	public Page<AdBO> selectPraiseListByMember(AdPraiseParam adPraiseParam,Long memberId) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andTypeEqualTo(AdTypeEnum.AD_TYPE_PRAISE.getVal());
		if(adPraiseParam.getStatusEnum().getVal()==1){  //开枪中
			cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val);
			example.setOrderByClause("begin_time asc");
		}else if(adPraiseParam.getStatusEnum().getVal()==2){ //即将开始
			cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
			example.setOrderByClause("begin_time asc");
		}else if(adPraiseParam.getStatusEnum().getVal()==3){ //已结束
			cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTED.val);
			example.setOrderByClause("gmt_create desc");
		}

		List<AdDO> DOS = adDOMapper.selectByExample(example);
		List<AdBO> BOS = new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			AdBO BO = AdConverter.convertBO(adDO);
			BOS.add(BO);
		}
		Page<AdBO> page = new Page<AdBO>();
		page.setCurrentPage(adPraiseParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}

	/**
	 * 抢赞
	 */
	@Override
	@Transactional
	public BigDecimal clickPraise(Long id, Long memberId, String num) {
		// 查询出没有领取的积分，取出一个给用户
		PointPoolDO pointPoolDO = pointPoolDOMapperExtend.selectPoint(id);
		if (pointPoolDO == null) { // 说明积分领取完
			AdDO ad = new AdDO();
			ad.setId(memberId);
			ad.setStatus(AdStatusEnum.AD_STATUS_PUTED.val);
			ad.setGmtModified(new Date());
			adDOMapper.updateByPrimaryKeySelective(ad);
			return new BigDecimal(0);
		} else {
			pointPoolDO.setMemberId(memberId);
			pointPoolDO.setMemberNum(num);
			pointPoolDO.setStatus(PointPoolStatusEnum.AD_POINT_GET.val);
			pointPoolDO.setGmtModified(new Date());
			pointPoolDOMapper.updateByPrimaryKeySelective(pointPoolDO);
			// 给用户加积分
			adtransactionMainAddService.sendNotice(pointPoolDO.getId());
			return pointPoolDO.getPoint().multiply(new BigDecimal(PropertyType.ad_account_scale_default));
		}

	}

	/**
	 * 定时器改变状态
	 */

	@Override
	public void updatAdToPutting() {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val).andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal());
		List<AdDO> listADD = adDOMapper.selectByExample(example);
		if (!listADD.isEmpty()) {
			Collection<SolrInputDocument> documents = new ArrayList<>();
			for (AdDO adDO : listADD) {
				Date date = new Date();
				if (adDO.getBeginTime().getTime() <= date.getTime()) {
					adDO.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
					adDO.setGmtModified(date);
					adDOMapper.updateByPrimaryKey(adDO);
					if (adDO.getType() == AdTypeEnum.AD_TYPE_FLAT.getVal() || adDO.getType() == AdTypeEnum.AD_TYPE_VIDEO.getVal()) {
						SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
						documents.add(document);
					}
				}
			}
			solrService.addSolrDocsList(documents, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		}
	}

	@Override
	public void updatAdToPuted() {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val).andTypeEqualTo(AdTypeEnum.AD_TYPE_PRAISE.getVal());
		List<AdDO> listADD = adDOMapper.selectByExample(example);
		if (!listADD.isEmpty())
			for (AdDO adDO : listADD) {
				Date date = new Date();
				Calendar nowTime = Calendar.getInstance();
				nowTime.add(Calendar.MINUTE, -20);
				if ((nowTime.getTime().getTime() - adDO.getBeginTime().getTime()) > 0) {
					adDO.setStatus(AdStatusEnum.AD_STATUS_PUTED.val);
					adDO.setGmtModified(date);
					adDOMapper.updateByPrimaryKey(adDO);
					// 将没有领完的积分退还给用户
					matransactionMainAddService.sendNotice(adDO.getId());
				}
			}
	}

	@Override
	public Page<AdBO> selectChoiceness(AdMemberParam adMemberParam) {
		List<AdDO> DOS = adDOMapperExtend.selectChoiceness();
		List<AdBO> BOS = new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			if (adDO.getType() == 3) {
				AdBO BO = AdConverter.convertBO(adDO);
				BOS.add(BO);
			} else {
				if (adDO.getStatus() == 2) {
					AdBO BO = AdConverter.convertBO(adDO);
					BOS.add(BO);
				}
			}
		}
		Page<AdBO> page = new Page<AdBO>();
		page.setCurrentPage(adMemberParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}

	@Override
	public Integer selectRPIsSend(Long merchantId) {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal()).andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		int count = (int) adDOMapper.countByExample(example);
		return count;
	}

	@Override
	public BigDecimal getRedPacket(Long merchantId, Long memberId, String memberNum) {
		PointPoolDOExample ppexample = new PointPoolDOExample();
		ppexample.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PACKET.val).andStatusEqualTo(PointPoolStatusEnum.AD_POINT_NO_GET.val);
		// 查询出没有领取的积分，取出一个给用户
		List<PointPoolDO> list = pointPoolDOMapper.selectByExample(ppexample);
		if (!list.isEmpty()) {
			PointPoolDO pointPoolDO = list.get(0);
			pointPoolDO.setStatus(new Byte("1"));
			pointPoolDO.setMemberId(memberId);
			pointPoolDO.setMemberNum(memberNum);
			pointPoolDO.setGmtModified(new Date());
			pointPoolDOMapper.updateByPrimaryKeySelective(pointPoolDO);
			// 给用户加积分
			adtransactionMainAddService.sendNotice(pointPoolDO.getId());
			if (list.size() == 1) { // 红包领取完成 将红包下架
				AdDO ad = new AdDO();
				ad.setId(pointPoolDO.getAdId());
				ad.setGmtModified(new Date());
				ad.setStatus(AdStatusEnum.AD_STATUS_OUT.val);
				adDOMapper.updateByPrimaryKeySelective(ad);
			}
			return pointPoolDO.getPoint();
		} else {
			return new BigDecimal(0);
		}

	}

	@Override
	public AdBO get(Long id) {
		AdDO adDO = adDOMapper.selectByPrimaryKey(id);
		AdBO BO = AdConverter.convertBO(adDO);
		return BO;
	}

	@Override
	public Boolean selectRedPacketByMember(Long merchantId, Long memberId) {
		AdDOExample rpexample = new AdDOExample();
		rpexample.createCriteria().andStatusEqualTo(new Byte("1")).andMerchantIdEqualTo(merchantId);
		AdDO adDO = adDOMapper.selectByExample(rpexample).get(0);
		PointPoolDOExample example = new PointPoolDOExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PACKET.val).andAdIdEqualTo(adDO.getId());
		List<PointPoolDO> list = pointPoolDOMapper.selectByExample(example);
		return list.isEmpty() ? false : true;
	}

	@Override
	public ClickAdPointBO getClickAdPoint(Long memberId, BigDecimal point) {
		MemberAdRecordDO marDO = new MemberAdRecordDO();
		marDO.setMemberId(memberId);
		marDO.setClickDate(new Date());
		MemberAdRecordDOView view = MemberAdRecordDOMapperExtend.selectPointToday(marDO);
		ClickAdPointBO clickAdPointBO = new ClickAdPointBO();
		clickAdPointBO.setAdTotlePoint(point.multiply(new BigDecimal(PropertyType.ad_commission_0_default)).multiply(new BigDecimal(PropertyType.ad_account_scale_default)));
		clickAdPointBO.setAddPoint(view.getTotlePoint());
		return clickAdPointBO;
	}

	@Override
	public List<ViewBO> getAllAd() {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal()).andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val);
		RowBounds rowBounds = new RowBounds((currentPage - 1) * 100, 100);
		List<AdDO> list = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		int count = (int) adDOMapper.countByExample(example);
		int totalPageNum;
		if (count % 100 == 0) {
			totalPageNum = count / 100;
		} else {
			totalPageNum = count / 100 + 1;
		}
		if (currentPage >= totalPageNum) {
			currentPage = 1;
		} else {
			currentPage++;
		}
		List<ViewBO> bos = new ArrayList<>();
		for (AdDO adDO : list) {
			ViewBO bo = new ViewBO();
			bo.setId(adDO.getId());
			bo.setViewCount(adDO.getViewcount());
			bos.add(bo);
		}
		return bos;

	}

	@Override
	public void updateViewCount(Long id, Integer count) {
		AdDO adDO = new AdDO();
		adDO.setId(id);
		adDO.setViewcount(count);
		adDOMapper.updateByPrimaryKeySelective(adDO);
		// 更新solr广告浏览人数
		adDO = adDOMapper.selectByPrimaryKey(id);
		SolrInputDocument document = AdConverter.convertSolrUpdateDocument(adDO);
		solrService.addSolrDocs(document, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());

	}

	@Override
	public Page<AdBO> listAllAd(ListAdParam listAdParam) {
		AdDOExample example = new AdDOExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_PUTED.val);
		if (StringUtils.isNotEmpty(listAdParam.getSortName()) && StringUtils.isNotEmpty(listAdParam.getSortOrder())) {
			example.setOrderByClause("gmt_create " + listAdParam.getSortOrder());
		}
		if (StringUtils.isNotEmpty(listAdParam.getTitle())) {
			criteria.andTitleLike("%" + listAdParam.getTitle() + "%");
		}
		if (listAdParam.getTypeEnum() != null) {
			criteria.andTypeEqualTo(listAdParam.getTypeEnum().getVal());
		}
		if (listAdParam.getPutWayEnum() != null) {
			criteria.andPutWayEqualTo(listAdParam.getPutWayEnum().val);
		}
		if (listAdParam.getStatusEnum() != null) {
			criteria.andStatusEqualTo(listAdParam.getStatusEnum().val);
		}

		RowBounds rowBounds = new RowBounds(listAdParam.getOffset(), listAdParam.getPageSize());

		Page<AdBO> page = new Page<>();
		page.setCurrentPage(listAdParam.getCurrentPage());
		page.setTotalCount((int) adDOMapper.countByExample(example));

		List<AdDO> adDOS = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		page.setRecords(AdConverter.convertBOS(adDOS));
		return page;
	}

	@Override
	public AdBO selectById(Long id) {
		AdDO adDO = adDOMapper.selectByPrimaryKey(id);
		return AdConverter.convertBO(adDO);
	}

	@Override
	public void operatorUpdateAdStatus(Long id, AdStatusEnum adStatusEnum) {
		AdDO adDO = new AdDO();
		adDO.setId(id);
		adDO.setStatus(adStatusEnum.val);
		adDOMapper.updateByPrimaryKeySelective(adDO);
		if (adStatusEnum.val == AdStatusEnum.AD_STATUS_OUT.val) {
			matransactionMainAddService.sendNotice(id);
		}
		// 删除solr中的数据
		solrService.delSolrDocsById(adDO.getId(), adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
	}

	@Override
	public List<AdBO> listFlatVideoAd(ListAdParam listAdParam) {
		AdDOExample adDOExample = new AdDOExample();
		List<Byte> statusList = new ArrayList<>();
		statusList.add(AdStatusEnum.AD_STATUS_ADD.val);
		statusList.add(AdStatusEnum.AD_STATUS_PUTING.val);
		List<Byte> typeList = new ArrayList<>();
		typeList.add(AdTypeEnum.AD_TYPE_FLAT.getVal());
		typeList.add(AdTypeEnum.AD_TYPE_VIDEO.getVal());
		adDOExample.createCriteria().andStatusIn(statusList).andTypeIn(typeList);
		RowBounds rowBounds = new RowBounds(listAdParam.getOffset(), listAdParam.getPageSize());
		List<AdDO> adDOS = adDOMapper.selectByExampleWithRowbounds(adDOExample, rowBounds);
		return AdConverter.convertBOS(adDOS);
	}

	@Override
	public void updateAdIndex(Long id) {
		AdDO adDO = adDOMapper.selectByPrimaryKey(id);
		if (adDO == null) {
			return;
		}

		SolrDocument solrDocument = solrService.getSolrDocsById(id, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		if (solrDocument == null) {
			SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
			solrService.addSolrDocs(document, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		}
	}

	@Override
	public void rebuildAdIndex() {
		ListAdParam listAdParam = new ListAdParam();
		listAdParam.setPageSize(1000);
		int currentPage = 0;

		while (true) {
			currentPage++;
			listAdParam.setCurrentPage(currentPage);
			AdDOExample adDOExample = new AdDOExample();
			List<Byte> statusList = new ArrayList<>();
			statusList.add(AdStatusEnum.AD_STATUS_ADD.val);
			statusList.add(AdStatusEnum.AD_STATUS_PUTING.val);
			List<Byte> typeList = new ArrayList<>();
			typeList.add(AdTypeEnum.AD_TYPE_FLAT.getVal());
			typeList.add(AdTypeEnum.AD_TYPE_VIDEO.getVal());
			adDOExample.createCriteria().andStatusIn(statusList).andTypeIn(typeList);
			RowBounds rowBounds = new RowBounds(listAdParam.getOffset(), listAdParam.getPageSize());
			List<AdDO> adDOS = adDOMapper.selectByExampleWithRowbounds(adDOExample, rowBounds);
			if (adDOS == null || adDOS.isEmpty()) {
				return;
			}

			Collection<SolrInputDocument> documents = new ArrayList<>();
			for (AdDO adDO : adDOS) {
				SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
				documents.add(document);
			}
			solrService.addSolrDocsList(documents, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		}
	}

	@Override
	public void delInvalidAdIndex() {
		ListAdParam listAdParam = new ListAdParam();
		listAdParam.setPageSize(1000);
		int currentPage = 0;

		while (true) {
			currentPage++;
			listAdParam.setCurrentPage(currentPage);
			AdDOExample adDOExample = new AdDOExample();
			List<Byte> statusList = new ArrayList<>();
			statusList.add(AdStatusEnum.AD_STATUS_ADD.val);
			statusList.add(AdStatusEnum.AD_STATUS_PUTING.val);
			List<Byte> typeList = new ArrayList<>();
			typeList.add(AdTypeEnum.AD_TYPE_FLAT.getVal());
			typeList.add(AdTypeEnum.AD_TYPE_VIDEO.getVal());
			adDOExample.createCriteria().andStatusNotIn(statusList).andTypeIn(typeList);
			RowBounds rowBounds = new RowBounds(listAdParam.getOffset(), listAdParam.getPageSize());
			List<AdDO> adDOS = adDOMapper.selectByExampleWithRowbounds(adDOExample, rowBounds);
			if (adDOS == null || adDOS.isEmpty()) {
				return;
			}

			List<String> ids = new ArrayList<>();
			for (AdDO adDO : adDOS) {
				ids.add(String.valueOf(adDO.getId()));
			}
			solrService.delSolrDocsByIds(ids, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
		}
	}

	@Override
	public RedPacketInfoBO getRedPacketInfo(Long merchantId) {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val).andTypeEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal());
		List<AdDO> list = adDOMapper.selectByExample(example);
		RedPacketInfoBO redPacketInfoBO = new RedPacketInfoBO();
		if (!list.isEmpty()) {
			AdDO adDO = list.get(0);
			PointPoolDOExample ppexample = new PointPoolDOExample();
			ppexample.createCriteria().andAdIdEqualTo(adDO.getId());
			ppexample.setOrderByClause("point desc");
			List<PointPoolDO> ppList = pointPoolDOMapper.selectByExample(ppexample);
			redPacketInfoBO.setPoint(ppList.get(0).getPoint());
		}
		return redPacketInfoBO;

	}

	@Override
	public Boolean isExistsRedPacket(Long merchantId) {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal()).andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		long count = adDOMapper.countByExample(example);
		return count > 0 ? false : true;

	}

	@Override
	public Boolean isSendRedPacket(Long merchantId) {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal()).andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		long count = adDOMapper.countByExample(example);
		return count > 0 ? true : false;
	}

	@Override
	public void batchDeleteAd(List<Long> adIds) {
		adDOMapperExtend.batchDeleteAd(adIds);
		List<String> ids = new ArrayList<>();
		for (Long id : adIds) {
			ids.add(String.valueOf(id));
		}
		solrService.delSolrDocsByIds(ids, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(), adSrvConfig.getIsCloudSolr());
	}

	@Override
	public AdDetailBO selectDetail(Long id) {
		AdDO adDO=adDOMapper.selectByPrimaryKey(id);
		AdDetailBO detail=AdConverter.convertDetailBO(adDO);
		if(adDO.getType()==AdTypeEnum.AD_TYPE_FLAT.getVal() || adDO.getType()==AdTypeEnum.AD_TYPE_VIDEO.getVal()){
			detail.setAlreadyGetCount(adDO.getHits());
			detail.setNotGetCount(adDO.getAdCount()-adDO.getHits());
			detail.setAlreadyGetPoint(adDO.getPoint().multiply(BigDecimal.valueOf(adDO.getHits())));
			detail.setNotGetPoint(adDO.getTotalPoint().subtract(adDO.getPoint().multiply(BigDecimal.valueOf(adDO.getHits()))));

		}else if(adDO.getType()==AdTypeEnum.AD_TYPE_PRAISE.getVal()){
			PointPoolDOExample example =new PointPoolDOExample();
			example.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PRAISE.val)
				   .andStatusEqualTo(PointPoolStatusEnum.AD_POINT_GET.val);
			List<PointPoolDO>  ppList=pointPoolDOMapper.selectByExample(example);
			if(ppList.isEmpty()){
				detail.setAlreadyGetCount(0);
				detail.setNotGetCount(adDO.getAdCount());
				detail.setAlreadyGetPoint(BigDecimal.valueOf(0));
				detail.setNotGetPoint(adDO.getTotalPoint());
			}else{
				BigDecimal sumPoint=new BigDecimal(0);
				for (PointPoolDO pointPoolDO : ppList) {
					sumPoint=sumPoint.add(pointPoolDO.getPoint());
				}
				detail.setAlreadyGetCount(ppList.size());
				detail.setNotGetCount(adDO.getAdCount()-ppList.size());
				detail.setAlreadyGetPoint(sumPoint);
				detail.setNotGetPoint(adDO.getTotalPoint().subtract(sumPoint));
			}

		}else{
			PointPoolDOExample example =new PointPoolDOExample();
			example.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PACKET.val)
				   .andStatusEqualTo(PointPoolStatusEnum.AD_POINT_GET.val);
			List<PointPoolDO>  ppList=pointPoolDOMapper.selectByExample(example);
			if(ppList.isEmpty()){
				detail.setAlreadyGetCount(0);
				detail.setNotGetCount(adDO.getAdCount());
				detail.setAlreadyGetPoint(BigDecimal.valueOf(0));
				detail.setNotGetPoint(adDO.getTotalPoint());
			}else{
				BigDecimal sumPoint=new BigDecimal(0);
				for (PointPoolDO pointPoolDO : ppList) {
					sumPoint=sumPoint.add(pointPoolDO.getPoint());
				}
				detail.setAlreadyGetCount(ppList.size());
				detail.setNotGetCount(adDO.getAdCount()-ppList.size());
				detail.setAlreadyGetPoint(sumPoint);
				detail.setNotGetPoint(adDO.getTotalPoint().subtract(sumPoint));
				detail.setMediaUrl(adSrvConfig.getAdDefaultMediaUrl());
			}

		}
		return detail;
	}

	@Override
	public Boolean isMyData(Long id, Long merchantId) {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andIdEqualTo(id).andMerchantIdEqualTo(merchantId);
		int count = (int) adDOMapper.countByExample(example);
		return count > 0 ? true : false;
	}

	@Override
	public List<ReportAdBO> selectReportAdEarnings() {
		int count = adDOMapperExtend.selectReportAdEarningscount();
		int totalPageNum;
		if (count % 1000 == 0) {
			totalPageNum = count / 1000;
		} else {
			totalPageNum = count / 1000 + 1;
		}
		if (reportCurrentPage >= totalPageNum) {
			reportCurrentPage = 1;
		} else {
			reportCurrentPage++;
		}
		RowBounds rowBounds = new RowBounds(1000 * (reportCurrentPage - 1), 1000);
		List<ReportAdView> list = adDOMapperExtend.selectReportAdEarningsByRowbounds(rowBounds);
		List<ReportAdBO> listBO = new ArrayList<>();
		for (ReportAdView reportAdView : list) {
			ReportAdBO bo = new ReportAdBO();
			bo.setGmtCreate(reportAdView.getGmtCreate());
			bo.setId(reportAdView.getId());
			bo.setMerchantId(reportAdView.getMerchantId());
			bo.setMerchantNum(reportAdView.getMerchantNum());
			bo.setStatusEnum(AdStatusEnum.getEnum(reportAdView.getStatus()));
			bo.setTypeEnum(AdTypeEnum.getEnum(reportAdView.getType()));
			bo.setTotalPoint(reportAdView.getTotalPoint());
			bo.setTitle(reportAdView.getTitle());
			listBO.add(bo);
		}

		return listBO;
	}

	/**
	 * 查询E赚广告
	 *
	 * @param param
	 * @param memberId
	 * @return
	 */
	@Override
	public Page<AdEgainBO> selectPageAdEgain(Long memberId, AdEgainInternalParam param) {
		// 组装查询参数
		SelectAdEgainIdExample example = new SelectAdEgainIdExample();
		example.setLatitude(new BigDecimal(param.getLatitude()));
		example.setLongitude(new BigDecimal(param.getLongitude()));
		example.setMerchantIds(param.getMerchantIds());
		example.setAreas(param.getAreas());
		example.setType(param.getTypeEnum().getVal());
		// 查询E赚总数
		Long count = adDOMapperExtend.selectAdEgainCount(example);
		// 组装Page
		Page<AdEgainBO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(count.intValue());
		// 如果总数为0,或者总数小于等于offset
		if (count <= 0 || count <= param.getOffset()) {
			return page;
		}
		// 分页查询E赚id
		RowBounds rowBounds = new RowBounds(param.getCurrentPage(), param.getPageSize());
		List<Long> ids = adDOMapperExtend.selectPageAdEgainId(example, rowBounds);
		// 通过id查询E赚广告
		AdDOExample adDOExample = new AdDOExample();
		adDOExample.createCriteria().andIdIn(ids);
		List<AdDO> list = adDOMapper.selectByExample(adDOExample);

		// 组装E赞广告数据
		List<AdEgainBO> adEgainBOList = new ArrayList<>();
		for (AdDO item : list) {
			// 查询用户是否收藏该广告
			Boolean isFavorite = favoriteAdService.isFavoriteAd(item.getId(), memberId);
			adEgainBOList.add(AdConverter.convert(item, isFavorite));
		}
		page.setRecords(adEgainBOList);
		return page;
	}

	/**
	 * 查询积分排行版广告
	 *
	 * @param param
	 *            查询参数
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月19日
	 */
	@Override
	public List<AdPointBO> selectAdPoint(AdPointInternalParam param) {
		// 组装查询参数
		SelectAdPointIdExample example = new SelectAdPointIdExample();
		example.setLatitude(new BigDecimal(param.getLatitude()));
		example.setLongitude(new BigDecimal(param.getLongitude()));
		example.setMerchantIds(param.getMerchantIds());
		example.setAreas(param.getAreas());
		example.setOrderType(param.getOrderTypeEnum().getVal());
		example.setBeginTime(DateUtil.add(new Date(), -14, Calendar.DAY_OF_YEAR));
		// 查询积分排行榜广告id
		List<Long> ids = adDOMapperExtend.selectAdPointIdExample(example);
		if (ids == null || ids.isEmpty()) {
			return null;
		}
		// 通过id查询广告详细信息
		AdDOExample adDOExample = new AdDOExample();
		adDOExample.createCriteria().andIdIn(ids);
		List<AdDO> list = adDOMapper.selectByExample(adDOExample);
		return AdConverter.convert(list);
	}

	/**
	 * 查询精选推荐广告
	 *
	 * @param param
	 *            查询参数
	 * @param memberId
	 *            会员id
	 * @return
	 */
	@Override
	public Page<ChoicenessAdBO> selectPageChoicenessAd(Long memberId, AdChoicenessInternalParam param) {
		// 组装查询参数
		SelectChoicenessAdIdExample example = new SelectChoicenessAdIdExample();
		example.setLatitude(new BigDecimal(param.getLatitude()));
		example.setLongitude(new BigDecimal(param.getLongitude()));
		example.setMerchantIds(param.getMerchantIds());
		example.setAreas(param.getAreas());
		// 查询E赚总数
		Long count = adDOMapperExtend.selectChoicenessAdCount(example);
		// 组装Page
		Page<ChoicenessAdBO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(count.intValue());
		// 如果总数为0,或者总数小于等于offset
		if (count <= 0 || count <= param.getOffset()) {
			return page;
		}
		// 分页查询E赚id
		RowBounds rowBounds = new RowBounds(param.getCurrentPage(), param.getPageSize());
		List<Long> ids = adDOMapperExtend.selectPageChoicenessAdId(example, rowBounds);
		// 通过id查询E赚广告
		AdDOExample adDOExample = new AdDOExample();
		adDOExample.createCriteria().andIdIn(ids);
		List<AdDO> list = adDOMapper.selectByExample(adDOExample);

		// 组装E赞广告数据
		List<ChoicenessAdBO> choicenessAdBOList = new ArrayList<>();
		for (AdDO item : list) {
			// 查询用户是否收藏该广告
			Boolean isFavorite = favoriteAdService.isFavoriteAd(item.getId(), memberId);
			choicenessAdBOList.add(AdConverter.convertChoicenessAdBO(item, isFavorite));
		}
		page.setRecords(choicenessAdBOList);
		return page;
	}
}
