package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.hystrix.AddressServiceHystrix;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;

/**
 * 地址管理接口
 * @author zhangrc
 * @date 2017/03/22
 *
 */
@FeignClient(value= "user-srv" , fallback = AddressServiceHystrix.class)
public interface AddressService {
	
	/**
	 * 添加收货地址
	 * @param address
	 */
	 @RequestMapping(method = RequestMethod.POST, value = "address/save")
	 Result save(@RequestBody AddressParam address);
	 
	 /**
	  * 编辑收货地址
	  * @param address
	  */
	 @RequestMapping(method = RequestMethod.POST, value = "address/update")
	 Result update(@RequestBody AddressParam address,@RequestParam("id") Long id);
	 
	 /**
	  * 单个查询地址
	  * @return
	  */
	@RequestMapping(method = RequestMethod.GET, value = "address/get/{id}")
	Result<AddressDTO> get(@RequestParam("id") Long id);
	 
	 /**
	  * 查询所有地址
	  * @return
	  */
    @RequestMapping(method = RequestMethod.GET, value = "address/selectByUserId/{userId}")
    Result selectByUserId(@RequestParam("userId") Long userId);

    /**
	 *刪除地址
	 * @return
	 */
    @RequestMapping(method = RequestMethod.DELETE, value = "address/remove/{id}")
    Result delete(@RequestParam("id") Long userId);
	 
    /**
	 *修改默认地址
	 *@return
	 */
    @RequestMapping( method = RequestMethod.GET,value = "address/updateDefault")
    Result updateDefault(@RequestParam("id") Long id,@RequestParam("userId") Long userId);

}
