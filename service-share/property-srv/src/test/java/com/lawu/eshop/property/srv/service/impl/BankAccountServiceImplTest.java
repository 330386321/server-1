package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.property.param.BankAccountParam;
import com.lawu.eshop.property.srv.bo.BankAccountBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BankDO;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BankDOMapper;
import com.lawu.eshop.property.srv.service.BankAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class BankAccountServiceImplTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountDOMapper bankAccountDOMapper;

    @Autowired
    private BankDOMapper bankDOMapper;


    @Transactional
    @Rollback
    @Test
    public void saveBankAccount(){

        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountParam bankAccountParam = new BankAccountParam();
        bankAccountParam.setAccountName("习大大");
        bankAccountParam.setAccountNumber("6217852000014838927");
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("南山支行");
        bankAccountService.saveBankAccount("10001",bankAccountParam);

        List<BankAccountDO>  rntList = bankAccountDOMapper.selectByExample(null);
        Assert.assertNotNull(rntList);
        Assert.assertTrue(rntList.size() == 1);

    }

    @Transactional
    @Rollback
    @Test
    public void selectMyBank(){
        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountParam bankAccountParam = new BankAccountParam();
        bankAccountParam.setAccountName("习大大");
        bankAccountParam.setAccountNumber("6217852000014838927");
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("南山支行");
        bankAccountService.saveBankAccount("10001",bankAccountParam);

        List<BankAccountBO> rntList = bankAccountService.selectMyBank("10001");
        Assert.assertNotNull(rntList);
        Assert.assertTrue(rntList.size() == 1);

    }

    @Transactional
    @Rollback
    @Test
    public void remove(){
        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountParam bankAccountParam = new BankAccountParam();
        bankAccountParam.setAccountName("习大大");
        bankAccountParam.setAccountNumber("6217852000014838927");
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("南山支行");
        bankAccountService.saveBankAccount("10001",bankAccountParam);

        List<BankAccountBO> rntList = bankAccountService.selectMyBank("10001");
        Assert.assertNotNull(rntList);
        Assert.assertTrue(rntList.size() == 1);

        bankAccountService.remove(rntList.get(0).getId());

        rntList = bankAccountService.selectMyBank("10001");
        Assert.assertNull(rntList);
    }

    @Transactional
    @Rollback
    @Test
    public void selectByAccount(){
        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountParam bankAccountParam = new BankAccountParam();
        bankAccountParam.setAccountName("习大大");
        bankAccountParam.setAccountNumber("6217852000014838927");
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("南山支行");
        bankAccountService.saveBankAccount("10001",bankAccountParam);

        Boolean bool = bankAccountService.selectByAccount("6217852000014838927","10001");
        Assert.assertTrue(!bool);
    }

    @Transactional
    @Rollback
    @Test
    public void selectAccount(){
        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountParam bankAccountParam = new BankAccountParam();
        bankAccountParam.setAccountName("习大大");
        bankAccountParam.setAccountNumber("6217852000014838927");
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("南山支行");
        bankAccountService.saveBankAccount("10001",bankAccountParam);

//        List<BankAccountBO> rntList = bankAccountService.selectMyBank("10001");
//        Assert.assertNotNull(rntList);
//        Assert.assertTrue(rntList.size() == 1);

        BankAccountBO bo = bankAccountService.selectAccount(1L);
        Assert.assertNotNull(bo);
        Assert.assertEquals("6217852000014838927",bo.getAccountNumber());
    }

    @Transactional
    @Rollback
    @Test
    public void updateBankAccount(){
        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountParam bankAccountParam = new BankAccountParam();
        bankAccountParam.setAccountName("习大大");
        bankAccountParam.setAccountNumber("6217852000014838927");
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("南山支行");
        bankAccountService.saveBankAccount("10001",bankAccountParam);

        bankAccountParam = new BankAccountParam();
        bankAccountParam.setBankId(1);
        bankAccountParam.setSubBranchName("罗湖支行");
        bankAccountParam.setAccountNumber("6212264000054195975");
        bankAccountParam.setAccountName("彭丽媛");
        bankAccountService.updateBankAccount(1L,bankAccountParam);

        BankAccountBO bo = bankAccountService.selectAccount(1L);
        Assert.assertNotNull(bo);
        Assert.assertEquals("6212264000054195975",bo.getAccountNumber());
    }
}
