package com.lawu.eshop.product.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户端商品详情封装DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductInfoDTO {

	// 定时任务计算商品日销量使用
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtCreate;

	@ApiModelProperty(value = "商品ID")
	private Long id;

	@ApiModelProperty(value = "商家ID")
	private Long merchantId;

	@ApiModelProperty(value = "商品名称")
	private String name;

	@ApiModelProperty(value = "封面图片")
	private String featureImage;

	@ApiModelProperty(value = "滚动图片")
	private List<String> imagesHeadUrl;

	@ApiModelProperty(value = "型号最大价格")
	private String maxPrice;

	@ApiModelProperty(value = "型号最小价格")
	private String minPrice;

	@ApiModelProperty(value = "销量")
	private Integer totalSalesVolume;

	@ApiModelProperty(value = "型号信息")
	private List<MemberProductModelDTO> spec;

	@ApiModelProperty(value = "商品描述")
	private String content;
	
	@ApiModelProperty(value = "商品描述")
	private List<MemberProductImageDetailDTO> imageDetail;

	@ApiModelProperty(value = "门店信息")
	private MemberProductStoreDTO store;

	@ApiModelProperty(value = "评价信息")
	private List<MemberProductCommentInfoDTO> comments;
	
	@ApiModelProperty(value = "评价数量")
	private Integer commentCount;
	
	@ApiModelProperty(value = "是否收藏")
	private boolean favoriteFlag;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFeatureImage() {
		return featureImage;
	}

	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public List<String> getImagesHeadUrl() {
		return imagesHeadUrl;
	}

	public void setImagesHeadUrl(List<String> imagesHeadUrl) {
		this.imagesHeadUrl = imagesHeadUrl;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getTotalSalesVolume() {
		return totalSalesVolume;
	}

	public void setTotalSalesVolume(Integer totalSalesVolume) {
		this.totalSalesVolume = totalSalesVolume;
	}

	public List<MemberProductModelDTO> getSpec() {
		return spec;
	}

	public void setSpec(List<MemberProductModelDTO> spec) {
		this.spec = spec;
	}

	public List<MemberProductImageDetailDTO> getImageDetail() {
		return imageDetail;
	}

	public void setImageDetail(List<MemberProductImageDetailDTO> imageDetail) {
		this.imageDetail = imageDetail;
	}

	public MemberProductStoreDTO getStore() {
		return store;
	}

	public void setStore(MemberProductStoreDTO store) {
		this.store = store;
	}

	public List<MemberProductCommentInfoDTO> getComments() {
		return comments;
	}

	public void setComments(List<MemberProductCommentInfoDTO> comments) {
		this.comments = comments;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public boolean isFavoriteFlag() {
		return favoriteFlag;
	}

	public void setFavoriteFlag(boolean favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}
	
}
