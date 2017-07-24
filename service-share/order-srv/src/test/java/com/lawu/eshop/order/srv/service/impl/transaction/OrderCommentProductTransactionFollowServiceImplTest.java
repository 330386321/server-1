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
import com.lawu.eshop.mq.dto.mall.CommentProductNotification;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.srv.domain.FollowTransactionRecordDO;
import com.lawu.eshop.order.srv.domain.FollowTransactionRecordDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.mapper.FollowTransactionRecordDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class OrderCommentProductTransactionFollowServiceImplTest {

    @SuppressWarnings("rawtypes")
	@Autowired
    @Qualifier("orderCommentProductTransactionFollowServiceImpl")
    private TransactionFollowService orderCommentProductTransactionFollowServiceImpl;

	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
	
    @SuppressWarnings("unchecked")
	@Transactional
    @Rollback
    @Test
    public void receiveNotice() {
    	ShoppingOrderItemDO expected = new ShoppingOrderItemDO();
    	expected.setGmtCreate(new Date());
    	expected.setGmtModified(new Date());
    	expected.setIsAllowRefund(true);
    	expected.setIsEvaluation(false);
    	expected.setOrderStatus(ShoppingOrderStatusEnum.PENDING.getValue());
    	expected.setProductFeatureImage("test.jpg");
    	expected.setProductId(1L);
    	expected.setProductName("productName");
    	expected.setProductModelId(1L);
    	expected.setProductModelName("test");
    	expected.setQuantity(1);
    	expected.setRegularPrice(new BigDecimal(1));
    	expected.setSalesPrice(new BigDecimal(1));
    	expected.setSendTime(0);
    	expected.setShoppingOrderId(1L);
    	shoppingOrderItemDOMapper.insert(expected);
    	
		CommentProductNotification notification = new CommentProductNotification();
		notification.setShoppingOrderItemId(1L);
		notification.setTransactionId(1L);
		orderCommentProductTransactionFollowServiceImpl.receiveNotice(notification);
    	
    	ShoppingOrderItemDO actual = shoppingOrderItemDOMapper.selectByPrimaryKey(expected.getId());
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(true, actual.getIsEvaluation());
    	
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
