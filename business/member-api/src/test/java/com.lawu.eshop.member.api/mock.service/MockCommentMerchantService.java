package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import com.lawu.eshop.mall.dto.MemberProductCommentDTO;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentMerchantParam;
import com.lawu.eshop.member.api.service.CommentMerchantService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Service
public class MockCommentMerchantService implements CommentMerchantService {

    @Override
    public Result saveCommentMerchantInfo(@PathVariable("memberId") Long memberId, @ModelAttribute CommentMerchantParam param, @RequestParam("commentPic") String commentPic) {
        return null;
    }

    @Override
    public Result<Page<CommentDTO>> getCommentMerchantAllList(@ModelAttribute CommentMerchantListParam listParam) {
        return null;
    }

    @Override
    public Result<Page<CommentDTO>> getCommentMerchantListWithImgs(@ModelAttribute CommentMerchantListParam listParam) {
        return null;
    }

    @Override
    public Result<CommentGradeDTO> getCommentAvgGrade(@PathVariable("merchantId") Long merchantId) {
        return null;
    }

    @Override
    public Result<List<MemberProductCommentDTO>> geNewlyProductComment(@RequestParam("productId") Long productId) {
        return null;
    }

    @Override
    public Result<Integer> getProductCommentCount(@RequestParam("productId") Long productId) {
        return null;
    }

    @Override
    public Byte getGradeByOrderId(@RequestParam("id") Long id, @RequestParam("memberId") Long memberId) {
        return null;
    }
}
