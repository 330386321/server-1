package com.lawu.eshop.order.srv.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.OrderSrvApplicationTest;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderSrvApplicationTest.class)
@WebAppConfiguration
public class PayOrderControllerTest {

    private MockMvc mvc;

    @Autowired
    private PayOrderController payOrderController;
    
	@Autowired
	private PayOrderDOMapper payOrderDOMapper;
    
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(payOrderController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void save() throws Exception {
		Long memberId = 1L;
		String memberNum = "M00001";
		PayOrderParam param = new PayOrderParam();
		param.setFavoredAmount(1D);
		param.setMerchantId(1L);
		param.setMerchantNum("B00001");
		param.setNotFavoredAmount(1D);
		param.setTotalAmount(2D);
		param.setMerchantFavoredId(1L);
    	String content = JSONObject.toJSONString(param);
    	
    	RequestBuilder request = MockMvcRequestBuilders.post("/payOrder/savePayOrderInfo/" + memberId).param("numNum", memberNum).contentType(MediaType.APPLICATION_JSON).content(content);
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        PayOrderIdDTO payOrderIdDTO = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), PayOrderIdDTO.class);
        Assert.assertNotNull(payOrderIdDTO);
    }
}
