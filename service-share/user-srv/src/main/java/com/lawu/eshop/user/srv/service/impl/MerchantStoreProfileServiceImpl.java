package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/3/24.
 */
@Service
public class MerchantStoreProfileServiceImpl implements MerchantStoreProfileService {

    @Autowired
    private MerchantStoreProfileDOMapper merchantStoreProfileDOMapper;

    @Override
    public MerchantStoreProfileBO findMerchantStoreInfo(Long merchantProfileId) {
        MerchantStoreProfileDOExample example = new MerchantStoreProfileDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantProfileId);

        List<MerchantStoreProfileDO> merchantStoreDOS = merchantStoreProfileDOMapper.selectByExample(example);

        return merchantStoreDOS.isEmpty() ? null : MerchantStoreConverter.convertBO(merchantStoreDOS.get(0));
    }
}
