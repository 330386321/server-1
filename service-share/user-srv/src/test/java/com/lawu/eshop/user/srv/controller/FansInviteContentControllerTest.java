package com.lawu.eshop.user.srv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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

import com.gexin.fastjson.JSONObject;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.user.param.FansInviteContentParam;
import com.lawu.eshop.user.srv.UserSrvApplicationTest;

/**
 * @author hongqm
 * @date 2017/8/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserSrvApplicationTest.class)
@WebAppConfiguration
public class FansInviteContentControllerTest {

	private MockMvc mvc;

    @Autowired
    private FansInviteContentController fansInviteContentController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(fansInviteContentController).build();
    }
    
    @Transactional
    @Rollback
    @Test
    public void saveInviteContentService() {
    	FansInviteContentParam fansInviteContentParam = new FansInviteContentParam();
    	fansInviteContentParam.setFansInviteDetailId(1L);
    	fansInviteContentParam.setGmtCreate(new Date());
    	fansInviteContentParam.setGmtModified(new Date());
    	fansInviteContentParam.setInviteContent("invitecontent");
    	fansInviteContentParam.setLogoUrl("logourl");
    	fansInviteContentParam.setMerchantId(1L);
    	fansInviteContentParam.setMerchantNum("num");
    	fansInviteContentParam.setMerchantStoreIntro("intro");
    	fansInviteContentParam.setMerchantStoreName("storeName");
    	fansInviteContentParam.setUrl("url");
    	String str = JSONObject.toJSONString(fansInviteContentParam);
        RequestBuilder request = post("/fansInviteContent/saveInviteContentService").contentType(MediaType.APPLICATION_JSON).content(str);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
    
    @Transactional
    @Rollback
    @Test
    public void selectInviteContentById() {
        RequestBuilder request = post("/fansInviteContent/selectInviteContentById/1/1").param("id", "1").param("relateId", "1");
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
