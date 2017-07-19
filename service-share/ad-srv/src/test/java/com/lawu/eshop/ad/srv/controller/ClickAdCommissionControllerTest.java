package com.lawu.eshop.ad.srv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.srv.AdSrvApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdSrvApplicationTest.class)
@WebAppConfiguration
public class ClickAdCommissionControllerTest {
	

    private MockMvc mvc;

    @Autowired
    private ClickAdCommissionController clickAdCommissionController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(clickAdCommissionController).build();
    }
    
    @Transactional
    @Rollback
    @Test
    public void getNoneCommissionAds() {
    	
        try {
            RequestBuilder request = get("/commission/getNoneCommissionAds");
            ResultActions perform= mvc.perform(request);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
    
    @Transactional
    @Rollback
    @Test
    public void updateMemberAdRecardStatus() {
    	
        try {
            RequestBuilder request = post("/commission/updateMemberAdRecardStatus").param("id", "1");
            ResultActions perform= mvc.perform(request);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

}
