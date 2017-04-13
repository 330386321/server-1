package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.property.constants.ThirdPayStatusEnum;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.srv.domain.RechargeDO;
import com.lawu.eshop.property.srv.mapper.RechargeDOMapper;
import com.lawu.eshop.property.srv.service.RechargeService;
import com.lawu.eshop.utils.StringUtil;

@Service
public class RechargeServiceImpl implements RechargeService {

	@Autowired
	private RechargeDOMapper rechargeDOMapper;
	
	@Override
	@Transactional
	public RechargeSaveDTO save(RechargeSaveDataParam param) {
		RechargeDO recharge = new RechargeDO();
		recharge.setUserNum(param.getUserNum());
		recharge.setRechargeMoney(new BigDecimal(param.getRechargeMoney()));
		recharge.setCurrentScale(param.getRechargeScale());
		
		double dCurrentScale = new Double(param.getRechargeScale()).doubleValue();
		double dRechargeMoney = new Double(param.getRechargeMoney()).doubleValue();
		double money = dRechargeMoney * dCurrentScale;
		recharge.setMoney(new BigDecimal(money));
		
		recharge.setRechargeType(param.getPayTypeEnum().val);
		recharge.setChannel(param.getTransactionPayTypeEnum().val);
		recharge.setStatus(ThirdPayStatusEnum.PAYING.val);
		recharge.setRechargeNumber(StringUtil.getRandomNum(""));
		recharge.setGmtCreate(new Date());
		rechargeDOMapper.insertSelective(recharge);
		
		if(recharge.getId() == null){
			return null;
		}
		RechargeSaveDTO dto = new RechargeSaveDTO();
		dto.setId(recharge.getId());
		return dto;
	}

}
