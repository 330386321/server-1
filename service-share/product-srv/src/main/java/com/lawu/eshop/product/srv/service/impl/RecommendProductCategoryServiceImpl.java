package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.ListRecommendProductCategoryParam;
import com.lawu.eshop.product.srv.bo.RecommendProductCategoryBO;
import com.lawu.eshop.product.srv.converter.RecommendProductCategoryConverter;
import com.lawu.eshop.product.srv.domain.RecommendProductCategoryDO;
import com.lawu.eshop.product.srv.domain.RecommendProductCategoryDOExample;
import com.lawu.eshop.product.srv.mapper.RecommendProductCategoryDOMapper;
import com.lawu.eshop.product.srv.service.RecommendProductCategoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/10.
 */
@Service
public class RecommendProductCategoryServiceImpl implements RecommendProductCategoryService {

    @Autowired
    private RecommendProductCategoryDOMapper recommendProductCategoryDOMapper;

    @Override
    public void saveRecommendProductCategory(Integer categoryId, String categoryName) {
        RecommendProductCategoryDO recommendProductCategoryDO = new RecommendProductCategoryDO();
        recommendProductCategoryDO.setCategoryId(categoryId);
        recommendProductCategoryDO.setCategoryName(categoryName);
        recommendProductCategoryDO.setGmtCreate(new Date());
        recommendProductCategoryDOMapper.insertSelective(recommendProductCategoryDO);
    }

    @Override
    public void deleteRecommendProductCategoryById(Long id) {
        recommendProductCategoryDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public RecommendProductCategoryBO getRecommendProductCategoryById(Long id) {
        RecommendProductCategoryDO recommendProductCategoryDO = recommendProductCategoryDOMapper.selectByPrimaryKey(id);
        return RecommendProductCategoryConverter.convertBO(recommendProductCategoryDO);
    }

    @Override
    public Page<RecommendProductCategoryBO> listRecommendProductCategory(ListRecommendProductCategoryParam listRecommendProductCategoryParam) {
        RecommendProductCategoryDOExample recommendProductCategoryDOExample = new RecommendProductCategoryDOExample();
        recommendProductCategoryDOExample.setOrderByClause("ordinal is null, ordinal");

        RowBounds rowBounds = new RowBounds(listRecommendProductCategoryParam.getOffset(), listRecommendProductCategoryParam.getPageSize());
        Page<RecommendProductCategoryBO> page = new Page<>();
        page.setTotalCount(recommendProductCategoryDOMapper.countByExample(recommendProductCategoryDOExample));
        page.setCurrentPage(listRecommendProductCategoryParam.getCurrentPage());

        List<RecommendProductCategoryDO> recommendProductCategoryDOList = recommendProductCategoryDOMapper.selectByExampleWithRowbounds(recommendProductCategoryDOExample, rowBounds);
        page.setRecords(RecommendProductCategoryConverter.convertBO(recommendProductCategoryDOList));
        return page;
    }

    @Override
    public List<RecommendProductCategoryBO> listAllRecommendProductCategory() {
        RecommendProductCategoryDOExample recommendProductCategoryDOExample = new RecommendProductCategoryDOExample();
        recommendProductCategoryDOExample.setOrderByClause("ordinal is null, ordinal");
        List<RecommendProductCategoryDO> recommendProductCategoryDOList = recommendProductCategoryDOMapper.selectByExample(recommendProductCategoryDOExample);
        return RecommendProductCategoryConverter.convertBO(recommendProductCategoryDOList);
    }
}
