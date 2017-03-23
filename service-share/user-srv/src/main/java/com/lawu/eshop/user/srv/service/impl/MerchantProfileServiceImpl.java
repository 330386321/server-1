package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantProfileConverter;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2017/3/23.
 */
@Service
public class MerchantProfileServiceImpl implements MerchantProfileService {

    @Autowired
    private MerchantProfileDOMapper merchantProfileDOMapper;

    @Override
    public void updateMerchantSizeLink(MerchantProfileParam merchantProfileParamd) {
        MerchantProfileDO merchantProfileDO = MerchantProfileConverter.paramConvertDO(merchantProfileParamd);
        merchantProfileDOMapper.updateByPrimaryKeySelective(merchantProfileDO);
    }

    @Override
    public MerchantProfileBO findMerchantProfileInfo(Long merchantProfileId) {
        MerchantProfileDO  merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchantProfileId);
        return MerchantProfileConverter.convertBO(merchantProfileDO);
    }
}
