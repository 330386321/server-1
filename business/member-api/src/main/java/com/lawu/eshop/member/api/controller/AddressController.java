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
@RequestMapping(value = "/")
public class AddressController extends BaseController {
	
	 @Autowired
	 private AddressService addressService;
	
	 /**
	  * 收货地址列表
	  * @return
	  */
	//@Authorization
	@RequestMapping(value = "findAll", method = RequestMethod.GET)
    public List<AddressDTO> findAll(@RequestParam @ApiParam(required = true, value = "用户id") Long userId) {
		List<AddressDTO> addressDTO = addressService.findAll(userId);
        return addressDTO;
    }
	
	 /**
	  * 收货地址单个查询
	  * @return
	  */
	//@Authorization
	@RequestMapping(value = "find", method = RequestMethod.GET)
    public AddressDTO find(@RequestParam  @ApiParam (required = true, value = "收货地址id") Long id) {
		AddressDTO addressDTO=addressService.find(id);
		return addressDTO;
    }
	
	/**
	  * 收货地址删除
	  * @return
	  */
	//@Authorization
	@RequestMapping(value = "delete", method = RequestMethod.GET)
    public void delete(@RequestParam @ApiParam (required = true, value = "收货地址id") Long id) {
		addressService.delete(id);
    }
	
	/**
	  * 收货地址添加
	  * @return
	  */
	//@Authorization
	@ApiOperation(value = "insert", notes = "insert", httpMethod = "POST")
	@RequestMapping(value = "insert", method = RequestMethod.POST)
    public void insert(@ModelAttribute  @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
	addressService.insert(address);
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
