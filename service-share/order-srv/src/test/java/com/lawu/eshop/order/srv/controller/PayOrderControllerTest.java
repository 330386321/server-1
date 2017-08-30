package com.lawu.eshop.order.srv.controller;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.EvaluationEnum;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.dto.MemberPayOrderInfoDTO;
import com.lawu.eshop.order.dto.MerchantPayOrderListDTO;
import com.lawu.eshop.order.dto.OperatorPayOrderListDTO;
import com.lawu.eshop.order.dto.PayOrderAutoCommentDTO;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.order.param.MerchantPayOrderListParam;
import com.lawu.eshop.order.param.OperatorPayOrderParam;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.OrderSrvApplicationTest;
import com.lawu.eshop.order.srv.domain.PayOrderDO;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;
import com.lawu.eshop.utils.StringUtil;

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
		PayOrderParam expected = new PayOrderParam();
		expected.setFavoredAmount(1D);
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(1D);
		expected.setTotalAmount(2D);
		expected.setMerchantFavoredId(1L);
    	String content = JSONObject.toJSONString(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.post("/payOrder/savePayOrderInfo/" + memberId).param("numNum", memberNum).contentType(MediaType.APPLICATION_JSON).content(content);
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        PayOrderIdDTO payOrderIdDTO = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), PayOrderIdDTO.class);
        Assert.assertNotNull(payOrderIdDTO);
        
        PayOrderDO actual = payOrderDOMapper.selectByPrimaryKey(payOrderIdDTO.getOrderId());
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getGmtCreate());
        Assert.assertNotNull(actual.getGmtModified());
        Assert.assertEquals(payOrderIdDTO.getOrderNum(), actual.getOrderNum());
        Assert.assertEquals(PayOrderStatusEnum.STATUS_UNPAY.getVal(), actual.getStatus());
        Assert.assertEquals(CommissionStatusEnum.NOT_COUNTED.getValue(), actual.getCommissionStatus());
        Assert.assertEquals(true, actual.getOrderStatus());
        Assert.assertEquals(false, actual.getIsEvaluation());
        Assert.assertEquals(memberId, actual.getMemberId());
        Assert.assertEquals(memberNum, actual.getMemberNum());
        Assert.assertEquals(expected.getFavoredAmount(), actual.getFavoredAmount().doubleValue(), 0D);
        Assert.assertEquals(expected.getMerchantId(), actual.getMerchantId());
        Assert.assertEquals(expected.getMerchantNum(), actual.getMerchantNum());
        Assert.assertEquals(expected.getNotFavoredAmount(), actual.getNotFavoredAmount().doubleValue(), 0D);
        Assert.assertEquals(expected.getTotalAmount(), actual.getTotalAmount().doubleValue(), 0D);
        Assert.assertEquals(expected.getTotalAmount() - expected.getFavoredAmount(), actual.getActualAmount().doubleValue(), 0D);
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    @Rollback
    @Test
    public void getpayOrderList() throws Exception {
    	// 插入一条已经支付成功并且未评论的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setCommentTime(new Date());
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setPayType(TransactionPayTypeEnum.BALANCE.getVal());
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		payOrderDOMapper.insert(expected);
    	
		PayOrderListParam param = new PayOrderListParam();
		param.setCurrentPage(1);
		param.setEvaluationEnum(EvaluationEnum.UN_EVALUATION);
		param.setPageSize(10);
		
		String content = JSONObject.toJSONString(param);
		
    	RequestBuilder request = MockMvcRequestBuilders.post("/payOrder/getpayOrderList/" + expected.getMemberId()).contentType(MediaType.APPLICATION_JSON_UTF8).content(content);
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        Page<JSONObject> page = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), Page.class);
        Assert.assertNotNull(page);
        Assert.assertEquals(param.getCurrentPage(), page.getCurrentPage());
    	Assert.assertEquals(1, page.getTotalCount().intValue());
        
    	for (JSONObject item : page.getRecords()) {
    		PayOrderDTO actual = JSONObject.toJavaObject(item, PayOrderDTO.class);
    		Assert.assertNotNull(actual);
    		Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualAmount().doubleValue(), 0D);
    		Assert.assertEquals(expected.getMerchantId(), actual.getMerchantId());
    		Assert.assertEquals(expected.getIsEvaluation(), actual.getEvaluationEnum().getVal());
    		Assert.assertEquals(expected.getFavoredAmount().doubleValue(), actual.getFavoredAmount().doubleValue(), 0D);
    		Assert.assertEquals(expected.getGmtCreate().getTime(), actual.getGmtCreate().getTime(), 24*60*60*1000);
    		Assert.assertEquals(expected.getId(), actual.getId());
    		Assert.assertEquals(expected.getTotalAmount().doubleValue(), actual.getTotalAmount().doubleValue(), 0D);
    	}
    }
    
    @Transactional
    @Rollback
    @Test
    public void delPayOrderInfo() throws Exception {
    	// 插入一条已经支付成功并且未评论的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setCommentTime(new Date());
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setPayType(TransactionPayTypeEnum.BALANCE.getVal());
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		payOrderDOMapper.insert(expected);
    	
		
    	RequestBuilder request = MockMvcRequestBuilders.put("/payOrder/delPayOrderInfo/" + expected.getId()).param("memberId", expected.getMemberId().toString());
        ResultActions perform = mvc.perform(request);
        perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        PayOrderDO actual = payOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actual);
        Assert.assertEquals(false, actual.getOrderStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void selectThirdPayCallBackQueryPayOrder() throws Exception {
    	// 插入一条未支付的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_UNPAY.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		payOrderDOMapper.insert(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.get("/payOrder/selectThirdPayCallBackQueryPayOrder").param("orderId", expected.getId().toString());
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        ThirdPayCallBackQueryPayOrderDTO actual = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(), ThirdPayCallBackQueryPayOrderDTO.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualMoney(), 0.01D);
        Assert.assertEquals(expected.getMerchantNum(), actual.getBusinessUserNum());
        Assert.assertEquals(expected.getOrderNum(), actual.getOrderNum());
        Assert.assertEquals(expected.getStatus(), actual.getPayOrderStatusEnum().getVal());
    }
    
    @Transactional
    @Rollback
    @Test
    public void selectNotCommissionOrder() throws Exception {
    	// 插入一条已经支付成功并且未评论，未计算提成的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		payOrderDOMapper.insert(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.get("/payOrder/selectNotCommissionOrder");
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        List<ShoppingOrderCommissionDTO> shoppingOrderCommissionDTOList = JSONObject.parseArray(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), ShoppingOrderCommissionDTO.class);
        Assert.assertNotNull(shoppingOrderCommissionDTOList);
        Assert.assertEquals(1, shoppingOrderCommissionDTOList.size());
        ShoppingOrderCommissionDTO actual = shoppingOrderCommissionDTOList.get(0);
    	Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualAmount().doubleValue(), 0D);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getMemberNum(), actual.getMemberNum());
        Assert.assertEquals(expected.getMerchantNum(), actual.getMerchantNum());
    }
    
    @Transactional
    @Rollback
    @Test
    public void updatePayOrderCommissionStatus() throws Exception {
    	// 插入一条已经支付成功并且未评论，未计算提成的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		expected.setCommissionStatus((CommissionStatusEnum.NOT_COUNTED.getValue()));
		payOrderDOMapper.insert(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.put("/payOrder/updatePayOrderCommissionStatus").param("ids", expected.getId().toString());
        ResultActions perform = mvc.perform(request);
        perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_CREATED))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        PayOrderDO actual = payOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getGmtCommission());
        Assert.assertEquals(CommissionStatusEnum.CALCULATED.getValue(), actual.getCommissionStatus());
    }
    
    @SuppressWarnings("unchecked")
	@Transactional
    @Rollback
    @Test
    public void getMerchantPayOrderList() throws Exception {
    	// 插入一条已经支付成功并且未评论，未计算提成的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		expected.setCommissionStatus((CommissionStatusEnum.NOT_COUNTED.getValue()));
		payOrderDOMapper.insert(expected);
    	
		MerchantPayOrderListParam param = new MerchantPayOrderListParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		String content = JSONObject.toJSONString(param);
		
    	RequestBuilder request = MockMvcRequestBuilders.post("/payOrder/getMerchantPayOrderList").param("userId", expected.getMerchantId().toString()).contentType(MediaType.APPLICATION_JSON_UTF8).content(content);
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
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
		MerchantPayOrderListDTO actual = JSONObject.parseObject(page.getRecords().get(0).toJSONString(), MerchantPayOrderListDTO.class, mapping, JSON.DEFAULT_PARSER_FEATURE);
		
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualAmount().doubleValue(), 0D);
		Assert.assertEquals(expected.getGmtCreate().getTime(), actual.getGmtCreate().getTime(), 24*60*1000);
		Assert.assertEquals(expected.getOrderNum(), actual.getOrderNum());
    }
    
	@Transactional
    @Rollback
    @Test
    public void getOrderInfo() throws Exception {
    	// 插入一条已经支付成功并且未评论，未计算提成的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		expected.setCommissionStatus((CommissionStatusEnum.NOT_COUNTED.getValue()));
		payOrderDOMapper.insert(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.get("/payOrder/getOrderInfo").param("id", expected.getId().toString()).param("memberId", expected.getMemberId().toString());
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        MemberPayOrderInfoDTO actual = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), MemberPayOrderInfoDTO.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualAmount().doubleValue(), 0D);
        Assert.assertEquals(expected.getMerchantId(), actual.getMerchantId());
        Assert.assertEquals(expected.getIsEvaluation(), actual.getEvaluationEnum().getVal());
        Assert.assertEquals(expected.getFavoredAmount().doubleValue(), actual.getFavoredAmount().doubleValue(), 0D);
        Assert.assertEquals(expected.getOrderNum(), actual.getOrderNum());
        Assert.assertEquals(expected.getTotalAmount().doubleValue(), actual.getTotalAmount().doubleValue(), 0D);
    }
	
	@SuppressWarnings("unchecked")
	@Transactional
    @Rollback
    @Test
    public void getOperatorPayOrderList() throws Exception {
    	// 插入一条已经支付成功并且未评论，未计算提成的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		expected.setCommissionStatus((CommissionStatusEnum.NOT_COUNTED.getValue()));
		payOrderDOMapper.insert(expected);
    	
		OperatorPayOrderParam param = new OperatorPayOrderParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		String content = JSONObject.toJSONString(param);
		
    	RequestBuilder request = MockMvcRequestBuilders.post("/payOrder/getOperatorPayOrderList").contentType(MediaType.APPLICATION_JSON_UTF8).content(content);
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        Page<JSONObject> page = JSONObject.parseObject(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), Page.class);
        Assert.assertNotNull(page);
        Assert.assertEquals(param.getCurrentPage(), page.getCurrentPage());
    	Assert.assertEquals(1, page.getTotalCount().intValue());
        
    	OperatorPayOrderListDTO actual = JSONObject.toJavaObject(page.getRecords().get(0), OperatorPayOrderListDTO.class);
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualAmount().doubleValue(), 0D);
		Assert.assertEquals(expected.getGmtCreate().getTime(), actual.getGmtCreate().getTime(), 24*60*60*1000);
		Assert.assertEquals(expected.getOrderNum(), actual.getOrderNum());
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getMemberId(), actual.getMemberId());
		Assert.assertEquals(expected.getMerchantId(), actual.getMerchantId());
    }
	
	/**
	 * TODO SQL语法不支持
	 * @throws Exception
	 * @author jiangxinjun
	 * @date 2017年8月30日
	 */
	@Ignore
	@Transactional
    @Rollback
    @Test
    public void getAutoCommentPayOrderList() throws Exception {
    	// 插入一条已经支付成功并且未评论，未计算提成的买单记录
		PayOrderDO expected = new PayOrderDO();
		expected.setActualAmount(new BigDecimal(1));
		expected.setFavoredAmount(new BigDecimal(1));
		expected.setGmtCreate(new Date());
		expected.setGmtModified(new Date());
		expected.setIsEvaluation(false);
		expected.setMemberId(1L);
		expected.setMemberNum("M00001");
		expected.setMerchantId(1L);
		expected.setMerchantNum("B00001");
		expected.setNotFavoredAmount(new BigDecimal(1));
		expected.setOrderNum(StringUtil.getRandomNum(""));
		expected.setOrderStatus(true);
		expected.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		expected.setCommissionStatus((CommissionStatusEnum.NOT_COUNTED.getValue()));
		payOrderDOMapper.insert(expected);
    	
    	RequestBuilder request = MockMvcRequestBuilders.get("/payOrder/getAutoCommentPayOrderList");
        ResultActions perform = mvc.perform(request);
        MvcResult mvcResult = perform.andExpect(MockMvcResultMatchers.status().is(HttpCode.SC_OK))
        		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.ret").value(1000))
        		.andDo(MockMvcResultHandlers.print())
        		.andReturn();
        
        List<PayOrderAutoCommentDTO> payOrderAutoCommentDTOList = JSONObject.parseArray(JSONObject.parseObject(mvcResult.getResponse().getContentAsString()).getString("model"), PayOrderAutoCommentDTO.class);
        Assert.assertNotNull(payOrderAutoCommentDTOList);
    	Assert.assertEquals(1, payOrderAutoCommentDTOList.size());
        
    	PayOrderAutoCommentDTO actual = payOrderAutoCommentDTOList.get(0);
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getActualAmount().doubleValue(), actual.getActualAmount().doubleValue(), 0D);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getMemberId(), actual.getMemberId());
		Assert.assertEquals(expected.getMerchantId(), actual.getMerchantId());
    }
}

class JCDateDeserializer extends DateDeserializer {
	
    public static final JCDateDeserializer instance = new JCDateDeserializer();

    public JCDateDeserializer() {}

    protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val){
        if (val == null) {
            return null;
        } else if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            } else {
            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            	parser.setDateFomrat(dateFormat);
            }
        }
        return super.cast(parser, clazz, fieldName, val);
    }
}
