package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.util.List;

public class MemberShoppingCartGroupDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    /**
     * 同一个商家的商品放在一起
     */
    List<MemberShoppingCartDTO> item;

	public List<MemberShoppingCartDTO> getItem() {
		return item;
	}

	public void setItem(List<MemberShoppingCartDTO> item) {
		this.item = item;
	}
    
}