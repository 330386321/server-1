package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/3/24.
 */
@Service
public class MerchantStoreInfoServiceImpl implements MerchantStoreInfoService {

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    private MerchantStoreProfileDOMapper merchantStoreProfileDOMapper;

    @Autowired
    MerchantStoreImageDOMapper merchantStoreImageDOMapper;
    @Override
    public MerchantStoreInfoBO selectMerchantStore(Long id) {

        //商家门店基本信息
        MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
        merchantStoreDOExample.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        if(merchantStoreDOS.isEmpty()){
            return null;
        }

        //商家店铺扩展信息
        MerchantStoreProfileDOExample example = new MerchantStoreProfileDOExample();
        example.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantStoreProfileDO> merchantStoreProfileDOS = merchantStoreProfileDOMapper.selectByExample(example);

        //商家店铺图片信息
        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);

        MerchantStoreInfoBO merchantStoreInfoBO = MerchantStoreConverter.coverter(merchantStoreDOS.get(0));
        if(!merchantStoreProfileDOS.isEmpty()){
            merchantStoreInfoBO.setCompanyAddress(merchantStoreProfileDOS.get(0).getCompanyAddress());
            merchantStoreInfoBO.setCompanyName(merchantStoreProfileDOS.get(0).getCompanyName());
            merchantStoreInfoBO.setRegNumber(merchantStoreProfileDOS.get(0).getRegNumber());
            merchantStoreInfoBO.setLicenseIndate(merchantStoreProfileDOS.get(0).getLicenseIndate());
            merchantStoreInfoBO.setManageType(merchantStoreProfileDOS.get(0).getManageType());
            merchantStoreInfoBO.setCertifType(merchantStoreProfileDOS.get(0).getCertifType());
            merchantStoreInfoBO.setOperatorCardId(merchantStoreProfileDOS.get(0).getOperatorCardId());
            merchantStoreInfoBO.setOperatorName(merchantStoreProfileDOS.get(0).getOperatorName());
        }

        if(!merchantStoreImageDOS.isEmpty()){
            merchantStoreInfoBO.setType(merchantStoreImageDOS.get(0).getType());
            merchantStoreInfoBO.setPath(merchantStoreImageDOS.get(0).getPath());

        }

        return merchantStoreInfoBO;
    }
}
