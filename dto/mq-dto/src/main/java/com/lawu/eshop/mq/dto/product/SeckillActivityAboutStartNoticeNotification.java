package com.lawu.eshop.mq.dto.product;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * 抢购活动提醒MQ消息
 * 
 * @author jiangxinjun
 * @createDate 2017年11月27日
 * @updateDate 2017年11月27日
 */
public class SeckillActivityAboutStartNoticeNotification extends Notification {

    private static final long serialVersionUID = 2027629523621391005L;

    /**
     * 用户编号
     */
    private String memberNum;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 抢购活动商品id
     */
    private Long activityProductId;
    
    /**
     * 商品id
     */
    private Long productId;

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getActivityProductId() {
        return activityProductId;
    }

    public void setActivityProductId(Long activityProductId) {
        this.activityProductId = activityProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
}
