package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.LoveDetailDO;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.LoveDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.AdService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;

public class AdServiceImpl implements AdService {

	@Autowired
	private PropertyService propertyService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private LoveDetailDOMapper loveDetailDOMapper;
	
	@Override
	@Transactional
	public int clickAd(PropertyInfoDataParam param) {
		//新增交易明细记录、加财产余额、计爱心账户、加财产爱心账户
		
		String ad_commission_0 = propertyService.getValue(PropertyType.ad_commission_0);
		String love_account_scale = propertyService.getValue(PropertyType.love_account_scale);
		if ("".equals(ad_commission_0)) {
			ad_commission_0 = PropertyType.ad_commission_0_default;
		}
		if ("".equals(love_account_scale)) {
			love_account_scale = PropertyType.love_account_scale_default;
		}
		double d_love_account_scale = Double.valueOf(love_account_scale).doubleValue();
		double d_acture_in = 1 - d_love_account_scale;	//用户实际进账比例：1-爱心账户比例
		
		BigDecimal clickMoney = new BigDecimal(param.getPoint());	//广告点击实际所得
		BigDecimal adCommission0 = new BigDecimal(ad_commission_0);	//所得比例
		BigDecimal actureScaleIn = new BigDecimal(String.valueOf(d_acture_in));		//实际所得
		BigDecimal loveScaleIn = new BigDecimal(love_account_scale);//爱心账户
		
		BigDecimal actureMoneyIn = clickMoney.multiply(adCommission0).multiply(actureScaleIn);//实际所得余额
		BigDecimal actureLoveIn = clickMoney.multiply(adCommission0).multiply(loveScaleIn);//爱心账户
		String num = StringUtil.getRandomNum("");
		
		//新增点广告的余额交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.CLICK_AD.val);
		tdsParam.setTransactionNum(num);
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.ADVERTISING.getValue());
		tdsParam.setTransactionAccount("");
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(actureMoneyIn);
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
		tdsParam.setBizId(0L);
		transactionDetailService.save(tdsParam);
		
		//加会员财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getUserNum());
		infoDoView.setBalance(actureMoneyIn);
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);
		
		//计爱心账户
		LoveDetailDO loveDetailDO = new LoveDetailDO();
		loveDetailDO.setTitle(TransactionTitleEnum.CLICK_AD.val);
		loveDetailDO.setLoveNum(num);
		loveDetailDO.setUserNum(param.getUserNum());
		loveDetailDO.setLoveType(MemberTransactionTypeEnum.ADVERTISING.getValue());
		loveDetailDO.setAmount(actureLoveIn);
		loveDetailDO.setRemark("用户点广告所得计入的爱心账户");
		loveDetailDO.setGmtCreate(new Date());
		loveDetailDOMapper.insertSelective(loveDetailDO);
		
		//加会员财产爱心账户
		PropertyInfoDOEiditView infoDoView1 = new PropertyInfoDOEiditView();
		infoDoView1.setUserNum(param.getUserNum());
		infoDoView1.setLoveAccount(actureLoveIn);
		infoDoView1.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddLove(infoDoView1);
		
		return ResultCode.SUCCESS;
	}
	
	
//	public static void main(String[] args) {
//		String money = "1";
//		String love_account_scale = "0.003";
//		String scale1 = "0.5";
//		double d_love_account_scale = Double.valueOf(love_account_scale).doubleValue();
//		double d_acture_in = 1 - d_love_account_scale;
//		BigDecimal clickMoney = new BigDecimal(money);								//广告点击实际所得
//		BigDecimal adCommission0 = new BigDecimal(scale1);							//所得比例
//		BigDecimal actureScaleIn = new BigDecimal(String.valueOf(d_acture_in));		//实际所得
//		BigDecimal loveScaleIn = new BigDecimal(love_account_scale);				//爱心账户
//		
//		BigDecimal actureMoneyIn = clickMoney.multiply(adCommission0).multiply(actureScaleIn);//实际所得余额
//		BigDecimal actureLoveIn = clickMoney.multiply(adCommission0).multiply(loveScaleIn);//爱心账户
//		System.out.println(actureMoneyIn+"<----->"+actureLoveIn);
//	}
}
