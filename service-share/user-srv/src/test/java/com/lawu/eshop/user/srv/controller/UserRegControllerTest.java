package com.lawu.eshop.user.srv.controller;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.user.param.CollectionUserRegParam;
import com.lawu.eshop.user.srv.UserSrvApplicationTest;
import org.junit.Assert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author meishuquan
 * @date 2017/7/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserSrvApplicationTest.class)
@WebAppConfiguration
public class UserRegControllerTest {

    private MockMvc mvc;

    @Autowired
    private UserRegController userRegController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(userRegController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMemberRegDaily() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMemberRegDaily").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMerchantRegDaily() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMerchantRegDaily").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMemberRegMonth() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMemberRegMonth").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMerchantRegMonth() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMerchantRegMonth").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMemberRegArea() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMemberRegArea").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMerchantCommonRegArea() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMerchantCommonRegArea").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void collectionMerchantEntityRegArea() {
        CollectionUserRegParam param = new CollectionUserRegParam();
        param.setYear(2017);
        param.setMonth(7);
        param.setDay(19);
        param.setRegionPath("44/4403");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/userReg/collectionMerchantEntityRegArea").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

}
