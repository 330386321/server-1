package com.lawu.eshop.order.srv.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.idworker.client.impl.BizIdType;
import com.lawu.eshop.idworker.client.impl.IdWorkerHelperImpl;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.param.ShoppingRefundDetailLogisticsInformationParam;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundProcessBO;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverterTest;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverterTest;
import com.lawu.eshop.order.srv.converter.ShoppingRefundDetailConverterTest;
import com.lawu.eshop.order.srv.converter.ShoppingRefundProcessConverterTest;
import com.lawu.eshop.order.srv.domain.PropertyDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDOExample;
import com.lawu.eshop.order.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundProcessDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;
import com.lawu.eshop.utils.DateUtil;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class ShoppingRefundDetailServiceImplTest {
	
    @Autowired
    private ShoppingRefundDetailService shoppingRefundDetailService;
    
	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
	
	@Autowired
	private ShoppingRefundProcessDOMapper shoppingRefundProcessDOMapper;
	
	@Autowired
	private PropertyDOMapper propertyDOMapper;
	
    @SuppressWarnings("deprecation")
	@Transactional
    @Rollback
    @Test
    public void agreeToApply() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
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
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingRefundDetailAgreeToApplyForeignParam param = new ShoppingRefundDetailAgreeToApplyForeignParam();
    	param.setIsAgree(false);
    	param.setRefusalReasons("商品已经损坏");
    	shoppingRefundDetailService.agreeToApply(shoppingRefundDetailDO.getId(), expected.getMerchantId(), param);
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(RefundStatusEnum.REFUND_FAILED.getValue(), actual.getRefundStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtConfirmed());
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(param.getIsAgree(), actualShoppingRefundDetailDO.getIsAgree());
    	Assert.assertEquals(param.getRefusalReasons(), actualShoppingRefundDetailDO.getRefusalReasons());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_FAILED.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void agreeToRefund() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
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
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingRefundDetailAgreeToRefundForeignParam param = new ShoppingRefundDetailAgreeToRefundForeignParam();
    	param.setIsAgree(false);
    	param.setRefusalReasons("商品已经损坏");
    	shoppingRefundDetailService.agreeToRefund(shoppingRefundDetailDO.getId(), expected.getMerchantId(), param, false);
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(RefundStatusEnum.REFUND_FAILED.getValue(), actual.getRefundStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(param.getIsAgree(), actualShoppingRefundDetailDO.getIsAgree());
    	Assert.assertEquals(param.getRefusalReasons(), actualShoppingRefundDetailDO.getRefusalReasons());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_FAILED.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void agreeToRefundById() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
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
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	shoppingRefundDetailService.agreeToRefund(shoppingRefundDetailDO.getId());
    	
    	ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(shoppingOrderDO);
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), shoppingOrderDO.getOrderStatus());
    	Assert.assertEquals(0D, shoppingOrderDO.getActualAmount().doubleValue(), 0D);
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), actual.getRefundStatus());
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actual.getOrderStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(true, actualShoppingRefundDetailDO.getIsAgree());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void executeAutoForFillReturnAddress() {
    	PropertyDO remindPropertyDO = new PropertyDO();
    	remindPropertyDO.setGmtCreate(new Date());
    	remindPropertyDO.setGmtModified(new Date());
    	remindPropertyDO.setName(PropertyNameConstant.FILL_RETURN_ADDRESS_REMIND_TIME);
    	remindPropertyDO.setRemark("填写退货地址超时,提醒时间");
    	remindPropertyDO.setValue("5");
    	propertyDOMapper.insert(remindPropertyDO);
    	
    	PropertyDO refundPropertyDO = new PropertyDO();
    	refundPropertyDO.setGmtCreate(new Date());
    	refundPropertyDO.setGmtModified(new Date());
    	refundPropertyDO.setName(PropertyNameConstant.FILL_RETURN_ADDRESS_REFUND_TIME);
    	refundPropertyDO.setRemark("填写退货地址超时,自动退款时间");
    	refundPropertyDO.setValue("7");
    	propertyDOMapper.insert(refundPropertyDO);
    	
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
        /*
         * 插入一条未发提醒消息，但是已经超过提醒时间的退款记录
         */
        ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
        shoppingOrderItemDO.setGmtCreate(new Date());
        shoppingOrderItemDO.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(remindPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
        shoppingOrderItemDO.setIsAllowRefund(true);
        shoppingOrderItemDO.setIsEvaluation(false);
        shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
        shoppingOrderItemDO.setProductFeatureImage("test.jpg");
        shoppingOrderItemDO.setProductId(1L);
        shoppingOrderItemDO.setProductName("productName");
        shoppingOrderItemDO.setProductModelId(1L);
        shoppingOrderItemDO.setProductModelName("test");
        shoppingOrderItemDO.setQuantity(1);
        shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
        shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
        // 设置为已经发送过提醒消息了
        shoppingOrderItemDO.setSendTime(0);
        shoppingOrderItemDO.setShoppingOrderId(expected.getId());
        shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
        shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
        
        ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
        shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
        shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
        shoppingRefundDetailDO.setDescription("就是想退款");
        shoppingRefundDetailDO.setGmtModified(new Date());
        shoppingRefundDetailDO.setGmtCreate(new Date());
        shoppingRefundDetailDO.setReason("七天无理由退货");
        shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
        shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
        shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
        ShoppingOrderDO expected2 = new ShoppingOrderDO();
        expected2.setCommodityTotalPrice(new BigDecimal(1));
        expected2.setActualAmount(new BigDecimal(1));
        expected2.setFreightPrice(new BigDecimal(0));
        expected2.setGmtCreate(new Date());
        expected2.setGmtModified(new Date());
        expected2.setGmtTransport(new Date());
        expected2.setIsFans(true);
        expected2.setIsNeedsLogistics(true);
        expected2.setIsNoReasonReturn(true);
        expected2.setMemberId(1L);
        expected2.setMemberNum("M0001");
        expected2.setMerchantId(1L);
        expected2.setMerchantName("拉乌网络");
        expected2.setMerchantStoreId(1L);
        expected2.setMerchantNum("B0001");
        expected2.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
        expected2.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
        expected2.setOrderTotalPrice(new BigDecimal(1));
        expected2.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
        expected2.setStatus(StatusEnum.VALID.getValue());
        expected2.setConsigneeAddress("大冲商务中心1301");
        expected2.setConsigneeMobile("123456");
        expected2.setConsigneeName("Sunny");
        expected2.setIsDone(false);
        expected2.setShoppingCartIdsStr("1");
        expected2.setSendTime(0);
        expected2.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
        shoppingOrderDOMapper.insertSelective(expected2);
        
    	/*
    	 * 插入一条已经发过提醒消息，而且已经到退款超时时间退款记录
    	 */
    	ShoppingOrderItemDO shoppingOrderItemDO2 = new ShoppingOrderItemDO();
    	shoppingOrderItemDO2.setGmtCreate(new Date());
    	shoppingOrderItemDO2.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(refundPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
    	shoppingOrderItemDO2.setIsAllowRefund(true);
    	shoppingOrderItemDO2.setIsEvaluation(false);
    	shoppingOrderItemDO2.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO2.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO2.setProductId(1L);
    	shoppingOrderItemDO2.setProductName("productName");
    	shoppingOrderItemDO2.setProductModelId(1L);
    	shoppingOrderItemDO2.setProductModelName("test");
    	shoppingOrderItemDO2.setQuantity(1);
    	shoppingOrderItemDO2.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO2.setSalesPrice(new BigDecimal(1));
    	// 设置为已经发送过提醒消息了
    	shoppingOrderItemDO2.setSendTime(1);
    	shoppingOrderItemDO2.setShoppingOrderId(expected2.getId());
    	shoppingOrderItemDO2.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO2);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO2 = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO2.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO2.setAmount(shoppingOrderItemDO2.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO2.getQuantity())));
    	shoppingRefundDetailDO2.setDescription("就是想退款");
    	shoppingRefundDetailDO2.setGmtModified(new Date());
    	shoppingRefundDetailDO2.setGmtCreate(new Date());
    	shoppingRefundDetailDO2.setReason("七天无理由退货");
    	shoppingRefundDetailDO2.setShoppingOrderItemId(shoppingOrderItemDO2.getId());
    	shoppingRefundDetailDO2.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO2);
    	
    	shoppingRefundDetailService.executeAutoForFillReturnAddress();
    	
    	/*
    	 * 订单和订单项订单状态不变，但是提醒次数加1
    	 */
        ShoppingOrderDO actualShoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actualShoppingOrderDO);
        Assert.assertEquals(expected.getOrderStatus(), actualShoppingOrderDO.getOrderStatus());
        
        ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
        Assert.assertNotNull(actual);
        Assert.assertEquals(shoppingOrderItemDO.getRefundStatus(), actual.getRefundStatus());
        Assert.assertEquals(shoppingOrderItemDO.getOrderStatus(), actual.getOrderStatus());
        Assert.assertEquals(shoppingOrderItemDO.getSendTime() + 1, actual.getSendTime().intValue());
    	
        /*
         * 订单项和订单状态改为取消交易，真实金额为0，退款状态为退款成功
         * 新增一条退款成功的退款流程记录
         */
    	ShoppingOrderDO actualShoppingOrderDO2 = shoppingOrderDOMapper.selectByPrimaryKey(expected2.getId());
    	Assert.assertNotNull(actualShoppingOrderDO2);
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actualShoppingOrderDO2.getOrderStatus());
    	Assert.assertEquals(0D, actualShoppingOrderDO2.getActualAmount().doubleValue(), 0D);
    	
    	ShoppingOrderItemDO actual2 = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO2.getId());
    	Assert.assertNotNull(actual2);
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), actual2.getRefundStatus());
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actual2.getOrderStatus());
    	Assert.assertEquals(0, actual2.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO2.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(true, actualShoppingRefundDetailDO.getIsAgree());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO2.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO2.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void executeAutoForToBeRefund() {
    	PropertyDO refundedFirstRemindPropertyDO = new PropertyDO();
    	refundedFirstRemindPropertyDO.setGmtCreate(new Date());
    	refundedFirstRemindPropertyDO.setGmtModified(new Date());
    	refundedFirstRemindPropertyDO.setName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_FIRST_TIME);
    	refundedFirstRemindPropertyDO.setRemark("等待商家退款超时(待退款状态),第一次提醒时间");
    	refundedFirstRemindPropertyDO.setValue("10");
    	propertyDOMapper.insert(refundedFirstRemindPropertyDO);
    	
    	PropertyDO refundedSecondRemindPropertyDO = new PropertyDO();
    	refundedSecondRemindPropertyDO.setGmtCreate(new Date());
    	refundedSecondRemindPropertyDO.setGmtModified(new Date());
    	refundedSecondRemindPropertyDO.setName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_SECOND_TIME);
    	refundedSecondRemindPropertyDO.setRemark("等待商家退款超时(待退款状态),第二次提醒时间");
    	refundedSecondRemindPropertyDO.setValue("10");
    	propertyDOMapper.insert(refundedSecondRemindPropertyDO);
    	
    	PropertyDO refundedRefundPropertyDO = new PropertyDO();
    	refundedRefundPropertyDO.setGmtCreate(new Date());
    	refundedRefundPropertyDO.setGmtModified(new Date());
    	refundedRefundPropertyDO.setName(PropertyNameConstant.TO_BE_REFUNDED_REFUND_TIME);
    	refundedRefundPropertyDO.setRemark("等待商家退款超时(待退款状态),平台自动退款退款");
    	refundedRefundPropertyDO.setValue("14");
    	propertyDOMapper.insert(refundedRefundPropertyDO);
    	
    	PropertyDO confirmedRemindPropertyDO = new PropertyDO();
    	confirmedRemindPropertyDO.setGmtCreate(new Date());
    	confirmedRemindPropertyDO.setGmtModified(new Date());
    	confirmedRemindPropertyDO.setName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REMIND_TIME);
    	confirmedRemindPropertyDO.setRemark("等待商家退款超时(待确认状态),提醒时间");
    	confirmedRemindPropertyDO.setValue("2");
    	propertyDOMapper.insert(confirmedRemindPropertyDO);
    	
    	PropertyDO confirmedRefundPropertyDO = new PropertyDO();
    	confirmedRefundPropertyDO.setGmtCreate(new Date());
    	confirmedRefundPropertyDO.setGmtModified(new Date());
    	confirmedRefundPropertyDO.setName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
    	confirmedRefundPropertyDO.setRemark("等待商家退款超时(待确认状态),平台自动退款退款");
    	confirmedRefundPropertyDO.setValue("3");
    	propertyDOMapper.insert(confirmedRefundPropertyDO);
    	
    	/*
    	 * 生成一个订单，有一个订单项，正处于待退款状态，已经发过一次提醒消息，已经到第二次提醒时间，但是还未到超时退款时间
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
        expected.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
        expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
        expected.setOrderTotalPrice(new BigDecimal(1));
        expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
        expected.setStatus(StatusEnum.VALID.getValue());
        expected.setConsigneeAddress("大冲商务中心1301");
        expected.setConsigneeMobile("123456");
        expected.setConsigneeName("Sunny");
        expected.setIsDone(false);
        expected.setShoppingCartIdsStr("1");
        expected.setSendTime(0);
        expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
        shoppingOrderDOMapper.insertSelective(expected);
        
        ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
        shoppingOrderItemDO.setGmtCreate(new Date());
        shoppingOrderItemDO.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(refundedSecondRemindPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
        shoppingOrderItemDO.setIsAllowRefund(true);
        shoppingOrderItemDO.setIsEvaluation(false);
        shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
        shoppingOrderItemDO.setProductFeatureImage("test.jpg");
        shoppingOrderItemDO.setProductId(1L);
        shoppingOrderItemDO.setProductName("productName");
        shoppingOrderItemDO.setProductModelId(1L);
        shoppingOrderItemDO.setProductModelName("test");
        shoppingOrderItemDO.setQuantity(1);
        shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
        shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
        // 已经发送1次提醒消息
        shoppingOrderItemDO.setSendTime(1);
        shoppingOrderItemDO.setShoppingOrderId(expected.getId());
        shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
        shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
        
        ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
        shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
        shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
        shoppingRefundDetailDO.setDescription("就是想退款");
        shoppingRefundDetailDO.setGmtModified(new Date());
        shoppingRefundDetailDO.setGmtCreate(new Date());
        shoppingRefundDetailDO.setReason("七天无理由退货");
        shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
        shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
        shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingOrderDO expected2 = new ShoppingOrderDO();
    	expected2.setCommodityTotalPrice(new BigDecimal(1));
    	expected2.setActualAmount(new BigDecimal(1));
    	expected2.setFreightPrice(new BigDecimal(0));
    	expected2.setGmtCreate(new Date());
    	expected2.setGmtModified(new Date());
    	expected2.setGmtTransport(new Date());
    	expected2.setIsFans(true);
    	expected2.setIsNeedsLogistics(true);
    	expected2.setIsNoReasonReturn(true);
    	expected2.setMemberId(1L);
    	expected2.setMemberNum("M0001");
    	expected2.setMerchantId(1L);
    	expected2.setMerchantName("拉乌网络");
    	expected2.setMerchantStoreId(1L);
    	expected2.setMerchantNum("B0001");
    	expected2.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
    	expected2.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected2.setOrderTotalPrice(new BigDecimal(1));
    	expected2.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected2.setStatus(StatusEnum.VALID.getValue());
    	expected2.setConsigneeAddress("大冲商务中心1301");
    	expected2.setConsigneeMobile("123456");
    	expected2.setConsigneeName("Sunny");
    	expected2.setIsDone(false);
    	expected2.setShoppingCartIdsStr("1");
    	expected2.setSendTime(0);
    	expected2.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected2);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO2 = new ShoppingOrderItemDO();
    	shoppingOrderItemDO2.setGmtCreate(new Date());
    	shoppingOrderItemDO2.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(refundedRefundPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
    	shoppingOrderItemDO2.setIsAllowRefund(true);
    	shoppingOrderItemDO2.setIsEvaluation(false);
    	shoppingOrderItemDO2.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO2.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO2.setProductId(1L);
    	shoppingOrderItemDO2.setProductName("productName");
    	shoppingOrderItemDO2.setProductModelId(1L);
    	shoppingOrderItemDO2.setProductModelName("test");
    	shoppingOrderItemDO2.setQuantity(1);
    	shoppingOrderItemDO2.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO2.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO2.setSendTime(2);
    	shoppingOrderItemDO2.setShoppingOrderId(expected2.getId());
    	shoppingOrderItemDO2.setRefundStatus(RefundStatusEnum.TO_BE_REFUNDED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO2);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO2 = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO2.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO2.setAmount(shoppingOrderItemDO2.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO2.getQuantity())));
    	shoppingRefundDetailDO2.setDescription("就是想退款");
    	shoppingRefundDetailDO2.setGmtModified(new Date());
    	shoppingRefundDetailDO2.setGmtCreate(new Date());
    	shoppingRefundDetailDO2.setReason("七天无理由退货");
    	shoppingRefundDetailDO2.setShoppingOrderItemId(shoppingOrderItemDO2.getId());
    	shoppingRefundDetailDO2.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO2);
    	
    	shoppingRefundDetailService.executeAutoForToBeRefund();
    	
    	/*
    	 * 订单和订单项订单状态不变，订单项发送提醒次数加1
    	 */
    	ShoppingOrderDO actualShoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actualShoppingOrderDO);
        Assert.assertEquals(expected.getOrderStatus(), actualShoppingOrderDO.getOrderStatus());
        
        ShoppingOrderItemDO actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
        Assert.assertNotNull(actualShoppingOrderItemDO);
        Assert.assertEquals(shoppingOrderItemDO.getRefundStatus(), actualShoppingOrderItemDO.getRefundStatus());
        Assert.assertEquals(shoppingOrderItemDO.getOrderStatus(), actualShoppingOrderItemDO.getOrderStatus());
        Assert.assertEquals(shoppingOrderItemDO.getSendTime() + 1, actualShoppingOrderItemDO.getSendTime().intValue());
    	
        /*
         * 订单和订单项的订单变为交易取消，退款金额为0，订单项的退款状态为退款成功，新增一条退款成功的退款流程记录
         */
    	ShoppingOrderDO actualShoppingOrderDO2 = shoppingOrderDOMapper.selectByPrimaryKey(expected2.getId());
    	Assert.assertNotNull(actualShoppingOrderDO2);
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actualShoppingOrderDO2.getOrderStatus());
    	Assert.assertEquals(0D, actualShoppingOrderDO2.getActualAmount().doubleValue(), 0D);
    	
    	ShoppingOrderItemDO actualShoppingOrderItemDO2 = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO2.getId());
    	Assert.assertNotNull(actualShoppingOrderItemDO2);
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), actualShoppingOrderItemDO2.getRefundStatus());
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actualShoppingOrderItemDO2.getOrderStatus());
    	Assert.assertEquals(0, actualShoppingOrderItemDO2.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO2.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(true, actualShoppingRefundDetailDO.getIsAgree());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO2.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO2.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void executeAutoForToBeReturn() {
    	PropertyDO remindPropertyDO = new PropertyDO();
    	remindPropertyDO.setGmtCreate(new Date());
    	remindPropertyDO.setGmtModified(new Date());
    	remindPropertyDO.setName(PropertyNameConstant.TO_BE_RETURNED_REMIND_TIME);
    	remindPropertyDO.setRemark("等待买家退货超时,提醒时间");
    	remindPropertyDO.setValue("5");
    	propertyDOMapper.insert(remindPropertyDO);
    	
    	PropertyDO revokePropertyDO = new PropertyDO();
    	revokePropertyDO.setGmtCreate(new Date());
    	revokePropertyDO.setGmtModified(new Date());
    	revokePropertyDO.setName(PropertyNameConstant.TO_BE_RETURNED_REVOKE_TIME);
    	revokePropertyDO.setRemark("等待买家退货超时,撤销退款申请时间");
    	revokePropertyDO.setValue("7");
    	propertyDOMapper.insert(revokePropertyDO);
    	
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
        expected.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
        expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
        expected.setOrderTotalPrice(new BigDecimal(1));
        expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
        expected.setStatus(StatusEnum.VALID.getValue());
        expected.setConsigneeAddress("大冲商务中心1301");
        expected.setConsigneeMobile("123456");
        expected.setConsigneeName("Sunny");
        expected.setIsDone(false);
        expected.setShoppingCartIdsStr("1");
        expected.setSendTime(0);
        expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
        shoppingOrderDOMapper.insertSelective(expected);
        
        ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
        shoppingOrderItemDO.setGmtCreate(new Date());
        shoppingOrderItemDO.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(remindPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
        shoppingOrderItemDO.setIsAllowRefund(true);
        shoppingOrderItemDO.setIsEvaluation(false);
        shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
        shoppingOrderItemDO.setProductFeatureImage("test.jpg");
        shoppingOrderItemDO.setProductId(1L);
        shoppingOrderItemDO.setProductName("productName");
        shoppingOrderItemDO.setProductModelId(1L);
        shoppingOrderItemDO.setProductModelName("test");
        shoppingOrderItemDO.setQuantity(1);
        shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
        shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
        // 未发送提醒消息
        shoppingOrderItemDO.setSendTime(0);
        shoppingOrderItemDO.setShoppingOrderId(expected.getId());
        shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_RETURNED.getValue());
        shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
        
        ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
        shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
        shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
        shoppingRefundDetailDO.setDescription("就是想退款");
        shoppingRefundDetailDO.setGmtModified(new Date());
        shoppingRefundDetailDO.setGmtCreate(new Date());
        shoppingRefundDetailDO.setReason("七天无理由退货");
        shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
        shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
        shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingOrderDO expected2 = new ShoppingOrderDO();
    	expected2.setCommodityTotalPrice(new BigDecimal(1));
    	expected2.setActualAmount(new BigDecimal(1));
    	expected2.setFreightPrice(new BigDecimal(0));
    	expected2.setGmtCreate(new Date());
    	expected2.setGmtModified(new Date());
    	expected2.setGmtTransport(new Date());
    	expected2.setIsFans(true);
    	expected2.setIsNeedsLogistics(true);
    	expected2.setIsNoReasonReturn(true);
    	expected2.setMemberId(1L);
    	expected2.setMemberNum("M0001");
    	expected2.setMerchantId(1L);
    	expected2.setMerchantName("拉乌网络");
    	expected2.setMerchantStoreId(1L);
    	expected2.setMerchantNum("B0001");
    	expected2.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
    	expected2.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected2.setOrderTotalPrice(new BigDecimal(1));
    	expected2.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected2.setStatus(StatusEnum.VALID.getValue());
    	expected2.setConsigneeAddress("大冲商务中心1301");
    	expected2.setConsigneeMobile("123456");
    	expected2.setConsigneeName("Sunny");
    	expected2.setIsDone(false);
    	expected2.setShoppingCartIdsStr("1");
    	expected2.setSendTime(0);
    	expected2.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected2);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO2 = new ShoppingOrderItemDO();
    	shoppingOrderItemDO2.setGmtCreate(new Date());
    	shoppingOrderItemDO2.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(revokePropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
    	shoppingOrderItemDO2.setIsAllowRefund(true);
    	shoppingOrderItemDO2.setIsEvaluation(false);
    	shoppingOrderItemDO2.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO2.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO2.setProductId(1L);
    	shoppingOrderItemDO2.setProductName("productName");
    	shoppingOrderItemDO2.setProductModelId(1L);
    	shoppingOrderItemDO2.setProductModelName("test");
    	shoppingOrderItemDO2.setQuantity(1);
    	shoppingOrderItemDO2.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO2.setSalesPrice(new BigDecimal(1));
    	// 已经发送提醒消息
    	shoppingOrderItemDO2.setSendTime(1);
    	shoppingOrderItemDO2.setShoppingOrderId(expected2.getId());
    	shoppingOrderItemDO2.setRefundStatus(RefundStatusEnum.TO_BE_RETURNED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO2);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO2 = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO2.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO2.setAmount(shoppingOrderItemDO2.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO2.getQuantity())));
    	shoppingRefundDetailDO2.setDescription("就是想退款");
    	shoppingRefundDetailDO2.setGmtModified(new Date());
    	shoppingRefundDetailDO2.setGmtCreate(new Date());
    	shoppingRefundDetailDO2.setReason("七天无理由退货");
    	shoppingRefundDetailDO2.setShoppingOrderItemId(shoppingOrderItemDO2.getId());
    	shoppingRefundDetailDO2.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO2);
    	
    	shoppingRefundDetailService.executeAutoForToBeReturn();
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
        Assert.assertNotNull(actual);
        Assert.assertEquals(shoppingOrderItemDO.getOrderStatus(), actual.getOrderStatus());
        Assert.assertEquals(shoppingOrderItemDO.getSendTime() + 1, actual.getSendTime().intValue());
        
    	ShoppingOrderItemDO actual2 = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO2.getId());
    	Assert.assertNotNull(actual2);
    	Assert.assertNull(actual2.getRefundStatus());
    	Assert.assertEquals(expected2.getOrderStatus(), actual2.getOrderStatus());
    	Assert.assertEquals(0, actual2.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO2.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertEquals(StatusEnum.INVALID.getValue(), actualShoppingRefundDetailDO.getStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void executeAutoRefundFailed() {
    	PropertyDO remindPropertyDO = new PropertyDO();
    	remindPropertyDO.setGmtCreate(new Date());
    	remindPropertyDO.setGmtModified(new Date());
    	remindPropertyDO.setName(PropertyNameConstant.REFUND_FAILED_REMIND_TIME);
    	remindPropertyDO.setRemark("等待买家退货超时,提醒时间");
    	remindPropertyDO.setValue("5");
    	propertyDOMapper.insert(remindPropertyDO);
    	
    	PropertyDO revokePropertyDO = new PropertyDO();
    	revokePropertyDO.setGmtCreate(new Date());
    	revokePropertyDO.setGmtModified(new Date());
    	revokePropertyDO.setName(PropertyNameConstant.REFUND_FAILED_REVOKE_TIME);
    	revokePropertyDO.setRemark("等待买家退货超时,撤销退款申请时间");
    	revokePropertyDO.setValue("7");
    	propertyDOMapper.insert(revokePropertyDO);
    	
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
        expected.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
        expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
        expected.setOrderTotalPrice(new BigDecimal(1));
        expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
        expected.setStatus(StatusEnum.VALID.getValue());
        expected.setConsigneeAddress("大冲商务中心1301");
        expected.setConsigneeMobile("123456");
        expected.setConsigneeName("Sunny");
        expected.setIsDone(false);
        expected.setShoppingCartIdsStr("1");
        expected.setSendTime(0);
        expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
        shoppingOrderDOMapper.insertSelective(expected);
        
        ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
        shoppingOrderItemDO.setGmtCreate(new Date());
        shoppingOrderItemDO.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(remindPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
        shoppingOrderItemDO.setIsAllowRefund(true);
        shoppingOrderItemDO.setIsEvaluation(false);
        shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
        shoppingOrderItemDO.setProductFeatureImage("test.jpg");
        shoppingOrderItemDO.setProductId(1L);
        shoppingOrderItemDO.setProductName("productName");
        shoppingOrderItemDO.setProductModelId(1L);
        shoppingOrderItemDO.setProductModelName("test");
        shoppingOrderItemDO.setQuantity(1);
        shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
        shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
        // 未发送提醒消息
        shoppingOrderItemDO.setSendTime(0);
        shoppingOrderItemDO.setShoppingOrderId(expected.getId());
        shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
        shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
        
        ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
        shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
        shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
        shoppingRefundDetailDO.setDescription("就是想退款");
        shoppingRefundDetailDO.setGmtModified(new Date());
        shoppingRefundDetailDO.setGmtCreate(new Date());
        shoppingRefundDetailDO.setReason("七天无理由退货");
        shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
        shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
        shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingOrderDO expected2 = new ShoppingOrderDO();
    	expected2.setCommodityTotalPrice(new BigDecimal(1));
    	expected2.setActualAmount(new BigDecimal(1));
    	expected2.setFreightPrice(new BigDecimal(0));
    	expected2.setGmtCreate(new Date());
    	expected2.setGmtModified(new Date());
    	expected2.setGmtTransport(new Date());
    	expected2.setIsFans(true);
    	expected2.setIsNeedsLogistics(true);
    	expected2.setIsNoReasonReturn(true);
    	expected2.setMemberId(1L);
    	expected2.setMemberNum("M0001");
    	expected2.setMerchantId(1L);
    	expected2.setMerchantName("拉乌网络");
    	expected2.setMerchantStoreId(1L);
    	expected2.setMerchantNum("B0001");
    	expected2.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
    	expected2.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected2.setOrderTotalPrice(new BigDecimal(1));
    	expected2.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected2.setStatus(StatusEnum.VALID.getValue());
    	expected2.setConsigneeAddress("大冲商务中心1301");
    	expected2.setConsigneeMobile("123456");
    	expected2.setConsigneeName("Sunny");
    	expected2.setIsDone(false);
    	expected2.setShoppingCartIdsStr("1");
    	expected2.setSendTime(0);
    	expected2.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected2);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO2 = new ShoppingOrderItemDO();
    	shoppingOrderItemDO2.setGmtCreate(new Date());
    	shoppingOrderItemDO2.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(revokePropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
    	shoppingOrderItemDO2.setIsAllowRefund(true);
    	shoppingOrderItemDO2.setIsEvaluation(false);
    	shoppingOrderItemDO2.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO2.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO2.setProductId(1L);
    	shoppingOrderItemDO2.setProductName("productName");
    	shoppingOrderItemDO2.setProductModelId(1L);
    	shoppingOrderItemDO2.setProductModelName("test");
    	shoppingOrderItemDO2.setQuantity(1);
    	shoppingOrderItemDO2.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO2.setSalesPrice(new BigDecimal(1));
    	// 已经发送提醒消息
    	shoppingOrderItemDO2.setSendTime(1);
    	shoppingOrderItemDO2.setShoppingOrderId(expected2.getId());
    	shoppingOrderItemDO2.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO2);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO2 = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO2.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO2.setAmount(shoppingOrderItemDO2.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO2.getQuantity())));
    	shoppingRefundDetailDO2.setDescription("就是想退款");
    	shoppingRefundDetailDO2.setGmtModified(new Date());
    	shoppingRefundDetailDO2.setGmtCreate(new Date());
    	shoppingRefundDetailDO2.setReason("七天无理由退货");
    	shoppingRefundDetailDO2.setShoppingOrderItemId(shoppingOrderItemDO2.getId());
    	shoppingRefundDetailDO2.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO2);
    	
    	shoppingRefundDetailService.executeAutoRefundFailed();
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
        Assert.assertNotNull(actual);
        Assert.assertEquals(shoppingOrderItemDO.getOrderStatus(), actual.getOrderStatus());
        Assert.assertEquals(shoppingOrderItemDO.getSendTime() + 1, actual.getSendTime().intValue());
        
    	ShoppingOrderItemDO actual2 = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO2.getId());
    	Assert.assertNotNull(actual2);
    	Assert.assertNull(actual2.getRefundStatus());
    	Assert.assertEquals(expected2.getOrderStatus(), actual2.getOrderStatus());
    	Assert.assertEquals(0, actual2.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO2 = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO2.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO2);
    	Assert.assertEquals(StatusEnum.INVALID.getValue(), actualShoppingRefundDetailDO2.getStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void executeAutoToBeConfirmedForRefund() {
    	PropertyDO confirmedRemindPropertyDO = new PropertyDO();
    	confirmedRemindPropertyDO.setGmtCreate(new Date());
    	confirmedRemindPropertyDO.setGmtModified(new Date());
    	confirmedRemindPropertyDO.setName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REMIND_TIME);
    	confirmedRemindPropertyDO.setRemark("等待商家退款超时(待确认状态),提醒时间");
    	confirmedRemindPropertyDO.setValue("2");
    	propertyDOMapper.insert(confirmedRemindPropertyDO);
    	
    	PropertyDO confirmedRefundPropertyDO = new PropertyDO();
    	confirmedRefundPropertyDO.setGmtCreate(new Date());
    	confirmedRefundPropertyDO.setGmtModified(new Date());
    	confirmedRefundPropertyDO.setName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME);
    	confirmedRefundPropertyDO.setRemark("等待商家退款超时(待确认状态),平台自动退款退款");
    	confirmedRefundPropertyDO.setValue("3");
    	propertyDOMapper.insert(confirmedRefundPropertyDO);
    	
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(confirmedRefundPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	shoppingRefundDetailService.executeAutoToBeConfirmedForRefund();
    	
    	ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(shoppingOrderDO);
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), shoppingOrderDO.getOrderStatus());
    	Assert.assertEquals(0D, shoppingOrderDO.getActualAmount().doubleValue(), 0D);
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), actual.getRefundStatus());
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actual.getOrderStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(true, actualShoppingRefundDetailDO.getIsAgree());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void executeAutoToBeConfirmedForReturnRefund() {
    	PropertyDO confirmedRemindPropertyDO = new PropertyDO();
    	confirmedRemindPropertyDO.setGmtCreate(new Date());
    	confirmedRemindPropertyDO.setGmtModified(new Date());
    	confirmedRemindPropertyDO.setName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REMIND_TIME);
    	confirmedRemindPropertyDO.setRemark("等待商家确认(退货退款),提醒时间");
    	confirmedRemindPropertyDO.setValue("5");
    	propertyDOMapper.insert(confirmedRemindPropertyDO);
    	
    	PropertyDO confirmedRefundPropertyDO = new PropertyDO();
    	confirmedRefundPropertyDO.setGmtCreate(new Date());
    	confirmedRefundPropertyDO.setGmtModified(new Date());
    	confirmedRefundPropertyDO.setName(PropertyNameConstant.TO_BE_CONFIRMED_FOR_RETURN_REFUND_REFUND_TIME);
    	confirmedRefundPropertyDO.setRemark("等待商家确认(退货退款),平台自动退款退款");
    	confirmedRefundPropertyDO.setValue("7");
    	propertyDOMapper.insert(confirmedRefundPropertyDO);
    	
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(DateUtil.add(new Date(), Integer.valueOf(confirmedRefundPropertyDO.getValue()) * -1, Calendar.DAY_OF_YEAR));
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	shoppingRefundDetailService.executeAutoToBeConfirmedForReturnRefund();
    	
    	ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(shoppingOrderDO);
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), shoppingOrderDO.getOrderStatus());
    	Assert.assertEquals(0D, shoppingOrderDO.getActualAmount().doubleValue(), 0D);
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), actual.getRefundStatus());
    	Assert.assertEquals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue(), actual.getOrderStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertNotNull(actualShoppingRefundDetailDO.getGmtRefund());
    	Assert.assertEquals(true, actualShoppingRefundDetailDO.getIsAgree());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.REFUND_SUCCESSFULLY.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void get() {
    	ShoppingRefundDetailDO expected = new ShoppingRefundDetailDO();
    	expected.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	expected.setAmount(new BigDecimal(1));
    	expected.setDescription("就是想退款");
    	expected.setGmtModified(new Date());
    	expected.setGmtCreate(new Date());
    	expected.setReason("七天无理由退货");
    	expected.setShoppingOrderItemId(1L);
    	expected.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(expected);
    	ShoppingRefundDetailBO actual = shoppingRefundDetailService.get(expected.getId());
    	ShoppingRefundDetailConverterTest.assertShoppingRefundDetailBO(expected, actual);
    }
    
    @Transactional
    @Rollback
    @Test
    public void fillLogisticsInformation() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.TO_BE_RETURNED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingRefundDetailLogisticsInformationParam param = new ShoppingRefundDetailLogisticsInformationParam();
    	param.setExpressCompanyCode("SF");
    	param.setExpressCompanyId(1);
    	param.setExpressCompanyName("顺丰");
    	param.setWaybillNum("123456789");
    	shoppingRefundDetailService.fillLogisticsInformation(shoppingRefundDetailDO.getId(), expected.getMemberId(), param);
    	
    	ShoppingOrderItemDO actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actualShoppingOrderItemDO);
    	Assert.assertEquals(RefundStatusEnum.TO_BE_REFUNDED.getValue(), actualShoppingOrderItemDO.getRefundStatus());
    	Assert.assertEquals(0, actualShoppingOrderItemDO.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actual = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertNotNull(actual.getGmtSubmit());
    	Assert.assertEquals(param.getExpressCompanyCode(), actual.getExpressCompanyCode());
    	Assert.assertEquals(param.getExpressCompanyId(), actual.getExpressCompanyId());
    	Assert.assertEquals(param.getExpressCompanyName(), actual.getExpressCompanyName());
    	Assert.assertEquals(param.getWaybillNum(), actual.getWaybillNum());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(actualShoppingOrderItemDO.getRefundStatus(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void fillReturnAddress() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingRefundDetailRerurnAddressParam param = new ShoppingRefundDetailRerurnAddressParam();
    	param.setConsigneeAddress("大冲商务中心");
    	param.setConsigneeMobile("123456");
    	param.setConsigneeName("Sunny");
    	shoppingRefundDetailService.fillReturnAddress(shoppingRefundDetailDO.getId(), expected.getMerchantId(), param);
    	
    	ShoppingOrderItemDO actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actualShoppingOrderItemDO);
    	Assert.assertEquals(RefundStatusEnum.TO_BE_RETURNED.getValue(), actualShoppingOrderItemDO.getRefundStatus());
    	Assert.assertEquals(0, actualShoppingOrderItemDO.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actual = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertNotNull(actual.getGmtFill());
    	Assert.assertEquals(param.getConsigneeAddress(), actual.getConsigneeAddress());
    	Assert.assertEquals(param.getConsigneeMobile(), actual.getConsigneeMobile());
    	Assert.assertEquals(param.getConsigneeName(), actual.getConsigneeName());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(actualShoppingOrderItemDO.getRefundStatus(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void getByShoppingOrderItemId() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	ShoppingRefundProcessDO shoppingRefundProcessDO = new ShoppingRefundProcessDO();
    	shoppingRefundProcessDO.setGmtCreate(new Date());
    	shoppingRefundProcessDO.setShoppingRefundDetailId(shoppingRefundDetailDO.getId());
    	shoppingRefundProcessDO.setRefundStatus(RefundStatusEnum.TO_BE_CONFIRMED.getValue());
    	shoppingRefundProcessDOMapper.insert(shoppingRefundProcessDO);
    	
    	ShoppingOrderItemExtendBO actual = shoppingRefundDetailService.getByShoppingOrderItemId(shoppingOrderItemDO.getId());
    	ShoppingOrderConverterTest.assertShoppingOrderBO(expected, actual.getShoppingOrder());
    	ShoppingOrderItemConverterTest.assertShoppingOrderItemBO(shoppingOrderItemDO, actual);
    	ShoppingRefundDetailConverterTest.assertShoppingRefundDetailBO(shoppingRefundDetailDO, actual.getShoppingRefundDetail());
    	for (ShoppingRefundProcessBO item : actual.getShoppingRefundDetail().getShoppingRefundProcessList()) {
    		ShoppingRefundProcessConverterTest.assertShoppingRefundProcessBO(shoppingRefundProcessDO, item);
    	}
    }
    
    @Transactional
    @Rollback
    @Test
    public void platformIntervention() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	shoppingRefundDetailService.platformIntervention(shoppingRefundDetailDO.getId(), expected.getMemberId());
    	
    	ShoppingOrderItemDO actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actualShoppingOrderItemDO);
    	Assert.assertEquals(RefundStatusEnum.PLATFORM_INTERVENTION.getValue(), actualShoppingOrderItemDO.getRefundStatus());
    	Assert.assertEquals(0, actualShoppingOrderItemDO.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actual = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertNotNull(actual.getGmtIntervention());
    	
    	ShoppingRefundProcessDOExample shoppingRefundProcessDOExample = new ShoppingRefundProcessDOExample();
    	shoppingRefundProcessDOExample.createCriteria().andShoppingRefundDetailIdEqualTo(shoppingRefundDetailDO.getId());
    	ShoppingRefundProcessDO shoppingRefundProcessDO = shoppingRefundProcessDOMapper.selectByExample(shoppingRefundProcessDOExample).get(0);
    	Assert.assertNotNull(shoppingRefundProcessDO);
    	Assert.assertNotNull(shoppingRefundProcessDO.getGmtCreate());
    	Assert.assertEquals(shoppingRefundDetailDO.getId(), shoppingRefundProcessDO.getShoppingRefundDetailId());
    	Assert.assertEquals(RefundStatusEnum.PLATFORM_INTERVENTION.getValue(), shoppingRefundProcessDO.getRefundStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void revokeRefundRequest() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	shoppingRefundDetailService.revokeRefundRequest(shoppingRefundDetailDO.getId(), expected.getMemberId());
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertNull(actual.getRefundStatus());
    	Assert.assertEquals(expected.getOrderStatus(), actual.getOrderStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertEquals(StatusEnum.INVALID.getValue(), actualShoppingRefundDetailDO.getStatus());
    }
    
    @Transactional
    @Rollback
    @Test
    public void revokeRefundRequestById() {
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
    	expected.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	expected.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
    	expected.setOrderTotalPrice(new BigDecimal(1));
    	expected.setOrderNum(IdWorkerHelperImpl.generate(BizIdType.ORDER));
    	expected.setStatus(StatusEnum.VALID.getValue());
    	expected.setConsigneeAddress("大冲商务中心1301");
    	expected.setConsigneeMobile("123456");
    	expected.setConsigneeName("Sunny");
    	expected.setIsDone(false);
    	expected.setShoppingCartIdsStr("1");
    	expected.setSendTime(0);
    	expected.setPaymentMethod(TransactionPayTypeEnum.BALANCE.getVal());
    	shoppingOrderDOMapper.insertSelective(expected);
    	
    	ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
    	shoppingOrderItemDO.setGmtCreate(new Date());
    	shoppingOrderItemDO.setGmtModified(new Date());
    	shoppingOrderItemDO.setIsAllowRefund(true);
    	shoppingOrderItemDO.setIsEvaluation(false);
    	shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
    	shoppingOrderItemDO.setProductFeatureImage("test.jpg");
    	shoppingOrderItemDO.setProductId(1L);
    	shoppingOrderItemDO.setProductName("productName");
    	shoppingOrderItemDO.setProductModelId(1L);
    	shoppingOrderItemDO.setProductModelName("test");
    	shoppingOrderItemDO.setQuantity(1);
    	shoppingOrderItemDO.setRegularPrice(new BigDecimal(1));
    	shoppingOrderItemDO.setSalesPrice(new BigDecimal(1));
    	// 已经发送两次提醒消息
    	shoppingOrderItemDO.setSendTime(1);
    	shoppingOrderItemDO.setShoppingOrderId(expected.getId());
    	shoppingOrderItemDO.setRefundStatus(RefundStatusEnum.REFUND_FAILED.getValue());
    	shoppingOrderItemDOMapper.insert(shoppingOrderItemDO);
    	
    	ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
    	shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
    	shoppingRefundDetailDO.setAmount(shoppingOrderItemDO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemDO.getQuantity())));
    	shoppingRefundDetailDO.setDescription("就是想退款");
    	shoppingRefundDetailDO.setGmtModified(new Date());
    	shoppingRefundDetailDO.setGmtCreate(new Date());
    	shoppingRefundDetailDO.setReason("七天无理由退货");
    	shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderItemDO.getId());
    	shoppingRefundDetailDO.setStatus(StatusEnum.VALID.getValue());
    	shoppingRefundDetailDOMapper.insert(shoppingRefundDetailDO);
    	
    	shoppingRefundDetailService.revokeRefundRequest(shoppingRefundDetailDO.getId());
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertNull(actual.getRefundStatus());
    	Assert.assertEquals(expected.getOrderStatus(), actual.getOrderStatus());
    	Assert.assertEquals(0, actual.getSendTime().intValue());
    	
    	ShoppingRefundDetailDO actualShoppingRefundDetailDO = shoppingRefundDetailDOMapper.selectByPrimaryKey(shoppingRefundDetailDO.getId());
    	Assert.assertNotNull(actualShoppingRefundDetailDO);
    	Assert.assertEquals(StatusEnum.INVALID.getValue(), actualShoppingRefundDetailDO.getStatus());
    }
    
}
