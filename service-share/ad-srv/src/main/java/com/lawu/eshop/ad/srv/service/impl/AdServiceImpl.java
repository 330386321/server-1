package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.RedPacketArithmetic;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.bo.ClickAdPointBO;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.domain.AdDOExample.Criteria;
import com.lawu.eshop.ad.srv.domain.AdRegionDO;
import com.lawu.eshop.ad.srv.domain.FavoriteAdDOExample;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDOExample;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDOExample;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.AdRegionDOMapper;
import com.lawu.eshop.ad.srv.mapper.FavoriteAdDOMapper;
import com.lawu.eshop.ad.srv.mapper.MemberAdRecordDOMapper;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.utils.AdArithmeticUtil;

/**
 * E赚接口实现类
 * @author zhangrc
 * @date 2017/4/6
 */
@Service
public class AdServiceImpl implements AdService {
	
	@Autowired
	private AdDOMapper adDOMapper;
	
	@Autowired
	private AdRegionDOMapper adRegionDOMapper;
	
	@Autowired
	private FavoriteAdDOMapper favoriteAdDOMapper;
	
	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;
	
	@Autowired
	private MemberAdRecordDOMapper memberAdRecordDOMapper;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	@Qualifier("adMerchantCutPointTransactionMainServiceImpl")
    private TransactionMainService mctransactionMainAddService;
	
	@Autowired
	@Qualifier("adMerchantAddPointTransactionMainServiceImpl")
    private TransactionMainService matransactionMainAddService;
	
	@Autowired
	@Qualifier("adUserAddPointTransactionMainServiceImpl")
    private TransactionMainService adtransactionMainAddService;

	/**
	 * 商家发布E赚
	 * @param adParam
	 * @param merchantId
	 * @return
	 */
	@Override
	@Transactional
	public Integer saveAd(AdSaveParam adSaveParam) {
		AdParam adParam= adSaveParam.getAdParam();
		AdDO adDO=new AdDO();
		adDO.setTitle(adParam.getTitle());
		adDO.setMerchantId(adSaveParam.getMerchantId());
		adDO.setMerchantNum(adSaveParam.getUserNum());
		adDO.setMerchantLatitude(adSaveParam.getLatitude());
		adDO.setMerchantLongitude(adSaveParam.getLongitude());
		adDO.setMediaUrl(adSaveParam.getMediaUrl());
		adDO.setType(adParam.getTypeEnum().val);
		adDO.setPutWay(adParam.getPutWayEnum().val);
		adDO.setBeginTime(adParam.getBeginTime());
		adDO.setEndTime(adParam.getEndTime());
		adDO.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
		adDO.setPoint(adParam.getPoint());
		adDO.setAdCount(adParam.getAdCount());
		adDO.setRadius(adParam.getRadius());
		if(adParam.getTypeEnum().val==2){
			adDO.setStatus(AdStatusEnum.AD_STATUS_AUDIT.val); //视频广告默认为审核中
		}
		adDO.setTotalPoint(adParam.getTotalPoint());
		adDO.setGmtCreate(new Date());
		adDO.setGmtModified(new Date());
		adDO.setAreas(adParam.getAreas());
		adDO.setContent(adParam.getContent());
		Integer i=adDOMapper.insert(adDO);
		if(adParam.getTypeEnum().val==3){ //E赞  红包
			savePointPool(adDO,adSaveParam.getCount());
		}else if(adParam.getTypeEnum().val==4){
			saveRPPool(adDO);
		}
		if(adParam.getAreas()!=null){ //不是全国投放
			String[] path=adParam.getAreas().split(",");
			for (String s : path) {
				AdRegionDO adRegionDO=new AdRegionDO();
				adRegionDO.setAdId(adDO.getId());
				adRegionDO.setMerchantId(adSaveParam.getMerchantId());
				adRegionDO.setGmtCreate(new Date());
				adRegionDO.setRegionId(s);
				adRegionDOMapper.insert(adRegionDO);
			}
			
		}
		//发送消息，通知其他模块处理事务 积分的处理
		mctransactionMainAddService.sendNotice(adDO.getId());
		//将广告添加到solr中
		if(adDO.getType()==1){
			SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
		    SolrUtil.addSolrDocs(document, SolrUtil.SOLR_AD_CORE);
		}
		return i;
	}
	
	/**
	 * 分配积分
	 */
	public void savePointPool(AdDO adDO,Integer count){	
		//算法生成积分明细
	    Integer piontCount=0;
		piontCount=count%10==0?count/10:count/10+1 ;
		if(piontCount<=10)
			 piontCount=10;
		double[] points=AdArithmeticUtil.getMoney(adDO.getTotalPoint().doubleValue(), piontCount);
		for (int j = 0; j < piontCount; j++) {
			PointPoolDO pointPool=new PointPoolDO();
			pointPool.setAdId(adDO.getId());
			pointPool.setMerchantId(adDO.getMerchantId());
			pointPool.setStatus(new Byte("0"));
			pointPool.setType(new Byte("1"));
			pointPool.setGmtCreate(new Date());
			pointPool.setGmtModified(new Date());
			pointPool.setOrdinal(j);
			pointPool.setPoint(new BigDecimal( points[j]));
			pointPoolDOMapper.insert(pointPool);
		}
	}
	
	/**
	 * 分配红包
	 * @param adDO
	 */
	public void saveRPPool(AdDO adDO){
		if(adDO.getPutWay()==4){ //普通红包
			BigDecimal point=adDO.getTotalPoint().divide(new BigDecimal(adDO.getAdCount()),2, RoundingMode.HALF_UP);
			for (int j = 0; j < adDO.getAdCount(); j++) {
				PointPoolDO pointPool=new PointPoolDO();
				pointPool.setAdId(adDO.getId());
				pointPool.setMerchantId(adDO.getMerchantId());
				pointPool.setStatus(new Byte("0"));
				pointPool.setType(new Byte("2"));
				pointPool.setGmtCreate(new Date());
				pointPool.setGmtModified(new Date());
				pointPool.setOrdinal(j);
				pointPool.setPoint(point);
				pointPoolDOMapper.insert(pointPool);
			}
		}else if(adDO.getPutWay()==5){ //手气红包
			List<Double> points=RedPacketArithmetic.spiltRedPackets(adDO.getTotalPoint().doubleValue(), adDO.getAdCount());
			for (int j = 0; j < adDO.getAdCount(); j++) {
				PointPoolDO pointPool=new PointPoolDO();
				pointPool.setAdId(adDO.getId());
				pointPool.setMerchantId(adDO.getMerchantId());
				pointPool.setStatus(new Byte("0"));
				pointPool.setType(new Byte("2"));
				pointPool.setGmtCreate(new Date());
				pointPool.setGmtModified(new Date());
				pointPool.setOrdinal(j);
				pointPool.setPoint(new BigDecimal( points.get(j)));
				pointPoolDOMapper.insert(pointPool);
			}
		}
		
	}

	
	
	/**
	 * 商家广告查询
	 * @param adMerchantParam
	 * @param merchantId
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMerchant(AdMerchantParam adMerchantParam, Long merchantId) {
		AdDOExample example=new AdDOExample();
		if(adMerchantParam.getStatusEnum()==null && adMerchantParam.getTypeEnum()==null && adMerchantParam.getPutWayEnum()==null ){
			example.createCriteria().andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val)
					.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_AUDIT.val)
					.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_OUT.val)
					.andMerchantIdEqualTo(merchantId);
		}else{
			Criteria c1=example.createCriteria();
			
			if(adMerchantParam.getStatusEnum()!=null){
				if(adMerchantParam.getStatusEnum().val==3){
					c1.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTED.val)
					.andMerchantIdEqualTo(merchantId);
					
				}else{
					c1.andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val)
					.andMerchantIdEqualTo(merchantId);
					Criteria  c2=example.createCriteria();
					 c2.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val)
					.andMerchantIdEqualTo(merchantId);
					 example.or(c2);
				}
				
			}
			if(adMerchantParam.getTypeEnum()!=null){
				c1.andTypeEqualTo(adMerchantParam.getTypeEnum().val)
				.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val)
				.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_AUDIT.val)
				.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_OUT.val)
				.andMerchantIdEqualTo(merchantId);
				
			}
			if(adMerchantParam.getPutWayEnum()!=null){
				c1.andTypeEqualTo(adMerchantParam.getPutWayEnum().val)
				.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val)
				.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_AUDIT.val)
				.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_OUT.val)
				.andMerchantIdEqualTo(merchantId);
			}
			
			
		}
		 example.setOrderByClause("gmt_create "+adMerchantParam.getOrderType()+"");
		 RowBounds rowBounds = new RowBounds(adMerchantParam.getOffset(), adMerchantParam.getPageSize());
		 Long count=adDOMapper.countByExample(example);
		 List<AdDO> DOS=adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		 Page<AdBO> page=new Page<AdBO>();
		 page.setCurrentPage(adMerchantParam.getCurrentPage());
		 page.setTotalCount(count.intValue());
		 page.setRecords(AdConverter.convertBOS(DOS));
		return page;
	}

	/**
	 * 运营平台(商家)对E赚的管理(下架)
	 * @param statusEnum
	 * @return
	 */
	@Override
	@Transactional
	public Integer updateStatus( Long id) {
		AdDO adDO=new AdDO();
		adDO.setId(id);
		adDO.setStatus(new Byte("4"));
		Integer i=adDOMapper.updateByPrimaryKeySelective(adDO);
		AdDO ad= adDOMapper.selectByPrimaryKey(id);
		if(ad.getType()==3){ //E赞 下架 退还积分
			matransactionMainAddService.sendNotice(ad.getId());
		}else{
			matransactionMainAddService.sendNotice(id);
		}
		//删除solr中的数据
		SolrUtil.delSolrDocsById(adDO.getId(), SolrUtil.SOLR_AD_CORE);
		return i;
	}
	
	
	/**
	 * 运营平台(商家)对E赚的管理(删除)
	 * @param statusEnum
	 * @return
	 */
	@Override
	@Transactional
	public Integer remove( Long id) {
		AdDO adDO=new AdDO();
		adDO.setId(id);
		adDO.setStatus(AdStatusEnum.AD_STATUS_DELETE.val);
		Integer i=adDOMapper.updateByPrimaryKeySelective(adDO);
		//删除solr中的数据
		SolrUtil.delSolrDocsById(adDO.getId(), SolrUtil.SOLR_AD_CORE);
		return i;
	}

	/**
	 * 运营平台对广告的查询
	 * @param adMerchantParam
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByPlatForm(AdFindParam adPlatParam) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
		if(adPlatParam.getPutWayEnum()!=null){
			cr.andPutWayEqualTo(adPlatParam.getPutWayEnum().val);
		}else if(adPlatParam.getTypeEnum()!=null){
			cr.andTypeEqualTo(adPlatParam.getTypeEnum().val);
		}else if(adPlatParam.getStatusEnum()!=null){
			cr.andStatusEqualTo(adPlatParam.getStatusEnum().val);
		}else if(adPlatParam.getBeginTime()!=null && adPlatParam.getEndTime()!=null){
			cr.andGmtCreateBetween(adPlatParam.getBeginTime(), adPlatParam.getEndTime());
		}
		 RowBounds rowBounds = new RowBounds(adPlatParam.getOffset(), adPlatParam.getPageSize());
		 Long count=adDOMapper.countByExample(example);
		 List<AdDO> DOS=adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		 Page<AdBO> page=new Page<AdBO>();
		 page.setCurrentPage(adPlatParam.getCurrentPage());
		 page.setTotalCount(count.intValue());
		 page.setRecords(AdConverter.convertBOS(DOS));
		return page;
	}

	/**
	 * 会员对广告的观看
	 * @param adMerchantParam
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMember(AdMemberParam adMemberParam,Long memberId) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		Criteria cr2= example.createCriteria();
		cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val).andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PACKET.val);
		cr2.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val); //投放中
		example.or(cr2);
		if(adMemberParam.getTypeEnum()!=null){
			cr.andTypeEqualTo(adMemberParam.getTypeEnum().val);
			cr2.andTypeEqualTo(adMemberParam.getTypeEnum().val);
		}
		if(adMemberParam.getOrderTypeEnum()!=null){
			if(adMemberParam.getOrderTypeEnum().val==1){
				example.setOrderByClause("total_point desc");
				cr.andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PRAISE.val);
			}else{
				example.setOrderByClause("point desc");
				cr.andAdCountGreaterThanOrEqualTo(20);
				cr.andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PRAISE.val);
			}
		}
		
		List<AdDO> DOS=adDOMapper.selectByExample(example);
		List<AdBO> BOS=new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			FavoriteAdDOExample fAexample=new FavoriteAdDOExample();
			fAexample.createCriteria().andAdIdEqualTo(adDO.getId()).andMemberIdEqualTo(memberId);
			Long count=favoriteAdDOMapper.countByExample(fAexample);
			AdBO BO=AdConverter.convertBO(adDO);
			if(count.intValue()>0){
				BO.setIsFavorite(true);
			}else{
				BO.setIsFavorite(false);
			}
			BOS.add(BO);
		}
		Page<AdBO> page=new Page<AdBO>();
		page.setCurrentPage(adMemberParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}
	
	
	/**
	 * 查看E赚详情
	 * @param id
	 * @return
	 */
	@Override
	public AdBO selectAbById(Long id,Long memberId) {
		AdDO adDO=adDOMapper.selectByPrimaryKey(id);
		FavoriteAdDOExample example=new FavoriteAdDOExample();
		example.createCriteria().andAdIdEqualTo(adDO.getId()).andMemberIdEqualTo(memberId);
		Long count=favoriteAdDOMapper.countByExample(example);
		AdBO adBO=AdConverter.convertBO(adDO);
		Long praiseCount=0l;
		boolean isPraise=false;
		if(adDO.getType()==3){ //获取E赞的抢赞人数
			PointPoolDOExample ppexample=new PointPoolDOExample();
			ppexample.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(new Byte("1"))
					                   .andStatusEqualTo(new Byte("1"));
			praiseCount=pointPoolDOMapper.countByExample(ppexample);
			
			PointPoolDOExample ppexample2=new PointPoolDOExample();
			ppexample2.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(new Byte("1"))
					                   .andStatusEqualTo(new Byte("1")).andMemberIdEqualTo(memberId);
			Long number=pointPoolDOMapper.countByExample(ppexample2);
			if(number>0){
				isPraise=true;
			}
		}
		adBO.setIsPraise(isPraise);
		adBO.setNumber(praiseCount.intValue());
		if(count.intValue()>0){
			adBO.setIsFavorite(true);
		}else{
			adBO.setIsFavorite(false);
		}
		
		return adBO;
	}
	
	@Override
	public Integer clickAd(Long id, Long memberId,String num) {
		AdDO adDO=adDOMapper.selectByPrimaryKey(id);
		Integer hits= adDO.getHits();
		if(hits==null)
			hits=0;
		//平面和视频点击次数加一
		int i=0;
		if(adDO.getType()!=3 && hits<adDO.getAdCount()){
			hits+=1;
			adDO.setHits(hits);
			adDOMapper.updateByPrimaryKey(adDO);
			MemberAdRecordDO memberAdRecordD=new MemberAdRecordDO();
			memberAdRecordD.setAdId(adDO.getId());
			memberAdRecordD.setPoint(adDO.getPoint());
			memberAdRecordD.setMemberId(memberId);
			memberAdRecordD.setMemberNum(num);
			memberAdRecordD.setGmtCreate(new Date());
			memberAdRecordD.setClickDate(new Date());
			i=memberAdRecordDOMapper.insert(memberAdRecordD);
			adtransactionMainAddService.sendNotice(adDO.getId());
		}else if(hits==adDO.getAdCount()){
			adDO.setStatus(AdStatusEnum.AD_STATUS_PUTED.val); //投放结束
			adDOMapper.updateByPrimaryKey(adDO);
		}  
		return i;
	}


	/**
	 * 视频审核
	 */
	@Override
	@Transactional
	public Integer auditVideo(Long id) {
		AdDO adDO=new AdDO();
		adDO.setId(id);
		adDO.setStatus(new Byte("1"));
		Integer i=adDOMapper.updateByPrimaryKeySelective(adDO);
		SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
	    SolrUtil.addSolrDocs(document, SolrUtil.SOLR_AD_CORE);
		return i;
	}

	/**
	 * e赞查询
	 */
	@Override
	public Page<AdBO> selectPraiseListByMember(AdPraiseParam adPraiseParam) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andTypeEqualTo(AdTypeEnum.AD_TYPE_PRAISE.val); 
		if(adPraiseParam.getStatusEnum().val==1){  //开枪中
			cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val);
		}else if(adPraiseParam.getStatusEnum().val==2){ //即将开始
			cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		}else if(adPraiseParam.getStatusEnum().val==3){ //已结束
			cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTED.val);
		}
		List<AdDO> DOS=adDOMapper.selectByExample(example);
		List<AdBO> BOS=new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			PointPoolDOExample ppexample=new PointPoolDOExample();
			ppexample.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(new Byte("1"))
					                   .andStatusEqualTo(new Byte("1"));
			 Long count=pointPoolDOMapper.countByExample(ppexample);
			 AdBO BO=AdConverter.convertBO(adDO);
			 BO.setNumber(count.intValue());
			 BOS.add(BO);
		}
		Page<AdBO> page=new Page<AdBO>();
		page.setCurrentPage(adPraiseParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}

	/**
	 * 抢赞
	 */
	@Override
	public BigDecimal clickPraise(Long id,Long memberId,String num) {
		PointPoolDOExample ppexample=new PointPoolDOExample();
		ppexample.createCriteria().andAdIdEqualTo(id).andTypeEqualTo(new Byte("1"))
				                   .andStatusEqualTo(new Byte("0"));
		//查询出没有领取的积分，取出一个给用户
		List<PointPoolDO>  list=pointPoolDOMapper.selectByExample(ppexample); 
		if(list.isEmpty()){ //说明积分领取完
			AdDO ad=new AdDO();
			ad.setId(memberId);
			ad.setStatus(AdStatusEnum.AD_STATUS_PUTED.val);
			adDOMapper.updateByPrimaryKeySelective(ad);
			return new BigDecimal(0);
		}else{
			PointPoolDO pointPoolDO=list.get(0);
			pointPoolDO.setMemberId(memberId);
			pointPoolDO.setMemberNum(num);
			pointPoolDO.setStatus(new Byte("1"));
			pointPoolDOMapper.updateByPrimaryKeySelective(pointPoolDO);
			//给用户加积分
			adtransactionMainAddService.sendNotice(pointPoolDO.getId());
			return pointPoolDO.getPoint();
		}
		
	}

	/**
	 * 定时器改变状态
	 */
	@Override
	public void updateRacking() {
		AdDOExample example=new AdDOExample();
		example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		 List<AdDO> listADD=adDOMapper.selectByExample(example);
		 if(!listADD.isEmpty())
			for (AdDO adDO : listADD) {
				Date date=new Date();
				if(adDO.getBeginTime().getTime()==date.getTime()){
					adDO.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
					adDOMapper.updateByPrimaryKey(adDO);
					SolrInputDocument document = new SolrInputDocument();
					document.addField("id", adDO.getId());
					document.addField("status_s", 3);
				    SolrUtil.addSolrDocs(document, SolrUtil.SOLR_AD_CORE);
				}
			}
		 
		 AdDOExample example2=new AdDOExample();
		 example2.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val);
		 List<AdDO> listPutIng=adDOMapper.selectByExample(example);
		 if(!listPutIng.isEmpty())
				for (AdDO adDO : listPutIng) {
					Date date=new Date();
					if((date.getTime()-adDO.getBeginTime().getTime())>adDO.getEndTime().getTime()){
						adDO.setStatus(AdStatusEnum.AD_STATUS_PUTED.val);
						adDOMapper.updateByPrimaryKey(adDO);
						//删除solr中的数据
						SolrInputDocument document = new SolrInputDocument();
						document.addField("id", adDO.getId());
						document.addField("status_s", 1);
					    SolrUtil.addSolrDocs(document, SolrUtil.SOLR_AD_CORE);
					}
				}
		
	}

	@Override
	public Page<AdBO> selectChoiceness(AdMemberParam adMemberParam) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val).andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PACKET.val);
		Criteria cr2= example.createCriteria();
		cr2.andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		example.or(cr2);
		example.setOrderByClause("gmt_create desc");
		if(adMemberParam.getTypeEnum()!=null){
			cr.andTypeEqualTo(adMemberParam.getTypeEnum().val);
		}
		List<AdDO> DOS=adDOMapper.selectByExample(example);
		List<AdBO> BOS=new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			if(adDO.getType()==3){
				AdBO BO=AdConverter.convertBO(adDO);
				BOS.add(BO);
			}else{
				if(adDO.getStatus()==2){
					AdBO BO=AdConverter.convertBO(adDO);
					BOS.add(BO);
				}
			}
			
		}
		Page<AdBO> page=new Page<AdBO>();
		page.setCurrentPage(adMemberParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}

	@Override
	public Integer selectRPIsSend(Long merchantId) {
		AdDOExample example=new AdDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(AdTypeEnum.AD_TYPE_PACKET.val);
		 Long count=adDOMapper.countByExample(example);
		return count.intValue();
	}

	
	@Override
	public BigDecimal getRedPacket(Long merchantId,Long memberId,String memberNum) {
		PointPoolDOExample ppexample=new PointPoolDOExample();
		ppexample.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(new Byte("2"))
				                   .andStatusEqualTo(new Byte("0"));
		//查询出没有领取的积分，取出一个给用户
		List<PointPoolDO>  list=pointPoolDOMapper.selectByExample(ppexample);
		if(!list.isEmpty()){ 
			PointPoolDO pointPoolDO=list.get(0);
			pointPoolDO.setStatus(new Byte("1"));
			pointPoolDO.setMemberId(memberId);
			pointPoolDO.setMemberNum(memberNum);
			pointPoolDOMapper.updateByPrimaryKeySelective(pointPoolDO);
			//给用户加积分
			adtransactionMainAddService.sendNotice(pointPoolDO.getId());
			return pointPoolDO.getPoint();
		}else{
			return new BigDecimal(0);
		}
		
	}

	@Override
	public AdBO get(Long id) {
		AdDO adDO=adDOMapper.selectByPrimaryKey(id);
		AdBO BO=AdConverter.convertBO(adDO);
		return BO;
	}
	
	@Override
	public Boolean selectRedPacketByMember(Long merchantId, Long memberId) {
		AdDOExample rpexample =new AdDOExample();
		rpexample.createCriteria().andStatusEqualTo(new Byte("1")).andMerchantIdEqualTo(merchantId);
		AdDO adDO=adDOMapper.selectByExample(rpexample).get(0);
		PointPoolDOExample example=new PointPoolDOExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andTypeEqualTo(new Byte("1")).andAdIdEqualTo(adDO.getId());
		List<PointPoolDO> list=pointPoolDOMapper.selectByExample(example);
		if(list.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public ClickAdPointBO getClickAdPoint(Long memberId,Long adId) {
		MemberAdRecordDOExample example=new MemberAdRecordDOExample();
		example.createCriteria().andClickDateEqualTo(new Date()).andMemberIdEqualTo(memberId);
		List<MemberAdRecordDO> list= memberAdRecordDOMapper.selectByExample(example);
		BigDecimal totlePoint=new BigDecimal(0);
		if(!list.isEmpty()){
			for (MemberAdRecordDO memberAdRecordDO : list) {
				totlePoint=totlePoint.add(memberAdRecordDO.getPoint());
			}
		}
		AdDO adDO=adDOMapper.selectByPrimaryKey(adId);
		ClickAdPointBO clickAdPointBO=new ClickAdPointBO();
		clickAdPointBO.setAdTotlePoint(adDO.getTotalPoint());
		clickAdPointBO.setAddPoint(totlePoint);
		return clickAdPointBO;
	}

	@Override
	public List<Long> getAllAd() {
		AdDOExample example =new AdDOExample();
		example.createCriteria().andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PACKET.val).andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
		List<AdDO> list=adDOMapper.selectByExample(example);
		
		List<Long> ids=new ArrayList<>();
		for (AdDO adDO : list) {
			ids.add(adDO.getId());
		}
		return ids;
		
	}

	@Override
	public void updateViewCount(Long id,Integer count) {
		AdDO adDO=new  AdDO();
		adDO.setId(id);
		adDO.setViewcount(count);
		adDOMapper.updateByPrimaryKeySelective(adDO);
	}

}
