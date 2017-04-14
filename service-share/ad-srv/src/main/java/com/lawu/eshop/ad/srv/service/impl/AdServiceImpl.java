package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.domain.AdDOExample.Criteria;
import com.lawu.eshop.ad.srv.domain.AdRegionDO;
import com.lawu.eshop.ad.srv.domain.FavoriteAdDOExample;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;
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
	public Integer saveAd(AdParam adParam, Long merchantId,String mediaUrl,Integer count,String num) {
		AdDO adDO=new AdDO();
		adDO.setTitle(adParam.getTitle());
		adDO.setMerchantId(merchantId);
		adDO.setMerchantNum(num);
		adDO.setMediaUrl(mediaUrl);
		adDO.setType(adParam.getTypeEnum().val);
		adDO.setPutWay(adParam.getPutWayEnum().val);
		adDO.setBeginTime(adParam.getBeginTime());
		adDO.setEndTime(adParam.getEndTime());
		adDO.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
		if(adParam.getTypeEnum().val!=3){
			adDO.setPoint(adParam.getPoint());
			adDO.setAdCount(adParam.getAdCount());
			if(adParam.getPutWayEnum().val==3)
				adDO.setRadius(adParam.getRadius());
		}else if(adParam.getTypeEnum().val==2){
			adDO.setStatus(AdStatusEnum.AD_STATUS_DELETE.val); //视频广告默认为审核中
		}
		adDO.setTotalPoint(adParam.getTotalPoint());
		adDO.setGmtCreate(new Date());
		adDO.setGmtModified(new Date());
		if(adParam.getAreas()!=null) adDO.setAreas(adParam.getAreas());
		if(adParam.getContent()!=null) adDO.setContent(adParam.getContent());
		Integer i=adDOMapper.insert(adDO);
		if(adParam.getTypeEnum().val==3){ //E赞
			savePointPool(adDO,count);
		}
		if(adParam.getAreas()!=null){ //不是全国投放
			String[] path=adParam.getAreas().split("/");
			for (String s : path) {
				AdRegionDO adRegionDO=new AdRegionDO();
				adRegionDO.setAdId(adDO.getId());
				adRegionDO.setMerchantId(merchantId);
				adRegionDO.setGmtCreate(new Date());
				adRegionDO.setRegionId(s);
				adRegionDOMapper.insert(adRegionDO);
			}
			
		}
		//发送消息，通知其他模块处理事务 积分的处理
		mctransactionMainAddService.sendNotice(adDO.getId());
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
	 * 商家广告查询
	 * @param adMerchantParam
	 * @param merchantId
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMerchant(AdMerchantParam adMerchantParam, Long merchantId) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andMerchantIdEqualTo(merchantId).andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
		if(adMerchantParam.getPutWayEnum()!=null){
			cr.andPutWayEqualTo(adMerchantParam.getPutWayEnum().val);
		}else if(adMerchantParam.getTypeEnum()!=null){
			cr.andTypeEqualTo(adMerchantParam.getTypeEnum().val);
		}else if(adMerchantParam.getStatusEnum()!=null){
			cr.andStatusEqualTo(adMerchantParam.getStatusEnum().val);
		}else if(adMerchantParam.getBeginTime()!=null && adMerchantParam.getEndTime()!=null){
			cr.andGmtCreateBetween(adMerchantParam.getBeginTime(), adMerchantParam.getEndTime());
		}
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
		return i;
	}

	/**
	 * 运营平台对E赚的查询
	 * @param adMerchantParam
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByPlatForm(AdMerchantParam adMerchantParam) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andStatusNotEqualTo(AdStatusEnum.AD_STATUS_DELETE.val);
		if(adMerchantParam.getPutWayEnum()!=null){
			cr.andPutWayEqualTo(adMerchantParam.getPutWayEnum().val);
		}else if(adMerchantParam.getTypeEnum()!=null){
			cr.andTypeEqualTo(adMerchantParam.getTypeEnum().val);
		}else if(adMerchantParam.getStatusEnum()!=null){
			cr.andStatusEqualTo(adMerchantParam.getStatusEnum().val);
		}else if(adMerchantParam.getBeginTime()!=null && adMerchantParam.getEndTime()!=null){
			cr.andGmtCreateBetween(adMerchantParam.getBeginTime(), adMerchantParam.getEndTime());
		}
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
	 * 会员对广告的观看
	 * @param adMerchantParam
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMember(AdMemberParam adMemberParam) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val); //投放中
		if(adMemberParam.getOrderTypeEnum()!=null){
			if(adMemberParam.getOrderTypeEnum().val==1){
				example.setOrderByClause("gmt_create desc");
			}else if(adMemberParam.getOrderTypeEnum().val==2){
				example.setOrderByClause("total_point desc");
				cr.andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PRAISE.val);
			}else if(adMemberParam.getOrderTypeEnum().val==3){
				example.setOrderByClause("point desc");
				cr.andAdCountGreaterThanOrEqualTo(20);
				cr.andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PRAISE.val);
			}
		}
		if(adMemberParam.getTypeEnum()!=null){
			cr.andTypeEqualTo(adMemberParam.getTypeEnum().val);
		}
		List<AdDO> DOS=adDOMapper.selectByExample(example);
		List<AdBO> BOS=new ArrayList<AdBO>();
		for (AdDO adDO : DOS) {
			FavoriteAdDOExample adExample=new FavoriteAdDOExample();
			adExample.createCriteria().andAdIdEqualTo(adDO.getId());
			Long attenCount=favoriteAdDOMapper.countByExample(adExample);
			AdBO BO=AdConverter.convertBO(adDO);
			BO.setAttenCount(attenCount.intValue());
			BOS.add(BO);
		}
		Page<AdBO> page=new Page<AdBO>();
		page.setCurrentPage(adMemberParam.getCurrentPage());
		page.setRecords(BOS);
		return page;
	}
	
	
	/**
	 * 查看E赚详情,点击次数加1,点击次数和投放广告数相等则投放结束
	 * @param id
	 * @return
	 */
	@Override
	public AdBO selectAbById(Long id) {
		AdDO adDO=adDOMapper.selectByPrimaryKey(id);
		FavoriteAdDOExample example=new FavoriteAdDOExample();
		example.createCriteria().andAdIdEqualTo(adDO.getId());
		Long count=favoriteAdDOMapper.countByExample(example);
		AdBO adBO=AdConverter.convertBO(adDO);
		Long number=0l;
		if(adDO.getType()==3){
			PointPoolDOExample ppexample=new PointPoolDOExample();
			ppexample.createCriteria().andAdIdEqualTo(adDO.getId()).andTypeEqualTo(new Byte("1"))
					                   .andStatusEqualTo(new Byte("1"));
			number=pointPoolDOMapper.countByExample(ppexample);
		}
		adBO.setNumber(number.intValue());
		adBO.setAttenCount(count.intValue());
		return adBO;
	}
	
	@Override
	public Integer clickAd(Long id, Long memberId) {
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
			memberAdRecordD.setMemberId(memberId);
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
	public Integer clickPraise(Long id,Long memberId,String num) {
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
			return 1;
		}else{
			PointPoolDO pointPoolDO=list.get(0);
			pointPoolDO.setMemberId(memberId);
			pointPoolDO.setMemberNum(num);
			pointPoolDO.setStatus(new Byte("1"));
			pointPoolDOMapper.updateByPrimaryKeySelective(pointPoolDO);
			//给用户加积分
			adtransactionMainAddService.sendNotice(pointPoolDO.getId());
			return 2;
		}
		
	}

	

}
