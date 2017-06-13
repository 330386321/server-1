package com.lawu.eshop.ad.constants;

public class AdPraiseConfig {
	
	 /**
     * 核心线程数，指保留的线程池大小
     */
	public final static Integer CORE_POOL_SIZE=20;
    
    /**
     *  指的是线程池的最大数
     */
	public final static Integer MAXIMUM_POLL_SIZE=60;
    
    /**
     * 指的是空闲线程结束的超时时间
     */ 
	public final static Integer KEEP_ALIVE_TIME=1;
    
    /**
     * 概率：分子
     */
	public final static Integer A=5; 
    
    /**
     * 概率：分母
     */
	public final static Integer B=1000;
	
	/**
	 * 点击多少次才有一次真的请求服务端
	 */
	public final static Integer CLICK_PRAISE_AD_TIMES=40;

}
