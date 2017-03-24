package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.converter.InviterConverter;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantDOExample;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公共服务实现
 *
 * @author meishuquan
 * @date 2017/3/23
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private MemberDOMapper memberDOMapper;

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Override
    public InviterBO getInviterByAccount(String account) {

        //查询会员信息
        MemberDOExample memberDOExample = new MemberDOExample();
        memberDOExample.createCriteria().andAccountEqualTo(account);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(memberDOExample);
        if (!memberDOS.isEmpty()) {
            return InviterConverter.convertBO(memberDOS.get(0));
        }

        //查询商户信息
        MerchantDOExample merchantDOExample = new MerchantDOExample();
        merchantDOExample.createCriteria().andAccountEqualTo(account);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(merchantDOExample);
        if (!merchantDOS.isEmpty()) {
            //TODO   查询门店信息
            return null;
        }
        return null;
    }
}
