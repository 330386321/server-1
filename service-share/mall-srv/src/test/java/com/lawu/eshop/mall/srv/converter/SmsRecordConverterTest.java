package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.dto.SmsRecordDTO;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.converter.SmsRecordConverter;
import com.lawu.eshop.mall.srv.domain.SmsRecordDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class SmsRecordConverterTest {

    @Test
    public void convertBO() {
        SmsRecordDO smsRecordDO = new SmsRecordDO();
        smsRecordDO.setId(1L);
        smsRecordDO.setContent("12345");
        SmsRecordBO smsRecordBO = SmsRecordConverter.convertBO(smsRecordDO);
        Assert.assertEquals(smsRecordDO.getContent(), smsRecordBO.getContent());
    }

    @Test
    public void convertDTO() {
        SmsRecordBO smsRecordBO = new SmsRecordBO();
        smsRecordBO.setId(1L);
        smsRecordBO.setContent("12345");
        SmsRecordDTO smsRecordDTO = SmsRecordConverter.convertDTO(smsRecordBO);
        Assert.assertEquals(smsRecordBO.getContent(), smsRecordDTO.getContent());
    }
}
