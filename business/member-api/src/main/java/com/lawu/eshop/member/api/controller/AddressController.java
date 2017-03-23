package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 描述：收货地址管理
 * @author zhangrc
 * @date 2017/03/22
 *
 */
@Api(tags = "address")
@RestController
@RequestMapping(value = "address/")
public class AddressController extends BaseController {
	
	 @Autowired
	 private AddressService addressService;
	
	 /**
	  * 收货地址列表
	  * @return
	  */
	//@Authorization
	@RequestMapping(value = "listByUserId", method = RequestMethod.GET)
    public List<AddressDTO> listByUserId(@RequestParam @ApiParam(required = true, value = "用户id") Long userId) {
		List<AddressDTO> addressDTO = addressService.listByUserId(userId);
        return addressDTO;
    }
	
	 /**
	  * 收货地址单个查询 	
	  * @return
	  */
	//@Authorization
	@RequestMapping(value = "get", method = RequestMethod.GET)
    public AddressDTO get(@RequestParam  @ApiParam (required = true, value = "收货地址id") Long id) {
		AddressDTO addressDTO=addressService.get(id);
		return addressDTO;
    }
	
	/**
	  * 收货地址删除
	  * @return
	  */
	//@Authorization
	@ApiOperation(value = "delete", notes = "删除", httpMethod = "GET")
	@RequestMapping(value = "delete", method = RequestMethod.GET)
    public void delete(@RequestParam @ApiParam (required = true, value = "收货地址id") Long id) {
		addressService.delete(id);
    }
	
	/**
	  * 收货地址添加
	  * @return
	  */
	//@Authorization
	@ApiOperation(value = "save", notes = "添加", httpMethod = "POST")
	@RequestMapping(value = "save", method = RequestMethod.POST)
    public void insert(@ModelAttribute  @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
	addressService.save(address);
    }

	/**
	  * 收货地址修改
	  * @return
	  */
	//@Authorization
	@RequestMapping(value = "update", method = RequestMethod.GET)
    public void update(@ModelAttribute @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
	   addressService.update(address);
    }
	
	

}
