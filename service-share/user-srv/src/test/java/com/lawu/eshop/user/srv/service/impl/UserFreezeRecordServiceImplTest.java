package com.lawu.eshop.user.srv.service.impl;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.user.param.CollectionUserRegParam;
import com.lawu.eshop.user.param.UserFreezeRecordParam;
import com.lawu.eshop.user.srv.domain.UserFreezeRecordDO;
import com.lawu.eshop.user.srv.mapper.UserFreezeRecordDOMapper;
import com.lawu.eshop.user.srv.service.UserFreezeRecordService;

/**
 * @author meishuquan
 * @date 2017/9/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class UserFreezeRecordServiceImplTest {

    @Autowired
    private UserFreezeRecordDOMapper userFreezeRecordDOMapper;

    @Autowired
    private UserFreezeRecordService userFreezeRecordService;

    @Transactional
    @Rollback
    @Test
    public void saveUserFreezeRecord() {
        UserFreezeRecordParam param = new UserFreezeRecordParam();
        param.setUserNum("M0001");
        param.setAccount("13666666666");
        param.setUserType(UserType.MEMBER.val);
        param.setCause("系统冻结");
        userFreezeRecordService.saveUserFreezeRecord(param);

        List<UserFreezeRecordDO> list = userFreezeRecordDOMapper.selectByExample(null);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(param.getUserNum(), list.get(0).getUserNum());
    }

}
