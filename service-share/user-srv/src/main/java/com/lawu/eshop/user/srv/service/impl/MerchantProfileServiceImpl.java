package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.bo.MerchantSizeLinkBO;
import com.lawu.eshop.user.srv.converter.MerchantInfoConverter;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Created by Administrator on 2017/3/23.
 */
@Service
public class MerchantProfileServiceImpl implements MerchantProfileService {

    @Autowired
    private MerchantProfileDOMapper merchantProfileDOMapper;

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
        BeanUtils.copyProperties(merchantProfileDO,merchantSizeLinkBO);
        return merchantSizeLinkBO;
    }
}
