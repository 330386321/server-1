package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.constants.ConsumptionTypeEnum;
import com.lawu.eshop.property.dto.UserIncomeExpenditureDTO;
import com.lawu.eshop.property.dto.UserIncomeExpenditureDatailDTO;
import com.lawu.eshop.property.param.UserIncomeExpenditureQueryParam;
import com.lawu.eshop.property.srv.bo.UserIncomeExpenditureBO;
import com.lawu.eshop.property.srv.domain.extend.UserIncomeExpenditureDO;
import com.lawu.eshop.property.srv.domain.extend.UserIncomeExpenditureExample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangqh
 * @date 2017/7/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserIncomeExpenditureConverterTest {

    @Test
    public void convert(){
        UserIncomeExpenditureQueryParam param = new UserIncomeExpenditureQueryParam();
        param.setDate(new Date());

        UserIncomeExpenditureExample bo = UserIncomeExpenditureConverter.convert(param);
        Assert.assertNotNull(bo);
    }

    @Test
    public void convert1(){
        UserIncomeExpenditureDO userIncomeExpenditureDO = new UserIncomeExpenditureDO();
        userIncomeExpenditureDO.setUserNum("M10001");
        userIncomeExpenditureDO.setAmount(new BigDecimal("1"));
        userIncomeExpenditureDO.setDirection(new Byte("1"));
        UserIncomeExpenditureBO bo = UserIncomeExpenditureConverter.convert(userIncomeExpenditureDO);
        Assert.assertNotNull(bo);
    }

    @Test
    public void convertUserIncomeExpenditureBOList(){
        List<UserIncomeExpenditureDO> userIncomeExpenditureDOList = new ArrayList<>();
        UserIncomeExpenditureDO userIncomeExpenditureDO = new UserIncomeExpenditureDO();
        userIncomeExpenditureDO.setUserNum("M10001");
        userIncomeExpenditureDO.setAmount(new BigDecimal("1"));
        userIncomeExpenditureDO.setDirection(new Byte("1"));
        userIncomeExpenditureDOList.add(userIncomeExpenditureDO);
        List<UserIncomeExpenditureBO> rtnList = UserIncomeExpenditureConverter.convertUserIncomeExpenditureBOList(userIncomeExpenditureDOList);
        Assert.assertNotNull(rtnList);
    }

    @Test
    public void convertUserIncomeExpenditureDTOList(){
        List<UserIncomeExpenditureBO> userIncomeExpenditureBOList = new ArrayList<>();
        UserIncomeExpenditureBO userIncomeExpenditureBO = new UserIncomeExpenditureBO();
        userIncomeExpenditureBO.setUserNum("M10001");
        userIncomeExpenditureBO.setAmount(new BigDecimal("1"));
        userIncomeExpenditureBO.setDirection(ConsumptionTypeEnum.INCOME);
        userIncomeExpenditureBOList.add(userIncomeExpenditureBO);
        List<UserIncomeExpenditureDTO> rtnList = UserIncomeExpenditureConverter.convertUserIncomeExpenditureDTOList(userIncomeExpenditureBOList);
        Assert.assertNotNull(rtnList);
    }

    @Test
    public void convertUserIncomeExpenditureDatailDTO(){
        List<UserIncomeExpenditureBO> userIncomeExpenditureBOList = new ArrayList<>();
        UserIncomeExpenditureBO userIncomeExpenditureBO = new UserIncomeExpenditureBO();
        userIncomeExpenditureBO.setUserNum("M10001");
        userIncomeExpenditureBO.setAmount(new BigDecimal("1"));
        userIncomeExpenditureBO.setDirection(ConsumptionTypeEnum.INCOME);
        userIncomeExpenditureBOList.add(userIncomeExpenditureBO);
        UserIncomeExpenditureDatailDTO dto = UserIncomeExpenditureConverter.convertUserIncomeExpenditureDatailDTO(userIncomeExpenditureBOList);
        Assert.assertNotNull(dto);
    }
}
