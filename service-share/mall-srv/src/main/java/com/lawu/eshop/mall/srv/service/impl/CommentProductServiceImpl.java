package com.lawu.eshop.mall.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.CommentAnonymousEnum;
import com.lawu.eshop.mall.constants.CommentStatusEnum;
import com.lawu.eshop.mall.constants.CommentTypeEnum;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.domain.CommentImageDO;
import com.lawu.eshop.mall.srv.domain.CommentProductDO;
import com.lawu.eshop.mall.srv.domain.CommentProductDOExample;
import com.lawu.eshop.mall.srv.mapper.CommentImageDOMapper;
import com.lawu.eshop.mall.srv.mapper.CommentProductDOMapper;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
@Service
public class CommentProductServiceImpl implements CommentProductService {

    @Autowired
    private CommentProductDOMapper commentProductDOMapper;
    @Autowired
    private CommentImageDOMapper commentImageDOMapper;

    @Override
    public Integer saveCommentProductInfo(Long memberId, CommentProductParam param, String headImg) {
        CommentProductDO commentProductDO = new CommentProductDO();
        commentProductDO.setMemberId(memberId);
        commentProductDO.setContent(param.getContent());
        commentProductDO.setStatus(param.getStatusEnum().val);
        if (CommentAnonymousEnum.COMMENT_ANONYMOUS_SUCCESS.equals(param.getAnonymousEnum())) {
            commentProductDO.setIsAnonymous(true);//匿名
        } else {
            commentProductDO.setIsAnonymous(false);
        }
        commentProductDO.setProductId(param.getProductId());
        commentProductDO.setGmtCreate(new Date());
        commentProductDO.setGmtModified(new Date());
        Integer id = commentProductDOMapper.insert(commentProductDO);
        if (!StringUtils.isEmpty(headImg)) {
            String imgs[] = headImg.split(",");
            if (id != null && id > 0) {
                //新增评价图片
                for (int i = 0; i < imgs.length; i++) {
                    if (!StringUtils.isEmpty(imgs[i])) {
                        CommentImageDO commentImageDO = new CommentImageDO();
                        commentImageDO.setCommentId(Long.valueOf(id));
                        commentImageDO.setImgUrl(imgs[i]);
                        commentImageDO.setStatus(true);//有效
                        commentImageDO.setType(CommentTypeEnum.COMMENT_TYPE_PRODUCT.val);//评论商品
                        commentImageDO.setGmtCreate(new Date());
                        commentImageDO.setGmtModified(new Date());
                        commentImageDOMapper.insert(commentImageDO);
                    }
                }
            }
        }
        return id;
    }

    @Override
    public Page<CommentProductBO> getCommentProducts(CommentProductListParam listParam) {
        CommentProductDOExample example = new CommentProductDOExample();
        example.createCriteria().andStatusEqualTo(CommentStatusEnum.COMMENT_STATUS_VALID.val).
                andProductIdEqualTo(listParam.getProductId());

        RowBounds rowBounds = new RowBounds(listParam.getOffset(), listParam.getPageSize());
        Page<CommentProductBO> page = new Page<>();
        page.setTotalCount(commentProductDOMapper.countByExample(example));
        page.setCurrentPage(listParam.getCurrentPage());

        //查询评价列表
        List<CommentProductDO> commentProductDOS = commentProductDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<CommentProductBO> commentProductBOS  = new ArrayList<>();
        for (CommentProductDO commentProductDO : commentProductDOS) {

        }
        return null;
    }
}
