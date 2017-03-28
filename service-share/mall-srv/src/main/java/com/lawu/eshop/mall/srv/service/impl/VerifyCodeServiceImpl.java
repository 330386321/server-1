package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.bo.VerifyCodeBO;
import com.lawu.eshop.mall.srv.converter.VerifyCodeConverter;
import com.lawu.eshop.mall.srv.domain.VerifyCodeDO;
import com.lawu.eshop.mall.srv.mapper.VerifyCodeDOMapper;
import com.lawu.eshop.mall.srv.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author meishuquan
 * @date 2017/3/28
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private VerifyCodeDOMapper verifyCodeDOMapper;

    @Override
    public Long save(String mobile, String verifyCode, VerifyCodePurposeEnum purpose) {
        VerifyCodeDO verifyCodeDO = new VerifyCodeDO();
        verifyCodeDO.setMobile(mobile);
        verifyCodeDO.setCode(verifyCode);
        verifyCodeDO.setPurpose(purpose.val);
        verifyCodeDO.setGmtCreate(new Date());
        verifyCodeDOMapper.insertSelective(verifyCodeDO);
        return verifyCodeDO.getId();
    }

    @Override
    public VerifyCodeBO getVerifyCodeById(Long id) {
        VerifyCodeDO verifyCodeDO = verifyCodeDOMapper.selectByPrimaryKey(id);
        return verifyCodeDO == null ? null : VerifyCodeConverter.convertBO(verifyCodeDO);
    }
}
