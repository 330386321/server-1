/**
 * 
 */
package com.lawu.eshop.product.srv.service.impl;

import java.util.List;

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

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.product.srv.ProductSrvApplicationTest;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;
import com.lawu.eshop.product.srv.mapper.ProductCategoryeDOMapper;
import com.lawu.eshop.product.srv.service.ProductCategoryService;

/**
 * @author lihj
 * @date 2017年7月17日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductSrvApplicationTest.class)
@WebAppConfiguration
public class ProductCategoryServiceTest {

	private MockMvc mvc;

	private Logger log=Logger.getLogger(ProductCategoryServiceTest.class);
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductCategoryeDOMapper productCategoryeDOMapper;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(productCategoryService).build();
	}
	
	@Transactional
    @Rollback
    @Test
	public void save(){
		ProductCategoryeDO pc =new ProductCategoryeDO();
		pc.setIsVirtual(true);
		pc.setLevel(Byte.valueOf("1"));
		pc.setName("测试".concat("1"));
		pc.setOrdinal(Byte.valueOf("2"));
		pc.setParentId(-1);
		pc.setPath("path");
		pc.setStatue(true);
		pc.setType(Byte.valueOf("3"));
		productCategoryeDOMapper.insert(pc);
	}
	
	
	@Transactional
    @Rollback
    @Test
    public void findAll(){
		Integer idOne = saveCategory("1");
		Integer idTwo = saveCategory("2");
		List<ProductCategoryBO> list = productCategoryService.findAll();
		log.info(JSON.toJSON(list));
		Assert.assertEquals(list.size(), 2);
	}

	
	/**
	 * 通过ID查询
	 */
	@Transactional
    @Rollback
    @Test
	public void getById() {
		Integer idOne = saveCategory("1");
		Integer idTwo = saveCategory("2");
		ProductCategoryBO bo = productCategoryService.getById(idOne);
		List<ProductCategoryBO> list = productCategoryService.findAll();
		log.info(JSON.toJSON(list));
		Assert.assertNotNull(bo);
	}
	
	@Transactional
    @Rollback
    @Test
	public void getFullName() {
		Integer idZero = saveCategory("0");
		Integer idOne = saveCategory("1");
		Integer idTwo = saveTwoPathCategory("2",idZero+"/"+idOne);
		Integer idThree = saveThreeCategory("3",idZero+"/"+idOne+"/"+idTwo);
		List<ProductCategoryBO> list = productCategoryService.findAll();
		log.info(JSON.toJSON(list));
		String str = productCategoryService.getFullName(list.get(1).getId());
		log.info(str);
		Assert.assertEquals("测试1",str);
		String strTwo = productCategoryService.getFullName(list.get(2).getId());
		log.info(strTwo);
		Assert.assertEquals("测试0-测试1",strTwo);
		String strThree = productCategoryService.getFullName(list.get(3).getId());
		log.info(strThree);
		Assert.assertEquals("测试0-测试1-测试2",strThree);
	}
	 
	/**
	 * 查询推荐商品类别
	 * 
	 * @return
	 */
	@Transactional
    @Rollback
    @Test
	public void listRecommendProductCategory() {
		Integer idOne = saveCategory("1");
		Integer idTwo = saveCategory("2");
		List<ProductCategoryBO> list =productCategoryService.listRecommendProductCategory();
		log.info(list.size());
		Assert.assertEquals(list.size(),2);
	}
	
	@Transactional
    @Rollback
    @Test
	public void find() {
		Integer idOne = saveCategory("1");
		Integer idTwo = saveCategory("2");
		List<ProductCategoryBO> list = productCategoryService.find(Long.valueOf(-1));
		Assert.assertEquals(list.size(),2);
	}
	
	
	/**
	 * 根据商品类别ID查询商品完整ID
	 *
	 * @param id
	 * @return
	 */
	@Transactional
    @Rollback
    @Test
	public void getFullCategoryId() {
		Integer idOne = saveCategory("1");
		Integer idTwo = saveCategory("2");
		List<ProductCategoryBO> list =productCategoryService.listRecommendProductCategory();
		for (int i = 0; i < list.size(); i++) {
			String str = productCategoryService.getFullCategoryId(list.get(i).getId());
			log.info(str);
			Assert.assertEquals(str,list.get(i).getId().toString());
			
		}
	}
	
	
	/**
	 * @param string
	 */
	private Integer saveCategory(String str) {
		ProductCategoryeDO pc =new ProductCategoryeDO();
		pc.setIsVirtual(true);
		pc.setLevel(Byte.valueOf("1"));
		pc.setName("测试".concat(str));
		pc.setOrdinal(Byte.valueOf("2"));
		pc.setParentId(-1);
		pc.setPath("1");
		pc.setStatue(true);
		pc.setType(Byte.valueOf("3"));
		productCategoryeDOMapper.insert(pc);
		return pc.getId();
	}
	/**
	 * @param string
	 */
	private Integer saveTwoPathCategory(String str,String path) {
		ProductCategoryeDO pc =new ProductCategoryeDO();
		pc.setIsVirtual(true);
		pc.setLevel(Byte.valueOf("1"));
		pc.setName("测试".concat(str));
		pc.setOrdinal(Byte.valueOf("2"));
		pc.setParentId(-1);
		pc.setPath(path);
		pc.setStatue(true);
		pc.setType(Byte.valueOf("3"));
		productCategoryeDOMapper.insert(pc);
		return pc.getId();
	}
	/**
	 * @param string
	 */
	private Integer saveThreeCategory(String str,String path) {
		ProductCategoryeDO pc =new ProductCategoryeDO();
		pc.setIsVirtual(true);
		pc.setLevel(Byte.valueOf("1"));
		pc.setName("测试".concat(str));
		pc.setOrdinal(Byte.valueOf("2"));
		pc.setParentId(-1);
		pc.setPath(path);
		pc.setStatue(true);
		pc.setType(Byte.valueOf("3"));
		productCategoryeDOMapper.insert(pc);
		return pc.getId();
	}
	
}
