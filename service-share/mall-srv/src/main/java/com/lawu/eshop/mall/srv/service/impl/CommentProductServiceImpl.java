package com.lawu.eshop.mall.srv.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.CommentAnonymousEnum;
import com.lawu.eshop.mall.constants.CommentGradeEnum;
import com.lawu.eshop.mall.constants.CommentStatusEnum;
import com.lawu.eshop.mall.constants.CommentTypeEnum;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductPageParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentGradeBO;
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
import com.lawu.eshop.mq.dto.order.ShoppingOrderAutoCommentNotification;

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

    @Autowired
    @Qualifier("orderCommentProductTransactionMainServiceImpl")
    private TransactionMainService transactionMainService;

    @Override
    @Transactional
    public Integer saveCommentProductInfo(Long memberId, CommentProductParam param, String headImg) {
        CommentProductDO commentProductDO = new CommentProductDO();
        commentProductDO.setMemberId(memberId);
        commentProductDO.setContent(param.getContent());
        commentProductDO.setStatus(CommentStatusEnum.COMMENT_STATUS_VALID.val);
        commentProductDO.setIsAnonymous(param.getAnonymousEnum().val);//匿名
        commentProductDO.setOrderItemId(param.getShoppingOrderItemId());
        commentProductDO.setMerchantId(param.getMerchantId());
        commentProductDO.setGrade(param.getGradeEnum().val);
        commentProductDO.setProductId(param.getProductId());
        commentProductDO.setGmtCreate(new Date());
        commentProductDO.setGmtModified(new Date());
         commentProductDOMapper.insert(commentProductDO);//新增评价信息
        Long id =commentProductDO.getId();
        if (!StringUtils.isEmpty(headImg)) {
            String imgs[] = headImg.split(",");
            if (id != null && id > 0) {
                //新增评价图片
                for (int i = 0; i < imgs.length; i++) {
                    if (!StringUtils.isEmpty(imgs[i])) {
                        CommentImageDO commentImageDO = new CommentImageDO();
                        commentImageDO.setCommentId(id);
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
        // 更新评价状态 发消息
        transactionMainService.sendNotice(param.getShoppingOrderItemId());
        return id.intValue();
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
            imageDOExample.createCriteria().andCommentIdEqualTo(commentProductDO.getId()).andTypeEqualTo(CommentTypeEnum.COMMENT_TYPE_PRODUCT.val);
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
                imageDOExample.createCriteria().andCommentIdEqualTo(commentProductDOView.getId()).andTypeEqualTo(CommentTypeEnum.COMMENT_TYPE_PRODUCT.val);
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

    @Override
    public CommentProductBO findProductComment(Long commentId) {

        CommentProductDO commentProductDO = commentProductDOMapper.selectByPrimaryKey(commentId);
        CommentProductBO commentProductBO = CommentProductConverter.converterBO(commentProductDO);
        return commentProductBO;
    }

    @Override
    @Transactional
    public Integer replyProductComment(Long commentId, String replyContent) {
        CommentProductDO commentProductDO = new CommentProductDO();
        commentProductDO.setId(commentId);
        commentProductDO.setReplyContent(replyContent);
        commentProductDO.setGmtReply(new Date());
        int rows = commentProductDOMapper.updateByPrimaryKeySelective(commentProductDO);
        return rows;
    }

    @Override
    @Transactional
    public void delCommentProductInfo(Long commentId) {
        CommentProductDO commentProductDO = new CommentProductDO();
        commentProductDO.setId(commentId);
        commentProductDO.setStatus(CommentStatusEnum.COMMENT_STATUS_INVALID.val);
        commentProductDOMapper.updateByPrimaryKeySelective(commentProductDO);
    }

    @Override
    public CommentGradeBO getCommentAvgGrade(Long productId) {
        CommentProductDOExample commentProductDOExample = new CommentProductDOExample();
        commentProductDOExample.createCriteria().andProductIdEqualTo(productId);
        List<CommentProductDO> commentProductDOS = commentProductDOMapper.selectByExample(commentProductDOExample);
        if (commentProductDOS.isEmpty()) {
            return null;
        }
        Double avgGrade = commentProductDOMapperExtend.selectAvgGrade(productId);
        avgGrade = new BigDecimal(avgGrade).setScale(2, RoundingMode.UP).doubleValue();
        Integer goodCount = commentProductDOMapperExtend.selectGoodGradeCount(productId);
        CommentProductDOExample example = new CommentProductDOExample();
        example.createCriteria().andStatusEqualTo(CommentStatusEnum.COMMENT_STATUS_VALID.val).
                andProductIdEqualTo(productId);
        Integer totalCount = commentProductDOMapper.countByExample(example);
        double goodGrade = new BigDecimal((double) goodCount / totalCount).setScale(2, RoundingMode.UP).doubleValue();
        CommentGradeBO commentGradeBO = new CommentGradeBO();
        commentGradeBO.setAvgGrade(avgGrade);
        commentGradeBO.setGoodGrad(goodGrade);
        return commentGradeBO;
    }

    @Override
    public Page<CommentProductBO> getCommentProductListOperator(CommentListParam listParam) {
        CommentProductDOExample example = new CommentProductDOExample();
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(listParam.getOffset(), listParam.getPageSize());
        Page<CommentProductBO> page = new Page<>();
        page.setTotalCount(commentProductDOMapper.countByExample(example));
        page.setCurrentPage(listParam.getCurrentPage());

        //查询评价列表
        List<CommentProductDO> commentProductDOS = commentProductDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        if (commentProductDOS.isEmpty()) {
            return null;
        }
        List<CommentProductBO> commentProductBOS = new ArrayList<>();
        for (CommentProductDO commentProductDO : commentProductDOS) {
            CommentProductBO commentProductBO = CommentProductConverter.converterBO(commentProductDO);
            commentProductBOS.add(commentProductBO);
        }
        page.setRecords(commentProductBOS);
        return page;
    }

    @Override
    @Transactional
    public void saveCommentProductInfoOrderJob(ShoppingOrderAutoCommentNotification notification) {
        CommentProductDOExample example = new CommentProductDOExample();
        example.createCriteria().andOrderItemIdEqualTo(notification.getShoppingOrderItem());
        List<CommentProductDO> oldComment = commentProductDOMapper.selectByExample(example);
        if(!oldComment.isEmpty()){
            return;
        }
        CommentProductDO productDO = new CommentProductDO();
        productDO.setMemberId(notification.getMemberId());
        productDO.setMerchantId(notification.getMerchantId());
        productDO.setProductId(notification.getProductId());
        productDO.setOrderItemId(notification.getShoppingOrderItem());
        productDO.setIsAnonymous(CommentAnonymousEnum.COMMENT_ANONYMOUS.val);//匿名
        productDO.setContent("好评");
        productDO.setGrade(CommentGradeEnum.COMMENT_STAR_LEVEL_FIVE.val);
        productDO.setStatus(CommentStatusEnum.COMMENT_STATUS_VALID.val);
        productDO.setGmtModified(new Date());
        productDO.setGmtCreate(new Date());
        productDO.setGrade(CommentGradeEnum.COMMENT_STAR_LEVEL_FIVE.val);
        commentProductDOMapper.insert(productDO);
    }

    @Override
    public Page<CommentProductBO> getProductCommentListByMerchantId(CommentMerchantListParam pageParam) {
        CommentProductDOExample example = new CommentProductDOExample();
        example.createCriteria().andStatusEqualTo(CommentStatusEnum.COMMENT_STATUS_VALID.val).
                andMerchantIdEqualTo(pageParam.getMerchantId());
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());
        Page<CommentProductBO> page = new Page<>();
        page.setTotalCount(commentProductDOMapper.countByExample(example));
        page.setCurrentPage(pageParam.getCurrentPage());

        //查询评价列表
        List<CommentProductDO> commentProductDOS = commentProductDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<CommentProductBO> commentProductBOS = new ArrayList<>();
        for (CommentProductDO commentProductDO : commentProductDOS) {
            CommentProductBO commentProductBO = CommentProductConverter.converterBO(commentProductDO);
            CommentImageDOExample imageDOExample = new CommentImageDOExample();
            imageDOExample.createCriteria().andCommentIdEqualTo(commentProductDO.getId()).andTypeEqualTo(CommentTypeEnum.COMMENT_TYPE_PRODUCT.val);
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
}
