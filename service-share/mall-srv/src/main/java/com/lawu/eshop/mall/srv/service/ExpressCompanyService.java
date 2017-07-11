package com.lawu.eshop.mall.srv.service;

import java.util.List;

import com.lawu.eshop.mall.srv.bo.ExpressCompanyBO;

/**
 * 快递公司服务接口
 *
 * @author Sunny
 * @date 2017/3/27
 */
public interface ExpressCompanyService {
	
	/**
	 * 查询全部快递公司，根据ordinal排序
	 * 
	 * @return
	 */
	List<ExpressCompanyBO> list();
	
	/**
	 * 查询快递公司，根据ordinal排序
	 * 
	 * @param isShow
	 *            是否显示
	 * @return
	 */
	List<ExpressCompanyBO> list(Boolean isShow);
	
	/**
	 * 根据快递公司id查询快递公司
	 * 
	 * @param id
	 *            快递公司id
	 * @return
	 */
	ExpressCompanyBO get(Integer id);

}
