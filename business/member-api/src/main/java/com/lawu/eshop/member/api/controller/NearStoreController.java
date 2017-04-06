package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.NearStoreService;
import com.lawu.eshop.user.dto.NearStoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Api(tags = "nearStore")
@RestController
@RequestMapping(value = "nearStore/")
public class NearStoreController extends BaseController {

    @Autowired
    private NearStoreService nearStoreService;

    @ApiOperation(value = "猜你喜欢", notes = "猜你喜欢，按距离查询门店信息。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listNearStore", method = RequestMethod.GET)
    public Result sendSms(@RequestParam @ApiParam(required = true, value = "经度") Double longitude,
                          @RequestParam @ApiParam(required = true, value = "纬度") Double latitude) {
        Result result = nearStoreService.listNearStore(longitude, latitude);
        if (!isSuccess(result)) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }

        List<NearStoreDTO> nearStoreDTOS = (List<NearStoreDTO>) result.getModel();
        //TODO 平均得分，人均消费

        return successGet(nearStoreDTOS);
    }
}
