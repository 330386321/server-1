package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 收货地址列表
     * @audit  sunlinqing 2016.03.29
     */
    @Authorization
    @ApiOperation(value = "收货地址信息查询", notes = "根据会员id获取收货地址列表信息[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    public Result<List<AddressDTO>> selectAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result<List<AddressDTO>> addressDTOS = addressService.selectAddress(userId);
        return addressDTOS;
    }

    /**
     * 收货地址单个查询
     * @audit  sunlinqing 2016.03.29
     */
    @Authorization
    @ApiOperation(value = "查询单个收货地址", notes = "单个查询收货地址[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result<AddressDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                  @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
        Result<AddressDTO> addressDTO = addressService.get(id);
        return addressDTO;
    }

    /**
     * 收货地址删除
     * @audit  sunlinqing 2016.03.29
     */
    @Authorization
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址[1002]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                         @PathVariable @ApiParam(required = true, value = "收货地址id") Long id) {
        Result rs = addressService.delete(id);
        return rs;
    }

    /**
     * 收货地址添加
     * @audit  sunlinqing 2016.03.29
     */
    @Authorization
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址[1000|1001]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "addAddress", method = RequestMethod.POST)
    public Result addAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                             @ModelAttribute @ApiParam(required = true, value = "收货地址信息") AddressParam address) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result rs = addressService.save(userId, address);
        return rs;
    }

    /**
     * 收货地址修改
     * @audit  sunlinqing 2016.03.29
     */
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

    /**
     * 收货默认地址修改
     * @audit  sunlinqing 2016.03.29
     */
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
