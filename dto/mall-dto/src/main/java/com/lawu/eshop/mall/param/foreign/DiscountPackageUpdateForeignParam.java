package com.lawu.eshop.mall.param.foreign;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DiscountPackageUpdateForeignParam {
	
	/**
	 * 优惠套餐id
	 */
	@NotNull
	@ApiModelProperty(name = "id", value = "优惠套餐id", required = true)
	private Long id;
	
	/**
	 * 套餐名称
	 */
	@NotBlank
	@ApiModelProperty(name = "name", value = "套餐名称", required = true)
	private String name;

	/**
	 * 套餐价格
	 */
	@NotNull
	@ApiModelProperty(name = "name", value = "套餐价格", required = true)
	private BigDecimal price;

	/**
	 * 其他说明
	 */
	@ApiModelProperty(name = "name", value = "其他说明", required = true)
	private String otherInstructions;

	/**
	 * 有效期-开始(yyyy-MM-dd)
	 */
	@NotNull
	@ApiModelProperty(name = "validityPeriodBegin", value = "有效期-开始(yyyy-MM-dd)", required = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date validityPeriodBegin;

	/**
	 * 有效期-结束(yyyy-MM-dd)
	 */
	@NotNull
	@ApiModelProperty(name = "validityPeriodEnd", value = "有效期-结束(yyyy-MM-dd)", required = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date validityPeriodEnd;

	/**
	 * 使用时间-周一到周日(用1-7表示,并用逗号分隔)
	 */
	@NotBlank
	@ApiModelProperty(name = "useTimeWeek", value = "使用时间-周一到周日(用0-6表示,0为周日,并用逗号分隔)", required = true)
	private String useTimeWeek;

	/**
	 * 使用时间-开始(HH:mm)
	 */
	@NotNull
	@ApiModelProperty(name = "useTimeBegin", value = "使用时间-开始(HH:mm)", required = true)
	@DateTimeFormat(pattern = "HH:mm")
	private Date useTimeBegin;

	/**
	 * 使用时间-结束(HH:mm)
	 */
	@NotNull
	@ApiModelProperty(name = "useTimeBegin", value = "使用时间-开始(HH:mm)", required = true)
	@DateTimeFormat(pattern = "HH:mm")
	private Date useTimeEnd;

	/**
	 * 是否需要预约
	 */
	@NotNull
	@ApiModelProperty(name = "isReservation", value = "是否需要预约", required = true)
	private Boolean isReservation;

	/**
	 * 使用规则
	 */
	@ApiModelProperty(name = "useRules", value = "使用规则")
	private String useRules;
	
	/**
	 * 套餐内容
	 */
	@NotBlank(message = "套餐内容不能为空")
	@ApiModelProperty(name = "discountPackageContents", value = "套餐内容", notes = "如果是新增的套餐内容id置空", example = "[{\"id\":null,\"name\":null,\"quantity\":null,\"unit\":null,\"unitPrice\":null}]")
	private String discountPackageContents;
	
	/**
	 * 套餐图片详情
	 */
	@NotBlank(message = "套餐图片详情不能为空")
	@ApiModelProperty(name = "discountPackageImages", value = "套餐图片详情", notes = "如果是新增的图片详情id置空", example = "[{\"description\":null,\"id\":null}]")
	private String discountPackageImages;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getOtherInstructions() {
		return otherInstructions;
	}

	public void setOtherInstructions(String otherInstructions) {
		this.otherInstructions = otherInstructions;
	}

	public Date getValidityPeriodBegin() {
		return validityPeriodBegin;
	}

	public void setValidityPeriodBegin(Date validityPeriodBegin) {
		this.validityPeriodBegin = validityPeriodBegin;
	}

	public Date getValidityPeriodEnd() {
		return validityPeriodEnd;
	}

	public void setValidityPeriodEnd(Date validityPeriodEnd) {
		this.validityPeriodEnd = validityPeriodEnd;
	}

	public String getUseTimeWeek() {
		return useTimeWeek;
	}

	public void setUseTimeWeek(String useTimeWeek) {
		this.useTimeWeek = useTimeWeek;
	}

	public Date getUseTimeBegin() {
		return useTimeBegin;
	}

	public void setUseTimeBegin(Date useTimeBegin) {
		this.useTimeBegin = useTimeBegin;
	}

	public Date getUseTimeEnd() {
		return useTimeEnd;
	}

	public void setUseTimeEnd(Date useTimeEnd) {
		this.useTimeEnd = useTimeEnd;
	}

	public Boolean getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(Boolean isReservation) {
		this.isReservation = isReservation;
	}

	public String getUseRules() {
		return useRules;
	}

	public void setUseRules(String useRules) {
		this.useRules = useRules;
	}

	public String getDiscountPackageContents() {
		return discountPackageContents;
	}

	public void setDiscountPackageContents(String discountPackageContents) {
		this.discountPackageContents = discountPackageContents;
	}

	public String getDiscountPackageImages() {
		return discountPackageImages;
	}

	public void setDiscountPackageImages(String discountPackageImages) {
		this.discountPackageImages = discountPackageImages;
	}
	
}
