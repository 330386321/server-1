package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.bo.VerifyCodeBO;
import com.lawu.eshop.mall.srv.domain.VerifyCodeDO;
import com.lawu.eshop.mall.srv.mapper.VerifyCodeDOMapper;
import com.lawu.eshop.mall.srv.service.VerifyCodeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class VerifyCodeServiceImplTest {
    @Autowired
    private VerifyCodeDOMapper verifyCodeDOMapper;

    @Autowired
    private VerifyCodeService verifyCodeService;


    @Transactional
    @Rollback
    @Test
    public void savePicCode(){
        String mobile = "123456789";
        String picCode ="123456";
        verifyCodeService.savePicCode(mobile,picCode,VerifyCodePurposeEnum.USER_REGISTER);
        List<VerifyCodeDO> list = verifyCodeDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
    }

    @Transactional
    @Rollback
    @Test
    public void getVerifyCodeById(){
        VerifyCodeDO verifyCodeDO = new VerifyCodeDO();
        verifyCodeDO.setCode("123");
        verifyCodeDO.setMobile("123456789");
        verifyCodeDO.setPurpose(VerifyCodePurposeEnum.USER_REGISTER.val);
        verifyCodeDOMapper.insert(verifyCodeDO);
        VerifyCodeBO verifyCodeBO = verifyCodeService.getVerifyCodeById(verifyCodeDO.getId());
        Assert.assertNotNull(verifyCodeBO);
        Assert.assertEquals("123",verifyCodeBO.getCode());

    }

    @Transactional
    @Rollback
    @Test
    public  void getLastPicVerifyCode(){
        VerifyCodeDO verifyCodeDO = new VerifyCodeDO();
        verifyCodeDO.setCode("123");
        verifyCodeDO.setMobile("123456789");
        verifyCodeDO.setPurpose(VerifyCodePurposeEnum.PIC_VERIFY_CODE.val);
        verifyCodeDO.setGmtCreate(new Date());
        verifyCodeDOMapper.insert(verifyCodeDO);

        VerifyCodeBO verifyCodeBO  = verifyCodeService.getLastPicVerifyCode("123456789");
        Assert.assertNotNull(verifyCodeBO);
        Assert.assertEquals("123",verifyCodeBO.getCode());
    }
}
