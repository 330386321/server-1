package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PayTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.BackagePropertyinfoDataParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoBackageParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.bo.PropertyPointAndBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;
import com.lawu.eshop.property.srv.converter.PropertyBalanceConverter;
import com.lawu.eshop.property.srv.converter.PropertyInfoConverter;
import com.lawu.eshop.property.srv.converter.PropertyPointConverter;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.PwdUtil;

/**
 * 资产管理服务实现
 *
 * @author meishuquan
 * @date 2017/3/27
 */
@Service
public class PropertyInfoServiceImpl implements PropertyInfoService {

	@Autowired
	private PropertyInfoDOMapper propertyInfoDOMapper;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PointDetailService pointDetailService;

	@Override
	public PropertyInfoBO getPropertyInfoByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
		return propertyInfoDOS.isEmpty() ? null : PropertyInfoConverter.convertBO(propertyInfoDOS.get(0));
	}

	@Override
	@Transactional
	public void updatePayPwd(String userNum, String newPwd) {
		PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
		propertyInfoDO.setUserNum(userNum);
		propertyInfoDO.setPayPassword(PwdUtil.generate(newPwd));
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		propertyInfoDOMapper.updateByExampleSelective(propertyInfoDO, propertyInfoDOExample);
	}

	@Override
	public PropertyBalanceBO getPropertyBalanceByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return null;
		}

		return PropertyBalanceConverter.convert(propertyInfoDOS.get(0));
	}

	@Override
	public PropertyPointBO getPropertyPointByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return null;
		}

		return PropertyPointConverter.convert(propertyInfoDOS.get(0));
	}

	@Override
	public int updatePropertyNumbers(String userNum, String column, String flag, BigDecimal number) {
		if (("B".equals(column) || "P".equals(column) || "L".equals(column))
				&& ("A".equals(flag) || "M".equals(flag))) {
			PropertyInfoDOEiditView editView = new PropertyInfoDOEiditView();
			editView.setUserNum(userNum);
			if ("B".equals(column)) {
				editView.setBalance(number);
				if ("A".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(editView);
				} else if ("M".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoMinusBalance(editView);
				}
			} else if ("P".equals(column)) {
				editView.setPoint(number);
				if ("A".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(editView);
				} else if ("M".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoMinusPoint(editView);
				}
			} else if ("L".equals(column)) {
				editView.setLoveAccount(number);
				if ("A".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoAddLove(editView);
				} else if ("M".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoMinusLove(editView);
				}
			}
		} else {
			return 0;
		}

		return ResultCode.SUCCESS;
	}

	@Override
	public int validateBalance(String userNum, String amount,boolean isVerifyPwd,String inPwd) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		//校验资产记录，是否为空，记录数=1,
		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return ResultCode.PROPERTY_INFO_NULL;
		} else if (propertyInfoDOS.size() > 1) {
			return ResultCode.PROPERTY_INFO_OUT_INDEX;
		}
		
		//校验资金是否冻结
		if(PropertyinfoFreezeEnum.YES.getVal().equals(propertyInfoDOS.get(0).getFreeze())){
			return ResultCode.PROPERTYINFO_FREEZE_YES;
		}
		
		// 校验支付密码
		if (isVerifyPwd && !PwdUtil.verify(inPwd, propertyInfoDOS.get(0).getPayPassword())) {
			return ResultCode.PAY_PWD_ERROR;
		}

		//校验余额是否足够
		PropertyBalanceBO balanceBO = PropertyBalanceConverter.convert(propertyInfoDOS.get(0));
		BigDecimal dbBalance = balanceBO.getBalance();
		double dBalacne = dbBalance.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		double dOrderMoney = Double.parseDouble(amount);
		if (dBalacne < dOrderMoney) {
			return ResultCode.PROPERTY_INFO_BALANCE_LESS;
		}
		
		return ResultCode.SUCCESS;
	}

	@Override
	public int validatePoint(String userNum, String point) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return ResultCode.PROPERTY_INFO_NULL;
		}

		BigDecimal dbPoint = propertyInfoDOS.get(0).getPoint();
		double dPoint = dbPoint.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		double drPoint = Double.parseDouble(point);
		if (dPoint < drPoint) {
			return ResultCode.PROPERTY_INFO_POINT_LESS;
		}
		return ResultCode.SUCCESS;
	}

	@Override
	public PropertyPointAndBalanceBO getPropertyInfoMoney(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
		PropertyPointAndBalanceBO bo = new PropertyPointAndBalanceBO();
		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			bo.setBalance(new BigDecimal("0"));
			bo.setPoint(new BigDecimal("0"));
			return bo;
		}
		PropertyInfoDO pdo = propertyInfoDOS.get(0);
		bo.setBalance(pdo.getBalance());
		bo.setPoint(pdo.getPoint());
		return bo;
	}

	@Override
	@Transactional
	public void savePropertyInfo(String userNum) {
		PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
		propertyInfoDO.setUserNum(userNum);
		propertyInfoDO.setGmtCreate(new Date());
		propertyInfoDOMapper.insertSelective(propertyInfoDO);
	}

	@Override
	public BigDecimal selectLoveAccount(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return new BigDecimal(0);
		}
		BigDecimal loveAccount = propertyInfoDOS.get(0).getLoveAccount();
		return loveAccount;
	}

	@Override
	@Transactional
	public int updateBalanceAndPoint(BackagePropertyinfoDataParam dparam) {
		PropertyInfoDOEiditView editView = new PropertyInfoDOEiditView();
		editView.setUserNum(dparam.getUserNum());
		if (PayTypeEnum.BALANCE.getVal().equals(dparam.getPayTypeEnum().getVal())) {
			editView.setBalance(new BigDecimal(dparam.getMoney()));
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(editView);
			
			TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
			tdsParam.setTitle(TransactionTitleEnum.BACKAGE.getVal());
			tdsParam.setUserNum(dparam.getUserNum());
			if(dparam.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				tdsParam.setTransactionType(MemberTransactionTypeEnum.BACKAGE.getValue());
			}else if(dparam.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				tdsParam.setTransactionType(MerchantTransactionTypeEnum.BACKAGE.getValue());
			}
			tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
			tdsParam.setAmount(new BigDecimal(dparam.getMoney()));
			tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
			transactionDetailService.save(tdsParam);
		} else if (PayTypeEnum.POINT.getVal().equals(dparam.getPayTypeEnum().getVal())) {
			editView.setPoint(new BigDecimal(dparam.getMoney()));
			propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(editView);
			
			PointDetailSaveDataParam pdsParam = new PointDetailSaveDataParam();
			pdsParam.setTitle(TransactionTitleEnum.BACKAGE.getVal());
			pdsParam.setUserNum(dparam.getUserNum());
			if(dparam.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				pdsParam.setPointType(MemberTransactionTypeEnum.BACKAGE.getValue());
			}else if(dparam.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				pdsParam.setPointType(MerchantTransactionTypeEnum.BACKAGE.getValue());
			} 
			pdsParam.setPoint(new BigDecimal(dparam.getMoney()));
			pdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
			pointDetailService.save(pdsParam);
		}
		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int updatePropertyinfoFreeze(String userNum, PropertyinfoFreezeEnum freeze) {
		PropertyInfoDOExample example = new PropertyInfoDOExample();
		example.createCriteria().andUserNumEqualTo(userNum);
		PropertyInfoDO pdo = new PropertyInfoDO();
		pdo.setFreeze(freeze.getVal());
		pdo.setGmtModified(new Date());
		propertyInfoDOMapper.updateByExampleSelective(pdo, example);
		return ResultCode.SUCCESS;
	}

	@Override
	public Page<PropertyInfoBO> getPropertyinfoPageList(PropertyInfoBackageParam param) {
		PropertyInfoDOExample example = new PropertyInfoDOExample();
		PropertyInfoDOExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(param.getUserNum())){
			criteria.andUserNumEqualTo(param.getUserNum());
		}else if(param.getUserType() != null){
			if(param.getUserType().getVal() == UserTypeEnum.MEMBER.getVal()){
				criteria.andUserNumLike(UserCommonConstant.MEMBER_NUM_TAG + "%");
			}else if(param.getUserType().getVal() == UserTypeEnum.MEMCHANT.getVal()){
				criteria.andUserNumLike(UserCommonConstant.MERCHANT_NUM_TAG + "%");
			}
		}

		Page<PropertyInfoBO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(propertyInfoDOMapper.countByExample(example));

		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());

		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		page.setRecords(PropertyInfoConverter.convertBO(propertyInfoDOS));
		return page;
	}

	@Override
	public PropertyinfoFreezeEnum getPropertyinfoFreeze(String userNum) {
		PropertyinfoFreezeEnum rtn = null;
		PropertyInfoDOExample example = new PropertyInfoDOExample();
		example.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> dos = propertyInfoDOMapper.selectByExample(example);
		if(dos == null || dos.isEmpty() || dos.size() > 1){
			return rtn;
		}
		rtn = PropertyinfoFreezeEnum.getEnum(dos.get(0).getFreeze());
		return rtn;
	}

}
