package com.lawu.eshop.mall.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.CommentStatusEnum;
import com.lawu.eshop.mall.constants.CommentTypeEnum;
import com.lawu.eshop.mall.param.CommentListParam;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentMerchantPageParam;
import com.lawu.eshop.mall.param.CommentMerchantParam;
import com.lawu.eshop.mall.srv.bo.CommentGradeBO;
import com.lawu.eshop.mall.srv.bo.CommentMerchantBO;
import com.lawu.eshop.mall.srv.converter.CommentMerchantConverter;
import com.lawu.eshop.mall.srv.domain.CommentImageDO;
import com.lawu.eshop.mall.srv.domain.CommentImageDOExample;
import com.lawu.eshop.mall.srv.domain.CommentMerchantDO;
import com.lawu.eshop.mall.srv.domain.CommentMerchantDOExample;
import com.lawu.eshop.mall.srv.domain.extend.CommentMerchantDOView;
import com.lawu.eshop.mall.srv.mapper.CommentImageDOMapper;
import com.lawu.eshop.mall.srv.mapper.CommentMerchantDOMapper;
import com.lawu.eshop.mall.srv.mapper.extend.CommentMerchantDOMapperExtend;
import com.lawu.eshop.mall.srv.service.CommentMerchantService;
import com.lawu.eshop.utils.DateUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
@Service
public class CommentMerchantServiceImpl implements CommentMerchantService {

    @Autowired
    private CommentMerchantDOMapper commentMerchantDOMapper;
    @Autowired
    private CommentImageDOMapper commentImageDOMapper;
    @Autowired
    private CommentMerchantDOMapperExtend commentMerchantDOMapperExtend;

    @Autowired
    @Qualifier("orderCommentMerchantTransactionMainServiceImpl")
    private TransactionMainService transactionMainService;


    @Override
    @Transactional
    public Integer saveCommentMerchantInfo(Long memberId, CommentMerchantParam param, String commentPic) {

        CommentMerchantDO commentMerchantDO = new CommentMerchantDO();
        commentMerchantDO.setMemberId(memberId);
        commentMerchantDO.setMerchantId(param.getMerchantId());
        commentMerchantDO.setContent(param.getContent());
        commentMerchantDO.setGrade(param.getGradeEnum().val);
        commentMerchantDO.setAvgSpend(param.getAvgSpend());
        commentMerchantDO.setIsAnonymous(param.getAnonymousEnum().val);//匿名

        commentMerchantDO.setStatus(CommentStatusEnum.COMMENT_STATUS_VALID.val);
        commentMerchantDO.setGmtCreate(new Date());
        commentMerchantDO.setGmtModified(new Date());
        commentMerchantDOMapper.insert(commentMerchantDO);
        Long id = commentMerchantDO.getId();
        if (!StringUtils.isEmpty(commentPic)) {
            String imgs[] = commentPic.split(",");
            if (id != null && id > 0) {
                //新增评价图片
                for (int i = 0; i < imgs.length; i++) {
                    if (!StringUtils.isEmpty(imgs[i])) {
                        CommentImageDO commentImageDO = new CommentImageDO();
                        commentImageDO.setCommentId(id);
                        commentImageDO.setImgUrl(imgs[i]);
                        commentImageDO.setStatus(true);//有效
                        commentImageDO.setType(CommentTypeEnum.COMMENT_TYPE_MERCHANT.val);//评论商家
                        commentImageDO.setGmtCreate(new Date());
                        commentImageDO.setGmtModified(new Date());
                        commentImageDOMapper.insert(commentImageDO);
                    }
                }
            }
        }
        transactionMainService.sendNotice(param.getOrderId());
        return id.intValue();
    }

    @Override
    public Page<CommentMerchantBO> getCommentMerchantAllList(CommentMerchantListParam listParam) {

        CommentMerchantDOExample example = new CommentMerchantDOExample();
        example.createCriteria().andStatusEqualTo(CommentStatusEnum.COMMENT_STATUS_VALID.val).
                andMerchantIdEqualTo(listParam.getMerchantId());
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(listParam.getOffset(), listParam.getPageSize());
        Page<CommentMerchantBO> page = new Page<>();
        page.setTotalCount(commentMerchantDOMapper.countByExample(example));
        page.setCurrentPage(listParam.getCurrentPage());

        //查询评价列表
        List<CommentMerchantDO> commentMerchantDOS = commentMerchantDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<CommentMerchantBO> commentMerchantBOS = new ArrayList<>();
        for (CommentMerchantDO commentMerchantDO : commentMerchantDOS) {

            CommentMerchantBO commentMerchantBO = CommentMerchantConverter.converBO(commentMerchantDO);

            //查询图片
            CommentImageDOExample imageDOExample = new CommentImageDOExample();
            imageDOExample.createCriteria().andCommentIdEqualTo(commentMerchantDO.getId()).andTypeEqualTo(CommentTypeEnum.COMMENT_TYPE_MERCHANT.val);
            List<CommentImageDO> commentImageDOS = commentImageDOMapper.selectByExample(imageDOExample);
            List<String> images = new ArrayList<String>();
            if (!commentImageDOS.isEmpty()) {
                for (int i = 0; i < commentImageDOS.size(); i++) {
                    images.add(commentImageDOS.get(i).getImgUrl());
                }
                commentMerchantBO.setUrlImgs(images);
            }

            commentMerchantBOS.add(commentMerchantBO);
        }
        page.setRecords(commentMerchantBOS);
        return page;
    }

    @Override
    public Page<CommentMerchantBO> getCommentMerchantListWithImgs(CommentMerchantListParam listParam) {
        int totalCount = commentMerchantDOMapperExtend.selectCountByMerchantId(listParam.getMerchantId());

        Page<CommentMerchantBO> commentProductBOPage = new Page<CommentMerchantBO>();
        commentProductBOPage.setTotalCount(totalCount);
        commentProductBOPage.setCurrentPage(listParam.getCurrentPage());

        CommentMerchantPageParam merchantPageParam = new CommentMerchantPageParam();
        merchantPageParam.setCurrentPage(listParam.getOffset());
        merchantPageParam.setPageSize(listParam.getPageSize());
        merchantPageParam.setMerchantId(listParam.getMerchantId());
        //查询评论列表信息
        List<CommentMerchantDOView> commentMerchantDOViews = commentMerchantDOMapperExtend.selectCommentsWithImg(merchantPageParam);
        Page<CommentMerchantBO> pages = new Page<CommentMerchantBO>();
        List<CommentMerchantBO> commentMerchantBOS = new ArrayList<CommentMerchantBO>();
        if (!commentMerchantDOViews.isEmpty()) {
            for (CommentMerchantDOView commentMerchantDOView : commentMerchantDOViews) {
                CommentMerchantBO commentMerchantBO = CommentMerchantConverter.converterBOFromView(commentMerchantDOView);

                //查询对应的评价图片
                CommentImageDOExample imageDOExample = new CommentImageDOExample();
                imageDOExample.createCriteria().andCommentIdEqualTo(commentMerchantDOView.getId()).andTypeEqualTo(CommentTypeEnum.COMMENT_TYPE_MERCHANT.val);
                List<CommentImageDO> commentImageDOS = commentImageDOMapper.selectByExample(imageDOExample);
                List<String> images = new ArrayList<String>();
                if (!commentImageDOS.isEmpty()) {
                    for (int i = 0; i < commentImageDOS.size(); i++) {
                        images.add(commentImageDOS.get(i).getImgUrl());
                    }
                    commentMerchantBO.setUrlImgs(images);
                }
                commentMerchantBOS.add(commentMerchantBO);
            }
        }
        pages.setCurrentPage(listParam.getCurrentPage());
        pages.setTotalCount(totalCount);
        pages.setRecords(commentMerchantBOS);
        return pages;
    }

    @Override
    public CommentMerchantBO findMerchantComment(Long commentId) {
        CommentMerchantDO commentMerchantDO = commentMerchantDOMapper.selectByPrimaryKey(commentId);
        CommentMerchantBO commentMerchantBO = CommentMerchantConverter.converBO(commentMerchantDO);
        return commentMerchantBO;
    }

    @Override
    @Transactional
    public int replyMerchantComment(Long commentId, String replyContent) {
        CommentMerchantDO commentMerchantDO = new CommentMerchantDO();
        commentMerchantDO.setId(commentId);
        commentMerchantDO.setReplyContent(replyContent);
        commentMerchantDO.setGmtReply(new Date());
        int row = commentMerchantDOMapper.updateByPrimaryKeySelective(commentMerchantDO);
        return row;
    }

    @Override
    public CommentGradeBO getCommentAvgGrade(Long merchantId) {
        CommentMerchantDOExample commentMerchantDOExample = new CommentMerchantDOExample();
        commentMerchantDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        List<CommentMerchantDO> commentMerchantDOS = commentMerchantDOMapper.selectByExample(commentMerchantDOExample);
        if (commentMerchantDOS.isEmpty()) {
            return null;
        }

        //平均得分
        Double avgGrade = commentMerchantDOMapperExtend.selectAvgGrade(merchantId);
        avgGrade = new BigDecimal(avgGrade).setScale(2, RoundingMode.UP).doubleValue();

        //好评率
        Integer goodCount = commentMerchantDOMapperExtend.selectGoodGradeCount(merchantId);
        CommentMerchantDOExample example = new CommentMerchantDOExample();
        example.createCriteria().andStatusEqualTo(CommentStatusEnum.COMMENT_STATUS_VALID.val).
                andMerchantIdEqualTo(merchantId);
        Integer totalCount = commentMerchantDOMapper.countByExample(example);
        double goodGrade = new BigDecimal((double) goodCount / totalCount).setScale(2, RoundingMode.UP).doubleValue();

        //人均消费
        Double averageConsumeAmount = commentMerchantDOMapperExtend.getAvgSpend(merchantId);
        averageConsumeAmount = new BigDecimal(averageConsumeAmount).setScale(2, RoundingMode.UP).doubleValue();

        CommentGradeBO commentGradeBO = new CommentGradeBO();
        commentGradeBO.setAverageConsumeAmount(averageConsumeAmount);
        commentGradeBO.setAvgGrade(avgGrade);
        commentGradeBO.setGoodGrad(goodGrade);
        return commentGradeBO;
    }

    @Override
    public Page<CommentMerchantBO> getCommentMerchantListOperator(CommentListParam listParam) {
        CommentMerchantDOExample example = new CommentMerchantDOExample();
        example.setOrderByClause("id desc");
        CommentMerchantDOExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(new Byte("1"));
        if(org.apache.commons.lang.StringUtils.isNotEmpty(listParam.getBeginDate())){
            criteria.andGmtCreateGreaterThanOrEqualTo(DateUtil.stringToDate(listParam.getBeginDate() + " 00:00:00"));
        }
        if(org.apache.commons.lang.StringUtils.isNotEmpty(listParam.getEndDate())){
            criteria.andGmtCreateLessThanOrEqualTo(DateUtil.stringToDate(listParam.getEndDate() + " 23:59:59"));
        }
        RowBounds rowBounds = new RowBounds(listParam.getOffset(), listParam.getPageSize());
        Page<CommentMerchantBO> page = new Page<>();
        page.setTotalCount(commentMerchantDOMapper.countByExample(example));
        page.setCurrentPage(listParam.getCurrentPage());

        //查询评价列表
        List<CommentMerchantDO> commentMerchantDOS = commentMerchantDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        if (commentMerchantDOS.isEmpty()) {
            return null;
        }
        List<CommentMerchantBO> commentMerchantBOS = new ArrayList<>();
        for (CommentMerchantDO commentProductDO : commentMerchantDOS) {
            CommentMerchantBO commentMerchantBO = CommentMerchantConverter.converBO(commentProductDO);
            commentMerchantBOS.add(commentMerchantBO);
        }
        page.setRecords(commentMerchantBOS);
        return page;
    }

    @Override
    @Transactional
    public void delCommentMerchantInfo(Long commentId) {
        CommentMerchantDO commentMerchantDO = new CommentMerchantDO();
        commentMerchantDO.setId(commentId);
        commentMerchantDO.setStatus(CommentStatusEnum.COMMENT_STATUS_INVALID.val);
        commentMerchantDOMapper.updateByPrimaryKeySelective(commentMerchantDO);
    }

}
