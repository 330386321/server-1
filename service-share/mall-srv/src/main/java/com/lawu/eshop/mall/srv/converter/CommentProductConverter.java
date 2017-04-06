package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.domain.CommentProductDO;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public class CommentProductConverter {

    public static CommentProductBO coverStatisticsBO(CommentProductDO commentProductDO) {
        if (commentProductDO == null) {
            return null;
        }
        CommentProductBO commentProductBO = new CommentProductBO();
        commentProductBO.setContent(commentProductDO.getContent());
        commentProductBO.setGmtCreate(commentProductDO.getGmtCreate());
        return null;
    }
}
