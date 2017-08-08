package com.lawu.eshop.ad.srv.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.constants.RedPacketPutWayEnum;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectParam;
import com.lawu.eshop.ad.srv.AdSrvApplicationTest;
import com.lawu.eshop.ad.srv.bo.UserRedPacketBO;
import com.lawu.eshop.ad.srv.domain.extend.UserRedpacketMaxMoney;
import com.lawu.eshop.ad.srv.service.UserRedPacketService;
import com.lawu.eshop.framework.core.page.Page;

/**
 * @author lihj
 * @date 2017/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdSrvApplicationTest.class)
@WebAppConfiguration
public class UserRedPacketServiceImplTest {

	private MockMvc mvc;

	@Autowired
	private UserRedPacketService userRedPacketService;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(userRedPacketService).build();
	}

	@Transactional
	@Rollback
	@Test
	public void addUserRedPacket() {
		UserRedPacketSaveParam param = new UserRedPacketSaveParam();
		param.setRedPacketPutWayEnum(RedPacketPutWayEnum.PUT_WAY_LUCK);
		param.setTotalCount(1);
		param.setTotalMoney(new BigDecimal(100));
		param.setUserAccount("15000000000");
		param.setUserNum("M000001");
		Integer i = userRedPacketService.addUserRedPacket(param);
		UserRedPacketSelectParam query = new UserRedPacketSelectParam();
		query.setCurrentPage(1);
		query.setPageSize(10);
		query.setUserNum("M000001");
		Page<UserRedPacketBO> userPage = userRedPacketService.selectUserRedPacketList(query);
		UserRedPacketBO bo = userPage.getRecords().get(0);
		Assert.assertEquals(param.getUserAccount(), bo.getUserAccount());
		Assert.assertEquals(Integer.valueOf(param.getTotalCount()), bo.getTotalCount());
		int t = param.getTotalMoney().compareTo(bo.getTotalMoney());
		Assert.assertEquals(0,t);
		Assert.assertEquals(param.getUserNum(), bo.getUserNum());
	}

	@Transactional
	@Rollback
	@Test
	public void isExistsRedPacket() {
		UserRedPacketSaveParam param = new UserRedPacketSaveParam();
		param.setRedPacketPutWayEnum(RedPacketPutWayEnum.PUT_WAY_LUCK);
		param.setTotalCount(1);
		param.setTotalMoney(new BigDecimal(100));
		param.setUserAccount("15000000000");
		param.setUserNum("M000001");
		userRedPacketService.addUserRedPacket(param);
		UserRedPacketSelectParam query = new UserRedPacketSelectParam();
		query.setCurrentPage(1);
		query.setPageSize(10);
		query.setUserNum("M000001");
		Page<UserRedPacketBO> userPage = userRedPacketService.selectUserRedPacketList(query);
		List<UserRedPacketBO> list = userPage.getRecords();
		for (int i = 0; i < list.size(); i++) {
			boolean flag = userRedPacketService.isExistsRedPacket(list.get(i).getId());
			Assert.assertEquals(true, flag);
		}
	}

	@Transactional
	@Rollback
	@Test
	public void executeUserRedPacketData() {
		UserRedPacketSaveParam param = new UserRedPacketSaveParam();
		param.setRedPacketPutWayEnum(RedPacketPutWayEnum.PUT_WAY_LUCK);
		param.setTotalCount(1);
		param.setTotalMoney(new BigDecimal(100));
		param.setUserAccount("15000000000");
		param.setUserNum("M000001");
		userRedPacketService.addUserRedPacket(param);
		userRedPacketService.executeUserRedPacketData();
	}

	@Transactional
	@Rollback
	@Test
	public void getUserRedpacketMoney() {
		UserRedPacketSaveParam param = new UserRedPacketSaveParam();
		param.setRedPacketPutWayEnum(RedPacketPutWayEnum.PUT_WAY_LUCK);
		param.setTotalCount(1);
		param.setTotalMoney(new BigDecimal(100));
		param.setUserAccount("15000000000");
		param.setUserNum("M000001");
		userRedPacketService.addUserRedPacket(param);
		UserRedPacketSelectParam query = new UserRedPacketSelectParam();
		query.setCurrentPage(1);
		query.setPageSize(10);
		query.setUserNum("M000001");
		Page<UserRedPacketBO> userPage = userRedPacketService.selectUserRedPacketList(query);
		UserRedPacketBO bo = userPage.getRecords().get(0);
		userRedPacketService.getUserRedpacketMoney(bo.getId(), bo.getUserNum());
		boolean flag = userRedPacketService.isExistsRedPacket(bo.getId());
		Assert.assertEquals(false, flag);
	}

	@Transactional
	@Rollback
	@Test
	public void getUserRedpacketMaxMoney(){
		UserRedPacketSaveParam param = new UserRedPacketSaveParam();
		param.setRedPacketPutWayEnum(RedPacketPutWayEnum.PUT_WAY_LUCK);
		param.setTotalCount(1);
		param.setTotalMoney(new BigDecimal(100));
		param.setUserAccount("15000000000");
		param.setUserNum("M000001");
		userRedPacketService.addUserRedPacket(param);
		UserRedPacketSelectParam query = new UserRedPacketSelectParam();
		query.setCurrentPage(1);
		query.setPageSize(10);
		query.setUserNum("M000001");
		Page<UserRedPacketBO> userPage = userRedPacketService.selectUserRedPacketList(query);
		UserRedPacketBO bo = userPage.getRecords().get(0);
		UserRedpacketMaxMoney maxMoney = userRedPacketService.getUserRedpacketMaxMoney(bo.getId());
		int t = maxMoney.getMaxMoney().compareTo(new BigDecimal(100));
		Assert.assertEquals(0,t);
	}
	
}
