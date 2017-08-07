package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.param.PointDetailQueryData1Param;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.CommissionUtilBO;
import com.lawu.eshop.property.srv.domain.FansInviteDetailDO;
import com.lawu.eshop.property.srv.domain.LoveDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import com.lawu.eshop.property.srv.mapper.FansInviteDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.LoveDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.service.CommissionUtilService;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;

/**
 * <p>
 * Description:与积分/余额相关业务处理(加、减)
 * </p>
 *
 * @author Yangqh
 * @date 2017年4月12日 下午12:54:03
 */
@Service
public class PropertyInfoDataServiceImpl implements PropertyInfoDataService {

	@Autowired
	private PointDetailService pointDetailService;
	@Autowired
	private PropertyInfoService propertyInfoService;
	@Autowired
	private FansInviteDetailDOMapper fansInviteDetailDOMapper;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private LoveDetailDOMapper loveDetailDOMapper;
	@Autowired
	private CommissionUtilService commissionUtilService;
	@Autowired
	private PointDetailDOMapper pointDetailDOMapper;

	@Override
	@Transactional
	public int doHanlderMinusPoint(PropertyInfoDataParam param) {
		int retCode = propertyInfoService.validatePoint(param.getUserNum(), param.getPoint());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		String pointNum = StringUtil.getRandomNum("");
		// 插入积分明细
		PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
		pointDetailSaveDataParam.setPointNum(pointNum);
		pointDetailSaveDataParam.setUserNum(param.getUserNum());
		if (param.getMemberTransactionTypeEnum() != null) {
			pointDetailSaveDataParam.setTitle(param.getMemberTransactionTypeEnum().getName());
			pointDetailSaveDataParam.setPointType(param.getMemberTransactionTypeEnum().getValue());
		} else if (param.getMerchantTransactionTypeEnum() != null) {
			pointDetailSaveDataParam.setTitle(param.getMerchantTransactionTypeEnum().getName());
			pointDetailSaveDataParam.setPointType(param.getMerchantTransactionTypeEnum().getValue());
		} else {
			return ResultCode.BIZ_TYPE_NULL;
		}
		pointDetailSaveDataParam.setPoint(new BigDecimal(param.getPoint()));
		pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.OUT.getVal());
		pointDetailSaveDataParam.setBizId(param.getBizId());
		pointDetailService.save(pointDetailSaveDataParam);

		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "M", point);

		// 插入邀请粉丝记录
		if (param.getMerchantTransactionTypeEnum() != null && param.getMerchantTransactionTypeEnum()
				.getValue() == MerchantTransactionTypeEnum.INVITE_FANS.getValue()) {
			FansInviteDetailDO fansInviteDetailDO = new FansInviteDetailDO();
			fansInviteDetailDO.setMerchantId(param.getMerchantId());
			fansInviteDetailDO.setPointNum(pointNum);
			fansInviteDetailDO.setRegionName(param.getRegionName());
			fansInviteDetailDO.setSex(param.getSex());
			fansInviteDetailDO.setAge(param.getAge());
			fansInviteDetailDO.setInviteFansCount(param.getInviteFansCount());
			fansInviteDetailDO.setConsumePoint(new BigDecimal(param.getPoint()));
			fansInviteDetailDO.setGmtCreate(new Date());
			int i = fansInviteDetailDOMapper.insertSelective(fansInviteDetailDO);
			return i;
		}
		
		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int doHanlderAddPoint(PropertyInfoDataParam param) {

		// 插入积分明细
		PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
		pointDetailSaveDataParam.setPointNum(StringUtil.getRandomNum(""));
		pointDetailSaveDataParam.setUserNum(param.getUserNum());
		if (param.getMemberTransactionTypeEnum() != null) {
			pointDetailSaveDataParam.setTitle(param.getMemberTransactionTypeEnum().getName());
			pointDetailSaveDataParam.setPointType(param.getMemberTransactionTypeEnum().getValue());
		} else if (param.getMerchantTransactionTypeEnum() != null) {
			pointDetailSaveDataParam.setTitle(param.getMerchantTransactionTypeEnum().getName());
			pointDetailSaveDataParam.setPointType(param.getMerchantTransactionTypeEnum().getValue());
		} else {
			return ResultCode.BIZ_TYPE_NULL;
		}
		pointDetailSaveDataParam.setPoint(new BigDecimal(param.getPoint()));
		pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
		pointDetailSaveDataParam.setBizId(param.getBizId());
		pointDetailService.save(pointDetailSaveDataParam);

		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "A", point);

		return ResultCode.SUCCESS;
	}

	@Override
	public int doHanlderAddBalance(PropertyInfoDataParam param) {
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTransactionNum(StringUtil.getRandomNum(""));
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount("");
		if (param.getMemberTransactionTypeEnum() != null) {
			tdsParam.setTitle(param.getMemberTransactionTypeEnum().getName());
			tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		} else if (param.getMerchantTransactionTypeEnum() != null) {
			tdsParam.setTitle(param.getMerchantTransactionTypeEnum().getName());
			tdsParam.setTransactionType(param.getMerchantTransactionTypeEnum().getValue());
		}
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
		tdsParam.setAmount(new BigDecimal(param.getPoint()));
		tdsParam.setBizId("");
		tdsParam.setThirdTransactionNum("");
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
		transactionDetailService.save(tdsParam);
		
		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "B", "A", point);
		
		return ResultCode.SUCCESS;
	}

	@Override
	public int doHanlderMinusBalance(PropertyInfoDataParam param) {
		int retCode = propertyInfoService.validateBalance(param.getUserNum(), param.getPoint(),false,"");
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTransactionNum(StringUtil.getRandomNum(""));
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount("");
		if (param.getMemberTransactionTypeEnum() != null) {
			tdsParam.setTitle(param.getMemberTransactionTypeEnum().getName());
			tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		} else if (param.getMerchantTransactionTypeEnum() != null) {
			tdsParam.setTitle(param.getMerchantTransactionTypeEnum().getName());
			tdsParam.setTransactionType(param.getMerchantTransactionTypeEnum().getValue());
		}
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
		tdsParam.setAmount(new BigDecimal(param.getPoint()));
		tdsParam.setBizId("");
		tdsParam.setThirdTransactionNum("");
		tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.getVal());
		transactionDetailService.save(tdsParam);
		
		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "B", "M", point);
		
		return ResultCode.SUCCESS;
	}

	/**
	 *  用户收入加余额，计算爱心账户：
	 *  用户点广告(需要乘以0.5)
	 *  抢赞
	 *  抢红包
	 * @param param
	 * @return
	 */
	@Override
	@Transactional
	public int doHanlderBalanceIncome(PropertyInfoDataParam param) {
		BigDecimal clickMoney = new BigDecimal(param.getPoint());
		CommissionUtilBO commissionBO;
		if(MemberTransactionTypeEnum.ADVERTISING.getValue().equals(param.getMemberTransactionTypeEnum().getValue())){
			commissionBO = commissionUtilService.getClickAdMine(clickMoney);
		}else{
			commissionBO = commissionUtilService.getIncomeMoney(clickMoney);
		}
		BigDecimal actureMoneyIn = commissionBO.getActureMoneyIn();//实际进余额
		BigDecimal actureLoveIn = commissionBO.getActureLoveIn();//爱心账户
		
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTransactionNum(StringUtil.getRandomNum(""));
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount("");
		if (param.getMemberTransactionTypeEnum() != null) {
			tdsParam.setTitle(param.getMemberTransactionTypeEnum().getName());
			tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		} else if (param.getMerchantTransactionTypeEnum() != null) {
			tdsParam.setTitle(param.getMerchantTransactionTypeEnum().getName());
			tdsParam.setTransactionType(param.getMerchantTransactionTypeEnum().getValue());
		}
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
		tdsParam.setAmount(actureMoneyIn);
		tdsParam.setBizId("");
		tdsParam.setThirdTransactionNum("");
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
		transactionDetailService.save(tdsParam);
		
		// 更新用户资产余额
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "B", "A", actureMoneyIn);
		
		LoveDetailDO loveDetailDO = new LoveDetailDO();
		loveDetailDO.setTitle(param.getLoveTypeEnum().getName());
		loveDetailDO.setLoveNum(StringUtil.getRandomNum(""));
		loveDetailDO.setUserNum(param.getUserNum());
		loveDetailDO.setLoveType(param.getLoveTypeEnum().getValue());
		loveDetailDO.setAmount(actureLoveIn);
		loveDetailDO.setGmtCreate(new Date());
		loveDetailDO.setBizId(param.getTempBizId());
		loveDetailDOMapper.insertSelective(loveDetailDO);
		
		//更新爱心账户
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "L", "A", actureLoveIn);
		
		return ResultCode.SUCCESS;
	}

	@Override
	public Integer getPointDetailByUserNumAndPointTypeAndBizId(PointDetailQueryData1Param param) {
		PointDetailDOExample example = new PointDetailDOExample();
		example.createCriteria().andUserNumEqualTo(param.getUserNum()).andPointTypeEqualTo(param.getPointType()).andBizIdEqualTo(param.getBizId());
		long count = pointDetailDOMapper.countByExample(example);
		if(count == 0){
			return Integer.valueOf(0);
		}else{
			return Integer.valueOf(1);
		}
	}
	
	
}
