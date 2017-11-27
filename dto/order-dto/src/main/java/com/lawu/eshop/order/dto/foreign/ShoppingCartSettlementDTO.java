package com.lawu.eshop.order.dto.foreign;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingCartSettlementDTO {
	
    /**
     * 总计
     */
    @ApiModelProperty(value = "总计", required = true)
    private BigDecimal total;
    
    /**
     * 用户余额
     */
    @ApiModelProperty(value = "用户余额", required = true)
    private BigDecimal balance;
    
    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id", required = true)
    private Long activityId;
    
    /**
     * 活动商品id
     */
    @ApiModelProperty(value = "活动商品id", required = true)
    private Long activityProductId;
    
    /**
     * 活动商品型号id
     */
    @ApiModelProperty(value = "活动商品型号id", required = true)
    private Long activityProductModelId;
    
    /**
     * 订单数据 - 分单显示
     */
    @ApiModelProperty(value = "订单数据", required = true)
    private List<ShoppingCartSettlementItemDTO> items;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityProductId() {
        return activityProductId;
    }

    public void setActivityProductId(Long activityProductId) {
        this.activityProductId = activityProductId;
    }
    
    public Long getActivityProductModelId() {
        return activityProductModelId;
    }

    public void setActivityProductModelId(Long activityProductModelId) {
        this.activityProductModelId = activityProductModelId;
    }

    public List<ShoppingCartSettlementItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartSettlementItemDTO> items) {
		this.items = items;
	}
    
}