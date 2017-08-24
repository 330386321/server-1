/**
 * 
 */
package com.lawu.eshop.ad.srv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.ad.constants.RedPacketPutWayEnum;
import com.lawu.eshop.ad.param.UserRedPacketSaveParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectNumParam;
import com.lawu.eshop.ad.param.UserRedPacketSelectParam;
import com.lawu.eshop.ad.srv.AdSrvApplicationTest;
import com.lawu.eshop.framework.web.HttpCode;

/**
 * @author lihj
 * @date 2017年8月7日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdSrvApplicationTest.class)
@WebAppConfiguration
public class UserRedPacketControllerTest {
	private MockMvc mvc;

	@Autowired
	private UserRedPacketController userRedPacketController;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(userRedPacketController).build();
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
		param.setUserNum("M00001");
		String json = JSONObject.toJSONString(param);
		RequestBuilder request = post("/userRedPacket/addUserRedPacket").contentType(MediaType.APPLICATION_JSON)
				.content(json);
		try {
			ResultActions perform = mvc.perform(request);
			MvcResult result = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Transactional
	@Rollback
	@Test
	public void selectUserRedPacketList() {
		addUserRedPacket();
		UserRedPacketSelectNumParam param = new UserRedPacketSelectNumParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		param.setNum("M00001");
		String json = JSONObject.toJSONString(param);
		RequestBuilder request = post("/userRedPacket/selectUserRedPacketList").contentType(MediaType.APPLICATION_JSON)
				.content(json);
		try {
			ResultActions perform = mvc.perform(request);
			MvcResult result = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
			System.out.println(result.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Transactional
	@Rollback
	@Test
	public void isExistsRedPacket() {
		addUserRedPacket();
		UserRedPacketSelectNumParam param = new UserRedPacketSelectNumParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		param.setNum("M00001");
		String json = JSONObject.toJSONString(param);
		RequestBuilder request = post("/userRedPacket/selectUserRedPacketList").contentType(MediaType.APPLICATION_JSON)
				.content(json);
		try {
			ResultActions perform = mvc.perform(request);
			MvcResult result = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
			String str = result.getResponse().getContentAsString();
			List<String> list = fromJson(str);
			for (int i = 0; i < list.size(); i++) {
				RequestBuilder query = get("/userRedPacket/isExistsRedPacket/" + list.get(i));
				ResultActions queryPerform = mvc.perform(query);
				MvcResult queryResult = queryPerform.andExpect(status().is(HttpCode.SC_OK))
						.andDo(MockMvcResultHandlers.print()).andReturn();
				System.out.println(queryResult.getResponse().getContentAsString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

	@Transactional
	@Rollback
	@Test
	public void executeUserRedPacketData() {
		addUserRedPacket();
		RequestBuilder request = post("/userRedPacket/executeUserRedPacketData");
		try {
			ResultActions perform = mvc.perform(request);
			MvcResult result = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
			System.out.println(result.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Transactional
	@Rollback
	@Test
	public void getUserRedpacketMoney() {
		addUserRedPacket();
		UserRedPacketSelectNumParam param = new UserRedPacketSelectNumParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		param.setNum("M00001");
		String json = JSONObject.toJSONString(param);
		RequestBuilder request = post("/userRedPacket/selectUserRedPacketList").contentType(MediaType.APPLICATION_JSON)
				.content(json);
		try {
			ResultActions perform = mvc.perform(request);
			MvcResult result = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
			String str = result.getResponse().getContentAsString();
			List<String> list = fromJson(str);
			for (int i = 0; i < list.size(); i++) {
				// 判断是否可以领取
				RequestBuilder query = get("/userRedPacket/isExistsRedPacket/" + list.get(i));
				ResultActions queryPerform = mvc.perform(query);
				MvcResult queryResult = queryPerform.andExpect(status().is(HttpCode.SC_OK))
						.andDo(MockMvcResultHandlers.print()).andReturn();
				System.out.println("1111111111111111111" + queryResult.getResponse().getContentAsString());

				// 领取红包
				RequestBuilder getRedpacket = post("/userRedPacket/getUserRedpacketMoney")
						.param("redPacketId", list.get(i)).param("userNum", "M00001");
				ResultActions getPerform = mvc.perform(getRedpacket);
				MvcResult get = getPerform.andExpect(status().is(HttpCode.SC_CREATED))
						.andDo(MockMvcResultHandlers.print()).andReturn();
				System.out.println("2222222222222222222222" + get.getResponse().getContentAsString());

				//
				RequestBuilder query1 = get("/userRedPacket/isExistsRedPacket/" + list.get(i));
				ResultActions queryPerform1 = mvc.perform(query1);
				MvcResult queryResult1 = queryPerform1.andExpect(status().is(HttpCode.SC_OK))
						.andDo(MockMvcResultHandlers.print()).andReturn();
				System.out.println("33333333333333333333" + queryResult1.getResponse().getContentAsString());

			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Transactional
	@Rollback
	@Test
	public void getUserRedpacketMaxMoney() {
		addUserRedPacket();
		UserRedPacketSelectNumParam param = new UserRedPacketSelectNumParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		param.setNum("M00001");
		String json = JSONObject.toJSONString(param);
		RequestBuilder request = post("/userRedPacket/selectUserRedPacketList").contentType(MediaType.APPLICATION_JSON)
				.content(json);
		try {
			ResultActions perform = mvc.perform(request);
			MvcResult result = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
			String str = result.getResponse().getContentAsString();
			List<String> list = fromJson(str);
			for (int i = 0; i < list.size(); i++) {
				// 判断是否可以领取
				RequestBuilder query = get("/userRedPacket/isExistsRedPacket/" + list.get(i));
				ResultActions queryPerform = mvc.perform(query);
				MvcResult queryResult = queryPerform.andExpect(status().is(HttpCode.SC_OK))
						.andDo(MockMvcResultHandlers.print()).andReturn();
				System.out.println("1111111111111111111" + queryResult.getResponse().getContentAsString());

				RequestBuilder maxRequest = post("/userRedPacket/getUserRedpacketMaxMoney").param("redPacketId",
						list.get(i));
				ResultActions maxperform = mvc.perform(maxRequest);
				MvcResult maxresult = maxperform.andExpect(status().is(HttpCode.SC_CREATED))
						.andDo(MockMvcResultHandlers.print()).andReturn();
				System.out.println(maxresult.getResponse().getContentAsString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * @param str
	 * @return
	 */
	private List<String> fromJson(String str) {
		List<String> listIds = Lists.newArrayList();
		JSONObject json = JSONObject.parseObject(str);
		JSONObject records = JSONObject.parseObject(json.get("model").toString());
		List<JSONObject> list = (List<JSONObject>) records.get("records");
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = list.get(i);
			listIds.add(obj.getString("id"));
		}
		return listIds;
	}
}
