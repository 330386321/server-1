package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lawu.eshop.user.srv.domain.FavoriteMerchantDO;
import com.lawu.eshop.user.srv.mapper.FavoriteMerchantDOMapper;
import com.lawu.eshop.user.srv.service.FavoriteMerchantService;

@Service
public class FavoriteMerchantServiceImpl implements FavoriteMerchantService {
	
	@Resource
	private FavoriteMerchantDOMapper favoriteMerchantDOMapper;

	@Override
	public Integer save(Long memberId ,Long merchantId) {
		FavoriteMerchantDO favoriteMerchant=new FavoriteMerchantDO();
		favoriteMerchant.setMemberId(memberId);
		favoriteMerchant.setMerchantId(merchantId);
		favoriteMerchant.setGmtCreate(new Date());
		int id=favoriteMerchantDOMapper.insert(favoriteMerchant);
		return id; 
	}

}
