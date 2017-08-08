package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.user.param.FansInviteContentExtendParam;
import com.lawu.eshop.user.srv.domain.FansInviteContentDO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.mapper.FansInviteContentDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.service.FansInviteContentService;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.PwdUtil;
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
	
	@Autowired
	private MemberDOMapper memberDOMapper;
	
	@Transactional
    @Rollback
    @Test
    public void saveInviteContentService() {
		MemberDO memberDO = new MemberDO();
		memberDO.setNum("1");
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
		memberDOMapper.insertSelective(memberDO);
		
		memberDO.setNum("2");
        memberDO.setAccount("13666666667");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666667");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);
		
		FansInviteContentExtendParam fansInviteContentParam = new FansInviteContentExtendParam();
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
		fansInviteContentParam.setNums("1");
		fansInviteContentService.saveInviteContentService(fansInviteContentParam);
		List<FansInviteContentDO> list = fansInviteContentDOMapper.selectByExample(null);
		System.out.println(JSONObject.toJSONString(list));
		int i = fansInviteContentDOMapper.countByExample(null);
        Assert.assertEquals(1, i);
	}
	
	@Transactional
    @Rollback
    @Test
    public void saveInviteContentExtendService() {
		MemberDO memberDO = new MemberDO();
		memberDO.setNum("1");
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
		memberDOMapper.insertSelective(memberDO);
		
		memberDO.setNum("2");
        memberDO.setAccount("13666666667");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666667");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);
		
		FansInviteContentExtendParam fansInviteContentParam = new FansInviteContentExtendParam();
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
		fansInviteContentParam.setNums("1");
		fansInviteContentParam.setIds("1,2");
		fansInviteContentService.saveInviteContentExtendService(fansInviteContentParam);
		List<FansInviteContentDO> list = fansInviteContentDOMapper.selectByExample(null);
		System.out.println(JSONObject.toJSONString(list));
		int i = fansInviteContentDOMapper.countByExample(null);
        Assert.assertEquals(1, i);
	}
	
	@Test
	@Rollback
	@Transactional
	public void selectInviteContentById() {
		saveInviteContentService();
		fansInviteContentService.selectInviteContentById(1L);
	}
}
