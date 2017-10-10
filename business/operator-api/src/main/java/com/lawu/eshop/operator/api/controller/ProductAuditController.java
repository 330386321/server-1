package com.lawu.eshop.operator.api.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.LogService;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.operator.constants.LogTitleEnum;
import com.lawu.eshop.operator.constants.ModuleEnum;
import com.lawu.eshop.operator.constants.OperationTypeEnum;
import com.lawu.eshop.operator.param.LogParam;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.user.dto.MerchantViewDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import om.lawu.eshop.shiro.util.UserUtil;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@Api(tags = "productAudit")
@RestController
@RequestMapping(value = "productAudit/")
public class ProductAuditController extends BaseController {

    @Autowired
    private ProductAuditService productAuditService;

    @Autowired
    private LogService logService;

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "商品审列表", notes = "查询所有门店上架中商品  [1002]（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("productAudit:list")
    @PageBody
    @RequestMapping(value = "listProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> listProduct(@RequestBody @ApiParam ListProductParam listProductParam) {
        Result<Page<ProductQueryDTO>> result = productAuditService.listProduct(listProductParam);
        if(result != null && !result.getModel().getRecords().isEmpty()){
            for(ProductQueryDTO productQueryDTO : result.getModel().getRecords()){
                Result<MerchantViewDTO> merchantViewDTOResult = merchantService.getMerchantView(productQueryDTO.getMerchantId());
                if(isSuccess(merchantViewDTOResult)){
                    productQueryDTO.setStoreName(merchantViewDTOResult.getModel().getStoreName());
                    productQueryDTO.setAccount(merchantViewDTOResult.getModel().getAccount());
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "查询商品详情", notes = "编辑商品时，根据商品ID查询商品详情信息，[1002|1003]，（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("productAudit:detail")
    @RequestMapping(value = "getProduct/{id}", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> getProductById(@PathVariable @ApiParam(name = "id", required = true, value = "商品ID") Long id) {
        return productAuditService.selectEditProductById(id);
    }

    @ApiOperation(value = "商品批量下架", notes = "商品批量下架，[1002]。(梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("productAudit:soldOut")
    @RequestMapping(value = "downProduct", method = RequestMethod.PUT)
    public Result downProduct(@RequestParam @ApiParam(required = true, value = "商品ID(多个英文逗号分开)") String ids,
                              @RequestParam @ApiParam(required = true, value = "商家ID") Long merchantId) {
        Result result = productAuditService.updateProductStatus(ids, ProductStatusEnum.PRODUCT_STATUS_DOWN, merchantId);
        if (!isSuccess(result)) {
            return result;
        }

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", ProductStatusEnum.PRODUCT_STATUS_DOWN.getVal());
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.UPDATE);
        logParam.setModuleEnum(ModuleEnum.PRODUCT);
        logParam.setBusinessId(ids);
        logParam.setChangeTitle(LogTitleEnum.PRODUCT_DOWN.getName());
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return result;
    }

    @ApiOperation(value = "商品批量删除", notes = "商品批量删除，[1002]。(梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("productAudit:del")
    @RequestMapping(value = "deleteProduct", method = RequestMethod.PUT)
    public Result deleteProduct(@RequestParam @ApiParam(required = true, value = "商品ID(多个英文逗号分开)") String ids,
                                @RequestParam @ApiParam(required = true, value = "商家ID") Long merchantId) {
        Result result = productAuditService.updateProductStatus(ids, ProductStatusEnum.PRODUCT_STATUS_DEL, merchantId);
        if (!isSuccess(result)) {
            return result;
        }

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", ProductStatusEnum.PRODUCT_STATUS_DEL.getVal());
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.DELETE);
        logParam.setModuleEnum(ModuleEnum.PRODUCT);
        logParam.setBusinessId(ids);
        logParam.setChangeTitle(LogTitleEnum.PRODUCT_DELETE.getName());
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return result;
    }

}
