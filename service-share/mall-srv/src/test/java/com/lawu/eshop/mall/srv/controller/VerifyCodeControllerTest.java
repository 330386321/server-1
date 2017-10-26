package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.MallSrvApplicationTest;
import com.lawu.eshop.mall.srv.domain.VerifyCodeDO;
import com.lawu.eshop.mall.srv.mapper.VerifyCodeDOMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MallSrvApplicationTest.class)
@WebAppConfiguration
public class VerifyCodeControllerTest {
    private MockMvc mvc;

    @Autowired
    private  VerifyCodeController verifyCodeController;

    @Autowired
    private VerifyCodeDOMapper verifyCodeDOMapper;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(verifyCodeController).build();
    }


    @Transactional
    @Rollback
    @Test
    public void savePicCode(){
        RequestBuilder request = null;
        ResultActions perform = null;
        request = get("/verifyCode/savePicCode/123456").param("picCode","1234").param("purpose","USER_REGISTER");
        try {
            perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Rollback
    @Test
    public void verifySmsCode(){
        RequestBuilder request = null;
        ResultActions perform = null;
        request = get("/verifyCode/verifySmsCode/1").param("smsCode","1234");
        try {
            perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Rollback
    @Test
    public void verifyPicCode(){
        RequestBuilder request = null;
        ResultActions perform = null;
        request = get("/verifyCode/verifyPicCode/123456").param("picCode","1234");
        try {
            perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        VerifyCodeDO verifyCodeDO = new VerifyCodeDO();
        verifyCodeDO.setGmtCreate(new Date());
        verifyCodeDO.setPurpose(VerifyCodePurposeEnum.PIC_VERIFY_CODE.val);
        verifyCodeDO.setCode("123456");
        verifyCodeDO.setMobile("123456");
        verifyCodeDOMapper.insert(verifyCodeDO);
        request = get("/verifyCode/verifyPicCode/123456").param("picCode","1234");
        try {
            perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
