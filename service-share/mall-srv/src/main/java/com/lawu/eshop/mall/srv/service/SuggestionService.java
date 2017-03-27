package com.lawu.eshop.mall.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.param.SuggestionQueryParam;
import com.lawu.eshop.mall.srv.bo.SuggestionBO;

/**
 * 意见反馈服务接口
 *
 * @author Sunny
 * @date 2017/3/24
 */
public interface SuggestionService {
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param query
	 * @return
	 */
	Page<SuggestionBO> page(SuggestionQueryParam query);
	
	/**
	 * 根据条件查询所有记录数
	 * 
	 * @param query
	 * @return
	 */
	Integer count(SuggestionQueryParam query);
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param query	查询参数
	 * @param isPage 是否分页查询
	 * @return
	 */
	List<SuggestionBO> list(SuggestionQueryParam query,  boolean isPage);
	
	/**
	 * 根据id查询意见反馈
	 * 
	 * @param id
	 * @return
	 */
	SuggestionBO get(Integer id);
	
	/**
	 * 保存意见反馈
	 * @param param
	 * 
	 * @return 返回生成的id
	 */
	Integer save(SuggestionParam param);
	
	/**
	 * 根据id更新意见反馈
	 * 
	 * @param param
	 */
	Integer update(SuggestionParam param, Integer id);
	
	/**
	 * 根据id删除意见反馈
	 * 
	 * @param id
	 */
	Integer remove(Integer id);
	
}
