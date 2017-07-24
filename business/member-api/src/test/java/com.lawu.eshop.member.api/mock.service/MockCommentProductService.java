package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.member.api.service.CommentProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class MockCommentProductService  implements CommentProductService {

    @Override
    public Result saveCommentProductInfo(@PathVariable("memberId") Long memberId, @ModelAttribute CommentProductParam param, @RequestParam("headImg") String headImg) {
        return null;
    }

    @Override
    public Result<Page<CommentDTO>> getCommentProducts(@ModelAttribute CommentProductListParam listParam) {
        return null;
    }

    @Override
    public Result<Page<CommentDTO>> getCommentProductsWithImgs(@ModelAttribute CommentProductListParam listParam) {
        return null;
    }

    @Override
    public Result<CommentGradeDTO> getCommentAvgGrade(@PathVariable("productId") Long productId) {
        return null;
    }
}
