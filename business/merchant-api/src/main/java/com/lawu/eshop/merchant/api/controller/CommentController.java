package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.CommentService;
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
@Api("comment")
@RestController
@RequestMapping("comment/")
public class CommentController extends BaseController{

    @Autowired
    private CommentService commentService;

    /**
     * 商家回复商品评价
     * @param commentId
     * @param replyContent
     * @param token
     * @return
     */
    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "商家回复商品评价", notes = "商家回复商品评价 [1002，1005,1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "replyProductComment/{commentId}",method = RequestMethod.PUT)
    public Result replyProductComment(@PathVariable("commentId") @ApiParam(required = true, value = "评价ID") Long commentId,
                                      @RequestParam("replyContent") @ApiParam(required = true, value = "回复内容") String replyContent,
                                      @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token){

        return  commentService.replyProductComment(commentId,replyContent);
    }

    /**
     * 商家回复商品评价
     * @param commentId
     * @param replyContent
     * @param token
     * @return
     */
    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "商家回复商品评价", notes = "商家回复商品评价 [1002，1005,1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "replyMerchantComment/{commentId}",method = RequestMethod.PUT)
    public Result replyMerchantComment(@PathVariable("commentId") @ApiParam(required = true, value = "评价ID") Long commentId,
                                      @RequestParam("replyContent") @ApiParam(required = true, value = "回复内容") String replyContent,
                                      @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token){

        return  commentService.replyMerchantComment(commentId,replyContent);
    }
}
