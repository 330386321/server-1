package com.lawu.eshop.statistics.srv.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureQueryParam;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureSaveParam;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureSaveWrapperParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserIncomeExpenditureBO;
import com.lawu.eshop.statistics.srv.converter.ReportUserIncomeExpenditureConverter;
import com.lawu.eshop.statistics.srv.domain.ReportUserIncomeExpenditureDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserIncomeExpenditureDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportUserIncomeExpenditureDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportUserIncomeExpenditureService;

/**
 * 用户收支服务接口实现类
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
@Service
public class ReportUserIncomeExpenditureServiceImpl implements ReportUserIncomeExpenditureService {

	@Autowired
	private ReportUserIncomeExpenditureDOMapper reportUserIncomeExpenditureDOMapper;

	/**
	 * 保存用户收支记录
	 * 
	 * @param param
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	@Override
	@Transactional
	public void save(ReportUserIncomeExpenditureSaveParam param) {
		ReportUserIncomeExpenditureDO record = ReportUserIncomeExpenditureConverter.convert(param);
		reportUserIncomeExpenditureDOMapper.insertSelective(record);
	}
	
	/**
	 * 批量保存用户收支记录
	 * 
	 * @param param
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	@Override
	@Transactional
	public void batchSave(ReportUserIncomeExpenditureSaveWrapperParam param) {
		for (ReportUserIncomeExpenditureSaveParam reportUserIncomeExpenditureSaveParam : param.getParams()) {
			save(reportUserIncomeExpenditureSaveParam);
		}
	}

    /**
     * 分页查询用户收支记录
     * 
     * @param param
     * @return
     * @author Sunny
     * @date 2017年7月3日
     */
	@Override
	public Page<ReportUserIncomeExpenditureBO> page(ReportUserIncomeExpenditureQueryParam param) {
		Page<ReportUserIncomeExpenditureBO> rtn = new Page<>();
		rtn.setCurrentPage(param.getCurrentPage());
		
		ReportUserIncomeExpenditureDOExample example = new ReportUserIncomeExpenditureDOExample();
		ReportUserIncomeExpenditureDOExample.Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(param.getAccount());
		criteria.andGmtReportBetween(param.getStart(), param.getEnd());
		criteria.andUserTypeEqualTo(param.getUserType().getValue());
		
		long count = reportUserIncomeExpenditureDOMapper.countByExample(example);
		rtn.setTotalCount(Long.valueOf(count).intValue());
		if (count <= 0 || count <= param.getOffset()) {
			return rtn;
		}
		
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getCurrentPage());
		List<ReportUserIncomeExpenditureDO> reportUserIncomeExpenditureDOList = reportUserIncomeExpenditureDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		rtn.setRecords(ReportUserIncomeExpenditureConverter.convertReportUserIncomeExpenditureBOList(reportUserIncomeExpenditureDOList));
		return rtn;
	}
}
