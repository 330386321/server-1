package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.query.FavoriteMerchantParam;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;
import com.lawu.eshop.user.srv.converter.FavoriteMerchantConverter;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDO;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.mapper.FavoriteMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.FavoriteMerchantService;

@Service
public class FavoriteMerchantServiceImpl implements FavoriteMerchantService {
	
	@Resource
	private FavoriteMerchantDOMapper favoriteMerchantDOMapper;
	
	@Resource
	private MerchantStoreDOMapper merchantStoreDOMapper;

	@Override
	public Integer save(Long memberId ,Long merchantId) {
		FavoriteMerchantDO favoriteMerchant=new FavoriteMerchantDO();
		favoriteMerchant.setMemberId(memberId);
		favoriteMerchant.setMerchantId(merchantId);
		favoriteMerchant.setGmtCreate(new Date());
		int id=favoriteMerchantDOMapper.insert(favoriteMerchant);
		return id; 
	}

	@Override
	public Page<FavoriteMerchantBO> getMyFavoriteMerchant(FavoriteMerchantParam pageQuery) {
		FavoriteMerchantDOExample example = new FavoriteMerchantDOExample();
		example.createCriteria().andMemberIdEqualTo(pageQuery.getMemberId());
		int totalCount=favoriteMerchantDOMapper.countByExample(example);
		RowBounds rowBounds = new RowBounds(pageQuery.getCurrentPage(), pageQuery.getPageSize());
		List<FavoriteMerchantDO> FMDOS=favoriteMerchantDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<MerchantStoreDO> MDOS=new ArrayList<MerchantStoreDO>();
		for (FavoriteMerchantDO favoriteMerchantDO : FMDOS) {
			MerchantStoreDOExample msExample =new MerchantStoreDOExample();
			msExample.createCriteria().andMerchantIdEqualTo(favoriteMerchantDO.getMerchantId())
									  .andStatusEqualTo(new Byte("1"));
			 List<MerchantStoreDO> list=merchantStoreDOMapper.selectByExample(msExample);
			 if(!list.isEmpty()){
				 MerchantStoreDO merchantStoreDO=list.get(0);
				 MDOS.add(merchantStoreDO);
			 }
			 
		}
		List<FavoriteMerchantBO> fmBOS= FavoriteMerchantConverter.convertListBOS(MDOS, FMDOS);
		Page<FavoriteMerchantBO> page=new Page<FavoriteMerchantBO>();
		page.setTotalCount(totalCount);
		page.setCurrentPage(pageQuery.getCurrentPage());
		page.setRecords(fmBOS);
		return page;
	}

}
