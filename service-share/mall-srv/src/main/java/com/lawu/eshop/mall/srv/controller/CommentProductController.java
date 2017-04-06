package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.CommentProductDTO;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result saveCommentProductInfo(@PathVariable("memberId") Long memberId, @RequestBody CommentProductParam param,@RequestParam("headImg") String headImg) {
        if (param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Integer id = commentProductService.saveCommentProductInfo(memberId, param,headImg);
        if (id == null || id < 0) {
            successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "getCommentProducts")
    public Result<Page<CommentProductDTO>> getCommentProducts(@RequestBody CommentProductListParam listParam){

        if(listParam == null){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<CommentProductBO> commentProductDTOS =   commentProductService.getCommentProducts(listParam);
        return  successGet();
    }
}
