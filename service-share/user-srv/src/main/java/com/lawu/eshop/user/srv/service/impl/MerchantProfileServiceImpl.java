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
        MerchantProfileDO merchantProfileDO = MerchantProfileConverter.paramConvertDO(merchantProfileParamd);
        merchantProfileDO.setId(id);
        int result = merchantProfileDOMapper.updateByPrimaryKeySelective(merchantProfileDO);
        System.out.println("result = " + result );
        return result;
    }

    @Override
    public MerchantProfileBO findMerchantProfileInfo(Long merchantProfileId) {
        MerchantProfileDO  merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchantProfileId);
        return MerchantProfileConverter.convertBO(merchantProfileDO);
    }
}
