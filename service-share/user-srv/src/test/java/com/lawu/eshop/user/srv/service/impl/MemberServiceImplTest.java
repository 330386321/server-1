package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.srv.bo.CashUserInfoBO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MessagePushBO;
import com.lawu.eshop.user.srv.bo.RongYunBO;
import com.lawu.eshop.user.srv.domain.FansMerchantDO;
import com.lawu.eshop.user.srv.domain.InviteRelationDO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.mapper.FansMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDOMapper memberDOMapper;

    @Autowired
    private MemberProfileDOMapper memberProfileDOMapper;

    @Autowired
    private InviteRelationDOMapper inviteRelationDOMapper;

    @Autowired
    private FansMerchantDOMapper fansMerchantDOMapper;

    @Transactional
    @Rollback
    @Test
    public void find() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setSex(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberBO memberBO = memberService.find("13666666666", "123456");
        Assert.assertNotNull(memberBO);
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberInfoById() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setSex(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberBO memberBO = memberService.findMemberInfoById(memberDO.getId());
        Assert.assertNotNull(memberBO);
    }

    @Transactional
    @Rollback
    @Test
    public void updateMemberInfo() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setSex(DataTransUtil.intToByte(1));
        memberDO.setHeadimg("pic");
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        UserParam param = new UserParam();
        param.setNickname("测试昵称");
        int result = memberService.updateMemberInfo(param, memberDO.getId());
        Assert.assertEquals(1, result);

        MemberBO memberBO = memberService.findMemberInfoById(memberDO.getId());
        Assert.assertNotNull(memberBO);
        Assert.assertTrue(param.getNickname().equals(memberBO.getNickname()));
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

        memberService.updateLoginPwd(memberDO.getId(), "222222");
        MemberBO memberBO = memberService.findMemberInfoById(memberDO.getId());
        Assert.assertNotNull(memberBO);
        Assert.assertTrue(PwdUtil.verify("222222", memberBO.getPwd()));
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberByAccount() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberBO memberBO = memberService.getMemberByAccount(memberDO.getAccount());
        Assert.assertNotNull(memberBO);
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberListByUser() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberDO inviteMember = new MemberDO();
        inviteMember.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        inviteMember.setAccount("13666666666");
        inviteMember.setPwd(PwdUtil.generate("123456"));
        inviteMember.setMobile("13666666666");
        inviteMember.setStatus(DataTransUtil.intToByte(1));
        inviteMember.setInviterId(memberDO.getId());
        inviteMember.setInviterType(DataTransUtil.intToByte(1));
        inviteMember.setNickname("测试昵称");
        inviteMember.setGmtCreate(new Date());
        memberDOMapper.insertSelective(inviteMember);

        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(memberDO.getId());
        memberProfileDO.setInviteMemberCount(10);
        memberProfileDOMapper.insertSelective(memberProfileDO);

        memberProfileDO.setId(inviteMember.getId());
        memberProfileDOMapper.insertSelective(memberProfileDO);

        MemberQuery param = new MemberQuery();
        param.setAccountOrNickName("昵称");
        Page<MemberBO> memberBOPage = memberService.findMemberListByUser(memberDO.getId(), param, UserTypeEnum.MEMBER.val);
        Assert.assertNotNull(memberBOPage.getRecords());
        Assert.assertEquals(10, memberBOPage.getTotalCount().intValue());
        Assert.assertEquals(1, memberBOPage.getRecords().size());
    }

    @Transactional
    @Rollback
    @Test
    public void register() {
        //邀请人
        String userNum = RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG);
        //邀请人的推荐人
        String mNum = RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG);

        //邀请人的推荐人和邀请人关系
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserNum(mNum);
        inviteRelationDO.setInviteUserNum(userNum);
        inviteRelationDO.setDepth(1);
        inviteRelationDO.setType(DataTransUtil.intToByte(1));
        inviteRelationDOMapper.insertSelective(inviteRelationDO);

        //邀请人信息
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(userNum);
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        //邀请人扩展信息
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(memberDO.getId());
        memberProfileDOMapper.insertSelective(memberProfileDO);

        //邀请人的推荐人信息
        MemberDO memberDO1 = new MemberDO();
        memberDO1.setNum(mNum);
        memberDO1.setAccount("13666666666");
        memberDO1.setPwd(PwdUtil.generate("123456"));
        memberDO1.setMobile("13666666666");
        memberDO1.setStatus(DataTransUtil.intToByte(1));
        memberDO1.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO1);

        //邀请人的推荐人扩展信息
        MemberProfileDO memberProfileDO1 = new MemberProfileDO();
        memberProfileDO1.setId(memberDO1.getId());
        memberProfileDOMapper.insertSelective(memberProfileDO1);

        RegisterRealParam param = new RegisterRealParam();
        param.setAccount("13666666666");
        param.setPwd("123456");
        param.setInviterId(memberDO.getId());
        param.setUserNum(userNum);
        memberService.register(param);

        //会员信息
        List<MemberDO> memberDOList = memberDOMapper.selectByExample(null);
        Assert.assertNotNull(memberDOList);
        Assert.assertEquals(3, memberDOList.size());

        //扩展信息
        List<MemberProfileDO> memberProfileDOS = memberProfileDOMapper.selectByExample(null);
        Assert.assertNotNull(memberProfileDOS);
        Assert.assertEquals(3, memberProfileDOS.size());

        //粉丝记录
        List<FansMerchantDO> fansMerchantDOS = fansMerchantDOMapper.selectByExample(null);
        Assert.assertNotNull(fansMerchantDOS);
        Assert.assertEquals(1, fansMerchantDOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberById() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberBO memberBO = memberService.getMemberById(memberDO.getId());
        Assert.assertNotNull(memberBO);
    }

    @Transactional
    @Rollback
    @Test
    public void updateMemberHeadImg() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        memberService.updateMemberHeadImg("pic", memberDO.getId());
        MemberDO memberDO1 = memberDOMapper.selectByPrimaryKey(memberDO.getId());
        Assert.assertNotNull(memberDO1);
        Assert.assertTrue("pic".equals(memberDO1.getHeadimg()));
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
        memberDO.setRegionPath("44/4403/440303");
        memberDO.setRegionName("广东省深圳市南山区");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        CashUserInfoBO cashUserInfoBO = memberService.findCashUserInfo(memberDO.getId());
        Assert.assertNotNull(cashUserInfoBO);
        Assert.assertTrue(cashUserInfoBO.getProvinceId() == 44);
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberCount() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setRegionPath("44/4403/440303");
        memberDO.setRegionName("广东省深圳市南山区");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setRegionPath("44/4405/440305");
        memberDO.setRegionName("广东省深圳市南山区");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        int result = memberService.findMemberCount("ALL_PLACE");
        Assert.assertEquals(2, result);

        result = memberService.findMemberCount("4403");
        Assert.assertEquals(1, result);
    }

    @Transactional
    @Rollback
    @Test
    public void setGtAndRongYunInfo() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        memberService.setGtAndRongYunInfo(memberDO.getId(), "666666");
        MemberDO memberDO1 = memberDOMapper.selectByPrimaryKey(memberDO.getId());
        Assert.assertNotNull(memberDO1);
        Assert.assertTrue("666666".equals(memberDO1.getGtCid()));
    }

    @Transactional
    @Rollback
    @Test
    public void findMemberByNum() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberBO memberBO = memberService.findMemberByNum(memberDO.getNum());
        Assert.assertNotNull(memberBO);
        Assert.assertTrue(memberDO.getNum().equals(memberBO.getNum()));
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

        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setGtCid("666666");
        memberDO.setRegionPath("44/4405/440505");
        memberDO.setRegionName("广东省深圳市南山区");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        List<MessagePushBO> messagePushBOS = memberService.findMessagePushList("all");
        Assert.assertNotNull(messagePushBOS);
        Assert.assertEquals(2, messagePushBOS.size());

        messagePushBOS = memberService.findMessagePushList("44/4403");
        Assert.assertNotNull(messagePushBOS);
        Assert.assertEquals(1, messagePushBOS.size());
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

        MessagePushBO messagePushBO = memberService.findMessagePushByMobile("13666666666");
        Assert.assertNotNull(messagePushBO);
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

        MemberBO memberBO = memberService.isRegister("13666666666");
        Assert.assertNotNull(memberBO);
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberByNum() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberBO memberBO = memberService.getMemberByNum(memberDO.getNum());
        Assert.assertNotNull(memberBO);
        Assert.assertTrue(memberDO.getNum().equals(memberBO.getNum()));
    }

    @Transactional
    @Rollback
    @Test
    public void getRongYunInfoByNum() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        RongYunBO rongYunBO = memberService.getRongYunInfoByNum(memberDO.getNum());
        Assert.assertNotNull(rongYunBO);
        Assert.assertTrue(memberDO.getNum().equals(rongYunBO.getUserNum()));
    }

    @Transactional
    @Rollback
    @Test
    public void isExistsMobile() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        boolean result = memberService.isExistsMobile("13666666666");
        Assert.assertTrue(result);
    }

    @Transactional
    @Rollback
    @Test
    public void delUserGtPush() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGtCid("666666");
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        memberService.delUserGtPush(memberDO.getId());
        MemberDO memberDO1 = memberDOMapper.selectByPrimaryKey(memberDO.getId());
        Assert.assertNotNull(memberDO1);
        Assert.assertNull(memberDO1.getGtCid());
    }

    @Transactional
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

        MemberDO memberDO1 = new MemberDO();
        memberDO1.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO1.setAccount("13666666666");
        memberDO1.setPwd(PwdUtil.generate("123456"));
        memberDO1.setMobile("13666666666");
        memberDO1.setStatus(DataTransUtil.intToByte(1));
        memberDO1.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO1);

        List<Long> memberIds = new ArrayList<>();
        memberIds.add(memberDO.getId());
        memberIds.add(memberDO1.getId());
        List<MemberBO> memberBOS = memberService.getMemberByIds(memberIds);
        Assert.assertNotNull(memberBOS);
        Assert.assertEquals(2, memberBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void getMemberAccountById() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        String result = memberService.getMemberAccountById(memberDO.getId());
        Assert.assertNotNull(result);
        Assert.assertTrue(memberDO.getAccount().equals(result));
    }

    @Transactional
    @Rollback
    @Test
    public void getTotalCount() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd(PwdUtil.generate("123456"));
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MemberDO memberDO1 = new MemberDO();
        memberDO1.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO1.setAccount("13666666666");
        memberDO1.setPwd(PwdUtil.generate("123456"));
        memberDO1.setMobile("13666666666");
        memberDO1.setStatus(DataTransUtil.intToByte(1));
        memberDO1.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO1);

        int result = memberService.getTotalCount();
        Assert.assertEquals(2, result);
    }

}
