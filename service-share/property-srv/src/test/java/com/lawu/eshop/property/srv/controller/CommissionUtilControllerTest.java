package com.lawu.eshop.property.srv.controller;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.property.param.CommissionResultParam;
import com.lawu.eshop.property.srv.PropertySrvApplicationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yangqh
 * @date 2017/7/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PropertySrvApplicationTest.class)
@WebAppConfiguration
public class CommissionUtilControllerTest {

    private MockMvc mvc;

    @Autowired
    private CommissionUtilController commissionUtilController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(commissionUtilController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void getClickAdMine() {
        String requestJson = JSONObject.toJSONString(new BigDecimal("100"));
        RequestBuilder request = put("/commissionUtil/getClickAdMine").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getIncomeMoney() {
        String requestJson = JSONObject.toJSONString(new BigDecimal("100"));
        RequestBuilder request = put("/commissionUtil/getIncomeMoney").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCommissionResult() {
        BigDecimal beforeMoney = BigDecimal.valueOf(1);
        BigDecimal commisson0 = BigDecimal.valueOf(0.5);
        BigDecimal actualCommissionScope = BigDecimal.valueOf(0.997);
        BigDecimal loveAccountScale = BigDecimal.valueOf(0.003);

        CommissionResultParam param = new CommissionResultParam();
        param.setBeforeMoney(beforeMoney);
        param.setCommission0(commisson0);
        param.setActualCommissionScope(actualCommissionScope);
        param.setLoveAccountScale(loveAccountScale);


        String requestJson = JSONObject.toJSONString(new BigDecimal("100"));
        RequestBuilder request = put("/commissionUtil/getIncomeMoney").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
