package com.lawu.eshop.ad.param;

import java.util.List;

public class AdsolrFindParam extends AdSolrParam{
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 会员区域
	 */
	private String regionPath;
	
	/**
	 * 当前会员输入那个商家的粉丝
	 */
	private List<Long> merchantIds;
	
	
	private AdSolrParam adSolrParam;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRegionPath() {
		return regionPath;
	}

	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}

	public List<Long> getMerchantIds() {
		return merchantIds;
	}

	public void setMerchantIds(List<Long> merchantIds) {
		this.merchantIds = merchantIds;
	}

	public AdSolrParam getAdSolrParam() {
		return adSolrParam;
	}

	public void setAdSolrParam(AdSolrParam adSolrParam) {
		this.adSolrParam = adSolrParam;
	}
	
	
	

}
