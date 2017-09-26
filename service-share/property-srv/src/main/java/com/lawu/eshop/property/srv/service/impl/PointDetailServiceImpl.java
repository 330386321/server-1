package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.property.srv.bo.IncomeMsgBO;
import com.lawu.eshop.property.srv.domain.extend.IncomeMsgDOView;
import com.lawu.eshop.property.srv.domain.extend.IncomeMsgExample;
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
import com.lawu.eshop.property.srv.bo.AreaPointConsumeBO;
import com.lawu.eshop.property.srv.bo.PointConsumeReportBO;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.bo.ReportAdPointGroupByAreaBO;
import com.lawu.eshop.property.srv.converter.PointDetailConverter;
import com.lawu.eshop.property.srv.domain.PointDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.domain.extend.AreaPointConsumeDOView;
import com.lawu.eshop.property.srv.domain.extend.ReportAdPointGroupByAreaView;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PointDetailDOMapperExtend;
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
	@Autowired
	private PointDetailDOMapperExtend pointDetailDOMapperExtend;

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
		//保存省市区用于代理商区域统计
		if(param.getRegionPath() != null && !"".equals(param.getRegionPath())){
			String[] regions = param.getRegionPath().split("/");
			pointDetailDO.setProvinceId(regions.length > 0 ? Integer.valueOf(regions[0]) : 0);
			pointDetailDO.setCityId(regions.length > 1 ? Integer.valueOf(regions[1]) : 0);
			pointDetailDO.setAreaId(regions.length > 2 ? Integer.valueOf(regions[2]) : 0);
		}
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

	@Override
	public List<ReportAdPointGroupByAreaBO> getReportAdPointGroupByArea(String bdate, String edate) {
		List<ReportAdPointGroupByAreaView> list = pointDetailDOMapperExtend.getReportAdPointGroupByArea(bdate, edate);
		List<ReportAdPointGroupByAreaBO> rntList = new ArrayList<ReportAdPointGroupByAreaBO>();
		for(ReportAdPointGroupByAreaView r : list) {
			ReportAdPointGroupByAreaBO BO = new ReportAdPointGroupByAreaBO();
			BO.setAreaId(r.getAreaId());
			BO.setTotalPoint(r.getTotalPoint());
			rntList.add(BO);
		}
		return rntList;
	}

	@Override
	public List<AreaPointConsumeBO> getAreaPointConsume(String bdate, String edate) {
		List<AreaPointConsumeDOView> list = pointDetailDOMapperExtend.getAreaPointConsume(bdate, edate);
		List<AreaPointConsumeBO> rtnList = new ArrayList<AreaPointConsumeBO>();
		if(list != null && !list.isEmpty()) {
			for(AreaPointConsumeDOView view : list) {
				AreaPointConsumeBO BO = new AreaPointConsumeBO();
				BO.setAreaId(view.getAreaId());
				BO.setCityId(view.getCityId());
				BO.setProvinceId(view.getProvinceId());
				BO.setTotalPoint(view.getTotalPoint());
				BO.setType(view.getType());
				rtnList.add(BO);
			}
		}
		return rtnList;
	}

	@Override
	public List<AreaPointConsumeBO> getAreaPointRefund(String bdate, String edate) {
		List<AreaPointConsumeDOView> list = pointDetailDOMapperExtend.getAreaPointRefund(bdate, edate);
		List<AreaPointConsumeBO> rtnList = new ArrayList<AreaPointConsumeBO>();
		if(list != null && !list.isEmpty()) {
			for(AreaPointConsumeDOView view : list) {
				AreaPointConsumeBO BO = new AreaPointConsumeBO();
				BO.setAreaId(view.getAreaId());
				BO.setTotalPoint(view.getTotalPoint());
				rtnList.add(BO);
			}
		}
		return rtnList;
	}

	@Override
	public List<IncomeMsgBO> getIncomeMsgDataList(String begin,String end) {
		IncomeMsgExample example = new IncomeMsgExample();
		example.setBegin(begin);
		example.setEnd(end);
		List<IncomeMsgDOView> list = pointDetailDOMapperExtend.getIncomeMsgDataList(example);
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
}
