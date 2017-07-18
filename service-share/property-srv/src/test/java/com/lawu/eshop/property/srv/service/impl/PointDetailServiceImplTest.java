package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PointDetailQueryParam;
import com.lawu.eshop.property.param.PointDetailReportParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.srv.bo.PointConsumeReportBO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.domain.PointDetailDO;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class PointDetailServiceImplTest {

    @Autowired
    private PointDetailService pointDetailService;

    @Autowired
    private PointDetailDOMapper pointDetailDOMapper;

    @Transactional
    @Rollback
    @Test
    public void save(){
        PointDetailSaveDataParam param = new PointDetailSaveDataParam();
        param.setTitle("看广告");
        param.setPointNum("1212121");
        param.setUserNum("M10001");
        param.setPointType(new Byte("1"));
        param.setPoint(new BigDecimal("100"));
        param.setRemark("");
        param.setDirection(new Byte("1"));
        param.setBizId("1");
        int ret = pointDetailService.save(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);
    }

    @Transactional
    @Rollback
    @Test
    public void findPageByUserNum(){
        PointDetailDO pointDetailDO = new PointDetailDO();
        pointDetailDO.setTitle("看广告");
        pointDetailDO.setPointNum(StringUtil.getRandomNum(""));
        pointDetailDO.setUserNum("M10001");
        pointDetailDO.setPointType(new Byte("1"));
        pointDetailDO.setPoint(new BigDecimal("100"));
        pointDetailDO.setDirection(new Byte("1"));
        pointDetailDO.setBizId("1");
        pointDetailDO.setRemark("");
        pointDetailDO.setGmtCreate(new Date());
        pointDetailDOMapper.insertSelective(pointDetailDO);

        PointDetailQueryParam param = new PointDetailQueryParam();
        param.setPageSize(10);
        param.setCurrentPage(1);
        Page<PointDetailBO> rntResult = pointDetailService.findPageByUserNum("M10001",param);
        Assert.assertEquals(1,rntResult.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void getBackagePointPageList(){
        PointDetailDO pointDetailDO = new PointDetailDO();
        pointDetailDO.setTitle("看广告");
        pointDetailDO.setPointNum(StringUtil.getRandomNum(""));
        pointDetailDO.setUserNum("M10001");
        pointDetailDO.setPointType(MemberTransactionTypeEnum.BACKAGE.getValue());
        pointDetailDO.setPoint(new BigDecimal("100"));
        pointDetailDO.setDirection(new Byte("1"));
        pointDetailDO.setBizId("1");
        pointDetailDO.setRemark("");
        pointDetailDO.setGmtCreate(new Date());
        pointDetailDOMapper.insertSelective(pointDetailDO);

        TransactionDetailQueryForBackageParam param = new TransactionDetailQueryForBackageParam();
        param.setUserNum("M10001");
        param.setMemberTransactionType(MemberTransactionTypeEnum.BACKAGE);
        param.setPageSize(10);
        param.setCurrentPage(1);
        Page<PointDetailBO> rntResult = pointDetailService.getBackagePointPageList(param);
        Assert.assertEquals(1,rntResult.getTotalCount().intValue());

        TransactionDetailQueryForBackageParam param1 = new TransactionDetailQueryForBackageParam();
        param1.setUserNum("M10001");
        param1.setPageSize(10);
        param1.setCurrentPage(1);
        Page<PointDetailBO> rntResult1 = pointDetailService.getBackagePointPageList(param1);
        Assert.assertEquals(1,rntResult1.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void selectPointDetailListByDateAndDirection(){
        PointDetailDO pointDetailDO = new PointDetailDO();
        pointDetailDO.setTitle("看广告");
        pointDetailDO.setPointNum(StringUtil.getRandomNum(""));
        pointDetailDO.setUserNum("M10001");
        pointDetailDO.setPointType(MemberTransactionTypeEnum.BACKAGE.getValue());
        pointDetailDO.setPoint(new BigDecimal("100"));
        pointDetailDO.setDirection(new Byte("1"));
        pointDetailDO.setBizId("1");
        pointDetailDO.setRemark("");
        pointDetailDO.setGmtCreate(new Date());
        pointDetailDOMapper.insertSelective(pointDetailDO);

        PointDetailReportParam param = new PointDetailReportParam();
        param.setDirection(new Byte("1"));
        param.setDate(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
        List<PointConsumeReportBO> rntList = pointDetailService.selectPointDetailListByDateAndDirection(param);
        Assert.assertEquals(1,rntList.size());
    }

    @Transactional
    @Rollback
    @Test
    public void selectPointDetailListByDateAndDirectionAndPointType(){
        PointDetailDO pointDetailDO = new PointDetailDO();
        pointDetailDO.setTitle("看广告");
        pointDetailDO.setPointNum(StringUtil.getRandomNum(""));
        pointDetailDO.setUserNum("M10001");
        pointDetailDO.setPointType(MemberTransactionTypeEnum.BACKAGE.getValue());
        pointDetailDO.setPoint(new BigDecimal("100"));
        pointDetailDO.setDirection(new Byte("1"));
        pointDetailDO.setBizId("1");
        pointDetailDO.setRemark("");
        pointDetailDO.setGmtCreate(new Date());
        pointDetailDOMapper.insertSelective(pointDetailDO);

        PointDetailReportParam param = new PointDetailReportParam();
        param.setDirection(new Byte("1"));
        param.setDate(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
        param.setPointType(MemberTransactionTypeEnum.BACKAGE.getValue());
        List<PointConsumeReportBO> rntList = pointDetailService.selectPointDetailListByDateAndDirectionAndPointType(param);
        Assert.assertEquals(1,rntList.size());
    }
}
