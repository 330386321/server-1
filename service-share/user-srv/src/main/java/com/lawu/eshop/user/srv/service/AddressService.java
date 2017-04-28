package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;

/**
 * 地址管理接口
 * 
 * @author zhangrc
 * @date 2017/03/22
 *
 */
public interface AddressService {

	/**
	 * 添加收货地址
	 * 
	 * @param address
	 */
	@Deprecated
	Integer save(Long userId, AddressParam address);

	/**
	 * 编辑收货地址
	 * 
	 * @param address
	 */
	Integer update(AddressParam address, Long id);

	/**
	 * 单个查询地址
	 * 
	 * @return
	 */
	AddressBO get(Long id);

	/**
	 * 查询所有地址
	 * 
	 * @return
	 */
	@Deprecated
	List<AddressBO> selectByUserId(Long userId);

	/**
	 * 查询所有地址
	 * 
	 * @return
	 */
	Integer remove(Long id);

	/**
	 * 修改默认地址
	 * 
	 * @param id
	 * @param isDefault
	 */
	Integer updateDefault(Long id, String userNum);

	/**
	 * 根据用户编号 查询用户所有地址
	 * 
	 * @param userNum
	 * @return
	 * @author Sunny
	 */
	List<AddressBO> selectByUserNum(String userNum);

	/**
	 * 根据用户编号
	 * 添加收货地址
	 * 
	 * @param userNum
	 * @param param
	 * @author Sunny 
	 */
	int saveWithUserNum(String userNum, AddressParam param);
	
	/**
	 * 验证收货地址
	 * @param id
	 * @param userNum
	 * @return
	 */
	boolean isCheckAddress(Long id,String userNum);
	
}
