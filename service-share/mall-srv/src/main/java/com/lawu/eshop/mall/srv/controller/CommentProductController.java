package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
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
        List<CommentProductBO> commentProductBOS = commentProductBOPage.getRecords();

        List<CommentDTO> commentProductDTOS = CommentProductConverter.converterDTOS(commentProductBOS);
        Page<CommentDTO> pages = new Page<>();
        pages.setRecords(commentProductDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentProductBOPage.getTotalCount());
        return successGet(pages);
    }

    @RequestMapping(value = "getCommentProductsWithImgs", method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentProductsWithImgs(@RequestBody CommentProductListParam listParam) {
        if (listParam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentProductBO> commentProductBOPage = commentProductService.getCommentProductsWithImgs(listParam);

        List<CommentProductBO> commentProductBOS = commentProductBOPage.getRecords();

        List<CommentDTO> commentProductDTOS = CommentProductConverter.converterDTOS(commentProductBOS);
        Page<CommentDTO> pages = new Page<CommentDTO>();
        pages.setRecords(commentProductDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentProductBOPage.getTotalCount());
        return successGet(pages);
    }


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

}
