package com.lawu.eshop.ad.srv.thread;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.ad.srv.service.AdService;

public class AdClickPraiseThread implements Runnable {
	
	
	@Autowired
	private AdService adService;
	
	private Long id;
	
	private Long memberId;

	
	public AdClickPraiseThread(Long id,Long memberId) {
		this.id=id;
		this.memberId=memberId;
	}
	

	/**
	 * 描述：抢赞线程
	 */
	@Override
	public void run() {
		//adService.clickPraise(id,memberId);
	}
	
	
	

}