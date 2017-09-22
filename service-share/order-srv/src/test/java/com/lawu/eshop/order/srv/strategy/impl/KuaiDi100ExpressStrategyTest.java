package com.lawu.eshop.order.srv.strategy.impl;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.lawu.eshop.order.srv.OrderSrvApplicationTest;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;

/**
 * 
 * @author jiangxinjun
 * @date 2017年9月21日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderSrvApplicationTest.class)
@WebAppConfiguration
public class KuaiDi100ExpressStrategyTest {

	@Autowired
	@Qualifier("kuaiDi100ExpressStrategy")
	private ExpressStrategy expressStrategy;

	@Test
	public void inquiries() {
		ExpressInquiriesDetailBO actual = expressStrategy.inquiries("huitongkuaidi", "70094570443407");
		Assert.assertNotNull(actual);
	}
	
	@Ignore
	@Test
	public void recognition() {
	}
	
}
