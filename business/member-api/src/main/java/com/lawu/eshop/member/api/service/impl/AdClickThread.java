package com.lawu.eshop.member.api.service.impl;

import java.util.concurrent.Callable;

import com.lawu.eshop.ad.dto.ClickAdPointDTO;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdService;

public class AdClickThread implements Callable<Result<ClickAdPointDTO>>{
	
	
	private AdService adService;
	
	private Long id;
	
	private Long memberId;
	
	private String num;

	
	public AdClickThread(AdService adService,Long id,Long memberId,String num) {
		this.id=id;
		this.memberId=memberId;
		this.num=num;
		this.adService=adService;
	}
	

	/**
	 * 描述：抢赞线程
	 */
	@Override
	public Result<ClickAdPointDTO> call() throws Exception {
		 Result<ClickAdPointDTO> rs=adService.clickAd(id, memberId, num);
		return rs;
	} 
	

}