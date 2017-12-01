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
import com.lawu.eshop.operator.api.service.SeckillActivityProductService;
import com.lawu.eshop.product.dto.SeckillActivityProductInfoDTO;
import com.lawu.eshop.product.param.SeckillActivityProductAuditParam;
import com.lawu.eshop.product.param.SeckillActivityProductNotPassedParam;
import com.lawu.eshop.product.param.SeckillActivityProductPageSearchParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import om.lawu.eshop.shiro.util.UserUtil;

/**
 * 抢购活动商品控制器
 * 
 * @author jiangxinjun
 * @createDate 2017年11月27日
 * @updateDate 2017年11月27日
 */
@Api(tags = "seckillActivityProduct")
@RestController
@RequestMapping(value = "seckillActivityProduct/")
public class SeckillActivityProductController extends BaseController {
    
    @Autowired
    private SeckillActivityProductService seckillActivityProductService;
    
    /**
     * 根据id和查询参数分页查询抢购活动商品列表
     * 
     * @param id 抢购活动id
     * @param param 分页查询参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @SuppressWarnings("unchecked")
    @PageBody
    @ApiOperation(value = "查询抢购活动商品", notes = "查询抢购活动商品(蒋鑫俊)[1004]", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("seckillActivityProduct:pageSearch")
    @RequestMapping(value = "pageSearch/{id}", method = RequestMethod.GET)
    public Result<Page<SeckillActivityProductInfoDTO>> pageSearch(@PathVariable("id") Long id, @ModelAttribute @Validated SeckillActivityProductPageSearchParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return successGet(seckillActivityProductService.pageSearch(id, param));
    }
    
    /**
     * 抢购活动商品
     * 审核不通过
     * 
     * @param id 抢购活动商品id
     * @param param 反馈参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "抢购活动商品不通过", notes = "抢购活动商品不通过(蒋鑫俊)[1001, 1004, 1100]", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("seckillActivityProduct:notPassed")
    @RequestMapping(value = "notPassed/{id}", method = RequestMethod.PUT)
    public Result<?> notPassed(@PathVariable("id") Long id, @ModelAttribute @Validated SeckillActivityProductNotPassedParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return successCreated(seckillActivityProductService.notPassed(id, param));
    }
    
    /**
     * 审核抢购活动商品
     * 
     * @param id 抢购活动商品id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    @ApiOperation(value = "抢购活动商品审核", notes = "抢购活动商品审核(蒋鑫俊)[1001, 1100]", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("seckillActivityProduct:audit")
    @RequestMapping(value = "audit/{id}", method = RequestMethod.PUT)
    public Result<?> audit(@PathVariable("id") Long id) {
        String account = UserUtil.getCurrentUserAccount();
        SeckillActivityProductAuditParam param = new SeckillActivityProductAuditParam();
        param.setAuditorAccount(account);
        return successCreated(seckillActivityProductService.audit(id, param));
    }
}
