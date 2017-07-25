package com.lawu.eshop.order.srv.converter;

import org.junit.Assert;

import com.lawu.eshop.order.srv.bo.ShoppingRefundProcessBO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundProcessDO;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月25日
 */
public class ShoppingRefundProcessConverterTest {

    public static void assertShoppingRefundProcessBO(ShoppingRefundProcessDO expected, ShoppingRefundProcessBO actual) {
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(expected.getId(), actual.getId());
    	Assert.assertEquals(expected.getGmtCreate(), actual.getGmtCreate());
    	Assert.assertEquals(expected.getRefundStatus(), actual.getRefundStatus().getValue());
    	Assert.assertEquals(expected.getShoppingRefundDetailId(), actual.getShoppingRefundDetailId());
    }	
}
