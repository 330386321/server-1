package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.domain.CommentProductDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public class CommentProductConverter {

    public static CommentProductBO converterBO(CommentProductDO commentProductDO) {
        if (commentProductDO == null) {
            return null;
        }
        CommentProductBO commentProductBO = new CommentProductBO();
        commentProductBO.setContent(commentProductDO.getContent());
        commentProductBO.setGmtCreate(commentProductDO.getGmtCreate());
        commentProductBO.setAnonymous(commentProductDO.getIsAnonymous());
        commentProductBO.setReplyContent(commentProductDO.getReplyContent());
        return commentProductBO;
    }

    public static List<CommentDTO> converterDTOS(List<CommentProductBO> commentProductBOS) {
        if(commentProductBOS.isEmpty()){
            return null;
        }
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (CommentProductBO commentProductBO : commentProductBOS){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setGmtCreate(commentProductBO.getGmtCreate());
            commentDTO.setContent(commentProductBO.getContent());
            commentDTO.setAnonymous(commentProductBO.isAnonymous());
            commentDTO.setImgUrls(commentProductBO.getUrlImgs());
            commentDTO.setMemberId(commentProductBO.getMemberId());
            commentDTO.setId(commentProductBO.getId());
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
