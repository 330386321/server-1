package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.RefundDepositDoSuccessOrFailureNotification;
import com.lawu.eshop.mq.dto.user.HandleDepostMessage;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.property.constants.*;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.property.srv.bo.BusinessDepositDetailBO;
import com.lawu.eshop.property.srv.bo.BusinessDepositQueryBO;
import com.lawu.eshop.property.srv.domain.*;
import com.lawu.eshop.property.srv.domain.BusinessDepositDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BusinessDepositDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.BusinessDepositService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessDepositServiceImpl implements BusinessDepositService {

    private static Logger logger = LoggerFactory.getLogger(BusinessDepositServiceImpl.class);

    @Autowired
    private BusinessDepositDOMapper businessDepositDOMapper;

    @Autowired
    private BankAccountDOMapper bankAccountDOMapper;

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;

    @Autowired
    @Qualifier("handleDepositEditStoreStatusTransactionMainServiceImpl")
    private TransactionMainService<Reply> handleDepositEditStoreStatusTransactionMainServiceImpl;

    @Autowired
    @Qualifier("handleDepositAuditPassTransactionMainServiceImpl")
    private TransactionMainService<Reply> handleDepositAuditPassTransactionMainServiceImpl;

    @Autowired
    @Qualifier("handleDepositAuditCancelTransactionMainServiceImpl")
    private TransactionMainService<Reply> handleDepositAuditCancelTransactionMainServiceImpl;

    @Autowired
    @Qualifier("handleDepositRefundSuccessDownProductTransactionMainServiceImpl")
    private TransactionMainService<Reply> handleDepositRefundSuccessDownProductTransactionMainServiceImpl;

    @Autowired
    private MessageProducerService messageProducerService;


    @Override
    @Transactional
    public BusinessDepositInitDTO save(BusinessDepositSaveDataParam param) {
        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setBusinessId(Long.valueOf(param.getBusinessId()));
        bddo.setUserNum(param.getUserNum());
        bddo.setBusinessAccount(param.getBusinessAccount());
        bddo.setBusinessName(param.getBusinessName());
        bddo.setDepositNumber(StringUtil.getRandomNum(""));
        bddo.setAmount(new BigDecimal(param.getDeposit()));
        bddo.setStatus(BusinessDepositStatusEnum.PAYING.getVal());
        bddo.setProvinceId(Long.valueOf(param.getProvinceId()));
        bddo.setCityId(Long.valueOf(param.getCityId()));
        bddo.setAreaId(Long.valueOf(param.getAreaId()));
        bddo.setGmtCreate(new Date());
        businessDepositDOMapper.insertSelective(bddo);

        BusinessDepositInitDTO dto = new BusinessDepositInitDTO();
        dto.setId(bddo.getId());
        dto.setDeposit(param.getDeposit());
        return dto;
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public Result doHandleDepositNotify(NotifyCallBackParam param) {
        Result result = new Result();
        BusinessDepositDO deposit = businessDepositDOMapper.selectByPrimaryKey(Long.valueOf(param.getBizIds()));
        if (deposit == null) {
            result.setRet(ResultCode.FAIL);
            result.setMsg("保证金初始记录为空");
            return result;
        } else {
            if (!BusinessDepositStatusEnum.PAYING.getVal().equals(deposit.getStatus())) {
                result.setRet(ResultCode.SUCCESS);
                logger.info("保证金重复回调(判断幂等)");
                return result;
            }
        }
        BigDecimal dbMoney = deposit.getAmount();
        BigDecimal backMoney = new BigDecimal(param.getTotalFee());
        if (backMoney.compareTo(dbMoney) != 0) {
            result.setRet(ResultCode.NOTIFY_MONEY_ERROR);
            result.setMsg("保证金回调金额与保证金表保存的金额不一致(回调金额：" + backMoney + ",表中金额:" + dbMoney + ")");
            return result;
        }

        // 新增交易明细
//		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
//		tdsParam.setTitle(TransactionTitleEnum.DEPOSIT.val);
//		tdsParam.setTransactionNum(param.getOutTradeNo());
//		tdsParam.setUserNum(param.getUserNum());
//		tdsParam.setTransactionAccount(param.getBuyerLogonId());
//		tdsParam.setTransactionType(MerchantTransactionTypeEnum.DEPOSIT.getValue());
//		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().getVal());
//		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
//		tdsParam.setBizId(param.getBizIds());
//		tdsParam.setThirdTransactionNum(param.getTradeNo());
//		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
//		transactionDetailService.save(tdsParam);

        // 更新保证金记录状态
        BusinessDepositDO depositDO = new BusinessDepositDO();
        depositDO.setThirdNumber(param.getTradeNo());
        depositDO.setThirdAccount(param.getBuyerLogonId());
        depositDO.setPaymentMethod(param.getTransactionPayTypeEnum().getVal());
        depositDO.setStatus(BusinessDepositStatusEnum.VERIFY.getVal());
        depositDO.setGmtPay(new Date());
        depositDO.setGmtModified(new Date());
        BusinessDepositDOExample example = new BusinessDepositDOExample();
        example.createCriteria().andIdEqualTo(Long.valueOf(param.getBizIds())).andStatusEqualTo(BusinessDepositStatusEnum.PAYING.getVal());
        businessDepositDOMapper.updateByExampleSelective(depositDO, example);

        // 回调成功后，发送消息修改门店状态为：已缴保证金待核实
        handleDepositEditStoreStatusTransactionMainServiceImpl.sendNotice(Long.valueOf(param.getBizIds()));

        result.setRet(ResultCode.SUCCESS);
        return result;
    }

    @Override
    public Page<BusinessDepositQueryBO> selectDepositList(BusinessDepositQueryDataParam param) {
        BusinessDepositDOExample example = new BusinessDepositDOExample();

        if (param.getContent() != null && !"".equals(param.getContent().trim())) {
            Criteria criteria2 = example.createCriteria();
            criteria2.andDepositNumberEqualTo(param.getContent());

            Criteria criteria3 = example.createCriteria();
            criteria3.andThirdNumberEqualTo(param.getContent());

            example.or(criteria2);
            example.or(criteria3);

        } else {
            Criteria criteria1 = example.createCriteria();
            if (StringUtils.isNotEmpty(param.getBeginDate())) {
                criteria1.andGmtPayGreaterThanOrEqualTo(DateUtil.stringToDate(param.getBeginDate() + " 00:00:00"));
            }
            if (StringUtils.isNotEmpty(param.getEndDate())) {
                criteria1.andGmtPayLessThanOrEqualTo(DateUtil.stringToDate(param.getEndDate() + " 23:59:59"));
            }
            if (param.getBusinessDepositStatusEnum() != null) {
                criteria1.andStatusEqualTo(param.getBusinessDepositStatusEnum().getVal());
            } else {
                criteria1.andStatusGreaterThan(BusinessDepositStatusEnum.VERIFY.getVal());
            }

            if (param.getTransactionPayTypeEnum() != null) {
                criteria1.andPaymentMethodEqualTo(param.getTransactionPayTypeEnum().getVal());
            }
            if (param.getRegionPath() != null && !"".equals(param.getRegionPath())) {
                if (param.getRegionPath().split("/").length == 1) {
                    criteria1.andProvinceIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[0]));
                } else if (param.getRegionPath().split("/").length == 2) {
                    criteria1.andCityIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[1]));
                } else if (param.getRegionPath().split("/").length == 3) {
                    criteria1.andAreaIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[2]));
                }
            }

            if (StringUtils.isNotBlank(param.getSortName()) && StringUtils.isNotBlank(param.getSortOrder())) {
                String sortName = "";
                if ("gmtPay".equals(param.getSortName())) {
                    sortName = "gmt_pay";
                }
                example.setOrderByClause(sortName + " " + param.getSortOrder());
            }
        }

        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        Page<BusinessDepositQueryBO> page = new Page<BusinessDepositQueryBO>();
        page.setTotalCount(businessDepositDOMapper.countByExample(example));
        page.setCurrentPage(param.getCurrentPage());
        List<BusinessDepositDO> bddos = businessDepositDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<BusinessDepositQueryBO> newList = new ArrayList<BusinessDepositQueryBO>();
        for (BusinessDepositDO bddo : bddos) {
            BusinessDepositQueryBO ddqbo = new BusinessDepositQueryBO();
            ddqbo.setId(bddo.getId());
            ddqbo.setBusinessId(bddo.getBusinessId());
            ddqbo.setGmtPay(
                    bddo.getGmtPay() == null ? "" : DateUtil.getDateFormat(bddo.getGmtPay(), "yyyy-MM-dd HH:mm:ss"));
            ddqbo.setThirdNumber(bddo.getThirdNumber() == null ? "" : bddo.getThirdNumber());
            ddqbo.setBusinessAccount(bddo.getBusinessAccount() == null ? "" : bddo.getBusinessAccount());
            ddqbo.setDepositNumber(bddo.getDepositNumber() == null ? "" : bddo.getDepositNumber());
            ddqbo.setBusinessName(bddo.getBusinessName() == null ? "" : bddo.getBusinessName());
            ddqbo.setAmount(bddo.getAmount() == null ? new BigDecimal("0") : bddo.getAmount());
            ddqbo.setPayMethod(TransactionPayTypeEnum.getEnum(bddo.getPaymentMethod()).getName());
            ddqbo.setStatus(BusinessDepositStatusEnum.getEnum(bddo.getStatus()).getName());
            ddqbo.setBusinessDepositStatusEnum(BusinessDepositStatusEnum.getEnum(bddo.getStatus()));
            ddqbo.setUserNum(bddo.getUserNum());
            if (bddo.getBusinessBankAccountId() != null) {
                BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(bddo.getBusinessBankAccountId());
                ddqbo.setBusinessBankAccount(bankAccountDO.getAccountName() == null ? "" : bankAccountDO.getAccountName());
                ddqbo.setBankNo(bankAccountDO.getAccountNumber() == null ? "" : bankAccountDO.getAccountNumber());
                ddqbo.setBankName(bankAccountDO.getNote() == null ? ""
                        : bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf('(')));
                ddqbo.setBankBranchName(bankAccountDO.getSubBranchName() == null ? "" : bankAccountDO.getSubBranchName());
            } else {
                ddqbo.setBusinessBankAccount("");
                ddqbo.setBankNo("");
                ddqbo.setBankName("");
                ddqbo.setBankBranchName("");
            }

            ddqbo.setAuditUserName(bddo.getOperUserName() == null ? "" : bddo.getOperUserName());
            ddqbo.setRemark(bddo.getRemark() == null ? "" : bddo.getRemark());
            newList.add(ddqbo);
        }
        page.setRecords(newList);
        return page;
    }

    @Override
    @Transactional
    public int updateBusinessDeposit(BusinessDepositOperDataParam param) {
        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setStatus(param.getBusinessDepositOperEnum().getVal());
        bddo.setOperUserId(param.getOperUserId());
        bddo.setOperUserName(param.getOperUserName());
        bddo.setRemark(param.getFailReason() == null ? "" : param.getFailReason());
        bddo.setGmtModified(new Date());
        if (BusinessDepositOperEnum.VERIFYD.getVal().equals(param.getBusinessDepositOperEnum().getVal())) {
            bddo.setGmtVerify(new Date());
        } else if (BusinessDepositOperEnum.ACCPET_REFUND.getVal().equals(param.getBusinessDepositOperEnum().getVal())) {
            bddo.setGmtAccpet(new Date());
        }
        BusinessDepositDOExample example = new BusinessDepositDOExample();
        example.createCriteria().andIdEqualTo(Long.valueOf(param.getId()));
        businessDepositDOMapper.updateByExampleSelective(bddo, example);

        HandleDepostMessage message = new HandleDepostMessage();
        message.setUserNum(param.getUserNum());
        if (BusinessDepositOperEnum.VERIFYD.getVal().equals(param.getBusinessDepositOperEnum().getVal())) {
            // 核实操作成功后，发送MQ消息修改门店状态为：待审核,并修改门店审核显示状态
            handleDepositAuditPassTransactionMainServiceImpl.sendNotice(Long.valueOf(param.getId()));

        } else if (BusinessDepositOperEnum.REFUND_SUCCESS.getVal().equals(param.getBusinessDepositOperEnum().getVal())) {
            // 退款成功操作后，发送MQ消息修改门店状态为：注销
            handleDepositAuditCancelTransactionMainServiceImpl.sendNotice(Long.valueOf(param.getId()));
            //修改商品下架
            handleDepositRefundSuccessDownProductTransactionMainServiceImpl.sendNotice(Long.valueOf(param.getId()));
        }

        // 审核成功或失败后发送消息通知mall-srv模块发送推送消息给商家
        if (BusinessDepositOperEnum.REFUND_SUCCESS.getVal().equals(param.getBusinessDepositOperEnum().getVal()) || BusinessDepositOperEnum.REFUND_FAILURE.getVal().equals(param.getBusinessDepositOperEnum().getVal())) {
            RefundDepositDoSuccessOrFailureNotification notification = new  RefundDepositDoSuccessOrFailureNotification();
            notification.setDepositId(Long.valueOf(param.getId()));
            notification.setFailureReason(param.getFailReason() == null ? "" : param.getFailReason());
            notification.setMerchantNum(param.getUserNum());
            notification.setDepositOperEnumVal(param.getBusinessDepositOperEnum().getVal());
            messageProducerService.sendMessage(MqConstant.TOPIC_PROPERTY_SRV, MqConstant.TAG_PROPERTY_DEPOSIT_DO_RESULT, notification);
        }

        return ResultCode.SUCCESS;
    }

    @Override
    @Transactional
    public int refundDeposit(BusinessRefundDepositDataParam dparam) {

        // 校验支付密码
        PropertyInfoDOExample infoExample = new PropertyInfoDOExample();
        infoExample.createCriteria().andUserNumEqualTo(dparam.getUserNum());
        List<PropertyInfoDO> infoList = propertyInfoDOMapper.selectByExample(infoExample);
        if (infoList == null || infoList.isEmpty()) {
            return ResultCode.PROPERTY_INFO_NULL;
        } else if (infoList.size() > 1) {
            return ResultCode.PROPERTY_INFO_OUT_INDEX;
        } else {
            PropertyInfoDO info = infoList.get(0);
            if (!PwdUtil.verify(dparam.getPayPwd(), info.getPayPassword())) {
                return ResultCode.PAY_PWD_ERROR;
            }
        }

        // 满90天
        BusinessDepositDO deposit = businessDepositDOMapper.selectByPrimaryKey(Long.valueOf(dparam.getId()));
        int diffDays = DateUtil.daysOfTwo(deposit.getGmtVerify() == null ? deposit.getGmtModified() : deposit.getGmtVerify(), new Date());
        String sysDays = propertyService.getValue(PropertyType.DEPOSIT_REFUND_DIFF_DAYS);
        if (diffDays <= Integer.parseInt(sysDays)) {
            return ResultCode.DEPOSIT_IN_SYSTEM_DAYS;
        }

        // 校验提交的银行卡是否正确
        BankAccountDO bankAccountDo = bankAccountDOMapper.selectByPrimaryKey(Long.valueOf(dparam.getBusinessBankAccountId()));
        if (bankAccountDo == null) {
            return ResultCode.PROPERTY_CASH_BANK_NOT_EXIST;
        } else if (!bankAccountDo.getUserNum().trim().equals(dparam.getUserNum())) {
            return ResultCode.PROPERTY_CASH_BANK_NOT_MATCH;
        }

        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setStatus(BusinessDepositStatusEnum.APPLY_REFUND.getVal());
        bddo.setBusinessBankAccountId(Long.valueOf(dparam.getBusinessBankAccountId()));
        bddo.setGmtRefund(new Date());
        BusinessDepositDOExample example = new BusinessDepositDOExample();
        example.createCriteria().andIdEqualTo(Long.valueOf(dparam.getId()));
        businessDepositDOMapper.updateByExampleSelective(bddo, example);
        return ResultCode.SUCCESS;
    }

    @Override
    public BusinessDepositDetailBO selectDeposit(String businessId) {
        BusinessDepositDOExample example = new BusinessDepositDOExample();
        example.createCriteria().andBusinessIdEqualTo(Long.valueOf(businessId));
        example.setOrderByClause(" id desc ");
        List<BusinessDepositDO> list = businessDepositDOMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        BusinessDepositDetailBO bo = new BusinessDepositDetailBO();
        bo.setId(list.get(0).getId());
        bo.setAmount(list.get(0).getAmount().toString());
        bo.setBusinessDepositStatusEnum(BusinessDepositStatusEnum.getEnum(list.get(0).getStatus()));
        bo.setRemark(list.get(0).getRemark());
        bo.setGmtPay(list.get(0).getGmtPay());
        bo.setGmtVerify(list.get(0).getGmtVerify());
        bo.setGmtRefund(list.get(0).getGmtRefund());
        bo.setGmtAccpet(list.get(0).getGmtAccpet());
        bo.setGmtResult(list.get(0).getGmtModified());

        if (list.get(0).getBusinessBankAccountId() != null) {
            BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(list.get(0).getBusinessBankAccountId());
            if (bankAccountDO != null) {
                bo.setBankName(bankAccountDO.getNote() == null ? ""
                        : bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf('(')));
                String accountName = bankAccountDO.getAccountName() == null ? "" : bankAccountDO.getAccountName();
                if (accountName.length() == 2) {
                    accountName = "*" + accountName.substring(1);
                } else if (accountName.length() > 2) {
                    accountName = accountName.substring(0, 1) + "*" + accountName.substring(accountName.length() - 1);
                }
                bo.setAccountName(accountName);
                bo.setCardNo(bankAccountDO.getNote() == null ? ""
                        : bankAccountDO.getNote().substring(bankAccountDO.getNote().indexOf('(') + 1,
                        bankAccountDO.getNote().indexOf(')')));
            } else {
                bo.setBankName("");
                bo.setAccountName("");
                bo.setCardNo("");
            }
        } else {
            bo.setBankName("");
            bo.setAccountName("");
            bo.setCardNo("");
        }

        return bo;
    }

}
