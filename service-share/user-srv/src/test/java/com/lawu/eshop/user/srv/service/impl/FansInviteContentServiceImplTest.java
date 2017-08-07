package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.user.param.FansInviteContentParam;
import com.lawu.eshop.user.srv.mapper.FansInviteContentDOMapper;
import com.lawu.eshop.user.srv.service.FansInviteContentService;
/**
 * 
 * @author hongqm
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class FansInviteContentServiceImplTest {

	@Autowired
	private FansInviteContentService fansInviteContentService;
	
	@Autowired
	private FansInviteContentDOMapper fansInviteContentDOMapper;
	
	@Transactional
    @Rollback
    @Test
    public void saveInviteContentService() {
		FansInviteContentParam fansInviteContentParam = new FansInviteContentParam();
		fansInviteContentParam.setFansInviteDetailId(1L);
		fansInviteContentParam.setGmtCreate(new Date());
		fansInviteContentParam.setGmtModified(new Date());
		fansInviteContentParam.setInviteContent("sdasdad");
		fansInviteContentParam.setLogoUrl("logoUrl");
		fansInviteContentParam.setMerchantId(1L);
		fansInviteContentParam.setMerchantNum("num");
		fansInviteContentParam.setMerchantStoreIntro("简介");
		fansInviteContentParam.setMerchantStoreName("mingzi");
		fansInviteContentParam.setUrl("url");
		fansInviteContentService.saveInviteContentService(fansInviteContentParam);
		int i = fansInviteContentDOMapper.countByExample(null);
        Assert.assertEquals(1, i);
	}
	
	
	@Test
	@Rollback
	public void selectInviteContentById() {
		saveInviteContentService();
		fansInviteContentService.selectInviteContentById(1L);
	}
}
