package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.TransactionDetailQueryParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.converter.TransactionDetailConverter;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.service.TransactionDetailService;

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

	@Override
	public Page<TransactionDetailBO> findPageByUserNum(String userNum, Byte transactionType, TransactionDetailQueryParam transactionDetailQueryParam) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		if (transactionType != null) {
			criteria.andTransactionTypeEqualTo(transactionType);
		}
		
		if (transactionDetailQueryParam.getConsumptionType() != null) {
			switch (transactionDetailQueryParam.getConsumptionType()) {
				case INCOME : 
					criteria.andAmountGreaterThanOrEqualTo(new BigDecimal(0));
					break;
				case EXPENDITURE:
					criteria.andAmountLessThan(new BigDecimal(0));
					break;
				default:
					break;
			}
		}
		
		criteria.andUserNumEqualTo(userNum);
		
		Page<TransactionDetailBO> page = new Page<TransactionDetailBO>();
		page.setCurrentPage(transactionDetailQueryParam.getCurrentPage());
		page.setTotalCount(findCountByUserNum(userNum, transactionType));
		
		// 如果返回的总记录为0，直接返回page
		if (page.getTotalCount() == null || page.getTotalCount() <= 0) {
			return page;
		}
		
		transactionDetailDOExample.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(transactionDetailQueryParam.getOffset(), transactionDetailQueryParam.getPageSize());
		
		List<TransactionDetailBO> transactionDetailBOS = TransactionDetailConverter.convertBOS(transactionDetailDOMapper.selectByExampleWithRowbounds(transactionDetailDOExample, rowBounds));
		page.setRecords(transactionDetailBOS);
		
		return page;
	}

	@Override
	public Integer findCountByUserNum(String userNum, Byte transactionType) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		if (transactionType != null) {
			criteria.andTransactionTypeEqualTo(transactionType);
		}
		criteria.andUserNumEqualTo(userNum);
		
		return transactionDetailDOMapper.countByExample(transactionDetailDOExample);
	}

	@Override
	@Transactional
	public int save(TransactionDetailSaveDataParam param) {
		TransactionDetailDO transactionDetailDO = new TransactionDetailDO();
		transactionDetailDO.setTitle(param.getTitle());
		transactionDetailDO.setTransactionNum(param.getTransactionNum());
		transactionDetailDO.setUserNum(param.getUserNum());
		transactionDetailDO.setTransactionType(param.getTransactionType());
		transactionDetailDO.setTransactionAccount(param.getTransactionAccount());
		transactionDetailDO.setTransactionAccountType(param.getTransactionAccountType());
		transactionDetailDO.setAmount(param.getAmount());
		transactionDetailDO.setBizId(param.getBizId());
		transactionDetailDO.setRemark(param.getRemark());
		transactionDetailDO.setGmtCreate(new Date());
		transactionDetailDOMapper.insertSelective(transactionDetailDO);
		return ResultCode.SUCCESS;
	}

}
