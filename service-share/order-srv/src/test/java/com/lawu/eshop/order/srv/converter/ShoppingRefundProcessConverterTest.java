package com.lawu.eshop.order.srv.converter;

import java.util.List;

import org.junit.Assert;

import com.lawu.eshop.order.dto.ShoppingRefundProcessDTO;
import com.lawu.eshop.order.srv.bo.ShoppingRefundProcessBO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDO;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月25日
 */
public class ShoppingRefundProcessConverterTest {
	
	public static void assertShoppingRefundProcessDTO(ShoppingRefundProcessBO expected, ShoppingRefundProcessDTO actual) {
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getGmtCreate(), actual.getGmtCreate());
		Assert.assertEquals(expected.getRefundStatus(), actual.getRefundStatus().getValue());
	}
	
	public static void assertShoppingRefundProcessDTOList(List<ShoppingRefundProcessBO> expected, List<ShoppingRefundProcessDTO> actual) {
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertShoppingRefundProcessDTO(expected.get(i), actual.get(i));
		}
	}
	
	public static void assertShoppingRefundProcessBO(ShoppingRefundProcessDO expected, ShoppingRefundProcessBO actual) {
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getGmtCreate(), actual.getGmtCreate());
		Assert.assertEquals(expected.getRefundStatus(), actual.getRefundStatus().getValue());
		Assert.assertEquals(expected.getShoppingRefundDetailId(), actual.getShoppingRefundDetailId());
	}
	
	public static void assertShoppingRefundProcessBOList(List<ShoppingRefundProcessDO> expected, List<ShoppingRefundProcessBO> actual) {
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertShoppingRefundProcessBO(expected.get(i), actual.get(i));
		}
	}
}
