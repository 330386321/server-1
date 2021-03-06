package com.lawu.eshop.mall.srv.controller;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.mall.constants.SuggestionClientType;
import com.lawu.eshop.mall.param.SuggestionListParam;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.srv.MallSrvApplicationTest;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MallSrvApplicationTest.class)
@WebAppConfiguration
public class SuggestionControllerTest {
    private MockMvc mvc;

    @Autowired
    private  SuggestionController suggestionController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(suggestionController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void save(){
        SuggestionParam param = new SuggestionParam();
        param.setContent("test");
        String jsonData = JSONObject.toJSONString(param);
        RequestBuilder request = null;
        ResultActions perform = null;
        request = post("/suggestion/123456").contentType(MediaType.APPLICATION_JSON).content(jsonData);
        try {
            perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SuggestionParam param2 = new SuggestionParam();
        param2.setContent("test");
        param2.setClientType(SuggestionClientType.ANDROID);
        String jsonData2= JSONObject.toJSONString(param2);
        request = post("/suggestion/123456").contentType(MediaType.APPLICATION_JSON).content(jsonData2);
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
    public void getSuggestionList(){
        SuggestionListParam param = new SuggestionListParam();
        param.setCurrentPage(1);
        param.setPageSize(10);
        String jsonData= JSONObject.toJSONString(param);
        RequestBuilder request = null;
        ResultActions perform = null;
        request = post("/suggestion/getSuggestionList").contentType(MediaType.APPLICATION_JSON).content(jsonData);
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
    public void delSuggestion(){
        RequestBuilder request = null;
        ResultActions perform = null;
        request = put("/suggestion/delSuggestion/1");
        try {
            perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
