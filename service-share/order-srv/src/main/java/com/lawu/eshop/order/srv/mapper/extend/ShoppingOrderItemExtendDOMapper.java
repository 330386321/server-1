package com.lawu.eshop.order.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemExtendDOExample;

public interface ShoppingOrderItemExtendDOMapper {
	
    List<ShoppingOrderItemExtendDO> selectByExample(ShoppingOrderItemExtendDOExample example);

}