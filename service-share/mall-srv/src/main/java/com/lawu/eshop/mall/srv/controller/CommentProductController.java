package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import com.lawu.eshop.mall.dto.CommentOperatorDTO;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentGradeBO;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.converter.CommentProductConverter;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
@RestController
@RequestMapping(value = "commentProduct")
public class CommentProductController extends BaseController {

    @Autowired
    private CommentProductService commentProductService;

    /**
     * 新增用户商品评价
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "saveCommentProductInfo/{memberId}", method = RequestMethod.POST)
    public Result saveCommentProductInfo(@PathVariable("memberId") Long memberId, @RequestBody CommentProductParam param, @RequestParam("headImg") String headImg) {
        if (param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Integer id = commentProductService.saveCommentProductInfo(memberId, param, headImg);
        if (id == null || id < 0) {
            successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 获取评论信息列表(全部)
     *
     * @param listParam
     * @return
     */
    @RequestMapping(value = "getCommentProducts", method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentProducts(@RequestBody CommentProductListParam listParam) {

        if (listParam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentProductBO> commentProductBOPage = commentProductService.getCommentProducts(listParam);
        if(commentProductBOPage.getRecords().isEmpty()){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<CommentProductBO> commentProductBOS = commentProductBOPage.getRecords();

        List<CommentDTO> commentProductDTOS = CommentProductConverter.converterDTOS(commentProductBOS);
        Page<CommentDTO> pages = new Page<>();
        pages.setRecords(commentProductDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentProductBOPage.getTotalCount());
        return successGet(pages);
    }

    /**
     * 商品评论信息列表（有图）
     * @param listParam
     * @return
     */
    @RequestMapping(value = "getCommentProductsWithImgs", method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentProductsWithImgs(@RequestBody CommentProductListParam listParam) {
        if (listParam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentProductBO> commentProductBOPage = commentProductService.getCommentProductsWithImgs(listParam);
        if(commentProductBOPage.getRecords().isEmpty()){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<CommentProductBO> commentProductBOS = commentProductBOPage.getRecords();

        List<CommentDTO> commentProductDTOS = CommentProductConverter.converterDTOS(commentProductBOS);
        Page<CommentDTO> pages = new Page<CommentDTO>();
        pages.setRecords(commentProductDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentProductBOPage.getTotalCount());
        return successGet(pages);
    }

    /**
     * 回复商家评论
     * @param commentId
     * @param replyContent
     * @return
     */
    @RequestMapping(value = "replyProductComment/{commentId}", method = RequestMethod.PUT)
    public Result replyProductComment(@PathVariable("commentId") Long commentId, @RequestParam("replyContent") String replyContent) {
        //查询评论信息
        CommentProductBO commentProductBO = commentProductService.findProductComment(commentId);
        if (commentProductBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        int rows = commentProductService.replyProductComment(commentId, replyContent);
        if (rows == 0 || rows < 0) {
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }


    /**
     * 屏蔽评价信息
     * @param commentId
     * @return
     */
    @RequestMapping(value = "delCommentProductInfo/{commentId}",method = RequestMethod.DELETE)
    public Result delCommentProductInfo(@PathVariable("commentId") Long commentId){
        if (commentId == null) {
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        commentProductService.delCommentProductInfo(commentId);
        return  successDelete(ResultCode.SUCCESS);
    }

    /**
     * 获取综合评价及好评率
     * @param productId
     * @return
     */
    @RequestMapping(value = "getCommentAvgGrade/{productId}",method = RequestMethod.GET)
    public Result<CommentGradeDTO> getCommentAvgGrade(@PathVariable("productId") Long productId) {

        CommentGradeBO commentGradeBO = commentProductService.getCommentAvgGrade(productId);
        if(commentGradeBO == null){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        CommentGradeDTO commentGradeDTO = new CommentGradeDTO();
        commentGradeDTO.setGoodGrad(commentGradeBO.getGoodGrad());
        commentGradeDTO.setAvgGrade(commentGradeBO.getAvgGrade());
        return successGet(commentGradeDTO);
    }


    @RequestMapping(value = "getCommentProductListOperator",method = RequestMethod.POST)
    public Result<Page<CommentOperatorDTO>> getCommentProductListOperator(@RequestBody CommentListParam listParam){

        Page<CommentProductBO> commentProductBOPage = commentProductService.getCommentProductListOperator(listParam);
        if(commentProductBOPage.getRecords().isEmpty()){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<CommentProductBO> commentProductBOS = commentProductBOPage.getRecords();
        List<CommentOperatorDTO> commentOperatorDTOS = CommentProductConverter.converterOperatorDTOS(commentProductBOS);
        Page<CommentOperatorDTO> pages = new Page<CommentOperatorDTO>();
        pages.setRecords(commentOperatorDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentProductBOPage.getTotalCount());
        return successGet(pages);
    }

    /**
     * 根据商家ID查询商品评论信息
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "getProductCommentListByMerchantId",method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getProductCommentListByMerchantId(@RequestBody CommentMerchantListParam pageParam){

        if (pageParam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentProductBO> commentProductBOPage = commentProductService.getProductCommentListByMerchantId(pageParam);
        if(commentProductBOPage.getRecords().isEmpty()){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<CommentProductBO> commentProductBOS = commentProductBOPage.getRecords();

        List<CommentDTO> commentProductDTOS = CommentProductConverter.converterDTOS(commentProductBOS);
        Page<CommentDTO> pages = new Page<>();
        pages.setRecords(commentProductDTOS);
        pages.setCurrentPage(pageParam.getCurrentPage());
        pages.setTotalCount(commentProductBOPage.getTotalCount());
        return successGet(pages);
    }

}
