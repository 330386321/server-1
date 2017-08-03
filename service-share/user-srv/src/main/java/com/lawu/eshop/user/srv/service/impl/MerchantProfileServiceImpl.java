package com.lawu.eshop.user.srv.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantInfoFromPublishAdBO;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.bo.MerchantSizeLinkBO;
import com.lawu.eshop.user.srv.converter.MerchantInfoConverter;
import com.lawu.eshop.user.srv.converter.MerchantProfileConverter;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDOExample.Criteria;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.service.MerchantProfileService;

/**
 *
 * Created by Administrator on 2017/3/23.
 */
@Service
public class MerchantProfileServiceImpl implements MerchantProfileService {

    @Autowired
    private MerchantProfileDOMapper merchantProfileDOMapper;

    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;
    
    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;
    
    @Override
    public int updateMerchantSizeLink(MerchantProfileParam merchantProfileParamd, @RequestParam Long id) {
        MerchantProfileDO merchantProfileDO = MerchantInfoConverter.paramConvertDO(merchantProfileParamd);
        merchantProfileDO.setId(id);
        int result = merchantProfileDOMapper.updateByPrimaryKeySelective(merchantProfileDO);
        return result;
    }

    @Override
    public MerchantProfileBO findMerchantProfileInfo(Long merchantProfileId) {
        MerchantProfileDO  merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchantProfileId);
        return MerchantInfoConverter.convertBO(merchantProfileDO);
    }

    @Override
    public MerchantSizeLinkBO getMerchantSizeLink(Long merchantId) {
        MerchantProfileDO merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchantId);
        MerchantSizeLinkBO merchantSizeLinkBO = new MerchantSizeLinkBO();
        if(merchantProfileDO != null){
            BeanUtils.copyProperties(merchantProfileDO,merchantSizeLinkBO);
        }
        return merchantSizeLinkBO;
    }

	@Override
	public MerchantProfileBO getMerchantProfile(Long merchantId) {
		MerchantProfileDO merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchantId);
		return MerchantProfileConverter.convertBO(merchantProfileDO);
	}

	@Override
	public MerchantInfoFromPublishAdBO getMerchantInfoFromPublishAd(Long merchantId) {
		MerchantProfileDO merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchantId);
		MerchantStoreImageDOExample example = new MerchantStoreImageDOExample();
		Criteria criteria = example.createCriteria();
		criteria.andMerchantIdEqualTo(merchantId);
		criteria.andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
		List<MerchantStoreImageDO> list = merchantStoreImageDOMapper.selectByExample(example);
		MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
		com.lawu.eshop.user.srv.domain.MerchantStoreDOExample.Criteria storeCriteria = merchantStoreDOExample.createCriteria();
		storeCriteria.andMerchantIdEqualTo(merchantId);
		List<MerchantStoreDO> storeList = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
		MerchantInfoFromPublishAdBO result = new MerchantInfoFromPublishAdBO();
		if(merchantProfileDO != null) {
			result.setJdUrl(merchantProfileDO.getJdUrl());
			result.setTbUrl(merchantProfileDO.getTaobaoUrl());
			result.setTmUrl(merchantProfileDO.getTmallUrl());
			result.setWebsiteUrl(merchantProfileDO.getWebsiteUrl());
		}
		if(list != null && !list.isEmpty()) 
			result.setLogoUrl(list.get(0).getPath());
		if(storeList != null && !storeList.isEmpty())
			result.setStoreName(storeList.get(0).getName());
		return result;
	}
}
