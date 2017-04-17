package com.lawu.eshop.product.param;

import java.util.List;
import java.util.Map;

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
	
	private Map<String,List<String>> detailImageMap = null;//存放详情图片
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Map<String, List<String>> getDetailImageMap() {
		return detailImageMap;
	}

	public void setDetailImageMap(Map<String, List<String>> detailImageMap) {
		this.detailImageMap = detailImageMap;
	}
	

}
