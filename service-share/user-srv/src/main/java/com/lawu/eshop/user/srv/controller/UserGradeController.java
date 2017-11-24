package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.srv.service.UserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员等级
 */
@RestController
@RequestMapping(value = "grade/")
public class UserGradeController extends BaseController {

	@Autowired
	private UserGradeService userGradeService;

	/**
	 * 根据会员等级值获枚举取获取该等级参与抽奖活动兑换积分
	 * 
	 * @param gradeValue 等级值
	 * @return
	 */
	@RequestMapping(value = "selectLotteryActivityPointByGradeValue", method = RequestMethod.GET)
	public Result<Integer> selectLotteryActivityPointByGradeValue(@RequestParam("gradeValue") Byte gradeValue) {

		Integer lotteryActivityPoint = userGradeService.selectLotteryActivityPointByGradeValue(gradeValue);

		return successGet(lotteryActivityPoint);
	}



}
