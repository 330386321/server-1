package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.dto.CommentOperatorDTO;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.operator.api.service.CommentProductService;
import com.lawu.eshop.operator.api.service.MerchantStoreService;
import com.lawu.eshop.operator.api.service.ProductAuditService;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@Api(tags = "commentManage")
@RestController
@RequestMapping("commentManage/")
public class CommentManageController extends BaseController{

    @Autowired
    private CommentProductService commentProductService;
    
    @Autowired
    private ProductAuditService productAuditService;
    
    @Autowired
    private MerchantStoreService merchantStoreService;

    @PageBody
    @ApiOperation(value = "评价商品列表(全部)", notes = "评价商品列表 [1002，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProductListOperator", method = RequestMethod.GET)
    public Result<Page<CommentOperatorDTO>> getCommentProductListOperator(@ModelAttribute @ApiParam CommentListParam listParam) {
    
    	 Result<Page<CommentOperatorDTO>> rsPage= commentProductService.getCommentProductListOperator(listParam);
    	 if(isSuccess(rsPage)){
    		 Page<CommentOperatorDTO> page=rsPage.getModel();
    		 List<CommentOperatorDTO> list= page.getRecords();
    		 for (CommentOperatorDTO commentOperatorDTO : list) {
    			 Result<ProductEditInfoDTO>  productRs=productAuditService.selectEditProductById(commentOperatorDTO.getCommentToId());
    			 if(isSuccess(productRs)){
    				 commentOperatorDTO.setName(productRs.getModel().getName());
    			 }
			}
    	 }
         return rsPage;
    }

    @PageBody
    @ApiOperation(value = "评价商家列表(全部)", notes = "评价商品列表 [1002，1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentMerchantListOperator", method = RequestMethod.POST)
    public Result<Page<CommentOperatorDTO>> getCommentMerchantListOperator(@RequestBody @ApiParam CommentListParam listParam) {
    	 Result<Page<CommentOperatorDTO>> rsPage= commentProductService.getCommentMerchantListOperator(listParam);
    	 if(isSuccess(rsPage)){
    		 Page<CommentOperatorDTO> page=rsPage.getModel();
    		 List<CommentOperatorDTO> list= page.getRecords();
    		 for (CommentOperatorDTO commentOperatorDTO : list) {
    			 Result<MerchantStoreDTO>  merchantRs=merchantStoreService.selectMerchantStore(commentOperatorDTO.getCommentToId());
    			 if(isSuccess(merchantRs)){
    				 commentOperatorDTO.setName(merchantRs.getModel().getName());
    			 }
			}
    	 }
    	return rsPage;
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

    @ApiOperation(value = "批量屏蔽商家评价", notes = "屏蔽商家评价 [1004，1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "batchelDelCommentMerchantInfo/", method = RequestMethod.PUT)
    public Result batchelDelCommentMerchantInfo(@RequestParam @ApiParam(value = "评论ID集合 以逗号隔开",required = true) String ids){
        if(ids == null){
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        String[] commentIds=ids.split(",");
        for (String id : commentIds) {
        	 commentProductService.delCommentMerchantInfo(Long.parseLong(id));
		}
       
        return  successCreated();
    }
    
    @ApiOperation(value = "批量屏蔽商品评价", notes = "屏蔽商品评价 [1004，1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "batchelDelCommentProductInfo/", method = RequestMethod.PUT)
    public Result batchelDelCommentProductInfo(@RequestParam @ApiParam(value = "评论ID集合 以逗号隔开",required = true) String ids){
        if(ids == null){
            return successCreated(ResultCode.FAIL);
        }
        String[] commentIds=ids.split(",");
        for (String id : commentIds) {
        	 commentProductService.delCommentProductInfo(Long.parseLong(id));
		}
        return  successCreated(ResultCode.SUCCESS);
    }

}
