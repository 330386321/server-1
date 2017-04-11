package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.srv.bo.MerchantStoreImageBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreImageConverter;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/11.
 */
@Service
public class MerchantStoreImageServiceImpl implements MerchantStoreImageService {

    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Override
    public List<MerchantStoreImageBO> listMerchantStoreImageByType(Long merchantId, MerchantStoreImageEnum merchantStoreImageEnum) {
        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(merchantStoreImageEnum.val);
        List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
        return MerchantStoreImageConverter.convertBO(merchantStoreImageDOS);
    }
}
