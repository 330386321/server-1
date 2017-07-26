package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.interceptor.AuthorizationInterceptor;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.member.api.MemberApiApplicationTest;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.ThirdPayBodyEnum;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yangqh
 * @date 2017/7/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MemberApiApplicationTest.class)
@WebAppConfiguration
public class AlipayControllerTest extends BaseController {

    private MockMvc mvc;

    @Autowired
    private AlipayController alipayController;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(alipayController).addInterceptors(authorizationInterceptor).build();
    }

    @Test
    public void getAppAlipayReqParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("bizIds", "1");
        params.add("thirdPayBodyEnum", ThirdPayBodyEnum.M_RECHARGE_BALANCE_I.name());
        params.add("bizFlagEnum", ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.name());
        RequestBuilder request = post("/alipay/getAppAlipayReqParams").header("authorization","").params(params);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        params.add("thirdPayBodyEnum", ThirdPayBodyEnum.BILL_PAY_I.name());
        params.add("bizFlagEnum", ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.name());
        request = post("/alipay/getAppAlipayReqParams").header("authorization","").params(params);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        params.add("thirdPayBodyEnum", ThirdPayBodyEnum.ORDER_PAY_A.name());
        params.add("bizFlagEnum", ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.name());
        request = post("/alipay/getAppAlipayReqParams").header("authorization","").params(params);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

}
