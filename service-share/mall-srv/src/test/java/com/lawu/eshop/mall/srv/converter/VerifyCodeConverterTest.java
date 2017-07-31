package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.mall.srv.bo.VerifyCodeBO;
import com.lawu.eshop.mall.srv.converter.VerifyCodeConverter;
import com.lawu.eshop.mall.srv.domain.VerifyCodeDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class VerifyCodeConverterTest {

    @Test
    public void convertBO() {
        VerifyCodeDO verifyCodeDO = new VerifyCodeDO();
        verifyCodeDO.setId(1L);
        verifyCodeDO.setMobile("123");
        verifyCodeDO.setCode("1234");
        verifyCodeDO.setGmtCreate(new Date());
        VerifyCodeBO verifyCodeBO = VerifyCodeConverter.convertBO(verifyCodeDO);
        Assert.assertEquals(verifyCodeDO.getCode(), verifyCodeBO.getCode());
    }

    @Test
    public void convertDTO() {
        VerifyCodeBO verifyCodeBO = new VerifyCodeBO();
        verifyCodeBO.setId(1L);
        verifyCodeBO.setMobile("123");
        verifyCodeBO.setCode("1234");
        verifyCodeBO.setGmtCreate(new Date());
        VerifyCodeDTO verifyCodeDTO = VerifyCodeConverter.convertDTO(verifyCodeBO);
        Assert.assertEquals(verifyCodeBO.getCode(), verifyCodeDTO.getCode());

    }
}
