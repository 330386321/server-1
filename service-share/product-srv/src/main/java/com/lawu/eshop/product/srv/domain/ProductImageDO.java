package com.lawu.eshop.product.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class ProductImageDO implements Serializable {
    /**
     *
     * 主键
     * product_image.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 商品ID
     * product_image.product_id
     *
     * @mbg.generated
     */
    private Long productId;

    /**
     *
     * 图片路径
     * product_image.image_path
     *
     * @mbg.generated
     */
    private String imagePath;

    /**
     *
     * 状态(0删除1正常)
     * product_image.status
     *
     * @mbg.generated
     */
    private Boolean status;

    /**
     *
     * 图片类型(1-头部滚动图片2-详情图片)
     * product_image.img_type
     *
     * @mbg.generated
     */
    private Byte imgType;

    /**
     *
     * 顺序
     * product_image.sortid
     *
     * @mbg.generated
     */
    private Integer sortid;

    /**
     *
     * 修改时间
     * product_image.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * product_image.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_image
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.id
     *
     * @return the value of product_image.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.id
     *
     * @param id the value for product_image.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.product_id
     *
     * @return the value of product_image.product_id
     *
     * @mbg.generated
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.product_id
     *
     * @param productId the value for product_image.product_id
     *
     * @mbg.generated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.image_path
     *
     * @return the value of product_image.image_path
     *
     * @mbg.generated
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.image_path
     *
     * @param imagePath the value for product_image.image_path
     *
     * @mbg.generated
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.status
     *
     * @return the value of product_image.status
     *
     * @mbg.generated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.status
     *
     * @param status the value for product_image.status
     *
     * @mbg.generated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.img_type
     *
     * @return the value of product_image.img_type
     *
     * @mbg.generated
     */
    public Byte getImgType() {
        return imgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.img_type
     *
     * @param imgType the value for product_image.img_type
     *
     * @mbg.generated
     */
    public void setImgType(Byte imgType) {
        this.imgType = imgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.sortid
     *
     * @return the value of product_image.sortid
     *
     * @mbg.generated
     */
    public Integer getSortid() {
        return sortid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.sortid
     *
     * @param sortid the value for product_image.sortid
     *
     * @mbg.generated
     */
    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.gmt_modified
     *
     * @return the value of product_image.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.gmt_modified
     *
     * @param gmtModified the value for product_image.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_image.gmt_create
     *
     * @return the value of product_image.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_image.gmt_create
     *
     * @param gmtCreate the value for product_image.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}