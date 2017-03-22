package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户服务实现
 *
 * @author meishuquan
 * @date 2017/3/22
 */
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Override
    public void updatePwd(Long id, String pwd) {
        MerchantDO merchant = new MerchantDO();
        merchant.setId(id);
        merchant.setPwd(pwd);
        merchantDOMapper.updateByPrimaryKeySelective(merchant);
    }
}
