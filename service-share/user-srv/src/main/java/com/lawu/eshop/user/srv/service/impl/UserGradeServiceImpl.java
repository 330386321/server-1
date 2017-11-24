package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.user.srv.bo.UserGradeBO;
import com.lawu.eshop.user.srv.domain.UserGradeDO;
import com.lawu.eshop.user.srv.domain.UserGradeDOExample;
import com.lawu.eshop.user.srv.mapper.UserGradeDOMapper;
import com.lawu.eshop.user.srv.service.UserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGradeServiceImpl implements UserGradeService {

	@Autowired
	private UserGradeDOMapper userGradeDOMapper;


	@Override
	public Integer selectLotteryActivityPointByGradeValue(Byte gradeValue) {
		UserGradeDOExample example = new UserGradeDOExample();
		example.createCriteria().andGradeValueEqualTo(gradeValue);
		List<UserGradeDO> userGradeList = userGradeDOMapper.selectByExample(example);
		if(userGradeList == null || userGradeList.isEmpty()){
			return null;
		}
		return userGradeList.get(0).getLotteryActivityPoint();
	}

	@Override
	public List<UserGradeBO> selectGradeList() {
		UserGradeDOExample example = new UserGradeDOExample();
		example.setOrderByClause(" grade_weight asc ");
		List<UserGradeDO> userGradeList = userGradeDOMapper.selectByExample(example);
		List<UserGradeBO> bos = new ArrayList<>();
		for(UserGradeDO userGradeDO : userGradeList){
			UserGradeBO bo = new UserGradeBO();
			bo.setId(userGradeDO.getId());
			bo.setGradeName(userGradeDO.getGradeName());
			bo.setGradeValue(userGradeDO.getGradeValue());
			bo.setGradeWeight(userGradeDO.getGradeWeight());
			bo.setLotteryActivityPoint(userGradeDO.getLotteryActivityPoint());
			bo.setMinGrowthValue(userGradeDO.getMinGrowthValue());
			bo.setGmtCreate(userGradeDO.getGmtCreate());
			bo.setGmtModified(userGradeDO.getGmtModified());
			bos.add(bo);
		}
		return bos;
	}

	@Override
	public UserGradeBO selectUserGradeByMinGrowthValue(Integer resultMoney) {
		UserGradeDOExample example = new UserGradeDOExample();
		example.createCriteria().andMinGrowthValueLessThanOrEqualTo(resultMoney);
		example.setOrderByClause(" grade_weight desc ");
		List<UserGradeDO> userGradeList = userGradeDOMapper.selectByExample(example);
		if(userGradeList == null || userGradeList.isEmpty()){
			return null;
		}
		UserGradeDO userGradeDO = userGradeList.get(0);
		UserGradeBO bo = new UserGradeBO();
		bo.setId(userGradeDO.getId());
		bo.setGradeName(userGradeDO.getGradeName());
		bo.setGradeValue(userGradeDO.getGradeValue());
		bo.setGradeWeight(userGradeDO.getGradeWeight());
		bo.setLotteryActivityPoint(userGradeDO.getLotteryActivityPoint());
		bo.setMinGrowthValue(userGradeDO.getMinGrowthValue());
		bo.setGmtCreate(userGradeDO.getGmtCreate());
		bo.setGmtModified(userGradeDO.getGmtModified());
		return bo;
	}
}
