package com.lawu.eshop.ad.srv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.ad.constants.FavoriteAdTypeEnum;
import com.lawu.eshop.ad.param.FavoriteAdParam;
import com.lawu.eshop.ad.srv.AdSrvApplicationTest;
import com.lawu.eshop.ad.srv.domain.FavoriteAdDO;
import com.lawu.eshop.ad.srv.mapper.FavoriteAdDOMapper;
import com.lawu.eshop.framework.web.HttpCode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdSrvApplicationTest.class)
@WebAppConfiguration
public class FavoriteAdControllerTest {
	
	 private MockMvc mvc;

     @Autowired
     private FavoriteAdController favoriteAdController;
     
     
     @Autowired
     private FavoriteAdDOMapper favoriteAdDOMapper;

     @Before
     public void setUp() throws Exception {
         mvc = MockMvcBuilders.standaloneSetup(favoriteAdController).build();
     }
     
     @Transactional
     @Rollback
     @Test
     public void save() {
         try {
             RequestBuilder request = put("/favoriteAd/save").param("memberId", "1").param("adId", "1");
             ResultActions perform= mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();

         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }

     }
     
     @Transactional
     @Rollback
     @Test
     public void saveExist() {
    	 FavoriteAdDO favoriteAdDO = new FavoriteAdDO();
    	 favoriteAdDO.setAdId(1l);
    	 favoriteAdDO.setGmtCreate(new Date());
    	 favoriteAdDO.setMemberId(1l);
    	 favoriteAdDOMapper.insert(favoriteAdDO);
         try {
             RequestBuilder request = put("/favoriteAd/save").param("memberId", favoriteAdDO.getMemberId().toString()).param("adId", favoriteAdDO.getAdId().toString());
             ResultActions perform= mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();

         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }

     }
     
     @Transactional
     @Rollback
     @Test
     public void remove() {
    	 FavoriteAdDO favoriteAdDO=new FavoriteAdDO();
    	 favoriteAdDO.setAdId(1l);
    	 favoriteAdDO.setGmtCreate(new Date());
    	 favoriteAdDO.setMemberId(1l);
    	 favoriteAdDOMapper.insert(favoriteAdDO);
         try {
             RequestBuilder request = delete("/favoriteAd/remove/"+favoriteAdDO.getAdId()).param("memberId", "1");
             ResultActions perform= mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();

         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }

     }
     
     
     @Transactional
     @Rollback
     @Test
     public void selectMyFavoriteAd() {
    	 FavoriteAdParam paramQuery=new FavoriteAdParam();
     	 paramQuery.setCurrentPage(1);
     	 paramQuery.setPageSize(10);
     	 paramQuery.setTypeEnum(FavoriteAdTypeEnum.AD_TYPE_EGAIN);
         String requestListJson = JSONObject.toJSONString(paramQuery);
         try {
             RequestBuilder request = post("/favoriteAd/selectMyFavoriteAd").param("memberId", "1").contentType(MediaType.APPLICATION_JSON).content(requestListJson);
             ResultActions perform= mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();

         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }

     }
     
     @Transactional
     @Rollback
     @Test
     public void isFavoriteAd() {
         try {
             RequestBuilder request = get("/favoriteAd/isFavoriteAd").param("adId", "1").param("memberId", "1");
             ResultActions perform= mvc.perform(request);
             MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();

         } catch (Exception e) {
             e.printStackTrace();
             Assert.fail(e.getMessage());
         }

     }

}
