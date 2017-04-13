package com.lawu.eshop.member.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdService;

public class AdClickPraiseThread implements Runnable {
	
	
	@Autowired
	private AdService adService;
	
	private Long id;
	
	private Long memberId;
	
	private String userNum;

	
	public AdClickPraiseThread(Long id,Long memberId,String userNum) {
		this.id=id;
		this.memberId=memberId;
		this.userNum=userNum;
	}
	

	/**
	 * 描述：抢赞线程
	 */
	@Override
	public void run() {
		Result rs=adService.clickPraise(id,memberId,userNum);
	} 
	

}