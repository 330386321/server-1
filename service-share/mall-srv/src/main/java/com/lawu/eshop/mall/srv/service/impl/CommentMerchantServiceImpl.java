package com.lawu.eshop.mall.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.CommentAnonymousEnum;
import com.lawu.eshop.mall.constants.CommentStatusEnum;
import com.lawu.eshop.mall.constants.CommentTypeEnum;
import com.lawu.eshop.mall.param.CommentMerchantListParam;
import com.lawu.eshop.mall.param.CommentMerchantParam;
import com.lawu.eshop.mall.srv.bo.CommentMerchantBO;
import com.lawu.eshop.mall.srv.converter.CommentMerchantConverter;
import com.lawu.eshop.mall.srv.domain.CommentImageDO;
import com.lawu.eshop.mall.srv.domain.CommentImageDOExample;
import com.lawu.eshop.mall.srv.domain.CommentMerchantDO;
import com.lawu.eshop.mall.srv.domain.CommentMerchantDOExample;
import com.lawu.eshop.mall.srv.mapper.CommentImageDOMapper;
import com.lawu.eshop.mall.srv.mapper.CommentMerchantDOMapper;
import com.lawu.eshop.mall.srv.mapper.extend.CommentMerchantDOMapperExtend;
import com.lawu.eshop.mall.srv.service.CommentMerchantService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/6.
 */
@Service
public class CommentMerchantServiceImpl implements CommentMerchantService{

    @Autowired
    private CommentMerchantDOMapper commentMerchantDOMapper;
    @Autowired
    private CommentImageDOMapper commentImageDOMapper;
    @Autowired
    private CommentMerchantDOMapperExtend commentMerchantDOMapperExtend;



    @Override
    @Transactional
    public Integer saveCommentMerchantInfo(Long memberId, CommentMerchantParam param, String commentPic) {

        CommentMerchantDO  commentMerchantDO = new CommentMerchantDO();
        commentMerchantDO.setMemberId(memberId);
        commentMerchantDO.setMerchantId(param.getMerchantId());
        commentMerchantDO.setContent(param.getContent());
        commentMerchantDO.setGrade(param.getGradeEnum().val);
        if(CommentAnonymousEnum.COMMENT_ANONYMOUS_SUCCESS.equals(param.getAnonymousEnum())){
            commentMerchantDO.setIsAnonymous(true);//匿名
        }else{
            commentMerchantDO.setIsAnonymous(false);//不匿名
        }
        commentMerchantDO.setStatus(CommentStatusEnum.COMMENT_STATUS_VALID.val);
        commentMerchantDO.setGmtCreate(new Date());
        commentMerchantDO.setGmtModified(new Date());
      Integer id =   commentMerchantDOMapper.insert(commentMerchantDO);
        if (!StringUtils.isEmpty(commentPic)) {
            String imgs[] = commentPic.split(",");
            if (id != null && id > 0) {
                //新增评价图片
                for (int i = 0; i < imgs.length; i++) {
                    if (!StringUtils.isEmpty(imgs[i])) {
                        CommentImageDO commentImageDO = new CommentImageDO();
                        commentImageDO.setCommentId(Long.valueOf(id));
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
        return id;
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
            //查询平均评分
            float grade = commentMerchantDOMapperExtend.selectAvgGrade(commentMerchantDO.getMerchantId());
            commentMerchantBO.setGrade(grade);
            //查询图片
            CommentImageDOExample imageDOExample = new CommentImageDOExample();
            imageDOExample.createCriteria().andCommentIdEqualTo(commentMerchantDO.getId());
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
}
