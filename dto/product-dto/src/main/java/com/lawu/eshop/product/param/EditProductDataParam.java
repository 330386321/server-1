package com.lawu.eshop.product.param;

/**
 * 
 * <p>
 * Description: api提交给svr的参数对象
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午8:21:09
 *
 */
public class EditProductDataParam extends EditProductParam{

	private Long merchantId;
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	

}
