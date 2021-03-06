/**
 * 
 */
package com.lawu.eshop.product.srv.service.impl;

import org.apache.log4j.Logger;
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

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import com.lawu.eshop.product.srv.ProductSrvApplicationTest;
import com.lawu.eshop.product.srv.bo.ProductSearchBO;
import com.lawu.eshop.product.srv.service.ShoppingProductService;

/**
 * @author lihj
 * @date 2017年7月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductSrvApplicationTest.class)
@WebAppConfiguration
public class ShoppingProductServiceTest {

	private MockMvc mvc;

	private Logger log = Logger.getLogger(ProductServiceTest.class);

	@Autowired
	private ShoppingProductService shoppingProductService;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(shoppingProductService).build();
	}

	/**
	 * 
	 */
	@Transactional
	@Rollback
	@Test
	public void listHotProduct() {
		ListShoppingProductParam param = new ListShoppingProductParam();
		param.setMerchantId(1L);
		param.setCurrentPage(1);
		param.setPageSize(10);
		Page<ProductSearchBO> list = shoppingProductService.listHotProduct(param);
		log.info(JSON.toJSON(list));

	}

	@Transactional
	@Rollback
	@Test
	public void listAllProduct() {
		ListShoppingProductParam param = new ListShoppingProductParam();
		param.setMerchantId(1L);
		param.setCurrentPage(1);
		param.setPageSize(10);
		Page<ProductSearchBO> list = shoppingProductService.listAllProduct(param);
		log.info(JSON.toJSON(list));
	}

	@Transactional
	@Rollback
	@Test
	public void listNewProduct() {
		ListShoppingProductParam param = new ListShoppingProductParam();
		param.setMerchantId(1L);
		param.setCurrentPage(1);
		param.setPageSize(10);
		Page<ProductSearchBO> list = shoppingProductService.listNewProduct(param);
		log.info(JSON.toJSON(list));
	}

}
