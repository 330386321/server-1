package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：收货地址管理
 * 
 * @author zhangrc
 * @date 2017/03/22
 */
@Api(tags = "address")
@RestController
@RequestMapping(value = "address/")
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;

	@SuppressWarnings("unchecked")
	@Audit(date = "2017-03-29", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "收货地址信息查询", notes = "查询当前用户收货地址列表信息[]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Result<List<AddressDTO>> selectAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		Result<List<AddressDTO>> result = addressService.selectByUserNum(userNum);
		if (!isSuccess(result)) {
			return successGet(result.getRet());
		}

		return successGet(result);
	}

	@Audit(date = "2017-03-29", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "查询单个收货地址", notes = "单个查询收货地址[]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Result<AddressDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
		return addressService.get(id);
	}

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "删除收货地址", notes = "删除收货地址[1002]（张荣成）", httpMethod = "DELETE")
	@ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
	public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		Result rs = addressService.delete(id,userNum);
		if (!isSuccess(rs)) {
			return successGet(rs.getRet());
		}
		return successDelete();
	}

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-03-29", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "添加收货地址", notes = "添加收货地址[]（张荣成）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "addAddress", method = RequestMethod.POST)
	public Result addAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true, value = "收货地址信息") @Validated AddressParam address, BindingResult bindingResult) {
		
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
		
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		Result result = addressService.saveWithUserNum(userNum, address);
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}

		return successCreated();
	}

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-03-29", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "修改收货地址", notes = "修改收货地址[]（张荣成）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public Result update(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "收货地址id") Long id, @ModelAttribute @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		return addressService.update(address, id, userNum);

	}

	@SuppressWarnings("rawtypes")
	@Audit(date = "2017-03-29", reviewer = "孙林青")
	@Authorization
	@ApiOperation(value = "收货默认地址修改", notes = "修改收货默认地址[]（张荣成）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "updateDefault/{id}", method = RequestMethod.PUT)
	public Result updateDefault(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		return addressService.updateDefault(id, userNum);
	}

}
