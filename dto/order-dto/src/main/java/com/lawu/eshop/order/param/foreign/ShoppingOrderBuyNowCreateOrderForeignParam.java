package com.lawu.eshop.order.param.foreign;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * MemberApi购物车结算对外暴露参数
 * 
 * @author Sunny
 * @date 2017/4/6
 */
@ApiModel
public class ShoppingOrderBuyNowCreateOrderForeignParam {
	
	/**
	 * 运费
	 */
	@ApiModelProperty(name = "freightPrice", required = false, value = "运费")
	private BigDecimal freightPrice;
	
	/**
	 * 买家留言
	 */
	@ApiModelProperty(name = "message", required = false, value = "买家留言")
	private String message;
	
	/**
	 * 地址id
	 */
	@NotNull(message = "地址id不能为空")
	@ApiModelProperty(name = "addressId", required = true, value = "地址id")
	private Long addressId;
	
    /**
     * 商品型号ID
     */
    @NotNull(message = "产品型号id不能为空")
    @ApiModelProperty(name = "productModelId", required = true, value = "商品型号ID")
    private Long productModelId;
    
    /**
     * 活动商品型号id
     */
    @ApiModelProperty(value = "活动商品型号id", required = true)
    private Long activityProductModelId;
    
    /**
     * 数量
     */
    @Min(value = 1, message = "数量不能小于1")
    @ApiModelProperty(name = "quantity", required = true, value = "数量")
    private Integer quantity;

	public BigDecimal getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	public Long getActivityProductModelId() {
        return activityProductModelId;
    }

    public void setActivityProductModelId(Long activityProductModelId) {
        this.activityProductModelId = activityProductModelId;
    }

    public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}