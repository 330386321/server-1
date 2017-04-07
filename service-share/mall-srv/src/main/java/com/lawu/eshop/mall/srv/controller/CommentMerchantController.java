package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentMerchantParam;
import com.lawu.eshop.mall.srv.bo.CommentMerchantBO;
import com.lawu.eshop.mall.srv.converter.CommentMerchantConverter;
import com.lawu.eshop.mall.srv.service.CommentMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
@RestController
@RequestMapping(value = "commentMerchant")
public class CommentMerchantController extends BaseController {

    @Autowired
    private CommentMerchantService commentMerchantService;

    /**
     * 新增商家评价信息
     * @param memberId
     * @param param
     * @param commentPic
     * @return
     */
    @RequestMapping(value = "saveCommentMerchantInfo/{memberId}",method = RequestMethod.POST)
    public Result saveCommentMerchantInfo(@PathVariable("memberId") Long memberId, @RequestBody CommentMerchantParam param,
                                          @RequestParam("commentPic") String commentPic) {
        if (param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Integer id = commentMerchantService.saveCommentMerchantInfo(memberId, param, commentPic);
        if (id == null || id < 0) {
            successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated();
    }

    /**
     * 商家评论信息列表（全部）
     * @param listParam
     * @return
     */
    @RequestMapping(value = "getCommentMerchantAllList",method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentMerchantAllList(@RequestBody CommentMerchantListParam listParam){
        if(listParam == null){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentMerchantBO>  commentMerchantBOPage =   commentMerchantService.getCommentMerchantAllList(listParam);

        List<CommentMerchantBO> commentMerchantBOS = commentMerchantBOPage.getRecords();

        List<CommentDTO> commentDTOS = CommentMerchantConverter.converterDTOS(commentMerchantBOS);
        Page<CommentDTO> pages = new Page<CommentDTO>();
        pages.setRecords(commentDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentMerchantBOPage.getTotalCount());
        return successGet(pages);
    }

    @RequestMapping(value = "getCommentMerchantListWithImgs",method = RequestMethod.POST)
    public Result<Page<CommentDTO>> getCommentMerchantListWithImgs(@RequestBody CommentMerchantListParam listParam){
        if(listParam == null){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentMerchantBO>  commentMerchantBOPage =   commentMerchantService.getCommentMerchantListWithImgs(listParam);

        List<CommentMerchantBO> commentMerchantBOS = commentMerchantBOPage.getRecords();

        List<CommentDTO> commentDTOS = CommentMerchantConverter.converterDTOS(commentMerchantBOS);
        Page<CommentDTO> pages = new Page<CommentDTO>();
        pages.setRecords(commentDTOS);
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(commentMerchantBOPage.getTotalCount());
        return successGet(pages);
    }

    @RequestMapping(value = "replyMerchantComment/{commentId}", method = RequestMethod.PUT)
    public Result replyMerchantComment(@PathVariable("commentId") Long commentId, @RequestParam("replyContent") String replyContent) {
        //查询评论信息
        CommentMerchantBO commentMerchantBO = commentMerchantService.findMerchantComment(commentId);
        if (commentMerchantBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        int rows = commentMerchantService.replyMerchantComment(commentId, replyContent);
        if (rows == 0 || rows < 0) {
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

}
