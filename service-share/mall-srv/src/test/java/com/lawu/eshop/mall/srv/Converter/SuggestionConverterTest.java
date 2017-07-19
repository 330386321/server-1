package com.lawu.eshop.mall.srv.Converter;

import com.lawu.eshop.mall.constants.SuggestionClientType;
import com.lawu.eshop.mall.constants.SuggestionUserType;
import com.lawu.eshop.mall.dto.SuggestionDTO;
import com.lawu.eshop.mall.srv.bo.SuggestionBO;
import com.lawu.eshop.mall.srv.converter.SuggestionConverter;
import com.lawu.eshop.mall.srv.domain.SuggestionDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class SuggestionConverterTest {

    @Test
    public void convert() {
        SuggestionDO suggestionDO = new SuggestionDO();
        suggestionDO.setContent("1234");
        suggestionDO.setGmtCreate(new Date());
        suggestionDO.setGmtModified(new Date());
        suggestionDO.setId(1);
        suggestionDO.setUserNum("1234566");
        suggestionDO.setClientType(SuggestionClientType.ANDROID.value);
        suggestionDO.setUserType(SuggestionUserType.MEMBER.value);

        SuggestionBO suggestionBO = SuggestionConverter.convert(suggestionDO);
        Assert.assertEquals(suggestionDO.getContent(), suggestionBO.getContent());
    }

    @Test
    public void convertBOS() {
        List<SuggestionDO> dos = new ArrayList<>();
        SuggestionDO suggestionDO = new SuggestionDO();
        suggestionDO.setContent("1234");
        suggestionDO.setGmtCreate(new Date());
        suggestionDO.setGmtModified(new Date());
        suggestionDO.setId(1);
        suggestionDO.setUserNum("1234566");
        suggestionDO.setClientType(SuggestionClientType.ANDROID.value);
        suggestionDO.setUserType(SuggestionUserType.MEMBER.value);
        dos.add(suggestionDO);
        List<SuggestionBO> list = SuggestionConverter.convertBOS(dos);
        Assert.assertEquals(dos.get(0).getContent(), list.get(0).getContent());

    }

    @Test
    public void convertDTO() {
        SuggestionBO bo = new SuggestionBO();
        bo.setContent("1234");
        bo.setGmtCreate(new Date());
        bo.setGmtModified(new Date());
        bo.setId(1);
        bo.setUserNum("1234566");
        bo.setClientType(SuggestionClientType.ANDROID);
        bo.setUserType(SuggestionUserType.MEMBER);
        SuggestionDTO suggestionDTO = SuggestionConverter.convert(bo);
        Assert.assertEquals(bo.getContent(), suggestionDTO.getContent());
    }

    @Test
    public void convertDTOS() {
        List<SuggestionBO> bos = new ArrayList<>();
        SuggestionBO bo = new SuggestionBO();
        bo.setContent("1234");
        bo.setGmtCreate(new Date());
        bo.setGmtModified(new Date());
        bo.setId(1);
        bo.setUserNum("1234566");
        bo.setClientType(SuggestionClientType.ANDROID);
        bo.setUserType(SuggestionUserType.MEMBER);
        bos.add(bo);
        List<SuggestionDTO> list = SuggestionConverter.convertDTOS(bos);
        Assert.assertEquals(bos.get(0).getContent(), list.get(0).getContent());
    }
}
