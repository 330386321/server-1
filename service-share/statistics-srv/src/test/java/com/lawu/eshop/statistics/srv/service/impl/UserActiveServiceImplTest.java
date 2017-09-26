package com.lawu.eshop.statistics.srv.service.impl;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.statistics.srv.service.UserActiveService;
import com.lawu.eshop.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class UserActiveServiceImplTest {


	@Autowired
	private UserActiveService userActiveService;

	
	@Transactional
	@Rollback
	@Test
	public void collectionMemberActiveDaily() {
		userActiveService.collectionMemberActiveDaily(new Date());
	}
	
	@Transactional
	@Rollback
	@Test
	public void collectionMemberActiveAreaDaily() {
		userActiveService.collectionMemberActiveAreaDaily(DateUtil.getDate());
	}
	
	@Transactional
	@Rollback
	@Test
	public void collectionMerchantActiveDaily() {
		userActiveService.collectionMerchantActiveDaily(new Date());
    }

//	@Transactional
//	@Rollback
//	@Test
//    public void collectionMemberActiveMonth() {
//    	userActiveService.collectionMemberActiveAreaMonth();
//    }

//	@Transactional
//	@Rollback
//	@Test
//    public void collectionMerchantActiveMonth() {
//    	userActiveService.collectionMerchantActiveMonth();
//    }


	@Transactional
	@Rollback
	@Test
    public void collectionMerchantActiveAreaDaily() {
		userActiveService.collectionMerchantActiveAreaDaily(DateUtil.getDate());
    }

//	@Transactional
//	@Rollback
//	@Test
//    public void collectionMemberActiveAreaMonth() {
//		userActiveService.collectionMemberActiveAreaMonth();
//    }

//	@Transactional
//	@Rollback
//	@Test
//    public void collectionMerchantActiveAreaMonth() {
//		userActiveService.collectionMerchantActiveAreaMonth();
//    }
    
}
