/**
 * 
 */
package com.lawu.eshop.product.srv.converter;

import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.RecommendProductCategoryBO;
import com.lawu.eshop.product.srv.domain.RecommendProductCategoryDO;


/**
 * @author lihj
 * @date 2017年7月17日
 */
public class RecommendProductCategoryConverterTest {

	
	@Test
	public void convertBO(){
		RecommendProductCategoryDO rp =new RecommendProductCategoryDO();
		rp.setId(1L);
		rp.setCategoryId(2);
		rp.setCategoryName("categoryName");
		rp.setGmtCreate(new Date());
		RecommendProductCategoryBO rpd =RecommendProductCategoryConverter.convertBO(rp);
		Assert.assertEquals(rpd.getId(), rp.getId());
		Assert.assertEquals(rpd.getCategoryId(), rp.getCategoryId());
		Assert.assertEquals(rpd.getCategoryName(), rp.getCategoryName());
		Assert.assertEquals(rpd.getGmtCreate(), rp.getGmtCreate());
	}
	
	
	@Test
	public void convertDTO(){
		RecommendProductCategoryBO rp =new RecommendProductCategoryBO();
		rp.setId(1L);
		rp.setCategoryId(2);
		rp.setCategoryName("categoryName");
		rp.setGmtCreate(new Date());
		RecommendProductCategoryDTO rpc =RecommendProductCategoryConverter.convertDTO(rp);
		Assert.assertEquals(rpc.getId(), rp.getId());
		Assert.assertEquals(rpc.getCategoryId(), rp.getCategoryId());
		Assert.assertEquals(rpc.getCategoryName(), rp.getCategoryName());
		Assert.assertEquals(rpc.getGmtCreate(), rp.getGmtCreate());
	}
	
	@Test
	public void convertBOList(){
		List<RecommendProductCategoryDO> recommendProductCategoryDOS =Lists.newArrayList();
		RecommendProductCategoryDO rp =new RecommendProductCategoryDO();
		rp.setId(1L);
		rp.setCategoryId(2);
		rp.setCategoryName("categoryName");
		rp.setGmtCreate(new Date());
		recommendProductCategoryDOS.add(rp);
		List<RecommendProductCategoryBO> list =RecommendProductCategoryConverter.convertBO(recommendProductCategoryDOS);
		Assert.assertEquals(list.get(0).getId(), rp.getId());
		Assert.assertEquals(list.get(0).getCategoryId(), rp.getCategoryId());
		Assert.assertEquals(list.get(0).getCategoryName(), rp.getCategoryName());
		Assert.assertEquals(list.get(0).getGmtCreate(), rp.getGmtCreate());
	}
	
	@Test
	public void convertDTOList(){
		List<RecommendProductCategoryBO> recommendProductCategoryBOS =Lists.newArrayList();
		RecommendProductCategoryBO rp =new RecommendProductCategoryBO();
		rp.setId(1L);
		rp.setCategoryId(2);
		rp.setCategoryName("categoryName");
		rp.setGmtCreate(new Date());
		recommendProductCategoryBOS.add(rp);
		List<RecommendProductCategoryDTO> list =RecommendProductCategoryConverter.convertDTO(recommendProductCategoryBOS);
		Assert.assertEquals(list.get(0).getId(), rp.getId());
		Assert.assertEquals(list.get(0).getCategoryId(), rp.getCategoryId());
		Assert.assertEquals(list.get(0).getCategoryName(), rp.getCategoryName());
		Assert.assertEquals(list.get(0).getGmtCreate(), rp.getGmtCreate());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
