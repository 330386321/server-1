package com.lawu.eshop.order.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusToMemberEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemDTO;
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.srv.OrderSrvApplicationTest;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverterTest;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverterTest;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.utils.RandomUtil;

/**
 * 
 * @author Sunny
 * @date 2017年6月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderSrvApplicationTest.class)
@WebAppConfiguration
public class ShoppingOrderControllerTest {
	
    private MockMvc mvc;

    @Autowired
    private ShoppingOrderController shoppingOrderController;
    
    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Autowired
    private ShoppingCartDOMapper shoppingCartDOMapper;
    
	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
	
	@Autowired
	private PropertyDOMapper propertyDOMapper;
    
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(shoppingOrderController).build();
    }
    
    @Transactional
    @Rollback
    @Test
    public void save() throws Exception {
    	List<ShoppingOrderSettlementParam> expected = new ArrayList<>();
    	ShoppingOrderSettlementParam param = ShoppingOrderConverterTest.initShoppingOrderSettlementParam();
    	ShoppingOrderSettlementItemParam item = param.getItems().get(0);
    	expected.add(param);
    	
    	String content = JSONObject.toJSONString(expected);
    	RequestBuilder request = MockMvcRequestBuilders.post("/shoppingOrder/save").contentType(MediaType.APPLICATION_JSON).content(content);    	
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        List<Long> actual = JSONObject.parseArray(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), Long.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());
        
    	ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
    	shoppingOrderDOExample.createCriteria().andMemberIdEqualTo(param.getMemberId());
    	ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByExample(shoppingOrderDOExample).get(0);
    	
    	ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
    	shoppingOrderItemDOExample.createCriteria().andShoppingOrderIdEqualTo(shoppingOrderDO.getId());
    	ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByExample(shoppingOrderItemDOExample).get(0);
    	
    	ShoppingOrderConverterTest.assertShoppingOrderDO(param, shoppingOrderDO);
    	Assert.assertEquals(actual.get(0), shoppingOrderDO.getId());
    	ShoppingOrderItemConverterTest.assertShoppingOrderItemDO(item, shoppingOrderItemDO, shoppingOrderDO.getId());
    }
    
    @Transactional
    @Rollback
    @Test
    public void selectPageByMemberId() throws Exception {
    	ShoppingOrderDO expected  = new ShoppingOrderDO();
    	expected.setCommodityTotalPrice(new BigDecimal(1));
    	expected.setActualAmount(new BigDecimal(1));
    	expected.setFreightPrice(new BigDecimal(0));
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setIsFans(true);
    	expected.setIsNeedsLogistics(true);
    	expected.setIsNoReasonReturn(true);
    	expected.setMemberId(1L);
    	expected.setMemberNum("M0001");
    	expected.setMerchantId(1L);
    	expected.setMerchantName("拉乌网络");
    	expected.setMerchantStoreId(1L);
    	expected.setMerchantNum("B0001");
    	expected.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setExpressCompanyCode("韵达");
    	expected.setExpressCompanyId(1);
    	expected.setExpressCompanyName("韵达");
    	expected.setGmtPayment(new Date());
    	expected.setGmtTransaction(new Date());
    	expected.setGmtTransport(new Date());
    	expected.setIsDone(true);
    	expected.setThirdNumber("654321");
    	expected.setWaybillNum("3923440690592");
    	expected.setGmtCommission(new Date());
    	expected.setShoppingCartIdsStr("1,2");
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	expected.setSendTime(0);
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSendTime(0);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	
    	ShoppingOrderQueryForeignToMemberParam param = new ShoppingOrderQueryForeignToMemberParam();
    	param.setCurrentPage(1);
    	param.setPageSize(10);
    	param.setOrderStatus(ShoppingOrderStatusToMemberEnum.BE_EVALUATED);
    	param.setKeyword(expected.getOrderNum());
    	
    	String content = JSONObject.toJSONString(param);
    	RequestBuilder request = MockMvcRequestBuilders.post("/shoppingOrder/selectPageByMemberId/" + expected.getMemberId()).contentType(MediaType.APPLICATION_JSON).content(content);    	
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        Page<JSONObject> actual = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), Page.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(param.getCurrentPage(), actual.getCurrentPage());
    	Assert.assertEquals(1, actual.getTotalCount().intValue());
    	for (JSONObject jsonObject : actual.getRecords()) {
			 ShoppingOrderExtendQueryDTO shoppingOrderExtendQueryDTO = JSONObject.toJavaObject(jsonObject, ShoppingOrderExtendQueryDTO.class);
			 Assert.assertNotNull(shoppingOrderExtendQueryDTO);
			 Assert.assertEquals(expected.getMerchantName(), shoppingOrderExtendQueryDTO.getMerchantName());
			 Assert.assertEquals(1, shoppingOrderExtendQueryDTO.getAmountOfGoods().intValue());
			 Assert.assertEquals(expected.getId(), shoppingOrderExtendQueryDTO.getId());
			 Assert.assertEquals(expected.getFreightPrice().doubleValue(), shoppingOrderExtendQueryDTO.getFreightPrice().doubleValue(), 0D);
			 Assert.assertEquals(expected.getIsDone(), shoppingOrderExtendQueryDTO.getIsDone());
			 Assert.assertEquals(expected.getIsNeedsLogistics(), shoppingOrderExtendQueryDTO.getIsNeedsLogistics());
			 Assert.assertEquals(expected.getIsNoReasonReturn(), shoppingOrderExtendQueryDTO.getIsNoReasonReturn());
			 Assert.assertEquals(expected.getMerchantId(), shoppingOrderExtendQueryDTO.getMerchantId());
			 Assert.assertEquals(expected.getMerchantStoreId(), shoppingOrderExtendQueryDTO.getMerchantStoreId());
			 Assert.assertEquals(expected.getOrderStatus(), shoppingOrderExtendQueryDTO.getOrderStatus().getValue());
			 Assert.assertEquals(expected.getOrderTotalPrice().doubleValue(), shoppingOrderExtendQueryDTO.getOrderTotalPrice().doubleValue(), 0D);
			 for (ShoppingOrderItemDTO item : shoppingOrderExtendQueryDTO.getItems()) {
				Assert.assertEquals(shoppingOrderItemDO.getId(), item.getId());
				Assert.assertEquals(shoppingOrderItemDO.getIsAllowRefund(), item.getIsAllowRefund());
				Assert.assertEquals(shoppingOrderItemDO.getIsEvaluation(), item.getIsEvaluation());
				Assert.assertEquals(shoppingOrderItemDO.getOrderStatus(), item.getOrderStatus().getValue());
				Assert.assertEquals(shoppingOrderItemDO.getProductFeatureImage(), item.getProductFeatureImage());
				Assert.assertEquals(shoppingOrderItemDO.getProductId(), item.getProductId());
				Assert.assertEquals(shoppingOrderItemDO.getProductModelId(), item.getProductModelId());
				Assert.assertEquals(shoppingOrderItemDO.getProductModelName(), item.getProductModelName());
				Assert.assertEquals(shoppingOrderItemDO.getProductName(), item.getProductName());
				Assert.assertEquals(shoppingOrderItemDO.getQuantity(), item.getQuantity());
				Assert.assertEquals(shoppingOrderItemDO.getRefundStatus(), item.getRefundStatus() != null ? item.getRefundStatus().getValue() : null);
				Assert.assertEquals(shoppingOrderItemDO.getRegularPrice().doubleValue(), item.getRegularPrice().doubleValue(), 0D);
			 }
    	}
    }
    
    @Ignore
    @Test
    public void setMemberTokenOneToOne() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/shoppingOrder/getExpressInfo/75").accept(MediaType.APPLICATION_JSON)).andReturn();
        mvcResult.getResponse().getStatus();
        mvcResult.getResponse().getContentAsString();
    }

}
