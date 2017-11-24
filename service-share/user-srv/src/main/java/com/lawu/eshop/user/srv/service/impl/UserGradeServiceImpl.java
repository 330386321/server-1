package com.lawu.eshop.user.srv.service.impl;

import java.util.List;

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
}
