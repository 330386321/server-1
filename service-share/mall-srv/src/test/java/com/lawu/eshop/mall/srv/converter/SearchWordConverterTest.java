package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.constants.SearchWordTypeEnum;
import com.lawu.eshop.mall.dto.SearchWordDTO;
import com.lawu.eshop.mall.srv.bo.SearchWordBO;
import com.lawu.eshop.mall.srv.converter.SearchWordConverter;
import com.lawu.eshop.mall.srv.domain.SearchWordDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class SearchWordConverterTest {

    @Test
    public void convertBO() {
        SearchWordDO searchWordDO = new SearchWordDO();
        searchWordDO.setId(1L);
        searchWordDO.setWord("KEY");
        searchWordDO.setType(SearchWordTypeEnum.WORD_TYPE_PRODUCT.val);
        searchWordDO.setGmtCreate(new Date());
        SearchWordBO searchWordBO = SearchWordConverter.convertBO(searchWordDO);
        Assert.assertEquals(searchWordDO.getWord(), searchWordBO.getWord());
    }

    @Test
    public void convertDTO() {
        SearchWordBO searchWordBO = new SearchWordBO();
        searchWordBO.setId(1L);
        searchWordBO.setWord("KEY");
        searchWordBO.setType(SearchWordTypeEnum.WORD_TYPE_PRODUCT.val);
        searchWordBO.setGmtCreate(new Date());
        SearchWordDTO searchWordDTO = SearchWordConverter.convertDTO(searchWordBO);
        Assert.assertEquals(searchWordBO.getWord(), searchWordDTO.getWord());
    }
}
