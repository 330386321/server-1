package com.lawu.eshop.order.srv.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lawu.eshop.order.srv.OrderSrvApplication;
import com.lawu.eshop.order.srv.controller.ShoppingOrderController;

/**
 * 
 * @author Sunny
 * @date 2017年6月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderSrvApplication.class)
@WebAppConfiguration
public class ShoppingOrderControllerTest {
	
    private MockMvc mvc;

    @Autowired
    private ShoppingOrderController shoppingOrderController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(shoppingOrderController).build();
    }

    @Ignore
    @Test
    public void setMemberTokenOneToOne() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/shoppingOrder/getExpressInfo/75").accept(MediaType.APPLICATION_JSON)).andReturn();
        mvcResult.getResponse().getStatus();
        mvcResult.getResponse().getContentAsString();
        
    }

}
