package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.member.api.service.MerchantFavoredService;
import com.lawu.eshop.member.api.service.MerchantStoreImageService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ShoppingProductService;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import com.lawu.eshop.user.dto.MerchantStoreImageDTO;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.ShoppingStoreDetailDTO;
import com.lawu.eshop.user.dto.StoreDetailDTO;
import com.lawu.eshop.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
@Api(tags = "merchantDetail")
@RestController
@RequestMapping(value = "merchantDetail/")
public class MerchantDetailController extends BaseController {

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantStoreImageService merchantStoreImageService;

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    @Autowired
    private ShoppingProductService shoppingProductService;

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "要买单门店详情", notes = "要买单门店详情(用户评价、更多商家查询其他接口)。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "store/{id}", method = RequestMethod.GET)
    public Result<StoreDetailDTO> storeDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                              @PathVariable @ApiParam(required = true, value = "门店ID") Long id) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<StoreDetailDTO> stoResult = merchantStoreService.getStoreDetailById(id, memberId);
        if (!isSuccess(stoResult)) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        StoreDetailDTO storeDetailDTO = stoResult.getModel();
        Result<MerchantFavoredDTO> merResult = merchantFavoredService.findFavoredByMerchantId(storeDetailDTO.getMerchantId());
        if (isSuccess(merResult)) {
            MerchantFavoredDTO merchantFavoredDTO = merResult.getModel();
            storeDetailDTO.setTypeEnum(com.lawu.eshop.user.constants.MerchantFavoredTypeEnum.getEnum(merchantFavoredDTO.getTypeEnum().val));
            storeDetailDTO.setReachAmount(merchantFavoredDTO.getReachAmount());
            storeDetailDTO.setFavoredAmount(merchantFavoredDTO.getFavoredAmount());
            storeDetailDTO.setDiscountRate(merchantFavoredDTO.getDiscountRate());
            storeDetailDTO.setMerchantFavoredId(merchantFavoredDTO.getId());
            storeDetailDTO.setValidDayTime(merchantFavoredDTO.getValidDayBeginTime() + "-" + merchantFavoredDTO.getValidDayEndTime());
            storeDetailDTO.setPreferentialTime(merchantFavoredDTO.getValidWeekTime() + merchantFavoredDTO.getValidDayBeginTime() + "-" + merchantFavoredDTO.getValidDayEndTime());
            storeDetailDTO.setValidTime(DateUtil.getDateFormat(merchantFavoredDTO.getEntireBeginTime()) + "至" + DateUtil.getDateFormat(merchantFavoredDTO.getEntireEndTime()));
            storeDetailDTO.setIsOverdue(DateUtil.isOverdue(merchantFavoredDTO.getEntireEndTime()));
        }
        return successGet(storeDetailDTO);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "商家相册", notes = "商家相册(店内环境照)。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listMerchantStoreImage/{merchantId}", method = RequestMethod.GET)
    public Result<List<MerchantStoreImageDTO>> listMerchantStoreImage(@PathVariable @ApiParam(required = true, value = "商家ID") Long merchantId) {
        return merchantStoreImageService.listMerchantStoreImageByType(merchantId, MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "要购物门店详情", notes = "要购物门店详情基本信息。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "shoppingStore/{id}", method = RequestMethod.GET)
    public Result<ShoppingStoreDetailDTO> shoppingStoreDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                              @PathVariable @ApiParam(required = true, value = "门店ID") Long id) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        return merchantStoreService.getShoppingStoreDetailById(id, memberId);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "要购物门店详情店铺首页", notes = "要购物门店详情店铺首页。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listHotProduct", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listHotProduct(@ModelAttribute @ApiParam ListShoppingProductParam listShoppingProductParam) {
        return shoppingProductService.listHotProduct(listShoppingProductParam);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "要购物门店详情全部商品", notes = "要购物门店详情全部商品。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listAllProduct", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listAllProduct(@ModelAttribute @ApiParam ListShoppingProductParam listShoppingProductParam) {
        return shoppingProductService.listAllProduct(listShoppingProductParam);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "要购物门店详情最新上架", notes = "要购物门店详情最新上架。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listNewProduct", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listNewProduct(@ModelAttribute @ApiParam ListShoppingProductParam listShoppingProductParam) {
        return shoppingProductService.listNewProduct(listShoppingProductParam);
    }

}
