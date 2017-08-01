 package com.lawu.eshop.member.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.authorization.interceptor.AuthorizationInterceptor;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.member.api.MemberApiApplicationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 /**
  * @author yangqh
  * @date 2017/8/1
  */
 @RunWith(SpringJUnit4ClassRunner.class)
 @SpringBootTest(classes = MemberApiApplicationTest.class)
 @WebAppConfiguration
 public class ShoppingCartControllerTest {

     private MockMvc mvc;

     @Autowired
     private ShoppingCartController shoppingCartController;

     @Autowired
     private AuthorizationInterceptor authorizationInterceptor;

     @Before
     public void setUp() throws Exception {
         mvc = MockMvcBuilders.standaloneSetup(shoppingCartController).addInterceptors(authorizationInterceptor).build();
     }

     @Test
     public void save() {
         MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
         params.add("productModelId","1");
         params.add("quantity","10");
         RequestBuilder request = post("/shoppingCart/save").params(params);
         try {
             ResultActions perform = mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }
     }

     @Test
     public void findListByMemberId() {
         RequestBuilder request = get("/shoppingCart/findListByMemberId").header("authorization","");
         try {
             ResultActions perform = mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }
     }

     @Test
     public void update() {
         MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
         params.add("productModelId","1");
         params.add("quantity","10");
         RequestBuilder request = put("/shoppingCart/update/1").header("authorization","").param("id","1").params(params);
         try {
             ResultActions perform = mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }
     }

//     @Test
//     public void delete1() {
//         List<Long> ids = new ArrayList<>();
//         ids.add(1L);
//         String requestJson = JSONObject.toJSONString(ids);
//         RequestBuilder request = delete("/shoppingCart/delete").contentType(MediaType.APPLICATION_JSON).content(requestJson).header("authorization","");
//         try {
//             ResultActions perform = mvc.perform(request);
//             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_NO_CONTENT)).andDo(MockMvcResultHandlers.print()).andReturn();
//         } catch (Exception e) {
//             e.printStackTrace();
//             Assert.fail(e.getMessage());
//         }
//     }
 }
