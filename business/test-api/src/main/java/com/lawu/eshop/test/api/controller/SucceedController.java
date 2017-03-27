package com.lawu.eshop.test.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leach
 * @date 2017/3/27
 */
@Api(tags = "succeed")
@RestController
@RequestMapping(value = "succeed/")
public class SucceedController extends BaseController {


    @ApiOperation(value = "GET请求成功", notes = "OK ，服务器成功返回用户请求的数据。[]（孙林青）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "{pathParam1}/get/{pathParam2}", method = RequestMethod.GET)
    public Result<TestDTO> getSuccess(@PathVariable @ApiParam(required = true, value = "路径参数1") String pathParam1,
                                      @PathVariable @ApiParam(required = true, value = "路径参数2") Long pathParam2,
                                  @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setRemark("备注");
        return successGet(testDTO);
    }

    @ApiOperation(value = "POST请求成功", notes = "用户新建数据成功。[]（孙林青）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "post/{pathParam}", method = RequestMethod.POST)
    public Result<TestDTO> postSuccess(@PathVariable @ApiParam(required = true, value = "路径参数") String pathParam,
                                      @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setRemark("备注");
        return successCreated(testDTO);
    }

    @ApiOperation(value = "PUT请求成功", notes = "用户修改数据成功。[]（孙林青）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "put/{pathParam}", method = RequestMethod.PUT)
    public Result<TestDTO> putSuccess(@PathVariable @ApiParam(required = true, value = "路径参数") String pathParam,
                                       @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setRemark("备注");
        return successCreated(testDTO);
    }

    @ApiOperation(value = "异步处理请求成功", notes = "表示一个请求已经进入后台排队（异步任务）。[]（孙林青）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "queue/{pathParam}", method = RequestMethod.POST)
    public Result<TestDTO> queueSuccess(@PathVariable @ApiParam(required = true, value = "路径参数") String pathParam,
                                      @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setRemark("备注");
        return successCreated(testDTO);
    }

    @ApiOperation(value = "DELETE成功。", notes = "用户删除数据成功。[]（孙林青）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "delete/{pathParam}", method = RequestMethod.DELETE)
    public Result<TestDTO> deleteSuccess(@PathVariable @ApiParam(required = true, value = "路径参数") String pathParam,
                                        @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setRemark("备注");
        return successDelete(testDTO);
    }

    @ApiOperation(value = "DELETE非法请求返回。", notes = "用户删除数据失败，可能为客户端传入信息出错。[1003]（孙林青）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "deleteErr/{pathParam}", method = RequestMethod.DELETE)
    public Result<TestDTO> deleteErr(@PathVariable @ApiParam(required = true, value = "路径参数") String pathParam,
                                         @RequestParam @ApiParam(required = true, value = "请求参数") String reqParam) {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(1L);
        testDTO.setRemark("备注");
        return successCreated(ResultCode.RESOURCE_NOT_FOUND);
    }


}

