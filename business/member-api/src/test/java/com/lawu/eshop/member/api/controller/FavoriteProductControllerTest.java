package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.interceptor.AuthorizationInterceptor;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.member.api.MemberApiApplicationTest;
import com.lawu.eshop.user.constants.ManageTypeEnum;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yangqh
 * @date 2017/7/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MemberApiApplicationTest.class)
@WebAppConfiguration
public class FavoriteProductControllerTest {

    private MockMvc mvc;

    @Autowired
    private FavoriteProductController favoriteProductController;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(favoriteProductController).addInterceptors(authorizationInterceptor).build();
    }

    @Test
    public void addFavoriteProduct() {
        RequestBuilder request = post("/favoriteProduct/addFavoriteProduct").header("authorization","").param("productId","1");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void selectMyFavoriteProduct() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("currentPage","1");
        params.add("pageSize","10");
        RequestBuilder request = get("/favoriteProduct/selectMyFavoriteProduct").header("authorization","").params(params);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void remove() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("manageTypeEnum", ManageTypeEnum.COMMON.name());
        params.add("merchantId","1");
        RequestBuilder request = delete("/favoriteProduct/remove/1").header("authorization","").param("productId","1");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_NO_CONTENT)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
