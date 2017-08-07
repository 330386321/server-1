package com.lawu.eshop.property.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
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
import com.lawu.eshop.property.srv.service.CommissionService;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.StringUtil;

@Service
public class CommissionServiceImpl implements CommissionService {
	
	private static Logger logger = LoggerFactory.getLogger(CommissionServiceImpl.class);

	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private LoveDetailDOMapper loveDetailDOMapper;
	@Autowired
	private PointDetailService pointDetailService;
	@Autowired
	private TransactionDetailDOMapper transactionDetailDOMapper;
	@Autowired
	private PointDetailDOMapper pointDetailDOMapper;

	@Override
	@Transactional
	public int calculation(CommissionJobParam param) {
		// 新增交易明细记录、加财产余额、计爱心账户、加财产爱心账户

		String num = StringUtil.getRandomNum("");
		if (param.isLast()) {

			// 最后一级若为用户进余额，为商家进广告积分
			if (param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
				
				// 先查询该上级是否已经算过提成
				TransactionDetailDOExample example = new TransactionDetailDOExample();
				Criteria criteria = example.createCriteria();
				criteria.andBizIdEqualTo(param.getBizId().toString()).andUserNumEqualTo(param.getUserNum())
						.andTransactionTypeEqualTo(param.getTypeVal());
				List<TransactionDetailDO> dos = transactionDetailDOMapper.selectByExample(example);
				logger.info("isLast dos={},size={}",dos,dos.size());
				if (!dos.isEmpty()) {
					logger.info("isLast已计算过提成，重复计算，直接返回！");
					return ResultCode.SUCCESS;
				}
				
				// 新增点广告的余额交易明细
				TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
				tdsParam.setTransactionNum(num);
				tdsParam.setUserNum(param.getUserNum());
				tdsParam.setTitle(param.getTypeName());
				tdsParam.setTransactionType(param.getTypeVal());
				tdsParam.setTransactionAccount("");
				tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
				tdsParam.setAmount(param.getActureMoneyIn());
				tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
				tdsParam.setBizId(param.getBizId().toString());
				transactionDetailService.save(tdsParam);

				// 加用户（会员或商家）财产余额
				PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
				infoDoView.setUserNum(param.getUserNum());
				infoDoView.setBalance(param.getActureMoneyIn());
				infoDoView.setGmtModified(new Date());
				propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

			} else if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				
				// 先判断该上级是否已算过提成
				PointDetailDOExample example = new PointDetailDOExample();
				com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria criteria = example.createCriteria();
				criteria.andBizIdEqualTo(param.getBizId().toString()).andUserNumEqualTo(param.getUserNum())
						.andPointTypeEqualTo(param.getTypeVal());
				List<PointDetailDO> dos = pointDetailDOMapper.selectByExample(example);
				logger.info("isLast dos={},size={}",dos,dos.size());
				if (!dos.isEmpty()) {
					logger.info("isLast已计算过提成，重复计算，直接返回！");
					return ResultCode.SUCCESS;
				}
				
				// 新增积分明细
				PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
				pointDetailSaveDataParam.setPointNum(num);
				pointDetailSaveDataParam.setUserNum(param.getUserNum());
				pointDetailSaveDataParam.setTitle(param.getTypeName());
				pointDetailSaveDataParam.setPointType(param.getTypeVal());
				pointDetailSaveDataParam.setPoint(param.getActureMoneyIn());
				pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
				pointDetailSaveDataParam.setBizId(param.getBizId().toString());
				pointDetailService.save(pointDetailSaveDataParam);

				// 更新用户资产
				PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
				infoDoView.setUserNum(param.getUserNum());
				infoDoView.setPoint(param.getActureMoneyIn());
				infoDoView.setGmtModified(new Date());
				propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(infoDoView);
			}

			// 最后一级不计爱心账户

		} else {

			// 先查询该上级是否已经算过提成
			TransactionDetailDOExample example = new TransactionDetailDOExample();
			Criteria criteria = example.createCriteria();
			criteria.andBizIdEqualTo(param.getBizId().toString()).andUserNumEqualTo(param.getUserNum())
					.andTransactionTypeEqualTo(param.getTypeVal());
			List<TransactionDetailDO> dos = transactionDetailDOMapper.selectByExample(example);
			logger.info("isLast dos={},size={}",dos,dos.size());
			if (!dos.isEmpty()) {
				logger.info("已计算过提成，重复计算，直接返回！");
				return ResultCode.SUCCESS;
			}

			// 新增点广告的余额交易明细
			TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
			tdsParam.setTransactionNum(num);
			tdsParam.setUserNum(param.getUserNum());
			tdsParam.setTitle(param.getTypeName());
			tdsParam.setTransactionType(param.getTypeVal());
			tdsParam.setTransactionAccount("");
			tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.getVal());
			tdsParam.setAmount(param.getActureMoneyIn());
			tdsParam.setDirection(PropertyInfoDirectionEnum.IN.getVal());
			tdsParam.setBizId(param.getBizId().toString());
			transactionDetailService.save(tdsParam);

			// 加用户（会员或商家）财产余额
			PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
			infoDoView.setUserNum(param.getUserNum());
			infoDoView.setBalance(param.getActureMoneyIn());
			infoDoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

			// 计爱心账户
			LoveDetailDO loveDetailDO = new LoveDetailDO();
			loveDetailDO.setTitle(param.getLoveTypeName());
			loveDetailDO.setLoveNum(num);
			loveDetailDO.setUserNum(param.getUserNum());
			loveDetailDO.setLoveType(param.getLoveTypeVal());
			loveDetailDO.setAmount(param.getActureLoveIn());
			loveDetailDO.setRemark("");
			loveDetailDO.setGmtCreate(new Date());
			loveDetailDO.setBizId(param.getTempBidId() == null ? "0" : param.getTempBidId().toString());
			loveDetailDOMapper.insertSelective(loveDetailDO);

			// 加会员财产爱心账户
			PropertyInfoDOEiditView infoDoView1 = new PropertyInfoDOEiditView();
			infoDoView1.setUserNum(param.getUserNum());
			infoDoView1.setLoveAccount(param.getActureLoveIn());
			infoDoView1.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddLove(infoDoView1);

		}

		return ResultCode.SUCCESS;
	}


}
