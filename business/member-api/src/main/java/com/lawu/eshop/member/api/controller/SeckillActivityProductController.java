package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.SeckillActivityProductService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.param.ActivityProductBuyQueryParam;
import com.lawu.eshop.product.dto.SeckillActivityProductBuyPageDTO;
import com.lawu.eshop.product.dto.SeckillActivityProductInformationDTO;
import com.lawu.eshop.product.dto.foreign.SeckillActivityProductInformationForeignDTO;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 抢购活动商品控制
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
@Api(tags = "seckillActivityProduct")
@RestController
@RequestMapping(value = "seckillActivityProduct/")
public class SeckillActivityProductController extends BaseController {
    
    @Autowired
    private SeckillActivityProductService seckillActivityProductService;
    
    @Autowired
    private ShoppingOrderService shoppingOrderService;

    @Audit(date = "2017-11-24", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "查询当前活动所有商品", notes = "根据抢购活动id分页查询活动下的所有商品[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "page/{id}", method = RequestMethod.GET)
    public Result<Page<SeckillActivityProductBuyPageDTO>> page(@PathVariable("id") Long id, @ModelAttribute @Validated SeckillActivityProductPageQueryParam param, BindingResult bindingResult) {
        String message = validate(bindingResult);
        if (message != null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        return successGet(seckillActivityProductService.page(id, param));
    }
    
    /**
     * 查询抢购活动商品信息
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    @Audit(date = "2017-11-24", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取活动商品详情", notes = "获取活动商品详情[1004,1100]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "information/{id}", method = RequestMethod.GET)
    public Result<SeckillActivityProductInformationForeignDTO> information(@ApiParam(value = "如果用户有登录就传入") @RequestHeader(name = UserConstant.REQ_HEADER_TOKEN, required = false) String token, @ApiParam(value = "抢购活动商品id") @PathVariable("id") Long id) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<SeckillActivityProductInformationDTO>  result = seckillActivityProductService.information(id, memberId);
        if (!isSuccess(result)) {
            return successGet(result);
        }
        SeckillActivityProductInformationDTO model = result.getModel();
        SeckillActivityProductInformationForeignDTO rtn = new SeckillActivityProductInformationForeignDTO();
        if (memberId != null && memberId != 0L) {
            ActivityProductBuyQueryParam param = new ActivityProductBuyQueryParam();
            param.setActivityProductId(model.getActivityProductId());
            param.setMemberId(memberId);
            Result<Boolean> isBuyResult = shoppingOrderService.isBuy(param);
            if (!isSuccess(isBuyResult)) {
                return successGet(isBuyResult);
            }
            rtn.setBuy(isBuyResult.getModel());
        } else {
            rtn.setBuy(false);
        }
        rtn.setActivityProductId(model.getActivityProductId());
        rtn.setActivityStatus(model.getActivityStatus());
        rtn.setAttentionCount(model.getAttentionCount());
        rtn.setCountdown(model.getCountdown());
        rtn.setMemberLevel(model.getMemberLevel());
        rtn.setProductModelList(model.getProductModelList());
        rtn.setSellingPrice(model.getSellingPrice());
        rtn.setAttention(model.getAttention());
        rtn.setSoldQuantity(model.getSoldQuantity());
        rtn.setExceededAttentionTime(model.getExceededAttentionTime());
        return successGet(rtn);
    }
}
