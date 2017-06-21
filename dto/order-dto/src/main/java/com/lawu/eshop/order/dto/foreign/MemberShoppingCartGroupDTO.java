package com.lawu.eshop.order.dto.foreign;

import java.util.List;

public class MemberShoppingCartGroupDTO {
	
    /**
     * 同一个商家的商品放在一起
     */
    private List<MemberShoppingCartDTO> item;

	public List<MemberShoppingCartDTO> getItem() {
		return item;
	}

	public void setItem(List<MemberShoppingCartDTO> item) {
		this.item = item;
	}
    
}