package com.lawu.eshop.mall.srv.mapper.extend;

import com.lawu.eshop.mall.param.CommentProductPageParam;
import com.lawu.eshop.mall.srv.domain.extend.CommentProductDOView;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
public interface CommentProductDOMapperExtend {

    List<CommentProductDOView> selectCommentsWithImg(CommentProductPageParam param);

    int selectCountByProductId(Long productId);

    int selectGoodGradeCount(Long productId);

    Double selectAvgGrade(Long productId);
}
