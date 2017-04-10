package com.lawu.eshop.ad.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.ad.srv.converter.AdPlatformConverter;
import com.lawu.eshop.ad.srv.domain.AdPlatformDO;
import com.lawu.eshop.ad.srv.domain.AdPlatformDOExample;
import com.lawu.eshop.ad.srv.domain.AdPlatformDOExample.Criteria;
import com.lawu.eshop.ad.srv.mapper.AdPlatformDOMapper;
import com.lawu.eshop.ad.srv.service.AdPlatformService;

@Service
public class AdPlatformServiceImpl implements AdPlatformService {
	
	@Autowired
	private AdPlatformDOMapper adPlatformDOMapper;

	@Override
	@Transactional
	public Integer saveAdPlatform(AdPlatformParam adPlatformParam,String url) {
		AdPlatformDO adPlatformDO=new AdPlatformDO();
		adPlatformDO.setTitle(adPlatformParam.getTitle());
		adPlatformDO.setMediaUrl(url);
		//纯链接
		if(adPlatformParam.getTypeEnum().equals(TypeEnum.TYPE_LINK)){
			adPlatformDO.setType(new Byte("1"));
			adPlatformDO.setLinkUrl(adPlatformParam.getLinkUrl());
		}else{ //商品
			adPlatformDO.setType(new Byte("2"));
			adPlatformDO.setProductId(adPlatformParam.getProductId());  
		}
		adPlatformDO.setPosition(adPlatformParam.getPositionEnum().val);
		adPlatformDO.setStatus(new Byte("1"));
		adPlatformDO.setGmtCreate(new Date());
		adPlatformDO.setGmtModified(new Date());
		Integer id=adPlatformDOMapper.insert(adPlatformDO);
		return id;
	}

	@Override
	@Transactional
	public Integer removeAdPlatform(Long id) {
		AdPlatformDOExample example = new AdPlatformDOExample();
		example.createCriteria().andIdEqualTo(id);
		AdPlatformDO adPlatformDO=new AdPlatformDO();
		adPlatformDO.setStatus(new Byte("0"));
		Integer i=adPlatformDOMapper.updateByExampleSelective(adPlatformDO, example);
		return i;
	}

	@Override
	public List<AdPlatformBO> selectByPosition(PositionEnum positionEnum) {
		AdPlatformDOExample example = new AdPlatformDOExample();
		Criteria criteria=example.createCriteria();
		criteria.andStatusEqualTo(new Byte("1")).andPositionEqualTo(positionEnum.val);
		List<AdPlatformDO> DOS=adPlatformDOMapper.selectByExample(example);
		return  DOS.isEmpty() ? null :AdPlatformConverter.convertBOS(DOS);
	}

}