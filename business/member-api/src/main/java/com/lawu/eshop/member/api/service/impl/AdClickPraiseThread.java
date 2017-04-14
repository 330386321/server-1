package com.lawu.eshop.member.api.service.impl;

import java.util.concurrent.Callable;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdService;

public class AdClickPraiseThread implements Callable<Result>{
	
	
	private AdService adService;
	
	private Long id;
	
	private Long memberId;
	
	private String num;

	
	public AdClickPraiseThread(AdService adService,Long id,Long memberId,String num) {
		this.id=id;
		this.memberId=memberId;
		this.num=num;
		this.adService=adService;
	}
	

	/**
	 * 描述：抢赞线程
	 */
	@Override
	public Result call() throws Exception {
		Result rs=adService.clickPraise(id,memberId,num);
		return rs;
	} 
	

}