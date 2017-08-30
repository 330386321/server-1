package com.lawu.eshop.order.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusToMemberEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusToMerchantEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderQueryToMerchantDTO;
import com.lawu.eshop.order.param.ShoppingOrderRequestRefundParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.order.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMemberParam;
import com.lawu.eshop.order.param.foreign.ShoppingOrderQueryForeignToMerchantParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundQueryForeignParam;
import com.lawu.eshop.order.srv.OrderSrvApplicationTest;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverterTest;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverterTest;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDOExample;
import com.lawu.eshop.order.srv.json.JCDateDeserializer;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundProcessDOMapper;
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
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
	
	@Autowired
	private ShoppingRefundProcessDOMapper shoppingRefundProcessDOMapper;
	
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
    
    @SuppressWarnings("unchecked")
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
    
	@Transactional
    @Rollback
    @Test
    public void cancelOrder() throws Exception {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1,2");
    	expected.setSendTime(0);
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.put("/shoppingOrder/cancelOrder/" + expected.getMemberId() + "/" + expected.getId());    	
        ResultActions perform = mvc.perform(request);
        perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
    	ShoppingOrderDO actual = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actual.getOrderStatus());
    }
    
	@Transactional
    @Rollback
    @Test
    public void deleteOrder() throws Exception {
		/*
		 * 插入一条订单记录
		 * 是商家粉丝/需要物流/七天无理由退货/待支付/未计算提成/未完成/交易取消/有效
		 */
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1,2");
    	expected.setSendTime(0);
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.put("/shoppingOrder/deleteOrder/" + expected.getMemberId() + "/" + expected.getId());
        ResultActions perform = mvc.perform(request);
        perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
    	ShoppingOrderDO actual = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(StatusEnum.INVALID.getValue(), actual.getStatus());
    }
	
	@Transactional
    @Rollback
    @Test
    public void getExpressInfo() throws Exception {
		/*
		 * 插入一条订单记录
		 * 是粉丝/需要物流/七天无理由退货/未完成/未计算提成/待收货
		 */
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1,2");
    	expected.setSendTime(0);
    	expected.setExpressCompanyCode("SF");
    	expected.setExpressCompanyId(1);
    	expected.setExpressCompanyName("顺丰速递");
    	expected.setWaybillNum("123456");
    	expected.setGmtPayment(new Date());
    	expected.setGmtTransport(new Date());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	// 用户调用
    	if (true) {
	    	RequestBuilder request = MockMvcRequestBuilders.get("/shoppingOrder/getExpressInfo/" + expected.getId())
	    			.param("memberId", expected.getMemberId().toString());
	        ResultActions perform = mvc.perform(request);
	        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
	        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
	        		.andDo(MockMvcResultHandlers.print())
	        		.andReturn();
	        
	        ShoppingOrderExpressDTO actual = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), ShoppingOrderExpressDTO.class);
	        Assert.assertNotNull(actual);
	        Assert.assertEquals(expected.getExpressCompanyName(), actual.getExpressCompanyName());
	        Assert.assertEquals(expected.getWaybillNum(), actual.getWaybillNum());
	        Assert.assertNotNull(actual.getExpressInquiriesDetailDTO());
    	}
    	
    	// 商家调用
        if (true) {
	        RequestBuilder request = MockMvcRequestBuilders.get("/shoppingOrder/getExpressInfo/" + expected.getId())
	    			.param("merchantId", expected.getMerchantId().toString());
	        ResultActions perform = mvc.perform(request);
	        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
	        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
	        		.andDo(MockMvcResultHandlers.print())
	        		.andReturn();
	        
	        ShoppingOrderExpressDTO actual = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), ShoppingOrderExpressDTO.class);
	        Assert.assertNotNull(actual);
	        Assert.assertEquals(expected.getExpressCompanyName(), actual.getExpressCompanyName());
	        Assert.assertEquals(expected.getWaybillNum(), actual.getWaybillNum());
	        Assert.assertNotNull(actual.getExpressInquiriesDetailDTO());
        }
    }
	
	@SuppressWarnings("unchecked")
	@Transactional
    @Rollback
    @Test
    public void selectRefundPageByMemberId() throws Exception {
		/*
		 * 插入一条订单记录
		 * 是粉丝/需要物流/七天无理由退货/未完成/未计算提成/待收货/余额支付
		 */
		ShoppingOrderDO expected = new ShoppingOrderDO();
    	expected.setCommodityTotalPrice(new BigDecimal(1));
    	expected.setActualAmount(new BigDecimal(1));
    	expected.setFreightPrice(new BigDecimal(0));
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setGmtTransport(new Date());
    	expected.setIsFans(true);
    	expected.setIsNeedsLogistics(true);
    	expected.setIsNoReasonReturn(true);
    	expected.setMemberId(1L);
    	expected.setMemberNum("M0001");
    	expected.setMerchantId(1L);
    	expected.setMerchantName("拉乌网络");
    	expected.setMerchantStoreId(1L);
    	expected.setMerchantNum("B0001");
    	expected.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setExpressCompanyCode("SF");
    	expected.setExpressCompanyId(1);
    	expected.setExpressCompanyName("顺丰速递");
    	expected.setWaybillNum("123456");
    	expected.setGmtPayment(new Date());
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
		/*
		 * 插入一条订单项记录
		 * 待收货/未评价/允许退货
		 */
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
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
    	
		/*
		 * 插入一条订单项记录
		 * 退货退款/有效
		 */
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingRefundQueryForeignParam param = new ShoppingRefundQueryForeignParam();
    	param.setCurrentPage(1);
    	param.setKeyword(expected.getOrderNum());
    	param.setPageSize(10);
    	String content = JSONObject.toJSONString(param);
    	
    	RequestBuilder request = MockMvcRequestBuilders.post("/shoppingOrder/selectRefundPageByMemberId/" + expected.getMemberId()).contentType(MediaType.APPLICATION_JSON_UTF8).content(content);
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        Page<JSONObject> page = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), Page.class);
        Assert.assertNotNull(page);
        Assert.assertEquals(param.getCurrentPage(), page.getCurrentPage());
    	Assert.assertEquals(1, page.getTotalCount().intValue());
    	ShoppingOrderItemRefundDTO actual = JSONObject.parseObject(page.getRecords().get(0).toString(), ShoppingOrderItemRefundDTO.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getMerchantName(), actual.getMerchantName());
        Assert.assertEquals(shoppingRefundDetailDO.getAmount().doubleValue(), actual.getAmount().doubleValue(), 0D);
        Assert.assertEquals(shoppingOrderItemDO.getId(), actual.getId());
        Assert.assertEquals(expected.getIsNoReasonReturn(), actual.getIsNoReasonReturn());
        Assert.assertEquals(expected.getMerchantId(), actual.getMerchantId());
        Assert.assertEquals(expected.getMerchantStoreId(), actual.getMerchantStoreId());
        Assert.assertEquals(shoppingOrderItemDO.getProductFeatureImage(), actual.getProductFeatureImage());
        Assert.assertEquals(shoppingOrderItemDO.getProductModelName(), actual.getProductModelName());
        Assert.assertEquals(shoppingOrderItemDO.getProductName(), actual.getProductName());
        Assert.assertEquals(shoppingOrderItemDO.getQuantity(), actual.getQuantity());
        Assert.assertEquals(shoppingOrderItemDO.getRefundStatus(), actual.getRefundStatus().getValue());
        Assert.assertEquals(shoppingRefundDetailDO.getId(), actual.getShoppingRefundDetailId());
        Assert.assertEquals(shoppingRefundDetailDO.getType(), actual.getType().getValue());
    }
	
	@Transactional
    @Rollback
    @Test
    public void requestRefund() throws Exception {
		/*
		 * 插入一条订单记录
		 * 是粉丝/需要物流/七天无理由退货/未完成/未计算提成/待收货/余额支付
		 */
		ShoppingOrderDO expected = new ShoppingOrderDO();
    	expected.setCommodityTotalPrice(new BigDecimal(1));
    	expected.setActualAmount(new BigDecimal(1));
    	expected.setFreightPrice(new BigDecimal(0));
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setGmtTransport(new Date());
    	expected.setIsFans(true);
    	expected.setIsNeedsLogistics(true);
    	expected.setIsNoReasonReturn(true);
    	expected.setMemberId(1L);
    	expected.setMemberNum("M0001");
    	expected.setMerchantId(1L);
    	expected.setMerchantName("拉乌网络");
    	expected.setMerchantStoreId(1L);
    	expected.setMerchantNum("B0001");
    	expected.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setExpressCompanyCode("SF");
    	expected.setExpressCompanyId(1);
    	expected.setExpressCompanyName("顺丰速递");
    	expected.setWaybillNum("123456");
    	expected.setGmtPayment(new Date());
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
		/*
		 * 插入一条订单项记录
		 * 待收货/未评价/允许退货
		 */
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
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
    	
    	ShoppingOrderRequestRefundParam param = new ShoppingOrderRequestRefundParam();
    	param.setDescription("就是要退款");
    	param.setReason("七天无理由退款");
    	param.setType(ShoppingRefundTypeEnum.REFUND);
    	String content = JSONObject.toJSONString(param);
    	
    	RequestBuilder request = MockMvcRequestBuilders.put("/shoppingOrder/requestRefund/" + shoppingOrderItemDO.getId())
    			.param("memberId", expected.getMemberId().toString())
    			.contentType(MediaType.APPLICATION_JSON_UTF8).content(content);
        ResultActions perform = mvc.perform(request);
        perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(ShoppingOrderStatusEnum.REFUNDING.getValue(), actual.getOrderStatus());
    	Assert.assertEquals(RefundStatusEnum.TO_BE_CONFIRMED.getValue(), actual.getRefundStatus());
    	
    	ShoppingRefundDetailDOExample example = new ShoppingRefundDetailDOExample();
    	example.createCriteria().andShoppingOrderItemIdEqualTo(shoppingOrderItemDO.getId());
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByExample(example).get(0);
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertEquals(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())).doubleValue(), actualShoppingRefundDetailDO.getAmount().doubleValue(), 0D);
    	Assert.assertEquals(param.getReason(), actualShoppingRefundDetailDO.getReason());
    	Assert.assertEquals(param.getDescription(), actualShoppingRefundDetailDO.getDescription());
    	Assert.assertEquals(param.getType().getValue(), actualShoppingRefundDetailDO.getType());
    	Assert.assertEquals(StatusEnum.VALID.getValue(), actualShoppingRefundDetailDO.getStatus());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(actualShoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(actualShoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(actual.getRefundStatus(), shoppingRefundProcessDO.getRefundStatus());
    }
	
	@Transactional
    @Rollback
    @Test
    public void orderPayment() throws Exception {
		/*
		 * 插入一条订单记录
		 * 是粉丝/需要物流/七天无理由退货/未完成/未计算提成/待支付
		 */
		ShoppingOrderDO expected = new ShoppingOrderDO();
    	expected.setCommodityTotalPrice(new BigDecimal(1));
    	expected.setActualAmount(new BigDecimal(1));
    	expected.setFreightPrice(new BigDecimal(0));
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setIsFans(true);
    	expected.setIsNoReasonReturn(true);
    	expected.setMemberId(1L);
    	expected.setMemberNum("M0001");
    	expected.setMerchantId(1L);
    	expected.setMerchantName("拉乌网络");
    	expected.setMerchantStoreId(1L);
    	expected.setMerchantNum("B0001");
    	expected.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	shoppingOrderDOMapper.insertSelective(expected);
    	
		/*
		 * 插入一条订单项记录
		 * 待收货/未评价/允许退货
		 */
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue());
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
    	
    	RequestBuilder request = MockMvcRequestBuilders.get("/shoppingOrder/orderPayment/" + expected.getId()).param("memberId", expected.getMemberId().toString());
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        ShoppingOrderPaymentDTO actual = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), ShoppingOrderPaymentDTO.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getOrderNum(), actual.getOrderNum());
        Assert.assertEquals(expected.getOrderTotalPrice().doubleValue(), actual.getOrderTotalPrice().doubleValue(), 0D);
    }
	
	@Transactional
    @Rollback
    @Test
    public void tradingSuccess() throws Exception {
		/*
		 * 插入一条订单记录
		 * 是粉丝/需要物流/七天无理由退货/未完成/未计算提成/待收货/余额支付
		 */
		ShoppingOrderDO expected = new ShoppingOrderDO();
    	expected.setCommodityTotalPrice(new BigDecimal(1));
    	expected.setActualAmount(new BigDecimal(1));
    	expected.setFreightPrice(new BigDecimal(0));
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setIsFans(true);
    	expected.setIsNoReasonReturn(true);
    	expected.setMemberId(1L);
    	expected.setMemberNum("M0001");
    	expected.setMerchantId(1L);
    	expected.setMerchantName("拉乌网络");
    	expected.setMerchantStoreId(1L);
    	expected.setMerchantNum("B0001");
    	expected.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(RandomUtil.getTableNumRandomString(""));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	expected.setGmtPayment(new Date());
    	expected.setExpressCompanyCode("SF");
    	expected.setExpressCompanyId(1);
    	expected.setExpressCompanyName("顺丰速递");
    	expected.setWaybillNum("123456");
    	expected.setGmtTransport(new Date());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
		/*
		 * 插入一条订单项记录
		 * 待收货/未评价/允许退货
		 */
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
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
    	
    	/*
    	 * 查询一条退款中的订单项记录
    	 * 允许退款/未评论/退款中/待确认 
    	 */
    	ShoppingOrderItemDO refundShoppingOrderItemDO = new ShoppingOrderItemDO();
    	refundShoppingOrderItemDO.setGmtCreate(new Date());
    	refundShoppingOrderItemDO.setGmtModified(new Date());
    	refundShoppingOrderItemDO.setIsAllowRefund(true);
    	refundShoppingOrderItemDO.setIsEvaluation(false);
    	refundShoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	refundShoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
    	refundShoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	refundShoppingOrderItemDO.setProductId(1L);
    	refundShoppingOrderItemDO.setProductName("productName");
    	refundShoppingOrderItemDO.setProductModelId(1L);
    	refundShoppingOrderItemDO.setProductModelName("test");
    	refundShoppingOrderItemDO.setQuantity(1);
    	refundShoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	refundShoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	refundShoppingOrderItemDO.setSendTime(0);
    	refundShoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDOMapper.insert(refundShoppingOrderItemDO);
    	
    	/*
    	 * 查询一条退款详情记录
    	 * 退款/有效
    	 */
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setAmount(refundShoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(refundShoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(refundShoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	RequestBuilder request = MockMvcRequestBuilders.put("/shoppingOrder/tradingSuccess/" + expected.getId()).param("memberId", expected.getMemberId().toString());
        ResultActions perform = mvc.perform(request);
        perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        ShoppingOrderDO actual = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(false, actual.getIsAutomaticReceipt());
    	Assert.assertEquals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue(), actual.getOrderStatus());
    	
    	ShoppingOrderItemDO actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actualShoppingOrderItemDO);
    	Assert.assertEquals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue(), actualShoppingOrderItemDO.getOrderStatus());
    	
    	actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(refundShoppingOrderItemDO.getId());
    	Assert.assertNotNull(actualShoppingOrderItemDO);
    	Assert.assertNull(actualShoppingOrderItemDO.getRefundStatus());
    	Assert.assertEquals(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue(), actualShoppingOrderItemDO.getOrderStatus());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertEquals(StatusEnum.INVALID.getValue(), actualShoppingRefundDetailDO.getStatus());
    }
	
    @SuppressWarnings("unchecked")
	@Transactional
    @Rollback
    @Test
    public void selectPageByMerchantId() throws Exception {
    	/*
    	 * 插入一条订单记录
    	 * 是粉丝/需要物流/七天无理由退货/交易成功/未计算提成/有效/余额支付/已完成
    	 */
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
    	expected.setExpressCompanyCode("YD");
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
    	
    	/*
    	 * 查询一条订单项记录
    	 * 交易成功
    	 */
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
    	
    	ShoppingOrderQueryForeignToMerchantParam param = new ShoppingOrderQueryForeignToMerchantParam();
    	param.setCurrentPage(1);
    	param.setPageSize(10);
    	param.setOrderStatus(ShoppingOrderStatusToMerchantEnum.COMPLETED);
    	param.setKeyword(expected.getOrderNum());
    	
    	String content = JSONObject.toJSONString(param);
    	RequestBuilder request = MockMvcRequestBuilders.post("/shoppingOrder/selectPageByMerchantId/" + expected.getMemberId()).contentType(MediaType.APPLICATION_JSON).content(content);    	
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        Page<JSONObject> page = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), Page.class);
        Assert.assertNotNull(page);
        Assert.assertEquals(param.getCurrentPage(), page.getCurrentPage());
    	Assert.assertEquals(1, page.getTotalCount().intValue());
    	
		ParserConfig mapping = new ParserConfig();
    	mapping.putDeserializer(Date.class, new JCDateDeserializer());
    	ShoppingOrderQueryToMerchantDTO actual = JSONObject.parseObject(page.getRecords().get(0).toJSONString(), ShoppingOrderQueryToMerchantDTO.class, mapping, JSON.DEFAULT_PARSER_FEATURE);
		
    	Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getConsigneeName(), actual.getConsigneeName());
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getOrderNum(), actual.getOrderNum());
		Assert.assertEquals(expected.getOrderStatus(), actual.getOrderStatus().getValue());
		Assert.assertEquals(shoppingOrderItemDO.getProductFeatureImage(), actual.getProductFeatureImage());
		Assert.assertEquals(shoppingOrderItemDO.getGmtCreate().getTime(), actual.getGmtCreate().getTime(), 24*60*60*1000);
    }    
}
