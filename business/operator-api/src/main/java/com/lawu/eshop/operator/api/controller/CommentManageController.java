package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentOperatorDTO;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.operator.api.service.CommentProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@Api(value = "commentManage")
@RestController
@RequestMapping("commentManage/")
public class CommentManageController extends BaseController{

    @Autowired
    private CommentProductService commentProductService;

    @ApiOperation(value = "评价商品列表(全部)", notes = "评价商品列表 [1002，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProductListOperator", method = RequestMethod.GET)
    public Result<Page<CommentOperatorDTO>> getCommentProductListOperator(@ModelAttribute @ApiParam CommentListParam listParam) {
        return commentProductService.getCommentProductListOperator(listParam);
    }

    @ApiOperation(value = "评价商家列表(全部)", notes = "评价商品列表 [1002，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentMerchantListOperator", method = RequestMethod.GET)
    public Result<Page<CommentOperatorDTO>> getCommentMerchantListOperator(@ModelAttribute @ApiParam CommentListParam listParam) {
        return commentProductService.getCommentMerchantListOperator(listParam);
    }

    @ApiOperation(value = "屏蔽商品评价", notes = "屏蔽商品评价 [1004，1000]（章勇）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "delCommentProductInfo/{commentId}", method = RequestMethod.DELETE)
    public Result delCommentProductInfo(@PathVariable("commentId") @ApiParam(value = "评论ID",required = true) Long commentId){
        if(commentId == null){
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        return  successDelete(commentProductService.delCommentProductInfo(commentId));
    }

    @ApiOperation(value = "屏蔽商家评价", notes = "屏蔽商家评价 [1004，1000]（章勇）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "delCommentMerchantInfo/{commentId}", method = RequestMethod.DELETE)
    public Result delCommentMerchantInfo(@PathVariable("commentId") @ApiParam(value = "评论ID",required = true) Long commentId){
        if(commentId == null){
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        return  successDelete(commentProductService.delCommentMerchantInfo(commentId));
    }

}
