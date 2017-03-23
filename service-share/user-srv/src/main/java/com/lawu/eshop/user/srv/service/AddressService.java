package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;
import com.lawu.eshop.user.srv.domain.AddressDO;

/**
 * 地址管理接口
 * @author zhangrc
 * @date 2017/03/22
 *
 */
public interface AddressService {
	
	/**
	 * 添加收货地址
	 * @param address
	 */
	 void insert(AddressParam address);
	 
	 /**
	  * 编辑收货地址
	  * @param address
	  */
	 void update(AddressParam address);
	 
	 /**
	  * 单个查询地址
	  * @return
	  */
	 AddressBO find(Long id);
	 
	 /**
	  * 查询所有地址
	  * @return
	  */
	 List<AddressBO> findAll(Long userId);

	 /**
	  * 查询所有地址
	  * @return
	  */
	 void delete(Long id);
	 
	 

}
