package com.lawu.eshop.ad.srv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.param.ReportAdEarningsDetailParam;
import com.lawu.eshop.ad.srv.AdSrvApplicationTest;
import com.lawu.eshop.framework.web.HttpCode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdSrvApplicationTest.class)
@WebAppConfiguration
public class ReportAdEarningsDetailControllerTest {
	
	 private MockMvc mvc;
	
	 @Autowired
     private  ReportAdEarningsDetailController  reportAdEarningsDetailController;
     

     @Before
     public void setUp() throws Exception {
         mvc = MockMvcBuilders.standaloneSetup(reportAdEarningsDetailController).build();
     }
     
     @Transactional
     @Rollback
     @Test
     public void getReportAdEarningsDetail() {
    	 ReportAdEarningsDetailParam param=new ReportAdEarningsDetailParam();
         param.setAdTypeEnum(AdTypeEnum.AD_TYPE_FLAT);
         param.setAdId(1l);
         param.setCurrentPage(1);
         param.setPageSize(10);
         String requestListJson = JSONObject.toJSONString(param);
         try {
             RequestBuilder request = post("/reportAdEarningsDetail/getReportAdEarningsDetail/").contentType(MediaType.APPLICATION_JSON).content(requestListJson);
             ResultActions perform= mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();

         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }

     }

}
