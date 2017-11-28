package com.lawu.eshop.operator.api.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.SeckillActivityService;
import com.lawu.eshop.product.dto.SeckillActivityDetailsDTO;
import com.lawu.eshop.product.dto.SeckillActivityInfoDTO;
import com.lawu.eshop.product.param.SeckillActivityPageQueryParam;
import com.lawu.eshop.product.param.SeckillActivityUpdateParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 抢购活动控制器
 * 
 * @author jiangxinjun
 * @createDate 2017年11月27日
 * @updateDate 2017年11月27日
 */
@Api(tags = "seckillActivity")
@RestController
@RequestMapping(value = "seckillActivity/")
public class SeckillActivityController extends BaseController {
    
    @Autowired
    private SeckillActivityService seckillActivityService;
    
    /**
     * 根据查询参数分页查询活动列表
     * 
     * @param param
     * @param bindingResult
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @SuppressWarnings("unchecked")
    @PageBody
    @ApiOperation(value = "查询抢购活动", notes = "分页查询抢购活动(蒋鑫俊)[1004]", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("seckillActivity:page")
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<SeckillActivityInfoDTO>> page(@ModelAttribute @Validated SeckillActivityPageQueryParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return successGet(seckillActivityService.page(param));
    }
    
    /**
     * 根据id查询活动详情
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "查询抢购活动详情", notes = "查询抢购活动详情(蒋鑫俊)[1100]", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("seckillActivity:detail")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result<SeckillActivityDetailsDTO> detail(@PathVariable("id") Long id) {
        return successCreated(seckillActivityService.detail(id));
    }
    
    /**
     * 根据id删除抢购活动   
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "删除抢购活动", notes = "删除抢购活动(蒋鑫俊)[1001, 1100]", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequiresPermissions("seckillActivity:delete")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable("id") Long id) {
        Result<?> result = seckillActivityService.delete(id);
        if (!isSuccess(result)) {
            return successCreated(result);
        }
        return successDelete();
    }
    
    /**
     * 根据id下架抢购活动
     * 下架之后抢购的活动的状态为已结束
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "下架抢购活动", notes = "下架抢购活动(蒋鑫俊)[1001, 1100]", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequiresPermissions("seckillActivity:down")
    @RequestMapping(value = "down/{id}", method = RequestMethod.PUT)
    public Result<?> down(@PathVariable("id") Long id) {
        return successCreated(seckillActivityService.down(id));
    }
    
    /**
     * 根据id发布抢购活动   
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "发布抢购活动", notes = "发布抢购活动(蒋鑫俊)[1001, 1100]", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequiresPermissions("seckillActivity:release")
    @RequestMapping(value = "release/{id}", method = RequestMethod.PUT)
    public Result<?> release(@PathVariable("id") Long id) {
        return successCreated(seckillActivityService.release(id));
    }
    
    /**
     * 根据id更新抢购活动
     * 
     * @param id 抢购活动id
     * @param param 抢购活动更新参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "更新抢购活动", notes = "更新抢购活动(蒋鑫俊)[1001, 1100]", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("seckillActivity:update")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Result<?> update(@PathVariable("id") Long id, @ModelAttribute @Validated SeckillActivityUpdateParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return successCreated(seckillActivityService.update(id, param));
    }
    
    /**
     * 根据id审核抢购活动
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "抢购活动审核", notes = "抢购活动审核(蒋鑫俊)[1001, 1100]", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("seckillActivity:audit")
    @RequestMapping(value = "audit/{id}", method = RequestMethod.PUT)
    public Result<?> audit(@PathVariable("id") Long id) {
        return successCreated(seckillActivityService.audit(id));
    }
}
