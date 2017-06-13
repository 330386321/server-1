package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/5/10.
 */
@Api(tags = "index")
@RestController
@RequestMapping(value = "index/")
public class IndexController extends BaseController {

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AdService adService;

    @ApiOperation(value = "更新门店索引", notes = "更新门店索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("index:store")
    @RequestMapping(value = "updateStoreIndex", method = RequestMethod.GET)
    public Result updateStoreIndex() {
          return merchantStoreService.rebuildStoreIndex();
    }

    @ApiOperation(value = "更新商品索引", notes = "更新商品索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("index:product")
    @RequestMapping(value = "updateProductIndex", method = RequestMethod.GET)
    public Result updateProductIndex() {
        return productService.rebuildProductIndex();
    }

    @ApiOperation(value = "更新广告索引", notes = "更新广告索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("index:ad")
    @RequestMapping(value = "updateAdIndex", method = RequestMethod.GET)
    public Result updateAdIndex() {
        return adService.rebuildAdIndex();
    }

    @ApiOperation(value = "删除无效门店索引", notes = "更新门店索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("indexDel:store")
    @RequestMapping(value = "delInvalidStoreIndex", method = RequestMethod.GET)
    public Result delInvalidStoreIndex() {
        return merchantStoreService.delInvalidStoreIndex();
    }

    @ApiOperation(value = "删除无效商品索引", notes = "更新商品索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("indexDel:product")
    @RequestMapping(value = "delInvalidProductIndex", method = RequestMethod.GET)
    public Result delInvalidProductIndex() {
        return productService.delInvalidProductIndex();
    }

    @ApiOperation(value = "删除无效广告索引", notes = "更新广告索引。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("indexDel:ad")
    @RequestMapping(value = "delInvalidAdIndex", method = RequestMethod.GET)
    public Result delInvalidAdIndex() {
        return adService.delInvalidAdIndex();
    }

}
