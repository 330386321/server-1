/**
 * 
 */
package com.lawu.eshop.product.srv.converter;

import org.junit.Assert;
import org.junit.Test;

import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;


/**
 * @author lihj
 * @date 2017年7月13日
 */
public class ProductCategoryConverterTest {

	@Test
	public void convertBO(){
		ProductCategoryeDO pcd = new ProductCategoryeDO();
		pcd.setId(1);
		pcd.setName("测试");
		pcd.setLevel(Byte.valueOf("1"));
		pcd.setParentId(2);
		pcd.setPath("123123123");
		pcd.setType(Byte.valueOf("2"));
		ProductCategoryBO bo =ProductCategoryConverter.convertBO(pcd);
		Assert.assertEquals(bo.getId(), pcd.getId());
		Assert.assertEquals(bo.getName(), pcd.getName());
		Assert.assertEquals(Byte.valueOf(bo.getLevel().toString()), pcd.getLevel());
		Assert.assertEquals(bo.getParentId(), pcd.getParentId());
		Assert.assertEquals(bo.getPath(), pcd.getPath());
		Assert.assertEquals(Byte.valueOf(bo.getType().toString()), pcd.getType());
	}
	
	@Test
	public void convertDTO(){
		ProductCategoryBO bo =new ProductCategoryBO();
		bo.setId(1);
		bo.setName("测");
		bo.setPath("www.baidu.com");
		bo.setParentId(2);
		bo.setLevel(3);
		
		ProductCategoryDTO dto =ProductCategoryConverter.convertDTO(bo);
		Assert.assertEquals(dto.getId(), bo.getId());
		Assert.assertEquals(dto.getName(), bo.getName());
		Assert.assertEquals(dto.getPath(), bo.getPath());
		Assert.assertEquals(dto.getParentId(), bo.getParentId());
		Assert.assertEquals(dto.getLevel(), bo.getLevel());
	}
	
}
