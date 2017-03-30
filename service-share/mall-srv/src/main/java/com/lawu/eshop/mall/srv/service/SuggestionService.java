package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.param.SuggestionParam;

/**
 * 意见反馈服务接口
 *
 * @author Sunny
 * @date 2017/3/24
 */
public interface SuggestionService {
	
	/**
	 * 保存意见反馈
	 * @param param
	 * 
	 * @return 返回生成的id
	 */
	Integer save(String userNum, SuggestionParam param);
	
}
