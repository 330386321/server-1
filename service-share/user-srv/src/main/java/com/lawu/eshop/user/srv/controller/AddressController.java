package com.lawu.eshop.user.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;
import com.lawu.eshop.user.srv.converter.AddressConverter;
import com.lawu.eshop.user.srv.service.AddressService;

/**
 * 描述：收货地址管理
 * 
 * @author zhangrc
 * @date 2017/03/22
 *
 */
@RestController
@RequestMapping(value = "address/")
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;

	/**
	 * 收货地址列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	@RequestMapping(value = "selectByUserId", method = RequestMethod.GET)
	public Result selectByUserId(@RequestParam Long userId) {
		List<AddressBO> addressBOS = addressService.selectByUserId(userId);
		return successAccepted(AddressConverter.convertListDOTS(addressBOS));
	}

	/**
	 * 增加收货地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestParam Long userId, @RequestBody AddressParam addressDO) {
		Integer i = addressService.save(userId, addressDO);
		if (i > 0) {
			return successCreated(ResultCode.SUCCESS);
		} else {
			return successCreated(ResultCode.SAVE_FAIL);
		}

	}

	/**
	 * 根据用户编号 查询用户所有地址
	 * 
	 * @param userNum
	 *            用户编号
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "selectByUserNum/{userNum}", method = RequestMethod.GET)
	public Result<List<AddressDTO>> selectByUserNum(@PathVariable("userNum") String userNum) {

		List<AddressBO> addressBOS = addressService.selectByUserNum(userNum);

		return successGet(AddressConverter.convertListDOTS(addressBOS));
	}

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
	@RequestMapping(value = "saveWithUserNum/{userNum}", method = RequestMethod.POST)
	public Result saveWithUserNum(@PathVariable("userNum") String userNum, @RequestBody @Validated AddressParam addressDO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError error : bindingResult.getAllErrors()) {
				if (sb.length() > 0) {
					sb.append("||");
				}
				sb.append(error.getDefaultMessage());
			}

			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, sb.toString());
		}

		int result = addressService.saveWithUserNum(userNum, addressDO);

		if (result != ResultCode.SUCCESS) {
			return successCreated(result);
		}

		return successCreated();
	}

	/**
	 * 单个查询地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Result<AddressDTO> get(@PathVariable Long id) {
		AddressBO addressBO = addressService.get(id);
		return successGet(AddressConverter.convertDTO(addressBO));
	}

	/**
	 * 修改地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public Result update(@RequestBody AddressParam addressParam, @PathVariable Long id) {
		Integer i = addressService.update(addressParam, id);
		if (i > 0) {
			return successCreated(ResultCode.SUCCESS);
		} else {
			return successCreated(ResultCode.FAIL);
		}
	}

	/**
	 * 删除地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
	public Result remove(@PathVariable Long id) {
		Integer i = addressService.remove(id);
		if (i > 0) {
			return successDelete();
		} else {
			return successCreated(ResultCode.FAIL);
		}
	}

	/**
	 * 修改默认地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateDefault/{id}", method = RequestMethod.GET)
	public Result updateDefault(@PathVariable Long id, @RequestParam Long userId) {
		Integer i = addressService.updateDefault(id, userId);
		if (i > 0) {
			return successCreated(ResultCode.SUCCESS);
		} else {
			return successCreated(ResultCode.FAIL);
		}
	}

}
