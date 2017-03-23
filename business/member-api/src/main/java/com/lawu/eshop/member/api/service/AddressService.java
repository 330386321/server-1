package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.member.api.service.hystrix.AddressServiceHystrix;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;

/**
 * 地址管理接口
 * @author zhangrc
 * @date 2017/03/22
 *
 */
@FeignClient(value= "user-srv", fallback = AddressServiceHystrix.class)
public interface AddressService {
	
	/**
	 * 添加收货地址
	 * @param address
	 */
	 @RequestMapping(method = RequestMethod.POST, value = "address/save")
	 void save(@ModelAttribute AddressParam address);
	 
	 /**
	  * 编辑收货地址
	  * @param address
	  */
	 @RequestMapping(method = RequestMethod.GET, value = "address/update")
	 void update(AddressParam address);
	 
	 /**
	  * 单个查询地址
	  * @return
	  */
	@RequestMapping(method = RequestMethod.GET, value = "address/get")
	AddressDTO get(@RequestParam("id") Long id);
	 
	 /**
	  * 查询所有地址
	  * @return
	  */
    @RequestMapping(method = RequestMethod.GET, value = "address/listByUserId")
    List<AddressDTO> listByUserId(@RequestParam("userId") Long userId);

    /**
	 *刪除地址
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET, value = "address/delete")
	void delete(@RequestParam("userId") Long userId);
	 
	 

}
