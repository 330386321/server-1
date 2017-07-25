package com.lawu.eshop.order.srv.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.srv.OrderSrvApplicationTest;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderSrvApplicationTest.class)
@WebAppConfiguration
public class ShoppingCartControllerTest {

    private MockMvc mvc;

    @Autowired
    private ShoppingCartController shoppingCartController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void save() throws Exception {
    	Long memberId = 1L;
    	ShoppingCartSaveParam param = new ShoppingCartSaveParam();
    	param.setMerchantId(1L);
    	param.setMerchantName("拉乌网络");
    	param.setMerchantStoreId(1L);
    	RequestBuilder request = MockMvcRequestBuilders.post(memberId + "").contentType(MediaType.APPLICATION_JSON).content("");    	
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
