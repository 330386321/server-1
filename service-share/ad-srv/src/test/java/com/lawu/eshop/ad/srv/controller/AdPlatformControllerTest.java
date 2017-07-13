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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.AdSrvApplicationTest;
import com.lawu.eshop.ad.srv.mapper.AdPlatformDOMapper;
import com.lawu.eshop.framework.web.HttpCode;

/**
 * @author zhangrc
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdSrvApplicationTest.class)
@WebAppConfiguration
public class AdPlatformControllerTest {

    private MockMvc mvc;

    @Autowired
    private AdPlatformController adPlatformController;

    @Autowired
    private AdPlatformDOMapper adPlatformDOMapper;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(adPlatformController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void save() {
    	AdPlatformParam adPlatformParam=new AdPlatformParam();
		adPlatformParam.setTitle("要购物热门商品");
		adPlatformParam.setPositionEnum(PositionEnum.SHOPPING_HOT);
		adPlatformParam.setContent("测试");
		adPlatformParam.setTypeEnum(TypeEnum.TYPE_PRODUCT);
		adPlatformParam.setProductId(10061l);
		adPlatformParam.setRegionPath("44/4403/440305");
    	String requestJson = JSONObject.toJSONString(adPlatformParam);
        RequestBuilder request = post("/adPlatform/saveAdPlatform").contentType(MediaType.APPLICATION_JSON).content(requestJson).param("url", "ad_image/1494898117327258307.png");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
    
    @Transactional
    @Rollback
    @Test
    public void selectByPosition() {
    	AdPlatformParam adPlatformParam=new AdPlatformParam();
		adPlatformParam.setTitle("要购物热门商品");
		adPlatformParam.setPositionEnum(PositionEnum.SHOPPING_HOT);
		adPlatformParam.setContent("测试");
		adPlatformParam.setTypeEnum(TypeEnum.TYPE_PRODUCT);
		adPlatformParam.setProductId(10061l);
		adPlatformParam.setRegionPath("44/4403/440305");
    	String requestJson = JSONObject.toJSONString(adPlatformParam);
        RequestBuilder request = post("/adPlatform/saveAdPlatform").contentType(MediaType.APPLICATION_JSON).content(requestJson).param("url", "ad_image/1494898117327258307.png");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            
            String requestListJson = JSONObject.toJSONString(PositionEnum.SHOPPING_HOT);
            RequestBuilder requestList = post("/adPlatform/selectByPosition").contentType(MediaType.APPLICATION_JSON).content(requestListJson);
            ResultActions performList = mvc.perform(requestList);
            MvcResult mvcResultList = performList.andExpect(status().is(HttpCode.SC_ACCEPTED)).andDo(MockMvcResultHandlers.print()).andReturn();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    @Transactional
    @Rollback
    @Test
    public void selectList() {
    	AdPlatformParam adPlatformParam=new AdPlatformParam();
		adPlatformParam.setTitle("要购物热门商品");
		adPlatformParam.setPositionEnum(PositionEnum.SHOPPING_HOT);
		adPlatformParam.setContent("测试");
		adPlatformParam.setTypeEnum(TypeEnum.TYPE_PRODUCT);
		adPlatformParam.setProductId(10061l);
		adPlatformParam.setRegionPath("44/4403/440305");
    	String requestJson = JSONObject.toJSONString(adPlatformParam);
        RequestBuilder request = post("/adPlatform/saveAdPlatform").contentType(MediaType.APPLICATION_JSON).content(requestJson).param("url", "ad_image/1494898117327258307.png");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            
            AdPlatformFindParam param=new AdPlatformFindParam();
            param.setCurrentPage(1);
    		param.setPageSize(20);
    		param.setTitle("要购物");
    		param.setPositionEnum(PositionEnum.SHOPPING_HOT);
            String requestListJson = JSONObject.toJSONString(param);
            RequestBuilder requestList = post("/adPlatform/selectList").contentType(MediaType.APPLICATION_JSON).content(requestListJson);
            ResultActions performList = mvc.perform(requestList);
            MvcResult mvcResultList = performList.andExpect(status().is(HttpCode.SC_ACCEPTED)).andDo(MockMvcResultHandlers.print()).andReturn();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
    
  
	

}
