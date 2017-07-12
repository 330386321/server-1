package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.CommentAnonymousEnum;
import com.lawu.eshop.mall.constants.CommentGradeEnum;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.mall.srv.bo.CommentProductBO;
import com.lawu.eshop.mall.srv.domain.CommentImageDO;
import com.lawu.eshop.mall.srv.domain.CommentProductDO;
import com.lawu.eshop.mall.srv.mapper.CommentImageDOMapper;
import com.lawu.eshop.mall.srv.mapper.CommentProductDOMapper;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class CommentProductServiceImplTest {

    @Autowired
    private CommentProductService commentProductService;

    @Autowired
    private CommentProductDOMapper commentProductDOMapper;

    @Autowired
    private CommentImageDOMapper commentImageDOMapper;

    @Transactional
    @Rollback
    @Test
    public void saveCommentProductInfo(){
        CommentProductParam param = new CommentProductParam();
        param.setContent("test");
        param.setAnonymousEnum(CommentAnonymousEnum.COMMENT_ANONYMOUS);
        param.setMerchantId(1L);
        param.setGradeEnum(CommentGradeEnum.COMMENT_STAR_LEVEL_FOUR);
        param.setProductId(1L);
        param.setProductModelId(1L);
        param.setShoppingOrderItemId(1L);
        String pic = "default/default_mediaUrl.png,default/default_mediaUrl2.png";
        commentProductService.saveCommentProductInfo(1L,param,pic);
        List<CommentProductDO> list =commentProductDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());

        List<CommentImageDO> commentImageDOS = commentImageDOMapper.selectByExample(null);
        Assert.assertNotNull(commentImageDOS);
        Assert.assertEquals(2,commentImageDOS.size());


    }

    @Transactional
    @Rollback
    @Test
    public void getCommentProducts(){
        CommentProductParam param = new CommentProductParam();
        param.setContent("test");
        param.setAnonymousEnum(CommentAnonymousEnum.COMMENT_ANONYMOUS);
        param.setMerchantId(1L);
        param.setGradeEnum(CommentGradeEnum.COMMENT_STAR_LEVEL_FOUR);
        param.setProductId(1L);
        param.setProductModelId(1L);
        param.setShoppingOrderItemId(1L);
        String pic = "default/default_mediaUrl.png,default/default_mediaUrl2.png";
        commentProductService.saveCommentProductInfo(1L,param,pic);

        CommentProductListParam listParam =  new CommentProductListParam();
        listParam.setProductId(1L);
        listParam.setCurrentPage(1);
        listParam.setPageSize(10);
        Page<CommentProductBO> page = commentProductService.getCommentProducts(listParam);
        Assert.assertNotNull(page);
        Assert.assertEquals(1,page.getTotalCount().intValue());

    }


    @Transactional
    @Rollback
    @Test
    public void getCommentProductsWithImgs(){
        CommentProductParam param = new CommentProductParam();
        param.setContent("test");
        param.setAnonymousEnum(CommentAnonymousEnum.COMMENT_ANONYMOUS);
        param.setMerchantId(1L);
        param.setGradeEnum(CommentGradeEnum.COMMENT_STAR_LEVEL_FOUR);
        param.setProductId(1L);
        param.setProductModelId(1L);
        param.setShoppingOrderItemId(1L);
        String pic = "default/default_mediaUrl.png,default/default_mediaUrl2.png";
        commentProductService.saveCommentProductInfo(1L,param,pic);

        commentProductService.saveCommentProductInfo(1L,param,"");

        CommentProductListParam listParam =  new CommentProductListParam();
        listParam.setProductId(1L);
        listParam.setCurrentPage(1);
        listParam.setPageSize(10);
        Page<CommentProductBO> page = commentProductService.getCommentProductsWithImgs(listParam);
        Assert.assertNotNull(page);
        Assert.assertEquals(1,page.getTotalCount().intValue());
    }

    public void findProductComment(){

    }
}
