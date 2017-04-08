package com.lawu.eshop.ad.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdParam;
import com.lawu.eshop.ad.srv.bo.AdBO;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.domain.AdDOExample.Criteria;
import com.lawu.eshop.ad.srv.domain.AdRegionDO;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.AdRegionDOMapper;
import com.lawu.eshop.ad.srv.service.AdService;
import com.lawu.eshop.framework.core.page.Page;

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
	
	//@Autowired
    //private TransactionMainService transactionMainService;

	/**
	 * 商家发布E赚
	 * @param adParam
	 * @param merchantId
	 * @return
	 */
	@Override
	@Transactional
	public Integer saveAd(AdParam adParam, Long merchantId,String mediaUrl) {
		AdDO adDO=new AdDO();
		adDO.setTitle(adParam.getTitle());
		adDO.setMerchantId(merchantId);
		adDO.setMediaUrl(mediaUrl);
		adDO.setAdCount(adParam.getAdCount());
		adDO.setType(adParam.getTypeEnum().val);
		adDO.setPutWay(adParam.getPutWayEnum().val);
		if(adParam.getPutWayEnum().val==3){
			adDO.setRadius(adParam.getRadius());
		}
		adDO.setBeginTime(adParam.getBeginTime());
		adDO.setEndTime(adParam.getEndTime());
		if(adParam.getPoint()!=null){
			adDO.setPoint(adParam.getPoint());
		}
		adDO.setTotalPoint(adParam.getTotalPoint());
		if(adParam.getTypeEnum().val==2){
			adDO.setStatus(new Byte("5")); //视频广告默认为审核中
		}else{
			adDO.setStatus(new Byte("1"));//其余的为上架中
		}
		adDO.setGmtCreate(new Date());
		adDO.setGmtModified(new Date());
		if(adParam.getAreas()!=null){ 
			adDO.setAreas(adParam.getAreas());
		}
		if(adParam.getContent()!=null)
		adDO.setContent(adParam.getContent());
		Integer id=adDOMapper.insert(adDO);
		if(adParam.getAreas()!=null){ //不是全国投放
			String[] path=adParam.getAreas().split("/");
			for (String s : path) {
				AdRegionDO adRegionDO=new AdRegionDO();
				adRegionDO.setAdId(Long.valueOf(id));
				adRegionDO.setMerchantId(merchantId);
				adRegionDO.setGmtCreate(new Date());
				adRegionDO.setRegionId(s);
				adRegionDOMapper.insert(adRegionDO);
			}
			
		}
		
		//发送消息，通知其他模块处理事务 积分的处理
		//transactionMainService.sendNotice(merchantId);
		return id;
	}

	/**
	 * 商家E赚查询
	 * @param adMerchantParam
	 * @param merchantId
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMerchant(AdMerchantParam adMerchantParam, Long merchantId) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andMerchantIdEqualTo(merchantId).andStatusNotEqualTo(new Byte("0"));
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
	public Integer updateStatus( Long id) {
		AdDO adDO=new AdDO();
		adDO.setId(id);
		adDO.setStatus(new Byte("4"));
		Integer i=adDOMapper.updateByPrimaryKeySelective(adDO);
		if(i>0){
			AdDO ad= adDOMapper.selectByPrimaryKey(id);
			int point= ad.getPoint().intValue();
			Integer hits=ad.getHits();
			int count=point*hits;
			if(count<ad.getTotalPoint().intValue()){//退还积分
				
			}
		}
		return i;
	}
	
	
	/**
	 * 运营平台(商家)对E赚的管理(删除)
	 * @param statusEnum
	 * @return
	 */
	@Override
	public Integer remove( Long id) {
		AdDO adDO=new AdDO();
		adDO.setId(id);
		adDO.setStatus(new Byte("0"));
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
		cr.andStatusNotEqualTo(new Byte("0"));
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
	 * 会员对E赚的观看
	 * @param adMerchantParam
	 * @return
	 */
	@Override
	public Page<AdBO> selectListByMember(AdMerchantParam adMerchantParam, Long memberId) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andStatusEqualTo(new Byte("2")) //投放中
		  .andPutWayEqualTo(adMerchantParam.getPutWayEnum().val);
		RowBounds rowBounds = new RowBounds(adMerchantParam.getOffset(), adMerchantParam.getPageSize());
		//所有E赚
		List<AdDO> DOS=adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<AdDO> list=watchAdList(DOS,memberId);
		Page<AdBO> page=new Page<AdBO>();
		page.setCurrentPage(adMerchantParam.getCurrentPage());
		page.setTotalCount(list.size());
		page.setRecords(AdConverter.convertBOS(list));
		return page;
	}
	
	/**
	 * 筛选结果集合公用方法
	 * @param DOS
	 * @param memberId
	 * @return
	 */
	public List<AdDO> watchAdList(List<AdDO> DOS,Long memberId){
		List<AdDO> list=new ArrayList<AdDO>();
		if(DOS==null){
			return list;
		}
		String regionPath="10/101/10101";
		for (AdDO adDO : DOS) {
			if(adDO.getType()==1){//区域投放
				String[] path=adDO.getAreas().split("/");
				String[] memberPath=regionPath.split("/");
				for (String s : path) {
					for (String mp : memberPath) {
						if(s.equals(mp))
							list.add(adDO);
					}
				}
				
			}else if(adDO.getType()==1){//粉丝投放
				//当前广告商家的粉丝
				//List<Fens>
				list.add(adDO);
			}else{//雷达投放
				list.add(adDO);
			}
			
		}
		return  list;
	}
	
	/**
	 * 查看E赚详情,点击次数加1,点击次数和投放广告数相等则投放结束
	 * @param id
	 * @return
	 */
	@Override
	public AdBO selectAbById(Long id) {
		AdDO adDO=adDOMapper.selectByPrimaryKey(id);
		Integer hits= adDO.getHits();
		if(hits!=null && hits<adDO.getAdCount()){
			hits+=1;
			adDO.setHits(hits);
			adDOMapper.updateByPrimaryKey(adDO);
		}else{
			adDO.setStatus(new Byte("3"));
			adDOMapper.updateByPrimaryKey(adDO);
		}
		AdBO adBO=AdConverter.convertBO(adDO);
		return adBO;
	}

	/**
	 * 人气榜
	 * @param userNum
	 * @return
	 */
	@Override
	public List<AdBO> selectMoods(Long memberId) {
		return null;
	}

	/**
	 * 积分榜
	 * @param memberId
	 * @return
	 */
	@Override
	public List<AdBO> selectPoint(Long memberId) {
		AdDOExample example=new AdDOExample();
		Criteria cr= example.createCriteria();
		cr.andStatusEqualTo(new Byte("2")); //投放中
		example.setOrderByClause("total_point desc");
		//所有E赚
		List<AdDO> DOS=adDOMapper.selectByExample(example);
		List<AdDO> list=watchAdList(DOS,memberId);
		AdConverter.convertBOS(list);
		return null;
	}

	/**
	 * 视频审核
	 */
	@Override
	public Integer auditVideo(Long id) {
		AdDO adDO=new AdDO();
		adDO.setId(id);
		adDO.setStatus(new Byte("1"));
		Integer i=adDOMapper.updateByPrimaryKeySelective(adDO);
		return null;
	}

}
