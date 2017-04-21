package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.*;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.merchant.api.service.CommentService;
import com.lawu.eshop.merchant.api.service.MemberService;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@Api(tags = "comment")
@RestController
@RequestMapping("comment/")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    /**
     * 商家回复商品评价
     *
     * @param commentId
     * @param replyContent
     * @param token
     * @return
     */
    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "商家回复商品评价", notes = "商家回复商品评价 [1002，1005,1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "replyProductComment/{commentId}", method = RequestMethod.PUT)
    public Result replyProductComment(@PathVariable("commentId") @ApiParam(required = true, value = "评价ID") Long commentId,
                                      @RequestParam("replyContent") @ApiParam(required = true, value = "回复内容") String replyContent,
                                      @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {

        return commentService.replyProductComment(commentId, replyContent);
    }

    /**
     * 商家回复商品评价
     *
     * @param commentId
     * @param replyContent
     * @param token
     * @return
     */
    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "商家回复商品评价", notes = "商家回复商品评价 [1002，1005,1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "replyMerchantComment/{commentId}", method = RequestMethod.PUT)
    public Result replyMerchantComment(@PathVariable("commentId") @ApiParam(required = true, value = "评价ID") Long commentId,
                                       @RequestParam("replyContent") @ApiParam(required = true, value = "回复内容") String replyContent,
                                       @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {

        return commentService.replyMerchantComment(commentId, replyContent);
    }

    @ApiOperation(value = "根据商家ID查询商品评论信息", notes = "根据商家ID查询商品评论信息 [1002，1004,1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getProductCommentListByMerchantId", method = RequestMethod.GET)
    public Result<Page<ProductCommentListDTO>> getProductCommentListByMerchantId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true) CommentListParam listparam) throws Exception {
        Long merchantId = UserUtil.getCurrentUserId(getRequest());
        if (listparam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        CommentMerchantListParam param = new CommentMerchantListParam();
        param.setMerchantId(merchantId);
        param.setCurrentPage(listparam.getCurrentPage());
        param.setPageSize(listparam.getPageSize());
        List<ProductCommentListDTO> productCommentListDTOS = new ArrayList<>();
        Page<ProductCommentListDTO> pages = new Page<>();
        Result<Page<CommentDTO>> comments = commentService.getProductCommentListByMerchantId(param);
        if (comments.getModel() == null || comments.getModel().getRecords().isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        for (CommentDTO commentDTO : comments.getModel().getRecords()) {
            //设置评论信息
            ProductCommentListDTO commentListDTO = new ProductCommentListDTO();
            commentListDTO.setContent(commentDTO.getContent());
            commentListDTO.setReplyContent(commentDTO.getReplyContent());
            commentListDTO.setAnonymous(commentDTO.getAnonymous());
            commentListDTO.setGmtCreate(commentDTO.getGmtCreate());
            commentListDTO.setImgUrls(commentDTO.getImgUrls());
            commentListDTO.setId(commentDTO.getId());
            commentListDTO.setGrade(commentDTO.getGrade());
            //查询评论用户信息
            Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());
            commentListDTO.setHeadImg(user.getModel().getHeadimg());
            commentListDTO.setNickName(user.getModel().getNickname());
            commentListDTO.setLevel(user.getModel().getLevel());
            //查询商品信息
            Result<com.lawu.eshop.product.dto.CommentProductInfoDTO> product = productService.selectCommentProductInfo(commentDTO.getProductModelId());
            commentListDTO.setName(product.getModel().getName());
            commentListDTO.setPrice(product.getModel().getPrice());
            commentListDTO.setFeatureImage(product.getModel().getFeatureImage());
            commentListDTO.setSpec(product.getModel().getModelName());
            productCommentListDTOS.add(commentListDTO);
        }
        pages.setCurrentPage(param.getCurrentPage());
        pages.setTotalCount(comments.getModel().getTotalCount());
        pages.setRecords(productCommentListDTOS);
        return successGet(pages);
    }


    @ApiOperation(value = "商品评价刷选商品信息列表", notes = "商品评价刷选商品信息列表 [1002，1004,1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getProductsByMerchantId", method = RequestMethod.GET)
    public Result<Page<CommentProductInfoDTO>> getProductsByMerchantId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true) CommentListParam listparam) throws Exception {

        Long merchantId = UserUtil.getCurrentUserId(getRequest());
        if (listparam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        CommentMerchantListParam param = new CommentMerchantListParam();
        param.setMerchantId(merchantId);
        param.setCurrentPage(listparam.getCurrentPage());
        param.setPageSize(listparam.getPageSize());
        List<CommentProductInfoDTO> commentProductInfoDTOS = new ArrayList<>();
        Page<CommentProductInfoDTO> pages = new Page<>();
        Result<Page<CommentProductIdDTO>> productIds = commentService.getProductCommentIdsByMerchantId(param);
        if (productIds.getModel() == null || productIds.getModel().getRecords().isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        for (CommentProductIdDTO commentProductIdDTO : productIds.getModel().getRecords()) {
            //查询商品信息
            CommentProductInfoDTO commentProductInfoDTO = new CommentProductInfoDTO();
            Result<com.lawu.eshop.product.dto.CommentProductInfoDTO> product = productService.selectCommentProductInfo(commentProductIdDTO.getProductModelId());
            commentProductInfoDTO.setName(product.getModel().getName());
            commentProductInfoDTO.setImgUrl(product.getModel().getFeatureImage());
            commentProductInfoDTO.setProductId(commentProductIdDTO.getProductId());
            commentProductInfoDTOS.add(commentProductInfoDTO);
        }
        pages.setTotalCount(productIds.getModel().getTotalCount());
        pages.setCurrentPage(productIds.getModel().getCurrentPage());
        pages.setRecords(commentProductInfoDTOS);
        return successGet(pages);
    }

    @ApiOperation(value = "评价商品列表(全部)", notes = "评价商品列表 [1002，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProducts", method = RequestMethod.GET)
    public Result<Page<MerchantProductCommentListDTO>> getCommentProducts(@ModelAttribute @ApiParam CommentProductListParam listParam) throws Exception {

        List<MerchantProductCommentListDTO> commentProductDTOS = new ArrayList<>();
        Page<MerchantProductCommentListDTO> pages = new Page<>();
        //获取评论列表
        Result<Page<CommentDTO>> result = commentService.getCommentProducts(listParam);
        if (result.getModel() == null || result.getModel().getRecords().isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        for (CommentDTO commentDTO : result.getModel().getRecords()) {
            //设置评论信息
            MerchantProductCommentListDTO commentProductDTO = new MerchantProductCommentListDTO();
            commentProductDTO.setContent(commentDTO.getContent());
            commentProductDTO.setReplyContent(commentDTO.getReplyContent());
            commentProductDTO.setAnonymous(commentDTO.getAnonymous());
            commentProductDTO.setGmtCreate(commentDTO.getGmtCreate());
            commentProductDTO.setImgUrls(commentDTO.getImgUrls());
            commentProductDTO.setId(commentDTO.getId());
            commentProductDTO.setGrade(commentDTO.getGrade());
            //查询评论用户信息
            Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());
            commentProductDTO.setHeadImg(user.getModel().getHeadimg());
            commentProductDTO.setNickName(user.getModel().getNickname());
            commentProductDTO.setLevel(user.getModel().getLevel());
            //查询商品信息
            Result<com.lawu.eshop.product.dto.CommentProductInfoDTO> product = productService.selectCommentProductInfo(commentDTO.getProductModelId());
            commentProductDTO.setName(product.getModel().getName());
            commentProductDTO.setPrice(product.getModel().getPrice());
            commentProductDTO.setSpec(product.getModel().getModelName());
            commentProductDTO.setFeatureImage(product.getModel().getFeatureImage());
            commentProductDTOS.add(commentProductDTO);
        }
        pages.setCurrentPage(result.getModel().getCurrentPage());
        pages.setTotalCount(result.getModel().getTotalCount());
        pages.setRecords(commentProductDTOS);
        return successGet(pages);
    }

    @ApiOperation(value = "评价商家列表", notes = "评价商家列表 [1005，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getCommentMerchantList", method = RequestMethod.GET)
    public Result<Page<CommentMerchantInfoDTO>> getCommentMerchantAllList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true) CommentListParam param) {

        Long merchantId = UserUtil.getCurrentUserId(getRequest());
        CommentMerchantListParam listParam = new CommentMerchantListParam();
        listParam.setMerchantId(merchantId);
        listParam.setCurrentPage(param.getCurrentPage());
        listParam.setPageSize(param.getPageSize());
        List<CommentMerchantInfoDTO> commentMerchantDTOS = new ArrayList<>();
        Page<CommentMerchantInfoDTO> pages = new Page<>();
        //获取评论列表
        Result<Page<CommentDTO>> result = commentService.getCommentMerchantAllList(listParam);
        if (result.getModel() == null || result.getModel().getRecords().isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        for (CommentDTO commentDTO : result.getModel().getRecords()) {
            CommentMerchantInfoDTO commentMerchantDTO = new CommentMerchantInfoDTO();
            commentMerchantDTO.setAnonymous(commentDTO.getAnonymous());
            commentMerchantDTO.setContent(commentDTO.getContent());
            commentMerchantDTO.setGmtCreate(commentDTO.getGmtCreate());
            commentMerchantDTO.setReplyContent(commentDTO.getReplyContent());
            List imgs = commentDTO.getImgUrls();
            if (imgs == null) {
                imgs = new ArrayList();
            }
            commentMerchantDTO.setImgUrls(imgs);
            commentMerchantDTO.setGrade(commentDTO.getGrade());
            commentMerchantDTO.setId(commentDTO.getId());
            //查询评论用户信息
            Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());
            commentMerchantDTO.setHeadImg(user.getModel().getHeadimg());
            commentMerchantDTO.setNickName(user.getModel().getNickname());
            commentMerchantDTO.setLevel(user.getModel().getLevel());
            commentMerchantDTOS.add(commentMerchantDTO);
        }
        pages.setCurrentPage(result.getModel().getCurrentPage());
        pages.setTotalCount(result.getModel().getTotalCount());
        pages.setRecords(commentMerchantDTOS);
        return successGet(pages);
    }

}
