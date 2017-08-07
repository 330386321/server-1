package com.lawu.eshop.merchant.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.merchant.api.MerchantApiApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MerchantApiApplicationTest.class)
@WebAppConfiguration
public class WorkOrderControllerTest {

	 private MockMvc mvc;

     @Autowired
     private WorkOrderController workOrderController;

     @Before
     public void setUp() throws Exception {
         mvc = MockMvcBuilders.standaloneSetup(workOrderController).build();
     }
     
     @Test
     public void saveWorkOrder() {
         RequestBuilder request = post("/workOrder/saveWorkOrder").header("authorization", "8888").param("content", "内容");
         try {
             ResultActions perform = mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
             Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }
     }
}
