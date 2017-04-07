package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentProductDTO;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.member.api.service.CommentProductService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.user.constants.UploadFileTypeConstant;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
@Api("commentProduct")
@RestController
@RequestMapping(value = "/")
public class CommentProductController extends BaseController {

    @Autowired
    private CommentProductService commentProductService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "用户评价商品", notes = "用户评价商品 [1005，1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveCommentProductInfo", method = RequestMethod.POST)
    public Result saveCommentProductInfo(@ModelAttribute @ApiParam CommentProductParam param,@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        HttpServletRequest request = getRequest();
        Long memberId = UserUtil.getCurrentUserId(request);
        StringBuffer headImg = new StringBuffer();
        Collection<Part> parts;
        try {
            parts = request.getParts();
            for (Part part : parts) {
                Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_STORE, part);
                String flag = map.get("resultFlag");
                String fileName = part.getSubmittedFileName();
                if (UploadFileTypeConstant.UPLOAD_RETURN_TYPE.equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    headImg.append(imgUrl + ",");
                } else {
                    return successCreated(Integer.valueOf(flag));
                }
            }
            //更新订单评价状态
            //TODO
            return commentProductService.saveCommentProductInfo(memberId, param, headImg.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
        }

    }

    @ApiOperation(value = "评价商品列表(全部)", notes = "评价商品列表 [1004，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProducts", method = RequestMethod.GET)
    public Result<Page<CommentProductDTO>> getCommentProducts(@ModelAttribute @ApiParam CommentProductListParam listParam) {

        List<CommentProductDTO> commentProductDTOS = new ArrayList<>();
        Page<CommentProductDTO> pages = new Page<>();
        //获取评论列表
        Result<Page<CommentDTO>> result = commentProductService.getCommentProducts(listParam);
        if (!result.getModel().getRecords().isEmpty()) {
            for (CommentDTO commentDTO : result.getModel().getRecords()) {
                //设置评论信息
                CommentProductDTO commentProductDTO = new CommentProductDTO();
                commentProductDTO.setContent(commentDTO.getContent());
                commentProductDTO.setAnonymous(commentDTO.getAnonymous());
                commentProductDTO.setGmtCreate(commentDTO.getGmtCreate());
                commentProductDTO.setImgUrls(commentDTO.getImgUrls());
                commentProductDTO.setId(commentDTO.getId());
                //查询评论用户信息
                Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());
                commentProductDTO.setHeadImg(user.getModel().getHeadimg());
                commentProductDTO.setNickName(user.getModel().getNickname());
                //查询商品信息
                Result<ProductInfoDTO> product = productService.selectProductById(listParam.getProductId());
                commentProductDTO.setName(product.getModel().getName());
                commentProductDTO.setPriceMax(product.getModel().getPriceMax());
                commentProductDTO.setPriceMin(product.getModel().getPriceMin());
                commentProductDTO.setSpec(product.getModel().getSpec());
                commentProductDTOS.add(commentProductDTO);
            }
        }
        pages.setCurrentPage(result.getModel().getCurrentPage());
        pages.setTotalCount(result.getModel().getTotalCount());
        pages.setRecords(commentProductDTOS);
        return successGet(pages);
    }

    @ApiOperation(value = "评价商品列表（有图）", notes = "评价商品列表（有图） [1004，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProductsWithImgs", method = RequestMethod.GET)
    public Result<Page<CommentProductDTO>> getCommentProductsWithImgs(@ModelAttribute @ApiParam CommentProductListParam listParam) {

        List<CommentProductDTO> commentProductDTOS = new ArrayList<>();
        Page<CommentProductDTO> pages = new Page<>();
        //获取评论列表
        Result<Page<CommentDTO>> result = commentProductService.getCommentProductsWithImgs(listParam);
        if (!result.getModel().getRecords().isEmpty()) {
            for (CommentDTO commentDTO : result.getModel().getRecords()) {
                //设置评论信息
                CommentProductDTO commentProductDTO = new CommentProductDTO();
                commentProductDTO.setContent(commentDTO.getContent());
                commentProductDTO.setAnonymous(commentDTO.getAnonymous());
                commentProductDTO.setGmtCreate(commentDTO.getGmtCreate());
                commentProductDTO.setImgUrls(commentDTO.getImgUrls());
                commentProductDTO.setId(commentDTO.getId());
                //查询评论用户信息
                Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());
                commentProductDTO.setHeadImg(user.getModel().getHeadimg());
                commentProductDTO.setNickName(user.getModel().getNickname());
                //查询商品信息
                Result<ProductInfoDTO> product = productService.selectProductById(listParam.getProductId());
                commentProductDTO.setName(product.getModel().getName());
                commentProductDTO.setPriceMax(product.getModel().getPriceMax());
                commentProductDTO.setPriceMin(product.getModel().getPriceMin());
                commentProductDTO.setSpec(product.getModel().getSpec());
                commentProductDTOS.add(commentProductDTO);
            }
        }
        pages.setCurrentPage(result.getModel().getCurrentPage());
        pages.setTotalCount(result.getModel().getTotalCount());
        pages.setRecords(commentProductDTOS);
        return successGet(pages);
    }



}
