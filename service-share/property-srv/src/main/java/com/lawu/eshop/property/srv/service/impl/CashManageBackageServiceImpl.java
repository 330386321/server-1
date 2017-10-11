package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.CashOperEnum;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.AgentWithdrawCashReportParam;
import com.lawu.eshop.property.param.CashBackageOperDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDetailParam;
import com.lawu.eshop.property.param.CashBackageQuerySumParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.param.WithdrawCashReportParam;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQueryBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQuerySumBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashReportBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDOExample;
import com.lawu.eshop.property.srv.domain.WithdrawCashDOExample.Criteria;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.domain.extend.WithdrawCashDOView;
import com.lawu.eshop.property.srv.domain.extend.WithdrawCashOperDOView;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.WithdrawCashDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.mapper.extend.WithdrawCashDOMapperExtend;
import com.lawu.eshop.property.srv.service.CashManageBackageService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;

@Service
public class CashManageBackageServiceImpl implements CashManageBackageService {

    @Autowired
    private WithdrawCashDOMapper withdrawCashDOMapper;
    @Autowired
    private BankAccountDOMapper bankAccountDOMapper;
    @Autowired
    private WithdrawCashDOMapperExtend withdrawCashDOMapperExtend;
    @Autowired
    private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private PropertyService propertyService;

    @Override
    public Page<WithdrawCashBackageQueryBO> findCashInfo(CashBackageQueryDataParam param) {
        WithdrawCashDOExample example = new WithdrawCashDOExample();

        Criteria criteria1 = example.createCriteria();
        criteria1.andUserTypeEqualTo(param.getUserTypeEnum().getVal());
        if (StringUtils.isNotEmpty(param.getBeginDate())) {
            criteria1.andGmtCreateGreaterThanOrEqualTo(DateUtil.stringToDate(param.getBeginDate() + " 00:00:00"));
        }
        if (StringUtils.isNotEmpty(param.getEndDate())) {
            criteria1.andGmtCreateLessThanOrEqualTo(DateUtil.stringToDate(param.getEndDate() + " 23:59:59"));
        }
        if (!CashStatusEnum.ALL.getVal().equals(param.getCashStatsuEnum().getVal())) {
            criteria1.andStatusEqualTo(param.getCashStatsuEnum().getVal());
        }

        if (param.getRegionPath() != null && !"".equals(param.getRegionPath())) {
            if (param.getRegionPath().split("/").length == 1) {
                criteria1.andProvinceIdEqualTo(Integer.valueOf(param.getRegionPath().split("/")[0]));
            } else if (param.getRegionPath().split("/").length == 2) {
                criteria1.andCityIdEqualTo(Integer.valueOf(param.getRegionPath().split("/")[1]));
            } else if (param.getRegionPath().split("/").length == 3) {
                criteria1.andAreaIdEqualTo(Integer.valueOf(param.getRegionPath().split("/")[2]));
            }
        }
        example.clear();
        if (param.getContent() != null && !"".equals(param.getContent().trim())) {
            Criteria criteria2 = example.or();
            criteria2.andAccountEqualTo(param.getContent());
            criteria2.getAllCriteria().addAll(criteria1.getAllCriteria());

            Criteria criteria3 = example.or();
            criteria3.andCashNumberEqualTo(param.getContent());
            criteria3.getAllCriteria().addAll(criteria1.getAllCriteria());

            Criteria criteria4 = example.or();
            criteria4.andNameLike("%" + param.getContent() + "%");
            criteria4.getAllCriteria().addAll(criteria1.getAllCriteria());
        }


        if (StringUtils.isNotBlank(param.getSortName()) && StringUtils.isNotBlank(param.getSortOrder())) {
            String sortName = "";
            if ("gmtCreate".equals(param.getSortName())) {
                sortName = "gmt_create";
            }
            example.setOrderByClause(sortName + " " + param.getSortOrder());
        }

        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        Page<WithdrawCashBackageQueryBO> page = new Page<WithdrawCashBackageQueryBO>();
        page.setTotalCount(withdrawCashDOMapper.countByExample(example));
        page.setCurrentPage(param.getCurrentPage());
        List<WithdrawCashDO> listDOS = withdrawCashDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<WithdrawCashBackageQueryBO> cbos = new ArrayList<WithdrawCashBackageQueryBO>();
        for (WithdrawCashDO cdo : listDOS) {
            WithdrawCashBackageQueryBO bqbo = new WithdrawCashBackageQueryBO();
            bqbo.setId(cdo.getId());
            bqbo.setUserNum(cdo.getUserNum());
            bqbo.setAccount(cdo.getAccount());
            bqbo.setName(cdo.getName());
            bqbo.setRegionFullName(cdo.getRegionFullName());
            bqbo.setStatus(CashStatusEnum.getEnum(cdo.getStatus()).getName());
            bqbo.setCashStatsuEnum(CashStatusEnum.getEnum(cdo.getStatus()));
            BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(cdo.getBusinessBankAccountId());
            bqbo.setBusinessBankAccount(bankAccountDO.getAccountName());
            bqbo.setBankNo(bankAccountDO.getAccountNumber());
            bqbo.setBankName(bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf('(')));
            bqbo.setBankBranchName(bankAccountDO.getSubBranchName());

            bqbo.setCashNumber(cdo.getCashNumber());
            bqbo.setAuditUserName(cdo.getAuditUserName() == null ? "" : cdo.getAuditUserName());
            bqbo.setAuditFaildReason(cdo.getAuditFaildReason() == null ? "" : cdo.getAuditFaildReason());
            bqbo.setGmtCreate(DateUtil.getDateFormat(cdo.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
            if (cdo.getGmtModified() != null) {
                bqbo.setGmtModified(DateUtil.getDateFormat(cdo.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                bqbo.setGmtModified("");
            }
            bqbo.setCashMoney(cdo.getCashMoney());
            if (CashStatusEnum.APPLY.getVal().equals(cdo.getStatus()) || CashStatusEnum.FAILURE.getVal().equals(cdo.getStatus())) {
                bqbo.setPoundage(new BigDecimal("0"));
                bqbo.setMoney(new BigDecimal("0"));
            } else {
                bqbo.setPoundage(cdo.getCashMoney().subtract(cdo.getMoney()));
                bqbo.setMoney(cdo.getMoney());
            }
            cbos.add(bqbo);
        }
        page.setRecords(cbos);
        return page;
    }

    @Override
    public WithdrawCashBackageQuerySumBO getTotalNum(CashBackageQuerySumParam param) {
        WithdrawCashDOView view = new WithdrawCashDOView();
        view.setUserType(param.getUserTypeEnum().getVal());
        view.setStatus(CashStatusEnum.SUCCESS.getVal());
        view = withdrawCashDOMapperExtend.getTotalNum(view);

        WithdrawCashBackageQuerySumBO bo = new WithdrawCashBackageQuerySumBO();
        bo.setSuccessMoney(view.getSuccessMoney());
        bo.setSuccessNums(view.getSuccessNums());
        return bo;
    }

    @Override
    public Page<WithdrawCashBackageQueryBO> findCashInfoDetail(CashBackageQueryDetailParam param) {
        WithdrawCashDOExample example = new WithdrawCashDOExample();

        Criteria criteria1 = example.createCriteria();
        criteria1.andUserNumEqualTo(param.getUserNum());

        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        Page<WithdrawCashBackageQueryBO> page = new Page<WithdrawCashBackageQueryBO>();
        page.setTotalCount(withdrawCashDOMapper.countByExample(example));
        page.setCurrentPage(param.getCurrentPage());
        List<WithdrawCashDO> listDOS = withdrawCashDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<WithdrawCashBackageQueryBO> cbos = new ArrayList<WithdrawCashBackageQueryBO>();
        for (WithdrawCashDO cdo : listDOS) {
            WithdrawCashBackageQueryBO bqbo = new WithdrawCashBackageQueryBO();
            bqbo.setId(cdo.getId());
            bqbo.setUserNum(cdo.getUserNum());
            bqbo.setAccount(cdo.getAccount());
            bqbo.setName(cdo.getName());
            bqbo.setRegionFullName(cdo.getRegionFullName());
            bqbo.setStatus(CashStatusEnum.getEnum(cdo.getStatus()).getName());
            bqbo.setCashStatsuEnum(CashStatusEnum.getEnum(cdo.getStatus()));
            BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(cdo.getBusinessBankAccountId());
            bqbo.setBusinessBankAccount(bankAccountDO.getAccountName());
            bqbo.setBankNo(bankAccountDO.getAccountNumber());
            bqbo.setBankName(bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf('(')));
            bqbo.setBankBranchName(bankAccountDO.getSubBranchName());

            bqbo.setCashNumber(cdo.getCashNumber());
            bqbo.setAuditUserName(cdo.getAuditUserName() == null ? "" : cdo.getAuditUserName());
            bqbo.setAuditFaildReason(cdo.getAuditFaildReason() == null ? "" : cdo.getAuditFaildReason());
            bqbo.setGmtCreate(DateUtil.getDateFormat(cdo.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
            if (cdo.getGmtModified() != null) {
                bqbo.setGmtModified(DateUtil.getDateFormat(cdo.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                bqbo.setGmtModified("");
            }
            bqbo.setMoney(cdo.getMoney());
            cbos.add(bqbo);
        }
        page.setRecords(cbos);
        return page;
    }

    @Override
    @Transactional
    public int updateWithdrawCash(CashBackageOperDataParam param) {

        // 批量修改提现表状态
        List<WithdrawCashOperDOView> paramList = new ArrayList<WithdrawCashOperDOView>();
        String ids = param.getId();
        String idsArray[] = ids.split(",");
        String currentScale = propertyService.getValue(PropertyType.CASH_SCALE);
        double dCurrentScale = Double.parseDouble(currentScale);
        for (int i = 0; i < idsArray.length; i++) {

            WithdrawCashDO wcdo = withdrawCashDOMapper.selectByPrimaryKey(Long.valueOf(idsArray[i]));
            if ((CashStatusEnum.ACCEPT.getVal().equals(param.getCashOperEnum().getVal()) && !CashStatusEnum.APPLY.getVal().equals(wcdo.getStatus()))
                    || (!CashStatusEnum.ACCEPT.getVal().equals(param.getCashOperEnum().getVal()) && !CashStatusEnum.ACCEPT.getVal().equals(wcdo.getStatus()))) {
                return ResultCode.REPEAT_OPERATE;
            }

            paramList.clear();
            WithdrawCashOperDOView view = new WithdrawCashOperDOView();

            //受理操作时计算手续费
            if (CashStatusEnum.ACCEPT.getVal().equals(param.getCashOperEnum().getVal())) {
                WithdrawCashDOExample example = new WithdrawCashDOExample();
                example.createCriteria().andUserNumEqualTo(wcdo.getUserNum()).andStatusEqualTo(CashStatusEnum.SUCCESS.getVal()).andGmtCreateGreaterThanOrEqualTo(DateUtil.formatDate(DateUtil.getDateFormat(new Date(), "yyyy-MM") + "-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
                int count = withdrawCashDOMapper.countByExample(example);
                double dCashMoney = wcdo.getCashMoney().doubleValue();
                double money = 0;
                if (count > 0) {
                    String minusMoney = propertyService.getValue(PropertyType.CASH_GREATER_ONE_MINUS_MONEY);
                    money = dCashMoney * dCurrentScale - Double.parseDouble(minusMoney);
                } else {
                    money = dCashMoney * dCurrentScale;
                }
                view.setCurrentScale(currentScale);
                view.setMoney(BigDecimal.valueOf(money));
            }

            view.setId(Integer.valueOf(idsArray[i]));
            view.setStatus(param.getCashOperEnum().getVal());
            view.setAuditFailReason(param.getFailReason() == null ? "" : param.getFailReason());
            view.setAuditUserId(param.getAuditUserId() == null ? 0 : param.getAuditUserId());
            view.setAuditUserName(param.getAuditUserName() == null ? "" : param.getAuditUserName());
            view.setGmtModified(new Date());
            if (CashOperEnum.ACCEPT.getVal().equals(param.getCashOperEnum().getVal())) {
                view.setGmtAccept(new Date());
            } else {
                view.setGmtFinish(new Date());
            }
            paramList.add(view);
            withdrawCashDOMapperExtend.updateBatchWithdrawCashStatus(paramList);
        }
        if (!CashStatusEnum.FAILURE.getVal().equals(param.getCashOperEnum().getVal())) {
            return ResultCode.SUCCESS;
        }

        // 失败的情况要回滚数据
        for (int i = 0; i < idsArray.length; i++) {

            WithdrawCashDO wcdo = withdrawCashDOMapper.selectByPrimaryKey(Long.valueOf(idsArray[i]));

            // 新增交易明细
            TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
            tdsParam.setTitle(TransactionTitleEnum.CASH_FAIL_BACK.getVal());
            tdsParam.setTransactionNum(StringUtil.getRandomNum(""));
            tdsParam.setUserNum(wcdo.getUserNum());
            if (wcdo.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                tdsParam.setTransactionType(MemberTransactionTypeEnum.WITHDRAW_BACK.getValue());
            } else if (wcdo.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
                tdsParam.setTransactionType(MerchantTransactionTypeEnum.WITHDRAW_BACK.getValue());
            }
            tdsParam.setTransactionAccount(wcdo.getAccount());
            tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
            tdsParam.setAmount(wcdo.getCashMoney());
            tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
            tdsParam.setBizId(wcdo.getId() == null ? "" : wcdo.getId().toString());
            transactionDetailService.save(tdsParam);

            PropertyInfoDOEiditView infoView = new PropertyInfoDOEiditView();
            infoView.setBalance(wcdo.getCashMoney());
            infoView.setUserNum(wcdo.getUserNum());
            infoView.setGmtModified(new Date());
            propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoView);
        }

        return ResultCode.SUCCESS;
    }

    @Override
    public List<WithdrawCashReportBO> selectWithdrawCashListByDateAndStatus(WithdrawCashReportParam param) {
        WithdrawCashDOExample example = new WithdrawCashDOExample();
        Date begin = DateUtil.formatDate(param.getDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.formatDate(param.getDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        example.createCriteria().andStatusEqualTo(param.getStatus()).andGmtFinishBetween(begin, end);
        List<WithdrawCashDO> rntList = withdrawCashDOMapper.selectByExample(example);
        List<WithdrawCashReportBO> wrbs = new ArrayList<WithdrawCashReportBO>();
        for (WithdrawCashDO cdo : rntList) {
            WithdrawCashReportBO wrb = new WithdrawCashReportBO();
            wrb.setId(cdo.getId());
            wrb.setUserNum(cdo.getUserNum());
            wrb.setFinishDate(DateUtil.getDateFormat(cdo.getGmtFinish(), "yyyy-MM-dd"));
            wrb.setCashMoney(cdo.getCashMoney());
            wrbs.add(wrb);
        }
        return wrbs;
    }

    @Override
    public List<WithdrawCashReportBO> selectAgentWithdrawCashList(AgentWithdrawCashReportParam param) {
        WithdrawCashDOExample example = new WithdrawCashDOExample();
        Date begin = DateUtil.formatDate(param.getDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.formatDate(param.getDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        example.createCriteria().andStatusEqualTo(param.getStatus()).andGmtFinishBetween(begin, end).andCityIdEqualTo(param.getCityId());
        List<WithdrawCashDO> rntList = withdrawCashDOMapper.selectByExample(example);
        List<WithdrawCashReportBO> wrbs = new ArrayList<>();
        WithdrawCashReportBO wrb;
        for (WithdrawCashDO cdo : rntList) {
            wrb = new WithdrawCashReportBO();
            wrb.setId(cdo.getId());
            wrb.setUserNum(cdo.getUserNum());
            wrb.setFinishDate(DateUtil.getDateFormat(cdo.getGmtFinish(), "yyyy-MM-dd"));
            wrb.setCashMoney(cdo.getCashMoney());
            wrbs.add(wrb);
        }
        return wrbs;
    }

}
