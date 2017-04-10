package com.lawu.eshop.order.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShoppingOrderDO implements Serializable {
    /**
     *
     * 主键
     * shopping_order.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 用户ID
     * shopping_order.member_id
     *
     * @mbg.generated
     */
    private Long memberId;

    /**
     *
     * 商家ID
     * shopping_order.merchant_id
     *
     * @mbg.generated
     */
    private Long merchantId;

    /**
     *
     * 商家名称
     * shopping_order.merchant_name
     *
     * @mbg.generated
     */
    private String merchantName;

    /**
     *
     * 收货人姓名
     * shopping_order.consignee_name
     *
     * @mbg.generated
     */
    private String consigneeName;

    /**
     *
     * 收货人地址
     * shopping_order.consignee_address
     *
     * @mbg.generated
     */
    private String consigneeAddress;

    /**
     *
     * 收货人手机号码
     * shopping_order.consignee_mobile
     *
     * @mbg.generated
     */
    private String consigneeMobile;

    /**
     *
     * 订单备注(退货理由)
     * shopping_order.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * 买家留言
     * shopping_order.message
     *
     * @mbg.generated
     */
    private String message;

    /**
     *
     * 运费
     * shopping_order.freight_price
     *
     * @mbg.generated
     */
    private BigDecimal freightPrice;

    /**
     *
     * 商品总价
     * shopping_order.commodity_total_price
     *
     * @mbg.generated
     */
    private BigDecimal commodityTotalPrice;

    /**
     *
     * 订单总价
     * shopping_order.order_total_price
     *
     * @mbg.generated
     */
    private BigDecimal orderTotalPrice;

    /**
     *
     * 订单的总状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)
     * shopping_order.order_status
     *
     * @mbg.generated
     */
    private Byte orderStatus;

    /**
     *
     * 状态(0删除1正常)
     * shopping_order.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 是否支持无理由退货,0否 1是
     * shopping_order.is_no_reason_return
     *
     * @mbg.generated
     */
    private Boolean isNoReasonReturn;

    /**
     *
     * 是否评价
     * shopping_order.is_evaluation
     *
     * @mbg.generated
     */
    private Boolean isEvaluation;

    /**
     *
     * 订单编号
     * shopping_order.order_num
     *
     * @mbg.generated
     */
    private String orderNum;

    /**
     *
     * 运单编号
     * shopping_order.waybill_num
     *
     * @mbg.generated
     */
    private String waybillNum;

    /**
     *
     * 快递公司id
     * shopping_order.express_company_id
     *
     * @mbg.generated
     */
    private Integer expressCompanyId;

    /**
     *
     * 快递公司编码
     * shopping_order.express_company_code
     *
     * @mbg.generated
     */
    private String expressCompanyCode;

    /**
     *
     * 快递公司名称
     * shopping_order.express_company_name
     *
     * @mbg.generated
     */
    private String expressCompanyName;

    /**
     *
     * 付款时间
     * shopping_order.gmt_payment
     *
     * @mbg.generated
     */
    private Date gmtPayment;

    /**
     *
     * 发货时间
     * shopping_order.gmt_transport
     *
     * @mbg.generated
     */
    private Date gmtTransport;

    /**
     *
     * 交易时间
     * shopping_order.gmt_transaction
     *
     * @mbg.generated
     */
    private Date gmtTransaction;

    /**
     *
     * 创建时间
     * shopping_order.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     *
     * 修改时间
     * shopping_order.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table shopping_order
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.id
     *
     * @return the value of shopping_order.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.id
     *
     * @param id the value for shopping_order.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.member_id
     *
     * @return the value of shopping_order.member_id
     *
     * @mbg.generated
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.member_id
     *
     * @param memberId the value for shopping_order.member_id
     *
     * @mbg.generated
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.merchant_id
     *
     * @return the value of shopping_order.merchant_id
     *
     * @mbg.generated
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.merchant_id
     *
     * @param merchantId the value for shopping_order.merchant_id
     *
     * @mbg.generated
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.merchant_name
     *
     * @return the value of shopping_order.merchant_name
     *
     * @mbg.generated
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.merchant_name
     *
     * @param merchantName the value for shopping_order.merchant_name
     *
     * @mbg.generated
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.consignee_name
     *
     * @return the value of shopping_order.consignee_name
     *
     * @mbg.generated
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.consignee_name
     *
     * @param consigneeName the value for shopping_order.consignee_name
     *
     * @mbg.generated
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.consignee_address
     *
     * @return the value of shopping_order.consignee_address
     *
     * @mbg.generated
     */
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.consignee_address
     *
     * @param consigneeAddress the value for shopping_order.consignee_address
     *
     * @mbg.generated
     */
    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.consignee_mobile
     *
     * @return the value of shopping_order.consignee_mobile
     *
     * @mbg.generated
     */
    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.consignee_mobile
     *
     * @param consigneeMobile the value for shopping_order.consignee_mobile
     *
     * @mbg.generated
     */
    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile == null ? null : consigneeMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.remark
     *
     * @return the value of shopping_order.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.remark
     *
     * @param remark the value for shopping_order.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.message
     *
     * @return the value of shopping_order.message
     *
     * @mbg.generated
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.message
     *
     * @param message the value for shopping_order.message
     *
     * @mbg.generated
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.freight_price
     *
     * @return the value of shopping_order.freight_price
     *
     * @mbg.generated
     */
    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.freight_price
     *
     * @param freightPrice the value for shopping_order.freight_price
     *
     * @mbg.generated
     */
    public void setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.commodity_total_price
     *
     * @return the value of shopping_order.commodity_total_price
     *
     * @mbg.generated
     */
    public BigDecimal getCommodityTotalPrice() {
        return commodityTotalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.commodity_total_price
     *
     * @param commodityTotalPrice the value for shopping_order.commodity_total_price
     *
     * @mbg.generated
     */
    public void setCommodityTotalPrice(BigDecimal commodityTotalPrice) {
        this.commodityTotalPrice = commodityTotalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.order_total_price
     *
     * @return the value of shopping_order.order_total_price
     *
     * @mbg.generated
     */
    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.order_total_price
     *
     * @param orderTotalPrice the value for shopping_order.order_total_price
     *
     * @mbg.generated
     */
    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.order_status
     *
     * @return the value of shopping_order.order_status
     *
     * @mbg.generated
     */
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.order_status
     *
     * @param orderStatus the value for shopping_order.order_status
     *
     * @mbg.generated
     */
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.status
     *
     * @return the value of shopping_order.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.status
     *
     * @param status the value for shopping_order.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.is_no_reason_return
     *
     * @return the value of shopping_order.is_no_reason_return
     *
     * @mbg.generated
     */
    public Boolean getIsNoReasonReturn() {
        return isNoReasonReturn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.is_no_reason_return
     *
     * @param isNoReasonReturn the value for shopping_order.is_no_reason_return
     *
     * @mbg.generated
     */
    public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
        this.isNoReasonReturn = isNoReasonReturn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.is_evaluation
     *
     * @return the value of shopping_order.is_evaluation
     *
     * @mbg.generated
     */
    public Boolean getIsEvaluation() {
        return isEvaluation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.is_evaluation
     *
     * @param isEvaluation the value for shopping_order.is_evaluation
     *
     * @mbg.generated
     */
    public void setIsEvaluation(Boolean isEvaluation) {
        this.isEvaluation = isEvaluation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.order_num
     *
     * @return the value of shopping_order.order_num
     *
     * @mbg.generated
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.order_num
     *
     * @param orderNum the value for shopping_order.order_num
     *
     * @mbg.generated
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.waybill_num
     *
     * @return the value of shopping_order.waybill_num
     *
     * @mbg.generated
     */
    public String getWaybillNum() {
        return waybillNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.waybill_num
     *
     * @param waybillNum the value for shopping_order.waybill_num
     *
     * @mbg.generated
     */
    public void setWaybillNum(String waybillNum) {
        this.waybillNum = waybillNum == null ? null : waybillNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.express_company_id
     *
     * @return the value of shopping_order.express_company_id
     *
     * @mbg.generated
     */
    public Integer getExpressCompanyId() {
        return expressCompanyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.express_company_id
     *
     * @param expressCompanyId the value for shopping_order.express_company_id
     *
     * @mbg.generated
     */
    public void setExpressCompanyId(Integer expressCompanyId) {
        this.expressCompanyId = expressCompanyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.express_company_code
     *
     * @return the value of shopping_order.express_company_code
     *
     * @mbg.generated
     */
    public String getExpressCompanyCode() {
        return expressCompanyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.express_company_code
     *
     * @param expressCompanyCode the value for shopping_order.express_company_code
     *
     * @mbg.generated
     */
    public void setExpressCompanyCode(String expressCompanyCode) {
        this.expressCompanyCode = expressCompanyCode == null ? null : expressCompanyCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.express_company_name
     *
     * @return the value of shopping_order.express_company_name
     *
     * @mbg.generated
     */
    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.express_company_name
     *
     * @param expressCompanyName the value for shopping_order.express_company_name
     *
     * @mbg.generated
     */
    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName == null ? null : expressCompanyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.gmt_payment
     *
     * @return the value of shopping_order.gmt_payment
     *
     * @mbg.generated
     */
    public Date getGmtPayment() {
        return gmtPayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.gmt_payment
     *
     * @param gmtPayment the value for shopping_order.gmt_payment
     *
     * @mbg.generated
     */
    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.gmt_transport
     *
     * @return the value of shopping_order.gmt_transport
     *
     * @mbg.generated
     */
    public Date getGmtTransport() {
        return gmtTransport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.gmt_transport
     *
     * @param gmtTransport the value for shopping_order.gmt_transport
     *
     * @mbg.generated
     */
    public void setGmtTransport(Date gmtTransport) {
        this.gmtTransport = gmtTransport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.gmt_transaction
     *
     * @return the value of shopping_order.gmt_transaction
     *
     * @mbg.generated
     */
    public Date getGmtTransaction() {
        return gmtTransaction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.gmt_transaction
     *
     * @param gmtTransaction the value for shopping_order.gmt_transaction
     *
     * @mbg.generated
     */
    public void setGmtTransaction(Date gmtTransaction) {
        this.gmtTransaction = gmtTransaction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.gmt_create
     *
     * @return the value of shopping_order.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.gmt_create
     *
     * @param gmtCreate the value for shopping_order.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_order.gmt_modified
     *
     * @return the value of shopping_order.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_order.gmt_modified
     *
     * @param gmtModified the value for shopping_order.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}