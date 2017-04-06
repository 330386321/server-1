package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.member.api.service.CommentProductService;
import com.lawu.eshop.user.constants.UploadFileTypeConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import util.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.Iterator;
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

    @ApiOperation(value = "用户评价商品", notes = "用户评价商品 [1005，1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveCommentProductInfo}", method = RequestMethod.POST)
    public Result saveCommentProductInfo(@ModelAttribute @ApiParam CommentProductParam param) {
        HttpServletRequest request = getRequest();
        Long memberId = UserUtil.getCurrentUserId(request);
        StringBuffer headImg = new StringBuffer();
        Collection<Part> parts ;
        try {
            parts = request.getParts();
            for (Part part : parts) {
                Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_STORE,part);
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
            return commentProductService.saveCommentProductInfo(memberId, param,headImg.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
        }

    }


}
