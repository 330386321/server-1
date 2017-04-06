package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.srv.bo.CommentMerchantBO;
import com.lawu.eshop.mall.srv.domain.CommentMerchantDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public class CommentMerchantConverter {
    /**
     * 商家评价BO转换
     *
     * @param commentMerchantDO
     * @return
     */
    public static CommentMerchantBO converBO(CommentMerchantDO commentMerchantDO) {
        if (commentMerchantDO == null) {
            return null;
        }
        CommentMerchantBO commentMerchantBO = new CommentMerchantBO();
        commentMerchantBO.setContent(commentMerchantDO.getContent());
        commentMerchantBO.setGmtCreate(commentMerchantDO.getGmtCreate());
        commentMerchantBO.setAnonymous(commentMerchantDO.getIsAnonymous());
        commentMerchantBO.setReplyContent(commentMerchantDO.getReplyContent());
        commentMerchantBO.setMemberId(commentMerchantDO.getMemberId());
        commentMerchantBO.setMerchantId(commentMerchantDO.getMerchantId());
        return commentMerchantBO;
    }

    public static List<CommentDTO> converterDTOS(List<CommentMerchantBO> commentMerchantBOS) {
        if (commentMerchantBOS.isEmpty()) {
            return null;
        }
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (CommentMerchantBO commentMerchantBO : commentMerchantBOS) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setGmtCreate(commentMerchantBO.getGmtCreate());
            commentDTO.setContent(commentMerchantBO.getContent());
            commentDTO.setAnonymous(commentMerchantBO.isAnonymous());
            commentDTO.setImgUrls(commentMerchantBO.getUrlImgs());
            commentDTO.setMemberId(commentMerchantBO.getMemberId());
            commentDTO.setId(commentMerchantBO.getId());
            commentDTO.setGrade(commentMerchantBO.getGrade());
            commentDTO.setReplyContent(commentMerchantBO.getReplyContent());
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
