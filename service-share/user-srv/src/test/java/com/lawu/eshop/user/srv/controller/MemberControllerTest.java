package com.lawu.eshop.user.srv.controller;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserSexEnum;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.srv.UserSrvApplicationTest;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.RandomUtil;
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

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author meishuquan
 * @date 2017/7/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserSrvApplicationTest.class)
@WebAppConfiguration
public class MemberControllerTest {

    private MockMvc mvc;

    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberDOMapper memberDOMapper;

    @Autowired
    private MemberProfileDOMapper memberProfileDOMapper;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Transactional
    @Rollback
    @Test
    public void find() {
        RequestBuilder request = get("/member/withPwd/13666666666").param("pwd", "123456");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberInfo() {
        RequestBuilder request = get("/member/findMemberInfo/100");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void updateMemberInfo() {
        UserParam param = new UserParam();
        param.setUserSexEnum(UserSexEnum.SEX_MALE);
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = put("/member/updateMemberInfo/100").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void updateLoginPwd() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = put("/member/updateLoginPwd/" + memberDO.getId()).param("originalPwd", "123456").param("newPwd", "222222");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void resetLoginPwd() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = put("/member/resetLoginPwd/13666666666").param("newPwd", "222222");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberByAccount() {
        RequestBuilder request = get("/member/getMember/13666666666");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberListByUser() {
        MemberQuery param = new MemberQuery();
        param.setAccountOrNickName("测试");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/member/findMemberListByUser").param("userId", "100").param("inviterType", "1").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void register() {
        RegisterRealParam param = new RegisterRealParam();
        param.setInviterId(100L);
        param.setUserNum("M0001");
        param.setAccount("13666666666");
        param.setPwd("123456");
        String requestJson = JSONObject.toJSONString(param);
        RequestBuilder request = post("/member/register").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void saveHeadImage() {
        RequestBuilder request = post("/member/saveHeadImage/100").param("headimg", "pic");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findCashUserInfo() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setRegionPath("44/4403/440303");
        memberDO.setRegionName("广东省深圳市南山区");
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = get("/member/findCashUserInfo/" + memberDO.getId());
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberCount() {
        RequestBuilder request = get("/member/findMemberCount").param("areas", "");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void setGtAndRongYunInfo() {
        RequestBuilder request = put("/member/setGtAndRongYunInfo/100").param("cid", "8888");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberInfoForShoppingOrder() {
        RequestBuilder request = get("/member/getMemberInfoForShoppingOrder/100");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findMember() {
        RequestBuilder request = get("/member/findMember/100");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findMessagePushList() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setGtCid("666666");
        memberDO.setRegionPath("44/4403/440303");
        memberDO.setRegionName("广东省深圳市南山区");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = get("/member/findMessagePushList").param("area", "all");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findMessagePushByMobile() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = get("/member/findMessagePushByMobile").param("moblie", "13666666666");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void isRegister() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = get("/member/isRegister").param("moblie", "13666666666");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberByNum() {
        RequestBuilder request = get("/member/getMemberByNum").param("num", "M0001");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getRongYunInfo() {
        RequestBuilder request = get("/member/getRongYunInfo/M0001");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void isExistsMobile() {
        RequestBuilder request = get("/member/isExistsMobile").param("mobile", "13666666666");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void delUserGtPush() {
        RequestBuilder request = put("/member/delUserGtPush").param("memberId", "100");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /*@Transactional
    @Rollback
    @Test
    public void getMemberByIds() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        List<Long> memberIds = new ArrayList<>();
        memberIds.add(memberDO.getId());
        RequestBuilder request = get("/member/getMemberByIds").param("memberIds", memberIds.toString());
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }*/

    @Transactional
    @Rollback
    @Test
    public void findMemberMineInfo() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setSex(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(memberDO.getId());
        memberProfileDOMapper.insertSelective(memberProfileDO);

        RequestBuilder request = get("/member/findMemberMineInfo/" + memberDO.getId());
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberAccountById() {
        RequestBuilder request = get("/member/getMemberAccountById").param("memberId", "100");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void findUserAccountAndRegionPathByNum() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = get("/member/findUserAccountAndRegionPathByNum").param("userNum", memberDO.getNum());
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void getTotalCount() {
        RequestBuilder request = get("/member/getTotalCount");
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Transactional
    @Rollback
    @Test
    public void adQueryMemberInfo() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setRegionPath("44/4403/440303");
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RequestBuilder request = get("/member/adQueryMemberInfo/" + memberDO.getId());
        try {
            ResultActions perform = mvc.perform(request);
            MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_OK)).andDo(MockMvcResultHandlers.print()).andReturn();
            Assert.assertEquals(HttpCode.SC_OK, mvcResult.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

}
