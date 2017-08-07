package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.param.PointDetailQueryParam;
import com.lawu.eshop.property.param.PointDetailReportParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.srv.bo.PointConsumeReportBO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.converter.PointDetailConverter;
import com.lawu.eshop.property.srv.domain.PointDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;

/**
 * 积分明细服务实现
 *
 * @author Sunny
 * @date 2017/3/30
 */
@Service
public class PointDetailServiceImpl implements PointDetailService {

	@Autowired
	private PointDetailDOMapper pointDetailDOMapper;
	@Autowired
	private PropertyInfoDOMapper propertyInfoDOMapper;

	/**
	 * 根据用户编号、查询参数分页查询积分明细
	 * 
	 * @param userNum
	 *            用户编号
	 * @param pointDetailQueryParam
	 *            查询参数
	 * @return
	 */
	@Override
	public Page<PointDetailBO> findPageByUserNum(String userNum, PointDetailQueryParam pointDetailQueryParam) {
		PointDetailDOExample pointDetailDOExample = new PointDetailDOExample();
		Criteria criteria = pointDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(userNum);

		Integer count = pointDetailDOMapper.countByExample(pointDetailDOExample);
		
		Page<PointDetailBO> page = new Page<>();
		page.setCurrentPage(pointDetailQueryParam.getCurrentPage());
		page.setTotalCount(count.intValue());
		
		// 如果返回的总记录为0，直接返回page
		if (count <= 0 || pointDetailQueryParam.getOffset() >= count) {
			return page;
		}

		pointDetailDOExample.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(pointDetailQueryParam.getOffset(), pointDetailQueryParam.getPageSize());

		List<PointDetailDO> list = pointDetailDOMapper.selectByExampleWithRowbounds(pointDetailDOExample, rowBounds);

		page.setRecords(PointDetailConverter.convertBOS(list));
		list = null;
		
		return page;
	}

	@Override
	@Transactional
	public int save(PointDetailSaveDataParam param) {
		PropertyInfoDOExample example = new PropertyInfoDOExample();
		example.createCriteria().andUserNumEqualTo(param.getUserNum());
		List<PropertyInfoDO> propertyInfoList = propertyInfoDOMapper.selectByExample(example);

		PointDetailDO pointDetailDO = new PointDetailDO();
		pointDetailDO.setTitle(param.getTitle());
		pointDetailDO.setPointNum(StringUtil.getRandomNum(""));
		pointDetailDO.setUserNum(param.getUserNum());
		pointDetailDO.setPointType(param.getPointType());
		pointDetailDO.setPoint(param.getPoint());
		pointDetailDO.setDirection(param.getDirection());
		pointDetailDO.setBizId(param.getBizId());
		pointDetailDO.setRemark(param.getRemark());
		pointDetailDO.setGmtCreate(new Date());
		pointDetailDO.setPreviousPoint((propertyInfoList == null || propertyInfoList.isEmpty()) ? new BigDecimal(0) : propertyInfoList.get(0).getPoint());
		pointDetailDOMapper.insertSelective(pointDetailDO);
		return ResultCode.SUCCESS;
	}

    @Override
    public Page<PointDetailBO> getBackagePointPageList(TransactionDetailQueryForBackageParam param) {
		PointDetailDOExample pointDetailDOExample = new PointDetailDOExample();
		Criteria criteria = pointDetailDOExample.createCriteria();
		if(StringUtils.isNotEmpty(param.getUserNum())){
			criteria.andUserNumEqualTo(param.getUserNum());
		}

		List<Byte> transactionTypeList = new ArrayList<>();
		if(param.getMemberTransactionType() == null && param.getMerchantTransactionType() == null){
			transactionTypeList.add(MemberTransactionTypeEnum.BACKAGE.getValue());
			transactionTypeList.add(MemberTransactionTypeEnum.INTEGRAL_RECHARGE.getValue());
			transactionTypeList.add(MerchantTransactionTypeEnum.BACKAGE.getValue());
			transactionTypeList.add(MerchantTransactionTypeEnum.INTEGRAL_RECHARGE.getValue());
			criteria.andPointTypeIn(transactionTypeList);
		}else{
			if (param.getMemberTransactionType() != null) {
				transactionTypeList.add(MemberTransactionTypeEnum.BACKAGE.getValue());
				transactionTypeList.add(MemberTransactionTypeEnum.INTEGRAL_RECHARGE.getValue());
				criteria.andPointTypeIn(transactionTypeList);
			}
			if(param.getMerchantTransactionType() != null){
				transactionTypeList.add(MerchantTransactionTypeEnum.BACKAGE.getValue());
				transactionTypeList.add(MerchantTransactionTypeEnum.INTEGRAL_RECHARGE.getValue());
				criteria.andPointTypeIn(transactionTypeList);
			}
		}

		Integer count = pointDetailDOMapper.countByExample(pointDetailDOExample);

		Page<PointDetailBO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		page.setTotalCount(count.intValue());

		// 如果返回的总记录为0，直接返回page
		if (page.getTotalCount() == null || page.getTotalCount() <= 0) {
			return page;
		}

		pointDetailDOExample.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());

		List<PointDetailDO> list = pointDetailDOMapper.selectByExampleWithRowbounds(pointDetailDOExample, rowBounds);

		page.setRecords(PointDetailConverter.convertBOS(list));

		return page;
    }

	@Override
	public List<PointConsumeReportBO> selectPointDetailListByDateAndDirection(PointDetailReportParam param) {
		PointDetailDOExample example = new PointDetailDOExample();
		Date begin = DateUtil.formatDate(param.getDate()+" 00:00:00","yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.formatDate(param.getDate()+" 23:59:59","yyyy-MM-dd HH:mm:ss");
		example.createCriteria().andDirectionEqualTo(param.getDirection()).andGmtCreateBetween(begin, end);
		List<PointDetailDO> rntList = pointDetailDOMapper.selectByExample(example);
		List<PointConsumeReportBO> bos = new ArrayList<>();
		for(PointDetailDO pdo : rntList){
			PointConsumeReportBO bo = new PointConsumeReportBO();
			bo.setId(pdo.getId());
			bo.setPoint(pdo.getPoint());
			bo.setUserNum(pdo.getUserNum());
			bos.add(bo);
		}
		return bos;
	}

	@Override
	public List<PointConsumeReportBO> selectPointDetailListByDateAndDirectionAndPointType(PointDetailReportParam param) {
		PointDetailDOExample example = new PointDetailDOExample();
		Date begin = DateUtil.formatDate(param.getDate()+" 00:00:00","yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.formatDate(param.getDate()+" 23:59:59","yyyy-MM-dd HH:mm:ss");
		example.createCriteria().andDirectionEqualTo(param.getDirection()).andPointTypeEqualTo(param.getPointType()).andGmtCreateBetween(begin, end);
		List<PointDetailDO> rntList = pointDetailDOMapper.selectByExample(example);
		List<PointConsumeReportBO> bos = new ArrayList<>();
		for(PointDetailDO pdo : rntList){
			PointConsumeReportBO bo = new PointConsumeReportBO();
			bo.setId(pdo.getId());
			bo.setPoint(pdo.getPoint());
			bo.setUserNum(pdo.getUserNum());
			bos.add(bo);
		}
		return bos;
	}

}
