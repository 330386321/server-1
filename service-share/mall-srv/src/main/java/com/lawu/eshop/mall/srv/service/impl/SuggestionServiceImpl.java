package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.SuggestionListParam;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.srv.bo.SuggestionBO;
import com.lawu.eshop.mall.srv.converter.SuggestionConverter;
import com.lawu.eshop.mall.srv.domain.SuggestionDO;
import com.lawu.eshop.mall.srv.domain.SuggestionDOExample;
import com.lawu.eshop.mall.srv.mapper.SuggestionDOMapper;
import com.lawu.eshop.mall.srv.service.SuggestionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 反馈意见服务实现
 *
 * @author Sunny
 * @date 2017/3/24
 */
@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private SuggestionDOMapper suggestionDOMapper;

    @Transactional
    @Override
    public Integer save(String userNum, SuggestionParam param) {
        SuggestionDO suggestionDO = SuggestionConverter.convert(userNum, param);
        suggestionDO.setGmtCreate(new Date());
        suggestionDO.setGmtModified(new Date());

        // 空值交给Mybatis去处理
        int result = suggestionDOMapper.insertSelective(suggestionDO);

        // save返回id出去
        return suggestionDO.getId() != null ? suggestionDO.getId() : result;
    }

    @Override
    public Page<SuggestionBO> getSuggestionList(SuggestionListParam pageParam) {
        SuggestionDOExample suggestionDOExample = new SuggestionDOExample();
        if (pageParam.getBeginTime() != null && pageParam.getEndTime() != null) {
            suggestionDOExample.createCriteria().andGmtCreateBetween(pageParam.getBeginTime(),pageParam.getEndTime());
        }
        suggestionDOExample.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());
        Page<SuggestionBO> page = new Page<>();
        page.setTotalCount(suggestionDOMapper.countByExample(suggestionDOExample));
        page.setCurrentPage(pageParam.getCurrentPage());

        //查询意见反馈记录列表
        List<SuggestionDO> suggestionDOS = suggestionDOMapper.selectByExampleWithRowbounds(suggestionDOExample,rowBounds);
        if(suggestionDOS.isEmpty()){
            return  null;
        }
        List<SuggestionBO> suggestionBOS = SuggestionConverter.convertBOS(suggestionDOS);

        page.setRecords(suggestionBOS);
        return page;
    }

}
