package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.param.AdCommissionJobParam;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.LoveTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.LoveDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.LoveDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.AdService;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.StringUtil;

@Service
public class AdServiceImpl implements AdService {

	@Autowired
	private PropertyService propertyService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private LoveDetailDOMapper loveDetailDOMapper;
	@Autowired
	private PointDetailService pointDetailService;
	@Autowired
	private PropertyInfoService propertyInfoService;
	@Autowired
	private TransactionDetailDOMapper transactionDetailDOMapper;
	@Autowired
	private PointDetailDOMapper pointDetailDOMapper;

	@Override
	@Transactional
	public int clickAd(PropertyInfoDataParam param) {
		// 新增交易明细记录、加财产余额、计爱心账户、加财产爱心账户

		String ad_commission_0 = propertyService.getValue(PropertyType.ad_commission_0);
		String love_account_scale = propertyService.getValue(PropertyType.love_account_scale);
		if ("".equals(ad_commission_0)) {
			ad_commission_0 = PropertyType.ad_commission_0_default;
		}
		if ("".equals(love_account_scale)) {
			love_account_scale = PropertyType.love_account_scale_default;
		}
		double d_love_account_scale = Double.valueOf(love_account_scale).doubleValue();
		double d_acture_in = 1 - d_love_account_scale; // 用户实际进账比例：1-爱心账户比例

		BigDecimal clickMoney = new BigDecimal(param.getPoint()); // 广告点击实际所得
		BigDecimal adCommission0 = new BigDecimal(ad_commission_0); // 所得比例
		BigDecimal actureScaleIn = new BigDecimal(String.valueOf(d_acture_in)); // 实际所得
		BigDecimal loveScaleIn = new BigDecimal(love_account_scale);// 爱心账户

		BigDecimal actureMoneyIn = clickMoney.multiply(adCommission0).multiply(actureScaleIn);// 实际所得余额
		BigDecimal actureLoveIn = clickMoney.multiply(adCommission0).multiply(loveScaleIn);// 爱心账户
		String num = StringUtil.getRandomNum("");

		// 新增点广告的余额交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(MemberTransactionTypeEnum.ADVERTISING.getName());
		tdsParam.setTransactionNum(num);
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.ADVERTISING.getValue());
		tdsParam.setTransactionAccount("");
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(actureMoneyIn);
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
		tdsParam.setBizId(0L);
		transactionDetailService.save(tdsParam);

		// 加会员财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getUserNum());
		infoDoView.setBalance(actureMoneyIn);
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

		// 计爱心账户
		LoveDetailDO loveDetailDO = new LoveDetailDO();
		loveDetailDO.setTitle(LoveTypeEnum.AD_CLICK.getName());
		loveDetailDO.setLoveNum(num);
		loveDetailDO.setUserNum(param.getUserNum());
		loveDetailDO.setLoveType(LoveTypeEnum.AD_CLICK.getValue());
		loveDetailDO.setAmount(actureLoveIn);
		loveDetailDO.setRemark("用户点广告所得，计入的爱心账户");
		loveDetailDO.setGmtCreate(new Date());
		loveDetailDOMapper.insertSelective(loveDetailDO);

		// 加会员财产爱心账户
		PropertyInfoDOEiditView infoDoView1 = new PropertyInfoDOEiditView();
		infoDoView1.setUserNum(param.getUserNum());
		infoDoView1.setLoveAccount(actureLoveIn);
		infoDoView1.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddLove(infoDoView1);

		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int calculation(AdCommissionJobParam param) {
		// 新增交易明细记录、加财产余额、计爱心账户、加财产爱心账户

		String num = StringUtil.getRandomNum("");
		if (!param.isLast()) {

			// 先查询该上级是否已经算过提成
			TransactionDetailDOExample example = new TransactionDetailDOExample();
			Criteria criteria = example.createCriteria();
			criteria.andBizIdEqualTo(param.getMemberAdRecordId().toString()).andUserNumEqualTo(param.getUserNum());
			Criteria criteria1 = example.createCriteria();
			criteria1.andTransactionTypeEqualTo(MemberTransactionTypeEnum.LOWER_INCOME.getValue());
			Criteria criteria2 = example.createCriteria();
			criteria2.andTransactionTypeEqualTo(MerchantTransactionTypeEnum.LOWER_INCOME.getValue());
			example.or(criteria1);
			example.or(criteria2);
			List<TransactionDetailDO> dos = transactionDetailDOMapper.selectByExample(example);
			if (dos != null && dos.size() > 0) {
				return ResultCode.SUCCESS;
			}

			// 新增点广告的余额交易明细
			TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
			tdsParam.setTransactionNum(num);
			tdsParam.setUserNum(param.getUserNum());
			if (param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
				tdsParam.setTitle(MemberTransactionTypeEnum.LOWER_INCOME.getName());
				tdsParam.setTransactionType(MemberTransactionTypeEnum.LOWER_INCOME.getValue());

			} else if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				tdsParam.setTitle(MerchantTransactionTypeEnum.LOWER_INCOME.getName());
				tdsParam.setTransactionType(MerchantTransactionTypeEnum.LOWER_INCOME.getValue());
			}
			tdsParam.setTransactionAccount("");
			tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
			tdsParam.setAmount(param.getActureMoneyIn());
			tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
			tdsParam.setBizId(param.getMemberAdRecordId());
			transactionDetailService.save(tdsParam);

			// 加用户（会员或商家）财产余额
			PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
			infoDoView.setUserNum(param.getUserNum());
			infoDoView.setBalance(param.getActureMoneyIn());
			infoDoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

			// 计爱心账户
			LoveDetailDO loveDetailDO = new LoveDetailDO();
			loveDetailDO.setTitle(LoveTypeEnum.AD_COMMISSION.getName());
			loveDetailDO.setLoveNum(num);
			loveDetailDO.setUserNum(param.getUserNum());
			loveDetailDO.setLoveType(LoveTypeEnum.AD_COMMISSION.getValue());
			loveDetailDO.setAmount(param.getActureLoveIn());
			loveDetailDO.setRemark("下级点击广告所得提成，计入爱心账户");
			loveDetailDO.setGmtCreate(new Date());
			loveDetailDOMapper.insertSelective(loveDetailDO);

			// 加会员财产爱心账户
			PropertyInfoDOEiditView infoDoView1 = new PropertyInfoDOEiditView();
			infoDoView1.setUserNum(param.getUserNum());
			infoDoView1.setLoveAccount(param.getActureLoveIn());
			infoDoView1.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddLove(infoDoView1);

		} else {

			// 先判断该上级是否已算过提成
			PointDetailDOExample example = new PointDetailDOExample();
			com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria criteria = example.createCriteria();
			criteria.andBizIdEqualTo(param.getMemberAdRecordId().toString()).andUserNumEqualTo(param.getUserNum());
			com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria criteria1 = example.createCriteria();
			criteria1.andPointTypeEqualTo(MemberTransactionTypeEnum.LOWER_INCOME.getValue());
			com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria criteria2 = example.createCriteria();
			criteria2.andPointTypeEqualTo(MerchantTransactionTypeEnum.LOWER_INCOME.getValue());
			example.or(criteria1);
			example.or(criteria2);
			List<PointDetailDO> dos = pointDetailDOMapper.selectByExample(example);
			if (dos != null && dos.size() > 0) {
				return ResultCode.SUCCESS;
			}

			// 新增积分明细
			PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
			pointDetailSaveDataParam.setPointNum(num);
			pointDetailSaveDataParam.setUserNum(param.getUserNum());
			if (param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
				pointDetailSaveDataParam.setTitle(MemberTransactionTypeEnum.LOWER_INCOME.getName());
				pointDetailSaveDataParam.setPointType(MemberTransactionTypeEnum.LOWER_INCOME.getValue());

			} else if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				pointDetailSaveDataParam.setTitle(MerchantTransactionTypeEnum.LOWER_INCOME.getName());
				pointDetailSaveDataParam.setPointType(MerchantTransactionTypeEnum.LOWER_INCOME.getValue());

			}
			pointDetailSaveDataParam.setPoint(param.getActureMoneyIn());
			pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.IN.val);
			pointDetailSaveDataParam.setBizId(param.getMemberAdRecordId().toString());
			pointDetailService.save(pointDetailSaveDataParam);

			// 更新用户资产
			propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "A", param.getActureMoneyIn());

			// 最后一级不计爱心账户

		}

		return ResultCode.SUCCESS;
	}

	// public static void main(String[] args) {
	// String money = "1";
	// String love_account_scale = "0.003";
	// String scale1 = "0.5";
	// double d_love_account_scale =
	// Double.valueOf(love_account_scale).doubleValue();
	// double d_acture_in = 1 - d_love_account_scale;
	// BigDecimal clickMoney = new BigDecimal(money); //广告点击实际所得
	// BigDecimal adCommission0 = new BigDecimal(scale1); //所得比例
	// BigDecimal actureScaleIn = new BigDecimal(String.valueOf(d_acture_in));
	// //实际所得
	// BigDecimal loveScaleIn = new BigDecimal(love_account_scale); //爱心账户
	//
	// BigDecimal actureMoneyIn =
	// clickMoney.multiply(adCommission0).multiply(actureScaleIn);//实际所得余额
	// BigDecimal actureLoveIn =
	// clickMoney.multiply(adCommission0).multiply(loveScaleIn);//爱心账户
	// System.out.println(actureMoneyIn+"<----->"+actureLoveIn);
	// }
}