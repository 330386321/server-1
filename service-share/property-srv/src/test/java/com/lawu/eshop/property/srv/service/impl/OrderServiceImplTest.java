package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.OrderRefundStatusEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransactionDetailDOMapper transactionDetailDOMapper;


    @Transactional
    @Rollback
    @Test
    public void doHandleOrderPayNotify(){
        NotifyCallBackParam param = new NotifyCallBackParam();
        param.setUserNum("M10001");
        param.setOutTradeNo("111111111");
        param.setBuyerLogonId("fdf**fdfd");
        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        param.setTotalFee("100");
        param.setBizIds("1");
        param.setTradeNo("2222222222");
        int ret = orderService.doHandleOrderPayNotify(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        TransactionDetailDO tdo = new TransactionDetailDO();
        tdo.setUserNum("M10001");
        tdo.setAmount(new BigDecimal("100"));
        tdo.setBizId("1");
        tdo.setBizNum("1");
        tdo.setDirection(new Byte("1"));
        tdo.setThirdTransactionNum("111111111");
        tdo.setTitle("ce");
        tdo.setTransactionAccount("fdfd");
        tdo.setTransactionAccountType(new Byte("1"));
        transactionDetailDOMapper.insert(tdo);

        NotifyCallBackParam param1 = new NotifyCallBackParam();
        param1.setUserNum("M10001");
        param1.setOutTradeNo("111111111");
        param1.setBuyerLogonId("fdf**fdfd");
        param1.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        param1.setTotalFee("100");
        param1.setBizIds("1");
        param1.setTradeNo("2222222222");
        int ret1 = orderService.doHandleOrderPayNotify(param1);
        Assert.assertEquals(ResultCode.SUCCESS,ret1);
    }

    @Transactional
    @Rollback
    @Test
    public void doHandlePayOrderNotify(){
        NotifyCallBackParam param = new NotifyCallBackParam();
        param.setUserNum("M10001");
        param.setOutTradeNo("111111111");
        param.setBuyerLogonId("fdf**fdfd");
        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        param.setTotalFee("100");
        param.setBizIds("1");
        param.setTradeNo("2222222222");
        param.setSideUserNum("B10002");
        int ret = orderService.doHandlePayOrderNotify(param);

        NotifyCallBackParam param1 = new NotifyCallBackParam();
        param1.setUserNum("M10001");
        param1.setOutTradeNo("111111111");
        param1.setBuyerLogonId("fdf**fdfd");
        param1.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        param1.setTotalFee("100");
        param1.setBizIds("1");
        param1.setTradeNo("2222222222");
        param.setSideUserNum("B10002");
        int ret1 = orderService.doHandlePayOrderNotify(param1);
        Assert.assertEquals(ResultCode.SUCCESS,ret1);
    }

    @Transactional
    @Rollback
    @Test
    public void comfirmDelivery(){
        OrderComfirmDataParam param = new OrderComfirmDataParam();
        param.setUserNum("M10001");
        param.setBizId("1");
        param.setTotalOrderMoney("100");
        int ret1 = orderService.comfirmDelivery(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret1);
    }

    @Transactional
    @Rollback
    @Test
    public void doRefundScopeInside() {
        OrderRefundDataParam param = new OrderRefundDataParam();
        param.setUserNum("M10001");
        param.setSideUserNum("B10002");
        param.setOrderId("1");
        param.setOrderItemIds("1");
        param.setRefundMoney("100");
        param.setLast(true);
        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.BALANCE);
        param.setTradeNo("1111111111");
        param.setOrderRefundStatusEnum(OrderRefundStatusEnum.FINISH);
        try {
            int ret = orderService.doRefundScopeInside(param);
            Assert.assertEquals(ResultCode.SUCCESS,ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        try {
            int ret = orderService.doRefundScopeInside(param);
            Assert.assertEquals(ResultCode.SUCCESS,ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.WX);
        try {
            int ret = orderService.doRefundScopeInside(param);
            Assert.assertEquals(ResultCode.SUCCESS,ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Rollback
    @Test
    public void comfirmReleaseFreeze(){
        OrderReleaseFreezeParam param = new OrderReleaseFreezeParam();
        param.setUserNums("M10001");
        param.setOrderIds("1");
        param.setAccounts("1361313");
        param.setPayWays(new Byte[]{1});
        int ret = orderService.comfirmReleaseFreeze(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);
    }

    @Transactional
    @Rollback
    @Test
    public void comfirmSysJob(){
        OrderSysJobParam param = new OrderSysJobParam();
        param.setUserNums("M10001");
        param.setOrderIds("1");
        param.setAccounts("164641");
        param.setOrderActualMoney("100");
        param.setPayWays(new Byte[]{1});
        int ret = orderService.comfirmSysJob(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);
    }
}
