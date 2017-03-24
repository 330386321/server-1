package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户服务实现
 *
 * @author meishuquan
 * @date 2017/3/22
 */
@Service
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

    @Override
    public MerchantBO getMerchantByAccount(String account) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
        return merchantDOS.isEmpty() ? null : MerchantConverter.convertBO(merchantDOS.get(0));
    }

    @Override
    public MerchantBO findMerchantInfo(Long merchantProfileId) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(merchantProfileId);

        return MerchantConverter.convertBO(merchantDO);
    }
    
	@Override
	public List<MerchantBO> getMerchantByInviterId(Long inviterId) {
		 MerchantDOExample example = new MerchantDOExample();
	     example.createCriteria().andInviterIdEqualTo(inviterId);
	     List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
		return merchantDOS.isEmpty() ? null : MerchantConverter.convertBOS(merchantDOS);
	}
}
