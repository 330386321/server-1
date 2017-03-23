package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;

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
	 void save(AddressParam address);
	 
	 /**
	  * 编辑收货地址
	  * @param address
	  */
	 void update(AddressParam address);
	 
	 /**
	  * 单个查询地址
	  * @return
	  */
	 AddressBO get(Long id);
	 
	 /**
	  * 查询所有地址
	  * @return
	  */
	 List<AddressBO> listByUserId(Long userId);

	 /**
	  * 查询所有地址
	  * @return
	  */
	 void delete(Long id);
	 
	 /**
	  * 修改默认地址
	  * @param id
	  * @param isDefault
	  */
	 void updateStatus(Long id ,Boolean isDefault,Long userId);

}
