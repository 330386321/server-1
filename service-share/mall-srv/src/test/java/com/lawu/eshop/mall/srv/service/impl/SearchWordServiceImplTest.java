package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.SearchWordTypeEnum;
import com.lawu.eshop.mall.param.SearchWordParam;
import com.lawu.eshop.mall.srv.bo.SearchWordBO;
import com.lawu.eshop.mall.srv.domain.SearchWordDO;
import com.lawu.eshop.mall.srv.mapper.SearchWordDOMapper;
import com.lawu.eshop.mall.srv.service.SearchWordService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class SearchWordServiceImplTest {
    @Autowired
    private SearchWordDOMapper searchWordDOMapper;
    @Autowired
    private SearchWordService searchWordService;

    @Transactional
    @Rollback
    @Test
    public void saveSearchWord(){
        searchWordService.saveSearchWord("test", SearchWordTypeEnum.WORD_TYPE_PRODUCT);
        List<SearchWordDO> searchWordDOS = searchWordDOMapper.selectByExample(null);
        Assert.assertNotNull(searchWordDOS);
        Assert.assertEquals(1,searchWordDOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void getSearchWordById(){
        SearchWordDO searchWordDO = new SearchWordDO();
        searchWordDO.setWord("test");
        searchWordDO.setType(SearchWordTypeEnum.WORD_TYPE_PRODUCT.val);
        searchWordDO.setGmtCreate(new Date());
        searchWordDOMapper.insertSelective(searchWordDO);

        SearchWordBO searchWordBO = searchWordService.getSearchWordById(searchWordDO.getId());
        Assert.assertNotNull(searchWordBO);
        Assert.assertEquals(SearchWordTypeEnum.WORD_TYPE_PRODUCT.val,searchWordBO.getType());
    }

    @Transactional
    @Rollback
    @Test
    public void  deleteSearchWordById(){
        SearchWordDO searchWordDO = new SearchWordDO();
        searchWordDO.setWord("test");
        searchWordDO.setType(SearchWordTypeEnum.WORD_TYPE_PRODUCT.val);
        searchWordDO.setGmtCreate(new Date());
        searchWordDOMapper.insertSelective(searchWordDO);

        searchWordService.deleteSearchWordById(searchWordDO.getId());
        List<SearchWordDO> searchWordDOS = searchWordDOMapper.selectByExample(null);
        Assert.assertEquals(0,searchWordDOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void listSearchWord(){
        SearchWordDO searchWordDO = new SearchWordDO();
        searchWordDO.setWord("test");
        searchWordDO.setType(SearchWordTypeEnum.WORD_TYPE_PRODUCT.val);
        searchWordDO.setGmtCreate(new Date());
        searchWordDOMapper.insertSelective(searchWordDO);

        SearchWordParam param = new SearchWordParam();
        param.setCurrentPage(1);
        param.setPageSize(10);
        param.setSearchWordTypeEnum(SearchWordTypeEnum.WORD_TYPE_PRODUCT);
        Page<SearchWordBO> page = searchWordService.listSearchWord(param);
        Assert.assertNotNull(page.getRecords());
        Assert.assertEquals(1,page.getRecords().size());
    }
}
