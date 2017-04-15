package com.lawu.eshop.mall.param;

import com.lawu.eshop.mall.constants.CommentAnonymousEnum;
import com.lawu.eshop.mall.constants.CommentGradeEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
public class CommentProductParam {

    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容", required = true)
    private String content;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分", required = true)
    private CommentGradeEnum gradeEnum;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    private Long productId;
    
    /**
     * 订单项ID
     */
    @ApiModelProperty(value = "订单项ID", required = true)
    private Long shoppingOrderItemId;

    /**
     * 是否匿名（0：否1：是）
     */
    @ApiModelProperty(value = "是否匿名（COMMENT_ANONYMOUS_FAILED：否 COMMENT_ANONYMOUS_SUCCESS：是）", required = true)
    private CommentAnonymousEnum anonymousEnum;

    @ApiModelProperty(value = "订单项ID")
    private Long orderItemId;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentGradeEnum getGradeEnum() {
        return gradeEnum;
    }

    public void setGradeEnum(CommentGradeEnum gradeEnum) {
        this.gradeEnum = gradeEnum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CommentAnonymousEnum getAnonymousEnum() {
        return anonymousEnum;
    }

    public void setAnonymousEnum(CommentAnonymousEnum anonymousEnum) {
        this.anonymousEnum = anonymousEnum;
    }

	public Long getShoppingOrderItemId() {
		return shoppingOrderItemId;
	}

	public void setShoppingOrderItemId(Long shoppingOrderItemId) {
		this.shoppingOrderItemId = shoppingOrderItemId;
	}

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }
}
