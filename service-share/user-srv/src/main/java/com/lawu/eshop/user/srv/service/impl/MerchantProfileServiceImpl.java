package com.lawu.eshop.user.srv.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.user.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantInfoFromInviteFansBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoFromPublishAdBO;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.bo.MerchantSizeLinkBO;
import com.lawu.eshop.user.srv.converter.MerchantInfoConverter;
import com.lawu.eshop.user.srv.converter.MerchantProfileConverter;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.domain.extend.MerchantInfoFromInviteFansDOView;
import com.lawu.eshop.user.srv.domain.extend.MerchantInfoFromPublishAdDOView;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.MerchantInfoFromInviteFansDOMapperExtend;
import com.lawu.eshop.user.srv.mapper.extend.MerchantInfoFromPublishAdDOMapperExtend;
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
    private MerchantInfoFromPublishAdDOMapperExtend merchantInfoFromPublishAdDOMapperExtend;
    
    @Autowired
    private MerchantInfoFromInviteFansDOMapperExtend merchantInfoFromInviteFansDOMapperExtend;
    
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
		List<MerchantInfoFromPublishAdDOView> list = merchantInfoFromPublishAdDOMapperExtend.getMerchantInfoFromPublishAd(merchantId);
		MerchantInfoFromPublishAdBO result = new MerchantInfoFromPublishAdBO();
		if(list != null && !list.isEmpty()) {
			MerchantInfoFromPublishAdDOView merchantInfoFromPublishAdDOView = list.get(0);
			result.setJdUrl(merchantInfoFromPublishAdDOView.getJdUrl());
			result.setLogoUrl(merchantInfoFromPublishAdDOView.getLogoUrl());
			result.setStoreName(merchantInfoFromPublishAdDOView.getStoreName());
			result.setTbUrl(merchantInfoFromPublishAdDOView.getTaobaoUrl());
			result.setTmUrl(merchantInfoFromPublishAdDOView.getTmUrl());
			result.setWebsiteUrl(merchantInfoFromPublishAdDOView.getWebsiteUrl());
		}
		return result;
	}

	@Override
	public MerchantInfoFromInviteFansBO getMerchantInfoFromInviteFans(Long merchantId) {
		List<MerchantInfoFromInviteFansDOView> list = merchantInfoFromInviteFansDOMapperExtend.getMerchantInfoFromInviteFans(merchantId);
		MerchantInfoFromInviteFansBO merchantInfoFromInviteFansBO = new MerchantInfoFromInviteFansBO();
		if(list != null && !list.isEmpty()) {
			MerchantInfoFromInviteFansDOView view = list.get(0);
			merchantInfoFromInviteFansBO.setMerchantStoreId(view.getMerchantStoreId());
			merchantInfoFromInviteFansBO.setMerchantStoreIntro(view.getMerchantStoreIntro());
			merchantInfoFromInviteFansBO.setMerchantStoreLogo(view.getMerchantStoreLogo());
			merchantInfoFromInviteFansBO.setMerchantStoreName(view.getMerchantStoreName());
			merchantInfoFromInviteFansBO.setMerchantStoreUrl(view.getMerchantStoreUrl());
		}
		return merchantInfoFromInviteFansBO;
	}
	
}
