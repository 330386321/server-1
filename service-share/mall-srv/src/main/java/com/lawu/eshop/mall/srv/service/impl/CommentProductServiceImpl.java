package com.lawu.eshop.mall.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.CommentAnonymousEnum;
import com.lawu.eshop.mall.constants.CommentStatusEnum;
import com.lawu.eshop.mall.constants.CommentTypeEnum;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductPageParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.converter.CommentProductConverter;
import com.lawu.eshop.mall.srv.domain.CommentImageDO;
import com.lawu.eshop.mall.srv.domain.CommentImageDOExample;
import com.lawu.eshop.mall.srv.domain.CommentProductDO;
import com.lawu.eshop.mall.srv.domain.CommentProductDOExample;
import com.lawu.eshop.mall.srv.domain.extend.CommentProductDOView;
import com.lawu.eshop.mall.srv.mapper.CommentImageDOMapper;
import com.lawu.eshop.mall.srv.mapper.CommentProductDOMapper;
import com.lawu.eshop.mall.srv.mapper.extend.CommentProductDOMapperExtend;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private CommentProductDOMapperExtend commentProductDOMapperExtend;

    @Override
    @Transactional
    public Integer saveCommentProductInfo(Long memberId, CommentProductParam param, String headImg) {
        CommentProductDO commentProductDO = new CommentProductDO();
        commentProductDO.setMemberId(memberId);
        commentProductDO.setContent(param.getContent());
        commentProductDO.setStatus(CommentStatusEnum.COMMENT_STATUS_VALID.val);
        if (CommentAnonymousEnum.COMMENT_ANONYMOUS_SUCCESS.equals(param.getAnonymousEnum())) {
            commentProductDO.setIsAnonymous(true);//匿名
        } else {
            commentProductDO.setIsAnonymous(false);
        }
        commentProductDO.setProductId(param.getProductId());
        commentProductDO.setGmtCreate(new Date());
        commentProductDO.setGmtModified(new Date());
        Integer id = commentProductDOMapper.insert(commentProductDO);//新增评价信息
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
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(listParam.getOffset(), listParam.getPageSize());
        Page<CommentProductBO> page = new Page<>();
        page.setTotalCount(commentProductDOMapper.countByExample(example));
        page.setCurrentPage(listParam.getCurrentPage());

        //查询评价列表
        List<CommentProductDO> commentProductDOS = commentProductDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<CommentProductBO> commentProductBOS = new ArrayList<>();
        for (CommentProductDO commentProductDO : commentProductDOS) {
            CommentProductBO commentProductBO = CommentProductConverter.converterBO(commentProductDO);
            CommentImageDOExample imageDOExample = new CommentImageDOExample();
            imageDOExample.createCriteria().andCommentIdEqualTo(commentProductDO.getId());
            List<CommentImageDO> commentImageDOS = commentImageDOMapper.selectByExample(imageDOExample);
            List<String> images = new ArrayList<String>();
            if (!commentImageDOS.isEmpty()) {
                for (int i = 0; i < commentImageDOS.size(); i++) {
                    images.add(commentImageDOS.get(i).getImgUrl());
                }
                commentProductBO.setUrlImgs(images);
            }

            commentProductBOS.add(commentProductBO);
        }
        page.setRecords(commentProductBOS);
        return page;
    }

    @Override
    public Page<CommentProductBO> getCommentProductsWithImgs(CommentProductListParam listParam) {

        int totalCount = commentProductDOMapperExtend.selectCountByProductId(listParam.getProductId());

        Page<CommentProductBO> commentProductBOPage = new Page<CommentProductBO>();
        commentProductBOPage.setTotalCount(totalCount);
        commentProductBOPage.setCurrentPage(listParam.getCurrentPage());

        CommentProductPageParam productPageParam = new CommentProductPageParam();
        productPageParam.setCurrentPage(listParam.getOffset());
        productPageParam.setPageSize(listParam.getPageSize());
        productPageParam.setProductId(listParam.getProductId());
        //查询评论列表信息
        List<CommentProductDOView> commentProductDOViews = commentProductDOMapperExtend.selectCommentsWithImg(productPageParam);

        Page<CommentProductBO> pages = new Page<CommentProductBO>();
        List<CommentProductBO> commentProductBOS = new ArrayList<CommentProductBO>();
        if (!commentProductDOViews.isEmpty()) {
            for (CommentProductDOView commentProductDOView : commentProductDOViews) {
                CommentProductBO commentProductBO = CommentProductConverter.converterBOFromView(commentProductDOView);
                //查询对应的评价图片
                CommentImageDOExample imageDOExample = new CommentImageDOExample();
                imageDOExample.createCriteria().andCommentIdEqualTo(commentProductDOView.getId());
                List<CommentImageDO> commentImageDOS = commentImageDOMapper.selectByExample(imageDOExample);
                List<String> images = new ArrayList<String>();
                if (!commentImageDOS.isEmpty()) {
                    for (int i = 0; i < commentImageDOS.size(); i++) {
                        images.add(commentImageDOS.get(i).getImgUrl());
                    }
                    commentProductBO.setUrlImgs(images);
                }
                commentProductBOS.add(commentProductBO);
            }
        }
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(totalCount);
        pages.setRecords(commentProductBOS);
        return pages;
    }
}
