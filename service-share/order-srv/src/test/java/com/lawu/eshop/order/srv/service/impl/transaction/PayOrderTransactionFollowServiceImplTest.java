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

import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import com.lawu.eshop.mq.dto.order.PayOrderNotification;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.order.srv.domain.FollowTransactionRecordDOExample;
import com.lawu.eshop.order.srv.domain.PayOrderDO;
import com.lawu.eshop.order.srv.mapper.FollowTransactionRecordDOMapper;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;
import com.lawu.eshop.utils.StringUtil;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class PayOrderTransactionFollowServiceImplTest {

    @SuppressWarnings("rawtypes")
	@Autowired
    @Qualifier("payOrderTransactionFollowServiceImpl")
    private TransactionFollowService payOrderTransactionFollowServiceImpl;

	@Autowired
	private PayOrderDOMapper payOrderDOMapper;
	
	@Autowired
	private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
	
    @SuppressWarnings("unchecked")
	@Transactional
    @Rollback
    @Test
    public void receiveNotice() {
		// 插入一条已经支付成功并且未评论的买单记录
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
		expected.setPayType(TransactionPayTypeEnum.BALANCE.getVal());
		expected.setStatus(PayOrderStatusEnum.STATUS_UNPAY.getVal());
		expected.setTotalAmount(new BigDecimal(2));
		payOrderDOMapper.insert(expected);
    	
    	PayOrderNotification notification = new PayOrderNotification();
    	notification.setPayOrderId(expected.getId());
		notification.setTransactionId(1L);
		payOrderTransactionFollowServiceImpl.receiveNotice(notification);
    	
    	PayOrderDO actual = payOrderDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal(), actual.getStatus());
    	
    	FollowTransactionRecordDOExample example = new FollowTransactionRecordDOExample();
    	example.createCriteria().andTransationIdEqualTo(notification.getTransactionId());
    	FollowTransactionRecordDO followTransactionRecordDO = followTransactionRecordDOMapper.selectByExample(example).get(0);
    	Assert.assertNotNull(followTransactionRecordDO);
    	Assert.assertNotNull(followTransactionRecordDO.getTopic());
    	Assert.assertNotNull(followTransactionRecordDO.getGmtCreate());
    	Assert.assertNotNull(followTransactionRecordDO.getId());
    	Assert.assertEquals(notification.getTransactionId(), followTransactionRecordDO.getTransationId());
    }
    
}
