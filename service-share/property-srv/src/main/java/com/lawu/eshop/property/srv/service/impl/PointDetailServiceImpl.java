package com.lawu.eshop.property.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.PointDetailQueryParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.converter.PointDetailConverter;
import com.lawu.eshop.property.srv.domain.PointDetailDO;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample;
import com.lawu.eshop.property.srv.domain.PointDetailDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.service.PointDetailService;

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
	
	/**
	 * 根据用户编号、查询参数分页查询积分明细
	 * 
	 * @param userNo 用户编号
	 * @param transactionDetailQueryParam 查询参数
	 * @return 
	 */
	@Override
	public Page<PointDetailBO> findPageByUserNum(String userNum, PointDetailQueryParam pointDetailQueryParam) {
		PointDetailDOExample pointDetailDOExample = new PointDetailDOExample();
		Criteria criteria = pointDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(userNum);
		
		Page<PointDetailBO> page = new Page<PointDetailBO>();
		page.setCurrentPage(pointDetailQueryParam.getCurrentPage());
		page.setTotalCount(findCountByUserNum(userNum));
		
		// 如果返回的总记录为0，直接返回page
		if (page.getTotalCount() == null || page.getTotalCount() <= 0) {
			return page;
		}
		
		pointDetailDOExample.setOrderByClause("gmt_create desc");
		RowBounds rowBounds = new RowBounds(pointDetailQueryParam.getOffset(), pointDetailQueryParam.getPageSize());
		
		List<PointDetailBO> pointDetailBOS = PointDetailConverter.convertBOS(pointDetailDOMapper.selectByExampleWithRowbounds(pointDetailDOExample, rowBounds));
		page.setRecords(pointDetailBOS);
		
		return page;
	}
	
	/**
	 * 根据用户编号和交易类型查询交易的总条数
	 * 
	 * @param userNo
	 * @param transactionType
	 * @return
	 */
	@Override
	public Integer findCountByUserNum(String userNum) {
		PointDetailDOExample pointDetailDOExample = new PointDetailDOExample();
		Criteria criteria = pointDetailDOExample.createCriteria();
		criteria.andUserNumEqualTo(userNum);
		
		return pointDetailDOMapper.countByExample(pointDetailDOExample);
	}

	@Override
	@Transactional
	public int save(PointDetailSaveDataParam param) {
		PointDetailDO pointDetailDO = new PointDetailDO();
		pointDetailDO.setTitle(param.getTitle());
		pointDetailDO.setPointNum(param.getPointNum());
		pointDetailDO.setUserNum(param.getUserNum());
		pointDetailDO.setPointType(param.getPointType());
		pointDetailDO.setPoint(param.getPoint());
		pointDetailDO.setRemark(param.getRemark());
		pointDetailDO.setGmtCreate(new Date());
		pointDetailDOMapper.insertSelective(pointDetailDO);
		return ResultCode.SUCCESS;
	}

}
