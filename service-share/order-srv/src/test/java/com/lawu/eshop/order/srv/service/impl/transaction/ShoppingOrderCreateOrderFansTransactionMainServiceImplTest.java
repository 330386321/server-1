package com.lawu.eshop.order.srv.service.impl.transaction;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.TransactionRecordDO;
import com.lawu.eshop.order.srv.domain.TransactionRecordDOExample;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.TransactionRecordDOMapper;
import com.lawu.eshop.utils.RandomUtil;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class ShoppingOrderCreateOrderFansTransactionMainServiceImplTest {

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("shoppingOrderCreateOrderFansTransactionMainServiceImpl")
	private TransactionMainService shoppingOrderCreateOrderFansTransactionMainServiceImpl;
	
	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private TransactionRecordDOMapper transactionRecordDOMapper;

	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void receiveNotice() {
		// 初始化数据
    	ShoppingOrderDO expected = new ShoppingOrderDO();
    	expected.setCommodityTotalPrice(new BigDecimal(1));
    	expected.setActualAmount(new BigDecimal(1));
    	expected.setFreightPrice(new BigDecimal(0));
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setIsFans(true);
    	expected.setIsNeedsLogistics(true);
    	expected.setIsNoReasonReturn(false);
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
    	// 发送消息
		shoppingOrderCreateOrderFansTransactionMainServiceImpl.sendNotice(expected.getId());
		// 交易发送消息是否正常
    	TransactionRecordDOExample example = new TransactionRecordDOExample();
    	example.createCriteria().andRelateIdEqualTo(expected.getId());
    	TransactionRecordDO transactionRecordDO = transactionRecordDOMapper.selectByExample(example).get(0);
		Assert.assertNotNull(transactionRecordDO);
		Assert.assertNotNull(transactionRecordDO.getGmtModified());
		Assert.assertNotNull(transactionRecordDO.getGmtCreate());
		Assert.assertNotNull(transactionRecordDO.getId());
		Assert.assertNotNull(transactionRecordDO.getType());
		Assert.assertEquals(false, transactionRecordDO.getIsProcessed());
		Assert.assertEquals(expected.getId(), transactionRecordDO.getRelateId());
		Assert.assertEquals(0L, transactionRecordDO.getTimes().longValue());
		// 接收回复消息
		Reply reply = new Reply();
		reply.setTransactionId(transactionRecordDO.getId());
		shoppingOrderCreateOrderFansTransactionMainServiceImpl.receiveCallback(reply);
		// 校验回复消息处理是否正常
		transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecordDO.getId());
		Assert.assertEquals(true, transactionRecordDO.getIsProcessed());
		// 第二次发送消息
		shoppingOrderCreateOrderFansTransactionMainServiceImpl.sendNotice(expected.getId());
		// 校验第二次发送消息是否正常
    	transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecordDO.getId());
		Assert.assertEquals(false, transactionRecordDO.getIsProcessed());
	}

}
