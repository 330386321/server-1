package com.lawu.eshop.ad.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.srv.service.PointPoolService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

/**
 * 
 * @author zhangrc
 * @date 2017/4/11
 *
 */
@RestController
@RequestMapping(value = "pointPool/")
public class PointPoolController extends BaseController{

	@Autowired
	private PointPoolService pointPoolService;
	
	/**
	 * 根据广告id 查询e赞前三名会员
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "selectMemberList", method = RequestMethod.POST)
    public Result<List<Long>> selectMemberList(@RequestParam Long id) {
		List<Long> memberIds = pointPoolService.selectMemberList(id);
		return  successAccepted(memberIds);
    }
}
