package com.lawu.eshop.property.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.converter.TransactionDetailConverter;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;

/**
 * 交易明细服务实现
 *
 * @author Sunny
 * @date 2017/3/29
 */
@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

	@Autowired
	private TransactionDetailDOMapper transactionDetailDOMapper;

	/**
	 * 根据用户编号、查询参数分页查询交易明细  - 商家
	 * 
	 * @param userNum 用户编号
	 * @param param 查询参数
	 * @return 
	 */
	@Override
	public Page<TransactionDetailBO> findPageByUserNumForMerchant(String userNum, TransactionDetailQueryForMerchantParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(userNum);
		if (param.getConsumptionType() != null) {
			criteria.andDirectionEqualTo(param.getConsumptionType().getValue());
		}
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		
		Page<TransactionDetailBO> page = new Page<TransactionDetailBO>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(count);
		
		// 如果返回的总记录为0，直接返回page
		if (count <= 0) {
			return page;
		}
		
		transactionDetailDOExample.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		List<TransactionDetailBO> transactionDetailBOS = TransactionDetailConverter.convertBOS(transactionDetailDOMapper.selectByExampleWithRowbounds(transactionDetailDOExample, rowBounds));
		page.setRecords(transactionDetailBOS);
		
		return page;
	}
	
	/**
	 * 根据用户编号、查询参数分页查询交易明细  - 买家
	 * 
	 * @param userNum 用户编号
	 * @param param 查询参数
	 * @return 
	 */
	@Override
	public Page<TransactionDetailBO> findPageByUserNumForMember(String userNum, TransactionDetailQueryForMemberParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(userNum);
		
		if (param.getTransactionType() != null) {
			criteria.andTransactionTypeEqualTo(param.getTransactionType().getValue());	
		}
		
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		
		Page<TransactionDetailBO> page = new Page<TransactionDetailBO>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(count);
		
		// 如果返回的总记录为0，直接返回page
		if (count <= 0) {
			return page;
		}
		
		transactionDetailDOExample.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		List<TransactionDetailDO> list = transactionDetailDOMapper.selectByExampleWithRowbounds(transactionDetailDOExample, rowBounds);
		
		page.setRecords(TransactionDetailConverter.convertBOS(list));
		
		return page;
	}

	@Override
	@Transactional
	public int save(TransactionDetailSaveDataParam param) {
		TransactionDetailDO transactionDetailDO = new TransactionDetailDO();
		transactionDetailDO.setTitle(param.getTitle());
		transactionDetailDO.setTransactionNum(StringUtil.getRandomNum(""));
		transactionDetailDO.setUserNum(param.getUserNum());
		transactionDetailDO.setTransactionType(param.getTransactionType());
		transactionDetailDO.setTransactionAccount(param.getTransactionAccount());
		transactionDetailDO.setTransactionAccountType(param.getTransactionAccountType());
		transactionDetailDO.setAmount(param.getAmount());
		transactionDetailDO.setDirection(param.getDirection());
		transactionDetailDO.setThirdTransactionNum(param.getThirdTransactionNum() == null ? "" : param.getThirdTransactionNum());
		transactionDetailDO.setBizId(param.getBizId() == null ? "" : param.getBizId().toString());
		transactionDetailDO.setRemark(param.getRemark());
		transactionDetailDO.setGmtCreate(new Date());
		transactionDetailDO.setBizNum(param.getBizNum() == null ? "" : param.getBizNum());
		transactionDetailDOMapper.insertSelective(transactionDetailDO);
		param.setId(transactionDetailDO.getId());
		return ResultCode.SUCCESS;
	}

	@Override
	public boolean verifyOrderIsPaySuccess(NotifyCallBackParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(param.getUserNum()).andThirdTransactionNumEqualTo(param.getOutTradeNo());
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		if(count > 0){
			return true;
		}
		return false;
	}

}
