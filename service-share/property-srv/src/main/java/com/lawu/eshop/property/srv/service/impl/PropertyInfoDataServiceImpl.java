package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.idworker.client.impl.BizIdType;
import com.lawu.eshop.idworker.client.impl.IdWorkerHelperImpl;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.param.CheckRepeatOfPropertyOperationParam;
import com.lawu.eshop.property.param.PointDetailQueryData1Param;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.CommissionUtilBO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.domain.FansInviteDetailDO;
import com.lawu.eshop.property.srv.domain.LoveDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.exception.PointNegativeException;
import com.lawu.eshop.property.srv.exception.RepeatDealException;
import com.lawu.eshop.property.srv.exception.SavePointDetailException;
import com.lawu.eshop.property.srv.mapper.FansInviteDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.LoveDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.CommissionUtilService;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;

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

	private static Logger logger = LoggerFactory.getLogger(PropertyInfoDataServiceImpl.class);

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
	@Autowired
	private PropertyInfoDOMapper propertyInfoDOMapper;
	@Autowired
	@Qualifier("lotteryRecordTransactionMainServiceImpl")
	private TransactionMainService lotteryRecordTransactionMainServiceImpl;

	@Override
	@Transactional
	public int doHanlderMinusPoint(PropertyInfoDataParam param) {

		if(param.getBizId() != null && !"".equals(param.getBizId()) && !"0".equals(param.getBizId())){
			CheckRepeatOfPropertyOperationParam validateParam = new CheckRepeatOfPropertyOperationParam();
			validateParam.setUserNum(param.getUserNum());
			validateParam.setMerchantTransactionTypeEnum(param.getMerchantTransactionTypeEnum());
			validateParam.setMemberTransactionTypeEnum(param.getMemberTransactionTypeEnum());
			validateParam.setBizIds(param.getBizId());
			boolean repeat = pointDetailService.verifyRepeatByUserNumAndTransactionTypeAndBizId(validateParam);
			if (repeat) {
				logger.info("重复操作(判断幂等)-积分扣除业务");//商家发广告
				return ResultCode.PROCESSED_RETURN_SUCCESS;
			}
		}

		int retCode = propertyInfoService.validatePoint(param.getUserNum(), param.getPoint());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		String pointNum = IdWorkerHelperImpl.generate(BizIdType.POINT);
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
		pointDetailSaveDataParam.setRegionPath(param.getRegionPath());
		pointDetailService.save(pointDetailSaveDataParam);

		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		int ret = propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "M", point);
		if(ResultCode.ERROR_POINT_NEGATIVE == ret){
			throw new PointNegativeException(ResultCode.get(ResultCode.ERROR_POINT_NEGATIVE));
		}
		
		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int doHanlderAddPoint(PropertyInfoDataParam param) {

		if(param.getBizId() != null && !"".equals(param.getBizId()) && !"0".equals(param.getBizId())){
			CheckRepeatOfPropertyOperationParam validateParam = new CheckRepeatOfPropertyOperationParam();
			validateParam.setUserNum(param.getUserNum());
			validateParam.setMerchantTransactionTypeEnum(param.getMerchantTransactionTypeEnum());
			validateParam.setMemberTransactionTypeEnum(param.getMemberTransactionTypeEnum());
			validateParam.setBizIds(param.getBizId());
			boolean repeat = pointDetailService.verifyRepeatByUserNumAndTransactionTypeAndBizId(validateParam);
			if (repeat) {
				logger.info("重复操作(判断幂等)-积分累加业务");//广告下架&邀请过期
				return ResultCode.PROCESSED_RETURN_SUCCESS;
			}
		}

		// 插入积分明细
		PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
		pointDetailSaveDataParam.setPointNum(IdWorkerHelperImpl.generate(BizIdType.POINT));
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
	@Transactional
	public int doHanlderAddBalance(PropertyInfoDataParam param) {
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTransactionNum(IdWorkerHelperImpl.generate(BizIdType.TRANSACTION));
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
	@Transactional
	public int doHanlderMinusBalance(PropertyInfoDataParam param) {
		int retCode = propertyInfoService.validateBalance(param.getUserNum(), param.getPoint(),false,"");
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTransactionNum(IdWorkerHelperImpl.generate(BizIdType.TRANSACTION));
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
		int ret = propertyInfoService.updatePropertyNumbers(param.getUserNum(), "B", "M", point);
		if(ResultCode.ERROR_BALANCE_NEGATIVE == ret){
			throw new PointNegativeException(ResultCode.get(ResultCode.ERROR_BALANCE_NEGATIVE));
		}
		
		return ResultCode.SUCCESS;
	}

	/**
	 *  用户收入加余额，计算爱心账户：
	 *  1、用户点广告(所得需要乘以0.5)
	 *  2、抢赞
	 *  3、抢红包(用户发的、商家发的)
	 *  4、用户同意商家邀请
	 * @param param
	 * @return
	 */
	@Override
	@Transactional
	public int doHanlderBalanceIncome(PropertyInfoDataParam param) {
		BigDecimal clickMoney = new BigDecimal(param.getPoint());
		CommissionUtilBO commissionBO;
		if(MemberTransactionTypeEnum.AD_PLANE.getValue().equals(param.getMemberTransactionTypeEnum().getValue()) ||
				MemberTransactionTypeEnum.AD_VIDEO.getValue().equals(param.getMemberTransactionTypeEnum().getValue())){
			commissionBO = commissionUtilService.getClickAdMine(clickMoney);
		}else{
			commissionBO = commissionUtilService.getIncomeMoney(clickMoney);
		}
		BigDecimal actureMoneyIn = commissionBO.getActureMoneyIn();//实际进余额
		BigDecimal actureLoveIn = commissionBO.getActureLoveIn();//爱心账户

		PropertyInfoDOExample examplePropertyInfo = new PropertyInfoDOExample();
		examplePropertyInfo.createCriteria().andUserNumEqualTo(param.getUserNum());
		List<PropertyInfoDO> propertyInfoList = propertyInfoDOMapper.selectByExample(examplePropertyInfo);

		LoveDetailDO loveDetailDO = new LoveDetailDO();
		loveDetailDO.setTitle(param.getLoveTypeEnum().getName());
		loveDetailDO.setLoveNum(IdWorkerHelperImpl.generate(BizIdType.LOVE));
		loveDetailDO.setUserNum(param.getUserNum());
		loveDetailDO.setLoveType(param.getLoveTypeEnum().getValue());
		loveDetailDO.setAmount(actureLoveIn);
		loveDetailDO.setGmtCreate(new Date());
		loveDetailDO.setBizId(param.getTempBizId());
		loveDetailDO.setPreviousAmount((propertyInfoList == null || propertyInfoList.isEmpty()) ? new BigDecimal(0) : propertyInfoList.get(0).getLoveAccount());
		loveDetailDOMapper.insertSelective(loveDetailDO);

		//更新爱心账户
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "L", "A", actureLoveIn);

		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTransactionNum(IdWorkerHelperImpl.generate(BizIdType.TRANSACTION));
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount("");
		tdsParam.setTitle(transactionDetailService.packageTitle(param.getMemberTransactionTypeEnum(),param.getMerchantTransactionTypeEnum(),param.getTitle()));
		if (param.getMemberTransactionTypeEnum() != null) {
			tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		} else if (param.getMerchantTransactionTypeEnum() != null) {
			tdsParam.setTransactionType(param.getMerchantTransactionTypeEnum().getValue());
		}
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
		tdsParam.setAmount(actureMoneyIn);
		tdsParam.setBizId(param.getTempBizId());
		tdsParam.setThirdTransactionNum("");
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
		tdsParam.setPreviousAmount((propertyInfoList == null || propertyInfoList.isEmpty()) ? new BigDecimal(0) : propertyInfoList.get(0).getBalance());
		tdsParam.setRegionPath(param.getRegionPath());
		tdsParam.setTransactionDesc(param.getTransactionDesc());
		transactionDetailService.save(tdsParam);
		
		// 更新用户资产余额
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "B", "A", actureMoneyIn);
		
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

	@Override
	@Transactional
	public int doHanlderMinusPointWithLottery(PropertyInfoDataParam param) {
		if (param.getBizId() != null && !"".equals(param.getBizId()) && !"0".equals(param.getBizId())) {
			CheckRepeatOfPropertyOperationParam validateParam = new CheckRepeatOfPropertyOperationParam();
			validateParam.setUserNum(param.getUserNum());
			validateParam.setMerchantTransactionTypeEnum(param.getMerchantTransactionTypeEnum());
			validateParam.setMemberTransactionTypeEnum(param.getMemberTransactionTypeEnum());
			validateParam.setBizIds(param.getBizId());
			boolean repeat = pointDetailService.verifyRepeatByUserNumAndTransactionTypeAndBizId(validateParam);
			if (repeat) {
				logger.info("重复操作(判断幂等)-积分扣除业务");
				return ResultCode.PROCESSED_RETURN_SUCCESS;
			}
		}

		int retCode = propertyInfoService.validatePoint(param.getUserNum(), param.getPoint());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		String pointNum = IdWorkerHelperImpl.generate(BizIdType.POINT);
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
		pointDetailSaveDataParam.setRegionPath(param.getRegionPath());
		pointDetailService.save(pointDetailSaveDataParam);

		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		int ret = propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "M", point);
		if (ResultCode.ERROR_POINT_NEGATIVE == ret) {
			throw new PointNegativeException(ResultCode.get(ResultCode.ERROR_POINT_NEGATIVE));
		}

		PointDetailBO pointDetailBO = pointDetailService.getPointDetailByUserNumAndBizId(param.getUserNum(), param.getBizId());
		if (pointDetailBO == null) {
			throw new SavePointDetailException(ResultCode.get(ResultCode.ERROR_SAVE_POINT_DETAIL));
		}
		lotteryRecordTransactionMainServiceImpl.sendNotice(pointDetailBO.getId());
		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public Long doHanlderMinusPointByFans(PropertyInfoDataParam param) {

		if(param.getBizId() != null && !"".equals(param.getBizId()) && !"0".equals(param.getBizId())){
			CheckRepeatOfPropertyOperationParam validateParam = new CheckRepeatOfPropertyOperationParam();
			validateParam.setUserNum(param.getUserNum());
			validateParam.setMerchantTransactionTypeEnum(param.getMerchantTransactionTypeEnum());
			validateParam.setMemberTransactionTypeEnum(param.getMemberTransactionTypeEnum());
			validateParam.setBizIds(param.getBizId());
			boolean repeat = pointDetailService.verifyRepeatByUserNumAndTransactionTypeAndBizId(validateParam);
			if (repeat) {
				logger.info("重复操作(判断幂等)-商家邀请粉丝");
				throw new RepeatDealException(ResultCode.get(ResultCode.PROCESSED_RETURN_SUCCESS));
			}
		}

		String pointNum = IdWorkerHelperImpl.generate(BizIdType.POINT);
		// 插入积分明细
		PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
		pointDetailSaveDataParam.setPointNum(pointNum);
		pointDetailSaveDataParam.setUserNum(param.getUserNum());
		pointDetailSaveDataParam.setTitle(param.getMerchantTransactionTypeEnum().getName());
		pointDetailSaveDataParam.setPointType(param.getMerchantTransactionTypeEnum().getValue());
		pointDetailSaveDataParam.setPoint(new BigDecimal(param.getPoint()));
		pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.OUT.getVal());
		pointDetailSaveDataParam.setRegionPath(param.getRegionPath());
		pointDetailSaveDataParam.setBizId(param.getBizId());
		pointDetailService.save(pointDetailSaveDataParam);

		// 插入商家邀请记录
		FansInviteDetailDO fansInviteDetailDO = new FansInviteDetailDO();
		fansInviteDetailDO.setMerchantId(param.getMerchantId());
		fansInviteDetailDO.setPointNum(pointNum);
		fansInviteDetailDO.setRegionName(param.getRegionName());
		fansInviteDetailDO.setSex(param.getSex());
		fansInviteDetailDO.setAge(param.getAge());
		fansInviteDetailDO.setInviteFansCount(param.getInviteFansCount());
		fansInviteDetailDO.setConsumePoint(new BigDecimal(param.getPoint()));
		fansInviteDetailDO.setGmtCreate(new Date());
		fansInviteDetailDOMapper.insertSelective(fansInviteDetailDO);

		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		int ret = propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "M", point);
		if(ResultCode.ERROR_POINT_NEGATIVE == ret){
			throw new PointNegativeException(ResultCode.get(ResultCode.ERROR_POINT_NEGATIVE));
		}
		return fansInviteDetailDO.getId();
	}
}
