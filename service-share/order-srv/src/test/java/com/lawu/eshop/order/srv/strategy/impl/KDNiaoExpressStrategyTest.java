package com.lawu.eshop.order.srv.strategy.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.lawu.eshop.order.srv.OrderSrvApplicationTest;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ExpressRecognitionDetailBO;

/**
 * 
 * @author jiangxinjun
 * @date 2017年9月4日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderSrvApplicationTest.class)
@WebAppConfiguration
public class KDNiaoExpressStrategyTest {

	@Autowired
	private KDNiaoExpressStrategy kDNiaoExpressStrategy;

	@Test
	public void inquiries() {
		ExpressInquiriesDetailBO actual = kDNiaoExpressStrategy.inquiries("YD", "3916525428265");
		Assert.assertNotNull(actual);
	}
	
	@Test
	public void recognition() {
		ExpressRecognitionDetailBO actual = kDNiaoExpressStrategy.recognition("3916525428265");
		Assert.assertNotNull(actual);
	}
	
}
