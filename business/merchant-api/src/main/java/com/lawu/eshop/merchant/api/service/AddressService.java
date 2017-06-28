package com.lawu.eshop.merchant.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;

/**
 * 地址管理接口
 * 
 * @author zhangrc
 * @date 2017/03/22
 *
 */
@FeignClient(value = "user-srv")
public interface AddressService {

	/**
	 * 添加收货地址
	 * 
	 * @deprecated
	 * @param address
	 */
	@Deprecated
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "address/save")
	Result save(@RequestParam("userId") Long id, @RequestBody AddressParam address);

	/**
	 * 编辑收货地址
	 * 
	 * @param address
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "address/update/{id}")
	Result update(@RequestBody AddressParam address, @PathVariable("id") Long id);

	/**
	 * 单个查询地址
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "address/get/{id}")
	Result<AddressDTO> get(@PathVariable("id") Long id);

	/**
	 * 查询所有地址
	 * 
	 * @return
	 */
	@Deprecated
	@RequestMapping(method = RequestMethod.GET, value = "address/selectByUserId")
	Result<List<AddressDTO>> selectByUserId(@RequestParam("userId") Long userId);

	/**
	 * 刪除地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "address/remove/{id}")
	Result delete(@PathVariable("id") Long userId, @RequestParam("userNum") String userNum);

	/**
	 * 修改默认地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "address/updateDefault/{id}")
	Result updateDefault(@PathVariable("id") Long id, @RequestParam("userNum") String userNum);

	/**
	 * 根据用户编号 查询用户所有地址
	 * 
	 * @param userNum
	 *            用户编号
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "address/selectByUserNum/{userNum}", method = RequestMethod.GET)
	Result<List<AddressDTO>> selectByUserNum(@PathVariable("userNum") String userNum);
	
	/**
	 * 根据用户编号 添加收货地址
	 * 
	 * @param userNum
	 *            用户编号
	 * @param param
	 *            保存地址参数
	 * @param bindingResult
	 *            参数验证结果
	 * @author Sunny
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "address/saveWithUserNum/{userNum}", method = RequestMethod.POST)
	Result saveWithUserNum(@PathVariable("userNum") String userNum, @RequestBody @Validated AddressParam addressDO);
}
