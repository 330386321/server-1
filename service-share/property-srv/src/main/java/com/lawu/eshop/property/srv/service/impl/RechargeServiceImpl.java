package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.*;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.param.AgentReportRechargeQueryParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.RechargeQueryDataParam;
import com.lawu.eshop.property.param.RechargeReportParam;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.AgentReportRechargeQueryBO;
import com.lawu.eshop.property.srv.bo.AreaRechargePointBO;
import com.lawu.eshop.property.srv.bo.BalanceAndPointListQueryBO;
import com.lawu.eshop.property.srv.bo.RechargeReportBO;
import com.lawu.eshop.property.srv.domain.RechargeDO;
import com.lawu.eshop.property.srv.domain.RechargeDOExample;
import com.lawu.eshop.property.srv.domain.extend.AreaRechargePointDOView;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.domain.extend.ReportAreaRechargeDOExtend;
import com.lawu.eshop.property.srv.domain.extend.ReportAreaRechargeDOView;
import com.lawu.eshop.property.srv.mapper.RechargeDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.mapper.extend.RechargeDOMapperExtend;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.RechargeService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RechargeServiceImpl implements RechargeService {

    private static Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Autowired
    private RechargeDOMapper rechargeDOMapper;
    @Autowired
    private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private PointDetailService pointDetailService;
    @Autowired
    private RechargeDOMapperExtend rechargeDOMapperExtend;

    @Override
    @Transactional
    public RechargeSaveDTO save(RechargeSaveDataParam param) {

        RechargeDO recharge = new RechargeDO();
        recharge.setUserNum(param.getUserNum());
        recharge.setRechargeMoney(new BigDecimal(param.getRechargeMoney()));
        recharge.setCurrentScale(param.getRechargeScale());

        double dCurrentScale = Double.parseDouble(param.getRechargeScale());
        double dRechargeMoney = Double.parseDouble(param.getRechargeMoney());
        double money = dRechargeMoney * dCurrentScale;
        recharge.setMoney(BigDecimal.valueOf(money));

        recharge.setRechargeType(param.getPayTypeEnum().getVal());
        recharge.setChannel(param.getTransactionPayTypeEnum().getVal());
        recharge.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        recharge.setRechargeNumber(StringUtil.getRandomNum(""));
        recharge.setGmtCreate(new Date());
        //保存省市区用于代理商区域统计
        if(param.getRegionPath() != null && !"".equals(param.getRegionPath())){
            String[] regions = param.getRegionPath().split("/");
            recharge.setProvinceId(regions.length > 0 ? Integer.valueOf(regions[0]) : 0);
            recharge.setCityId(regions.length > 1 ? Integer.valueOf(regions[1]) : 0);
            recharge.setAreaId(regions.length > 2 ? Integer.valueOf(regions[2]) : 0);
        }
        if(recharge.getUserNum().startsWith(com.lawu.eshop.user.constants.UserTypeEnum.MEMBER.name())){
            recharge.setUserType(com.lawu.eshop.user.constants.UserTypeEnum.MEMBER.val);
        } else if(recharge.getUserNum().startsWith(UserTypeEnum.MEMCHANT.name())){
            recharge.setUserType(com.lawu.eshop.user.constants.UserTypeEnum.MEMCHANT.val);
        }
        rechargeDOMapper.insertSelective(recharge);

        if (recharge.getId() == null) {
            return null;
        }
        RechargeSaveDTO dto = new RechargeSaveDTO();
        dto.setId(recharge.getId());
        return dto;
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public Result doHandleRechargeNotify(NotifyCallBackParam param) {
        Result result = new Result();

        RechargeDO recharge = rechargeDOMapper.selectByPrimaryKey(Long.valueOf(param.getBizIds()));
        if (recharge == null) {
            result.setRet(ResultCode.FAIL);
            result.setMsg("充值记录为空");
            return result;
        } else {
            if (ThirdPayStatusEnum.SUCCESS.getVal().equals(recharge.getStatus())) {
                result.setRet(ResultCode.PROCESSED_RETURN_SUCCESS);
                logger.info("重复回调(判断幂等)");
                return result;
            }
        }
        BigDecimal dbMoney = recharge.getRechargeMoney();
        BigDecimal backMoney = new BigDecimal(param.getTotalFee());
        if (backMoney.compareTo(dbMoney) != 0) {
            result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
            result.setMsg("充值回调金额与充值表保存的金额不一致(回调金额：" + backMoney + ",表中金额:" + dbMoney + ")");
            return result;
        }

        // 充余额：加财产余额，新增充余额交易明细,修改充值表状态
        // 充积分：加财产积分，新增积分明细，新增充积分交易明细,修改充值表状态

        int bizFlagInt = Integer.parseInt(param.getBizFlag());
        TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();

        if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))
                || ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
            //新增交易明细
            tdsParam.setTitle(TransactionTitleEnum.RECHARGE.getVal());
            tdsParam.setUserNum(param.getUserNum());
            tdsParam.setTransactionAccount(param.getBuyerLogonId());
            if (param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                tdsParam.setTransactionType(MemberTransactionTypeEnum.RECHARGE.getValue());
            } else if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
                tdsParam.setTransactionType(MerchantTransactionTypeEnum.RECHARGE.getValue());
            }
            tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().getVal());
            tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
            tdsParam.setBizId(param.getBizIds());
            tdsParam.setThirdTransactionNum(param.getTradeNo());
            tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
            tdsParam.setBizNum(param.getOutTradeNo());
            transactionDetailService.save(tdsParam);

            //加余额
            PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
            infoDoView.setUserNum(param.getUserNum());
            infoDoView.setBalance(new BigDecimal(param.getTotalFee()));
            infoDoView.setGmtModified(new Date());
            propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

        } else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))
                || ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.getVal().equals(StringUtil.intToByte(bizFlagInt))) {
            //新增交易明细
            tdsParam.setTitle(TransactionTitleEnum.INTEGRAL_RECHARGE.getVal());
            tdsParam.setUserNum(param.getUserNum());
            tdsParam.setTransactionAccount(param.getBuyerLogonId());
            if (param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                tdsParam.setTransactionType(MemberTransactionTypeEnum.RECHARGE.getValue());
            } else if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
                tdsParam.setTransactionType(MerchantTransactionTypeEnum.RECHARGE.getValue());
            }
            tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().getVal());
            tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
            tdsParam.setBizId(param.getBizIds());
            tdsParam.setThirdTransactionNum(param.getTradeNo());
            tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
            tdsParam.setBizNum(param.getOutTradeNo());
            transactionDetailService.save(tdsParam);

            //新增积分明细
            PointDetailSaveDataParam pdsParam = new PointDetailSaveDataParam();
            pdsParam.setTitle(TransactionTitleEnum.INTEGRAL_RECHARGE.getVal());
            pdsParam.setPointNum(param.getOutTradeNo());
            pdsParam.setUserNum(param.getUserNum());
            if (param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                pdsParam.setPointType(MemberTransactionTypeEnum.RECHARGE.getValue());
            } else if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
                pdsParam.setPointType(MerchantTransactionTypeEnum.RECHARGE.getValue());
            }
            pdsParam.setPoint(recharge.getMoney());
            pdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
            pdsParam.setRemark("");
            pointDetailService.save(pdsParam);

            //加积分
            PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
            infoDoView.setUserNum(param.getUserNum());
            infoDoView.setPoint(recharge.getMoney());
            infoDoView.setGmtModified(new Date());
            propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(infoDoView);
        }

        //更新充值表状态
        RechargeDO rechargeDO = new RechargeDO();
        rechargeDO.setStatus(ThirdPayStatusEnum.SUCCESS.getVal());
        rechargeDO.setThirdNumber(param.getTradeNo());
        rechargeDO.setGmtModified(new Date());
        RechargeDOExample example = new RechargeDOExample();
        example.createCriteria().andIdEqualTo(Long.valueOf(param.getBizIds()));
        rechargeDOMapper.updateByExampleSelective(rechargeDO, example);

        result.setRet(ResultCode.SUCCESS);

        return result;
    }

    @Override
    public ThirdPayCallBackQueryPayOrderDTO getRechargeMoney(String rechargeId) {
        RechargeDO recharge = rechargeDOMapper.selectByPrimaryKey(Long.valueOf(rechargeId));
        if (recharge == null) {
            return null;
        }
        ThirdPayCallBackQueryPayOrderDTO rechargeDTO = new ThirdPayCallBackQueryPayOrderDTO();
        rechargeDTO.setActualMoney(recharge.getRechargeMoney().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        rechargeDTO.setOrderNum(recharge.getRechargeNumber());
        return rechargeDTO;
    }

    @Override
    public Page<BalanceAndPointListQueryBO> selectPropertyinfoList(RechargeQueryDataParam dparam) {
        RechargeDOExample example = new RechargeDOExample();
        com.lawu.eshop.property.srv.domain.RechargeDOExample.Criteria criteria = example.createCriteria();
        if (dparam.getUserNum() != null && !"".equals(dparam.getUserNum())) {
            criteria.andUserNumEqualTo(dparam.getUserNum());
        }
        if (dparam.getStatus() != null) {
            criteria.andStatusEqualTo(dparam.getStatus().getVal());
        }
        if (dparam.getRechargeNumber() != null && !"".equals(dparam.getRechargeNumber())) {
            criteria.andRechargeNumberEqualTo(dparam.getRechargeNumber());
        }
        RowBounds rowBounds = new RowBounds(dparam.getOffset(), dparam.getPageSize());
        Page<BalanceAndPointListQueryBO> page = new Page<BalanceAndPointListQueryBO>();
        page.setTotalCount(rechargeDOMapper.countByExample(example));
        page.setCurrentPage(dparam.getCurrentPage());
        List<RechargeDO> rechargeDOS = rechargeDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<BalanceAndPointListQueryBO> cbos = new ArrayList<BalanceAndPointListQueryBO>();
        for (RechargeDO rdo : rechargeDOS) {
            BalanceAndPointListQueryBO bo = new BalanceAndPointListQueryBO();
            bo.setChannel(TransactionPayTypeEnum.getEnum(rdo.getChannel()));
            bo.setCurrentScale(rdo.getCurrentScale());
            bo.setGmtCreate(DateUtil.getDateFormat(rdo.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
            bo.setMoney(rdo.getMoney().toString());
            bo.setRechargeMoney(rdo.getRechargeMoney().toString());
            bo.setRechargeNumber(rdo.getRechargeNumber());
            bo.setRechargeType(PayTypeEnum.getEnum(rdo.getRechargeType()).getName());
            bo.setStatus(ThirdPayStatusEnum.getEnum(rdo.getStatus()));
            bo.setThirdNumber(rdo.getThirdNumber());
            cbos.add(bo);
        }
        page.setRecords(cbos);
        return page;
    }

    @Override
    public String getRechargePayType(Long id) {
        String payType = "";
        RechargeDO rechargeDO = rechargeDOMapper.selectByPrimaryKey(id);
        if (rechargeDO == null || rechargeDO.getChannel() == null) {
            return payType;
        }
        TransactionPayTypeEnum payTypeEnum = TransactionPayTypeEnum.getEnum(rechargeDO.getChannel());
        if (payTypeEnum == null) {
            return payType;
        }
        return payTypeEnum.getName();
    }

    @Override
    public List<RechargeReportBO> selectWithdrawCashListByDateAndStatus(RechargeReportParam param) {
        RechargeDOExample example = new RechargeDOExample();
        Date begin = DateUtil.formatDate(param.getDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.formatDate(param.getDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        example.createCriteria().andStatusEqualTo(param.getStatus()).andRechargeTypeEqualTo(param.getRechargeType()).andGmtModifiedBetween(begin, end);
        List<RechargeDO> rntList = rechargeDOMapper.selectByExample(example);
        List<RechargeReportBO> bos = new ArrayList<>();
        for (RechargeDO rdo : rntList) {
            RechargeReportBO bo = new RechargeReportBO();
            bo.setId(rdo.getId());
            bo.setRechargeMoney(rdo.getRechargeMoney());
            bo.setUserNum(rdo.getUserNum());
            bos.add(bo);
        }
        return bos;
    }

    @Override
    public ThirdPayStatusEnum getRechargeById(Long id) {
        RechargeDO recharge = rechargeDOMapper.selectByPrimaryKey(id);
        if (recharge == null) {
            return null;
        }
        return ThirdPayStatusEnum.getEnum(recharge.getStatus());
    }

    @Override
    public List<AgentReportRechargeQueryBO> selectAgentAreaReportRechargeListByDate(AgentReportRechargeQueryParam param) {
        ReportAreaRechargeDOView recharge = new ReportAreaRechargeDOView();
        recharge.setChannel(param.getChannel());
        recharge.setStatus(param.getStatus());
        recharge.setBeginDate(param.getDate() + " 00:00:00");
        recharge.setEndDate(param.getDate() + " 23:59:59");
        List<ReportAreaRechargeDOExtend> rtnList = rechargeDOMapperExtend.selectAgentAreaReportRechargeListByDate(recharge);
        List<AgentReportRechargeQueryBO> bos = new ArrayList<>();
        for(ReportAreaRechargeDOExtend doExtend : rtnList){
            AgentReportRechargeQueryBO bo = new AgentReportRechargeQueryBO();
            bo.setProvinceId(doExtend.getProvinceId());
            bo.setCityId(doExtend.getCityId());
            bo.setAreaId(doExtend.getAreaId());
            bo.setMemberRechargeBalance(doExtend.getMemberRechargeBalance());
            bo.setMemberRechargePoint(doExtend.getMemberRechargePoint());
            bo.setMerchantRechargeBalance(doExtend.getMerchantRechargeBalance());
            bo.setMerchantRechargePoint(doExtend.getMerchantRechargePoint());
            bo.setTotalRechargeBalance(doExtend.getMemberRechargeBalance().add(doExtend.getMerchantRechargeBalance()));
            bo.setTotalRechargePoint(doExtend.getMemberRechargePoint().add(doExtend.getMerchantRechargePoint()));
            bos.add(bo);
        }
        return bos;
    }

	@Override
	public List<AreaRechargePointBO> selectAreaRechargePoint(String bdate, String edate) {
		List<AreaRechargePointDOView> list = rechargeDOMapperExtend.selectAreaRechargePoint(bdate, edate);
		List<AreaRechargePointBO> rtnList = new ArrayList<AreaRechargePointBO>();
		if(list != null && !list.isEmpty()) {
			for(AreaRechargePointDOView view : list) {
				AreaRechargePointBO BO = new AreaRechargePointBO();
				BO.setAreaId(view.getAreaId());
				BO.setCityId(view.getCityId());
				BO.setProvinceId(view.getProvinceId());
				BO.setTotalMoney(view.getTotalMoney());
				BO.setType(view.getType());
				rtnList.add(BO);
			}
			
		}
		return rtnList;
	}
}
