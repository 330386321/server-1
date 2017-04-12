package com.lawu.eshop.property.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitle;
import com.lawu.eshop.property.param.CashBackageOperDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDetailParam;
import com.lawu.eshop.property.param.CashBackageQuerySumParam;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQueryBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQuerySumBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDOExample;
import com.lawu.eshop.property.srv.domain.WithdrawCashDOExample.Criteria;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.domain.extend.WithdrawCashDOView;
import com.lawu.eshop.property.srv.domain.extend.WithdrawCashOperDOView;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.WithdrawCashDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.mapper.extend.WithdrawCashDOMapperExtend;
import com.lawu.eshop.property.srv.service.CashManageBackageService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.BeanUtil;
import com.lawu.eshop.utils.DateUtil;

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
	private TransactionDetailDOMapper transactionDetailDOMapper;

	@Override
	public Page<WithdrawCashBackageQueryBO> findCashInfo(CashBackageQueryDataParam param) {
		WithdrawCashDOExample example = new WithdrawCashDOExample();

		Criteria criteria1 = example.createCriteria();
		criteria1.andUserTypeEqualTo(param.getUserTypeEnum().val)
				.andGmtCreateBetween(param.getBeginDate(), param.getEndDate())
				.andStatusEqualTo(param.getCashStatsuEnum().val);
		if (param.getRegionPath() != null && !"".equals(param.getRegionPath())) {
			if (param.getRegionPath().split("/").length == 1) {
				criteria1.andProvinceIdEqualTo(Integer.valueOf(param.getRegionPath().split("/")[0]));
			} else if (param.getRegionPath().split("/").length == 2) {
				criteria1.andProvinceIdEqualTo(Integer.valueOf(param.getRegionPath().split("/")[1]));
			} else if (param.getRegionPath().split("/").length == 3) {
				criteria1.andProvinceIdEqualTo(Integer.valueOf(param.getRegionPath().split("/")[2]));
			}
		}

		if (param.getContent() != null && !"".equals(param.getContent().trim())) {
			Criteria criteria2 = example.createCriteria();
			criteria2.andAccountEqualTo(param.getContent());

			Criteria criteria3 = example.createCriteria();
			criteria3.andCashNumberEqualTo(param.getContent());

			Criteria criteria4 = example.createCriteria();
			criteria4.andNameLike("%" + param.getContent() + "%");

			example.or(criteria2);
			example.or(criteria3);
			example.or(criteria4);
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
			;
			if (CashStatusEnum.APPLY.val.equals(cdo.getStatus())) {
				bqbo.setStatus("申请中");
			} else if (CashStatusEnum.ACCEPT.val.equals(cdo.getStatus())) {
				bqbo.setStatus("受理中");
			} else if (CashStatusEnum.SUCCESS.val.equals(cdo.getStatus())) {
				bqbo.setStatus("成功");
			} else if (CashStatusEnum.FAILURE.val.equals(cdo.getStatus())) {
				bqbo.setStatus("失败");
			}

			BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(cdo.getBusinessBankAccountId());
			bqbo.setBusinessBankAccount(bankAccountDO.getAccountName());
			bqbo.setBankNo(bankAccountDO.getAccountNumber());
			bqbo.setBankName(bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf("(")));
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
	public WithdrawCashBackageQuerySumBO getTotalNum(CashBackageQuerySumParam param) throws Exception {
		WithdrawCashDOView view = new WithdrawCashDOView();
		view.setUserType(param.getUserTypeEnum().val);
		view = withdrawCashDOMapperExtend.getTotalNum(view);

		WithdrawCashBackageQuerySumBO bo = new WithdrawCashBackageQuerySumBO();
		BeanUtil.copyProperties(view, bo);
		return bo;
	}

	@Override
	public Page<WithdrawCashBackageQueryBO> findCashInfoDetail(CashBackageQueryDetailParam param) {
		WithdrawCashDOExample example = new WithdrawCashDOExample();

		Criteria criteria1 = example.createCriteria();
		criteria1.andUserTypeEqualTo(param.getUserTypeEnum().val).andAccountEqualTo(param.getAccount());

		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		Page<WithdrawCashBackageQueryBO> page = new Page<WithdrawCashBackageQueryBO>();
		page.setTotalCount(withdrawCashDOMapper.countByExample(example));
		page.setCurrentPage(param.getCurrentPage());
		List<WithdrawCashDO> listDOS = withdrawCashDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<WithdrawCashBackageQueryBO> cbos = new ArrayList<WithdrawCashBackageQueryBO>();
		for (WithdrawCashDO cdo : listDOS) {
			WithdrawCashBackageQueryBO bqbo = new WithdrawCashBackageQueryBO();
			bqbo.setId(cdo.getId());
			bqbo.setAccount(cdo.getAccount());
			bqbo.setName(cdo.getName());
			bqbo.setRegionFullName(cdo.getRegionFullName());
			;
			if (CashStatusEnum.APPLY.val.equals(cdo.getStatus())) {
				bqbo.setStatus("申请中");
			} else if (CashStatusEnum.ACCEPT.val.equals(cdo.getStatus())) {
				bqbo.setStatus("受理中");
			} else if (CashStatusEnum.SUCCESS.val.equals(cdo.getStatus())) {
				bqbo.setStatus("成功");
			} else if (CashStatusEnum.FAILURE.val.equals(cdo.getStatus())) {
				bqbo.setStatus("失败");
			}

			BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(cdo.getBusinessBankAccountId());
			bqbo.setBusinessBankAccount(bankAccountDO.getAccountName());
			bqbo.setBankNo(bankAccountDO.getAccountNumber());
			bqbo.setBankName(bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf("(")));
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
		String ids = param.getIds();
		String idsArray[] = ids.split(",");
		for (int i = 0; i < idsArray.length; i++) {
			WithdrawCashOperDOView view = new WithdrawCashOperDOView();
			view.setId(Integer.valueOf(idsArray[i]));
			view.setStatus(param.getCashOperEnum().val);
			view.setAuditFailReason(param.getAuditFailReason() == null ? "" : param.getAuditFailReason());
			view.setAuditUserId(param.getAuditUserId() == null ? 0 : param.getAuditUserId());
			view.setAuditUserName(param.getAuditUserName() == null ? "" : param.getAuditUserName());
			view.setGmtModified(new Date());
			paramList.add(view);
		}
		withdrawCashDOMapperExtend.updateBatchWithdrawCashStatus(paramList);
		if (!CashStatusEnum.FAILURE.val.equals(param.getCashOperEnum().val)) {
			return ResultCode.SUCCESS;
		}

		// 失败的情况要回滚数据
		for (int i = 0; i < idsArray.length; i++) {

			// 余额新增
			WithdrawCashDO wcdo = withdrawCashDOMapper.selectByPrimaryKey(Long.valueOf(idsArray[i]));
			PropertyInfoDOEiditView infoView = new PropertyInfoDOEiditView();
			infoView.setBalance(wcdo.getCashMoney());
			infoView.setUserNum(wcdo.getUserNum());
			infoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoView);

			// 新增退回交易明细
			TransactionDetailDO transactionDetailDO = new TransactionDetailDO();
			transactionDetailDO.setTitle(TransactionTitle.CASH_FAIL_BACK);
			transactionDetailDO.setTransactionNum(wcdo.getCashNumber());
			transactionDetailDO.setUserNum(wcdo.getUserNum());
			if (wcdo.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
				transactionDetailDO.setTransactionType(MemberTransactionTypeEnum.WITHDRAW_BACK.getValue());
			} else if (wcdo.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				transactionDetailDO.setTransactionType(MerchantTransactionTypeEnum.WITHDRAW_BACK.getValue());
			}
			transactionDetailDO.setTransactionAccount(wcdo.getAccount());
			transactionDetailDO.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
			transactionDetailDO.setAmount(wcdo.getCashMoney());
			transactionDetailDO.setBizId(wcdo.getId());
			transactionDetailDO.setRemark("");
			transactionDetailDO.setGmtCreate(new Date());
			transactionDetailDOMapper.insertSelective(transactionDetailDO);
		}

		return ResultCode.SUCCESS;
	}

}