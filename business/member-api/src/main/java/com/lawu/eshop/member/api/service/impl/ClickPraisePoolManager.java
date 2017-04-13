package com.lawu.eshop.member.api.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 抢赞线程池 
 * @author zhangrc
 * @date 2017-4-11
 *
 */
public class ClickPraisePoolManager {
	
	private static ClickPraisePoolManager clientPoolManager;

	private static ExecutorService executor;

	/**
	 * 描述：私有构造函数
	 */
	private ClickPraisePoolManager() {
		// 初始化线程池
		if (executor == null) {
			executor = Executors.newFixedThreadPool(getProcessorsSize());
		}
	}

	/**
	 * 描述：懒汉式
	 */
	public synchronized static ClickPraisePoolManager getInstance() {
		if (clientPoolManager == null) {
			clientPoolManager = new ClickPraisePoolManager();
		}
		return clientPoolManager;
	}

	/**
	 * 描述：加入线程，但是最多只会生成getProcessorsSize()个线程同时处理
	 */
	public void addThread(Runnable thread) {
		if (null == thread) {
			return;
		}
		// 同时加入线程池,执行线程
		if (executor.isShutdown()) {
			executor = Executors.newFixedThreadPool(getProcessorsSize());
		}
		executor.submit(thread);
	}
	
	
	/**
	 * 描述：获取计算机处理器的线程数，最大为100
	 * 
	 */
	public Integer getProcessorsSize() {
		return Math.min(Runtime.getRuntime().availableProcessors()*12, 96);
	}

}
