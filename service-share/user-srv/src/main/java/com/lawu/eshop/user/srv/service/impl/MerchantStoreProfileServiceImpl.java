package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
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
    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Override
    public MerchantStoreProfileBO findMerchantStoreInfo(Long mid) {
        MerchantStoreProfileDOExample example = new MerchantStoreProfileDOExample();
        example.createCriteria().andMerchantIdEqualTo(mid);

        List<MerchantStoreProfileDO> merchantStoreDOS = merchantStoreProfileDOMapper.selectByExample(example);
        if(merchantStoreDOS.isEmpty()){
            return null;
        }
        MerchantStoreImageDOExample example1 = new MerchantStoreImageDOExample();
        example1.createCriteria().andMerchantIdEqualTo(mid)
                .andStatusEqualTo(true).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
        List<MerchantStoreImageDO> imageDOS = merchantStoreImageDOMapper.selectByExample(example1);
        MerchantStoreProfileBO merchantStoreProfileBO = MerchantStoreConverter.convertBO(merchantStoreDOS.get(0));
        merchantStoreProfileBO.setLogoUrl(imageDOS.get(0).getPath());
        return  merchantStoreProfileBO;
    }
}
