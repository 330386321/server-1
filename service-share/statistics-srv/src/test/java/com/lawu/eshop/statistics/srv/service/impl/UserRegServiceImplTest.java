package com.lawu.eshop.statistics.srv.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.statistics.param.UserRegAreaParam;
import com.lawu.eshop.statistics.param.UserRegParam;
import com.lawu.eshop.statistics.srv.service.UserRegService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class UserRegServiceImplTest {
	
	@Autowired
	private UserRegService userRegService;
	
	
	@Transactional
	@Rollback
	@Test
    public void saveUserRegDaily() {
		Integer memberCount = 10;
		Integer merchantCount = 20;
		userRegService.saveUserRegDaily(memberCount, merchantCount);
    }

	@Transactional
	@Rollback
	@Test
    public void saveUserRegMonth() {
		Integer memberCount = 10;
		Integer merchantCount = 20;
        userRegService.saveUserRegMonth(memberCount, merchantCount);
    }

	@Transactional
	@Rollback
	@Test
    public void updateUserRegArea() {
		UserRegAreaParam param = new UserRegAreaParam();
		param.setMemberCount(10);
		param.setMerchantCount(20);
		param.setCityId(4403);
		param.setMerchantCommonCount(10);
		param.setMerchantEntityCount(1);
		userRegService.updateUserRegArea(param);
    }

	@Transactional
	@Rollback
	@Test
    public void getReportUserRegDaily() {
		UserRegParam param = new UserRegParam();
		userRegService.getReportUserRegDaily(param);
    }

	@Transactional
	@Rollback
	@Test
    public void getReportUserRegMonth() {
		UserRegParam param = new UserRegParam();
		userRegService.getReportUserRegMonth(param);
    }

	@Transactional
	@Rollback
	@Test
    public void getReportUserRegArea() {
		userRegService.getReportUserRegArea();
    }
	
	@Transactional
	@Rollback
	@Test
    public void addUserRegAreaDaily() {
		UserRegAreaParam param = new UserRegAreaParam();
		param.setCityId(1);
		param.setMemberCount(1);
		param.setMerchantCommonCount(1);
		param.setMerchantCount(1);
		param.setMerchantEntityCount(1);
		param.setName("");
		userRegService.addUserRegAreaDaily(param);
    }
    
	@Transactional
	@Rollback
	@Test
    public void addUserRegAreaMonth() {
		UserRegAreaParam param = new UserRegAreaParam();
		param.setCityId(1);
		param.setMemberCount(1);
		param.setMerchantCommonCount(1);
		param.setMerchantCount(1);
		param.setMerchantEntityCount(1);
		param.setName("");
		userRegService.addUserRegAreaMonth(param);
    }
	
	@Transactional
	@Rollback
	@Test
    public void getReportDateUserRegDaily() {
		userRegService.getReportDateUserRegDaily();
    }
	
	@Transactional
	@Rollback
	@Test
    public void getReportDateUserRegMonth() {
		userRegService.getReportDateUserRegMonth();
    }
	
}
