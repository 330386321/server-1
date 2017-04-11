package com.lawu.eshop.user.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreService;

@Service
public class MerchantStoreServiceImpl implements MerchantStoreService{
	
	@Autowired
	private MerchantStoreDOMapper merchantStoreDOMapper;

	@Override
	public MerchantStoreBO selectMerchantStore(Long merchantId) {
		
		MerchantStoreDOExample example=new MerchantStoreDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId);
		 List<MerchantStoreDO>  list=merchantStoreDOMapper.selectByExample(example);
		 MerchantStoreDO merchantStoreDO=null;
		 if(!list.isEmpty()){
			 merchantStoreDO=list.get(0);
		 }
		 MerchantStoreBO bo= MerchantStoreConverter.convertStoreBO(merchantStoreDO);
		return bo;
	}

}
