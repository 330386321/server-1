package com.lawu.eshop.ad.srv.thread;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.ad.srv.service.AdService;

public class AdClickPraiseThread implements Runnable {
	
	
	@Autowired
	private AdService adService;
	
	private Long id;

	
	public AdClickPraiseThread(Long id) {
		this.id=id;
	}
	

	/**
	 * 描述：抢赞
	 */
	@Override
	public void run() {
		adService.clickPraise(id);
	}
	
	
	

}