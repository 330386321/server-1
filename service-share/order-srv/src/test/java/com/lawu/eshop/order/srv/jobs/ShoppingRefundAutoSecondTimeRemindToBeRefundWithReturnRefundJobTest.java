package com.lawu.eshop.order.srv.jobs;

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

import com.dangdang.ddframe.job.api.ShardingContext;
import com.lawu.eshop.idworker.client.impl.BizIdType;
import com.lawu.eshop.idworker.client.impl.IdWorkerHelperImpl;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.RefundStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.srv.constants.PropertyNameConstant;
import com.lawu.eshop.order.srv.domain.PropertyDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.utils.DateUtil;

/**
 * 退款中-待商家处理
 * 退款类型-退款
 * 商家处理超时定时任务
 * 发送站内信和推送给商家
 * 定时任务单元测试类
 * 
 * @author jiangxinjun
 * @createDate 2017年11月15日
 * @updateDate 2017年11月15日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class ShoppingRefundAutoSecondTimeRemindToBeRefundWithReturnRefundJobTest {
    
    @Autowired
    private ShoppingRefundAutoSecondTimeRemindToBeRefundWithReturnRefundJob shoppingRefundAutoSecondTimeRemindToBeRefundWithReturnRefundJob;
    
    @Autowired
    private ShardingContext shardingContext;
    
    @Autowired
    private PropertyDOMapper propertyDOMapper;
    
    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    
    @Autowired
    private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
    
    @Autowired
    private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
    
    @Transactional
    @Rollback
    @Test
    public void execute() {
        PropertyDO refundedSecondRemindPropertyDO = new PropertyDO();
        refundedSecondRemindPropertyDO.setGmtCreate(new Date());
        refundedSecondRemindPropertyDO.setGmtModified(new Date());
        refundedSecondRemindPropertyDO.setName(PropertyNameConstant.TO_BE_REFUNDED_REMIND_SECOND_TIME);
        refundedSecondRemindPropertyDO.setRemark("等待商家退款超时(待退款状态),第二次提醒时间");
        refundedSecondRemindPropertyDO.setValue("13");
        propertyDOMapper.insert(refundedSecondRemindPropertyDO);
        
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
        // 没有发送过提醒消息
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
        
        shoppingRefundAutoSecondTimeRemindToBeRefundWithReturnRefundJob.execute(shardingContext);
        
        ShoppingOrderDO actualShoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actualShoppingOrderDO);
        Assert.assertEquals(expected.getOrderStatus(), actualShoppingOrderDO.getOrderStatus());
        
        ShoppingOrderItemDO actualShoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(shoppingOrderItemDO.getId());
        Assert.assertNotNull(actualShoppingOrderItemDO);
        Assert.assertEquals(shoppingOrderItemDO.getRefundStatus(), actualShoppingOrderItemDO.getRefundStatus());
        Assert.assertEquals(shoppingOrderItemDO.getOrderStatus(), actualShoppingOrderItemDO.getOrderStatus());
        Assert.assertEquals(shoppingOrderItemDO.getSendTime() + 1, actualShoppingOrderItemDO.getSendTime().intValue());
    }
}
