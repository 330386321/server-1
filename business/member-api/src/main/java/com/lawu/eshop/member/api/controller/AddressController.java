package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 描述：收货地址管理
 * @author zhangrc
 * @date 2017/03/22
 */
@Api(tags = "address")
@RestController
@RequestMapping(value = "address/")
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "收货地址信息查询", notes = "查询当前用户收货地址列表信息[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<AddressDTO>> selectAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result<List<AddressDTO>> addressDTOS = addressService.selectAddress(userId);
        return addressDTOS;
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "查询单个收货地址", notes = "单个查询收货地址[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result<AddressDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
        Result<AddressDTO> addressDTO = addressService.get(id);
        return addressDTO;
    }

    @Authorization
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址[1002]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                         @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
        Result rs = addressService.delete(id);
        return rs;
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "addAddress", method = RequestMethod.POST)
    public Result addAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                             @ModelAttribute @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result rs = addressService.save(userId, address);
        return rs;
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "修改收货地址", notes = "修改收货地址[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public Result update(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                         @PathVariable @ApiParam(required = true, value = "收货地址id") Long id,
                         @ModelAttribute @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
        Result rs = addressService.update(address, id);
        return rs;

    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "收货默认地址修改", notes = "修改收货默认地址[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateDefault/{id}", method = RequestMethod.PUT)
    public Result updateDefault(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result rs = addressService.updateDefault(id, userId);
        return rs;
    }


}
