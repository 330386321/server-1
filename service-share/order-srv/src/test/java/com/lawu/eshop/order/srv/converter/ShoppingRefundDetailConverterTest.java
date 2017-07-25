package com.lawu.eshop.order.srv.converter;

import org.junit.Assert;

import com.lawu.eshop.order.srv.bo.ShoppingRefundDetailBO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月25日
 */
public class ShoppingRefundDetailConverterTest {

	 public static void assertShoppingRefundDetailBO(ShoppingRefundDetailDO expected, ShoppingRefundDetailBO actual){
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(expected.getGmtCreate().getTime(), actual.getGmtCreate().getTime());
    	Assert.assertEquals(expected.getGmtModified().getTime(), actual.getGmtModified().getTime());
    	Assert.assertEquals(expected.getExpressCompanyId(), actual.getExpressCompanyId());
    	Assert.assertEquals(expected.getAmount().doubleValue(), actual.getAmount().doubleValue(), 0D);
    	Assert.assertEquals(expected.getId(), actual.getId());
    	Assert.assertEquals(expected.getConsigneeAddress(), actual.getConsigneeAddress());
    	Assert.assertEquals(expected.getConsigneeMobile(), actual.getConsigneeMobile());
    	Assert.assertEquals(expected.getConsigneeName(), actual.getConsigneeName());
    	Assert.assertEquals(expected.getDescription(), actual.getDescription());
    	Assert.assertEquals(expected.getExpressCompanyCode(), actual.getExpressCompanyCode());
    	Assert.assertEquals(expected.getExpressCompanyName(), actual.getExpressCompanyName());
    	Assert.assertEquals(expected.getGmtConfirmed(), actual.getGmtConfirmed());
    	Assert.assertEquals(expected.getGmtFill(), actual.getGmtFill());
    	Assert.assertEquals(expected.getGmtIntervention(), actual.getGmtIntervention());
    	Assert.assertEquals(expected.getGmtRefund(), actual.getGmtRefund());
    	Assert.assertEquals(expected.getGmtSubmit(), actual.getGmtSubmit());
    	Assert.assertEquals(expected.getIsAgree(), actual.getIsAgree());
    	Assert.assertEquals(expected.getReason(), actual.getReason());
    	Assert.assertEquals(expected.getRefusalReasons(), actual.getRefusalReasons());
    	Assert.assertEquals(expected.getShoppingOrderItemId(), actual.getShoppingOrderItemId());
    	Assert.assertEquals(expected.getStatus(), actual.getStatus().getValue());
    	Assert.assertEquals(expected.getType(), actual.getType().getValue());
    	Assert.assertEquals(expected.getVoucherPicture(), actual.getVoucherPicture());
    	Assert.assertEquals(expected.getWaybillNum(), actual.getWaybillNum());
    }
	
}
