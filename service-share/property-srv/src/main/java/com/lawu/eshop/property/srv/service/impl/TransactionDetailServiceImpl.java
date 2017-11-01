package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.idworker.client.impl.BizIdType;
import com.lawu.eshop.idworker.client.impl.IdWorkerHelperImpl;
import com.lawu.eshop.property.constants.MemberTransactionCategoryEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionCategoryEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.BalancePayValidateDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TotalSalesQueryParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.param.UserIncomeExpenditureQueryParam;
import com.lawu.eshop.property.param.foreign.TransactionDetailMonthlyBillOfMemberForeignParam;
import com.lawu.eshop.property.param.foreign.TransactionDetailMonthlyBillOfMerchantForeignParam;
import com.lawu.eshop.property.param.foreign.TransactionDetailQueryForMemberForeignParam;
import com.lawu.eshop.property.param.foreign.TransactionDetailQueryForMerchantForeignParam;
import com.lawu.eshop.property.srv.bo.IncomeMsgBO;
import com.lawu.eshop.property.srv.bo.MonthlyBillBO;
import com.lawu.eshop.property.srv.bo.TotalSalesBO;
import com.lawu.eshop.property.srv.bo.TotalSalesGroupByAreaBO;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.bo.UserIncomeExpenditureBO;
import com.lawu.eshop.property.srv.converter.TotalSalesConverter;
import com.lawu.eshop.property.srv.converter.TransactionDetailConverter;
import com.lawu.eshop.property.srv.converter.UserIncomeExpenditureConverter;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample;
import com.lawu.eshop.property.srv.domain.TransactionDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.domain.extend.IncomeMsgDOView;
import com.lawu.eshop.property.srv.domain.extend.IncomeMsgExample;
import com.lawu.eshop.property.srv.domain.extend.MonthlyBillDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesGroupByAreaDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesQueryExample;
import com.lawu.eshop.property.srv.domain.extend.UserIncomeExpenditureDO;
import com.lawu.eshop.property.srv.domain.extend.UserIncomeExpenditureExample;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.TransactionDetailExtendDOMapper;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.DateUtil;

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
	
	@Autowired
	private TransactionDetailExtendDOMapper transactionDetailExtendDOMapper;

	@Autowired
	private PropertyInfoDOMapper propertyInfoDOMapper;

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
		
		Page<TransactionDetailBO> page = new Page<>();
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
	@Deprecated
	@Override
	public Page<TransactionDetailBO> findPageByUserNumForMember(String userNum, TransactionDetailQueryForMemberParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(userNum);

		if (param.getTransactionType() != null) {
			criteria.andTransactionTypeEqualTo(param.getTransactionType().getValue());	
		}
		
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		
		Page<TransactionDetailBO> page = new Page<>();
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
		if(param.getPreviousAmount() == null){
			PropertyInfoDOExample example = new PropertyInfoDOExample();
			example.createCriteria().andUserNumEqualTo(param.getUserNum());
			List<PropertyInfoDO> propertyInfoList = propertyInfoDOMapper.selectByExample(example);
			transactionDetailDO.setPreviousAmount((propertyInfoList == null || propertyInfoList.isEmpty()) ? new BigDecimal(0) : propertyInfoList.get(0).getBalance());
		}else{
			transactionDetailDO.setPreviousAmount(param.getPreviousAmount());
		}
		//保存省市区用于代理商区域统计
		if(param.getRegionPath() != null && !"".equals(param.getRegionPath()) && !"null".equals(param.getRegionPath())){
			String[] regions = param.getRegionPath().split("/");
			transactionDetailDO.setProvinceId(regions.length > 0 ? Integer.valueOf(regions[0]) : 0);
			transactionDetailDO.setCityId(regions.length > 1 ? Integer.valueOf(regions[1]) : 0);
			transactionDetailDO.setAreaId(regions.length > 2 ? Integer.valueOf(regions[2]) : 0);
		}
		transactionDetailDO.setTitle(param.getTitle());
		transactionDetailDO.setTransactionNum(IdWorkerHelperImpl.generate(BizIdType.TRANSACTION));
		transactionDetailDO.setUserNum(param.getUserNum());
		transactionDetailDO.setTransactionType(param.getTransactionType());
		transactionDetailDO.setTransactionAccount(param.getTransactionAccount());
		transactionDetailDO.setTransactionAccountType(param.getTransactionAccountType());
		transactionDetailDO.setAmount(param.getAmount());
		transactionDetailDO.setDirection(param.getDirection());
		transactionDetailDO.setThirdTransactionNum(param.getThirdTransactionNum() == null ? "" : param.getThirdTransactionNum());
		transactionDetailDO.setBizId(param.getBizId() == null ? "" : param.getBizId());
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
		criteria.andUserNumEqualTo(param.getUserNum()).andThirdTransactionNumEqualTo(param.getTradeNo());
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public Page<TransactionDetailBO> getBackageRechargePageList(TransactionDetailQueryForBackageParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		if(StringUtils.isNotEmpty(param.getUserNum())){
			criteria.andUserNumEqualTo(param.getUserNum());
		}

		List<Byte> transactionTypeList = new ArrayList<>();
		if(param.getMemberTransactionType() == null && param.getMerchantTransactionType() == null){
			transactionTypeList.add(MemberTransactionTypeEnum.BACKAGE.getValue());
			transactionTypeList.add(MemberTransactionTypeEnum.RECHARGE_BALANCE.getValue());
			transactionTypeList.add(MerchantTransactionTypeEnum.BACKAGE.getValue());
			transactionTypeList.add(MerchantTransactionTypeEnum.RECHARGE.getValue());
			criteria.andTransactionTypeIn(transactionTypeList);
		}else{
			if (param.getMemberTransactionType() != null) {
				transactionTypeList.add(MemberTransactionTypeEnum.BACKAGE.getValue());
				transactionTypeList.add(MemberTransactionTypeEnum.RECHARGE_BALANCE.getValue());
				criteria.andTransactionTypeIn(transactionTypeList);
			}
			if(param.getMerchantTransactionType() != null){
				transactionTypeList.add(MerchantTransactionTypeEnum.BACKAGE.getValue());
				transactionTypeList.add(MerchantTransactionTypeEnum.RECHARGE.getValue());
				criteria.andTransactionTypeIn(transactionTypeList);
			}
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
	 * 查询平台销售金额
	 *
	 * @param param
	 * @return
	 */
	@Override
	public List<TotalSalesBO> selectTotalSales(TotalSalesQueryParam param) {
		TotalSalesQueryExample example = TotalSalesConverter.convert(param);
		List<TotalSalesDO> totalSalesDOList = transactionDetailExtendDOMapper.selectTotalSales(example);
		return TotalSalesConverter.convertTotalSalesBOList(totalSalesDOList);
	}
	
	/**
	 * 查询平台销售金额
	 *
	 * @param param
	 * @return
	 */
	@Override
	public List<UserIncomeExpenditureBO> selectUserIncomeExpenditure(UserIncomeExpenditureQueryParam param) {
		UserIncomeExpenditureExample example = UserIncomeExpenditureConverter.convert(param);
		List<UserIncomeExpenditureDO> userIncomeExpenditureDOList = transactionDetailExtendDOMapper.selectUserIncomeExpenditure(example);
		return UserIncomeExpenditureConverter.convertUserIncomeExpenditureBOList(userIncomeExpenditureDOList);
	}

	@Override
	public List<IncomeMsgBO> getIncomeMsgDataList(String begin,String end) {
		IncomeMsgExample example = new IncomeMsgExample();
		example.setBegin(begin);
		example.setEnd(end);
		List<IncomeMsgDOView> list = transactionDetailExtendDOMapper.getIncomeMsgDataList(example);
		List<IncomeMsgBO> bos = new ArrayList<>();
		for(IncomeMsgDOView view : list){
			IncomeMsgBO bo = new IncomeMsgBO();
			bo.setType(view.getbType());
			bo.setMoney(view.getMoney());
			bo.setUserNum(view.getUserNum());
			bos.add(bo);
		}
		return bos;
	}

	/**
	 * 查询平台销售金额 group by area
	 *
	 * @param param
	 * @return
	 */
	@Override
	public List<TotalSalesGroupByAreaBO> selectTotalSalesGroupByArea(TotalSalesQueryParam param) {
		TotalSalesQueryExample example = TotalSalesConverter.convert(param);
		List<TotalSalesGroupByAreaDO> totalSalesDOList = transactionDetailExtendDOMapper.selectTotalSalesGroupByArea(example);
		List<TotalSalesGroupByAreaBO> boList = new ArrayList<TotalSalesGroupByAreaBO>();
		if(totalSalesDOList != null && !totalSalesDOList.isEmpty()) {
			for(TotalSalesGroupByAreaDO DO : totalSalesDOList) {
				TotalSalesGroupByAreaBO bo = new TotalSalesGroupByAreaBO();
				bo.setAmount(DO.getAmount());
				bo.setAreaId(DO.getAreaId());
				bo.setCityId(DO.getCityId());
				bo.setProvinceId(DO.getProvinceId());
				bo.setTransactionType(MerchantTransactionTypeEnum.getEnum(DO.getTransactionType()));
				bo.setType(DO.getType());
				boList.add(bo);
			}
		}
		return boList;
	}

	@Override
	public boolean verifyByUserNumAndTransactionTypeAndBizId(BalancePayDataParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		Byte transactionType = param.getMemberTransactionTypeEnum().getValue();
		if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
			transactionType = param.getMerchantTransactionTypeEnum().getValue();
		}
		criteria.andUserNumEqualTo(param.getUserNum()).andTransactionTypeEqualTo(transactionType).andBizIdEqualTo(param.getBizIds());
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyByUserNumAndTransactionTypeAndBizId(BalancePayValidateDataParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		Byte transactionType = param.getMemberTransactionTypeEnum().getValue();
		if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
			transactionType = param.getMerchantTransactionTypeEnum().getValue();
		}
		criteria.andUserNumEqualTo(param.getUserNum()).andTransactionTypeEqualTo(transactionType).andBizIdEqualTo(param.getBizIds());
		int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyOrderByUserNumAndTransactionType(BalancePayValidateDataParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		Byte transactionType = param.getMemberTransactionTypeEnum().getValue();
		if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
			transactionType = param.getMerchantTransactionTypeEnum().getValue();
		}
		criteria.andUserNumEqualTo(param.getUserNum()).andTransactionTypeEqualTo(transactionType);
		transactionDetailDOExample.setOrderByClause(" id desc ");
		List<TransactionDetailDO> DOList = transactionDetailDOMapper.selectByExample(transactionDetailDOExample);
		for(TransactionDetailDO tdo : DOList){
			if(param.getBizIds().contains(",")){
				if(tdo.getBizId().equals(param.getBizIds())){
					return true;
				}
			} else{
				String bizIds = tdo.getBizId();
				String []arrayBizId = bizIds.split(",");
				List<String> bizIdsList = Arrays.asList(arrayBizId);
				if(bizIdsList.contains(param.getBizIds())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean verifyOrderByUserNumAndTransactionType(BalancePayDataParam param) {
		TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
		Criteria criteria = transactionDetailDOExample.createCriteria();
		Byte transactionType = param.getMemberTransactionTypeEnum().getValue();
		if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
			transactionType = param.getMerchantTransactionTypeEnum().getValue();
		}
		criteria.andUserNumEqualTo(param.getUserNum()).andTransactionTypeEqualTo(transactionType);
		transactionDetailDOExample.setOrderByClause(" id desc ");
		List<TransactionDetailDO> DOList = transactionDetailDOMapper.selectByExample(transactionDetailDOExample);
		for(TransactionDetailDO tdo : DOList){
			if(param.getBizIds().contains(",")){
				if(tdo.getBizId().equals(param.getBizIds())){
					return true;
				}
			} else{
				String bizIds = tdo.getBizId();
				String []arrayBizId = bizIds.split(",");
				List<String> bizIdsList = Arrays.asList(arrayBizId);
				if(bizIdsList.contains(param.getBizIds())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String packageTitle(MemberTransactionTypeEnum memberTransactionTypeEnum,MerchantTransactionTypeEnum merchantTransactionTypeEnum,String title){
		String titleRet = "";
		if (memberTransactionTypeEnum != null) {
			if(title == null || "".equals(title)){
				titleRet = memberTransactionTypeEnum.getName();
			} else {
				if(MemberTransactionTypeEnum.MERCHANT_RED_SWEEP.getValue().equals(memberTransactionTypeEnum.getValue()) ||
						MemberTransactionTypeEnum.PAY_ORDERS.getValue().equals(memberTransactionTypeEnum.getValue()) ||
						MemberTransactionTypeEnum.PAY.getValue().equals(memberTransactionTypeEnum.getValue())){
					titleRet = memberTransactionTypeEnum.getName() + "-" + title;
				} else if (MemberTransactionTypeEnum.MEMBER_RED_SWEEP.getValue().equals(memberTransactionTypeEnum.getValue())){
					titleRet = memberTransactionTypeEnum.getName() + "-来自" + title;
				} else{
					titleRet = title;
				}
			}
		} else if (merchantTransactionTypeEnum != null) {
			if(title == null || "".equals(title)){
				titleRet = merchantTransactionTypeEnum.getName();
			} else {
				if(MerchantTransactionTypeEnum.PAY.getValue().equals(merchantTransactionTypeEnum.getValue())){
					titleRet = merchantTransactionTypeEnum.getName() + "-" + title;
				} else{
					titleRet = title;
				}
			}
		}
		return titleRet;
	}

    /**
     * 根据会员编号和查询参数分页查询交易明细
     * 
     * @param userNum 会员编号
     * @param param 查询参数
     * @return
     * @author jiangxinjun
     * @date 2017年10月20日
     */
    @Override
    public Page<TransactionDetailBO> page(String userNum, TransactionDetailQueryForMemberForeignParam param) {
        Page<TransactionDetailBO> rtn = new Page<>();
        TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
        Criteria criteria = transactionDetailDOExample.createCriteria();
        criteria.andUserNumEqualTo(userNum);
        if (!MemberTransactionCategoryEnum.ALL.equals(param.getTransactionCategory())) {
            criteria.andTransactionTypeIn(MemberTransactionTypeEnum.getEnum(param.getTransactionCategory()));
        }
        if (param.getDate() != null) {
            criteria.andGmtCreateBetween(DateUtil.getFirstSecondOfDay(DateUtil.getFirstDayOfMonth(param.getDate())), DateUtil.getLastSecondOfDay(DateUtil.getLastDayOfMonth(param.getDate())));
        }
        int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
        rtn.setCurrentPage(param.getCurrentPage());
        rtn.setTotalCount(count);
        // 如果返回的总记录为0，直接返回page
        if (count <= 0 || param.getOffset() >= count) {
            return rtn;
        }
        transactionDetailDOExample.setOrderByClause("gmt_create desc");
        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        List<TransactionDetailDO> list = transactionDetailDOMapper.selectByExampleWithRowbounds(transactionDetailDOExample, rowBounds);
        rtn.setRecords(TransactionDetailConverter.convertBOS(list));
        return rtn;
    }
    
    /**
     * 根据会员编号和查询参数月结账单
     * 
     * @param userNum 会员编号
     * @param param 查询参数
     * @return
     * @author jiangxinjun
     * @date 2017年10月20日
     */
    @Override
    public MonthlyBillBO monthlyBill(String userNum, TransactionDetailMonthlyBillOfMemberForeignParam param) {
        TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
        Criteria criteria = transactionDetailDOExample.createCriteria();
        criteria.andUserNumEqualTo(userNum);
        if (!MemberTransactionCategoryEnum.ALL.equals(param.getTransactionCategory())) {
            criteria.andTransactionTypeIn(MemberTransactionTypeEnum.getEnum(param.getTransactionCategory()));
        }
        if (param.getDate() != null) {
            criteria.andGmtCreateBetween(DateUtil.getFirstSecondOfDay(DateUtil.getFirstDayOfMonth(param.getDate())), DateUtil.getLastSecondOfDay(DateUtil.getLastDayOfMonth(param.getDate())));
        }
        List<MonthlyBillDO> monthlyBillDOList = transactionDetailExtendDOMapper.selectMonthlyBill(transactionDetailDOExample);
        return TransactionDetailConverter.convertMonthlyBillBO(monthlyBillDOList);
    }
    
    /**
     * 根据会员编号和查询参数分页查询交易明细
     * 
     * @param userNum 会员编号
     * @param param 查询参数
     * @return
     * @author jiangxinjun
     * @date 2017年10月20日
     */
    @Override
    public Page<TransactionDetailBO> page(String userNum, TransactionDetailQueryForMerchantForeignParam param) {
        Page<TransactionDetailBO> rtn = new Page<>();
        TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
        Criteria criteria = transactionDetailDOExample.createCriteria();
        criteria.andUserNumEqualTo(userNum);
        if (!MerchantTransactionCategoryEnum.ALL.equals(param.getTransactionCategory())) {
            List<Byte> merchantTransactionType = MerchantTransactionTypeEnum.getEnum(param.getTransactionCategory());
            if (merchantTransactionType != null && !merchantTransactionType.isEmpty()) {
                criteria.andTransactionTypeIn(merchantTransactionType);
            }
        }
        if (param.getDate() != null) {
            criteria.andGmtCreateBetween(DateUtil.getFirstSecondOfDay(DateUtil.getFirstDayOfMonth(param.getDate())), DateUtil.getLastSecondOfDay(DateUtil.getLastDayOfMonth(param.getDate())));
        }
        int count = transactionDetailDOMapper.countByExample(transactionDetailDOExample);
        rtn.setCurrentPage(param.getCurrentPage());
        rtn.setTotalCount(count);
        // 如果返回的总记录为0，直接返回page
        if (count <= 0 || param.getOffset() >= count) {
            return rtn;
        }
        transactionDetailDOExample.setOrderByClause("gmt_create desc");
        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        List<TransactionDetailDO> list = transactionDetailDOMapper.selectByExampleWithRowbounds(transactionDetailDOExample, rowBounds);
        rtn.setRecords(TransactionDetailConverter.convertBOS(list));
        return rtn;
    }
    
    /**
     * 根据会员编号和查询参数月结账单
     * 
     * @param userNum 会员编号
     * @param param 查询参数
     * @return
     * @author jiangxinjun
     * @date 2017年10月20日
     */
    @Override
    public MonthlyBillBO monthlyBill(String userNum, TransactionDetailMonthlyBillOfMerchantForeignParam param) {
        TransactionDetailDOExample transactionDetailDOExample = new TransactionDetailDOExample();
        Criteria criteria = transactionDetailDOExample.createCriteria();
        criteria.andUserNumEqualTo(userNum);
        if (!MerchantTransactionCategoryEnum.ALL.equals(param.getTransactionCategory())) {
            List<Byte> merchantTransactionType = MerchantTransactionTypeEnum.getEnum(param.getTransactionCategory());
            if (merchantTransactionType != null && !merchantTransactionType.isEmpty()) {
                criteria.andTransactionTypeIn(merchantTransactionType);
            }
        }
        if (param.getDate() != null) {
            criteria.andGmtCreateBetween(DateUtil.getFirstSecondOfDay(DateUtil.getFirstDayOfMonth(param.getDate())), DateUtil.getLastSecondOfDay(DateUtil.getLastDayOfMonth(param.getDate())));
        }
        List<MonthlyBillDO> monthlyBillDOList = transactionDetailExtendDOMapper.selectMonthlyBill(transactionDetailDOExample);
        return TransactionDetailConverter.convertMonthlyBillBO(monthlyBillDOList);
    }
}
