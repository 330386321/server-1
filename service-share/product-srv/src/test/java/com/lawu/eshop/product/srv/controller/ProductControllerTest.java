/**
 * 
 */
package com.lawu.eshop.product.srv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.ProductSrvApplicationTest;
import com.lawu.eshop.product.srv.domain.ProductModelDO;

/**
 * @author lihj
 * @date 2017年7月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductSrvApplicationTest.class)
@WebAppConfiguration
public class ProductControllerTest {

	private MockMvc mvc;

	private Logger log = Logger.getLogger(FavoriteProductControllerTest.class);

	@Autowired
	private ProductController productController;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	/**
	 * 添加、编辑商品
	 */
	@Transactional
	@Rollback
	@Test
	public void testsaveProduct() {
		EditProductDataParam product = initProduct();
		RequestBuilder request = post("/product/saveProduct").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(product));
		try{
			ResultActions perform = mvc.perform(request);
			log.info(perform.andReturn().getResponse().getContentAsString());
			MvcResult mvcResult = perform.andExpect(status().is(HttpCode.SC_CREATED)).andDo(MockMvcResultHandlers.print())
					.andReturn();
			Assert.assertEquals(HttpCode.SC_CREATED, mvcResult.getResponse().getStatus());
		}catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * 查询商品列表
	 */
	@Transactional
	@Rollback
	@Test
	public void testselectProduct() {
		ProductDataQuery query = new ProductDataQuery(); 
		RequestBuilder request =post("/product/selectProduct").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(query));
	}

	/**
	 * 商品批量操作
	 */
	@Transactional
	@Rollback
	@Test
	public void testupdateProductStatus() {

	}

	/**
	 * 用户端商品详情，根据ID查询商品详情
	 */
	@Transactional
	@Rollback
	@Test
	public void testselectProductById() {

	}

	/**
	 * 商家端编辑商品时，根据ID查询商品
	 */
	@Transactional
	@Rollback
	@Test
	public void testselectEditProductById() {

	}

	/**
	 * 操作库存 num 加减数量数量 flag M-减、A-加
	 */
	@Transactional
	@Rollback
	@Test
	public void testeditTotalInventory() {

	}

	/**
	 * 操作销量 num 加减数量数量 flag M-减、A-加
	 */
	@Transactional
	@Rollback
	@Test
	public void editTotalSaleVolume() {

	}

	/**
	 * 根据商品ID查询商品
	 */
	@Transactional
	@Rollback
	@Test
	public void getProduct() {

	}

	/**
	 * 查询已审核的所有商品
	 */
	@Transactional
	@Rollback
	@Test
	public void testselectProductByPlat() {

	}

	/**
	 * 查询商家上架商品的总数量
	 */
	@Transactional
	@Rollback
	@Test
	public void testselectProductCount() {

	}

	/**
	 * 查询所有上架中商品
	 */
	@Transactional
	@Rollback
	@Test
	public void testlistProduct() {

	}

	/**
	 * 更新商品平均日销量，同时更新solr
	 */
	@Transactional
	@Rollback
	@Test
	public void testupdateAverageDailySales() {

	}

	/**
	 * 更新商品索引
	 */
	@Transactional
	@Rollback
	@Test
	public void testupdateProductIndex() {

	}

	/**
	 * 重建商品索引
	 */
	@Transactional
	@Rollback
	@Test
	public void testrebuildProductIndex() {

	}

	/**
	 * 删除无效的商品索引
	 */
	@Transactional
	@Rollback
	@Test
	public void testdelInvalidProductIndex() {

	}

	/**
	 * 查询所有上架的商品
	 */
	@Transactional
	@Rollback
	@Test
	public void testlistAllProduct() {

	}

	/**
	 * 根据ids查询商品信息
	 */
	@Transactional
	@Rollback
	@Test
	public void testlistProductByIds() {

	}
	
	
	/**
	 * @return
	 */
	private EditProductDataParam initProduct() {
		EditProductDataParam product =new EditProductDataParam();
		product.setProductId(0L);
		product.setCategoryId(1);
		product.setName("小炒肉");
		product.setContent("商品描述");
		List<ProductModelDO> listModel =new ArrayList<ProductModelDO>();
		ProductModelDO model =new ProductModelDO();
		model.setName("大碗");
		model.setOriginalPrice(new BigDecimal(15));
		model.setPrice(new BigDecimal(12));
		model.setInventory(999);
		model.setSalesVolume(0);
		ProductModelDO model1 =new ProductModelDO();
		model1.setName("小碗");
		model1.setOriginalPrice(new BigDecimal(10));
		model1.setPrice(new BigDecimal(8));
		model1.setInventory(888);
		model1.setSalesVolume(0);
		listModel.add(model);
		listModel.add(model1);
		product.setSpec(JSON.toJSONString(listModel));
		product.setImageContents("详情图片描述");
		product.setIsAllowRefund(true);
		product.setMerchantId(1L);
		product.setMerchantNum("1");
		product.setFeatureImage("www.baidu.com");
		product.setProductImages("www.163.com");
		product.setDetailImages("111");
		return product;
	}

}
