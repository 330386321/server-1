/**
 * 
 */
package com.lawu.eshop.product.srv.service.impl;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.query.FavoriteProductQuery;
import com.lawu.eshop.product.srv.ProductSrvApplicationTest;
import com.lawu.eshop.product.srv.bo.FavoriteProductBO;
import com.lawu.eshop.product.srv.service.FavoriteProductService;

/**
 * @author lihj
 * @date 2017年7月17日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductSrvApplicationTest.class)
@WebAppConfiguration
public class FavoriteProductServiceImplTest {

	private MockMvc mvc;
	
	private Logger log=Logger.getLogger(FavoriteProductServiceImplTest.class);
	
	@Autowired
	private FavoriteProductService favoriteProductService;
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(favoriteProductService).build();
	}
	
	/**
	 * 商品收藏
	 */
	@Transactional
    @Rollback
    @Test
	public void save(){
		Long memberId =1L;
		Long productId=1L;
		favoriteProductService.save(memberId, productId);
		FavoriteProductQuery query =new FavoriteProductQuery();
		Page<FavoriteProductBO> page =favoriteProductService.selectMyFavoriteProduct(memberId, query);
		Assert.assertEquals(page.getRecords().size(), 1);
	}
	
	/**
	 * 取消收藏
	 */
	@Transactional
	@Rollback
	@Test
	public void remove(){
		Long memberId =1L;
		Long productId=1L;
		favoriteProductService.save(memberId, productId);
		favoriteProductService.remove(productId, memberId);
		FavoriteProductQuery query =new FavoriteProductQuery();
		Page<FavoriteProductBO> page =favoriteProductService.selectMyFavoriteProduct(memberId, query);
		Assert.assertEquals(page.getRecords().size(), 0);
	}
	
	/**
	 * 我收藏的商品
	 */
	@Transactional
	@Rollback
	@Test
	public void selectMyFavoriteProduct(){
		Long memberId =1L;
		Long productId=1L;
		favoriteProductService.save(memberId, productId);
		FavoriteProductQuery query =new FavoriteProductQuery();
		Page<FavoriteProductBO> page =favoriteProductService.selectMyFavoriteProduct(memberId, query);
		Assert.assertEquals(page.getRecords().size(), 1);
	}
	
	@Transactional
	@Rollback
	@Test
	public void getUserFavorite(){
		Long memberId =1L;
		Long productId=1L;
		favoriteProductService.save(memberId, productId);
		Integer count = favoriteProductService.getUserFavorite(productId, memberId);
		Assert.assertEquals(count, Integer.valueOf(1));
	}
	
	
	
	
}
